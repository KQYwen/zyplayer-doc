export default function applyOnce(fn, timeout) {
	let inCall = false;
	let lastParam = null;
	return function (param) {
		lastParam = param;
		if (inCall) return;
		inCall = true;
		setTimeout(() => {
			inCall = false;
			fn(lastParam);
		}, timeout);
	}
};
