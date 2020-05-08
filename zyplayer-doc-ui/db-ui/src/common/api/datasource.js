import Qs from 'qs'
import request from '../lib/common/request'

export function queryExecuteSql(data) {
	return request({url: '/zyplayer-doc-db/executor/execute', method: 'post', data: Qs.stringify(data)});
}

export function queryTableDdl(data) {
	return request({url: '/zyplayer-doc-db/doc-db/getTableDdl', method: 'post', data: Qs.stringify(data)});
}

