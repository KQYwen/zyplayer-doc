<template>
    <div>
        <el-container>
            <el-aside v-show="leftCollapse">
                <div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
                    <div style="margin-bottom: 10px;">
                        <el-select v-model="choiceSpace" @change="spaceChangeEvents" filterable placeholder="选择空间" style="width: 100%;">
                            <el-option-group label="">
                                <el-option key="0" label="创建空间" value="0"></el-option>
                                <el-option key="-1" label="空间管理" value="-1"></el-option>
                            </el-option-group>
                            <el-option-group label="">
                                <el-option v-for="item in spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-option-group>
                        </el-select>
                    </div>
                    <div align="center">
                        <el-button v-on:click="createWiki" icon="el-icon-plus" style="width: 100%;">创建文档</el-button>
                    </div>
                    <el-input v-model="searchKeywords" @input="searchByKeywords" @keyup.enter.native="searchByKeywords" placeholder="搜索文档" style="margin: 10px 0;">
                        <el-button slot="append" icon="el-icon-search" v-on:click="searchByKeywordsNewPage"></el-button>
                    </el-input>
                    <el-tree :props="defaultProps" :data="wikiPageList" @node-click="handleNodeClick"
                             @node-expand="handleNodeExpand" draggable @node-drop="handlePageDrop"
                             ref="wikiPageTree" :filter-node-method="filterPageNode" highlight-current
                             :expand-on-click-node="false" :default-expanded-keys="wikiPageExpandedKeys"
                             node-key="id"
                             style="background-color: #fafafa;">
                    </el-tree>
                </div>
            </el-aside>
            <el-container>
                <el-header>
                    <!--<el-switch v-model="isCollapse" ></el-switch>-->
                    <i class="el-icon-menu icon-collapse" @click="leftCollapse = !leftCollapse"></i>
                    <!--<div class="logo" @click="aboutDialogVisible = true">zyplayer-doc-wiki</div>-->
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
					<router-view @loadPageList="loadPageList"
								 @changeExpandedKeys="changeWikiPageExpandedKeys"
								 @switchSpace="switchSpacePage">
					</router-view>
				</el-main>
			</el-container>
        </el-container>
        <!--新建空间弹窗-->
        <el-dialog title="创建空间" :visible.sync="newSpaceDialogVisible" width="600px" :close-on-click-modal="false">
            <el-form label-width="100px" :model="newSpaceForm" :rules="newSpaceFormRules" ref="newSpaceForm">
                <el-form-item label="空间名：" prop="name">
                    <el-input v-model="newSpaceForm.name"></el-input>
                </el-form-item>
                <el-form-item label="空间描述：" prop="spaceExplain">
                    <el-input v-model="newSpaceForm.spaceExplain"></el-input>
                </el-form-item>
                <el-form-item label="空间开放：">
                    <el-switch v-model="newSpaceForm.openDoc" inactive-text="需要登录" :inactive-value="0" active-text="开放访问" :active-value="1"></el-switch>
                </el-form-item>
                <el-form-item label="目录加载：">
                    <el-switch v-model="newSpaceForm.treeLazyLoad" inactive-text="预先加载" :inactive-value="0" active-text="延迟加载" :active-value="1"></el-switch>
                </el-form-item>
                <el-form-item label="空间类型：">
                    <el-select v-model="newSpaceForm.type" filterable placeholder="选择类型" style="width: 100%;">
                        <el-option :key="1" label="公共空间" :value="1">
                            <span style="float: left">公共空间</span>
                            <span style="float: right; color: #8492a6; font-size: 13px;">属于公共，登录用户可访问、编辑</span>
                        </el-option>
                        <el-option :key="2" label="个人空间" :value="2">
                            <span style="float: left">个人空间</span>
                            <span style="float: right; color: #8492a6; font-size: 13px;">属于个人，所有登录用户可访问</span>
                        </el-option>
                        <el-option :key="3" label="隐私空间" :value="3">
                            <span style="float: left">隐私空间</span>
                            <span style="float: right; color: #8492a6; font-size: 13px;">属于个人，仅创建者可访问</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-if="newSpaceForm.id > 0" @click="onNewSpaceSubmit('newSpaceForm')">保存修改</el-button>
                    <el-button type="primary" v-else @click="onNewSpaceSubmit('newSpaceForm')">立即创建</el-button>
                    <el-button @click="onNewSpaceCancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--管理空间弹窗-->
        <el-dialog title="管理空间" :visible.sync="manageSpaceDialogVisible" :close-on-click-modal="false" width="80%">
            <el-table :data="spaceList" border style="width: 100%; margin-bottom: 5px;" max-height="500">
                <el-table-column prop="id" label="ID" width="60"></el-table-column>
                <el-table-column prop="name" label="名字"></el-table-column>
                <el-table-column prop="spaceExplain" label="说明"></el-table-column>
                <el-table-column label="开放地址">
                    <template slot-scope="scope">
                        <a target="_blank" :href="'open-wiki.html?space='+scope.row.uuid" v-if="scope.row.openDoc == 1">{{scope.row.name}}</a>
                        <span v-else>暂未开放</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createUserName" label="创建人"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" type="primary" v-on:click="editSpaceInfo(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" v-on:click="deleteSpaceInfo(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <!--关于弹窗-->
        <el-dialog title="关于zyplayer-doc-wiki" :visible.sync="aboutDialogVisible" width="600px">
            <el-form>
                <el-form-item label="项目地址：">
                    <a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>
                </el-form-item>
                <el-form-item label="开发人员：">
                    <a target="_blank" href="http://zyplayer.com">暮光：城中城</a>
                </el-form-item>
                <template v-if="upgradeInfo.lastVersion">
                    <el-form-item label="当前版本：">{{upgradeInfo.nowVersion}}</el-form-item>
                    <el-form-item label="最新版本：">{{upgradeInfo.lastVersion}}</el-form-item>
                    <el-form-item label="升级地址：">
                        <a target="_blank" :href="upgradeInfo.upgradeUrl">{{upgradeInfo.upgradeUrl}}</a>
                    </el-form-item>
                    <el-form-item label="升级内容：">{{upgradeInfo.upgradeContent}}</el-form-item>
                </template>
                <el-form-item label="">
                    欢迎加群讨论，QQ群号：466363173，欢迎提交需求，欢迎使用和加入开发！
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import userApi from '../../common/api/user'
    import pageApi from '../../common/api/page'

    export default {
        data() {
            return {
                leftCollapse: true,
                aboutDialogVisible: false,
                rightContentLoading: false,
                pathIndex: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                // 空间搜索相关
                spaceOptions: [],
                spaceList:[],
                choiceSpace: "",
                nowSpaceShow: {},
                newSpaceDialogVisible: false,
                manageSpaceDialogVisible: false,
                newSpaceForm: {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1},
                newSpaceFormRules: {
                    name: [
                        {required: true, message: '请输入空间名', trigger: 'blur'},
                        {min: 2, max: 25, message: '长度在 2 到 25 个字符', trigger: 'blur'}
                    ],
                },
                nowClickPath: {
                    id: '',
                    path: '',
                },
                // 依据目录树存储的map全局对象
                treePathDataMap: new Map(),
                // 搜索的输入内容
                searchKeywords: "",
                // 页面展示相关
                wikiPageList:[],
                wikiPage: {},
                wikiPageExpandedKeys: [],
                // 升级信息
                upgradeInfo: {},
            }
        },
        computed: {
            fullscreen () {
                return this.$store.state.global.fullscreen;
            }
        },
        mounted: function () {
            this.loadSpaceList();
            this.checkSystemUpgrade();
        },
        methods: {
			loadPageList(param) {
				param = param || {};
				this.doGetPageList(param.parentId, param.node);
			},
            createWiki() {
                if (this.nowClickPath.spaceId > 0) {
                    var param = {
                        spaceId: this.nowClickPath.spaceId,
                        parentId: this.nowClickPath.parentId, path: this.nowClickPath.path
                    };
                    this.$router.push({path: '/page/edit', query: param});
                } else {
					this.$message.warning("请先选择或创建空间");
                }
            },
            changeWikiPageExpandedKeys(pageId) {
                this.wikiPageExpandedKeys = [pageId];
            },
            searchByKeywords() {
                this.$refs.wikiPageTree.filter(this.searchKeywords);
            },
            searchByKeywordsNewPage() {
                var routeUrl = this.$router.resolve({path: '/page/search', query: {keywords: this.searchKeywords}});
                window.open(routeUrl.href, '_blank');
            },
            handleNodeClick(data) {
                console.log("点击节点：", data);
                this.nowClickPath = {spaceId: this.nowClickPath.spaceId, pageId: data.id, parentId: data.id, path: data.path};
                this.$router.push({path: '/page/show', query: this.nowClickPath});
            },
            handleNodeExpand(node) {
                if (node.children.length > 0 && node.children[0].needLoad) {
                    console.log("加载节点：", node);
					this.doGetPageList(node.id, node);
                }
            },
            handlePageDrop(draggingNode, dropNode, dropType, ev) {
                console.log('tree drop: ', draggingNode.data, dropNode.data, dropType);
                // 'prev'、'inner'、'next'
                // before、after、inner
                var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId};
                if (dropType == 'inner') {
                    param.parentId = dropNode.data.id;
                } else if (dropType == 'before') {
                    param.beforeSeq = dropNode.data.seqNo;
                } else if (dropType == 'after') {
                    param.afterSeq = dropNode.data.seqNo;
                }
				pageApi.pageChangeParent(param).then(res => {
					this.doGetPageList(null);
				});
            },
            filterPageNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            editSpaceInfo(row) {
				this.newSpaceForm = {
                    id: row.id, name: row.name, spaceExplain: row.spaceExplain,
                    treeLazyLoad: row.treeLazyLoad, openDoc: row.openDoc, type: row.type
                };
				this.newSpaceDialogVisible = true;
            },
            deleteSpaceInfo(row) {
				this.$confirm('确定要删除此空间及下面的所有文档吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let param = {id: row.id, delFlag: 1};
					pageApi.updateSpace(param).then(() => {
						this.loadSpaceList();
					});
				});
            },
            spaceChangeEvents(data) {
                if (data == 0) {
					this.newSpaceForm = {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1};
					this.choiceSpace = this.nowClickPath.spaceId;
					this.newSpaceDialogVisible = true;
                } else if (data == -1) {
                    // 使得选择的空间展示不变
					this.choiceSpace = this.nowClickPath.spaceId;
					this.manageSpaceDialogVisible = true;
                } else {
                    // 切换空间，重新初始化当前点击项，防止创建保存到之前点击的空间下去了
					this.nowClickPath = {spaceId: data};
                    for (var i = 0; i < this.spaceList.length; i++) {
                        if (this.spaceList[i].id == data) {
							this.nowSpaceShow = this.spaceList[i];
                            break;
                        }
                    }
					this.doGetPageList(null);
					this.$router.push({path: '/home', query: {spaceId: data}});
                }
            },
            loadSpaceList() {
				pageApi.spaceList({}).then(json => {
					this.spaceList = json.data || [];
                    var spaceOptions = [];
                    for (var i = 0; i < this.spaceList.length; i++) {
                        spaceOptions.push({
                            label: this.spaceList[i].name, value: this.spaceList[i].id
                        });
                    }
					this.spaceOptions = spaceOptions;
                    if (this.spaceList.length > 0) {
                        var spaceId = this.spaceList[0].id;
                        this.nowSpaceShow = this.spaceList[0];
                        this.nowClickPath = {spaceId: spaceId};
                        this.choiceSpace = spaceId;
                        this.doGetPageList(null);
                        // TODO 在首页时跳转
                        try {
                            if (this.$router.app._route.path == "/home") {
                                this.$router.push({path: '/home', query: {spaceId: spaceId}});
                            }
                        } catch (e) {
                            console.log(e);
                        }
                    }
                });
            },
            doGetPageList(parentId, node) {
                var nodePath = "";
                if (!!node) {
                    nodePath = node.nodePath || "/";
                    if (!nodePath.endsWith("/")) {
                        nodePath += "/";
                    }
                } else {
                    nodePath = "/";
                }
                var param = {spaceId: this.nowClickPath.spaceId, parentId: parentId || 0};
                if (this.nowSpaceShow.treeLazyLoad == 0) {
                    param.parentId = null;
                }
				pageApi.pageList(param).then(json => {
					var result = json.data || [];
					var pathIndex = [];
					if (this.nowSpaceShow.treeLazyLoad == 0) {
						pathIndex = result;
					} else {
                        for (var i = 0; i < result.length; i++) {
                            var item = result[i];
                            item.parentId = item.parentId || 0;
                            item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
                            pathIndex.push(item);
                        }
                    }
					this.createNodePath(pathIndex, nodePath);
                    if (parentId > 0) {
                        node.children = pathIndex;
                    } else {
						this.wikiPageList = pathIndex;
                        //this.lastClickNode = {};
                    }
                });
            },
            createNodePath(node, nodePath) {
                if (!nodePath.endsWith("/")) {
                    nodePath += "/";
                }
                for (var i = 0; i < node.length; i++) {
                    var item = node[i];
                    item.nodePath = nodePath + item.name;
                    if (!!item.children && item.children.length > 0) {
                        this.createNodePath(item.children, item.nodePath);
                    }
                }
            },
            userSettingDropdown(command) {
                console.log("command:" + command);
                if (command == 'userSignOut') {
                    this.userSignOut();
                } else if (command == 'aboutDoc') {
					this.aboutDialogVisible = true;
                } else if (command == 'myInfo') {
                    this.$router.push({path: '/user/myInfo'});
                } else if (command == 'console') {
                    window.location = process.env.VUE_APP_BASE_API;
                } else {
					this.$message.warning("暂未开放");
                }
            },
            userSignOut() {
				userApi.userLogout().then(() => {
					location.reload();
				});
            },
            onNewSpaceSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var param = {
                            id: this.newSpaceForm.id,
                            name: this.newSpaceForm.name,
                            type: this.newSpaceForm.type,
                            openDoc: this.newSpaceForm.openDoc,
                            spaceExplain: this.newSpaceForm.spaceExplain,
                            treeLazyLoad: this.newSpaceForm.treeLazyLoad,
                        };
						pageApi.updateSpace(param).then(json => {
							if (param.id > 0) {
								this.loadSpaceList();
							} else {
                                this.spaceList.push(json.data);
                                this.spaceOptions.push({
                                    label: json.data.name, value: json.data.id
                                });
                                this.nowSpaceShow = json.data;
                                this.nowClickPath = {spaceId: json.data.id};
                                this.choiceSpace = json.data.id;
                                this.doGetPageList(null);
                            }
                            this.newSpaceForm = {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1};
                            this.newSpaceDialogVisible = false;
                        });
                    }
                });
            },
            onNewSpaceCancel() {
                this.newSpaceDialogVisible = false;
            },
            checkSystemUpgrade() {
				userApi.systemUpgradeInfo({}).then(json => {
                    if (!!json.data) {
                        this.upgradeInfo = json.data;
                        console.log("zyplayer-doc发现新版本："
                            + "\n升级地址：" + json.data.upgradeUrl
                            + "\n当前版本：" + json.data.nowVersion
                            + "\n最新版本：" + json.data.lastVersion
                            + "\n升级内容：" + json.data.upgradeContent
                        );
                    }
                });
            },
            switchSpacePage(spaceId) {
                spaceId = parseInt(spaceId);
                if (this.choiceSpace == spaceId) {
                    return;
                }
				this.choiceSpace = spaceId;
				this.nowClickPath.spaceId = spaceId;
                this.doGetPageList(null);
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
	.el-header {
		background-color: #1D4E89 !important;
	}
    .el-header {color: #333; line-height: 40px; text-align: right;height: 40px !important;}
    .icon-collapse{float: left;font-size: 25px;color: #aaa;margin-top: 8px;cursor: pointer;}
    .icon-collapse:hover{color: #eee;}
</style>
