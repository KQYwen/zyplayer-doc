import PageLayout from './components/layouts/PageLayout.vue'
import EmptyKeepAliveLayout from './components/layouts/EmptyKeepAliveLayout.vue'

let routers = [
    {
        path: '/',
        name: '主页',
        component: () => import('./components/layouts/GlobalLayout.vue'),
        redirect: '/manage',
        children: [
            {
                path: '/doc',
                name: '文档管理',
                component: PageLayout,
                children: [
                    {
                        path: '/manage',
                        name: '文档地址管理',
                        meta: {
                            icon: 'FileTextOutlined'
                        },
                        component: () => import('./views/manage/DocManage.vue')
                    },
                    {
                        path: '/setting',
                        name: '系统设置',
                        meta: {
                            icon: 'SettingOutlined'
                        },
                        component: EmptyKeepAliveLayout,
                        children: [
                            {
                                path: '/setting/globalParam',
                                name: '全局参数',
                                component: () => import('./views/manage/GlobalParam.vue')
                            },
                            {
                                path: '/setting/view',
                                name: '展示配置',
                                meta: {
                                    hidden: true,
                                },
                                component: () => import('./views/common/SettingView.vue')
                            },
                        ]
                    },
                    // 以下是隐藏的菜单路由
                    {
                        path: '/swagger',
                        name: 'swagger文档',
                        meta: {
                            hidden: true,
                            icon: 'SettingOutlined'
                        },
                        component: EmptyKeepAliveLayout,
                        children: [
                            {
                                path: '/swagger/info',
                                name: '文档信息',
                                component: () => import('./views/swagger/DocInfo.vue')
                            },
                            {
                                path: '/swagger/view',
                                name: '文档展示',
                                component: () => import('./views/swagger/DocView.vue')
                            },
                        ]
                    },
                    {
                        path: '/api',
                        name: 'API请求',
                        meta: {
                            hidden: true,
                        },
                        component: EmptyKeepAliveLayout,
                        children: [
                            {
                                path: '/api/request',
                                name: '接口请求',
                                component: () => import('./views/apiRequest/ApiRequest.vue')
                            },
                        ]
                    },
                ]
            },
        ]
    },
]
export default routers;
