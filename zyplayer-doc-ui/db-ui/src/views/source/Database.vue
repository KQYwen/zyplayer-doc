<template>
	<div class="table-database-vue">
		<el-tabs v-model="tabActiveName" @tab-click="tabActiveNameChange">
			<el-tab-pane label="数据表" name="columns">
			</el-tab-pane>
			<el-tab-pane label="数据表" name="columns">
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
	import datasourceApi from '../../common/api/datasource'

	export default {
		data() {
			return {
				columnListLoading: false,
				vueQueryParam: {},
				columnList: [],
				tableInfo: [],
				keyword: '',
			};
		},
		mounted: function () {
			// 延迟设置展开的目录，edit比app先初始化
			setTimeout(() => {
				this.$emit('initLoadDataList', {
					sourceId: this.vueQueryParam.sourceId,
					host: this.vueQueryParam.host,
					dbName: this.vueQueryParam.dbName
				});
			}, 500);
		},
		activated: function () {
			this.initQueryParam(this.$route);
		},
		methods: {
			initQueryParam(to) {
				this.vueQueryParam = to.query;
				let newName = {key: this.$route.fullPath, val: this.vueQueryParam.dbName};
				this.$store.commit('global/addTableName', newName);
			},
			tabActiveNameChange() {
				if (this.tabActiveName === 'relationChart') {
					this.$refs.relationChart.init({
						sourceId: this.vueQueryParam.sourceId,
						dbName: this.vueQueryParam.dbName,
						tableName: this.vueQueryParam.tableName,
					});
				} else if (this.tabActiveName === 'tableData') {
					if (!this.columnList || this.columnList.length <= 0) {
						this.$message.error("字段信息尚未加载成功，请稍候...");
						setTimeout(() => this.tabActiveName = 'columns', 0);
						return;
					}
					let primaryColumn = this.columnList.find(item => item.primaryKey == 1) || this.columnList[0];
					this.$refs.dataPreview.init({
						sourceId: this.vueQueryParam.sourceId,
						dbName: this.vueQueryParam.dbName,
						tableName: this.vueQueryParam.tableName,
						host: this.vueQueryParam.host,
						dbType: this.tableStatusInfo.dbType,
						// 默认排序字段，先随便取一个，impala等数据库必须排序后才能分页查
						orderColumn: primaryColumn.name,
					}, this.columnList);
				}
			},
		}
	}
</script>
<style>
	.table-database-vue .el-table td, .table-database-vue .el-table th{padding: 5px 0;}
	.table-database-vue .label{width: 140px; text-align: right;}
</style>

