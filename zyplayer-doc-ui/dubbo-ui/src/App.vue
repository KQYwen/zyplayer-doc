<template>
    <div id="app">
        <template v-if="fullscreen">
            <router-view></router-view>
        </template>
        <el-container v-else>
            <el-aside style="background: #fff;">
                <div style="padding: 10px;height: 100%;box-sizing: border-box;">
					<div align="center">
						<el-dropdown split-button type="primary" @command="dropdownCommand" style="width: 280px;">
							<el-upload class="upload-page-file" action="zyplayer-doc-dubbo/doc-dubbo/uploadDocJar"
									   :on-success="uploadFileSuccess" :on-error="uploadFileError"
									   name="file" :show-file-list="false" :limit="999">
								<el-button type="primary" icon="el-icon-upload" style="width: 250px;">上传文档JAR</el-button>
							</el-upload>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item command="reload" icon="el-icon-refresh">重新加载服务列表</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</div>
					<el-input v-model="searchKeywords" placeholder="搜索文档" style="margin: 10px 0;">
						<el-button slot="append" icon="el-icon-search" v-on:click="searchByKeywords"></el-button>
					</el-input>
<!--                    <el-menu :router="true" class="el-menu-vertical" style="height: auto;">-->
<!--                        <el-menu-item index="/doc/dubboDocView"><i class="el-icon-coin"></i>系统管理</el-menu-item>-->
<!--                    </el-menu>-->
                    <div style="overflow: auto;padding-bottom: 30px;">
						<el-tree :data="pathIndex" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
                    </div>
                </div>
            </el-aside>
            <el-container>
                <el-header>
					<header-right></header-right>
                </el-header>
				<el-main style="padding: 0;box-shadow: rgba(0, 0, 0, 0.1) 0 0 8px;">
					<router-view :dubboDocMap="dubboDocMap" :treePathDataMap="treePathDataMap"></router-view>
				</el-main>
			</el-container>
		</el-container>
    </div>
</template>

<script>
    import dubboApi from './common/api/dubbo'
    import docTree from './common/js/doc-dubbo-tree'
    import headerRight from './views/user/HeaderRight'

    export default {
		components: {
			'header-right': headerRight
		},
        data() {
            return {
                isCollapse: false,
				pathIndex: [],
				defaultProps: {children: 'children', label: 'label'},
				// 展示的信息
				dubboInfo: {},
				// 请求的IP端口下拉选项
				requestHostOptions: [],
				// 依据目录树存储的map全局对象
				treePathDataMap: new Map(),
				// dubbo列表
				dubboDocList: [],
				dubboDocMap: [],
				// 搜索的输入内容
				searchKeywords: "",
            }
        },
		computed: {
			fullscreen () {
				return this.$store.state.global.fullscreen;
			}
		},
        mounted: function () {
			this.doGetServiceList();
        },
        methods: {
			handleNodeClick(data) {
				if (data.children == null) {
					// console.log(data);
					//console.log(this.dubboInfo);
					this.$router.push({
						path: '/doc/dubboDocView',
						query: {path: data.interface, method: data.method, application: data.application}
					});
				}
			},
			uploadFileError(err) {
				this.$message({message: "上传失败，" + err, type: 'error'});
			},
			uploadFileSuccess(response) {
				if (response.errCode == 200) {
					this.$message({message: "上传成功！", type: 'success'});
				} else {
					this.$message({message: "上传失败，" + response.errMsg, type: 'error'});
				}
			},
			reloadService() {
				dubboApi.reloadService().then(json => {
					this.$message({message: '加载成功！', type: 'success'});
					this.doGetServiceList();
				});
			},
			dropdownCommand(command) {
				if(command == 'reload') {
					this.reloadService();
				}
			},
			searchByKeywords() {
				this.pathIndex = docTree.createTreeViewByTreeWithMerge(this.treePathDataMap, this.dubboDocList, this.searchKeywords);
			},
			doGetServiceList() {
				dubboApi.getDocList({}).then(json => {
					this.dubboDocList = json.data.serverList || [];
					this.dubboDocMap = json.data.docMap || {};
					this.pathIndex = docTree.createTreeViewByTreeWithMerge(this.treePathDataMap, this.dubboDocList);
				});
			},
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
	.el-menu {
		box-sizing: border-box;
		border-right: 0;
		margin-right: 3px;
	}
	.el-tree{
		margin-right: 3px;
	}
	.el-tree-node__content{
		padding-right: 20px;
	}
	.el-header {
		background-color: #1D4E89 !important;
	}
	.el-button-group>.el-button:first-child{padding: 0!important;border: 0;}
</style>
