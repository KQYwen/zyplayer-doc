<template>
	<div id="app">
		<template v-if="fullscreen">
			<router-view></router-view>
		</template>
		<el-container v-else>
			<el-aside style="background: #fafafa;" :style="{ width: rightAsideWidth + 'px' }">
				<div style="padding: 10px;height: 100%;box-sizing: border-box;">
					<div style="margin-bottom: 10px;">
						<el-select v-model="choiceDatasourceGroup" @change="sourceGroupChangeEvents" size="small" filterable placeholder="请先选择分组" style="width: 100%;">
							<el-option value="" label="全部分组"></el-option>
							<el-option v-for="item in datasourceGroupList" :key="item" :value="item"></el-option>
						</el-select>
						<el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" size="small" filterable placeholder="请先选择数据源" style="width: 100%;margin-top: 10px;">
							<el-option v-for="item in datasourceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</div>
					<el-menu :router="true" class="el-menu-vertical" style="height: auto;">
						<el-menu-item index="/data/datasourceManage"><i class="el-icon-coin"></i>数据源管理</el-menu-item>
						<el-menu-item index="/data/executor"><i class="el-icon-video-play"></i>SQL执行器</el-menu-item>
						<el-submenu index="1">
							<template slot="title">
								<i class="el-icon-s-platform"></i>
								<span slot="title">管理工具</span>
							</template>
							<el-menu-item index="/data/export"><i class="el-icon-finished"></i>数据库表导出</el-menu-item>
							<el-menu-item index="/data/transferData"><i class="el-icon-document-copy"></i>数据互导工具</el-menu-item>
						</el-submenu>
					</el-menu>
					<el-tree :props="defaultProps" :data="databaseList" @node-click="handleNodeClick"
							 ref="databaseTree" highlight-current empty-text=""
							 :default-expanded-keys="databaseExpandedKeys"
							 node-key="id" @node-expand="handleNodeExpand"
							 v-loading="databaseListLoading"
							 element-loading-text="数据库信息加载中"
							 class="database-list-tree">
						<div slot-scope="{node, data}">
							<span v-if="data.needLoad"><i class="el-icon-loading"></i></span>
							<span v-else>
								{{node.label}}
								<el-tooltip v-if="!!data.comment" effect="dark" :content="data.comment" placement="top-start" :open-delay="600">
									<span style="color: #aaa;">-{{data.comment}}</span>
								</el-tooltip>
								<el-dropdown v-if="data.type == 1" @command="databaseActionDropdown">
									<i class="el-icon-more" @click.stop=""></i>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item icon="el-icon-coin" :command="{command: 'procedure', node: node}">函数管理</el-dropdown-item>
										<el-dropdown-item icon="el-icon-refresh" :command="{command: 'refresh', node: node}">刷新</el-dropdown-item>
										<!--										<el-dropdown-item icon="el-icon-upload2" :command="{command: 'upload', node: node}">导入</el-dropdown-item>-->
										<!--										<el-dropdown-item icon="el-icon-download" :command="{command: 'download', node: node}">导出</el-dropdown-item>-->
									</el-dropdown-menu>
								</el-dropdown>
							</span>
						</div>
					</el-tree>
				</div>
			</el-aside>
			<div ref="rightResize" class="right-resize">
				<i ref="rightResizeBar">...</i>
			</div>
			<el-container>
				<el-header>
					<span class="header-right-user-name">{{userSelfInfo.userName}}</span>
					<el-dropdown @command="userSettingDropdown" trigger="click">
						<i class="el-icon-setting" style="margin-right: 15px; font-size: 16px;cursor: pointer;color: #fff;"> </i>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item command="console">控制台</el-dropdown-item>
							<el-dropdown-item command="aboutDoc" divided>关于</el-dropdown-item>
							<el-dropdown-item command="myInfo">我的资料</el-dropdown-item>
							<el-dropdown-item command="userSignOut">退出登录</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</el-header>
				<el-main style="padding: 0;">
					<router-view @initLoadDataList="initLoadDataList"
								 @loadDatasourceList="loadDatasourceList">
					</router-view>
				</el-main>
			</el-container>
		</el-container>
		<about-dialog ref="aboutDialog"></about-dialog>
	</div>
</template>

