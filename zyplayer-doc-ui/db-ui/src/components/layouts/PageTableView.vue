<template>
	<div>
		<el-tabs v-model="activePage" type="card" closable @tab-click="changePage" @tab-remove="removePageTab"
				 @contextmenu.prevent.native="openContextMenu($event)" style="padding: 5px 10px 0;height: 30px;font-size: 12px;margin: 0 0 5px;">
			<el-tab-pane :label="pageTabNameMap[item.fullPath]||item.name" :name="getRouteRealPath(item)"
						 :fullPath="item.fullPath" :key="item.fullPath" v-for="item in pageList"/>
		</el-tabs>
		<keep-alive>
			<router-view v-on:listenToChildEvent="getDatafromChild" :key="$route.fullPath"
						 @initLoadDataList="initLoadDataList" @loadDatasourceList="loadDatasourceList"/>
		</keep-alive>

		<ul v-show="contextMenuVisible" :style="{left:left+'px',top:top+'px'}" class="contextmenu">
			<!--<li @click="curTabReload()"><el-button type="text" size="mini">重新加载</el-button></li>-->
			<li @click="closeAllTabs()">
				<el-button type="text" size="mini">关闭所有</el-button>
			</li>
			<li @click="closeOtherTabs('left')">
				<el-button type="text" size="mini">关闭左边</el-button>
			</li>
			<li @click="closeOtherTabs('right')">
				<el-button type="text" size="mini">关闭右边</el-button>
			</li>
			<li @click="closeOtherTabs('other')">
				<el-button type="text" size="mini">关闭其他</el-button>
			</li>
		</ul>

	</div>
</template>

<script>
import datasourceApi from "@/common/api/datasource";

