
import datasourceApi from '../../api/datasource'
/**
 * 编辑框自动提示数据库、表和字段等
 */
export default {
	isInit: false,
	source: {},
	databaseInfo: {},
	tableInfo: {},
	columnInfo: {},
	lastCallbackArr: [],
	isAutocomplete: false,
	change(source) {
		this.source = source;
		this.lastCallbackArr = [];
		console.log("change(sourceId)：" + JSON.stringify(this.source));
		if (!this.isInit) {
			console.log("change(sourceId)，isInit：" + this.isInit);
			this.isInit = true;
			let languageTools = ace.acequire("ace/ext/language_tools");
			languageTools.addCompleter(this);
		}
		// 初始加载
		if (!!this.source.sourceId) {
			// 加载所有库
			let databaseList = this.databaseInfo[this.source.sourceId] || [];
			if (databaseList.length <= 0) {
				datasourceApi.databaseList({sourceId: this.source.sourceId}).then(json => {
					this.databaseInfo[this.source.sourceId] = json.data || [];
				});
			}
			// 加载库下所有表
			if (!!this.source.dbName) {
				let tableKey = this.source.sourceId + '_' + this.source.dbName;
				let tableList = this.tableInfo[tableKey] || [];
				if (tableList.length <= 0) {
					datasourceApi.tableList({sourceId: this.source.sourceId, dbName: this.source.dbName}).then(json => {
						this.tableInfo[tableKey] = json.data || [];
					});
				}
			}
			// 加载表下所有字段
			if (!!this.source.tableName) {
				let columnKey = this.source.sourceId + '_' + this.source.dbName + '_' + this.source.tableName;
				let columnList = this.columnInfo[columnKey] || [];
				if (columnList.length <= 0) {
					datasourceApi.tableColumnList({sourceId: this.source.sourceId, dbName: this.source.dbName, tableName: this.source.tableName}).then(json => {
						this.columnInfo[columnKey] = json.data.columnList || [];
					});
				}
			}
		}
	},
	startAutocomplete(editor) {
		this.isAutocomplete = true;
		editor.execCommand("startAutocomplete");
	},
	async getCompletions(editor, session, pos, prefix, callback) {
		let callbackArr = [];
		let endPos = this.isAutocomplete ? pos.column : pos.column - 1;
		let lineStr = session.getLine(pos.row).substring(0, endPos);
		this.isAutocomplete = false;
		console.log("Executor.vue getCompletions，sourceId：" + JSON.stringify(this.source) + '， lineStr：' + lineStr, pos);
		if (!!this.source.tableName) {
			// 如果指定了表名，则只提示字段，其他都不用管，用在表数据查看页面
			callbackArr = await this.getAssignTableColumns(this.source.dbName, this.source.tableName);
			callback(null, callbackArr);
		} else if (lineStr.endsWith("from ") || lineStr.endsWith("join ") || lineStr.endsWith("into ")
			|| lineStr.endsWith("update ") || lineStr.endsWith("table ")) {
			// 获取库和表
			callbackArr = this.getDatabasesAndTables();
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else if (lineStr.endsWith(".")) {
			// 获取表和字段
			callbackArr = await this.getTablesAndColumns(lineStr);
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else if (lineStr.endsWith("select ") || lineStr.endsWith("where ") || lineStr.endsWith("and ")
			|| lineStr.endsWith("or ") || lineStr.endsWith("set ")) {
			// 获取字段
			callbackArr = await this.getTableColumns(session, pos);
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else {
			callback(null, this.lastCallbackArr);
		}
	},
	getDatabasesAndTables() {
		let callbackArr = [];
		// 所有表
		let tableList = this.tableInfo[this.source.sourceId + '_' + this.source.dbName] || [];
		tableList.forEach(item => callbackArr.push({
			caption: (!!item.tableComment) ? item.tableName + '-' + item.tableComment : item.tableName,
			snippet: item.tableName,
			meta: "表",
			type: "snippet",
			score: 1000
		}));
		// 所有库
		let databaseList = this.databaseInfo[this.source.sourceId] || [];
		databaseList.forEach(item => callbackArr.push({
			caption: item.dbName,
			snippet: item.dbName,
			meta: "库",
			type: "snippet",
			score: 1000
		}));
		return callbackArr;
	},
	async getTablesAndColumns(lineStr) {
		let isFound = false;
		let callbackArr = [];
		// 匹配 库名. 搜索表名
		let databaseList = this.databaseInfo[this.source.sourceId] || [];
		for (let i = 0; i < databaseList.length; i++) {
			let item = databaseList[i];
			if (lineStr.endsWith(item.dbName + ".")) {
				let tableList = this.tableInfo[this.source.sourceId + '_' + item.dbName] || [];
				if (tableList.length <= 0) {
					let res = await datasourceApi.tableList({sourceId: this.source.sourceId, dbName: item.dbName});
					tableList = res.data || [];
					this.tableInfo[this.source.sourceId + '_' + item.dbName] = tableList;
				}
				tableList.forEach(item => callbackArr.push({
					caption: (!!item.tableComment) ? item.tableName + '-' + item.tableComment : item.tableName,
					snippet: item.tableName,
					meta: "表",
					type: "snippet",
					score: 1000
				}));
				isFound = true;
			}
		}
		// 未找到，匹配 表名. 搜索字段名
		if (!isFound) {
			let tableList = this.tableInfo[this.source.sourceId + '_' + this.source.dbName] || [];
			for (let i = 0; i < tableList.length; i++) {
				let tableName = tableList[i].tableName;
				if (lineStr.endsWith(tableName + ".")) {
					callbackArr = await this.getAssignTableColumns(this.source.dbName, tableName);
				}
			}
		}
		return callbackArr;
	},
	async getTableColumns(session, pos) {
		let queryText = "";
		// 往前加
		for (let i = pos.row; i >= 0; i--) {
			let tempLine = session.getLine(i);
			queryText = tempLine + " " + queryText;
			if (tempLine.indexOf(";") >= 0) {
				break;
			}
		}
		// 往后加
		for (let i = pos.row + 1; i < session.getLength(); i++) {
			let tempLine = session.getLine(i);
			queryText = queryText + " " + tempLine;
			if (tempLine.indexOf(";") >= 0) {
				break;
			}
		}
		// 所有表，找下面的字段列表
		let callbackArr = [];
		let tableList = this.tableInfo[this.source.sourceId + '_' + this.source.dbName] || [];
		for (let i = 0; i < tableList.length; i++) {
			let tableName = tableList[i].tableName;
			if (queryText.indexOf(tableName) >= 0) {
				let tempArr = await this.getAssignTableColumns(this.source.dbName, tableName);
				callbackArr = callbackArr.concat(tempArr);
			}
		}
		return callbackArr;
	},

	/**
	 * 获取指定数据表的字段
	 * @param dbName
	 * @param tableName
	 */
	async getAssignTableColumns(dbName, tableName) {
		let columnKey = this.source.sourceId + '_' + dbName + '_' + tableName;
		let columnList = this.columnInfo[columnKey] || [];
		if (columnList.length <= 0) {
			let res = await datasourceApi.tableColumnList({
				sourceId: this.source.sourceId,
				dbName: dbName,
				tableName: tableName
			});
			columnList = res.data.columnList || [];
			this.columnInfo[columnKey] = columnList;
		}
		let callbackArr = [];
		columnList.forEach(item => {
			let caption = (!!item.description) ? item.name + "-" + item.description : item.name;
			callbackArr.push({
				caption: caption,
				snippet: item.name,
				meta: "字段",
				type: "snippet",
				score: 1000
			});
		});
		return callbackArr;
	}
}
