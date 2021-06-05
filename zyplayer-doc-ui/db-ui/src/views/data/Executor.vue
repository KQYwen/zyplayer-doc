<template>
    <div class="data-executor-vue">
        <div style="padding: 0 10px 10px;height: 100%;box-sizing: border-box;">
            <el-card style="margin-bottom: 10px;">
                <pre id="sqlExecutorEditor" style="width: 100%;height: 500px;margin-top: 0;"></pre>
                <div>
                    <el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain  size="small" icon="el-icon-video-pause">取消执行</el-button>
                    <el-tooltip v-else effect="dark" content="Ctrl+R、Ctrl+Enter" placement="top">
                        <el-button v-on:click="doExecutorSql" type="primary" plain  size="small" icon="el-icon-video-play">执行</el-button>
                    </el-tooltip>
                    <el-button icon="el-icon-brush" size="small" @click="formatterSql">SQL美化</el-button>
                    <el-button v-on:click="addFavorite('')" plain  size="small" icon="el-icon-star-off">收藏</el-button>
                    <el-button v-on:click="loadHistoryAndFavoriteList" plain  size="small" icon="el-icon-tickets">收藏及历史</el-button>
                    <div style="float: right;">
						<el-select v-model="choiceDatasourceGroup" @change="sourceGroupChangeEvents" size="small" filterable placeholder="请先选择分组" style="width: 200px;">
							<el-option value="" label="全部分组"></el-option>
							<el-option v-for="item in datasourceGroupList" :key="item" :value="item"></el-option>
						</el-select>
                        <el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" size="small" filterable placeholder="请选择数据源" style="width: 300px;margin-left: 10px;">
                            <el-option v-for="item in datasourceOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                    </div>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">{{executeError}}</div>
                <div v-else-if="executeResultList.length <= 0" v-loading="sqlExecuting">暂无数据</div>
                <div v-else style="position: relative;">
					<div style="position: absolute;right: 0;z-index: 1;">
						<!-- 复制选中行 -->
						<el-dropdown @command="handleCopyCheckLineCommand" v-show="this.choiceResultObj[this.executeShowTable] && this.choiceResultObj[this.executeShowTable].length > 0">
							<el-button type="primary" size="small" icon="el-icon-document-copy">
								复制选中行<i class="el-icon-arrow-down el-icon--right"></i>
							</el-button>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item command="insert">SQL Inserts</el-dropdown-item>
								<el-dropdown-item command="update">SQL Updates</el-dropdown-item>
								<el-dropdown-item command="json">JSON</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</div>
                    <el-tabs v-model="executeShowTable">
                        <el-tab-pane label="信息" name="table0">
                            <pre>{{executeResultInfo}}</pre>
                        </el-tab-pane>
                        <el-tab-pane :label="'结果'+resultItem.index" :name="resultItem.name" v-for="resultItem in executeResultList" v-if="!!resultItem.index">
                            <div v-if="!!resultItem.errMsg" style="color: #f00;">{{resultItem.errMsg}}</div>
                            <el-table v-else :data="resultItem.dataList" stripe border style="width: 100%; margin-bottom: 5px;" class="execute-result-table" max-height="600"
									  @selection-change="handleSelectionChange">
								<el-table-column type="selection" width="55"></el-table-column>
								<el-table-column type="index" width="50"></el-table-column>
                                <el-table-column v-for="item in resultItem.dataCols" :prop="item.prop" :label="item.prop" :width="item.width">
                                    <template slot-scope="scope">
                                        <textarea readonly :value="scope.row[item.prop]" class="el-textarea__inner" rows="1"></textarea>
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
                            <el-table-column prop="content" label="SQL">
                                <template slot-scope="scope">
                                    <pre style="margin: 0;">{{scope.row.content}}</pre>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="160px">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="primary" v-on:click="inputFavoriteSql(scope.row.content)">输入</el-button>
                                    <el-button size="mini" type="danger" v-on:click="delFavorite(scope.row)" style="margin-left: 10px;">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="历史记录" name="history">
                        <el-table :data="myHistoryListList" stripe border style="width: 100%; margin-bottom: 5px;">
                            <el-table-column prop="content" label="SQL">
								<template slot-scope="scope">
									<pre style="margin: 0;">{{scope.row.content}}</pre>
								</template>
							</el-table-column>
                            <el-table-column label="操作" width="160px">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="primary" v-on:click="inputFavoriteSql(scope.row.content)">输入</el-button>
                                    <el-button size="mini" type="success" v-on:click="addFavorite(scope.row.content)" style="margin-left: 10px;">收藏</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-drawer>
		<!--选择导出为update的条件列弹窗-->
		<el-dialog :visible.sync="exportConditionVisible" width="500px" title="选择更新语句条件">
			<div>
				更新条件列：
				<el-select v-model="conditionDataColsChoice" multiple placeholder="请选择" style="width: 370px;">
					<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.prop" :value="item.prop"></el-option>
				</el-select>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="exportConditionVisible = false">取 消</el-button>
				<el-button type="primary" @click="doCopyCheckLineUpdate">确 定</el-button>
			</span>
		</el-dialog>
        <span id="widthCalculate" style="visibility: hidden; white-space: nowrap;position: fixed;"></span>
    </div>
