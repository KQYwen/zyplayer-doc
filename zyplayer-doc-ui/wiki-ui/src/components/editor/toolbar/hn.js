import toolbarCommon from './common';

const $ = require("jquery");

export default {
	toolbarHn(hn) {
		// 找到内容，生成div把每一行包住
		let container = toolbarCommon.getSelectionContainer(true);
		if (container == null) return;
		let innerTextArr = container.innerText.split("\n");
		if (innerTextArr.length >= 1) {
			innerTextArr.filter(item => !!item).forEach(item => {
				$(container).before(`<div class="head head-${hn}">${item}</div>`);
			});
		}
		// 最后把当前行移出掉
		container.remove();
	}
}

