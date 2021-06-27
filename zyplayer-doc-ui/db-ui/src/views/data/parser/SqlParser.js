/**
 * 从字符串中找到以开始和结束字符包含的字符串数组
 * @author 暮光：城中城
 * @param text 待查找字符串
 * @param openToken 开始符号
 * @param closeToken 结束符号
 * @returns {[]} 结果
 */
export default {
	parser(text, openToken, closeToken) {
		let expressionArr = [];
		if (!text) {
			return expressionArr;
		}
		let start = text.indexOf(openToken, 0);
		if (start == -1) {
			return expressionArr;
		}
		let offset = 0;
		let expression = '';
		while (start > -1) {
			if (start > 0 && text[start - 1] == '\\') {
				offset = start + openToken.length;
			} else {
				expression = '';
				offset = start + openToken.length;
				let end = text.indexOf(closeToken, offset);
				while (end > -1) {
					if (end > offset && text[end - 1] == '\\') {
						expression += text.substr(offset, end - offset - 1);
						offset = end + closeToken.length;
						end = text.indexOf(closeToken, offset);
					} else {
						expression += text.substr(offset, end - offset);
						offset = end + closeToken.length;
						break;
					}
				}
				if (end == -1) {
					offset = text.length;
				} else {
					if (!!expression) {
						expressionArr.push(expression);
					}
					offset = end + closeToken.length;
				}
			}
			start = text.indexOf(openToken, offset);
		}
		return expressionArr;
	},
	parserArr(text, rules) {
		let expressionObj = {};
		rules.forEach(rule => {
			let expression = this.parser(text, rule.start, rule.end);
			expression.forEach(item => expressionObj[item] = 1);
		});
		let expressionArr = [];
		for (let key in expressionObj) {
			expressionArr.push(key);
		}
		return expressionArr;
	}
}