<script>
	import userApi from './common/api/user'
	import datasourceApi from './common/api/datasource'
	import aboutDialog from './views/common/AboutDialog'

	export default {
		data() {
			return {
				isCollapse: false,
				userSelfInfo: {},
				// 数据源相关
				datasourceOptions: [],
				datasourceList: [],
				datasourceGroupList: [],
				choiceDatasourceId: "",
				choiceDatasourceGroup: "",
				defaultProps: {children: 'children', label: 'name'},
				// 页面展示相关
				databaseList: [],
				databaseListLoading: false,
				databaseExpandedKeys: [],
				rightAsideWidth: 300,
			}
		},
		computed: {
			fullscreen () {
				return this.$store.state.global.fullscreen;
			}
		},
		components: {
			'about-dialog': aboutDialog
		},
		mounted: function () {
			this.getSelfUserInfo();
			this.loadDatasourceList();
			this.dragChangeRightAsideWidth();
		},
		methods: {
			userSettingDropdown(command) {
				console.log("command:" + command);
				if (command == 'userSignOut') {
					this.userSignOut();
				} else if (command == 'aboutDoc') {
					this.$refs.aboutDialog.show();
				} else if (command == 'myInfo') {
					this.$router.push({path: '/user/myInfo'});
				} else if (command == 'console') {
					window.open(process.env.VUE_APP_BASE_API, '_blank');
				} else {
					this.$message.warning("功能暂未开放");
				}
			},
			userSignOut() {
				userApi.userLogout().then(() => {
					location.reload();
				});
			},
			getSelfUserInfo() {
				userApi.getSelfUserInfo().then(json=>{
					this.userSelfInfo = json.data;
				});
			},
			sourceGroupChangeEvents() {
				let datasourceOptions = [];
				this.datasourceList.forEach(item => {
					if (!this.choiceDatasourceGroup || this.choiceDatasourceGroup == item.groupName) {
						datasourceOptions.push({label: item.name, value: item.id});
					}
				});
				this.datasourceOptions = datasourceOptions;
				this.choiceDatasourceId = '';
				this.databaseList = [];
			},
			datasourceChangeEvents() {
				let choiceSource = this.datasourceList.find(item => item.id == this.choiceDatasourceId);
				this.loadDatabaseList(this.choiceDatasourceId, choiceSource.name);
			},
			handleNodeClick(node) {
				console.log("点击节点：", node);
				// 执行器里面点击库表不跳转页面
				// if (this.$router.currentRoute.path == "/data/executor") {
				//     return;
				// }
				if (node.type == 1) {
					this.nowClickPath = {sourceId: this.choiceDatasourceId, host: node.host, dbName: node.dbName, tableName: node.tableName};
					this.$router.push({path: '/table/database', query: this.nowClickPath});
				} else if (node.type == 2) {
					this.nowClickPath = {sourceId: this.choiceDatasourceId, host: node.host, dbName: node.dbName, tableName: node.tableName};
					this.$router.push({path: '/table/info', query: this.nowClickPath});
				}
			},
			handleNodeExpand(node) {
				if (node.children.length > 0 && node.children[0].needLoad) {
					console.log("加载节点：", node);
					if (node.type == 1) {
						this.loadGetTableList(node);
					}
				}
			},
			databaseActionDropdown(param) {
				if (param.command == 'refresh') {
					param.node.loading = true;
					param.node.data.children = [];
					this.loadGetTableList(param.node.data, () => {
						setTimeout(() => param.node.loading = false, 500);
					});
				} else if (param.command == 'procedure') {
					let nodeData = param.node.data;
					let procedureParam = {sourceId: this.choiceDatasourceId,dbName: nodeData.dbName, host: nodeData.host};
					this.$router.push({path: '/procedure/list', query: procedureParam});
				} else {
					this.$message.warning("暂未支持的选项");
				}
			},
			loadGetTableList(node, callback) {
				datasourceApi.tableList({sourceId: this.choiceDatasourceId, dbName: node.dbName}).then(json => {
					let pathIndex = [];
					let result = json.data || [];
					for (let i = 0; i < result.length; i++) {
						let item = {
							id: node.host + "_" + node.dbName + "_" + result[i].tableName, host: node.host,
							dbName: node.dbName, tableName: result[i].tableName, name: result[i].tableName, type: 2,
							comment: result[i].tableComment
						};
						// item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
						pathIndex.push(item);
					}
					node.children = pathIndex;
					if (typeof callback == 'function') {
						callback(pathIndex);
					}
				});
			},
			loadDatasourceList() {
				datasourceApi.datasourceList({}).then(json => {
					this.datasourceList = json.data || [];
					if (this.datasourceList.length <= 0) {
						return;
					}
					let datasourceOptions = [];
					this.datasourceList.forEach(item => datasourceOptions.push({label: item.name, value: item.id}));
					this.datasourceOptions = datasourceOptions;
					let datasourceGroupList = [];
					this.datasourceList.filter(item => !!item.groupName).forEach(item => datasourceGroupList.push(item.groupName));
					this.datasourceGroupList = Array.from(new Set(datasourceGroupList));
				});
			},
			loadDatabaseList(sourceId, host) {
				return new Promise((resolve, reject) => {
					this.databaseList = [];
					this.databaseListLoading = true;
					datasourceApi.databaseList({sourceId: sourceId}).then(json => {
						this.databaseListLoading = false;
						let result = json.data || [];
						let pathIndex = [];
						let children = [];
						for (let i = 0; i < result.length; i++) {
							let item = {
								id: host + "_" + result[i].dbName, host: host, dbName: result[i].dbName,
								name: result[i].dbName, type: 1
							};
							item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
							children.push(item);
						}
						pathIndex.push({id: host, host: host, name: host, children: children});
						this.databaseList = pathIndex;
						resolve();
					}).catch(e => {
						this.choiceDatasourceId = '';
						this.databaseListLoading = false;
					});
				});
			},
			initLoadDataList(param) {
				if (this.databaseList.length > 0) {
					return;
				}
				this.choiceDatasourceId = parseInt(param.sourceId);
				this.loadDatabaseList(param.sourceId, param.host).then(() => {
					this.databaseExpandedKeys = [param.host];
				});
			},
			dragChangeRightAsideWidth: function() {
				// 保留this引用
				let resize = this.$refs.rightResize;
				let resizeBar = this.$refs.rightResizeBar;
				resize.onmousedown = e => {
					let startX = e.clientX;
					// 颜色改变提醒
					resize.style.background = "#ccc";
					resizeBar.style.background = "#aaa";
					resize.left = resize.offsetLeft;
					document.onmousemove = e2 => {
						// 计算并应用位移量
						let endX = e2.clientX;
						let moveLen = startX - endX;
						if ((moveLen < 0 && this.rightAsideWidth < 600) || (moveLen > 0 && this.rightAsideWidth > 200)) {
							startX = endX;
							this.rightAsideWidth -= moveLen;
						}
					};
					document.onmouseup = () => {
						// 颜色恢复
						resize.style.background = "#fafafa";
						resizeBar.style.background = "#ccc";
						document.onmousemove = null;
						document.onmouseup = null;
					};
					return false;
				};
			}
		}
	}
