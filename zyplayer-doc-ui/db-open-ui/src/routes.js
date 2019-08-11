import Home from './views/home/Home.vue'

import TableInfo from './views/table/Info.vue'
import TableRouterView from './views/table/RouterView.vue'

let routes = [
    {
        path: '/home',
        component: Home,
        name: '主页',
        meta: {
            requireAuth: true,
        }
    }, {
        path: '/table',
        name: '表信息',
        component: TableRouterView,
        children: [
            {path: 'info', name: '表信息',component: TableInfo},
        ]
    }, {
        path: '/',
        redirect: '/home'
    }
];

export default routes;
