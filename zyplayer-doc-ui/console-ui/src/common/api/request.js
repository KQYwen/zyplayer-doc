import axios from 'axios'
import vue from '../../main'

const service = axios.create({
	baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url process.env.APP_BASE_API
	timeout: 10000,
	headers: {'Content-type': 'application/x-www-form-urlencoded'},
	withCredentials: true
});
// 增加不需要验证结果的标记
const noValidate = {
	"/zyplayer-doc-db/executor/execute": true,
	"/zyplayer-doc-db/datasource/test": true,
};
let lastToastLoginTime = 0;

service.interceptors.request.use(
	config => {
		config.needValidateResult = true;
		// 增加不需要验证结果的标记
		if (noValidate[config.url]) {
			config.needValidateResult = false;
		}
		return config
	},
	error => {
		console.log(error);
		return Promise.reject(error);
	}
);

service.interceptors.response.use(
	response => {
		if (!!response.message) {
			vue.$message.error('请求错误：' + response.message);
		}else {
			if (!response.config.needValidateResult || response.data.errCode == 200) {
				return response.data;
			} else if (response.data.errCode == 400) {
				// 一秒钟只提示一次
				if (new Date().getTime() - lastToastLoginTime > 1000) {
					vue.$message.error('请先登录');
					lastToastLoginTime = new Date().getTime();
				}
				let redirectUrl = '';
				let locationHref = window.location.href;
				if (locationHref.indexOf("?") >= 0) {
					let reg = new RegExp("(^|&)redirect=([^&]*)(&|$)", "i");
					let r = locationHref.substring(locationHref.indexOf("?") + 1).match(reg);
					if (r != null) {
						redirectUrl = unescape(r[2]);
					}
				}
				redirectUrl = redirectUrl || encodeURIComponent(window.location.href);
				vue.$router.push({path: '/user/login', query: {redirect: redirectUrl}});
			} else if (response.data.errCode == 402) {
				vue.$router.push("/common/noAuth");
			} else if (response.data.errCode !== 200) {
				vue.$message.error(response.data.errMsg || "未知错误");
			}
		}
		return Promise.reject('请求错误');
	},
	error => {
		console.log('err' + error);
		vue.$message.info('请求错误：' + error.message);
		return Promise.reject(error)
	}
);
export default service;
