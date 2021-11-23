import {createStore} from 'vuex'

export default createStore({
    state() {
        return {
            docChangedNum: 1,
            userInfo: {},
            pageTabNameMap: {},
            swaggerDoc: {},
            swaggerDefinitions: {},
            urlMethodMap: {},
            methodStatistic: {},
            // 数据库存储的地址信息
            swaggerResource: {},
            // 全局参数
            globalParam: [],
        }
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
        },
        setGlobalParam(state, globalParam) {
            state.globalParam = globalParam;
        },
        setSwaggerResource(state, swaggerResource) {
            state.swaggerResource = swaggerResource;
        },
        addDocChangedNum(state) {
            state.docChangedNum++;
        },
        setSwaggerDoc(state, swaggerDoc) {
            state.swaggerDoc = swaggerDoc;
            state.swaggerDefinitions = swaggerDoc.definitions || {};
        },
        setUrlMethodMap(state, urlMethodMap) {
            state.urlMethodMap = urlMethodMap;
        },
        setMethodStatistic(state, methodStatistic) {
            state.methodStatistic = methodStatistic;
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


