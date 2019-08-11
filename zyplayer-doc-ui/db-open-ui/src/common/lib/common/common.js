import Qs from 'qs'
import global from '../../config/global'
import apimix from '../../config/apimix'

export default {
	data: {
		accessToken: '',
	},
	setAccessToken: function (token) {
		this.data.accessToken = token;
	},
	getAccessToken: function () {
		if (!this.data.accessToken) {
			var arr, reg = new RegExp("(^| )accessToken=([^;]*)(;|$)");
			if (arr = document.cookie.match(reg)) {
				this.data.accessToken = unescape(arr[2]);
			}
		}
		return this.data.accessToken;
	},
	validateResult: function (res, callback) {
		if (!!res.message) {
			global.vue.$message('请求错误：' + res.message);
		} else if (res.data.errCode == 400) {
			global.vue.$message('请先登录');
			var href = encodeURIComponent(window.location.href);
			// if (global.vue.$router.currentRoute.path != '/user/login') {
				// global.vue.$router.push({path: '/user/login', query: {redirect: href}});
			// }
			window.location = apimix.apilist1.HOST + "#/user/login?redirect=" + href;
		} else if (res.data.errCode == 402) {
			global.vue.$router.push("/common/noAuth");
		} else if (res.data.errCode !== 200) {
			global.vue.$message(res.data.errMsg || "未知错误");
		} else {
			if (typeof callback == 'function') {
				callback(res.data);
			}
		}
	},
	post: function (url, param, callback) {
		param = param || {};
		param.accessToken = this.getAccessToken();
		global.vue.axios({
			method: "post",
			url: url,
			headers: {'Content-type': 'application/x-www-form-urlencoded'},
			data: Qs.stringify(param),
			withCredentials: true,
		}).then((res) => {
			console.log("ok", res);
			this.validateResult(res, callback);
		}).catch((res) => {
			console.log("error", res);
			this.validateResult(res);
		});
	},
	postNonCheck: function (url, param, callback) {
		param = param || {};
		param.accessToken = this.getAccessToken();
		global.vue.axios({
			method: "post",
			url: url,
			headers: {'Content-type': 'application/x-www-form-urlencoded'},
			data: Qs.stringify(param),
			withCredentials: true,
		}).then((res) => {
			console.log("ok", res);
			if (typeof callback == 'function') {
				callback(res.data);
			}
		}).catch((res) => {
			console.log("error", res);
			if (typeof callback == 'function') {
				callback(res.data);
			}
		});
	},
	/**
	 * 返回不为空的字符串，为空返回def
	 */
	getNotEmptyStr(str, def) {
		if (isEmpty(str)) {
			return isEmpty(def) ? "" : def;
		}
		return str;
	},
	/**
	 * 是否是空对象
	 * @param obj
	 * @returns
	 */
	isEmptyObject(obj) {
		return isEmpty(obj) || $.isEmptyObject(obj);
	},
	/**
	 * 是否是空字符串
	 * @param str
	 * @returns
	 */
	isEmpty(str) {
		return (str == "" || str == null || str == undefined);
	},
	/**
	 * 是否不是空字符串
	 * @param str
	 * @returns
	 */
	isNotEmpty(str) {
		return !isEmpty(str);
	},
}

