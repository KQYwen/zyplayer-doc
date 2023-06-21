<template>
	<div class="data-executor-vue">
		<div style="padding: 0 10px 10px;height: 100%;box-sizing: border-box;">
			<el-card style="margin-bottom: 10px;">
				<div v-show="aceEditorShow">
					<el-alert
						title="筛选示例 (支持 and , or 等连接符)"
						type="info"
						:description="executorDesc"
						show-icon>
					</el-alert>
					<ace-editor v-model="sqlExecutorContent" @init="sqlExecutorInit" lang="sql" theme="monokai"
								width="100%"
								height="60" :options="sqlEditorConfig" :source="executorSource"
								style="margin-bottom: 10px;">
					</ace-editor>
				</div>
				<div>
					<el-button v-on:click="doAceEditorShow" type="primary" plain size="small" icon="el-icon-search">筛选
					</el-button>
					<el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain size="small"
							   icon="el-icon-video-pause">取消执行
					</el-button>
					<el-tooltip v-show="aceEditorShow" v-else effect="dark" content="Ctrl+R、Ctrl+Enter" placement="top">
						<el-button v-on:click="doExecutorClick" type="primary" plain size="small"
								   icon="el-icon-video-play">执行
						</el-button>
					</el-tooltip>

					<el-button icon="el-icon-refresh-left" size="small" @click="refreshData">重置</el-button>
					<el-button @click="downloadTableData" type="success" size="small" icon="el-icon-download" plain
							   style="margin-left: 10px;">导出
					</el-button>
				</div>
			</el-card>
			<el-card>
				<div v-if="!!executeError" style="color: #f00;">{{ executeError }}</div>
				<div v-else-if="sqlExecuting" v-loading="sqlExecuting" style="padding: 20px 0;">数据加载中...</div>
				<div v-else-if="executeResultList.length <= 0" v-loading="sqlExecuting" style="padding: 20px 0;">
					暂无数据
				</div>
				<div v-else style="position: relative;">
					<div style="position: absolute;right: 0;z-index: 1;" v-show="executeShowTable !== 'table0'">
						<span
							v-show="choiceResultObj[executeShowTable] && choiceResultObj[executeShowTable].length > 0">
							<el-button icon="el-icon-delete" size="small" @click="deleteCheckLine" type="danger" plain
									   style="margin-right: 10px;">删除</el-button>
							<!-- 复制选中行 -->
							<el-dropdown @command="handleCopyCheckLineCommand">
								<el-button type="primary" size="small" icon="el-icon-document-copy">
									复制选中行<i class="el-icon-arrow-down el-icon--right"></i>
								</el-button>
								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item command="insert">SQL Inserts</el-dropdown-item>
									<el-dropdown-item command="update">SQL Updates</el-dropdown-item>
									<el-dropdown-item command="json">JSON</el-dropdown-item>
								</el-dropdown-menu>
							</el-dropdown>
						</span>
						<el-tooltip effect="dark" content="选择展示列" placement="top">
							<el-button icon="el-icon-setting" size="small" style="margin-left: 10px;"
									   @click="choiceShowColumnDrawerShow"></el-button>
						</el-tooltip>
					</div>
					<el-tabs v-model="executeShowTable">
						<el-tab-pane label="信息" name="table0">
							<pre class="xxpre">{{ executeResultInfo }}</pre>
						</el-tab-pane>
						<el-tab-pane :label="'结果'+resultItem.index" :name="resultItem.name"
									 v-for="(resultItem,index) in executeResultList" :key="index"
									 v-if="!!resultItem.index" lazy>
							<div v-if="!!resultItem.errMsg" style="color: #f00;">{{ resultItem.errMsg }}</div>
							<div v-else-if="resultItem.dataList.length <= 0"
								 style="text-align: center; color: #aaa; padding: 20px 0;">暂无数据
							</div>
							<template v-else>
								<ux-grid
									v-clickoutside="handleClickOutside"
									stripe
									border
									:height="height"
									style="width: 100%; margin-bottom: 5px;" class="execute-result-table"
									:max-height="tableMaxHeight"
									@selection-change="handleSelectionChange"
									@cell-click="mouseOnFocus"
									@sort-change="tableSortChange"
									keep-source
									ref="plxTable"
									:edit-config="{trigger:'click',mode:'cell',activeMethod:activeMethod}"
									@edit-closed="editClosed"
									:checkboxConfig="{checkMethod: selectable, highlight: true}"
									:default-sort="tableSort">
									<ux-table-column type="checkbox" width="50"></ux-table-column>
									<ux-table-column type="index" width="50" title=" "></ux-table-column>
									<ux-table-column v-for="(item,index) in resultItem.dataCols" :key="index"
													 :prop="item.prop" :title="item.prop" :field="item.prop"
													 :resizable="true" edit-render
													 :width="item.width" sortable>
										<template v-slot:header="scope">
											<el-tooltip effect="dark" :content="item.desc" placement="top">
												<span>{{ item.prop }}</span>
											</el-tooltip>
										</template>
										<template v-slot="scope">
											<input title="" v-model="scope.row[item.prop]" class="el-textarea__inner"
													  ></input>
										</template>
										<template v-slot:edit="scope">
											<input title="" v-model="scope.row[item.prop]" class="el-textarea__inner"
											></input>
										</template>
									</ux-table-column>
								</ux-grid>
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
					<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.prop"
							   :value="item.prop"></el-option>
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
					<el-select v-model="downloadDataParam.downloadType" filterable placeholder="请选择导出类型"
							   style="width: 370px;">
						<el-option label="SQL Inserts" value="insert"></el-option>
						<el-option label="SQL Updates" value="update"></el-option>
						<el-option label="JSON" value="json"></el-option>
						<el-option label="Excel" value="excel"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="数据表：" v-if="downloadDataParam.downloadType === 'insert'">
					<el-checkbox :true-label="1" :false-label="0" v-model="downloadDataParam.dropTableFlag"
								 @change="dropTableFlagChange">删除表{{
							downloadDataParam.dropTableFlag == 1 ? '!!' : ''
						}}
					</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="downloadDataParam.createTableFlag"
								 @change="createTableFlagChange">创建表
					</el-checkbox>
				</el-form-item>
				<el-form-item label="更新条件列：" v-if="downloadDataParam.downloadType === 'update'">
					<el-select v-model="downloadDataParam.conditionColumnArr" multiple
							   placeholder="不选则是没有条件的更新"
							   style="width: 370px;">
						<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.prop"
								   :value="item.prop"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="downloadDataVisible = false">取 消</el-button>
				<el-button type="primary" @click="doDownloadTableData">确 定</el-button>
			</span>
		</el-dialog>
		<el-drawer
			size="350px"
			:with-header="false"
			:visible.sync="choiceShowColumnDrawer"
			:before-close="choiceShowColumnDrawerClose"
			direction="rtl">
			<div style="padding: 10px;">
				<el-row>
					<el-col :span="12">选择展示列</el-col>
					<el-col :span="12" style="text-align: right;">
						<el-checkbox v-model="choiceShowColumnAll" @change="choiceShowColumnAllChange">全选
						</el-checkbox>
						<el-button type="primary" size="mini" @click="choiceShowColumnOk" style="margin-left: 10px;">确定
						</el-button>
					</el-col>
				</el-row>
			</div>
			<div style="overflow: auto; height: calc(100vh - 50px);">
				<el-tree ref="showColumnTree" node-key="name" :props="showColumnProps" :data="tableDataColumns"
						 check-on-click-node show-checkbox
						 @check-change="tableDataColumnsCheckChange">
				</el-tree>
			</div>
		</el-drawer>
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
import storageUtil from "../../common/lib/zyplayer/storageUtil";
import Clickoutside from 'element-ui/src/utils/clickoutside'

