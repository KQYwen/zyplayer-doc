import GlobalLayout from './components/layouts/GlobalLayout'
import ShareLayout from './components/layouts/ShareLayout'
import ShareMobileLayout from './components/layouts/ShareMobileLayout'

let routes = [
	{path: '/', redirect: '/home'},
	{path: '/page/search', name: 'WIKI-全局搜索', component: () => import('@/views/page/Search')},
	{path: '/common/noAuth', name: 'WIKI-没有权限', component: () => import('@/views/common/NoAuth')},
	{path: '/page/editorTest', name: '编辑内容', component: () => import('@/views/page/EditorTest')},
	{
		path: '/',
		name: '页面管理',
		component: GlobalLayout,
		children: [
			{path: '/home', name: 'WIKI文档管理', component: () => import('@/views/home/Home')},
			{path: '/user/myInfo', name: 'WIKI-我的信息', component: () => import('@/views/user/MyInfo')},
			{path: '/page/show', name: 'WIKI-内容展示', component: () => import('@/views/page/Show')},
			{path: '/page/edit', name: 'WIKI-编辑内容', component: () => import('@/views/page/Edit')},
			{path: '/space/manage', name: 'WIKI-空间管理', component: () => import('@/views/space/Manage')},
		]
	},
	{
		path: '/',
		name: '页面管理',
		component: ShareLayout,
		children: [
			{path: '/page/share/home', name: 'WIKI-开放文档', component: () => import('@/views/page/share/pc/Home')},
			{path: '/page/share/view', name: 'WIKI-内容展示', component: () => import('@/views/page/share/pc/View')},
		]
	},
	{
		path: '/',
		name: '页面管理',
		component: ShareMobileLayout,
		children: [
			{path: '/page/share/mobile/view', name: 'WIKI-开放文档', component: () => import('@/views/page/share/mobile/View')},
		]
	},
];

export default routes;
