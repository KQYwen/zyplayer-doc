// 构造函数

import UndoInfo from "./undoInfo";
import Dom from "./dom";
import vue from '../../../main'

function UndoRedo(editorDom) {
	this.editorDom = editorDom;
	this.undoRedoList = [];
	this.undoRedoIndex = -1;
}

// 原型
UndoRedo.prototype = {
	constructor: UndoRedo,
	execute(type, index, before, after) {
		// 忽略后面的操作步骤
		if (this.undoRedoIndex >= 0 && this.undoRedoIndex < this.undoRedoList.length - 1) {
			this.undoRedoList.splice(this.undoRedoIndex, this.undoRedoList.length - this.undoRedoIndex);
		}
		// 最多保留50步
		if (this.undoRedoList.length >= 50) {
			this.undoRedoList.splice(0, 1);
		}
		// 处理下，只保留有用的字段
		before = this.handleDomColumn(before);
		after = this.handleDomColumn(after);
		this.undoRedoList.push(new UndoInfo(type, index, before, after));
		this.undoRedoIndex = this.undoRedoList.length - 1;
	},
	handleDomColumn(content) {
		if (!content) return '';
		let beforeObj = [];
		if (content instanceof Array) {
			content.forEach(item => {
				beforeObj.push({type: item.type, cls: item.cls, text: item.text, styleRange: item.styleRange});
			});
		} else {
			beforeObj.push({type: content.type, cls: content.cls, text: content.text, styleRange: content.styleRange});
		}
		return JSON.stringify(beforeObj);
	},
	undo() {
		if (this.undoRedoIndex >= this.undoRedoList.length) {
			this.undoRedoIndex = this.undoRedoList.length - 1;
		}
		if (this.undoRedoIndex < 0) {
			return;
		}
		let undoInfo = this.undoRedoList[this.undoRedoIndex];
		let changeContent = JSON.parse(undoInfo.before);
		let undoIndex = undoInfo.index;
		changeContent.forEach(item => {
			this.undoObjDomToEditor(undoInfo, undoIndex, item);
			undoIndex--;
		});
		this.undoRedoIndex = Math.max(this.undoRedoIndex - 1, -1);
	},
	redo() {
		this.undoRedoIndex++;
		if (this.undoRedoIndex < 0 || this.undoRedoIndex >= this.undoRedoList.length) {
			this.undoRedoIndex--;
			return;
		}
		let undoInfo = this.undoRedoList[this.undoRedoIndex];
		let actionText = (undoInfo.type == 1) ? undoInfo.after : undoInfo.before;
		let changeContent = JSON.parse(actionText);
		let undoIndex = undoInfo.index;
		changeContent.forEach(item => {
			this.redoObjDomToEditor(undoInfo, undoIndex, item);
			undoIndex++;
		});
	},
	redoObjDomToEditor(undoInfo, undoIndex, domObj) {
		let dom = new Dom(domObj.type, domObj.cls, domObj.text, domObj.styleRange);
		if (undoInfo.type == 1) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoIndex) {
				vue.$set(this.editorDom, undoIndex, dom);
			}
		} else if (undoInfo.type == 2) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length == undoIndex) {
				this.editorDom.push(dom);
			} else if (this.editorDom.length > undoIndex) {
				this.editorDom.splice(undoIndex, 0, dom);
			}
		} else if (undoInfo.type == 3) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoIndex) {
				this.editorDom.splice(undoIndex, 1);
			}
		}
	},
	undoObjDomToEditor(undoInfo, undoIndex, domObj) {
		let dom = new Dom(domObj.type, domObj.cls, domObj.text, domObj.styleRange);
		if (undoInfo.type == 1) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoIndex) {
				vue.$set(this.editorDom, undoIndex, dom);
			}
		} else if (undoInfo.type == 2) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoIndex) {
				this.editorDom.splice(undoIndex, 1);
			}
		} else if (undoInfo.type == 3) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length == undoIndex) {
				this.editorDom.push(dom);
			} else if (this.editorDom.length > undoIndex) {
				this.editorDom.splice(undoIndex, 0, dom);
			}
		}
	},
};

export default UndoRedo;
