<template>
    <div id="app">
        <template v-if="global.fullscreen">
            <router-view></router-view>
        </template>
        <el-container v-else>
            <el-aside width="200px">
                <div style="padding: 10px;" v-show="leftCollapse">
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
                    <el-input v-model="searchKeywords" @keyup.enter.native="searchByKeywords" placeholder="搜索文档" style="margin: 10px 0;">
                        <el-button slot="append" icon="el-icon-search" v-on:click="searchByKeywords"></el-button>
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
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    var app;
    export default {
        data() {
            return {
                leftCollapse: true,
                aboutDialogVisible: false,
                rightContentLoading: false,
                rightContentType: 0,// 右侧显示类型，0=欢迎页 1=文章内容 2=编辑或新增文章
                pathIndex: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                // 空间搜索相关
                spaceOptions: [],
                spaceList:[],
                choiceSpace: "",
                nowSpaceId: '',
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
                // 依据目录树存储的map全局对象
                treePathDataMap: new Map(),
                // 搜索的输入内容
                searchKeywords: "",
                lastClickNode: {},
                // 编辑相关
                newPageId: "",
                newPageTitle: "",
                // 页面展示相关
                wikiPageList:[],
                wikiPage: {},
                wikiPageExpandedKeys: [],
                pageContent: {},
                pageFileList: [],
                uploadFileList: [],
                uploadFormData: {pageId: 0},
                zanUserDialogVisible: false,
                zanUserList: [],
                // 评论相关
                commentTextInput: "",
                commentList: [],
                recommentInfo: {},
                // 页面跳转相关
                initOver: false,
                doNotPushState: false,
                urlParamPageId: 0,
                urlParam: {},
                // 升级信息
                upgradeInfo: {},
            }
        },
        mounted: function () {
            app = this;
            this.loadSpaceList();
        },
        methods: {
            createWiki() {

            },
            searchByKeywords() {
                this.$refs.wikiPageTree.filter(app.searchKeywords);
            },
            handleNodeClick(data) {
                app.rightContentType = 1;
                if (app.lastClickNode.id == data.id) {
                    return;
                }
                console.log("点击节点：", data);
                app.lastClickNode = data;
                app.urlParamPageId = app.lastClickNode.id;
            },
            handleNodeExpand(node) {
                if (node.children.length > 0 && node.children[0].needLoad) {
                    console.log("加载节点：", node);
                    app.doGetPageList(node.id, node);
                }
            },
            handlePageDrop(draggingNode, dropNode, dropType, ev) {
                //console.log('tree drop: ', draggingNode.data, dropNode.data, dropType);
                // 'prev'、'inner'、'next'
                var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId};
                if (dropType == 'inner') {
                    param.parentId = dropNode.data.id;
                }
                this.common.post(this.apilist1.pageUpdate, param, function (json) {
                    app.doGetPageList(null);
                });
            },
            filterPageNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            spaceChangeEvents(data) {
                if (data == 0) {
                    app.newSpaceForm = {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1};
                    app.choiceSpace = app.nowSpaceId;
                    app.newSpaceDialogVisible = true;
                } else if (data == -1) {
                    app.choiceSpace = app.nowSpaceId;
                    app.manageSpaceDialogVisible = true;
                } else {
                    app.nowSpaceId = data;
                    app.rightContentType = 0;
                    for (var i = 0; i < app.spaceList.length; i++) {
                        if (app.spaceList[i].id == data) {
                            app.nowSpaceShow = app.spaceList[i];
                            break;
                        }
                    }
                    app.doGetPageList(null);
                }
            },
            loadSpaceList() {
                this.common.post(this.apilist1.spaceList, {}, function (json) {
                    app.spaceList = json.data || [];
                    var spaceOptions = [];
                    for (var i = 0; i < app.spaceList.length; i++) {
                        spaceOptions.push({
                            label: app.spaceList[i].name, value: app.spaceList[i].id
                        });
                    }
                    app.spaceOptions = spaceOptions;
                    if (app.spaceList.length > 0) {
                        app.nowSpaceId = app.spaceList[0].id;
                        app.nowSpaceShow = app.spaceList[0];
                        app.choiceSpace = app.nowSpaceId;
                        app.doGetPageList(null);
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
                var param = {spaceId: this.nowSpaceId, parentId: parentId || 0};
                if (app.nowSpaceShow.treeLazyLoad == 0) {
                    param.parentId = null;
                }
                this.common.post(this.apilist1.pageList, param, function (json) {
                    var result = json.data || [];
                    var pathIndex = [];
                    if (app.nowSpaceShow.treeLazyLoad == 0) {
                        pathIndex = result;
                    } else {
                        for (var i = 0; i < result.length; i++) {
                            var item = result[i];
                            item.parentId = item.parentId || 0;
                            item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
                            pathIndex.push(item);
                        }
                    }
                    app.createNodePath(pathIndex, nodePath);
                    if (parentId > 0) {
                        node.children = pathIndex;
                    } else {
                        app.wikiPageList = pathIndex;
                        //app.lastClickNode = {};
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
</style>
