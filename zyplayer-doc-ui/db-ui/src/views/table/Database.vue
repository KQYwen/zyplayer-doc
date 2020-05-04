<template>
    <div class="table-database-vue">
        <el-card style="margin: 10px;" shadow="never">
            <div slot="header" class="clearfix">库信息</div>
            <el-form label-width="100px">
                <el-form-item label="数据源：">{{vueQueryParam.host}}</el-form-item>
                <el-form-item label="数据库：">{{vueQueryParam.dbName}}</el-form-item>
            </el-form>
            <el-form :inline="true" label-width="100px">
                <el-form-item label="关键字：">
                    <el-input v-model="keyword" placeholder="输入字段名或注释搜索相关的表或字段信息" style="width: 500px;"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="search-submit" type="primary" icon="el-icon-search" @click="searchSubmit">模糊搜索</el-button>
                </el-form-item>
            </el-form>
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
    import global from '../../common/config/global'

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
        activated: function () {
            this.initQueryParam(this.$route);
            // 延迟设置展开的目录，edit比app先初始化
            setTimeout(() => {
                global.vue.$app.initLoadDataList(this.vueQueryParam.sourceId, this.vueQueryParam.host, this.vueQueryParam.dbName);
            }, 500);
        },
        methods: {
            initQueryParam(to) {
                this.vueQueryParam = to.query;
                let newName = {key: this.$route.fullPath, val: '库-' + this.vueQueryParam.dbName};
                this.$store.commit('global/addTableName', newName);
            },
            searchSubmit() {
                let that = this;
                this.columnListLoading = true;
                this.vueQueryParam.searchText = this.keyword;
                this.common.post(this.apilist1.tableAndColumnBySearch, this.vueQueryParam, function (json) {
                    that.columnList = json.data || [];
                    that.columnListLoading = false;
                });
            },
        }
    }
</script>
<style>
    .table-database-vue .el-table td, .table-database-vue .el-table th{padding: 5px 0;}
</style>

