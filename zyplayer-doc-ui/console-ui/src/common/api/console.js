import Qs from 'qs'
import request from './request'

export default {
    userLogin: data => {
        return request({url: '/login', method: 'post', data: Qs.stringify(data)});
    },
    userLogout: data => {
        return request({url: '/logout', method: 'post', data: Qs.stringify(data)});
    },
    getSelfUserInfo: data => {
        return request({url: '/user/info/selfInfo', method: 'post', data: Qs.stringify(data)});
    },
	updateSelfPwd: data => {
        return request({url: '/user/info/updateSelfPwd', method: 'post', data: Qs.stringify(data)});
    },
	selfInfoWithAuth: data => {
        return request({url: '/user/info/selfInfoWithAuth', method: 'post', data: Qs.stringify(data)});
    },
    getUserInfoList: data => {
        return request({url: '/user/info/list', method: 'post', data: Qs.stringify(data)});
    },
	searchUserInfoList: data => {
        return request({url: '/user/info/search', method: 'post', data: Qs.stringify(data)});
    },
    updateUserInfo: data => {
        return request({url: '/user/info/update', method: 'post', data: Qs.stringify(data)});
    },
    deleteUserInfo: data => {
        return request({url: '/user/info/delete', method: 'post', data: Qs.stringify(data)});
    },
    userAuthList: data => {
        return request({url: '/user/info/auth/list', method: 'post', data: Qs.stringify(data)});
    },
    updateUserAuth: data => {
        return request({url: '/user/info/auth/update', method: 'post', data: Qs.stringify(data)});
    },
    resetPassword: data => {
        return request({url: '/user/info/resetPassword', method: 'post', data: Qs.stringify(data)});
    },
    userGroupList: data => {
        return request({url: '/user/group/list', method: 'post', data: Qs.stringify(data)});
    },
    updateUserGroup: data => {
        return request({url: '/user/group/update', method: 'post', data: Qs.stringify(data)});
    },
	deleteUserGroup: data => {
        return request({url: '/user/group/delete', method: 'post', data: Qs.stringify(data)});
    },
	updateUserGroupRelation: data => {
        return request({url: '/user/group/relation/update', method: 'post', data: Qs.stringify(data)});
    },
	removeUserGroupRelation: data => {
        return request({url: '/user/group/relation/remove', method: 'post', data: Qs.stringify(data)});
    },
	userGroupRelationList: data => {
        return request({url: '/user/group/relation/list', method: 'post', data: Qs.stringify(data)});
    },
};
