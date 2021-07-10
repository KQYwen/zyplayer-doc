import Qs from 'qs'
import request from './request'

export default {
    systemUpgradeInfo: data => {
        return request({url: '/system/info/upgrade', method: 'post', data: Qs.stringify(data)});
    },
};
