
export default {
	getSelectionContainer(judgeRoot) {
		let container = window.getSelection().getRangeAt(0).commonAncestorContainer;
		if (container.nodeType != 1) {
			container = container.parentNode;
		}
		if (container.nodeType != 1 || (judgeRoot && this.isRootBox(container))) {
			return null;
		}
		return container;
	},
	getRootDom(nowDom) {
		let newDom = this.getRealElem(nowDom);
		if (!newDom || this.isRootBox(nowDom)) return null;
		if (this.isRootBox(newDom.parentNode)) return newDom;
		return this.getRootDom(newDom.parentNode);
	},
	getRealElem: function (elem) {
		return !elem || elem.nodeType === 1 ? elem : elem.parentNode;
	},
	isRootBox(container) {
		return this.domHaveClass(container, "mg-editor");
	},
	domHaveClass(container, cls) {
		return container && container.classList && container.classList.contains(cls);
	},

}

