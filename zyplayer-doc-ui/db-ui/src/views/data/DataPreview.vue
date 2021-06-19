<template>
    <div class="data-executor-vue">
        <div style="padding: 0 10px 10px;height: 100%;box-sizing: border-box;">
            <el-card style="margin-bottom: 10px;">
				<ace-editor v-model="sqlExecutorContent" @init="sqlExecutorInit" lang="sql" theme="monokai" width="100%" height="60" :options="sqlEditorConfig" :source="executorSource" style="margin-bottom: 10px;"></ace-editor>
                <div>
                    <el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain  size="small" icon="el-icon-video-pause">取消执行</el-button>
                    <el-tooltip v-else effect="dark" content="Ctrl+R、Ctrl+Enter" placement="top">
                        <el-button v-on:click="doExecutorClick" type="primary" plain  size="small" icon="el-icon-video-play">筛选</el-button>
                    </el-tooltip>
                    <el-button icon="el-icon-refresh-left" size="small" @click="refreshData">重置</el-button>
					<el-button @click="downloadTableData" type="success" size="small" icon="el-icon-download" plain style="margin-left: 30px;">导出</el-button>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">{{executeError}}</div>
                <div v-else-if="sqlExecuting" v-loading="sqlExecuting" style="padding: 20px 0;">数据加载中...</div>
                <div v-else-if="executeResultList.length <= 0" v-loading="sqlExecuting" style="padding: 20px 0;">暂无数据</div>
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
							<div v-else-if="resultItem.dataList.length <= 0" style="text-align: center; color: #aaa; padding: 20px 0;">暂无数据</div>
							<template v-else>
								<el-table :data="resultItem.dataList" stripe border style="width: 100%; margin-bottom: 5px;" class="execute-result-table" :max-height="tableMaxHeight"
										  @selection-change="handleSelectionChange"
										  @sort-change="tableSortChange"
										  :default-sort="tableSort">
									<el-table-column type="selection" width="55"></el-table-column>
									<el-table-column type="index" width="50"></el-table-column>
									<el-table-column sortable v-for="item in resultItem.dataCols" :prop="item.prop" :label="item.prop" :width="item.width">
										<template slot-scope="scope">
											<textarea readonly :value="scope.row[item.prop]" class="el-textarea__inner" rows="1"></textarea>
										</template>
									</el-table-column>
								</el-table>
								<el-pagination
									style="margin-top: 10px;"
									@size-change="handlePageSizeChange"
									@current-change="handleCurrentChange"
									:current-page="currentPage"
									:page-sizes="[50, 100, 300, 500]"
									:page-size="pageSize"
									layout="total, sizes, prev, pager, next, jumper"
									:total="tableTotalCount">
								</el-pagination>
							</template>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-card>
        </div>
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
		<!--导出选择弹窗-->
		<el-dialog :visible.sync="downloadDataVisible" width="600px" title="表数据导出">
			<el-form label-width="120px">
				<el-form-item label="导出类型：">
					<el-select v-model="downloadDataParam.downloadType" filterable placeholder="请选择导出类型" style="width: 370px;">
						<el-option label="SQL Inserts" value="insert"></el-option>
						<el-option label="SQL Updates" value="update"></el-option>
						<el-option label="JSON" value="json"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="数据表：" v-if="downloadDataParam.downloadType === 'insert'">
					<el-checkbox :true-label="1" :false-label="0" v-model="downloadDataParam.dropTableFlag" @change="dropTableFlagChange">删除表{{downloadDataParam.dropTableFlag==1?'!!':''}}</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="downloadDataParam.createTableFlag" @change="createTableFlagChange">创建表</el-checkbox>
				</el-form-item>
				<el-form-item label="保留的列：">
					<el-select v-model="downloadDataParam.retainColumnArr" multiple placeholder="不选则保留全部列" style="width: 370px;">
						<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.prop" :value="item.prop"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="更新条件列：" v-if="downloadDataParam.downloadType === 'update'">
					<el-select v-model="downloadDataParam.conditionArr" multiple placeholder="不选则是没有条件的更新" style="width: 370px;">
						<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.prop" :value="item.prop"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="exportConditionVisible = false">取 消</el-button>
				<el-button type="primary" @click="doDownloadTableData">确 定</el-button>
			</span>
		</el-dialog>
		<form method="post" ref="downloadForm" :action="downloadFormParam.url" target="_blank">
			<input type="hidden" :name="key" :value="val" v-for="(val,key) in downloadFormParam.param">
		</form>
        <span id="widthCalculate" style="visibility: hidden; white-space: nowrap;position: fixed;"></span>
    </div>
