import GlobalLayout from './components/layouts/GlobalLayout'

let routes = [
	{path: '/', redirect: '/home'},
	{path: '/user/login', name: '系统登录', component: () => import('@/views/user/Login')},
	{path: '/page/search', name: '全局搜索', component: () => import('@/views/page/Search')},
	{path: '/common/noAuth', name: '没有权限', component: () => import('@/views/common/NoAuth')},
	{path: '/page/editorTest', name: '编辑内容', component: () => import('@/views/page/EditorTest')},
	{
		path: '/',
		name: '页面管理',
		component: GlobalLayout,
		children: [
			{path: '/home', name: '主页', component: () => import('@/views/home/Home')},
			{path: '/user/myInfo', name: '我的信息', component: () => import('@/views/user/MyInfo')},
			{path: '/page/show', name: '页面内容展示', component: () => import('@/views/page/Show')},
			{path: '/page/edit', name: '编辑内容', component: () => import('@/views/page/Edit')},
		]
	}
];

export default routes;
