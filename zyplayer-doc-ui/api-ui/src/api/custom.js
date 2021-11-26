import apiClient from './request/custom.js'

export const customApi = {
    get: (url, data) => apiClient({url: url, method: 'get', data: data}),
    post: (url, data) => apiClient({url: url, method: 'post', data: data}),
};

