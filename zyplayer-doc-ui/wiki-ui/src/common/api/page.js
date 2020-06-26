import Qs from 'qs'
import request from './request'

export default {
	pageUpdate: data => {
        return request({url: '/zyplayer-doc-wiki/page/update', method: 'post', data: Qs.stringify(data)});
    },
	pageChangeParent: data => {
        return request({url: '/zyplayer-doc-wiki/page/changeParent', method: 'post', data: Qs.stringify(data)});
    },
	pageList: data => {
        return request({url: '/zyplayer-doc-wiki/page/list', method: 'post', data: Qs.stringify(data)});
    },
	updatePage: data => {
        return request({url: '/zyplayer-doc-wiki/page/update', method: 'post', data: Qs.stringify(data)});
    },
	pageDetail: data => {
        return request({url: '/zyplayer-doc-wiki/page/detail', method: 'post', data: Qs.stringify(data)});
    },
	pageDelete: data => {
        return request({url: '/zyplayer-doc-wiki/page/delete', method: 'post', data: Qs.stringify(data)});
    },
	pageNews: data => {
        return request({url: '/zyplayer-doc-wiki/page/news', method: 'post', data: Qs.stringify(data)});
    },
	pageSearchByEs: data => {
        return request({url: '/zyplayer-doc-wiki/page/searchByEs', method: 'post', data: Qs.stringify(data)});
    },
	pageLock: data => {
        return request({url: '/zyplayer-doc-wiki/page/lock', method: 'post', data: Qs.stringify(data)});
    },
	pageUnlock: data => {
        return request({url: '/zyplayer-doc-wiki/page/unlock', method: 'post', data: Qs.stringify(data)});
    },
	spaceList: data => {
        return request({url: '/zyplayer-doc-wiki/space/list', method: 'post', data: Qs.stringify(data)});
    },
	updateSpace: data => {
        return request({url: '/zyplayer-doc-wiki/space/update', method: 'post', data: Qs.stringify(data)});
    },
	getPageUserAuthList: data => {
        return request({url: '/zyplayer-doc-wiki/page/auth/list', method: 'post', data: Qs.stringify(data)});
    },
	assignPageUserAuth: data => {
        return request({url: '/zyplayer-doc-wiki/page/auth/assign', method: 'post', data: Qs.stringify(data)});
    },
	deletePageFile: data => {
        return request({url: '/zyplayer-doc-wiki/page/file/delete', method: 'post', data: Qs.stringify(data)});
    },
	pageCommentList: data => {
        return request({url: '/zyplayer-doc-wiki/page/comment/list', method: 'post', data: Qs.stringify(data)});
    },
	updatePageComment: data => {
        return request({url: '/zyplayer-doc-wiki/page/comment/update', method: 'post', data: Qs.stringify(data)});
    },
	deletePageComment: data => {
        return request({url: '/zyplayer-doc-wiki/page/comment/delete', method: 'post', data: Qs.stringify(data)});
    },
	pageZanList: data => {
        return request({url: '/zyplayer-doc-wiki/page/zan/list', method: 'post', data: Qs.stringify(data)});
    },
	updatePageZan: data => {
        return request({url: '/zyplayer-doc-wiki/page/zan/update', method: 'post', data: Qs.stringify(data)});
    },
	openPageDetail: data => {
        return request({url: '/zyplayer-doc-wiki/open-api/page/detail', method: 'post', data: Qs.stringify(data)});
    },
	openPageList: data => {
        return request({url: '/zyplayer-doc-wiki/open-api/page/list', method: 'post', data: Qs.stringify(data)});
    },
	openSpaceInfo: data => {
        return request({url: '/zyplayer-doc-wiki/open-api/space/info', method: 'post', data: Qs.stringify(data)});
    },
	openPageNews: data => {
		return request({url: '/zyplayer-doc-wiki/open-api/page/news', method: 'post', data: Qs.stringify(data)});
	},
	xxxxxxxxxxxx: data => {
        return request({url: 'update', method: 'post', data: Qs.stringify(data)});
    },

};
