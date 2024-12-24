import axios from 'axios';
import router from '@/routes.js'
import {ElMessageBox, ElMessage} from 'element-plus';

const service = axios.create({
	baseURL: import.meta.env.VITE_APP_BASE_API, // url = base url + request url process.env.APP_BASE_API
	timeout: 10000,
	headers: {'Content-type': 'application/x-www-form-urlencoded'},
	withCredentials: true,
});
// 增加不需要验证结果的标记
const noValidate = {
	'/zyplayer-doc-db/executor/execute': true,
}

service.interceptors.request.use((config) => {
		config.needValidateResult = true;
		// 增加不需要验证结果的标记
		if (noValidate[config.url]) {
			config.needValidateResult = false;
		}
		return config;
	}, (error) => {
		console.log(error);
		return Promise.reject(error);
	}
);
service.interceptors.response.use(
	(response) => {
		if (!!response.message) {
			ElMessage.error('请求错误：' + response.message);
		} else {
			if (!response.config.needValidateResult || response.data.errCode === 200) {
				return response.data;
			} else if (response.data.errCode === 400) {
				let redirectUrl = getRedirectUrl();
				router.push({path: `/user/login`, query: {redirect: redirectUrl}});
				return Promise.reject(response.data);
			} else if (response.data.errCode !== 200) {
				ElMessage.error(response.data.errMsg || '未知错误');
			}
		}
		return Promise.reject('请求错误');
	}, (error) => {
		console.log('err' + error);
		ElMessage.info('请求错误：' + error.message);
		return Promise.reject(error);
	}
);
function getRedirectUrl() {
	let redirectUrl = '';
	let locationHref = window.location.href;
	if (locationHref.indexOf('?') >= 0) {
		let reg = new RegExp('(^|&)redirect=([^&]*)(&|$)', 'i');
		let r = locationHref.substring(locationHref.indexOf('?') + 1).match(reg);
		if (r != null) {
			redirectUrl = unescape(r[2]);
		}
	}
	return redirectUrl || encodeURIComponent(window.location.href);
}
export default service
