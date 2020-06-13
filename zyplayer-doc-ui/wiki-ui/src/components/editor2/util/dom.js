// 构造函数
import TextStyle from "./textStyle";
import StyleRange from "./styleRange";

function Dom(type = 'text', cls = '', text = '') {
	this.type = type;
	this.text = text;
	this.target = '';
	this.cls = cls;
	this.clsSet = new Set(cls.split(" "));
	this.startOffset = -1;
	this.endOffset = -1;
	this.dom = [];
	this.textStyle = [];
	// 一个范围的样式，例：{start: 1, end: 2, class: 'xx xxx'}
	this.styleRange = [];
}

// 原型
Dom.prototype = {
	constructor: Dom,
	// 删除class
	removeClass(cls) {
		if (!this.hasClass(cls)) return;
		this.clsSet.delete(cls);
		this.cls = Array.from(this.clsSet).join(" ");
	},
	hasClass(cls) {
		return this.clsSet.has(cls);
	},
	addClass(cls) {
		if (this.hasClass(cls)) return;
		this.clsSet.add(cls);
		this.cls = Array.from(this.clsSet).join(" ");
	},
	setOffset(start, end) {
		this.startOffset = start;
		this.endOffset = end;
	},
	setOffsetAll() {
		this.startOffset = 0;
		this.endOffset = this.text.length;
	},
	addText(startOffset, data) {
		if (!data) return;
		// todo 删除选中的内容
		// 如果在某个样式范围内，则加入
		for (let i = 0; i < this.styleRange.length; i++) {
			let item = this.styleRange[i];
			if (startOffset >= item.start && startOffset < item.end) {
				item.end += data.length;
			}
		}
		if (startOffset < this.text.length) {
			this.text = this.text.substring(0, startOffset) + data + this.text.substring(startOffset, this.text.length);
		} else {
			this.text = this.text + data;
		}
		this.computerStyleRangeToDom();
	},
	addSelectionTextStyle(cls) {
		if (this.startOffset < 0 || this.endOffset < 0 || this.startOffset == this.endOffset) {
			return;
		}
		// 添加新的分区
		let styleRangeNew = [].concat(this.styleRange);
		styleRangeNew.push(new StyleRange(this.startOffset, this.endOffset, cls));
		let textStyleArr = [];
		let textLen = this.text.length;
		for (let i = 0; i < textLen; i++) {
			textStyleArr[i] = '';
		}
		// 所有范围值拆分到每个字
		let rangeLen = styleRangeNew.length;
		for (let i = 0; i < rangeLen; i++) {
			let item = styleRangeNew[i];
			for (let j = item.start; j < item.end && j < textLen; j++) {
				textStyleArr[j] += ' ' + item.cls;
			}
		}
		// 去重再排序，获取排序后的字符串
		for (let i = 0; i < textLen; i++) {
			if (!!textStyleArr[i]) {
				let clsSet = new Set(textStyleArr[i].split(" "));
				textStyleArr[i] = Array.from(clsSet).sort((val1, val2) => val1 - val2).join(" ");
			}
		}
		// 合并同一个范围内样式表重叠的
		let styleRangeMerged = [];
		for (let i = 0; i < textLen; i++) {
			if (!!textStyleArr[i]) {
				let start = i;
				for (let j = i + 1; j < textLen; j++, i++) {
					if (textStyleArr[i] !== textStyleArr[j]) break;
				}
				styleRangeMerged.push(new StyleRange(start, i + 1, textStyleArr[i]));
			}
		}
		this.styleRange = styleRangeMerged;
		this.startOffset = this.endOffset = -1;
		this.computerStyleRangeToDom();
	},
	computerStyleRangeToDom() {
		// 把样式表渲染为dom列表
		this.dom = [];
		let lastStart = 0;
		for (let i = 0; i < this.styleRange.length; i++) {
			let item = this.styleRange[i];
			if (lastStart < item.start) {
				this.dom.push(new Dom('', '', this.text.substring(lastStart, item.start)));
			}
			let text = this.text.substring(item.start, item.end);
			this.dom.push(new Dom('', item.cls, text));
			lastStart = item.end;
		}
		if (lastStart < this.text.length) {
			this.dom.push(new Dom('', '', this.text.substring(lastStart, this.text.length)));
		}
	},
	// 回车事件处理
	keyEnter(editorDom, editorRange) {
		let nextText = '';
		let oldText = this.text || '';
		// 如果文字的中间位置点击，则把内容分割到两行
		if (editorRange.startOffset < oldText.length) {
			this.text = oldText.substring(0, editorRange.startOffset);
			nextText = oldText.substring(editorRange.startOffset, oldText.length);
		}
		for (let i = 0; i < editorDom.length; i++) {
			if (this == editorDom[i]) {
				editorDom.splice(i + 1, 0, new Dom('text', this.cls, nextText));
				break;
			}
		}
	},
};

export default Dom;
