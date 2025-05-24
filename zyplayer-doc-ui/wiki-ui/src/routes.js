import Login from './views/user/Login.vue';
import PageLayout from './views/view/PageLayout.vue';
import WikiLayout from './views/wiki/Layout.vue';

import WikiSpace from './views/wiki/Wiki.vue';
// import ShareLayout from './components/layouts/ShareLayout.vue';
// import ShareMobileLayout from './components/layouts/ShareMobileLayout.vue';

// import Search from './views/page/Search.vue';
import NoAuth from './views/common/NoAuth.vue';

// import Home from './views/home/Home.vue';
// import MyInfo from './views/user/MyInfo.vue';
import Show from './views/view/View.vue';
import Setting from './views/view/Setting.vue';
import Recycle from './views/view/Recycle.vue';
import Edit from './views/view/Edit.vue';

// import spaceManage from './views/space/Manage.vue';

// import sharePcHome from './views/page/share/pc/Home.vue';
// import sharePcView from './views/page/share/pc/View.vue';
// import shareMobileView from './views/page/share/mobile/View.vue';

import {createRouter, createWebHashHistory} from 'vue-router';

export default createRouter({
	history: createWebHashHistory(),
	routes: [
		{path: '/', redirect: '/wiki/space'},
		{path: '/user/login', name: 'systemLogin', component: Login},
		{path: '/page/search', name: 'WIKI-全局搜索', component: NoAuth},
		{path: '/common/noAuth', name: 'WIKI-没有权限', component: NoAuth},
		{
			path: '/',
			name: 'WikiLayout',
			component: WikiLayout,
			children: [
				{path: '/wiki/space', name: 'WIKI文档管理', component: WikiSpace},
			],
		},
		{
			path: '/',
			name: '文档管理',
			component: PageLayout,
			children: [
				// {path: '/home', name: 'WIKI文档管理', component: NoAuth},
				{path: '/user/myInfo', name: '我的信息', component: NoAuth},
				{path: '/view/:spaceId?/:pageId?', name: '页面查看', component: Show},
				{path: '/edit/:spaceId/:pageId', name: '页面编辑', component: Edit},
				{path: '/view/setting/:spaceId', name: '空间设置', component: Setting},
				{path: '/view/recycle/:spaceId', name: '回收站', component: Recycle},
				{path: '/space/manage', name: '空间管理', component: NoAuth},
			],
		},
		{
			path: '/',
			name: 'PC端开放文档',
			component: NoAuth,
			children: [
				{
					path: '/page/share/home',
					name: 'WIKI-开放文档',
					component: NoAuth,
				},
				{
					path: '/page/share/view',
					name: 'WIKI-内容展示',
					component: NoAuth,
				},
			],
		},
		{
			path: '/',
			name: 'APP端开放文档',
			component: NoAuth,
			children: [
				{
					path: '/page/share/mobile/view',
					name: 'WIKI-开放文档-APP',
					component: NoAuth,
				},
			],
		},
	]
});
