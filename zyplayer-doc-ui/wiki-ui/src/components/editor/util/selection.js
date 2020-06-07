/**
 * 本文件内容拷贝自：https://github.com/wangfupeng1988/wangEditor
 * zyplayer-doc在此基础上有稍作修改
 */

export default {
	// 选区是否为空
	isSelectionEmpty: function () {
		let range = window.getSelection().getRangeAt(0);
		if (range && range.startContainer) {
			if (range.startContainer === range.endContainer) {
				if (range.startOffset === range.endOffset) {
					return true;
				}
			}
		}
		return false;
	},
	getRealElem: function (elem) {
		return !elem || elem.nodeType === 1 ? elem : elem.parentNode;
	},
}
