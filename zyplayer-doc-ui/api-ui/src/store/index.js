import {createStore} from 'vuex'

export default createStore({
    state() {
        return {
            // 文档改变事件，供其他页面watch
            docChangedNum: 1,
            // 用户信息
            userInfo: {},
            // tab多标签的标签名map{xxx: 'val'}
            pageTabNameMap: {},
            // 数据库存储的文档信息
            apiDoc: {},
            // 全局参数
            globalParam: [],

            // swagger原始文档
            swaggerDoc: {},
            // swagger原始definitions
            swaggerDefinitions: {},
            // url对应的map信息 {'url + "." + method': {swagger.paths.url.method对象信息}}
            swaggerUrlMethodMap: {},
            // 方法统计{post: 10, total: 20}
            swaggerMethodStatistic: {},

            // openApi原始文档
            openApiDoc: {},
            // openApi原始definitions
            openApiDefinitions: {},
            // url对应的map信息 {'url + "." + method': {swagger.paths.url.method对象信息}}
            openApiUrlMethodMap: {},
            // 方法统计{post: 10, total: 20}
            openApiMethodStatistic: {},
        }
    },
    getters: {
        getDocChangedNum: (state) => () => {
            return state.docChangedNum;
        }
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
        },
        setGlobalParam(state, globalParam) {
            state.globalParam = globalParam;
        },
        setApiDoc(state, apiDoc) {
            state.apiDoc = apiDoc;
        },
        addDocChangedNum(state) {
            state.docChangedNum++;
        },
        // swagger
        setSwaggerDoc(state, swaggerDoc) {
            state.swaggerDoc = swaggerDoc;
            state.swaggerDefinitions = swaggerDoc.definitions || {};
        },
        setSwaggerUrlMethodMap(state, swaggerUrlMethodMap) {
            state.swaggerUrlMethodMap = swaggerUrlMethodMap;
        },
        setSwaggerMethodStatistic(state, swaggerMethodStatistic) {
            state.swaggerMethodStatistic = swaggerMethodStatistic;
        },
        // openApi
        setOpenApiDoc(state, openApiDoc) {
            state.openApiDoc = openApiDoc;
            state.openApiDefinitions = openApiDoc.definitions || {};
        },
        setOpenApiUrlMethodMap(state, openApiUrlMethodMap) {
            state.openApiUrlMethodMap = openApiUrlMethodMap;
        },
        setOpenApiMethodStatistic(state, openApiMethodStatistic) {
            state.openApiMethodStatistic = openApiMethodStatistic;
        },
        addTableName(state, item) {
            let sameObj = Object.assign({}, state.pageTabNameMap);
            sameObj[item.key] = item.val;
            state.pageTabNameMap = sameObj;
        },
    }
});

// 使用方法
// return this.$store.state.userInfo
// this.$store.commit('setUserInfo', 111);

// 动态计算值
// computed: {
//     initialEnv () {
//         return this.$store.state.initialEnv;
//     }
// },

// js文件中使用
// import store from '../../store/index'
// store.commit('setInitialEnv', this.initialEnv);