</template>

<script>
    import datasourceApi from '../../common/api/datasource'
	import copyFormatter from './copy/index'
    import sqlFormatter from "sql-formatter";
	import aceEditor from "../../common/lib/ace-editor";

    export default {
    	name: 'dataPreview',
        data() {
            return {
                sqlExecuting: false,
                executeResultList: [],
                executeResultInfo: "",
                executeShowTable: "table1",
                sqlExecutorEditor: {},
                nowExecutorId: 1,
                executeError: "",
				pageParam: {},
                vueQueryParam: {},
                pageSize: 50,
                currentPage: 1,
                tableTotalCount: 0,
                tableSort: {},
                tableMaxHeight: 600,
				tableStatusInfo: {},
				// 选择复制
				choiceResultObj: {},
				exportConditionVisible: false,
				conditionDataCols: [],
				conditionDataColsChoice: [],
				// 导出
				downloadDataVisible: false,
				downloadDataParam: {
					downloadType: 'insert',
					dropTableFlag: 0,
					createTableFlag: 0,
					conditionArr: [],
					retainColumnArr: [],
				},
				downloadFormParam: {
					url: 'zyplayer-doc-db/data-view/download',
					param: {}
				},
				// 编辑器
				sqlExecutorContent: '',
				sqlEditorConfig: {
					wrap: true,
					autoScrollEditorIntoView: true,
					enableBasicAutocompletion: true,
					enableSnippets: true,
					enableLiveAutocompletion: true,
					minLines: 3,
					maxLines: 3,
				},
				executorSource: {}
            }
        },
		components: {
			'ace-editor': aceEditor
		},
        mounted: function () {
			// 延迟设置展开的目录，edit比app先初始化
			// setTimeout(() => {
			// 	this.doExecutorSqlCommon();
				// this.$emit('initLoadDataList', {
				// 	sourceId: this.vueQueryParam.sourceId,
				// 	host: this.vueQueryParam.host,
				// 	dbName: this.vueQueryParam.dbName
				// });
			// }, 500);
        },
        methods: {
			init(param) {
				if (this.pageParam.sourceId == param.sourceId) {
					return;
				}
				this.pageParam = param;
				this.executorSource = {sourceId: param.sourceId, dbName: param.dbName, tableName: param.tableName};
				this.doExecutorSqlCommon();
                // this.vueQueryParam = to.query;
                // let newName = {key: this.$route.fullPath, val: '数据-'+this.vueQueryParam.tableName};
                // this.$store.commit('global/addTableName', newName);
				// datasourceApi.tableStatus(this.pageParam).then(json => {
				// 	this.tableStatusInfo = json.data || {};
				// });
            },
			sqlExecutorInit(editor) {
				this.sqlExecutorEditor = editor;
				this.sqlExecutorEditor.setFontSize(16);
				let that = this;
				this.sqlExecutorEditor.commands.addCommand({
					name: "execute-sql",
					bindKey: {win: "Ctrl-R|Ctrl-Shift-R|Ctrl-Enter", mac: "Command-R|Command-Shift-R|Command-Enter"},
					exec: function (editor) {
						that.doExecutorClick();
					}
				});
			},
            handleCurrentChange(to) {
                this.currentPage = to;
                this.doExecutorSqlCommon();
            },
            handlePageSizeChange(to) {
                this.pageSize = to;
				this.currentPage = 1;
                this.doExecutorSqlCommon();
            },
            tableSortChange(sort) {
                if (this.tableSort.prop === sort.prop && this.tableSort.order === sort.order) return;
                this.tableSort = {prop: sort.prop, order: sort.order};
                this.doExecutorSqlCommon();
            },
            refreshData() {
                this.tableSort = {};
                this.currentPage = 1;
				this.sqlExecutorEditor.setValue('', 1);
                this.doExecutorSqlCommon();
            },
            cancelExecutorSql() {
                datasourceApi.executeSqlCancel({executeId: this.nowExecutorId}).then(() => {
					this.sqlExecuting = false;
                    this.$message.success("取消成功");
                });
            },
            doExecutorClick() {
				this.tableSort = {};
				this.currentPage = 1;
                this.doExecutorSqlCommon();
            },
            doExecutorSqlCommon() {
                if (!this.pageParam.sourceId) {
                    this.$message.error("请先选择数据源");
                    return;
				}
				if (!this.tableSort.prop) {
					this.tableSort = {prop: this.pageParam.orderColumn, order: 'ascending'};
				}
				let conditionSql = this.sqlExecutorEditor.getSelectedText();
				conditionSql = conditionSql || this.sqlExecutorEditor.getValue();
				conditionSql = conditionSql || "";
				this.executeError = "";
				this.executeUseTime = "";
				this.executeResultList = [];
				this.tableMaxHeight = document.body.clientHeight - 420;
				this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
				this.sqlExecuting = true;
				let param = {
					sourceId: this.pageParam.sourceId,
					dbName: this.pageParam.dbName,
					tableName: this.pageParam.tableName,
					executeId: this.nowExecutorId,
					condition: conditionSql,
					pageNum: this.currentPage,
					pageSize: this.pageSize,
					orderColumn: this.tableSort.prop,
					orderType: (this.tableSort.order === 'ascending' ? 'asc' : 'desc'),
					params: '',
				};
                datasourceApi.dataViewQuery(param).then(json => {
					this.sqlExecuting = false;
                    if (json.errCode !== 200) {
                        this.executeError = json.errMsg;
                        return;
                    }
                    let resultList = json.data || [];
                    let executeResultList = [];
                    let executeResultInfo = "", itemIndex = 1;
                    for (let i = 0; i < resultList.length; i++) {
                        let objItem = JSON.parse(resultList[i]);
                        executeResultInfo += this.getExecuteInfoStr(objItem);
                        let resultItem = this.dealExecuteResult(objItem);
                        if (resultItem.updateCount < 0) {
                            resultItem.index = itemIndex;
							resultItem.name = 'table' + itemIndex;
                            itemIndex++;
                        }
                        executeResultList.push(resultItem);
					}
					if (!!json.total) {
						this.tableTotalCount = json.total || 0;
					}
					this.executeShowTable = (itemIndex === 1) ? "table0" : "table1";
					this.executeResultInfo = executeResultInfo;
					this.executeResultList = executeResultList;
                }).catch(e => {
					this.sqlExecuting = false;
				});
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
                        executeResultCols.push({prop: key, width: width + 50});
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
			handleSelectionChange(val) {
				this.$set(this.choiceResultObj, this.executeShowTable, val);
			},
			doCopyCheckLineUpdate() {
				let choiceData = this.choiceResultObj[this.executeShowTable] || [];
				if (choiceData.length > 0) {
					let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
					let tableName = '`' + this.pageParam.dbName + '`.`' + this.pageParam.tableName + '`';
					let copyData = copyFormatter.format('update', this.editorDbProduct, dataCols, choiceData, this.conditionDataColsChoice, tableName);
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
					let tableName = '`' + this.pageParam.dbName + '`.`' + this.pageParam.tableName + '`';
					let copyData = copyFormatter.format(type, this.editorDbProduct, dataCols, choiceData, '', tableName);
					this.$copyText(copyData).then(
							res => this.$message.success("内容已复制到剪切板！"),
							err => this.$message.error("抱歉，复制失败！")
					);
				}
			},
			doDownloadTableData() {
				let conditionSql = this.sqlExecutorEditor.getSelectedText();
				conditionSql = conditionSql || this.sqlExecutorEditor.getValue();
				conditionSql = conditionSql || "";
				this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
				this.downloadFormParam.param = {
					sourceId: this.pageParam.sourceId,
					dbName: this.pageParam.dbName,
					tableName: this.pageParam.tableName,
					downloadType: this.downloadDataParam.downloadType,
					conditionColumn: this.downloadDataParam.conditionArr.join(","),
					retainColumn: this.downloadDataParam.retainColumnArr.join(","),
					dropTableFlag: this.downloadDataParam.dropTableFlag,
					createTableFlag: this.downloadDataParam.createTableFlag,
					condition: conditionSql,
					executeId: this.nowExecutorId,
				};
				setTimeout(() => this.$refs.downloadForm.submit(), 0);
				this.downloadDataVisible = false;
			},
			downloadTableData() {
				this.downloadDataParam.conditionArr = [];
				this.conditionDataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
				this.downloadDataVisible = true;
			},
			dropTableFlagChange() {
				if (this.downloadDataParam.dropTableFlag === 1) {
					this.downloadDataParam.createTableFlag = 1;
				}
			},
			createTableFlagChange() {
				if (this.downloadDataParam.createTableFlag == 0) {
					this.downloadDataParam.dropTableFlag = 0;
				}
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