export default {
	name: 'dataPreview',
	directives: {Clickoutside},
	data() {
		return {
			executorDesc: "",
			aceEditorShow: false,
			height: 0,
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
			tableDataColumns: [],
			conditionDataCols: [],
			conditionDataColsChoice: [],
			// 导出
			downloadDataVisible: false,
			downloadDataParam: {
				downloadType: 'insert',
				dropTableFlag: 0,
				createTableFlag: 0,
				conditionColumnArr: [],
				retainColumnArr: [],
			},
			downloadFormParam: {
				url: 'zyplayer-doc-db/data-view/downloadMultiple',
				param: {}
			},
			// 选择展示列
			choiceShowColumnDrawer: false,
			showColumnProps: {label: 'name'},
			choiceShowColumnLast: [],
			choiceShowColumnAll: true,
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
			executorSource: {},
			columnMap: {},
			primaryKeyColumn: {},
			storageKey: {
				key: 'zyplayer-doc-table-show-columns', subKey: ''
			},
		}
	},
	components: {
		'ace-editor': aceEditor
	},
	mounted: function () {
		this.height = document.body.clientHeight - 330;
		let resizeWindow = () => {
			this.tableMaxHeight = document.body.clientHeight - 420;
		};
		resizeWindow();
		window.onresize = resizeWindow;
	},
	methods: {
		init(param, columnList) {
			if (this.pageParam.sourceId == param.sourceId) {
				return;
			}
			this.pageParam = param;
			this.executorSource = {sourceId: param.sourceId, dbName: param.dbName, tableName: param.tableName};
			this.columnMap = {};
			columnList.forEach(item => {
				this.columnMap[item.name] = item;
				if (item.primaryKey == 1) {
					this.primaryKeyColumn = item;
				}
			});
			this.tableDataColumns = columnList;
			this.executorDesc = columnList[0].name + " = ?";
			// 设置选择展示的列
			this.storageKey.subKey = param.sourceId + '-' + param.dbName + '-' + param.tableName;
			let storageShowColumns = storageUtil.set.get(this.storageKey.key, this.storageKey.subKey);
			this.choiceShowColumnLast = columnList.map(val => val.name);
			if (storageShowColumns) {
				let showColumns = storageShowColumns.split(',');
				showColumns = showColumns.filter(item => this.choiceShowColumnLast.indexOf(item) >= 0);
				if (showColumns.length > 0) {
					this.choiceShowColumnLast = showColumns;
				}
			}
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
			if (this.tableSort.prop === sort.column.title && this.tableSort.order === sort.order) return;
			this.tableSort = {prop: sort.column.title, order: sort.order};
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
		doAceEditorShow() {
			this.aceEditorShow = !this.aceEditorShow
			this.aceEditorShow?this.height = this.height - 125:this.height = this.height + 125;
		},
		doExecutorSqlCommon() {
			if (!this.pageParam.sourceId) {
				this.$message.error("请先选择数据源");
				return;
			}
			if (!this.tableSort.prop) {
				this.tableSort = {prop: this.pageParam.orderColumn, order: 'asc'};
			}
			let conditionSql = this.sqlExecutorEditor.getSelectedText();
			conditionSql = conditionSql || this.sqlExecutorEditor.getValue();
			conditionSql = conditionSql || "";
			this.executeError = "";
			this.executeUseTime = "";
			this.choiceResultObj = {};
			this.executeResultList = [];
			this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
			this.sqlExecuting = true;
			let param = {
				sourceId: this.pageParam.sourceId,
				dbName: this.pageParam.dbName,
				tableName: this.pageParam.tableName,
				executeId: this.nowExecutorId,
				condition: conditionSql,
				retainColumn: this.choiceShowColumnLast.join(','),
				pageNum: this.currentPage,
				pageSize: this.pageSize,
				orderColumn: this.tableSort.prop,
				orderType: this.tableSort.order,
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
				setTimeout (() => {
					for (let i = 0; i < executeResultList.length; i++) {
						this.datas = executeResultList[i].dataList;
						this.$refs.plxTable[i].reloadData(this.datas);
					}
				})

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
					let column = this.columnMap[key] || {};
					if(key.toLowerCase()==='zyplaydbrowid'){
						continue;
					}
					if(key.toLowerCase()==='zyplaydbctid'){
						continue;
					}
					executeResultCols.push({prop: key, width: width + 50, desc: (column.description || key)});
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
		//表格单元格鼠标焦点事件
		mouseOnFocus(row, column, cell, event) {
			if (this.uxGridCell) {
				this.uxGridCell.style.border = 'none'
			}
			if(column.type==='index'||column.type==='checkbox'){
				return;
			}
			cell.style.border = '2px solid #0078d7'
			this.uxGridCell = cell;
		},
		// 点击区域外
		handleClickOutside() {
			if (this.uxGridCell) {
				this.uxGridCell.style.border = 'none'
			}
		},
		//根据返回值用来决定该单元格是否允许编辑
		activeMethod(row){
			return true
		},
		//单元格编辑状态下被关闭时
		editClosed(row){
			//判断是否发生改变
			if(this.$refs.plxTable[0].isUpdateByRow(row.row)){
				let zyplaydbrowid= row.row.ZYPLAYDBROWID;
				let zyplaydbctid = row.row.zyplaydbctid;
				//存在说明为支持伪列的数据库
				if(zyplaydbrowid||zyplaydbctid){
					this.$refs.plxTable[0].reloadRow(row.row, null, null)
					let col = row.column.title;
					let sql = "";
					if(zyplaydbrowid){
						sql = "update \""+this.pageParam.dbName+"\".\""+this.pageParam.tableName+"\" set \""+col+"\" = \'"+row.row[col] +"\' where rowid = \'"+zyplaydbrowid+"\'";
					}else if(zyplaydbctid){
						sql = "update \""+this.pageParam.dbName+"\".\""+this.pageParam.tableName+"\" set \""+col+"\" = \'"+row.row[col] +"\' where ctid = \'"+zyplaydbctid+"\'";
					}
					datasourceApi.queryExecuteSql({
						sourceId: this.pageParam.sourceId,
						dbName: this.pageParam.dbName,
						executeId: this.nowExecutorId,
						sql: sql,
					}).then(response => {
						if(response.errCode!==200){
							this.$message.error(response.errMsg);
							return;
						}
						if(response.data[0].errCode!==0){
							this.$message.error(response.data[0].errMsg);
							return;
						}
						this.$message.success("修改成功");
						this.doExecutorSqlCommon();
					})
					return;
				}
				//不支持伪列数据库
				//@TODO MySQL、SQL Server等数据库，不存在伪列概念或不适合用于条件语句。因此，对于这些数据库中没有主键表的数据，只能进行精确匹配的修改和删除操作
			}
		},
		doCopyCheckLineUpdate() {
			let choiceData = this.choiceResultObj[this.executeShowTable] || [];
			if (choiceData.length > 0) {
				let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
				let copyData = copyFormatter.format('update', this.pageParam.dbType, dataCols, choiceData, this.conditionDataColsChoice, this.pageParam.dbName, this.pageParam.tableName);
				this.conditionDataColsChoice = [];
				this.exportConditionVisible = false;
				this.$copyText(copyData).then(
					res => this.$message.success("内容已复制到剪切板！"),
					err => this.$message.error("抱歉，复制失败！")
				);
			}
		},
		deleteCheckLine() {
			let choiceData = this.choiceResultObj[this.executeShowTable] || [];
			if (choiceData.length > 0) {
				let primaryKey = this.primaryKeyColumn.name;
				if (!primaryKey) {
					this.$message.error("删除失败，未找到数据表的主键列");
					return;
				}
				// 通过主键ID和值删除行的数据
				let deleteParam = [];
				choiceData.forEach(item => {
					let line = {};
					line[primaryKey] = item[primaryKey];
					deleteParam.push(line);
				});
				this.$confirm(`确定要删除选中的${choiceData.length}行数据吗？`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let param = {
						sourceId: this.pageParam.sourceId,
						dbName: this.pageParam.dbName,
						tableName: this.pageParam.tableName,
						lineJson: JSON.stringify(deleteParam)
					};
					datasourceApi.deleteTableLineData(param).then(() => {
						this.$message.success("删除成功！");
						this.refreshData();
					});
				}).catch(() => {
				});
			}
		},
		handleCopyCheckLineCommand(type) {
			let choiceData = this.choiceResultObj[this.executeShowTable] || [];
			if (choiceData.length > 0) {
				this.conditionDataColsChoice = [];
				let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
				if (type === 'update') {
					if (!!this.primaryKeyColumn.name) {
						this.conditionDataColsChoice = [this.primaryKeyColumn.name];
					} else {
						// 没有主键的时候，选择更新的条件列
						this.conditionDataCols = dataCols;
						this.exportConditionVisible = true;
						return;
					}
				}
				let copyData = copyFormatter.format(type, this.pageParam.dbType, dataCols, choiceData, this.conditionDataColsChoice, this.pageParam.dbName, this.pageParam.tableName);
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
			let condition = {}, conditionColumn = {}, retainColumn = {};
			condition[this.pageParam.tableName] = conditionSql;
			conditionColumn[this.pageParam.tableName] = this.downloadDataParam.conditionColumnArr.join(",");
			retainColumn[this.pageParam.tableName] = this.choiceShowColumnLast.join(",");
			this.downloadFormParam.param = {
				executeId: this.nowExecutorId,
				sourceId: this.pageParam.sourceId,
				dbName: this.pageParam.dbName,
				tableNames: this.pageParam.tableName,
				downloadType: this.downloadDataParam.downloadType,
				dropTableFlag: this.downloadDataParam.dropTableFlag,
				createTableFlag: this.downloadDataParam.createTableFlag,
				conditionJson: JSON.stringify(condition),
				conditionColumnJson: JSON.stringify(conditionColumn),
				retainColumnJson: JSON.stringify(retainColumn),
			};
			setTimeout(() => this.$refs.downloadForm.submit(), 0);
			this.downloadDataVisible = false;
		},
		downloadTableData() {
			let dataRes = this.executeResultList.find(item => item.name === this.executeShowTable);
			if (!dataRes || !dataRes.dataList || dataRes.dataList.length <= 0) {
				this.$message.warning("当前筛选条件下无数据，请重新筛选后再操作导出");
				return;
			}
			let primaryKey = this.primaryKeyColumn.name;
			if (this.downloadDataParam.conditionColumnArr.length <= 0 && !!primaryKey) {
				this.downloadDataParam.conditionColumnArr = [primaryKey];
			}
			this.conditionDataCols = dataRes.dataCols;
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
		choiceShowColumnDrawerShow() {
			this.choiceShowColumnDrawer = true;
			setTimeout(() => {
				this.$refs.showColumnTree.setCheckedKeys(this.choiceShowColumnLast);
				this.choiceShowColumnAll = (this.choiceShowColumnLast.length === this.tableDataColumns.length);
			}, 10);
		},
		choiceShowColumnDrawerClose() {
			this.choiceShowColumnDrawer = false;
		},
		choiceShowColumnOk() {
			let checkedKeys = this.$refs.showColumnTree.getCheckedKeys();
			if (checkedKeys.length <= 0) {
				this.$message.warning("必须选择一列展示");
			} else {
				storageUtil.set.save(this.storageKey.key, this.storageKey.subKey, checkedKeys.join(','), 50);
				this.choiceShowColumnLast = checkedKeys;
				this.choiceShowColumnDrawer = false;
				this.doExecutorClick();
			}
		},
		tableDataColumnsCheckChange() {
			let checkedKeys = this.$refs.showColumnTree.getCheckedKeys();
			this.choiceShowColumnAll = (checkedKeys.length === this.tableDataColumns.length);
		},
		choiceShowColumnAllChange() {
			let choiceAll = [];
			if (this.choiceShowColumnAll) {
				choiceAll = this.tableDataColumns.map(val => val.name);
			}
			this.$refs.showColumnTree.setCheckedKeys(choiceAll);
		},
		selectable({row}) {
			return row.id !== 2
		},
	}
}
</script>

<style scoped>
.data-executor-vue .ace-monokai .ace_print-margin {
	display: none;
}

.data-executor-vue .el-card__body {
	padding: 10px;
}

.data-executor-vue .el-table td, .el-table th {
	padding: 6px 0;
}

.data-executor-vue .execute-result-table .el-input__inner {
	height: 25px;
	line-height: 25px;
	padding: 0 5px;
}

.data-executor-vue .execute-result-table .el-textarea__inner {
	height: 26px;
	min-height: 26px;
	line-height: 26px;
	padding: 0;
	resize: none;
	font-size: 12px;
}

.data-executor-vue .execute-use-time {
	font-size: 12px;
	margin-right: 10px;
}

.data-executor-vue-out .el-tabs__nav-scroll {
	padding-left: 20px;
}

.data-executor-vue-out .el-button + .el-button {
	margin-left: 0px;
}

.data-executor-vue-out .el-table__body-wrapper {
	height: calc(100vh - 180px);
	overflow-y: auto;
}

/deep/ .elx-table .elx-body--column.col--ellipsis {
	height: 30px;
}

/deep/ .elx-table .elx-header--column.col--ellipsis {
	height: 30px;
//padding-left: 5px;
}

.xxpre {
	overflow: auto;
}

.el-textarea__inner {
	border: none;
	background-color: #f0f8ff00;
}

.el-textarea__inner::-webkit-scrollbar {
	display: none;
}
</style>
