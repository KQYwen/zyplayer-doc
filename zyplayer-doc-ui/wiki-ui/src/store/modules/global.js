export default {
    namespaced: true,
    state: {
        pageTabNameMap: {},
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
    }
}
