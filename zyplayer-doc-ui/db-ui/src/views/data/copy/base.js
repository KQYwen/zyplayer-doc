/**
 * 通用的转换器，如果通用的不能满足需要添加转换器实现转换
 * @author 暮光：城中城
 * @since 2021年5月23日
 */
export default {
	insert(dataCols, choiceData) {
		// 复制为insert语句
		let copyData = '';
		let names = '';
		dataCols.forEach(col => {
			if (names.length > 0) names += ', '
			names += col.prop;
		});
		choiceData.forEach(item => {
			let values = '';
			dataCols.forEach(col => {
				if (values.length > 0) values += ', ';
				let val = item[col.prop];
				if (typeof val === 'number' && !isNaN(val)) {
					values += val;
				} else {
					val = val.replaceAll('\'', '\'\'');
					values += "'" + val + "'";
				}
			});
			copyData += 'insert into `table` (' + names + ') values (' + values + ');\n';
		});
		return copyData;
	},
	update(dataCols, choiceData) {
		// 复制为update语句
		let copyData = '';
		choiceData.forEach(item => {
			let values = '', where = '';
			dataCols.forEach(col => {
				if (values.length > 0) values += ', ';
				values += col.prop + '=';
				let val = item[col.prop];
				if (typeof val === 'number' && !isNaN(val)) {
					values += val;
					if (col.prop === 'id') where = ' where id = ' + val;
				} else {
					val = val.replaceAll('\'', '\'\'');
					values += "'" + val + "'";
				}
			});
			copyData += 'update `table` set ' + values + where + ';\n';
		});
		return copyData;
	},
	json(dataCols, choiceData) {
		// 复制为json
		return JSON.stringify(choiceData);
	},
}
