import apiClient from './request/zyplayer.js'

export const zyplayerApi = {

    searchUserList: data => apiClient({url: '/user/info/search', method: 'post', data: data}),

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

    docAuthList: data => apiClient({url: '/doc-api/doc/auth/list', method: 'post', data: data}),
    docAuthAssign: data => apiClient({url: '/doc-api/doc/auth/assign', method: 'post', data: data}),
    docAuthDelete: data => apiClient({url: '/doc-api/doc/auth/delete', method: 'post', data: data}),

    apiCustomFolderAdd: data => apiClient({url: '/api-custom-folder/add', method: 'post', data: data}),
    apiCustomFolderUpdate: data => apiClient({url: '/api-custom-folder/update', method: 'post', data: data}),
    apiCustomFolderDelete: data => apiClient({url: '/api-custom-folder/delete', method: 'post', data: data}),
    apiCustomRequestAdd: data => apiClient({url: '/api-custom-request/add', method: 'post', data: data}),
    apiCustomRequestDetail: data => apiClient({url: '/api-custom-request/detail', method: 'post', data: data}),
    apiCustomRequestDelete: data => apiClient({url: '/api-custom-request/delete', method: 'post', data: data}),

    apiCustomNodeAdd: data => apiClient({url: '/api-custom-node/add', method: 'post', data: data}),
    apiCustomNodeUpdate: data => apiClient({url: '/api-custom-node/update', method: 'post', data: data}),
    apiCustomNodeDelete: data => apiClient({url: '/api-custom-node/delete', method: 'post', data: data}),
    apiCustomNodeDetail: data => apiClient({url: '/api-custom-node/detail', method: 'post', data: data}),
    apiCustomNodeChangeParent: data => apiClient({url: '/api-custom-node/changeParent', method: 'post', data: data}),
};

