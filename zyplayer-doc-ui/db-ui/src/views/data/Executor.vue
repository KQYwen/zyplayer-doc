<template>
    <div class="data-executor-vue">
        <div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
            <el-card style="margin-bottom: 10px;">
                <div>
                    <el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" filterable placeholder="请选择数据源" style="width: 400px;">
                        <el-option v-for="item in datasourceList" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
                    </el-select>
<!--                    <el-select v-model="choiceDatabase" @change="databaseChangeEvents" filterable placeholder="请选择数据库">-->
<!--                        <el-option v-for="item in databaseList" :key="item.dbName" :label="item.dbName" :value="item.dbName"></el-option>-->
<!--                    </el-select>-->
                </div>
                <pre id="sqlExecutorEditor" style="width: 100%;height: 500px;"></pre>
                <div>
                    <el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain  size="small" icon="el-icon-video-pause">取消执行</el-button>
                    <el-button v-else v-on:click="doExecutorSql" type="primary" plain  size="small" icon="el-icon-video-play">执行</el-button>
                    <div style="float: right;">
                        <el-button v-on:click="addFavorite('')" plain  size="small" icon="el-icon-star-off">收藏</el-button>
                        <el-button v-on:click="loadHistoryAndFavoriteList" plain  size="small" icon="el-icon-tickets">收藏及历史</el-button>
                    </div>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">
                    {{executeError}}
                </div>
                <div v-else>
                    <el-table :data="executeResultList" stripe border style="width: 100%; margin-bottom: 5px;" v-loading="sqlExecuting">
                        <el-table-column width="60px" v-if="executeResultCols.length > 0">
                            <template slot-scope="scope">{{scope.row._index}}</template>
                        </el-table-column>
                        <el-table-column v-for="item in executeResultCols" :prop="item.prop" :label="item.prop"></el-table-column>
                    </el-table>
                </div>
            </el-card>
        </div>
        <el-drawer title="收藏及历史" :visible.sync="historyDrawerVisible" size="50%" class="data-executor-vue-out">
            <div style="padding: 10px;">
                <el-tabs value="favorite">
                    <el-tab-pane label="我的收藏" name="favorite">
                        <el-table :data="myFavoriteList" stripe border style="width: 100%; margin-bottom: 5px;">
<!--                            <el-table-column prop="name" label="标题"></el-table-column>-->
                            <el-table-column prop="content" label="SQL"></el-table-column>
<!--                            <el-table-column prop="createTime" label="收藏时间" width="160px"></el-table-column>-->
                            <el-table-column label="操作" width="150px">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="primary" v-on:click="inputFavoriteSql(scope.row.content)">输入</el-button>
                                    <el-button size="mini" type="danger" v-on:click="delFavorite(scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="历史记录" name="history">
                        <el-table :data="myHistoryListList" stripe border style="width: 100%; margin-bottom: 5px;">
                            <el-table-column prop="content" label="SQL"></el-table-column>
                            <el-table-column label="操作" width="150px">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="primary" v-on:click="inputFavoriteSql(scope.row.content)">输入</el-button>
                                    <el-button size="mini" type="success" v-on:click="addFavorite(scope.row.content)">收藏</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-drawer>
    </div>
</template>

