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
                    <el-button v-on:click="doExecutorSql" type="primary" plain  size="small" icon="el-icon-video-play">执行</el-button>
                    <div style="float: right;">
                        <el-button v-on:click="" plain  size="small" icon="el-icon-star-off">收藏</el-button>
                        <el-button v-on:click="" plain  size="small" icon="el-icon-tickets">历史记录</el-button>
                    </div>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">
                    {{executeError}}
                </div>
                <div v-else>
                    <el-table :data="executeResultList" stripe border style="width: 100%; margin-bottom: 5px;">
                        <el-table-column v-for="item in executeResultCols" :prop="item.prop" :label="item.prop"></el-table-column>
                    </el-table>
                </div>
            </el-card>
        </div>
        <el-drawer title="我是外面的 Drawer" :visible.sync="historyDrawerVisible" size="50%">
            <div>
                <el-button @click="">打开里面的!</el-button>
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

                executeResultList: [],
                executeResultCols: [],
                sqlExecutorEditor: {},
                nowExecutorId: 1,
                executeError: "",

                historyDrawerVisible: false,
            }
        },
        mounted: function () {
            app = this;
            window.tableMetaInfo = "helloWorld";
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
            doExecutorSql() {
                if (!this.choiceDatasourceId) {
                    app.$message.error("请先选择数据源");
                    return;
                }
                app.executeError = "";
                app.executeResultList = [];
                app.executeResultCols = [];

                this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
                var sql = this.sqlExecutorEditor.getValue();
                this.common.postNonCheck(this.apilist1.executeSql, {
                    sourceId: this.choiceDatasourceId,
                    executeId: this.nowExecutorId,
                    sql: sql,
                    params: '',
                }, function (json) {
                    if (json.errCode != 200) {
                        app.executeError = json.errMsg;
                        return;
                    }
                    var resultData = JSON.parse(json.data) || {};
                    var dataList = resultData.result || [];
                    app.executeResultList = dataList;
                    var executeResultCols = [];
                    if (dataList.length > 0) {
                        for (var key in dataList[0]) {
                            executeResultCols.push({prop: key});
                        }
                    }
                    app.executeResultCols = executeResultCols;
                });
            },
            loadDatasourceList() {
                this.common.post(this.apilist1.datasourceList, {}, function (json) {
                    app.datasourceList = json.data || [];
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
</style>
