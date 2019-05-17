import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserRouterView from './views/user/RouterView.vue'

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
