import utilSelection from "../util/selection";

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
	selectionAddClass(cls) {
		let range = window.getSelection().getRangeAt(0);
		let nowContainer = range.startContainer;
		let newDomArr = [], innerText, newDom, haveEndDom = false;

		while (nowContainer != null) {
			innerText = nowContainer.data || nowContainer.innerText;
			if (nowContainer == range.startContainer && nowContainer == range.endContainer) {
				innerText = innerText.substring(range.startOffset, range.endOffset);
			} else if (nowContainer == range.startContainer) {
				innerText = innerText.substring(range.startOffset, innerText.length);
			} else if (nowContainer == range.endContainer || nowContainer.firstChild == range.endContainer) {
				innerText = innerText.substring(0, range.endOffset);
			}
			newDom = document.createElement("span");
			var classList = utilSelection.getRealElem(nowContainer).classList;
			if (classList) {
				newDom.classList = classList;
			}
			newDom.classList.add(cls);
			newDom.innerText = innerText;
			newDomArr.push(newDom);
			if (nowContainer == range.endContainer || nowContainer.firstChild == range.endContainer) {
				haveEndDom = true;
				break;
			}
			nowContainer = nowContainer.nextSibling || utilSelection.getRealElem(nowContainer).nextSibling;
		}
		if (!haveEndDom) {
			nowContainer = range.endContainer;
			innerText = nowContainer.data || nowContainer.innerText;
			innerText = innerText.substring(0, range.endOffset);
			newDom = document.createElement("span");
			if (nowContainer.classList) {
				newDom.classList = nowContainer.classList;
			}
			newDom.classList.add(cls);
			newDom.innerText = innerText;
			newDomArr.push(newDom);
		}
		let appendHtml = '';
		for (let i = 0; i < newDomArr.length; i++) {
			var item = newDomArr[i];
			if (i + 1 < newDomArr.length) {
				var itemNext = newDomArr[i + 1];
				if (itemNext.classList.value == item.classList.value) {
					i++;
					item.innerText += itemNext.innerText;
				}
			}
			let removeCls = [];
			item.classList.forEach(cls => {
				if (cls.startsWith("head-") || cls == 'head') {
					removeCls.push(cls);
				}
			});
			for (let j = 0; j < removeCls.length; j++) {
				item.classList.remove(removeCls[j]);
			}
			appendHtml += item.outerHTML;
		}
		document.execCommand('styleWithCSS', false, false);
		document.execCommand('insertHTML', false, appendHtml);
	},

}

