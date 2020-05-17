import axios from 'axios'
import global from '../../config/global'
import apimix from '../../config/apimix'

const service = axios.create({
	baseURL: apimix.host, // url = base url + request url process.env.APP_BASE_API
	timeout: 10000,
	headers: {'Content-type': 'application/x-www-form-urlencoded'},
	withCredentials: true
});
// 增加不需要验证结果的标记
const noValidate = {
	"/zyplayer-doc-db/executor/execute": true,
	"/zyplayer-doc-db/datasource/test": true,
};

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
			global.vue.$message('请求错误：' + response.message);
		}else {
			if (!response.config.needValidateResult || response.data.errCode == 200) {
				return response.data;
			} else if (response.data.errCode == 400) {
				global.vue.$message('请先登录');
				var href = encodeURIComponent(window.location.href);
				window.location = apimix.apilist1.HOST + "#/user/login?redirect=" + href;
			} else if (response.data.errCode == 402) {
				global.vue.$router.push("/common/noAuth");
			} else if (response.data.errCode !== 200) {
				global.vue.$message(response.data.errMsg || "未知错误");
			}
		}
		return Promise.reject('请求错误');
	},
	error => {
		console.log('err' + error);
		global.vue.$message.info('请求错误：' + error.message);
		return Promise.reject(error)
	}
);
export default service;
