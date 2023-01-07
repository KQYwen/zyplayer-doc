import Qs from 'qs'
import request from './request'

export default {
	userLogin: (data) => request({url: '/login', method: 'post', data: Qs.stringify(data)}),
	userLogout: () => request({url: '/logout', method: 'post', data: Qs.stringify({})}),
	getSelfUserInfo: () => request({url: '/user/info/selfInfo', method: 'post', data: Qs.stringify({})}),
	getUserBaseInfo: (data) => request({url: '/zyplayer-doc-wiki/common/user/base', method: 'post', data: Qs.stringify(data)}),
	userGroupList: (data) => request({url: '/user/group/list', method: 'post', data: Qs.stringify(data)}),
	getUserMessageList: (data) => request({url: '/user/message/list', method: 'post', data: Qs.stringify(data)}),
	readUserMessage: (data) => request({url: '/user/message/read', method: 'post', data: Qs.stringify(data)}),
	deleteUserMessage: (data) => request({url: '/user/message/delete', method: 'post', data: Qs.stringify(data)}),
}
