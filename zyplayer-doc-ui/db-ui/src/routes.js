import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserMyInfo from './views/user/MyInfo.vue'
import UserRouterView from './views/user/RouterView.vue'

import PageTableView from './components/layouts/PageTableView'

import TableInfo from './views/table/Info.vue'
import TableDatabase from './views/table/Database.vue'
import TableRouterView from './views/table/RouterView.vue'

import DataDatasourceManage from './views/data/DatasourceManage.vue'
import DataExport from './views/data/Export.vue'
import DataExecutor from './views/data/Executor.vue'
import DataTransferData from './views/data/TransferData.vue'
import DataRouterView from './views/data/RouterView.vue'

import CommonNoAuth from './views/common/NoAuth.vue'

let routes = [
    {
        path: '/home1',
        component: Home,
        name: '主页',
        meta: {
            requireAuth: true,
        }
    }, {
        path: '/',
        name: 'Tab标签页',
        component: PageTableView,
        children: [
            {path: '/home', name: '主页',component: Home},
            {path: '/user/login', name: '系统登录',component: UserLogin, meta: {fullscreen: true}},
            {path: '/user/myInfo', name: '我的信息',component: UserMyInfo},
            {path: '/table/info', name: '表信息',component: TableInfo},
            {path: '/table/database', name: '库信息',component: TableDatabase},
            {path: '/data/datasourceManage', name: '数据源管理',component: DataDatasourceManage},
            {path: '/data/export', name: '数据库导出',component: DataExport},
            {path: '/data/executor', name: 'SQL执行器',component: DataExecutor},
            {path: '/data/transferData', name: '数据互导工具',component: DataTransferData},
        ]
    }, {
        path: '/user1',
        name: '用户管理',
        component: UserRouterView,
        children: [
            {path: 'login', name: '系统登录',component: UserLogin, meta: {fullscreen: true}},
            {path: 'myInfo', name: '我的信息',component: UserMyInfo},
        ]
    }, {
        path: '/table1',
        name: '表信息',
        component: TableRouterView,
        children: [
            {path: 'info', name: '表信息',component: TableInfo},
            {path: 'database', name: '库信息',component: TableDatabase},
        ]
    }, {
        path: '/data1',
        name: '数据信息',
        component: DataRouterView,
        children: [
            {path: 'datasourceManage', name: '数据源管理',component: DataDatasourceManage},
            {path: 'export', name: '数据库导出',component: DataExport},
            {path: 'executor', name: 'SQL执行器',component: DataExecutor},
            {path: 'transferData', name: '数据互导工具',component: DataTransferData},
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
