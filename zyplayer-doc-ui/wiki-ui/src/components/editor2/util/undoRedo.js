// 构造函数

import UndoInfo from "./undoInfo";
import Dom from "./dom";

function UndoRedo(editorDom) {
	this.editorDom = editorDom;
	this.undoRedoList = [];
	this.undoRedoIndex = -1;
}

// 原型
UndoRedo.prototype = {
	constructor: UndoRedo,
	execute(type, index, before, after) {
		// 最多保留20步
		if (this.undoRedoList.length >= 20) {
			this.undoRedoList.splice(0, 1);
		}
		this.undoRedoList.push(new UndoInfo(type, index, before, after));
		this.undoRedoIndex = this.undoRedoList.length - 1;
	},
	undo() {
		if (this.undoRedoIndex < 0 || this.undoRedoIndex >= this.undoRedoList.length) {
			this.undoRedoIndex = this.undoRedoList.length - 1;
		}
		if (this.undoRedoIndex < 0) {
			return;
		}
		let undoInfo = this.undoRedoList[this.undoRedoIndex];
		let changeContent = JSON.parse(undoInfo.before);
		if (changeContent instanceof Array) {
			changeContent.forEach(item => {
				this.undoObjDomToEditor(undoInfo, item);
			});
		} else {
			this.undoObjDomToEditor(undoInfo, changeContent);
		}
		this.undoRedoIndex = Math.max(this.undoRedoIndex - 1, 0);
	},
	redo() {
		this.undoRedoIndex++;
		if (this.undoRedoIndex < 0 || this.undoRedoIndex >= this.undoRedoList.length) {
			this.undoRedoIndex--;
			return;
		}
		let undoInfo = this.undoRedoList[this.undoRedoIndex];
		let changeContent = JSON.parse(undoInfo.after);
		if (changeContent instanceof Array) {
			changeContent.forEach(item => {
				this.redoObjDomToEditor(undoInfo, item);
			});
		} else {
			this.redoObjDomToEditor(undoInfo, changeContent);
		}
	},
	redoObjDomToEditor(undoInfo, domObj) {
		let dom = new Dom(domObj.type, domObj.cls, domObj.text, domObj.styleRange);
		if (undoInfo.type == 1) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoInfo.index) {
				this.editorDom[undoInfo.index] = dom;
			}
		} else if (undoInfo.type == 2) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoInfo.index) {
				if (this.editorDom.length == undoInfo.index) {
					this.editorDom.push(dom);
				} else if (this.editorDom.length > undoInfo.index) {
					this.editorDom.splice(undoInfo.index, 0, dom);
				}
			}
		} else if (undoInfo.type == 3) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoInfo.index) {
				this.editorDom.splice(undoInfo.index, 1);
			}
		}
	},
	undoObjDomToEditor(undoInfo, domObj) {
		let dom = new Dom(domObj.type, domObj.cls, domObj.text, domObj.styleRange);
		if (undoInfo.type == 1) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoInfo.index) {
				this.editorDom[undoInfo.index] = dom;
			}
		} else if (undoInfo.type == 2) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length > undoInfo.index) {
				this.editorDom.splice(undoInfo.index, 1);
			}
		} else if (undoInfo.type == 3) {
			// 1=修改 2=添加 3=删除
			if (this.editorDom.length == undoInfo.index) {
				this.editorDom.push(dom);
			} else if (this.editorDom.length > undoInfo.index) {
				this.editorDom.splice(undoInfo.index, 0, dom);
			}
		}
	},
};

export default UndoRedo;
