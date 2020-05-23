import Qs from 'qs'
import request from './request'

export default {
	userLogin: data => {
		return request({url: '/login', method: 'post', data: Qs.stringify(data)});
	},
	userLogout: () => {
		return request({url: '/logout', method: 'post', data: Qs.stringify({})});
	},
	getSelfUserInfo: () => {
		return request({url: '/user/info/selfInfo', method: 'post', data: Qs.stringify({})});
	},
	getUserBaseInfo: data => {
		return request({url: '/zyplayer-doc-wiki/common/user/base', method: 'post', data: Qs.stringify(data)});
	},
};

// userLogin: '/login',
// userLogout: '/logout',
// getSelfUserInfo: '/user/info/selfInfo',
// getUserBaseInfo: '/zyplayer-doc-wiki/common/user/base',
