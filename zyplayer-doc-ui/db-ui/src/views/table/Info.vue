<template>
    <div class="table-info-vue">
        <el-card style="margin: 10px;">
            <div slot="header" class="clearfix">表信息</div>
            <el-row class="status-info-row">
                <el-col :span="24"><span class="label">数据源：</span>{{vueQueryParam.host}}</el-col>
            </el-row>
            <el-row class="status-info-row">
                <el-col :span="6"><span class="label">数据库：</span>{{vueQueryParam.dbName}}</el-col>
                <el-col :span="6"><span class="label">数据表：</span>{{vueQueryParam.tableName}}</el-col>
                <el-col :span="6"><span class="label">引擎：</span>{{tableStatusInfo.engine}}</el-col>
            </el-row>
            <el-row class="status-info-row">
                <el-col :span="6"><span class="label">行数：</span>{{tableStatusInfo.rows||0}}</el-col>
                <el-col :span="6"><span class="label">自动递增：</span>{{tableStatusInfo.avgRowLength||0}}</el-col>
                <el-col :span="6"><span class="label">行格式：</span>{{tableStatusInfo.rowFormat}}</el-col>
                <el-col :span="6"><span class="label">排序规则：</span>{{tableStatusInfo.collation}}</el-col>
            </el-row>
            <el-row class="status-info-row">
                <el-col :span="6"><span class="label">索引长度：</span>{{getBytesSize(tableStatusInfo.indexLength)}}</el-col>
                <el-col :span="6"><span class="label">数据长度：</span>{{getBytesSize(tableStatusInfo.dataLength)}}</el-col>
                <el-col :span="6"><span class="label">最大长度：</span>{{getBytesSize(tableStatusInfo.maxDataLength)}}</el-col>
                <el-col :span="6"><span class="label">可用空间：</span>{{getBytesSize(tableStatusInfo.dataFree)}}</el-col>
            </el-row>
            <el-row class="status-info-row">
                <el-col :span="6"><span class="label">创建时间：</span>{{tableStatusInfo.createTime}}</el-col>
                <el-col :span="6"><span class="label">修改时间：</span>{{tableStatusInfo.updateTime}}</el-col>
                <el-col :span="6"><span class="label">检查时间：</span>{{tableStatusInfo.checkTime}}</el-col>
                <el-col :span="6"><span class="label">创建选项：</span>{{tableStatusInfo.createOptions}}</el-col>
            </el-row>
            <el-row class="status-info-row">
                <el-col :span="24">
                    <span class="label">表注释：</span>
                    <span v-if="tableInfo.inEdit == 1" @keyup.enter="saveTableDescription">
                        <el-input v-model="tableInfo.newDesc" placeholder="输入表注释" v-on:blur="saveTableDescription" style="width: 500px;"></el-input>
                    </span>
                    <span v-else>{{tableInfo.description || '暂无注释'}} <i class="el-icon-edit edit-table-desc" v-on:click="tableInfo.inEdit = 1"></i></span>
                </el-col>
            </el-row>
        </el-card>
        <el-card style="margin: 10px;">
            <div slot="header" class="clearfix">
                字段信息
                <el-tooltip effect="dark" content="点击注释列可编辑字段注释" placement="top">
                    <i class="el-icon-info" style="color: #999;"></i>
                </el-tooltip>
            </div>
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
                        <template slot-scope="scope">{{scope.row.ispramary ? '是' : '否'}}</template>
                    </el-table-column>
                    <el-table-column label="注释">
                        <template slot-scope="scope">
                            <div v-if="scope.row.inEdit == 1" @keyup.enter="saveColumnDescription(scope.row)">
                                <el-input v-model="scope.row.newDesc" placeholder="输入字段注释" v-on:blur="saveColumnDescription(scope.row)"></el-input>
                            </div>
                            <div v-else class="description" v-on:click="descBoxClick(scope.row)">{{scope.row.description||'　'}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>

<script>
    import global from '../../common/config/global'
    var app;

    export default {
        data() {
            return {
                columnListLoading: false,
                vueQueryParam: {},
                tableStatusInfo: {},
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
            // 延迟设置展开的目录，edit比app先初始化
            setTimeout(function () {
                global.vue.$app.initLoadDataList(app.vueQueryParam.sourceId, app.vueQueryParam.host, app.vueQueryParam.dbName);
            }, 500);
        },
        methods: {
            initQueryParam(to) {
                this.columnListLoading = true;
                this.vueQueryParam = to.query;
                this.common.post(this.apilist1.tableColumnList, this.vueQueryParam, function (json) {
                    var columnList = json.data.columnList || [];
                    for (var i = 0; i < columnList.length; i++) {
                        columnList[i].inEdit = 0;
                        columnList[i].newDesc = columnList[i].description;
                    }
                    app.columnList = columnList;
                    var tableInfo = json.data.tableInfo || {};
                    tableInfo.inEdit = 0;
                    tableInfo.newDesc = tableInfo.description;
                    app.tableInfo = tableInfo;
                    app.columnListLoading = false;
                    var newName = {key: app.$route.fullPath, val: tableInfo.tableName};
                    app.$store.commit('global/addTableName', newName);
                    app.$forceUpdate();
                    console.log(newName)
                    // app.$store.state.global.pageTabNameMap
                });
                this.common.post(this.apilist1.tableStatus, this.vueQueryParam, function (json) {
                    app.tableStatusInfo = json.data || {};
                });
            },
            descBoxClick(row) {
                // row.newDesc = row.description;
                row.inEdit = 1;
            },
            getBytesSize(size) {
                if (!size) return "0 bytes";
                var num = 1024.00;
                if (size < num) return size + " bytes";
                if (size < Math.pow(num, 2)) return (size / num).toFixed(2) + "KB";
                if (size < Math.pow(num, 3)) return (size / Math.pow(num, 2)).toFixed(2) + "MB";
                if (size < Math.pow(num, 4)) return (size / Math.pow(num, 3)).toFixed(2) + "GB";
                return (size / Math.pow(num, 4)).toFixed(2) + "TB";
            },
            saveColumnDescription(row) {
                if (row.inEdit == 0 || row.description == row.newDesc) {
                    row.inEdit = 0;
                    return;
                }
                row.inEdit = 0;
                this.vueQueryParam.columnName = row.name;
                this.vueQueryParam.newDesc = row.newDesc;
                this.common.post(this.apilist1.updateTableColumnDesc, this.vueQueryParam, function (json) {
                    row.description = row.newDesc;
                    app.$message.success("修改成功");
                });
            },
            saveTableDescription() {
                if (this.tableInfo.inEdit == 0 || this.tableInfo.description == this.tableInfo.newDesc) {
                    this.tableInfo.inEdit = 0;
                    return;
                }
                this.tableInfo.inEdit = 0;
                this.vueQueryParam.newDesc = this.tableInfo.newDesc;
                this.common.post(this.apilist1.updateTableDesc, this.vueQueryParam, function (json) {
                    app.tableInfo.description = app.tableInfo.newDesc;
                    app.$message.success("修改成功");
                });
            },
        }
    }
</script>
<style>
    .table-info-vue .el-form-item{margin-bottom: 5px;}
    .table-info-vue .edit-table-desc{cursor: pointer; color: #409EFF;}
    .table-info-vue .description{cursor: pointer;}
    .table-info-vue .el-table td, .table-info-vue .el-table th{padding: 5px 0;}
    .table-info-vue .status-info-row{padding: 8px 0;}
    .table-info-vue .status-info-row .label{width: 80px;display: inline-block;text-align: right;color: #606266;}
</style>

