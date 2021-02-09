import PageTableView from './components/layouts/PageTableView'

let routes = [
	{path: '/', redirect: '/home'},
	{
		path: '/user/login',
		name: '系统登录',
		component: () => import('@/views/user/Login.vue'),
		meta: {fullscreen: true}
	}, {
		path: '/',
		name: '页面管理',
		component: PageTableView,
		children: [
			{path: '/home', name: '控制台', component: () => import('@/views/home/Home')},
		]
	}, {
		path: '/user',
		name: '用户管理',
		component: PageTableView,
		children: [
			{path: 'myInfo', name: '我的信息', component: () => import('@/views/user/MyInfo.vue')},
		]
	}, {
		path: '/console',
		name: '系统管理',
		component: PageTableView,
		children: [
			{path: 'userList', name: '用户管理', component: () => import('@/views/console/UserList.vue')},
			{path: 'roleList', name: '权限管理', component: () => import('@/views/console/AuthList.vue')},
			{path: 'authList', name: '角色列表', component: () => import('@/views/console/RoleList.vue')},
			{path: 'userGroupList', name: '分组管理', component: () => import('@/views/console/UserGroupList.vue')},
		]
	}, {
		path: '/common',
		name: '',
		component: PageTableView,
		children: [
			{path: 'noAuth', name: '没有权限', component: () => import('@/views/common/NoAuth.vue')},
		]
	},
];

export default routes;
