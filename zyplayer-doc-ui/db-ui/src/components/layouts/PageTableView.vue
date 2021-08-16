<template>
    <div>
        <el-tabs v-model="activePage" type="card" closable @tab-click="changePage" @tab-remove="removePageTab" style="padding: 5px 10px 0;">
            <el-tab-pane :label="pageTabNameMap[item.fullPath]||item.name" :name="getRouteRealPath(item)" :fullPath="item.fullPath" :key="item.fullPath" v-for="item in pageList"/>
        </el-tabs>
        <keep-alive>
            <router-view :key="$route.fullPath" @initLoadDataList="initLoadDataList" @loadDatasourceList="loadDatasourceList"/>
        </keep-alive>
    </div>
</template>

<script>
    export default {
        name: 'PageTableView',
        components: {},
        data() {
            return {
                pageList: [],
                linkList: [],
                activePage: '',
				multiPage: true,
				ignoreParamPath: [
					"/data/export",
				],
            }
        },
        computed: {
            pageTabNameMap () {
                return this.$store.state.global.pageTabNameMap;
            }
		},
		created() {
			let {name, path, fullPath} = this.$route;
			this.pageList.push({name, path, fullPath});
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
					let {name, path, fullPath} = newRoute;
					this.pageList.push({name, path, fullPath});
                }
				let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
				pageRoute.fullPath = newRoute.fullPath;
            },
        },
        methods: {
			initLoadDataList(param) {
                this.$emit('initLoadDataList', param);
            },
			loadDatasourceList() {
                this.$emit('loadDatasourceList');
            },
			isIgnoreParamPath(path) {
				return this.ignoreParamPath.indexOf(path) >= 0;
            },
			getRouteRealPath(route) {
				return this.isIgnoreParamPath(route.path) ? route.path : route.fullPath;
            },
            changePage(tab) {
                this.activePage = tab.name;
				this.$router.push(tab.$attrs.fullPath);
            },
            editPage(key, action) {
                this[action](key);
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

<style scoped>

</style>
