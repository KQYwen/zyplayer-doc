import {defineStore} from 'pinia';

export const useStorePageData = defineStore('pageData', {
	state: () => {
		return {
			// 页面加载状态 -1=空间不存在 0=初始值 1=加载中 2=加载成功 3=加载失败
			pageLoadStatus: 0,
			pageInfo: {},
			pageAuth: {},
			fileList: [],
			choosePageId: 0,
			optionPageId: 0,
			wikiPageList: [],
			pageIsUnLock: false,
			//---------------事件触发---------------
			// 页面列表改变事件
			eventPageListUpdate: false,
		}
	},
	getters: {
		getFirstViewPage: (state) => {
			return () => {
				let getPage = (dataList) => {
					for (let i = 0; i < dataList.length; i++) {
						let page = dataList[i];
						if (page.editorType !== 0) {
							return page;
						} else if (page.children) {
							let find = getPage(page.children);
							if (find) return find;
						}
					}
					return null;
				}
				return getPage(state.pageList);
			}
		},
		getPageById: (state) => {
			return (pageId) => {
				let getPage = (dataList) => {
					for (let i = 0; i < dataList.length; i++) {
						let page = dataList[i];
						if (page.id === pageId) {
							return page;
						} else if (page.children) {
							let find = getPage(page.children);
							if (find) return find;
						}
					}
					return null;
				}
				return getPage(state.pageList);
			}
		},
	}
});
