import {defineStore} from 'pinia'

export const useStoreDisplay = defineStore('wikiDisplay', {
	state: () => {
		return {
			// 左边目录栏宽度
			viewMenuWidth: 300,
		}
	},
})
