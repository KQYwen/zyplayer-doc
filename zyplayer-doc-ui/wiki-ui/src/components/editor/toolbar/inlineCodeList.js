import toolbarCommon from './common';
import utilBase from '../util/util';

const $ = require("jquery");

export default {
	handleCodeList() {
		let containerNow = window.getSelection().getRangeAt(0).commonAncestorContainer;
		let divEle = this.findRootDomOnNotCode(containerNow);
		if (divEle == null) return;
		divEle.classList.add("list-code");
		let codeEle = $(`<code start='1'></code>`)[0];
		$(divEle).append(codeEle);
		// 光标放到代码框里
		utilBase.moveSelectionRange(codeEle, 0, 0);
	},
	findRootDomOnNotCode(container) {
		container = container.nodeType == 3 ? container.parentNode : container;
		// 如果自己是root，创建一个div放最后
		if (toolbarCommon.isRootBox(container)) {
			let lastEle = document.createElement("div");
			container.appendChild(lastEle);
			return lastEle;
		}
		// 如果父元素是root，添加一个新的div，再把自己移除
		let parentNode = container.parentNode;
		if (toolbarCommon.isRootBox(parentNode)) {
			let lastEle = document.createElement("div");
			parentNode.insertBefore(lastEle, container);
			container.remove();
			return lastEle;
		}
		// 在非根结点里面输入```无效
		return null;
	},
}