<script>
    import '../../common/lib/ace/ace'
    import '../../common/lib/ace/theme-monokai'
    import '../../common/lib/ace/mode-sql'
    import '../../common/lib/ace/ext-language_tools'
    import '../../common/lib/ace/snippets/sql'
    var app;
    export default {
        data() {
            return {
                datasourceList: [],
                choiceDatasourceId: "",
                databaseList: [],
                choiceDatabase: "",

                sqlExecuting: false,
                executeResultList: [],
                executeResultCols: [],
                sqlExecutorEditor: {},
                nowExecutorId: 1,
                executeError: "",
                // 收藏及历史
                historyDrawerVisible: false,
                myFavoriteList: [],
                myHistoryListList: [],
            }
        },
        mounted: function () {
            app = this;
            window.tableMetaInfo = "";
            app.sqlExecutorEditor = app.initAceEditor("sqlExecutorEditor", 20);
            this.loadDatasourceList();
        },
        methods: {
            initAceEditor(editor, minLines) {
                return ace.edit(editor, {
                    theme: "ace/theme/monokai",
                    mode: "ace/mode/sql",
                    wrap: true,
                    autoScrollEditorIntoView: true,
                    enableBasicAutocompletion: true,
                    enableSnippets: true,
                    enableLiveAutocompletion: true,
                    minLines: minLines,
                    maxLines: 50,
                });
            },
            cancelExecutorSql() {
                this.common.post(this.apilist1.executeSqlCancel, {executeId: this.nowExecutorId}, function (json) {
                    app.$message.success("取消成功");
                });
            },
            loadHistoryAndFavoriteList() {
                this.historyDrawerVisible = true;
                this.loadHistoryList();
                this.loadFavoriteList();
            },
            loadFavoriteList() {
                this.common.post(this.apilist1.favoriteList, {}, function (json) {
                    app.myFavoriteList = json.data || [];
                });
            },
            loadHistoryList() {
                this.common.post(this.apilist1.historyList, {}, function (json) {
                    app.myHistoryListList = json.data || [];
                });
            },
            addFavorite(sqlValue) {
                if (!sqlValue) {
                    sqlValue = this.sqlExecutorEditor.getSelectedText();
                    if (!sqlValue) {
                        sqlValue = this.sqlExecutorEditor.getValue();
                    }
                }
                var param = {name: '我的收藏', content: sqlValue};
                this.common.post(this.apilist1.updateFavorite, param, function (json) {
                    app.$message.success("收藏成功");
                    app.loadFavoriteList();
                });
            },
            delFavorite(row) {
                var param = {id: row.id, yn: 0};
                this.common.post(this.apilist1.updateFavorite, param, function (json) {
                    app.$message.success("删除成功");
                    app.loadFavoriteList();
                });
            },
            inputFavoriteSql(content) {
                this.sqlExecutorEditor.setValue(content);
                this.historyDrawerVisible = false;
            },
            doExecutorSql() {
                if (!this.choiceDatasourceId) {
                    app.$message.error("请先选择数据源");
                    return;
                }
                app.executeError = "";
                app.executeResultList = [];
                app.executeResultCols = [];

                this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
                var sqlValue = this.sqlExecutorEditor.getSelectedText();
                if (!sqlValue) {
                    sqlValue = this.sqlExecutorEditor.getValue();
                }
                this.sqlExecuting = true;
                this.common.postNonCheck(this.apilist1.executeSql, {
                    sourceId: this.choiceDatasourceId,
                    executeId: this.nowExecutorId,
                    sql: sqlValue,
                    params: '',
                }, function (json) {
                    app.sqlExecuting = false;
                    if (json.errCode != 200) {
                        app.executeError = json.errMsg;
                        return;
                    }
                    var resultData = JSON.parse(json.data) || {};
                    var dataList = resultData.result || [];
                    var executeResultCols = [];
                    if (dataList.length > 0) {
                        for (var key in dataList[0]) {
                            executeResultCols.push({prop: key});
                        }
                        for (var i = 0; i < dataList.length; i++) {
                            dataList[i]._index = i + 1;
                        }
                    }
                    app.executeResultList = dataList;
                    app.executeResultCols = executeResultCols;
                });
            },
            loadDatasourceList() {
                this.common.post(this.apilist1.datasourceList, {}, function (json) {
                    app.datasourceList = json.data || [];
                    if (app.datasourceList.length > 0) {
                        app.choiceDatasourceId = app.datasourceList[0].id;
                    }
                });
            },
            datasourceChangeEvents() {
                // this.common.post(this.apilist1.databaseList, {sourceId: this.choiceDatasourceId}, function (json) {
                //     app.databaseList = json.data || [];
                // });
            },
            databaseChangeEvents() {

            },
        }
    }
</script>

<style>
    .data-executor-vue .ace-monokai .ace_print-margin{
        display: none;
    }
    .data-executor-vue .el-card__body{
        padding: 10px;
    }
    .data-executor-vue .el-table td, .el-table th{
        padding: 6px 0;
    }
    .data-executor-vue-out .el-tabs__nav-scroll{
        padding-left: 20px;
    }
    .data-executor-vue-out .el-button+.el-button{
        margin-left: 0px;
    }
    .data-executor-vue-out .el-table__body-wrapper{
        height: calc(100vh - 180px);
        overflow-y: auto;
    }
</style>
