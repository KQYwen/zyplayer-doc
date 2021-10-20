import apiClient from './request/custom.js'

export const customApi = {
    post: (url, data) => apiClient({url: url, method: 'post', data: data}),
};