export default {
	name: 'PageTableView',
	components: {},
	data() {
		return {
			pageList: [],
			linkList: [],
			activePage: '',
			datasourceList: [],
			multiPage: true,
			ignoreParamPath: [
				"/data/export",
			],
			contextMenuVisible: false,
			left: 0,
			top: 0,
		}
	},
	computed: {
		pageTabNameMap() {
			return this.$store.state.global.pageTabNameMap;
		}
	},
	created() {
		let {name, path, fullPath} = this.$route;
		//sql执行器tab页名称动态变化
		if (path === '/data/executor') {
			let database = this.$route.query.database;
			let datasourceId = this.$route.query.datasourceId;
			if (datasourceId) {
				datasourceApi.datasource({sourceId: datasourceId}).then(json => {
					let dataname = json.data.name;
					let groupName = json.data.name;
					if (dataname) {
						name = name + "( " + dataname + "[" + database + "] )"
					}
					this.pageList.push({name, path, fullPath});
					let activePage = this.getRouteRealPath(this.$route);
					this.linkList.push(activePage);
					this.activePage = activePage;
					this.$router.push(this.$route.fullPath);
				})
				return false;
			} else {
				datasourceApi.datasourceList({}).then(json => {
					this.datasourceList = json.data || [];
					if (this.datasourceList.length > 0) {
						let dataname = this.datasourceList[0].name;
						if (this.datasourceList[0].id) {
							datasourceApi.databaseList({sourceId: this.datasourceList[0].id}).then(json => {
								if (json.data.length > 0) {
									// 排除系统库
									let sysDbName = ["information_schema", "master", "model", "msdb", "tempdb"];
									let notSysDbItem = json.data.find(item => sysDbName.indexOf(item.dbName) < 0);
									let choiceDatabase = (!!notSysDbItem) ? notSysDbItem.dbName : json.data[0].dbName;
									if (dataname && choiceDatabase) {
										name = name + "( " + dataname + "[" + choiceDatabase + "] )"
									}
									this.$router.replace({
										query: {
											datasourceId: this.datasourceList[0].id,
											database: choiceDatabase
										}
									})
								}
							})
						}
					}
				})
				return false;
			}
		}
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
				//sql执行器tab页名称动态变化
				if (path === '/data/executor') {
					let database = newRoute.query.database;
					let datasourceId = newRoute.query.datasourceId;
					if (datasourceId) {
						datasourceApi.datasource({sourceId: datasourceId}).then(json => {
							let dataname = json.data.name;
							let groupName = json.data.groupName;
							if (dataname) {
								name = name + "( " + dataname + "[" + database + "] )"
							}
							//this.pageList.push({name, path, fullPath});
							let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
							pageRoute.name = name;

						})
					} else {
						datasourceApi.datasourceList({}).then(json => {
							this.datasourceList = json.data || [];
							if (this.datasourceList.length > 0) {
								let dataname = this.datasourceList[0].name;
								if (this.datasourceList[0].id) {
									datasourceApi.databaseList({sourceId: this.datasourceList[0].id}).then(json => {
										if (json.data.length > 0) {
											// 排除系统库
											let sysDbName = ["information_schema", "master", "model", "msdb", "tempdb"];
											let notSysDbItem = json.data.find(item => sysDbName.indexOf(item.dbName) < 0);
											let choiceDatabase = (!!notSysDbItem) ? notSysDbItem.dbName : json.data[0].dbName;
											if (dataname && choiceDatabase) {
												name = name + "( " + dataname + "[" + choiceDatabase + "] )"
											}
											this.pageList.splice(this.pageList.findIndex(item => item.fullPath === path), 1);
											this.linkList.splice(this.linkList.findIndex(item => item === path), 1);
											this.$router.replace({
												query: {
													datasourceId: this.datasourceList[0].id,
													database: choiceDatabase
												}
											})
										}
									})
								}
							}
						})
					}
				}

			}
			//let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
			//pageRoute.fullPath = newRoute.fullPath;
		},
		contextMenuVisible(value) {
			if (value) {
				document.body.addEventListener('click', this.closeContextMenu)
			} else {
				document.body.removeEventListener('click', this.closeContextMenu)
			}
		}
	},
	methods: {
		getDatafromChild(data) {
			this.datasourceList = data;
		},
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
		//tab栏右键菜单
		openContextMenu(e) {
			let obj = e.srcElement ? e.srcElement : e.target;
			if (obj.id) {
				//数据源与数据库名可能携带 "-" ,只截取第一次出现之后的部分
				let currentContextTabId = obj.id.split('-').slice(1).join('-');
				this.contextMenuVisible = true;
				this.$store.commit("saveCurContextTabId", currentContextTabId);
				this.left = e.clientX;
				this.top = e.clientY + 10;
			}
		},

		//刷新当前页
		curTabReload() {
			let currTabIndex = 0;
			let curContextTabId = this.$store.state.tagsView.curContextTabId;
			for (let i = 0; i < this.pageList.length; i++) {
				if (curContextTabId === this.pageList[i].fullPath) {
					currTabIndex = i;
					break;
				}
			}
			this.activePage = this.linkList[currTabIndex];
			this.$router.push(this.activePage);
			this.closeContextMenu()
		},
		// 关闭所有标签页
		closeAllTabs() {
			//删除所有tab标签
			this.linkList.splice(1, this.linkList.length)
			this.pageList.splice(1, this.pageList.length)
			this.activePage = this.linkList[0];
			this.$router.push(this.activePage);
			this.closeContextMenu()
		},
		// 关闭其它标签页
		closeOtherTabs(par) {
			let currTabIndex = 0;
			let curContextTabId = this.$store.state.tagsView.curContextTabId;
			for (let i = 0; i < this.pageList.length; i++) {
				if (curContextTabId === this.pageList[i].fullPath) {
					currTabIndex = i;
					break;
				}
			}
			switch (par) {
				case "left": {
					//删除左侧tab标签
					this.pageList.splice(0, currTabIndex);
					this.linkList.splice(0, currTabIndex);
					this.activePage = this.linkList[0];
					this.$router.push(this.activePage);
					break;
				}
				case "right": {
					//删除右侧tab标签
					this.pageList.splice(currTabIndex + 1, this.pageList.length);
					this.linkList.splice(currTabIndex + 1, this.linkList.length);
					this.activePage = this.linkList[currTabIndex];
					this.$router.push(this.activePage);
					break;
				}
				case "other": {
					//删除其他所有tab标签
					this.pageList.splice(0, currTabIndex);
					this.linkList.splice(0, currTabIndex);
					this.pageList.splice(1, this.pageList.length);
					this.linkList.splice(1, this.linkList.length);
					this.activePage = this.linkList[0];
					this.$router.push(this.activePage);
					break;
				}
			}
			this.closeContextMenu()
		},
		// 关闭contextMenu
		closeContextMenu() {
			this.contextMenuVisible = false;
		},
	}
}
</script>

<style scoped>

/deep/ .el-tabs--card > .el-tabs__header .el-tabs__item {
	height: 30px;
	line-height: 30px;
	font-size: 12px;
}

/deep/ .el-tabs__nav-next {
	line-height: 33px;
	font-size: 20px;
}

/deep/ .el-tabs__nav-prev {
	line-height: 33px;
	font-size: 20px;
}

.contextmenu {
	width: 100px;
	margin: 0;
	border: 1px solid #ccc;
	background: #fff;
	z-index: 3000;
	position: absolute;
	list-style-type: none;
	padding: 5px 0;
	border-radius: 4px;
	font-size: 14px;
	color: #333;
	box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.2);
}

.contextmenu li {
	margin: 0;
	padding: 0px 22px;

}

.contextmenu li:hover {
	background: #f2f2f2;
	cursor: pointer;
}

.contextmenu li button {
	color: #2c3e50;
}

</style>
