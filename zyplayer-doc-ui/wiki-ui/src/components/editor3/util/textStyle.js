// 构造函数

function TextStyle() {
	this.hn = '';// hn 标题，1~9
	this.strikeThrough = 0;// 删除线 1=有
	this.backColor = '';// 背景色，颜色值
	this.underline = 0;// 下划线 1=有
	this.align = 0;// 对齐 1=左 2=中 3=右
	this.bold = 0;// 加粗 1=有
}

// 原型
TextStyle.prototype = {
	constructor: TextStyle,
	notEq(compare) {
		return !this.eq(compare);
	},
	eq(compare) {
		if (!compare) return false;
		if (this == compare) return true;
		return this.hn == compare.hn
			&& this.strikeThrough == compare.strikeThrough
			&& this.backColor == compare.backColor
			&& this.underline == compare.underline
			&& this.align == compare.align
			&& this.bold == compare.bold;
	},
	getCls() {
		let clsSet = new Set();
		if (this.hn) clsSet.add('head').add('head-' + this.hn);
		if (this.strikeThrough) clsSet.add('strikethrough');
		if (this.backColor) clsSet.add('backcolor-' + this.backColor);
		if (this.underline) clsSet.add('underline');
		if (this.align) clsSet.add('align-' + this.align);
		if (this.bold) clsSet.add('bold');
		return Array.from(clsSet).join(" ");
	},
	clone() {
		let result = new TextStyle();
		result.hn = this.hn;// hn 标题，1~9
		result.strikeThrough = this.strikeThrough;// 删除线 1=有
		result.backColor = this.backColor;// 背景色，颜色值
		result.underline = this.underline;// 下划线 1=有
		result.align = this.align;// 对齐 1=左 2=中 3=右
		result.bold = this.bold;// 加粗 1=有
		return result;
	},
};

export default TextStyle;
