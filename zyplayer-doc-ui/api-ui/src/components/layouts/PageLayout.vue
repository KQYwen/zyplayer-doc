<template>
	<div class="page-layout">
		<a-tabs type="editable-card" hide-add v-model:activeKey="activePage" @tab-click="changePage" @edit="editPageTab" style="padding: 5px 10px 0;">
<!--			<template #addIcon>-->
<!--				<a-tooltip title="新建一个API接口">-->
<!--					<plus-outlined />-->
<!--				</a-tooltip>-->
<!--			</template>-->
			<a-tab-pane closable :tab="pageTabNameMap[item.fullPath]||item.name" :name="getRouteRealPath(item)" :fullPath="item.fullPath" :key="item.fullPath" v-for="item in pageList"/>
		</a-tabs>
		<div class="page-body">
			<router-view v-slot="{ Component, route }">
				<keep-alive>
					<component :is="Component" :key="route.fullPath" />
				</keep-alive>
			</router-view>
		</div>
	</div>
</template>

<script>
	import {PlusOutlined} from '@ant-design/icons-vue';

	export default {
		name: 'PageTableView',
		components: {PlusOutlined},
		data() {
			return {
				pageList: [],
				linkList: [],
				activePage: '',
				multiPage: true,
				ignoreParamPath: [
					"/data/export",
				],
				apiRequestIndex: 1,
			}
		},
		computed: {
			pageTabNameMap () {
				return this.$store.state.pageTabNameMap;
			}
		},
		created() {
			let {name, path, fullPath, query} = this.$route;
			let checkedTab = {name, path, fullPath, query};
			this.pageList.push(checkedTab);
			let activePage = this.getRouteRealPath(this.$route);
			this.linkList.push(activePage);
			this.activePage = activePage;
			this.$router.push(this.$route.fullPath);
		},
		watch: {
			'$route': function (newRoute, oldRoute) {
				let activePage = this.getRouteRealPath(newRoute);
				this.activePage = activePage;
				if (this.linkList.indexOf(activePage) < 0) {
					this.linkList.push(activePage);
					let {name, path, fullPath, query} = newRoute;
					this.pageList.push({name, path, fullPath, query});
				}
				let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
				pageRoute.fullPath = newRoute.fullPath;
				this.$store.commit('setActivePage', pageRoute);
			},
		},
		methods: {
			isIgnoreParamPath(path) {
				return this.ignoreParamPath.indexOf(path) >= 0;
			},
			getRouteRealPath(route) {
				return this.isIgnoreParamPath(route.path) ? route.path : route.fullPath;
			},
			changePage(tab) {
				let checkedTab = this.pageList.find(item => item.fullPath === tab);
				this.activePage = this.getRouteRealPath(checkedTab);
				this.$router.push(checkedTab.fullPath);
			},
			editPage(key, action) {
				this[action](key);
			},
			editPageTab(key, action) {
				if (action === 'add') {
					this.addPageTab();
				} else {
					this.removePageTab(key);
				}
			},
			addPageTab() {
				this.$router.push({path: '/custom/request', query: {id: this.apiRequestIndex++}});
			},
			removePageTab(key) {
				if (this.pageList.length === 1) {
					this.$message.warning('这是最后一页，不能再关闭了啦');
					return;
				}
				this.pageList = this.pageList.filter(item => this.getRouteRealPath(item) !== key);
				this.linkList = this.linkList.filter(item => item !== key);
				let index = this.linkList.indexOf(this.activePage);
				if (index < 0) {
					index = this.linkList.length - 1;
					this.activePage = this.linkList[index];
					this.$router.push(this.activePage);
				}
			},
		}
	}
</script>

<style>
	.page-layout{background: #fff;}
	.page-layout .page-body{padding: 0 10px 10px 10px;}
	.ant-tabs-bar{margin-bottom: 0;}
</style>
