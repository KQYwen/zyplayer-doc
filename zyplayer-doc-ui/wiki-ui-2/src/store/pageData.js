import {defineStore} from 'pinia'

export const useStorePageData = defineStore('pageData', {
	state: () => {
		return {
			pageInfo: {},
		}
	},
});

