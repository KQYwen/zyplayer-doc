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
	isRootBox(container) {
		return this.domHaveClass(container, "mg-editor");
	},
	domHaveClass(container, cls) {
		return container && container.classList && container.classList.contains(cls);
	},
}

