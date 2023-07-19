<template>
	<div class="data-executor-vue" :style="{ height: rightBodyHeight + 'px' }">
		<div style="padding: 0 10px 10px;height: 100%;box-sizing: border-box;">
			<el-card id="maintopcard" :style="{ height: rightBodyTopHeight + 'px' }">
				<ace-editor id="aceEditorId" v-model="sqlExecutorContent" ref="sqlEditor" @init="sqlExecutorInit"
							lang="sql"
							theme="monokai"
							width="100%" :height="aceEditorHeight" :options="sqlEditorConfig" :source="executorSource"
							@cursorSelection="cursorSelection"></ace-editor>
				<div :style="{ height: aceEditorButtonHeight + 'px','margin-top': '5px' }" >
					<el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain size="mini"
							   icon="el-icon-video-pause">取消执行
					</el-button>
					<el-button v-if="dataLoading" type="primary" plain size="mini"
							   :loading="dataLoading">查询完成,数据加载中
					</el-button>
					<el-tooltip v-if="!sqlExecuting&&!dataLoading" effect="dark" content="Ctrl+R、Ctrl+Enter" placement="top">
						<el-button v-on:click="doExecutorSql" type="primary" plain size="mini"
								   icon="el-icon-video-play">{{ executeButtonText }}
						</el-button>
					</el-tooltip>
					<el-button icon="el-icon-brush" size="mini" @click="formatterSql">SQL美化</el-button>
					<el-button v-on:click="addFavorite('')" plain size="mini" icon="el-icon-star-off">收藏</el-button>
					<div style="float: right;">
						<el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" size="mini" filterable
								   placeholder="请选择数据源" style="width: 300px;margin-left: 10px;">
							<el-option v-for="item in datasourceOptions" :key="item.id" :label="item.name"
									   :value="item.id"></el-option>
						</el-select>
						<el-select v-model="choiceDatabase" @change="databaseChangeEvents" size="mini" filterable
								   placeholder="请选择数据库" style="width: 200px;margin-left: 10px;">
							<el-option v-for="item in databaseList" :key="item.dbName" :label="item.dbName"
									   :value="item.dbName"></el-option>
						</el-select>
					</div>
				</div>
				<div v-if="sqlParams.length > 0" class="sql-params">
					<el-input :placeholder="'请输入'+param.key+'的值'" v-model="param.value"
							  v-for="(param,index) in sqlParams" :key="index">
						<template slot="prepend">{{ param.key }}</template>
					</el-input>
				</div>
			</el-card>
			<div ref="topResize" class="top-resize">
				<i ref="topResizeBar"></i>
			</div>
			<el-card :style="{ height: rightBodyButtomHeight + 'px' }">
				<div style="position: relative;">
					<div style="position: absolute;right: 0;z-index: 1;">
						<!-- 复制选中行 -->
						<el-dropdown @command="handleCopyCheckLineCommand"
									 v-show="this.choiceResultObj[this.executeShowTable] && this.choiceResultObj[this.executeShowTable].length > 0">
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
					<el-tabs :style="{ height: rightBodyButtomTabsHeight + 'px' }" v-model="executeShowTable"
							 type="border-card" @tab-click="tabHandleClick">
						<el-tab-pane label="执行历史" name="tabHistory">
							<el-table :data="myHistoryListList" stripe border
									  :style="{ height: rightBodyButtomTabsContentHeight + 'px',overflow: 'auto' }">
								<el-table-column prop="createTime" label="执行时间" width="160px"></el-table-column>
								<el-table-column prop="content" label="SQL">
									<template slot-scope="scope">
										<pre class="sql-content-line" @dblclick="inputFavoriteSql(scope.row)"
											 :title="scope.row.content">{{ scope.row.content }}</pre>
									</template>
								</el-table-column>
								<el-table-column label="操作" width="160px">
									<template slot-scope="scope">
										<el-button size="mini" type="primary" @click="inputFavoriteSql(scope.row)">
											输入
										</el-button>
										<el-button size="mini" type="success" @click="addFavorite(scope.row.content)"
												   style="margin-left: 10px;">收藏
										</el-button>
									</template>
								</el-table-column>
							</el-table>
						</el-tab-pane>
						<el-tab-pane label="我的收藏" name="tabFavorite">
							<el-table :data="myFavoriteList" stripe border
									  :style="{ height: rightBodyButtomTabsContentHeight + 'px',overflow: 'auto' }"
									  v-infinite-scroll>
								<el-table-column prop="createTime" label="执行时间" width="160px"></el-table-column>
								<el-table-column prop="content" label="SQL">
									<template slot-scope="scope">
										<pre class="sql-content-line" @dblclick="inputFavoriteSql(scope.row)"
											 :title="scope.row.content">{{ scope.row.content }}</pre>
									</template>
								</el-table-column>
								<el-table-column label="操作" width="160px">
									<template slot-scope="scope">
										<el-button size="mini" type="primary" v-on:click="inputFavoriteSql(scope.row)">
											输入
										</el-button>
										<el-button size="mini" type="danger" v-on:click="delFavorite(scope.row)"
												   style="margin-left: 10px;">
											删除
										</el-button>
									</template>
								</el-table-column>
							</el-table>
						</el-tab-pane>
						<el-tab-pane label="信息" name="tabInfo" v-if="!!executeResultInfo">
							<pre class="execute-result-info"
								 :style="{ height: rightBodyButtomTabsContentHeight + 'px',overflow: 'auto' }">{{
									executeResultInfo
								}}</pre>
						</el-tab-pane>
						<el-tab-pane label="错误" name="tabError" v-if="!!executeError">
							<div style="color: #f00;">{{ executeError }}</div>
						</el-tab-pane>
						<template v-else>
							<el-tab-pane :label="resultItem.label" :name="resultItem.name"
										 v-for="(resultItem,index) in executeResultList" :key="index"
										 lazy>
								<div v-if="!!resultItem.errMsg" style="color: #f00;">{{ resultItem.errMsg }}</div>
								<ux-grid v-else
										 v-clickoutside="handleClickOutside"
										 :data="resultItem.dataList"
										 @table-body-scroll="scroll"
										 @selection-change="handleSelectionChange"
										 @cell-click="mouseOnFocus"
										 @cell-mouse-leave="mouseLeave"
										 :checkboxConfig="{checkMethod: selectable, highlight: true}"
										 stripe border :height="height" max-height="600"
										 style="width: 100%; margin-bottom: 5px;" class="execute-result-table">
									<ux-table-column type="checkbox" width="55"></ux-table-column>
									<ux-table-column type="index" width="55" title=" "></ux-table-column>
									<ux-table-column v-for="(item,index) in resultItem.dataCols" :key="index"
													 :prop="item.prop" :title="item.label"
													 :resizable="true"
													 :width="item.width">
										<template slot="header" slot-scope="scope">
											<el-tooltip effect="dark" :content="item.desc" placement="top">
												<span>{{ item.label }}</span>
											</el-tooltip>
										</template>
										<template slot-scope="scope">
											<input title="" :value="scope.row[item.prop]" class="el-textarea__inner"
													  ></input>
										</template>
									</ux-table-column>
								</ux-grid>
								<el-pagination
									v-if="resultItem.selectCount"
									@current-change="handleCurrentChange"
									:current-page="currentPage"
									:page-size="pageSize"
									:page-sizes="[1000]"
									layout="total, sizes, prev, pager, next, jumper"
									:total="resultItem.selectCount">
								</el-pagination>
								<div v-if="resultItem.selectCount" style="position: absolute;right: 5px;bottom: 5px;">
									<el-button type="primary" plain v-on:click="viewAllData()">查看所有</el-button>
								</div>
								<div v-if="!resultItem.selectCount"
									 style="height: 20px;font-size: 13px;font-weight: 400;color: #606266;padding-left: 5px;">
									共 {{ resultItem.totalCount }} 条
								</div>
							</el-tab-pane>
						</template>
						<el-main v-loading="loadingAll"
								 v-show="loadingAll"
								 element-loading-text="正在加载中"
								 element-loading-spinner="el-icon-loading"
								 style="height: 175px;">
						</el-main>
					</el-tabs>
				</div>
			</el-card>
		</div>
		<!--选择导出为update的条件列弹窗-->
		<el-dialog :visible.sync="exportConditionVisible" width="500px" title="选择更新语句条件">
			<div>
				更新条件列：
				<el-select v-model="conditionDataColsChoice" multiple placeholder="请选择" style="width: 370px;">
					<el-option v-for="item in conditionDataCols" :key="item.prop" :label="item.label"
							   :value="item.prop"></el-option>
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
import copyFormatter from './copy/index'
import sqlFormatter from "sql-formatter"
import datasourceApi from '../../common/api/datasource'
import aceEditor from "../../common/lib/ace-editor";
import sqlParser from "./parser/SqlParser";
import Clickoutside from 'element-ui/src/utils/clickoutside';
import { throttle, debounce } from '@/common/utils/throttleDebounce.js'
import { EventSourcePolyfill } from "event-source-polyfill";
import storageUtil from "../../common/lib/zyplayer/storageUtil";

