import PageLayout from './components/layouts/PageLayout.vue'
import EmptyLayout from './components/layouts/EmptyLayout.vue'

let routers = [
    {
        path: '/',
        name: '主页',
        component: () => import('./components/layouts/GlobalLayout.vue'),
        redirect: '/doc/console',
        children: [
            {
                path: '/doc',
                name: '系统配置',
                meta: {
                    icon: 'SettingOutlined'
                },
                component: PageLayout,
                children: [
                    {
                        path: '/doc/console',
                        name: '控制台',
                        meta: {
                            icon: 'DashboardOutlined'
                        },
                        component: () => import('./views/common/Console.vue')
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
