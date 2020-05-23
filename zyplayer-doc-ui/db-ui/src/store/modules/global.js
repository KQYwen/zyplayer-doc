export default {
    namespaced: true,
    state: {
        pageTabNameMap: {},
		fullscreen: false,
    },
    getters: {
        getPageTabNameMap(state) {
            return state.pageTabNameMap;
        },
    },
    mutations: {
		addTableName(state, item) {
			let sameObj = Object.assign({}, state.pageTabNameMap);
			sameObj[item.key] = item.val;
			state.pageTabNameMap = sameObj;
		},
		setFullscreen(state, val) {
			state.fullscreen = val;
		},
    }
}
