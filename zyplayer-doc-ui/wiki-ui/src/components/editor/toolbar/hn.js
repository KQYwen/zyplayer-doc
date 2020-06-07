import toolbarCommon from './common';
import utilSelection from "../util/selection";

const $ = require("jquery");

export default {
	handleHn(hn) {
		// 找到内容，生成div把每一行包住
		// let container = toolbarCommon.getSelectionContainer(true);
		// if (container == null) return;
		// let innerTextArr = container.innerText.split("\n");
		// if (innerTextArr.length >= 1) {
		// 	innerTextArr.filter(item => !!item).forEach(item => {
		// 		$(container).before(`<div class="head head-${hn}">${item}</div>`);
		// 	});
		// }
		// // 最后把当前行移出掉
		// container.remove();
		let range = window.getSelection().getRangeAt(0);
		let nowContainer = range.startContainer;
		while (nowContainer != null) {
			let rootDom = toolbarCommon.getRootDom(nowContainer);
			if (rootDom == null) return;
			rootDom.classList.forEach(cls => {
				if (cls.startsWith("head-")) {
					rootDom.classList.remove(cls);
				}
			});
			rootDom.classList.add('head', 'head-' + hn);
			if (nowContainer == range.endContainer || nowContainer.firstChild == range.endContainer) {
				break;
			}
			nowContainer = nowContainer.nextSibling || utilSelection.getRealElem(nowContainer).nextSibling;
		}
		document.execCommand('styleWithCSS', false, false);
	}
}

