import vue from '../../main'

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
	validateResult: function (res) {
		return new Promise(function (resolve, reject) {
			if (!!res.message) {
				vue.$message('请求错误：' + res.message);
			} else if (res.data.errCode == 400) {
				vue.$message('请先登录');
				var href = encodeURIComponent(window.location.href);
				window.location = apimix.apilist1.HOST + "#/user/login?redirect=" + href;
			} else if (res.data.errCode == 402) {
				vue.$router.push("/common/noAuth");
			} else if (res.data.errCode !== 200) {
				vue.$message(res.data.errMsg || "未知错误");
			} else {
				resolve(res.data);
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
	/**
	 * param 将要转为URL参数字符串的对象
	 * key URL参数字符串的前缀
	 * encode true/false 是否进行URL编码,默认为true
	 * return URL参数字符串
	 */
	objUrlEncode(param, key, encode) {
		if (param == null) return '';
		var paramStr = '';
		var t = typeof (param);
		if (t == 'string' || t == 'number' || t == 'boolean') {
			paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
		} else {
			for (var i in param) {
				var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
				paramStr += urlEncode(param[i], k, encode);
			}
		}
		return paramStr;
	}
}

