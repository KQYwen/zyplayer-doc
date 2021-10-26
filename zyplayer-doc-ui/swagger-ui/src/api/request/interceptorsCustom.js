import qs from 'qs'
import { message } from 'ant-design-vue';

export default function (axios) {
    axios.interceptors.request.use(
        config => {
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
                return response.data;
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