</script>

<style>
	html, body {
		margin: 0;
		padding: 0;
		height: 100%;
	}
	#app, .el-container, .el-menu {
		height: 100%;
	}
	.el-header {
		background-color: #1D4E89 !important;
	}
	.database-list-tree{background-color: #fafafa;overflow-x: auto;min-height: 150px;}
	.database-list-tree .el-loading-mask{background-color: #fafafa;}
	.database-list-tree .el-tree-node>.el-tree-node__children {
		overflow: unset;
	}
	.header-right-user-name{color: #fff;padding-right: 5px;}
	.el-menu-vertical{border-right: 0;background: #fafafa;}
	.el-menu-vertical .el-menu{background: #fafafa;}
	.el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}

	.el-tree-node__content{}
	.el-tree-node__content .el-icon-more{margin-left: 5px;color: #606266;font-size: 12px; display: none;padding: 2px 5px;}
	.el-tree-node__content:hover .el-icon-more{display: inline-block;}

	.right-resize {
		width: 5px;
		height: 100%;
		cursor: w-resize;
		background: #fafafa;
	}
	.right-resize i{
		margin-top: 300px;
		width: 5px;
		height: 35px;
		display: inline-block;
		word-wrap: break-word;
		word-break: break-all;
		line-height: 8px;
		border-radius: 5px;
		background: #ccc;
		color: #888;
	}
</style>
