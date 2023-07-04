import {defineStore} from 'pinia'

export const useStorePageData = defineStore('pageData', {
	state: () => {
		return {
			spaceInfo: {},
			pageInfo: {},
			pageAuth: {},
			fileList: [],
		}
	},
});
