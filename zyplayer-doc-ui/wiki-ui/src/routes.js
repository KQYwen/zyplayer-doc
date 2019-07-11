import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserMyInfo from './views/user/MyInfo.vue'
import UserRouterView from './views/user/RouterView.vue'

import PageShow from './views/page/Show.vue'
import PageEdit from './views/page/Edit.vue'
import PageSearch from './views/page/Search.vue'
import PageRouterView from './views/page/RouterView.vue'

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
        path: '/page',
        name: '页面',
        component: PageRouterView,
        children: [
            {path: 'show', name: '页面内容展示',component: PageShow},
            {path: 'edit', name: '编辑内容',component: PageEdit},
            {path: 'search', name: '全局搜索',component: PageSearch, meta: {fullscreen: true}},
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
