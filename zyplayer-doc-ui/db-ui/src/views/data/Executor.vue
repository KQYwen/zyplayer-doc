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
                        <span v-if="!!executeUpdateCount" class="execute-use-time">影响行数：{{executeUpdateCount}}</span>
                        <span v-if="!!executeUseTime" class="execute-use-time">查询时间：{{executeUseTime/1000}}s</span>
                        <el-button v-on:click="addFavorite('')" plain  size="small" icon="el-icon-star-off">收藏</el-button>
                        <el-button v-on:click="loadHistoryAndFavoriteList" plain  size="small" icon="el-icon-tickets">收藏及历史</el-button>
                    </div>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">{{executeError}}</div>
                <div v-else-if="executeResultList.length <= 0" v-loading="sqlExecuting">暂无数据</div>
                <div v-else>
                    <el-tabs value="table1">
                        <el-tab-pane label="信息" name="table0">
                            <pre type="textarea" :rows="10" readonly v1-model="">{{executeResultInfo}}</pre>
                        </el-tab-pane>
                        <el-tab-pane :label="'结果'+(index+1)" :name="'table'+(index+1)" v-for="(resultItem, index) in executeResultList">
                            <div v-if="!!resultItem.errMsg" style="color: #f00;">{{resultItem.errMsg}}</div>
                            <el-table v-else :data="resultItem.dataList" stripe border style="width: 100%; margin-bottom: 5px;" class="execute-result-table" max-height="600">
                                <el-table-column width="60px" v-if="resultItem.dataCols.length > 0">
                                    <template slot-scope="scope">{{scope.row._index}}</template>
                                </el-table-column>
                                <el-table-column v-for="item in resultItem.dataCols" :prop="item.prop" :label="item.prop" :width="item.width">
                                    <template slot-scope="scope">
                                        <el-input :value="scope.row[item.prop]" :readonly="true"></el-input>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-card>
        </div>
        <el-drawer title="收藏及历史" :visible.sync="historyDrawerVisible" size="50%" class="data-executor-vue-out">
            <div style="padding: 10px;">
                <el-tabs value="favorite">
                    <el-tab-pane label="我的收藏" name="favorite">
                        <el-table :data="myFavoriteList" stripe border style="width: 100%; margin-bottom: 5px;" v-infinite-scroll>
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
        <span id="widthCalculate" style="visibility: hidden; white-space: nowrap;"></span>
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
                editorDbInfo: [],
                editorDbTableInfo: {},
                editorColumnInfo: {},

                sqlExecuting: false,
                executeResultList: [],
                executeResultInfo: "",
                executeUseTime: 0,
                executeUpdateCount: 0,
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
            this.loadDatasourceList();
            // 下面两行先后顺序不能改
            this.addEditorCompleter();
            app.sqlExecutorEditor = app.initAceEditor("sqlExecutorEditor", 15);
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
                this.common.post(this.apilist1.historyList, {sourceId: this.choiceDatasourceId}, function (json) {
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
                this.sqlExecutorEditor.setValue(content, 1);
                this.historyDrawerVisible = false;
            },
            doExecutorSql() {
                if (!this.choiceDatasourceId) {
                    app.$message.error("请先选择数据源");
                    return;
                }
                app.executeError = "";
                app.executeUseTime = "";
                app.executeResultList = [];

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
                    var resultList = json.data || [];
                    var executeResultList = [];
                    var executeUpdateCount = 0, executeUseTime = 0;
                    var executeResultInfo = "";
                    for (var i = 0; i < resultList.length; i++) {
                        var objItem = JSON.parse(resultList[i]);
                        executeUpdateCount += (objItem.updateCount || 0);
                        executeUseTime += (objItem.useTime || 0);
                        executeResultInfo += app.getExecuteInfoStr(objItem);
                        var resultItem = app.dealExecuteResult(objItem);
                        executeResultList.push(resultItem);
                    }
                    app.executeResultInfo = executeResultInfo;
                    app.executeUseTime = executeUseTime;
                    app.executeUpdateCount = executeUpdateCount;
                    app.executeResultList = executeResultList;
                });
            },
            loadDatasourceList() {
                this.common.post(this.apilist1.datasourceList, {}, function (json) {
                    app.datasourceList = json.data || [];
                    if (app.datasourceList.length > 0) {
                        app.choiceDatasourceId = app.datasourceList[0].id;
                        app.loadEditorData();
                    }
                });
            },
            loadEditorData() {
                this.common.post(this.apilist1.getEditorData, {sourceId: this.choiceDatasourceId}, function (json) {
                    var data = json.data || {};
                    app.editorDbInfo = data.db || [];
                    app.editorDbTableInfo = data.table || {};
                    app.editorColumnInfo = data.column || {};
                });
            },
            datasourceChangeEvents() {
                this.loadEditorData();
                // this.common.post(this.apilist1.databaseList, {sourceId: this.choiceDatasourceId}, function (json) {
                //     app.databaseList = json.data || [];
                // });
            },
            databaseChangeEvents() {

            },
            getExecuteInfoStr(resultData) {
                var resultStr = resultData.sql;
                resultStr += "\n> " + ((!!resultData.errMsg) ? "ERROR" : "OK");
                resultStr += "\n> " + (resultData.useTime || 0) / 1000 + "s";
                resultStr += "\n\n";
                return resultStr;
            },
            dealExecuteResult(resultData) {
                var dataList = resultData.result || [];
                var executeResultCols = [];
                if (dataList.length > 0) {
                    var propData = dataList[0];
                    for (var key in propData) {
                        // 动态计算宽度~自己想的一个方法，666
                        document.getElementById("widthCalculate").innerText = key;
                        var width1 = document.getElementById("widthCalculate").offsetWidth;
                        document.getElementById("widthCalculate").innerText = propData[key];
                        var width2 = document.getElementById("widthCalculate").offsetWidth;
                        var width = (width1 > width2) ? width1 : width2;
                        width = (width < 60) ? 60 : width;
                        width = (width > 200) ? 200 : width;
                        executeResultCols.push({prop: key, width: width + 20});
                    }
                    for (var i = 0; i < dataList.length; i++) {
                        dataList[i]._index = i + 1;
                    }
                }
                var resultObj = {};
                resultObj.dataList = dataList;
                resultObj.dataCols = executeResultCols;
                resultObj.useTime = resultData.useTime || 0;
                resultObj.errMsg = resultData.errMsg || "";
                resultObj.updateCount = resultData.updateCount || 0;
                return resultObj;
            },
            addEditorCompleter() {
                var languageTools = ace.require("ace/ext/language_tools");
                languageTools.addCompleter({
                    needDestory: true, // 一定得加上这个参数~不然页面生命周期内页面的切换，编辑器会有多个相同的completer
                    getCompletions: function (editor, session, pos, prefix, callback) {
                        var isFound = false;
                        var callbackArr = [];
                        var lineStr = session.getLine(pos.row).substring(0, pos.column - 1);
                        if (lineStr.endsWith("from ") || lineStr.endsWith("join ")) {
                            // 库
                            for (var i = 0; i < app.editorDbInfo.length; i++) {
                                callbackArr.push({caption: app.editorDbInfo[i].dbName, snippet: app.editorDbInfo[i].dbName, meta: "database", type: "snippet", score : 1000});
                            }
                            // 所有表
                            for (var key in app.editorDbTableInfo) {
                                var tableInfo = app.editorDbTableInfo[key];
                                for (var i = 0; i < tableInfo.length; i++) {
                                    var caption = (!!tableInfo[i].tableComment) ? tableInfo[i].tableName + "-" + tableInfo[i].tableComment : tableInfo[i].tableName;
                                    callbackArr.push({caption: caption, snippet: tableInfo[i].tableName, meta: "table", type: "snippet", score : 1000});
                                }
                            }
                            callback(null,  callbackArr);
                        } else if (lineStr.endsWith(".")) {
                            // 匹配 库名. 搜索表名
                            for (var i = 0; i < app.editorDbInfo.length; i++) {
                                if (lineStr.endsWith(app.editorDbInfo[i].dbName + ".")) {
                                    var tableInfo = app.editorDbTableInfo[app.editorDbInfo[i].dbName];
                                    if (!!tableInfo) {
                                        for (var j = 0; j < tableInfo.length; j++) {
                                            var caption = (!!tableInfo[j].tableComment) ? tableInfo[j].tableName + "-" + tableInfo[j].tableComment : tableInfo[j].tableName;
                                            callbackArr.push({caption: caption, snippet: tableInfo[j].tableName, meta: "table", type: "snippet", score : 1000});
                                        }
                                        isFound = true;
                                    }
                                }
                            }
                            // 未找到，匹配 表名. 搜索字段名
                            if (!isFound) {
                                for (var key in app.editorColumnInfo) {
                                    if (!lineStr.endsWith(key + ".")) {
                                        continue;
                                    }
                                    var columnInfo = app.editorColumnInfo[key];
                                    if (!!columnInfo) {
                                        for (var i = 0; i < columnInfo.length; i++) {
                                            var caption = (!!columnInfo[i].description) ? columnInfo[i].name + "-" + columnInfo[i].description : columnInfo[i].name;
                                            callbackArr.push({caption: caption, snippet: columnInfo[i].name, meta: "column", type: "snippet", score : 1000});
                                        }
                                        isFound = true;
                                    }
                                }
                            }
                            callback(null,  callbackArr);
                        } else if (lineStr.endsWith("select ") || lineStr.endsWith("where ") || lineStr.endsWith("and ")) {
                            var queryText = "";
                            // 往前加
                            for (var i = pos.row; i >= 0; i--) {
                                var tempLine = session.getLine(i);
                                queryText = tempLine + " " + queryText;
                                if (tempLine.indexOf(";") >= 0) {
                                    break;
                                }
                            }
                            // 往后加
                            for (var i = pos.row + 1; i < session.getLength(); i++) {
                                var tempLine = session.getLine(i);
                                queryText = queryText + " " + tempLine;
                                if (tempLine.indexOf(";") >= 0) {
                                    break;
                                }
                            }
                            // 所有表，找下面的字段列表
                            for (var key in app.editorDbTableInfo) {
                                var tableInfo = app.editorDbTableInfo[key];
                                for (var i = 0; i < tableInfo.length; i++) {
                                    if (queryText.indexOf(tableInfo[i].tableName) < 0) {
                                        continue;
                                    }
                                    var columnInfo = app.editorColumnInfo[tableInfo[i].tableName];
                                    if (!!columnInfo) {
                                        for (var j = 0; j < columnInfo.length; j++) {
                                            var caption = (!!columnInfo[j].description) ? columnInfo[j].name + "-" + columnInfo[j].description : columnInfo[j].name;
                                            callbackArr.push({caption: caption, snippet: columnInfo[j].name, meta: "column", type: "snippet", score : 1000});
                                        }
                                        isFound = true;
                                    }
                                }
                            }
                            callback(null,  callbackArr);
                        }
                    }
                });
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
    .data-executor-vue .execute-result-table .el-input__inner{
        height: 25px;
        line-height: 25px;
        padding: 0 5px;
    }
    .data-executor-vue .execute-use-time{
        font-size: 12px;margin-right: 10px;
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
