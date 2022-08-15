import PageTableView from './components/layouts/PageTableView.vue'

import Home from './views/home/Home.vue'
import MyInfo from './views/user/MyInfo.vue'
import Login from './views/user/Login.vue'

import UserList from './views/console/UserList.vue'
import AuthList from './views/console/AuthList.vue'
import RoleList from './views/console/RoleList.vue'
import UserGroupList from './views/console/UserGroupList.vue'

import NoAuth from './views/common/NoAuth.vue'

let routes = [
	{path: '/', redirect: '/home'},
	{
		path: '/user/login',
		name: '系统登录',
		component: Login,
		meta: {fullscreen: true}
	}, {
		path: '/',
		name: '页面管理',
		component: PageTableView,
		children: [
			{path: '/home', name: '控制台', component: Home},
		]
	}, {
		path: '/user',
		name: '用户管理',
		component: PageTableView,
		children: [
			{path: 'myInfo', name: '我的信息', component: MyInfo},
		]
	}, {
		path: '/console',
		name: '系统管理',
		component: PageTableView,
		children: [
			{path: 'userList', name: '用户管理', component: UserList},
			{path: 'roleList', name: '权限管理', component: AuthList},
			{path: 'authList', name: '角色列表', component: RoleList},
			{path: 'userGroupList', name: '分组管理', component: UserGroupList},
		]
	}, {
		path: '/common',
		name: '',
		component: PageTableView,
		children: [
			{path: 'noAuth', name: '没有权限', component: NoAuth},
		]
	},
];

export default routes;
