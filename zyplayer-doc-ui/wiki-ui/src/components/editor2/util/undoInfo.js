// 构造函数
function UndoInfo(type = 1, index = -1, before = '', after = '') {
	// 操作类型 1=修改 2=添加 3=删除
	this.type = type;
	// 数组下标
	this.index = index;
	// 修改前内容json
	this.before = before;
	// 修改后内容json
	this.after = after;
}

// 原型
UndoInfo.prototype = {
	constructor: UndoInfo,
};

export default UndoInfo;
