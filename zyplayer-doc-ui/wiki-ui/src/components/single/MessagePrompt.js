import { createVNode, render } from "vue";
import PromptDialog from "./PromptDialog.vue";

/**
 * 生成一个带输入框的弹窗
 * @author zyplayer-doc
 * @date 2024-12-08
 */
let promise = null;
function getInstance(config) {
	if (promise) {
		try {
			promise.close();
		} catch (error) {}
	}
	const div = document.createElement("div");
	const conf = Object.assign({cancelText: '取消', okText: '确定'}, config);
	const close = () => {
		try {
			document.body.removeChild(div);
			promise.reject();
			promise = null;
		} catch (error) {}
	};
	conf.close = close;
	conf.ok = (value) => {
		promise.resolve(value);
	};
	const node = createVNode(PromptDialog, conf);
	render(node, div);
	document.body.appendChild(div);
	return new Promise((resolve, reject) => {
		promise = {resolve, reject, close};
	});
}
const MessagePrompt = (opts) => {
	return getInstance(opts);
};

export default MessagePrompt;

