import {defineStore} from 'pinia';

export const useStoreUserData = defineStore('userData', {
	state: () => {
		return {
			// 用户信息
			userInfo: {},
			upgradeInfo: {},
			// 左侧菜单的路由和收起状态
			menuRouteKey: [],
			menuCollapsed: false,
		}
	},
});
