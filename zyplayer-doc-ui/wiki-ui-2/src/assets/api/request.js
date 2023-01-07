import axios from 'axios'
import {ElMessageBox, ElMessage} from 'element-plus'

const service = axios.create({
	baseURL: import.meta.env.VITE_APP_BASE_API, // url = base url + request url process.env.APP_BASE_API
	timeout: 10000,
	headers: {'Content-type': 'application/x-www-form-urlencoded'},
	withCredentials: true,
})
// 增加不需要验证结果的标记
const noValidate = {
	'/zyplayer-doc-db/executor/execute': true,
}

service.interceptors.request.use((config) => {
		config.needValidateResult = true
		// 增加不需要验证结果的标记
		if (noValidate[config.url]) {
			config.needValidateResult = false
		}
		return config
	}, (error) => {
		console.log(error)
		return Promise.reject(error)
	}
)
let lastToastLoginTime = new Date().getTime();
service.interceptors.response.use(
	(response) => {
		if (!!response.message) {
			ElMessage.error('请求错误：' + response.message)
		} else {
			if (!response.config.needValidateResult || response.data.errCode === 200) {
				return response.data
			} else if (response.data.errCode === 400) {
				// 两秒钟只提示一次
				if (new Date().getTime() - lastToastLoginTime > 2000) {
					ElMessage.warning('请先登录');
					lastToastLoginTime = new Date().getTime();
				}
				let href = encodeURIComponent(window.location.href)
				window.location = import.meta.env.VITE_APP_BASE_API + '#/user/login?redirect=' + href
			} else if (response.data.errCode !== 200) {
				ElMessage.error(response.data.errMsg || '未知错误')
			}
		}
		return Promise.reject('请求错误')
	}, (error) => {
		console.log('err' + error)
		ElMessage.info('请求错误：' + error.message)
		return Promise.reject(error)
	}
)
export default service
