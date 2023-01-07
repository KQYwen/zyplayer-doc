import Qs from 'qs'
import request from './request'

export default {
	getUserBaseInfo: (data) => {
		return request({url: '/zyplayer-doc-wiki/common/user/base', method: 'post', data: Qs.stringify(data)});
	},
}
