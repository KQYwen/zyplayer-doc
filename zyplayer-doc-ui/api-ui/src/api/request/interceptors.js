import qs from 'qs'
import { message } from 'ant-design-vue';
import {getZyplayerApiBaseUrl} from "./utils";

// 增加不需要验证结果的标记
const noValidate = {
    "./swagger-resources": true,
    "/v2/api-docs": true,
};

export default function (axios) {
    axios.interceptors.request.use(
        config => {
            config.needValidateResult = true;
            // 增加不需要验证结果的标记
            if (noValidate[config.url]) {
                config.needValidateResult = false;
            }
            if (config.method === 'get') {
                config.params = config.params || {};
                config.params = {...config.params, _: (new Date()).getTime()}
            } else if (config.method === 'post') {
                config.data = config.data || {};
                if (config.data instanceof FormData) {
                    // 表单，无需特殊处理
                } else if (config.data instanceof Object) {
                    config.data = qs.stringify(config.data);
                }
            }
            return config;
        },
        error => {
            console.log(error);
            return Promise.reject(error);
        }
    );
    axios.interceptors.response.use(
        response => {
            if (!!response.message) {
                vue.$message.error('请求错误：' + response.message);
            } else {
                if (!response.config.needValidateResult || response.data.errCode === 200) {
                    return response.data;
                } else if (response.data.errCode === 400) {
                    message.error('请先登录');
                    let href = encodeURIComponent(window.location.href);
                    window.location = getZyplayerApiBaseUrl() + "#/user/login?redirect=" + href;
                } else {
                    message.error(response.data.errMsg || "未知错误");
                }
            }
            return Promise.reject('请求错误');
        },
        error => {
            console.log('err' + error);
            message.error('请求错误：' + error.message);
            return Promise.reject(error)
        }
    );
}

