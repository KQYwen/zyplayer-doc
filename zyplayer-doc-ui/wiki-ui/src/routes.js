import Home from './views/home/Home.vue'

import UserLogin from './views/user/Login.vue'
import UserMyInfo from './views/user/MyInfo.vue'
import CommonRouterView from './components/layouts/PageView'

import PageShow from './views/page/Show.vue'
import PageEdit from './views/page/Edit.vue'
import PageSearch from './views/page/Search.vue'

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
		name: '用户管理',
		component: CommonRouterView,
		children: [
			{path: '/user/login', name: '系统登录', component: UserLogin, meta: {fullscreen: true}},
			{path: '/user/myInfo', name: '我的信息', component: UserMyInfo},
			{path: '/page/show', name: '页面内容展示', component: PageShow},
			{path: '/page/edit', name: '编辑内容', component: PageEdit},
			{path: '/page/search', name: '全局搜索', component: PageSearch, meta: {fullscreen: true}},
			{path: '/common/noAuth', name: '没有权限', component: CommonNoAuth},
		]
	}
];

export default routes;
