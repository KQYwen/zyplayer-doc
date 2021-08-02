/**
 * 通用的转换器，如果通用的不能满足需要添加转换器实现转换
 * @author 暮光：城中城
 * @since 2021年5月23日
 */
export default {
	insert(dataCols, choiceData, dbName, tableName) {
		let tableNameRes = (!!dbName) ? dbName + '.' : '';
		tableNameRes += (!!tableName) ? tableName : 'table';
		// 复制为insert语句
		let copyData = '';
		let names = '';
		dataCols.forEach(col => {
			if (names.length > 0) names += ', ';
			names += col.prop;
		});
		choiceData.forEach(item => {
			let values = '';
			dataCols.forEach(col => {
				if (values.length > 0) values += ', ';
				let val = item[col.prop] || '';
				if (typeof val === 'number' && !isNaN(val)) {
					values += val;
				} else {
					val = String(val).replaceAll('\'', '\'\'');
					values += "'" + val + "'";
				}
			});
			copyData += 'insert into ' + tableNameRes + ' (' + names + ') values (' + values + ');\n';
		});
		return copyData;
	},
	update(dataCols, choiceData, condition=[], dbName, tableName) {
		let tableNameRes = (!!dbName) ? dbName + '.' : '';
		tableNameRes += (!!tableName) ? tableName : 'table';
		// 复制为update语句
		let copyData = '';
		choiceData.forEach(item => {
			let values = '', where = '';
			dataCols.forEach(col => {
				let val = item[col.prop] || '';
				if (condition.indexOf(col.prop) >= 0) {
					if (typeof val === 'number' && !isNaN(val)) {
						if (where.length > 0) where += ' and ';
						where += col.prop + ' = ' + val;
					} else {
						if (where.length > 0) where += ' and ';
						where += col.prop + ' = ' + "'" + val + "'";
					}
				} else {
					if (values.length > 0) values += ', ';
					values += col.prop + '=';
					if (typeof val === 'number' && !isNaN(val)) {
						values += val;
					} else {
						val = String(val).replaceAll('\'', '\'\'');
						values += "'" + val + "'";
					}
				}
			});
			if (where.length > 0) where = ' where ' + where;
			copyData += 'update ' + tableNameRes + ' set ' + values + where + ';\n';
		});
		return copyData;
	},
	json(dataCols, choiceData, dbName, tableName) {
		// 复制为json
		return JSON.stringify(choiceData);
	},
}
