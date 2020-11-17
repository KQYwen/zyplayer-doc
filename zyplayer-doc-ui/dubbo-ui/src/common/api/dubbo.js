import Qs from 'qs'
import request from './request'

export default {
	findDocInfo: data => {
        return request({url: '/zyplayer-doc-dubbo/doc-dubbo/findDocInfo', method: 'post', data: Qs.stringify(data)});
    },
	reloadService: data => {
        return request({url: '/zyplayer-doc-dubbo/doc-dubbo/reloadService', method: 'post', data: Qs.stringify(data)});
    },
	getDocList: data => {
        return request({url: '/zyplayer-doc-dubbo/doc-dubbo/getDocList', method: 'post', data: Qs.stringify(data)});
    },
	saveDoc: data => {
        return request({url: '/zyplayer-doc-dubbo/doc-dubbo/saveDoc', method: 'post', data: Qs.stringify(data)});
    },
	request: data => {
        return request({url: '/zyplayer-doc-dubbo/doc-dubbo/request', method: 'post', data: Qs.stringify(data)});
    },
    systemUpgradeInfo: data => {
        return request({url: '/system/info/upgrade', method: 'post', data: Qs.stringify(data)});
    },
};
