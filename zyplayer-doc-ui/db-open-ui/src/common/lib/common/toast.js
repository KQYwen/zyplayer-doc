import global from '../../config/global'

/**
 * 提示工具类
 * @author
 * @since 2017年5月7日
 */
export default {
	notOpen: function () {
		global.vue.$message({
			message: '该功能暂未开放，敬请期待！',
			type: 'warning',
			showClose: true
		});
	},
	success: function (msg, time) {
		global.vue.$message({
			message: msg,
			duration: time || 3000,
			type: 'success',
			showClose: true
		});
	},
	warn: function (msg, time) {
		global.vue.$message({
			message: msg,
			duration: time || 3000,
			type: 'warning',
			showClose: true
		});
	},
	error: function (msg, time) {
		global.vue.$message({
			message: msg,
			duration: time || 3000,
			type: 'error',
			showClose: true
		});
	},
};
