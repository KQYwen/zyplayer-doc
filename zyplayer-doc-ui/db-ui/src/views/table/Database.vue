<template>
    <div class="table-database-vue">
        <el-card style="margin: 10px;" shadow="never">
            <div slot="header" class="clearfix">库信息</div>
			<el-row style="margin-bottom: 20px;">
				<el-col :span="12"><span class="label">数据源：</span>{{vueQueryParam.host}}</el-col>
				<el-col :span="12"><span class="label">数据库：</span>{{vueQueryParam.dbName}}</el-col>
			</el-row>
			<el-row>
				<el-col :span="24">
					<span class="label">关键字：</span>
					<el-input v-model="keyword" placeholder="输入字段名或注释搜索库中相关的表或字段信息" style="width: 350px;margin-right: 10px;"></el-input>
					<el-button class="search-submit" type="primary" icon="el-icon-search" @click="searchSubmit">模糊搜索</el-button>
					<el-button icon="el-icon-coin" @click="funcManage">函数管理</el-button>
				</el-col>
			</el-row>
        </el-card>
        <div style="padding: 10px;" v-loading="columnListLoading">
            <el-table :data="columnList" stripe border style="width: 100%; margin-bottom: 5px;">
                <el-table-column prop="tableName" label="表名" width="200"></el-table-column>
                <el-table-column prop="columnName" label="字段名" width="200"></el-table-column>
                <el-table-column prop="description" label="注释"></el-table-column>
            </el-table>
        </div>
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
            searchSubmit() {
                this.columnListLoading = true;
                this.vueQueryParam.searchText = this.keyword;
                datasourceApi.tableAndColumnBySearch(this.vueQueryParam).then(json => {
                    this.columnList = json.data || [];
                    this.columnListLoading = false;
                });
            },
            funcManage() {
				this.$router.push({path: '/procedure/list', query: this.vueQueryParam});
            },
        }
    }
</script>
<style>
    .table-database-vue .el-table td, .table-database-vue .el-table th{padding: 5px 0;}
    .table-database-vue .label{width: 140px; text-align: right;}
</style>

