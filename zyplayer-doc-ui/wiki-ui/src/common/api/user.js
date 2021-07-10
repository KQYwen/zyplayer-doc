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
	userGroupList: data => {
		return request({url: '/user/group/list', method: 'post', data: Qs.stringify(data)});
	},
	getUserMessageList: data => {
		return request({url: '/user/message/list', method: 'post', data: Qs.stringify(data)});
	},
	readUserMessage: data => {
		return request({url: '/user/message/read', method: 'post', data: Qs.stringify(data)});
	},
	deleteUserMessage: data => {
		return request({url: '/user/message/delete', method: 'post', data: Qs.stringify(data)});
	},
};

// userLogin: '/login',
// userLogout: '/logout',
// getSelfUserInfo: '/user/info/selfInfo',
// getUserBaseInfo: '/zyplayer-doc-wiki/common/user/base',
