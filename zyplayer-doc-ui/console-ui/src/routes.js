import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserRouterView from './views/user/RouterView.vue'

import ConsoleRoleList from './views/console/RoleList.vue'
import ConsoleUserList from './views/console/UserList.vue'
import ConsoleAuthList from './views/console/AuthList.vue'
import ConsoleRouterView from './views/console/RouterView.vue'

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
        path: '/console',
        name: '系统管理',
        component: ConsoleRouterView,
        children: [
            {path: 'userList', name: '用户管理',component: ConsoleUserList},
            {path: 'roleList', name: '权限管理',component: ConsoleRoleList},
            {path: 'authList', name: '角色列表',component: ConsoleAuthList},
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
