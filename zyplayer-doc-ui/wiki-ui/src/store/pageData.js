import {defineStore} from 'pinia'

export const useStorePageData = defineStore('pageData', {
	state: () => {
		return {
			pageInfo: {},
			pageAuth: {},
			fileList: [],
			choosePageId: 0,
			optionPageId: 0,
			wikiPageList: [],
			pageIsUnLock: false,
		}
	},
});
