import GlobalLayout from './components/layouts/GlobalLayout.vue'
import ShareLayout from './components/layouts/ShareLayout.vue'
import ShareMobileLayout from './components/layouts/ShareMobileLayout.vue'

import Search from './views/page/Search.vue'
import NoAuth from './views/common/NoAuth.vue'

import Home from './views/home/Home.vue'
import MyInfo from './views/user/MyInfo.vue'
import Show from './views/page/Show.vue'
import Edit from './views/page/Edit.vue'

import spaceManage from './views/space/Manage.vue'

import sharePcHome from './views/page/share/pc/Home.vue'
import sharePcView from './views/page/share/pc/View.vue'
import shareMobileView from './views/page/share/mobile/View.vue'

let routes = [
	{path: '/', redirect: '/home'},
	{path: '/page/search', name: 'WIKI-全局搜索', component: Search},
	{path: '/common/noAuth', name: 'WIKI-没有权限', component: NoAuth},
	{
		path: '/',
		name: '文档管理',
		component: GlobalLayout,
		children: [
			{path: '/home', name: 'WIKI文档管理', component: Home},
			{path: '/user/myInfo', name: 'WIKI-我的信息', component: MyInfo},
			{path: '/page/show', name: 'WIKI-页面查看', component: Show},
			{path: '/page/edit', name: 'WIKI-编辑内容', component: Edit},
			{path: '/space/manage', name: 'WIKI-空间管理', component: spaceManage},
		],
	},
	{
		path: '/',
		name: 'PC端开放文档',
		component: ShareLayout,
		children: [
			{
				path: '/page/share/home',
				name: 'WIKI-开放文档',
				component: sharePcHome,
			},
			{
				path: '/page/share/view',
				name: 'WIKI-内容展示',
				component: sharePcView,
			},
		],
	},
	{
		path: '/',
		name: 'APP端开放文档',
		component: ShareMobileLayout,
		children: [
			{
				path: '/page/share/mobile/view',
				name: 'WIKI-开放文档-APP',
				component: shareMobileView,
			},
		],
	},
]

export default routes