export default {
	directives: {Clickoutside},
	data() {
		return {
			rightBodyHeight: 0,
			rightBodyTopHeight: 0,
			rightBodyButtomHeight: 0,
			aceEditorHeight: 0,
			rightBodyButtomTabsHeight: 0,
			rightBodyButtomTabsContentHeight: 0,
			aceEditorButtonHeight: 0,

			changeFlag: true,
			changeFlag2: true,

			//遮罩层
			loadingAll: false,

			height: 0,
			scrollTop: 0,
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

			//选中的单元格
			uxGridCell: "",

			pageSize: 1000,
			currentPage: 1,

			sqlExecuting: false,
			dataLoading: false,
			executeResultList: [],
			executeResultInfo: "",
			executeShowTable: "tabHistory",
			sqlExecutorEditor: {},
			nowExecutorId: 1,
			executeError: "",
			// 收藏及历史
			myFavoriteList: [],
			myHistoryListList: [],
			// 选择复制
			choiceResultObj: {},

			exportConditionVisible: false,
			conditionDataCols: [],
			conditionDataColsChoice: [],
			//执行按钮文本
			executeButtonText: '执行',
			// 编辑器
			sqlExecutorContent: '',
			sqlEditorConfig: {
				wrap: true,
				autoScrollEditorIntoView: true,
				enableBasicAutocompletion: true,
				enableSnippets: true,
				enableLiveAutocompletion: true,
			},
			executorSource: {},
			// sql参数
			sqlParams: [],
			sqlParamWaiting: false,
			sqlParamHistory: {},
		}
	},
	components: {
		'ace-editor': aceEditor
	},
	mounted: function () {
		this.loadDatasourceList();
		this.dragChangeTopHeight();
		//计算高度
		this.elementHeightCalculationInit();
	},
	activated() {
		this.loadDatasourceList();
	},
	methods: {
		// 创建sse连接
		createSseConnect() {
			if (window.EventSource) {
				let clientId = storageUtil.data.get("CLIENTID") ? storageUtil.data.get("CLIENTID") : "";
				let url = process.env.VUE_APP_BASE_API+'/zyplayer-doc-db/doc-db/sse/createConnect?clientId='+clientId;
				//heartbeatTimeout:心跳超时监测 30s
				let source = new EventSourcePolyfill(url,
					{withCredentials: true,heartbeatTimeout: 30000})
				// 监听打开事件
				source.addEventListener('open', (e) => {
					//console.log("设备连接成功",e)
				})

				// 监听消息事件
				source.addEventListener("message", (e) => {
					const result = JSON.parse(e.data)
					const code = result.errCode
					const msg = result.errMsg
					const data = result.data

					if (code === 200) {
						console.log("see推送消息:",data)
						this.sqlExecuting = false;
						this.dataLoading = true;
						source.close();
					} else if (code === 0) {
						// 初次建立连接，客户端id储存本地
						storageUtil.data.set("CLIENTID", data)
						console.log("客户端id:",data)
					}

				})

				// 监听错误事件
				source.addEventListener("error", (e) => {
					console.log("发生错误，已断开与服务器的连接:",e)
					source.close();
				})

			} else {
				console.log("该浏览器不支持sse")
			}
		},
		sqlExecutorInit(editor) {
			this.sqlExecutorEditor = editor;
			this.sqlExecutorEditor.setFontSize(16);
			let that = this;
			this.sqlExecutorEditor.commands.addCommand({
				name: "execute-sql",
				bindKey: {win: "Ctrl-R|Ctrl-Shift-R|Ctrl-Enter", mac: "Command-R|Command-Shift-R|Command-Enter"},
				exec: function (editor) {
					that.doExecutorSql();
				}
			});
			editor.on('change', () => {
				if (!this.sqlParamWaiting) {
					this.sqlParamWaiting = true;
					setTimeout(() => {
						let content = editor.getValue();
						let paramArr = sqlParser.parserArr(content, [
							{start: '${', end: '}'}, {start: '#{', end: '}'}
						]);
						this.sqlParams = [];
						paramArr.forEach(item => {
							this.sqlParams.push({key: item, value: this.sqlParamHistory[item] || ''});
						});

						let aceEditorButtonHeight = this.aceEditorButtonHeight;
						let aceEditorHeight = this.aceEditorHeight;
						if (this.sqlParams.length > 0) {
							if(this.changeFlag){
								this.rightBodyTopHeight = aceEditorButtonHeight + aceEditorHeight + 35 +20;
								this.elementHeightCalculation();
								this.changeFlag = false;
								this.changeFlag2 = true;
							}
						}else{
							if(!this.changeFlag&&this.changeFlag2){
								this.rightBodyTopHeight = aceEditorButtonHeight + aceEditorHeight + 20 ;
								this.elementHeightCalculation();
								this.changeFlag2 = false;
								this.changeFlag = true;
							}
						}
						this.sqlParamWaiting = false;
					}, 300);
				}
			});
		},
		cursorSelection(sqlValue) {
			if (sqlValue) {
				this.executeButtonText = '执行已选择的'
			} else {
				this.executeButtonText = '执行'
			}
		},
		scroll({scrollTop, scrollLeft}) {
			this.scrollTop = scrollTop
		},
		selectable({row}) {
			return row.id !== 2
		},
		cancelExecutorSql() {
			datasourceApi.executeSqlCancel({executeId: this.nowExecutorId}).then(() => {
				this.$message.success("取消成功");
			});
		},
		loadHistoryAndFavoriteList() {
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
			let sqlParamObj = {};
			this.sqlParams.forEach(item => {
				if (!!item.value) {
					sqlParamObj[item.key] = item.value;
				}
			});
			let param = {
				name: '我的收藏',
				content: sqlValue,
				paramJson: JSON.stringify(sqlParamObj),
				datasourceId: this.choiceDatasourceId
			};
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
		inputFavoriteSql(item) {
			this.sqlExecutorEditor.setValue(item.content, 1);
			if (!!item.paramJson) {
				let paramJson = JSON.parse(item.paramJson);
				for (let key in paramJson) {
					this.sqlParamHistory[key] = paramJson[key];
				}
			}
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
		//执行sql（type=noPage查看所有）
		doExecutorSql(init,type) {
			if (!this.choiceDatasourceId) {
				this.$message.error("请先选择数据源");
				return;
			}
			if (!this.choiceDatabase) {
				this.$message.error("请先选择数据库");
				return;
			}

			this.loadingAll = true;
			this.executeError = "";
			this.executeUseTime = "";
			this.executeResultList = [];
			let sqlParamObj = {};
			this.sqlParams.forEach(item => {
				if (!!item.value) {
					sqlParamObj[item.key] = item.value;
					this.sqlParamHistory[item.key] = item.value;
				}
			});
			this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
			let sqlValue = this.sqlExecutorEditor.getSelectedText();
			if (!sqlValue) {
				sqlValue = this.sqlExecutorEditor.getValue();
			}
			this.sqlExecuting = true;
			this.createSseConnect();
			datasourceApi.queryExecuteSql({
				sourceId: this.choiceDatasourceId,
				dbName: this.choiceDatabase,
				executeId: this.nowExecutorId,
				pageNum: this.currentPage,
				pageSize: this.pageSize,
				type: type,
				sql: sqlValue,
				params: JSON.stringify(sqlParamObj),
			}).then(response => {
				this.sqlExecuting = false;
				this.dataLoading = false;
				this.loadingAll = false;
				if (response.errCode != 200) {
					this.executeShowTable = 'tabError';
					this.executeError = response.errMsg;
					return;
				}
				let resIndex = 1;
				let executeResultList = [];
				let resData = response.data || [];
				let executeResultInfo = "";
				resData.forEach(result => {
					let dataListRes = [];
					let previewColumns = [];
					executeResultInfo += this.getExecuteInfoStr(result);
					if (result.errCode === 0) {
						let dataListTemp = result.data || [];
						let headerList = result.header || [];
						// 组装表头
						let columnSet = {};
						if (headerList.length > 0) {
							let headerIndex = 0;
							headerList.forEach(item => {
								let key = 'value_' + (headerIndex++);
								columnSet[key] = item;
								previewColumns.push({prop: key, label: item, desc: item});
							});
							dataListTemp.forEach(item => {
								let dataItem = {}, dataIndex = 0;
								previewColumns.forEach(column => {
									let key = column.prop;
									dataItem[key] = item[dataIndex++];
									if ((dataItem[key] + '').length > columnSet[key].length) {
										columnSet[key] = dataItem[key] + '';
									}
								});
								dataListRes.push(dataItem);
							});
							previewColumns.forEach(item => {
								// 动态计算宽度~自己想的一个方法，666
								document.getElementById("widthCalculate").innerText = columnSet[item.prop];
								let width = document.getElementById("widthCalculate").offsetWidth;
								width = width + (columnSet[item.prop] === item.label ? 35 : 55);
								width = (width < 50) ? 50 : width;
								item.width = (width > 200) ? 200 : width;
							});
						}
					}
					executeResultList.push({
						label: '结果' + resIndex,
						name: 'result_' + resIndex,
						errMsg: result.errMsg,
						errCode: result.errCode,
						queryTime: result.queryTime,
						selectCount: result.selectCount,
						totalCount: dataListRes.length,
						dataCols: previewColumns,
						dataList: dataListRes
					});
					resIndex++;
					//动态设置表格高度
					if (result.selectCount) {
						this.height = this.rightBodyButtomTabsContentHeight - 40;
					}else{
						this.height = this.rightBodyButtomTabsContentHeight - 20;
					}
				});
				//多个结果情况下,且点击分页
				if (init != 1) {
					this.executeShowTable = (resIndex === 1) ? "tabInfo" : "result_1";
				}
				this.executeResultInfo = executeResultInfo;
				this.executeResultList = executeResultList;
				this.loadHistoryList();

			});
		},
		//查看所有数据
		viewAllData(init) {
			this.doExecutorSql(init,'noPage');
		},
		handleCurrentChange(to) {
			this.currentPage = to;
			let init = 1;
			this.doExecutorSql(init);
		},
		loadDatasourceList() {
			datasourceApi.datasourceList({}).then(json => {
				this.datasourceList = json.data || [];
				this.datasourceOptions = this.datasourceList;

				//子组件向父组件传值
				//this.$emit('listenToChildEvent',this.datasourceList);

				let datasourceGroupList = [];
				this.datasourceList.filter(item => !!item.groupName).forEach(item => datasourceGroupList.push(item.groupName || ''));
				this.datasourceGroupList = Array.from(new Set(datasourceGroupList));

				if (this.datasourceList.length > 0) {
					this.choiceDatasourceId = this.datasourceList[0].id;
					//初次加载根据query值设置对应数据源
					if (this.$route.query.datasourceId) {
						this.choiceDatasourceId = parseInt(this.$route.query.datasourceId);
					}
					//初次加载根据query值设置对应数据库
					if (this.$route.query.database) {
						this.choiceDatabase = this.$route.query.database;
					}
					this.executorSource = {sourceId: this.choiceDatasourceId};
					this.loadDatabaseList(true);
					this.loadSourceBaseInfo();
					this.loadHistoryAndFavoriteList();
				}
			});
		},
		//initFlag: 初次加载状态
		loadDatabaseList(initFlag) {
			datasourceApi.databaseList({sourceId: this.choiceDatasourceId}).then(json => {
				this.databaseList = json.data || [];
				if (this.databaseList.length > 0) {
					// 排除系统库
					let sysDbName = ["information_schema", "master", "model", "msdb", "tempdb"];
					let notSysDbItem = this.databaseList.find(item => sysDbName.indexOf(item.dbName) < 0);
					// 非初次加载，动态改变url参数
					if (!initFlag) {
						this.choiceDatabase = (!!notSysDbItem) ? notSysDbItem.dbName : this.databaseList[0].dbName;
						this.$router.replace({
							query: {
								datasourceId: this.choiceDatasourceId,
								database: this.choiceDatabase
							}
						})
					}
					this.executorSource = {sourceId: this.choiceDatasourceId, dbName: this.choiceDatabase};
				}
			});
		},
		loadSourceBaseInfo() {
			datasourceApi.getSourceBaseInfo({sourceId: this.choiceDatasourceId}).then(json => {
				let data = json.data || {};
				this.editorDbProduct = data.product || '';
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
				this.executorSource = {sourceId: this.choiceDatasourceId};
				this.loadDatabaseList();
				this.loadSourceBaseInfo();
				this.loadHistoryAndFavoriteList();
			}
		},
		datasourceChangeEvents() {
			this.executorSource = {sourceId: this.choiceDatasourceId};
			this.currentPage = 1;
			this.loadDatabaseList();
			this.loadSourceBaseInfo();
			this.loadHistoryAndFavoriteList();
		},
		databaseChangeEvents() {
			this.$router.replace({query: {datasourceId: this.choiceDatasourceId, database: this.choiceDatabase}})
			this.executorSource = {sourceId: this.choiceDatasourceId, dbName: this.choiceDatabase};
			this.currentPage = 1;
		},
		getExecuteInfoStr(resultData) {
			var resultStr = resultData.executeSql;
			resultStr += "\n> 状态：" + ((!!resultData.errMsg) ? "ERROR" : "OK");
			if (resultData.updateCount >= 0) {
				resultStr += "\n> 影响行数：" + resultData.updateCount;
			}
			resultStr += "\n> 耗时：" + (resultData.queryTime || 0) / 1000 + "s";
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
		handleSelectionChange(val) {
			this.$set(this.choiceResultObj, this.executeShowTable, val);
		},
		tabHandleClick(t) {

		},
		doCopyCheckLineUpdate() {
			let choiceData = this.choiceResultObj[this.executeShowTable] || [];
			if (choiceData.length > 0) {
				let dataCols = this.executeResultList.find(item => item.name === this.executeShowTable).dataCols;
				let copyData = copyFormatter.format('update', this.editorDbProduct, dataCols, choiceData, this.conditionDataColsChoice);
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
				let copyData = copyFormatter.format(type, this.editorDbProduct, dataCols, choiceData, '');
				this.$copyText(copyData).then(
					res => this.$message.success("内容已复制到剪切板！"),
					err => this.$message.error("抱歉，复制失败！")
				);
			}
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
		//表格单元格 hover 退出
		mouseLeave(row, column, cell, event) {
			// if(this.uxGridCell){
			// 	this.uxGridCell.style.border = 'none'
			// }
		},
		// 点击区域外
		handleClickOutside() {
			if (this.uxGridCell) {
				this.uxGridCell.style.border = 'none'
			}
		},
		dragChangeTopHeight: function () {
			//浏览器页面高度
			let winHeight = window.innerHeight;
			//主体高度
			let bodyHeight = winHeight - 82;
			this.rightBodyHeight = bodyHeight;
			let bodyTopHeight = bodyHeight * 0.75;
			// 保留this引用
			let resize = this.$refs.topResize;
			let resizeBar = this.$refs.topResizeBar;
			resize.onmousedown = e => {
				let startY = e.clientY;
				// 颜色改变提醒
				resize.style.setProperty("--topResizeColor", "#ccc");
				//resize.style.background = "#ccc";
				//resizeBar.style.background = "#aaa";
				resize.left = resize.offsetLeft;
				document.onmousemove = throttle( e2 => {
					// 计算并应用位移量
					let endY = e2.clientY;
					let moveLen = startY - endY;
					if ((moveLen < 0 && this.rightBodyTopHeight < bodyTopHeight) || (moveLen > 0 && this.rightBodyTopHeight > 100)) {
						startY = endY;
						this.rightBodyTopHeight -= moveLen;
						if (this.rightBodyTopHeight < 100) {
							this.rightBodyTopHeight = 100;
						}
						if (this.rightBodyTopHeight > bodyTopHeight) {
							this.rightBodyTopHeight = bodyTopHeight;
						}
						this.elementHeightCalculation();
					}
				},10);
				document.onmouseup = () => {
					// 颜色恢复
					resize.style.setProperty("--topResizeColor", "#fafafa");
					//resize.style.background = "#fafafa";
					//resizeBar.style.background = "#ccc";
					document.onmousemove = null;
					document.onmouseup = null;
				};
				return false;
			};
		},
		//高度计算初始化
		elementHeightCalculationInit(){
			//浏览器页面高度
			let winHeight = window.innerHeight;
			//主体高度
			let bodyHeight = winHeight - 82;
			this.rightBodyHeight = bodyHeight;
			let bodyTopHeight = bodyHeight * 0.55;
			this.rightBodyTopHeight = bodyTopHeight;
			let bodyButtomHeight = bodyHeight - bodyTopHeight - 10;
			this.rightBodyButtomHeight = bodyButtomHeight;
			this.rightBodyButtomTabsHeight = bodyButtomHeight - 20;
			this.rightBodyButtomTabsContentHeight = this.rightBodyButtomTabsHeight - 50;
			//虚拟表格高度
			this.height = this.rightBodyButtomTabsContentHeight;
			setTimeout(() => {
				this.aceEditorButtonHeight = 30;
				//主体上半部分高度
				let bodyTopHeight = document.getElementById('maintopcard').offsetHeight;
				this.aceEditorHeight = bodyTopHeight - this.aceEditorButtonHeight-20 -10;
			}, 500)
		},
		//高度实时计算
		elementHeightCalculation(){
			//浏览器页面高度
			let winHeight = window.innerHeight;
			//主体高度
			let bodyHeight = winHeight - 82;
			this.rightBodyHeight = bodyHeight;
			let bodyButtomHeight = bodyHeight - this.rightBodyTopHeight - 10;
			this.rightBodyButtomHeight = bodyButtomHeight;
			this.rightBodyButtomTabsHeight = bodyButtomHeight - 20;
			this.rightBodyButtomTabsContentHeight = this.rightBodyButtomTabsHeight - 50
			//虚拟表格高度
			this.height = this.rightBodyButtomTabsContentHeight-40;
			//触发编辑器高度变化监听
			this.aceEditorHeight = this.rightBodyTopHeight - this.aceEditorButtonHeight - 20 - 10;
			if (this.sqlParams.length > 0) {
				//触发编辑器高度变化监听
				this.aceEditorHeight = this.rightBodyTopHeight - this.aceEditorButtonHeight - 20 - 10 -35;
			}
		}
	}
}
</script>

<style scoped>
.data-executor-vue .ace-monokai .ace_print-margin {
	display: none;
}

.data-executor-vue .el-card__body {
	padding: 5px;
}

.data-executor-vue .sql-params {
	height: 50px;
	overflow: auto;
}

.data-executor-vue .sql-params .el-input-group {
	width: auto;
	margin: 3px 3px 0 0;
}

.data-executor-vue .sql-params .el-input__inner {
	width: 200px;
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

.data-executor-vue .sql-content-line {
	margin: 0;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.data-executor-vue .execute-use-time {
	font-size: 12px;
	margin-right: 10px;
}

.data-executor-vue .execute-result-info {
	white-space: pre-wrap;
	max-height: 400px;
	overflow: auto;
	background: #263238;
	color: #fff;
	padding: 0 10px;
	margin: 0;
	border-radius: 6px;
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

.el-textarea__inner {
	border: none;
	background-color: #f0f8ff00;
}

.el-textarea__inner::-webkit-scrollbar {
	display: none;
}

/deep/ .el-tabs--border-card > .el-tabs__content {
	padding: 5px;
}

/deep/ .elx-table .elx-body--column.col--ellipsis > .elx-cell,
.elx-table .elx-footer--column.col--ellipsis > .elx-cell,
.elx-table .elx-header--column.col--ellipsis > .elx-cell {
	overflow: auto;
	text-overflow: unset;
}

/deep/ .elx-table .elx-body--column.col--ellipsis > .elx-cell::-webkit-scrollbar {
	display: none;
}

:root {
	--topResizeColor: #fafafa;
}

.top-resize {
	width: 100%;
	height: 5px;
	cursor: s-resize;
	background: var(--topResizeColor);
	display: flex;
}

.top-resize:hover{
	background: #ccc;
}

.top-resize i {
	width: 35px;
	height: 5px;
	display: inline-block;
	line-height: 0px;
	border-radius: 5px;
	/*background: #ccc;*/
	/*color: #888;*/
	text-align: center;
	margin: auto;
}
</style>
