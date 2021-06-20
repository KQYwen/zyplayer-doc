import base from './base'
import sqlserver from './sqlserver'

export default {
	format(type, product, dataCols, choiceData, condition, dbName, tableName) {
		let formatter = this.getProduct(product);
		if (type === 'insert') {
			// 复制为insert语句
			return formatter.insert(dataCols, choiceData, dbName, tableName);
		} else if (type === 'update') {
			// 复制为update语句
			return formatter.update(dataCols, choiceData, condition, dbName, tableName);
		} else if (type === 'json') {
			// 复制为json
			return formatter.json(dataCols, choiceData, dbName, tableName);
		}
	},
	getProduct(product) {
		// 不同数据源类型可以用不用的处理器类型
		if (product === 'sqlserver') {
			return sqlserver;
		}
		return base;
	},
}
