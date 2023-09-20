import {defineStore} from 'pinia'

export const useStoreSpaceData = defineStore('spaceData', {
	state: () => {
		return {
			spaceInfo:{},
			chooseSpaceId:1,
			spaceOptions: [],
			spaceList:[]
		}
	},
});
