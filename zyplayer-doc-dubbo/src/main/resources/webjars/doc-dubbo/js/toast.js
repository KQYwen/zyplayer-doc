/**
 * 提示工具类
 * @author 暮光：城中城
 * @since 2017年5月7日
 */
var Toast = {
	notOpen: function () {
		app.$message({
			message: '该功能暂未开放，敬请期待！',
			type: 'warning',
			showClose: true
		});
	},
	success: function (msg, time) {
		app.$message({
			message: msg,
			duration: time || 3000,
			type: 'success',
			showClose: true
		});
	},
	warn: function (msg, time) {
		app.$message({
			message: msg,
			duration: time || 3000,
			type: 'warning',
			showClose: true
		});
	},
	error: function (msg, time) {
		app.$message({
			message: msg,
			duration: time || 3000,
			type: 'error',
			showClose: true
		});
	},
};
