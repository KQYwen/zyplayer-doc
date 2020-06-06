/**
 * 本文件内容拷贝自：https://github.com/wangfupeng1988/wangEditor
 * zyplayer-doc在此基础上有稍作修改
 */

import utilBase from './util'

export default {
	// 获取粘贴的纯文本
	getPasteText(e) {
		const clipboardData = e.clipboardData || (e.originalEvent && e.originalEvent.clipboardData);
		let pasteText;
		if (clipboardData == null) {
			pasteText = window.clipboardData && window.clipboardData.getData('text');
		} else {
			pasteText = clipboardData.getData('text/plain');
		}
		let resultStr = '';
		let htmlStr = utilBase.replaceHtmlSymbol(pasteText);
		htmlStr.split('<br/>').forEach(item => {
			if (resultStr) resultStr += '<br/>';
			resultStr += item.trim();
		});
		return resultStr;
	},
	// 获取粘贴的html
	getPasteHtml(e, filterStyle, ignoreImg) {
		const clipboardData = e.clipboardData || (e.originalEvent && e.originalEvent.clipboardData);
		let pasteText, pasteHtml;
		if (clipboardData == null) {
			pasteText = window.clipboardData && window.clipboardData.getData('text');
		} else {
			pasteText = clipboardData.getData('text/plain');
			pasteHtml = clipboardData.getData('text/html');
		}
		if (!pasteHtml && pasteText) {
			pasteHtml = utilBase.replaceHtmlSymbol(pasteText);
		}
		if (!pasteHtml) {
			return;
		}
		// 过滤word中状态过来的无用字符
		const docSplitHtml = pasteHtml.split('</html>');
		if (docSplitHtml.length === 2) {
			pasteHtml = docSplitHtml[0];
		}
		// 过滤无用标签
		pasteHtml = pasteHtml.replace(/<(meta|script|link).+?>/igm, '');
		// 去掉注释
		pasteHtml = pasteHtml.replace(/<!--.*?-->/mg, '');
		// 过滤 data-xxx 属性
		pasteHtml = pasteHtml.replace(/\s?data-.+?=('|").+?('|")/igm, '');
		if (ignoreImg) {
			// 忽略图片
			pasteHtml = pasteHtml.replace(/<img.+?>/igm, '');
		}
		if (filterStyle) {
			// 过滤样式
			pasteHtml = pasteHtml.replace(/\s?(class|style)=('|").*?('|")/igm, '');
		} else {
			// 保留样式
			pasteHtml = pasteHtml.replace(/\s?class=('|").*?('|")/igm, '');
		}
		return pasteHtml
	},
	// 获取粘贴的图片文件
	getPasteImgs(e) {
		const result = [];
		const txt = utilBase.getPasteText(e);
		if (txt) {
			// 有文字，就忽略图片
			return result;
		}
		const clipboardData = e.clipboardData || (e.originalEvent && e.originalEvent.clipboardData) || {};
		const items = clipboardData.items;
		if (!items) {
			return result;
		}
		utilBase.objForEach(items, (key, value) => {
			const type = value.type;
			if (/image/i.test(type)) {
				result.push(value.getAsFile());
			}
		});
		return result;
	},
}
