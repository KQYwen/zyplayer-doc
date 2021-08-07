<template>
	<div class="table-procedure-vue">
		<el-card style="margin: 10px;" shadow="never">
			<div slot="header" class="clearfix">库信息</div>
			<el-row>
				<el-col :span="12"><span class="label">数据源：</span>{{vueQueryParam.host}}</el-col>
				<el-col :span="12"><span class="label">数据库：</span>{{vueQueryParam.dbName}}</el-col>
			</el-row>
		</el-card>
		<el-card style="margin: 10px;" shadow="never" header="函数管理">
			<el-form :inline="true">
				<el-form-item label="名称">
					<el-input v-model="searchParam.name" placeholder="名字"></el-input>
				</el-form-item>
				<el-form-item label="类型">
					<el-select v-model="searchParam.type" placeholder="类型">
						<el-option value="">全部</el-option>
						<el-option value="PROCEDURE" label="存储过程"></el-option>
						<el-option value="FUNCTION" label="函数"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" :loading="procedureListLoading" @click="searchProcedureList" icon="el-icon-search">查询</el-button>
					<el-button @click="createProc" icon="el-icon-circle-plus-outline">新建函数</el-button>
				</el-form-item>
			</el-form>
			<el-table :data="procedureList" stripe border style="width: 100%;">
				<el-table-column prop="name" label="名称" width="200"></el-table-column>
				<el-table-column prop="type" label="类型" width="200">
					<!--<template slot-scope="scope">{{scope.row.type=='PROCEDURE' ? '过程' : '函数'}}</template>-->
				</el-table-column>
				<el-table-column prop="definer" label="定义者"></el-table-column>
				<el-table-column prop="created" label="创建时间"></el-table-column>
				<el-table-column prop="action" label="操作">
					<template slot-scope="scope">
						<el-button type="primary" @click="doEditProc(scope.row)">编辑</el-button>
						<el-button type="danger" @click="doDeleteProc(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
					style="margin-top: 10px;"
					@size-change="handlePageSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage"
					:page-sizes="[10, 30, 50]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="tableTotalCount">
			</el-pagination>
		</el-card>
		<!--新建函数弹窗-->
		<el-dialog :inline="true" title="新建函数" :visible.sync="newProcedureDialogVisible" width="760px">
			<el-form label-width="120px">
				<el-form-item label="类型：">
					<el-select v-model="newProcedureInfo.type" placeholder="请选择类型" style="width: 100%">
						<el-option value="PROCEDURE" label="存储过程"></el-option>
						<el-option value="FUNCTION" label="函数"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="函数名：">
					<el-input v-model="newProcedureInfo.name" placeholder="请输入函数名"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" style="text-align: center;">
				<el-button @click="newProcedureOk" type="primary">下一步</el-button>
				<el-button @click="newProcedureDialogVisible=false" plain>取消</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import datasourceApi from '../../common/api/datasource'

	export default {
		data() {
			return {
				procedureListLoading: false,
				vueQueryParam: {},
				procedureList: [],
				newProcedureDialogVisible: false,
				newProcedureInfo: {
					type: '',
					name: '',
				},
				searchParam: {
					name: '',
					type: ''
				},
				pageSize: 30,
				currentPage: 1,
				tableTotalCount: 0,
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
			this.initQueryParam(this.$route);
			this.searchProcedureList();
		},
		methods: {
			initQueryParam(to) {
				this.vueQueryParam = to.query;
				let newName = {key: this.$route.fullPath, val: '函数管理'};
				this.$store.commit('global/addTableName', newName);
			},
			handleCurrentChange(to) {
				this.currentPage = to;
				this.searchProcedureList();
			},
			handlePageSizeChange(to) {
				this.pageSize = to;
				this.searchProcedureList();
			},
			searchProcedureList() {
				this.procedureListLoading = true;
				let param = {...this.vueQueryParam, ...this.searchParam, pageNum: this.currentPage, pageSize: this.pageSize};
				datasourceApi.procedureList(param).then(json => {
					if (this.currentPage == 1) {
						this.tableTotalCount = json.total || 0;
					}
					this.procedureList = json.data || [];
					this.procedureListLoading = false;
				}).catch(() => {
					this.procedureListLoading = false;
				});
			},
			doEditProc(item) {
				let param = {...this.vueQueryParam, typeName: item.type, procName: item.name};
				this.$router.push({path: '/procedure/edit', query: param});
			},
			createProc() {
				this.newProcedureDialogVisible = true;
			},
			newProcedureOk() {
				if (!this.newProcedureInfo.type) {
					this.$message.error("请先选择类型");return;
				}
				if (!this.newProcedureInfo.name) {
					this.$message.error("请先输入函数名");return;
				}
				let param = {
					...this.vueQueryParam,
					typeName: this.newProcedureInfo.type,
					procName: this.newProcedureInfo.name
				};
				this.newProcedureDialogVisible = false;
				this.newProcedureInfo = {type: '', name: ''};
				this.$router.push({path: '/procedure/edit', query: param});
			},
			doDeleteProc(item) {
				let param = {
					sourceId: this.vueQueryParam.sourceId,
					dbName: this.vueQueryParam.dbName,
					typeName: item.type,
					procName: item.name
				};
				this.$confirm('确定要删除此存储过程吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					datasourceApi.deleteProcedure(param).then(json => {
						this.$message.success("删除成功！");
						this.searchProcedureList();
					});
				}).catch(() => {});
			},
		}
	}
</script>
<style>
	.table-procedure-vue .el-table td, .table-database-vue .el-table th{padding: 5px 0;}
</style>

