import {defineStore} from 'pinia'

export const useStoreUserData = defineStore('userData', {
	state: () => {
		return {
			// 用户信息
			userInfo: {},
		}
	},
})
