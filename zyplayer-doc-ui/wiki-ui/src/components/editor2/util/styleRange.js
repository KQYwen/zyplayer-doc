// 构造函数
function StyleRange(start, end, cls = '') {
	cls = cls.trim();
	this.start = start;
	this.end = end;
	this.cls = cls;
	this.clsSet = new Set(cls.split(" "));
}

// 原型
StyleRange.prototype = {
	constructor: StyleRange,
	// 删除class
	removeClass(cls = '') {
		cls = cls.trim();
		if (!this.hasClass(cls)) return;
		this.clsSet.delete(cls);
		this.cls = Array.from(this.clsSet).join(" ");
	},
	hasClass(cls = '') {
		cls = cls.trim();
		return this.clsSet.has(cls);
	},
	addClass(cls = '') {
		cls = cls.trim();
		if (this.hasClass(cls)) return;
		this.clsSet.add(cls);
		this.cls = Array.from(this.clsSet).join(" ");
	},
	getSortClass() {
		return Array.from(this.clsSet).sort((val1, val2) => val1 - val2).join(" ");
	},
	classSameAll(compare) {
		if (compare.clsSet.size != this.clsSet.size) return false;
		let values = Array.from(compare.clsSet);
		for (let i = 0; i < values.length; i++) {
			if (!this.clsSet.has(values[i])) {
				return false;
			}
		}
		return true;
	},
};

export default StyleRange;
