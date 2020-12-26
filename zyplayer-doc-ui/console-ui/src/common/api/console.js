import Qs from 'qs'
import request from './request'

export default {
    systemUpgradeInfo: data => {
        return request({url: '/system/info/upgrade', method: 'post', data: Qs.stringify(data)});
    },
    userLogin: data => {
        return request({url: '/login', method: 'post', data: Qs.stringify(data)});
    },
    userLogout: data => {
        return request({url: '/logout', method: 'post', data: Qs.stringify(data)});
    },
    getSelfUserInfo: data => {
        return request({url: '/user/info/selfInfo', method: 'post', data: Qs.stringify(data)});
    },
	selfInfoWithAuth: data => {
        return request({url: '/user/info/selfInfoWithAuth', method: 'post', data: Qs.stringify(data)});
    },
    getUserInfoList: data => {
        return request({url: '/user/info/list', method: 'post', data: Qs.stringify(data)});
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
};
