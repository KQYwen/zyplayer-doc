import base from './base'

export default {
	format(type, product, dataCols, choiceData) {
		let formatter = this.getProduct(product);
		if (type === 'insert') {
			// 复制为insert语句
			return formatter.insert(dataCols, choiceData);
		} else if (type === 'update') {
			// 复制为update语句
			return formatter.update(dataCols, choiceData);
		} else if (type === 'json') {
			// 复制为json
			return formatter.json(dataCols, choiceData);
		}
	},
	getProduct(product) {
		if (product === 'mysql') {
			// 不同数据源类型可以用不用的处理器类型，暂时只实现了一套基础的
		}
		return base;
	},
}
