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
		let styleRangeNew = [];
		// 拆分
		for (let i = 0; i < this.styleRange.length; i++) {
			let item = this.styleRange[i];
			// 全选  前面  中间  后面  全包含
			if (this.startOffset == item.start && this.endOffset == item.end) {
				item.addClass(cls);
				styleRangeNew.push(item);
			} else if (this.startOffset == item.start && this.endOffset < item.end) {
				styleRangeNew.push(new StyleRange(this.startOffset, this.endOffset, item.cls + ' ' + cls));
				styleRangeNew.push(new StyleRange(this.endOffset, item.end, item.cls));
			} else if (this.startOffset > item.start && this.endOffset < item.end) {
				styleRangeNew.push(new StyleRange(item.start, this.startOffset, item.cls));
				styleRangeNew.push(new StyleRange(this.startOffset, this.endOffset, item.cls + ' ' + cls));
				styleRangeNew.push(new StyleRange(this.endOffset, item.end, item.cls));
			} else if (this.startOffset > item.start && this.endOffset >= item.end) {
				styleRangeNew.push(new StyleRange(0, this.startOffset, item.cls));
				styleRangeNew.push(new StyleRange(this.startOffset, item.end, item.cls + ' ' + cls));
			} else if (this.startOffset <= item.start && this.endOffset >= item.end) {
				if (this.startOffset < item.start) {
					styleRangeNew.push(new StyleRange(this.startOffset, item.start, cls));
				}
				item.addClass(cls);
				styleRangeNew.push(item);
				if (this.endOffset > item.end) {
					styleRangeNew.push(new StyleRange(item.end, this.endOffset, cls));
				}
			} else {
				styleRangeNew.push(item);
			}
		}
		if (styleRangeNew.length <= 0) {
			styleRangeNew.push(new StyleRange(this.startOffset, this.endOffset, cls));
		}
		styleRangeNew.sort((val1, val2) => val1.start - val2.start);
		// 合并同一个范围内样式表重叠的
		let styleRangeLast = [];
		let len = styleRangeNew.length;
		for (let i = 0; i < len; i++) {
			let item = styleRangeNew[i];
			for (let j = i + 1; j < len; j++, i++) {
				let itemNext = styleRangeNew[j];
				if (!item.classSameAll(itemNext)) break;
				item.start = Math.min(item.start, itemNext.start);
				item.end = Math.max(item.end, itemNext.end);
			}
			styleRangeLast.push(item);
		}
		this.styleRange = styleRangeLast;
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