</template>

<script>
    import '../../common/lib/ace/ace'
    import '../../common/lib/ace/theme-monokai'
    import '../../common/lib/ace/mode-sql'
    import '../../common/lib/ace/ext-language_tools'
    import '../../common/lib/ace/snippets/sql'
	import copyFormatter from './copy/index'
    import sqlFormatter from "sql-formatter"
    import datasourceApi from '../../common/api/datasource'

    export default {
        data() {
            return {
                datasourceList: [],
                choiceDatasourceId: "",
				datasourceOptions: [],
				datasourceGroupList: [],
				choiceDatasourceGroup: "",

                databaseList: [],
                choiceDatabase: "",
				editorDbProduct: "",
                editorDbInfo: [],
                editorDbTableInfo: {},
                editorColumnInfo: {},

                sqlExecuting: false,
                executeResultList: [],
                executeResultInfo: "",
                executeShowTable: "table1",
                sqlExecutorEditor: {},
                nowExecutorId: 1,
                executeError: "",
                // 收藏及历史
                historyDrawerVisible: false,
                myFavoriteList: [],
                myHistoryListList: [],
				// 选择复制
				choiceResultObj: {},
				//
				exportConditionVisible: false,
				conditionDataCols: [],
				conditionDataColsChoice: [],
            }
        },
        mounted: function () {
            this.loadDatasourceList();
            // 下面两行先后顺序不能改
            this.addEditorCompleter();
            this.sqlExecutorEditor = this.initAceEditor("sqlExecutorEditor", 15);
            this.sqlExecutorEditor.setFontSize(16);
            let that = this;
            this.sqlExecutorEditor.commands.addCommand({
                name: "execute-sql",
                bindKey: {win: "Ctrl-R|Ctrl-Shift-R|Ctrl-Enter", mac: "Command-R|Command-Shift-R|Command-Enter"},
                exec: function (editor) {
                    that.doExecutorSql();
                }
            });
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
                    maxLines: 40,
                });
            },
            cancelExecutorSql() {
                datasourceApi.executeSqlCancel({executeId: this.nowExecutorId}).then(() => {
                    this.$message.success("取消成功");
                });
            },
            loadHistoryAndFavoriteList() {
                this.historyDrawerVisible = true;
                this.loadHistoryList();
                this.loadFavoriteList();
            },
            loadFavoriteList() {
                datasourceApi.favoriteList({sourceId: this.choiceDatasourceId}).then(json => {
                    this.myFavoriteList = json.data || [];
                });
            },
            loadHistoryList() {
                datasourceApi.historyList({sourceId: this.choiceDatasourceId}).then(json => {
                    this.myHistoryListList = json.data || [];
                });
            },
            addFavorite(sqlValue) {
                if (!sqlValue) {
                    sqlValue = this.sqlExecutorEditor.getSelectedText();
                    if (!sqlValue) {
                        sqlValue = this.sqlExecutorEditor.getValue();
                    }
                }
                let param = {name: '我的收藏', content: sqlValue, datasourceId: this.choiceDatasourceId};
                datasourceApi.updateFavorite(param).then(() => {
                    this.$message.success("收藏成功");
                    this.loadFavoriteList();
                });
            },
            delFavorite(row) {
                datasourceApi.updateFavorite({id: row.id, yn: 0}).then(() => {
                    this.$message.success("删除成功");
                    this.loadFavoriteList();
                });
            },
            inputFavoriteSql(content) {
                this.sqlExecutorEditor.setValue(content, 1);
                this.historyDrawerVisible = false;
            },
            formatterSql() {
                let dataSql = this.sqlExecutorEditor.getSelectedText();
                if (!!dataSql) {
                    let range = this.sqlExecutorEditor.getSelectionRange();
                    this.sqlExecutorEditor.remove(range);
                } else {
                    dataSql = this.sqlExecutorEditor.getValue();
                    this.sqlExecutorEditor.setValue('', 1);
                }
                if (!!dataSql) {
                    dataSql = sqlFormatter.format(dataSql);
                    this.sqlExecutorEditor.insert(dataSql);
                }
            },
            doExecutorSql() {
                if (!this.choiceDatasourceId) {
                    this.$message.error("请先选择数据源");
                    return;
                }
                this.executeError = "";
                this.executeUseTime = "";
                this.executeResultList = [];

                this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
                var sqlValue = this.sqlExecutorEditor.getSelectedText();
                if (!sqlValue) {
                    sqlValue = this.sqlExecutorEditor.getValue();
                }
                this.sqlExecuting = true;
                datasourceApi.queryExecuteSql({
                    sourceId: this.choiceDatasourceId,
                    executeId: this.nowExecutorId,
                    sql: sqlValue,
                    params: '',
                }).then(json => {
                    this.sqlExecuting = false;
                    if (json.errCode != 200) {
                        this.executeError = json.errMsg;
                        return;
                    }
                    var resultList = json.data || [];
                    var executeResultList = [];
                    var executeResultInfo = "", itemIndex = 1;
                    for (var i = 0; i < resultList.length; i++) {
                        var objItem = JSON.parse(resultList[i]);
                        executeResultInfo += this.getExecuteInfoStr(objItem);
                        var resultItem = this.dealExecuteResult(objItem);
                        if (resultItem.updateCount < 0) {
                            resultItem.index = itemIndex;
                            resultItem.name = 'table' + itemIndex;
                            itemIndex++;
                        }
                        executeResultList.push(resultItem);
                    }
                    this.executeShowTable = (itemIndex === 1) ? "table0" : "table1";
                    this.executeResultInfo = executeResultInfo;
                    this.executeResultList = executeResultList;
                });
            },
            loadDatasourceList() {
                datasourceApi.datasourceList({}).then(json => {
                    this.datasourceList = json.data || [];
					this.datasourceOptions = this.datasourceList;
					let datasourceGroupList = [];
					this.datasourceList.filter(item => !!item.groupName).forEach(item => datasourceGroupList.push(item.groupName || ''));
					this.datasourceGroupList = Array.from(new Set(datasourceGroupList));
                    if (this.datasourceList.length > 0) {
                        this.choiceDatasourceId = this.datasourceList[0].id;
                        this.loadEditorData();
                    }
                });
            },
            loadEditorData() {
                datasourceApi.getEditorData({sourceId: this.choiceDatasourceId}).then(json => {
                    let data = json.data || {};
                    this.editorDbInfo = data.db || [];
                    this.editorDbProduct = data.product || '';
                    this.editorDbTableInfo = data.table || {};
                    this.editorColumnInfo = data.column || {};
                });
            },
			sourceGroupChangeEvents() {
				let datasourceOptions = [];
				for (let i = 0; i < this.datasourceList.length; i++) {
					let item = this.datasourceList[i];
					if (!this.choiceDatasourceGroup || this.choiceDatasourceGroup == item.groupName) {
						datasourceOptions.push(item);
					}
				}
				this.datasourceOptions = datasourceOptions;
				if (datasourceOptions.length > 0) {
					this.choiceDatasourceId = datasourceOptions[0].id;
				}
			},
            datasourceChangeEvents() {
                this.loadEditorData();
            },
            databaseChangeEvents() {

            },
            getExecuteInfoStr(resultData) {
                var resultStr = resultData.sql;
                resultStr += "\n> 状态：" + ((!!resultData.errMsg) ? "ERROR" : "OK");
                if (resultData.updateCount >= 0) {
                    resultStr += "\n> 影响行数：" + resultData.updateCount;
                }
                resultStr += "\n> 耗时：" + (resultData.useTime || 0) / 1000 + "s";
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
                        width = (width < 50) ? 50 : width;
                        width = (width > 200) ? 200 : width;
                        executeResultCols.push({prop: key, width: width + 25});
                    }
                }
                var resultObj = {};
                resultObj.dataList = dataList;
                resultObj.dataCols = executeResultCols;
                resultObj.useTime = resultData.useTime || 0;
                resultObj.errMsg = resultData.errMsg || "";
                resultObj.updateCount = resultData.updateCount;
                return resultObj;
            },
            addEditorCompleter() {
                let that = this;
                var languageTools = ace.require("ace/ext/language_tools");
                languageTools.addCompleter({
                    needDestory: true, // 一定得加上这个参数~不然页面生命周期内页面的切换，编辑器会有多个相同的completer
                    getCompletions: function (editor, session, pos, prefix, callback) {
                        var isFound = false;
                        var callbackArr = [];
                        var lineStr = session.getLine(pos.row).substring(0, pos.column - 1);
                        if (lineStr.endsWith("from ") || lineStr.endsWith("join ")) {
                            // 库
                            for (var i = 0; i < that.editorDbInfo.length; i++) {
                                callbackArr.push({caption: that.editorDbInfo[i].dbName, snippet: that.editorDbInfo[i].dbName, meta: "database", type: "snippet", score : 1000});
                            }
                            // 所有表
                            for (var key in that.editorDbTableInfo) {
                                var tableInfo = that.editorDbTableInfo[key];
                                for (var i = 0; i < tableInfo.length; i++) {
                                    var caption = (!!tableInfo[i].tableComment) ? tableInfo[i].tableName + "-" + tableInfo[i].tableComment : tableInfo[i].tableName;
                                    callbackArr.push({caption: caption, snippet: tableInfo[i].tableName, meta: "table", type: "snippet", score : 1000});
                                }
                            }
                            callback(null,  callbackArr);
                        } else if (lineStr.endsWith(".")) {
                            // 匹配 库名. 搜索表名
                            for (var i = 0; i < that.editorDbInfo.length; i++) {
                                if (lineStr.endsWith(that.editorDbInfo[i].dbName + ".")) {
                                    var tableInfo = that.editorDbTableInfo[that.editorDbInfo[i].dbName];
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
                                for (var key in that.editorColumnInfo) {
                                    if (!lineStr.endsWith(key + ".")) {
                                        continue;
                                    }
                                    var columnInfo = that.editorColumnInfo[key];
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
                            for (var key in that.editorDbTableInfo) {
                                var tableInfo = that.editorDbTableInfo[key];
                                for (var i = 0; i < tableInfo.length; i++) {
                                    if (queryText.indexOf(tableInfo[i].tableName) < 0) {
                                        continue;
                                    }
                                    var columnInfo = that.editorColumnInfo[tableInfo[i].tableName];
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
			handleSelectionChange(val) {
            	this.$set(this.choiceResultObj, this.executeShowTable, val);
			},
			doCopyCheckLineUpdate() {
            	let choiceData = this.choiceResultObj[this.executeShowTable] || [];
				if (choiceData.length > 0) {
					let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
					let copyData = copyFormatter.format('update', this.editorDbProduct, dataCols, choiceData, this.conditionDataColsChoice, '`table`');
					this.conditionDataColsChoice = [];
					this.exportConditionVisible = false;
					this.$copyText(copyData).then(
							res => this.$message.success("内容已复制到剪切板！"),
							err => this.$message.error("抱歉，复制失败！")
					);
				}
			},
			handleCopyCheckLineCommand(type) {
            	let choiceData = this.choiceResultObj[this.executeShowTable] || [];
				if (choiceData.length > 0) {
					let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
					if (type === 'update') {
						// 选择更新的条件列
						this.conditionDataCols = dataCols;
						this.exportConditionVisible = true;
						return;
					}
					let copyData = copyFormatter.format(type, this.editorDbProduct, dataCols, choiceData, '', '`table`');
					this.$copyText(copyData).then(
							res => this.$message.success("内容已复制到剪切板！"),
							err => this.$message.error("抱歉，复制失败！")
					);
				}
			}
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
    .data-executor-vue .execute-result-table .el-textarea__inner{
        height: 27px;
        min-height: 27px;
        line-height: 25px;
        padding: 0 5px;
        resize: none;
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
