import apiClient from './request/zyplayer.js'

export const zyplayerApi = {
    getSelfUserInfo: data => apiClient({url: '/user/info/selfInfo', method: 'post', data: data}),
    userLogout: data => apiClient({url: '/logout', method: 'post', data: data}),
    systemUpgradeInfo: data => apiClient({url: '/system/info/upgrade', method: 'post', data: data}),
    swaggerDocList: data => apiClient({url: '/doc-swagger/doc/list', method: 'post', data: data}),
    swaggerDocAdd: data => apiClient({url: '/doc-swagger/doc/add', method: 'post', data: data}),
    swaggerDocUpdate: data => apiClient({url: '/doc-swagger/doc/update', method: 'post', data: data}),
    docSwaggerGlobalParamList: data => apiClient({url: '/doc-swagger/global-param/list', method: 'post', data: data}),
    docSwaggerGlobalParamUpdate: data => apiClient({url: '/doc-swagger/global-param/update', method: 'post', data: data}),
};

