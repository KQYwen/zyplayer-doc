import PageLayout from './components/layouts/PageLayout.vue'
import SharePageLayout from './components/share/PageLayout.vue'
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
                                name: 'Swagger文档信息',
                                component: () => import('./views/swagger/DocInfo.vue')
                            },
                            {
                                path: '/swagger/view',
                                name: 'Swagger文档展示',
                                component: () => import('./views/swagger/DocView.vue')
                            },
                        ]
                    },
                    // 以下是OpenApi的菜单路由
                    {
                        path: '/openapi',
                        name: 'openApi文档',
                        meta: {
                            hidden: true,
                            icon: 'SettingOutlined'
                        },
                        component: EmptyKeepAliveLayout,
                        children: [
                            {
                                path: '/openapi/info',
                                name: 'OpenApi文档信息',
                                component: () => import('./views/openapi/DocInfo.vue')
                            },
                            {
                                path: '/openapi/view',
                                name: 'OpenApi文档展示',
                                component: () => import('./views/openapi/DocView.vue')
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
    {
        path: '/share',
        name: '开放文档',
        component: () => import('./components/share/GlobalLayout.vue'),
        children: [
            {
                path: '/doc',
                name: '开放文档管理',
                component: SharePageLayout,
                children: [
                    {
                        path: '/share/home',
                        name: '开放文档使用说明',
                        meta: {
                            icon: 'FileTextOutlined'
                        },
                        component: () => import('./views/share/ShareHome.vue')
                    },
                    {
                        path: '/doc',
                        name: '开放文档查看',
                        meta: {
                            hidden: true,
                        },
                        component: EmptyKeepAliveLayout,
                        children: [
                            {
                                path: '/share/swagger/view',
                                name: 'Swagger开放文档展示',
                                component: () => import('./views/swagger/share/DocView.vue')
                            },
                            {
                                path: '/share/openapi/view',
                                name: 'OpenApi开放文档展示',
                                component: () => import('./views/openapi/share/DocView.vue')
                            },
                        ]
                    },
                ]
            }
        ]
    },
]
export default routers;
