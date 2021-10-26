import PageLayout from './components/layouts/PageLayout.vue'
import EmptyLayout from './components/layouts/EmptyLayout.vue'

let routers = [
    {
        path: '/',
        name: '主页',
        component: () => import('./components/layouts/GlobalLayout.vue'),
        redirect: '/doc/info',
        children: [
            {
                path: '/doc',
                name: '系统配置',
                component: PageLayout,
                children: [
                    {
                        path: '/doc/info',
                        name: '文档信息',
                        meta: {
                            icon: 'InfoCircleOutlined',
                        },
                        component: () => import('./views/doc/DocInfo.vue')
                    },
                    {
                        path: '/doc/manage',
                        name: '文档管理',
                        meta: {
                            icon: 'FileTextOutlined'
                        },
                        component: () => import('./views/doc/DocManage.vue')
                    },
                    {
                        path: '/doc/setting',
                        name: '系统配置',
                        meta: {
                            icon: 'SettingOutlined'
                        },
                        component: EmptyLayout,
                        children: [
                            {
                                path: '/doc/setting/view',
                                name: '展示配置',
                                component: () => import('./views/common/SettingView.vue')
                            },
                        ]
                    },
                    {
                        path: '/doc/view',
                        name: '文档展示',
                        meta: {
                            hidden: true,
                        },
                        component: () => import('./views/doc/DocView.vue')
                    },
                ]
            },
        ]
    },
]
export default routers;
