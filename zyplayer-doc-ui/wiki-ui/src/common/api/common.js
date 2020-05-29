import Qs from 'qs'
import request from './request'

export default {
	commonUpload: data => {
        return request({url: '/zyplayer-doc-wiki/common/upload', method: 'post', data: Qs.stringify(data)});
    },
	getUserBaseInfo: data => {
        return request({url: '/zyplayer-doc-wiki/common/user/base', method: 'post', data: Qs.stringify(data)});
    },
};
