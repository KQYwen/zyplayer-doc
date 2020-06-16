import GlobalLayout from './components/layouts/GlobalLayout'
import OpenLayout from './components/layouts/ShareLayout'

let routes = [
	{path: '/', redirect: '/home'},
	{path: '/user/login', name: 'WIKI-系统登录', component: () => import('@/views/user/Login')},
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
		]
	},
	{
		path: '/',
		name: '页面管理',
		component: OpenLayout,
		children: [
			{path: '/page/share/home', name: 'WIKI-开放文档', component: () => import('@/views/page/share/Home')},
			{path: '/page/share/view', name: 'WIKI-内容展示', component: () => import('@/views/page/share/View')},
		]
	}
];

export default routes;
