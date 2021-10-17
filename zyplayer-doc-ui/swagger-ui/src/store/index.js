import {createStore} from 'vuex'

export default createStore({
    state() {
        return {
            userInfo: {},
            pageTabNameMap: {},
            docMap: {
                '/getUserInfo': {
                    name: '获取用户信息'
                },
                '/deleteUserInfo': {
                    name: '删除用户信息'
                },
                '/updateUserInfo': {
                    name: '修改用户信息'
                },
            },
        }
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
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


