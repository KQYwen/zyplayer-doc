
import datasourceApi from '../../api/datasource'
/**
 * 编辑框自动提示数据库、表和字段等
 */
export default {
	isInit: false,
	source: {},
	databaseInfo: {},
	tableInfo: {},
	lastCallbackArr: [],
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
				let tableList = this.tableInfo[this.source.sourceId + '_' + this.source.dbName] || [];
				if (tableList.length <= 0) {
					datasourceApi.tableList({sourceId: this.source.sourceId, dbName: this.source.dbName}).then(json => {
						this.tableInfo[this.source.sourceId + '_' + this.source.dbName] = json.data || [];
					});
				}
			}
		}
	},
	getCompletions(editor, session, pos, prefix, callback) {
		let callbackArr = [];
		let lineStr = session.getLine(pos.row).substring(0, pos.column - 1);
		// console.log("Executor.vue getCompletions，sourceId：" + JSON.stringify(this.source) + '， lineStr：'+ lineStr, pos);
		if (lineStr.endsWith("from ") || lineStr.endsWith("join ")) {
			this.getDatabasesAndTables(callbackArr);
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else if (lineStr.endsWith(".")) {
			this.getTablesAndColumns(callbackArr, lineStr);
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else if (lineStr.endsWith("select ") || lineStr.endsWith("where ") || lineStr.endsWith("and ")) {
			this.getTableColumns(callbackArr, session, pos);
			this.lastCallbackArr = callbackArr;
			callback(null, callbackArr);
		} else {
			callback(null, this.lastCallbackArr);
		}
	},
	getDatabasesAndTables(callbackArr) {
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
	},
	getTablesAndColumns(callbackArr, lineStr) {
		let isFound = false;
		// 匹配 库名. 搜索表名
		let databaseList = this.databaseInfo[this.source.sourceId] || [];
		databaseList.forEach(async item => {
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
		});
		// 未找到，匹配 表名. 搜索字段名
		// if (!isFound) {
		// 	for (let key in this.editorColumnInfo) {
		// 		if (!lineStr.endsWith(key + ".")) {
		// 			continue;
		// 		}
		// 		let columnInfo = this.editorColumnInfo[key];
		// 		if (!!columnInfo) {
		// 			for (let i = 0; i < columnInfo.length; i++) {
		// 				let caption = (!!columnInfo[i].description) ? columnInfo[i].name + "-" + columnInfo[i].description : columnInfo[i].name;
		// 				callbackArr.push({
		// 					caption: caption,
		// 					snippet: columnInfo[i].name,
		// 					meta: "column",
		// 					type: "snippet",
		// 					score: 1000
		// 				});
		// 			}
		// 			isFound = true;
		// 		}
		// 	}
		// }
	},
	getTableColumns(callbackArr, session, pos) {
		return;
		let isFound = false;
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
		for (let key in this.editorDbTableInfo) {
			let tableInfo = this.editorDbTableInfo[key];
			for (let i = 0; i < tableInfo.length; i++) {
				if (queryText.indexOf(tableInfo[i].tableName) < 0) {
					continue;
				}
				let columnInfo = this.editorColumnInfo[tableInfo[i].tableName];
				if (!!columnInfo) {
					for (let j = 0; j < columnInfo.length; j++) {
						let caption = (!!columnInfo[j].description) ? columnInfo[j].name + "-" + columnInfo[j].description : columnInfo[j].name;
						callbackArr.push({
							caption: caption,
							snippet: columnInfo[j].name,
							meta: "column",
							type: "snippet",
							score: 1000
						});
					}
					isFound = true;
				}
			}
		}
	}
}
