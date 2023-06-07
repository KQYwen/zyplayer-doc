//节流函数（一定时间内只执行一次事件）
export function throttle(fn, delay) {
	let timer = null;
	return function() {
		const context = this;
		const args = arguments;
		if (!timer) {
			timer = setTimeout(function() {
				fn.apply(context, args);
				timer = null;
			}, delay);
		}
	}
}
//防抖函数（一定时间内只执行最后一次事件）
export function debounce(fn, delay) {
	let timer = null;
	return function() {
		const context = this;
		const args = arguments;
		if (timer) {
			clearTimeout(timer);
		}
		timer = setTimeout(function() {
			fn.apply(context, args);
			timer = null;
		}, delay);
	}
}
