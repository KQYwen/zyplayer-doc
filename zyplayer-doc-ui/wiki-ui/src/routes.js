import PageLayout from './views/view/PageLayout.vue';
// import ShareLayout from './components/layouts/ShareLayout.vue';
// import ShareMobileLayout from './components/layouts/ShareMobileLayout.vue';

// import Search from './views/page/Search.vue';
import NoAuth from './views/common/NoAuth.vue';

// import Home from './views/home/Home.vue';
// import MyInfo from './views/user/MyInfo.vue';
import Show from './views/view/View.vue';
import Edit from './views/view/Edit.vue';

// import spaceManage from './views/space/Manage.vue';

// import sharePcHome from './views/page/share/pc/Home.vue';
// import sharePcView from './views/page/share/pc/View.vue';
// import shareMobileView from './views/page/share/mobile/View.vue';

let routes = [
	{path: '/', redirect: '/home'},
	{path: '/page/search', name: 'WIKI-全局搜索', component: NoAuth},
	{path: '/common/noAuth', name: 'WIKI-没有权限', component: NoAuth},
	{
		path: '/',
		name: '文档管理',
		component: PageLayout,
		children: [
			{path: '/home', name: 'WIKI文档管理', component: NoAuth},
			{path: '/user/myInfo', name: 'WIKI-我的信息', component: NoAuth},
			{path: '/view/:spaceId?/:pageId?', name: 'WIKI-页面查看', component: Show},
			{path: '/edit/:spaceId/:pageId', name: 'WIKI-编辑内容', component: Edit},
			{path: '/space/manage', name: 'WIKI-空间管理', component: NoAuth},
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
];

export default routes;
