<template>
    <div class="table-info-vue">
        <el-card style="margin: 10px;">
            <div slot="header" class="clearfix">字段信息</div>
            <div style="padding: 10px;" v-loading="columnListLoading">
                <el-table :data="columnList" stripe border style="width: 100%; margin-bottom: 5px;">
                    <el-table-column prop="name" label="字段名" width="200"></el-table-column>
                    <el-table-column label="自增" width="50">
                        <template slot-scope="scope">{{scope.row.isidentity ? '是' : '否'}}</template>
                    </el-table-column>
                    <el-table-column prop="type" label="类型" width="150"></el-table-column>
                    <el-table-column prop="length" label="长度" width="80"></el-table-column>
                    <el-table-column label="NULL" width="60">
                        <template slot-scope="scope">{{scope.row.nullable ? '允许' : '不允许'}}</template>
                    </el-table-column>
                    <el-table-column label="主键" width="50">
                        <template slot-scope="scope">{{scope.row.primaryKey ? '是' : '否'}}</template>
                    </el-table-column>
                    <el-table-column label="注释">
                        <template slot-scope="scope">
                            <div class="description">{{scope.row.description}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>

<script>
    var app;

    export default {
        data() {
            return {
                columnListLoading: false,
                vueQueryParam: {},
                columnList: [],
                tableInfo: [],
            };
        },
        beforeRouteUpdate(to, from, next) {
            this.initQueryParam(to);
            next();
        },
        mounted: function () {
            app = this;
            this.initQueryParam(this.$route);
        },
        methods: {
            initQueryParam(to) {
                this.vueQueryParam = to.query;
                setTimeout(function () {
                    var columnList = docDbDatabase.columnList[app.vueQueryParam.tableName] || [];
                    for (var i = 0; i < columnList.length; i++) {
                        columnList[i].newDesc = columnList[i].description;
                    }
                    app.columnList = columnList;
                }, 200);
            },
        }
    }
</script>
<style>
    .table-info-vue .el-form-item{margin-bottom: 5px;}
    .table-info-vue .edit-table-desc{color: #409EFF;}
    .table-info-vue .description{}
    .table-info-vue .el-table td, .table-info-vue .el-table th{padding: 5px 0;}
</style>

