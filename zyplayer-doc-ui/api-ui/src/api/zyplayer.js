import apiClient from './request/zyplayer.js'

export const zyplayerApi = {
    getSelfUserInfo: data => apiClient({url: '/user/info/selfInfo', method: 'post', data: data}),
    userLogout: data => apiClient({url: '/logout', method: 'post', data: data}),
    systemUpgradeInfo: data => apiClient({url: '/system/info/upgrade', method: 'post', data: data}),
    apiDocList: data => apiClient({url: '/doc-api/doc/list', method: 'post', data: data}),
    apiDocAdd: data => apiClient({url: '/doc-api/doc/add', method: 'post', data: data}),
    apiDocUpdate: data => apiClient({url: '/doc-api/doc/update', method: 'post', data: data}),
    apiDocDetail: data => apiClient({url: '/doc-api/doc/detail', method: 'post', data: data}),
    apiDocApis: data => apiClient({url: '/doc-api/doc/apis', method: 'post', data: data}),
    apiDocApisDetail: data => apiClient({url: '/doc-api/doc/apis/detail', method: 'post', data: data}),
    docApiGlobalParamList: data => apiClient({url: '/doc-api/global-param/list', method: 'post', data: data}),
    docApiGlobalParamUpdate: data => apiClient({url: '/doc-api/global-param/update', method: 'post', data: data}),
    requestUrl: data => apiClient({url: '/doc-api/proxy/request', method: 'post', data: data}),
    apiShareDocDetail: data => apiClient({url: '/doc-api/share/detail', method: 'post', data: data}),
    apiShareDocApisDetail: data => apiClient({url: '/doc-api/share/apis/detail', method: 'post', data: data}),
};

