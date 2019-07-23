import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserMyInfo from './views/user/MyInfo.vue'
import UserRouterView from './views/user/RouterView.vue'

import DataDatasourceManage from './views/data/DatasourceManage.vue'
import DataRouterView from './views/data/RouterView.vue'

import IndexShow from './views/index/Show.vue'
import IndexRouterView from './views/index/RouterView.vue'

import CommonNoAuth from './views/common/NoAuth.vue'

let routes = [
    {
        path: '/home',
        component: Home,
        name: '主页',
        meta: {
            requireAuth: true,
        }
    }, {
        path: '/user',
        name: '用户管理',
        component: UserRouterView,
        children: [
            {path: 'login', name: '系统登录',component: UserLogin, meta: {fullscreen: true}},
            {path: 'myInfo', name: '我的信息',component: UserMyInfo},
        ]
    }, {
        path: '/index',
        name: '索引管理',
        component: IndexRouterView,
        children: [
            {path: 'show', name: '索引信息',component: IndexShow},
        ]
    }, {
        path: '/data',
        name: '数据信息',
        component: DataRouterView,
        children: [
            {path: 'datasourceManage', name: '数据源管理',component: DataDatasourceManage},
        ]
    }, {
        path: '/common',
        name: '',
        component: UserRouterView,
        children: [
            {path: 'noAuth', name: '没有权限',component: CommonNoAuth},
        ]
    }, {
        path: '/',
        redirect: '/home'
    }
];

export default routes;
