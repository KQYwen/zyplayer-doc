import apiClient from './request/zyplayer.js'

export const zyplayerApi = {
    getSelfUserInfo: data => apiClient({url: '/user/info/selfInfo', method: 'post', data: data}),
    userLogout: data => apiClient({url: '/logout', method: 'post', data: data}),
    systemUpgradeInfo: data => apiClient({url: '/system/info/upgrade', method: 'post', data: data}),
};

