import {defineStore} from 'pinia'

export const useStoreDisplay = defineStore('wikiDisplay', {
	state: () => {
		return {
			// 左边目录栏宽度
			viewMenuWidth: 300,
			// 是否显示右边评论栏
			commentShow: true,
			commentActiveTab: 'comment',
			showMenu: true,
			rightAsideWidth: 300,
			showHeader:true,
			// 当前页面 view、space
			currentPage: '',
		}
	},
})
