import Home from './views/home/Home.vue'
import Er from './views/home/Er.vue'

import UserLogin from './views/user/Login.vue'
import UserMyInfo from './views/user/MyInfo.vue'
import UserRouterView from './views/user/RouterView.vue'

import PageTableView from './components/layouts/PageTableView'

import TableInfo from './views/table/Info.vue'
import TableDatabase from './views/table/Database.vue'
import TableProcedure from './views/table/Procedure.vue'
import TableProcedureEdit from './views/table/ProcedureEdit.vue'

import DataDatasourceManage from './views/data/DatasourceManage.vue'
import DataExport from './views/data/Export.vue'
import DataExecutor from './views/data/Executor.vue'
import DataTransferData from './views/data/TransferData.vue'
import DataPreview from './views/data/DataPreview.vue'

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
        path: '/',
        redirect: '/home'
    }, {
        path: '/',
        name: 'Tab标签页',
        component: PageTableView,
        children: [
            {path: '/table/info', name: '表信息',component: TableInfo},
            {path: '/table/database', name: '库信息',component: TableDatabase},
            {path: '/procedure/list', name: '存储过程',component: TableProcedure},
            {path: '/procedure/edit', name: '编辑存储过程',component: TableProcedureEdit},
            {path: '/data/datasourceManage', name: '数据源管理',component: DataDatasourceManage},
            {path: '/data/export', name: '数据库导出',component: DataExport},
            {path: '/data/executor', name: 'SQL执行器',component: DataExecutor},
            {path: '/data/transferData', name: '数据互导工具',component: DataTransferData},
            {path: '/data/dataPreview', name: '表数据预览',component: DataPreview},
			{path: '/user/myInfo', name: '我的信息',component: UserMyInfo},
			// {path: '/home/er', name: 'ER图测试',component: Er},
        ]
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
    }
];

export default routes;
