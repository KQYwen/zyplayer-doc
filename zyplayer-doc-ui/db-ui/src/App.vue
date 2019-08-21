<template>
    <div id="app">
        <template v-if="global.fullscreen">
            <router-view></router-view>
        </template>
        <el-container v-else>
            <el-aside>
                <div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
                    <div style="margin-bottom: 10px;">
                        <el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" filterable placeholder="请先选择数据源" style="width: 100%;">
                            <el-option v-for="item in datasourceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </div>
                    <el-menu :router="true" class="el-menu-vertical" style="height: auto;">
                        <el-menu-item index="/"><i class="el-icon-s-home"></i>控制台</el-menu-item>
                        <el-submenu index="1">
                            <template slot="title">
                                <i class="el-icon-s-platform"></i>
                                <span slot="title">系统管理</span>
                            </template>
                            <el-menu-item index="/data/datasourceManage"><i class="el-icon-coin"></i>数据源管理</el-menu-item>
                            <el-menu-item index="/data/export"><i class="el-icon-finished"></i>数据库表导出</el-menu-item>
                            <el-menu-item index="/data/executor"><i class="el-icon-video-play"></i>SQL执行器</el-menu-item>
                        </el-submenu>
                    </el-menu>
                    <el-tree :props="defaultProps" :data="databaseList" @node-click="handleNodeClick"
                             ref="databaseTree" highlight-current
                             :default-expanded-keys="databaseExpandedKeys"
                             node-key="id" @node-expand="handleNodeExpand"
                             style="background-color: #fafafa;">
                        <span slot-scope="{node, data}">
                            <span v-if="data.needLoad"><i class="el-icon-loading"></i></span>
                            <span v-else>{{node.label}}</span>
                        </span>
                    </el-tree>
                </div>
            </el-aside>
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
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
        <!--关于弹窗-->
        <el-dialog title="关于zyplayer-doc" :visible.sync="aboutDialogVisible" width="600px">
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
    import global from './common/config/global'
    import toast from './common/lib/common/toast'

    var app;
    export default {
        data() {
            return {
                isCollapse: false,
                aboutDialogVisible: false,
                userSelfInfo: {},
                // 数据源相关
                datasourceOptions: [],
                datasourceList: [],
                choiceDatasourceId: "",
                defaultProps: {children: 'children', label: 'name'},
                // 页面展示相关
                nowDatasourceShow: {},
                databaseList: [],
                databaseExpandedKeys: [],
                // 升级信息
                upgradeInfo: {},
            }
        },
        mounted: function () {
            app = this;
            global.vue.$app = this;
            this.getSelfUserInfo();
            this.checkSystemUpgrade();
            this.loadDatasourceList();
        },
        methods: {
            userSettingDropdown(command) {
                console.log("command:" + command);
                if (command == 'userSignOut') {
                    this.userSignOut();
                } else if (command == 'aboutDoc') {
                    app.aboutDialogVisible = true;
                } else if (command == 'myInfo') {
                    this.$router.push({path: '/user/myInfo'});
                } else if (command == 'console') {
                    window.location = this.apilist1.HOST;
                } else {
                    toast.notOpen();
                }
            },
            userSignOut() {
                this.common.post(this.apilist1.userLogout, {}, function (json) {
                    location.reload();
                });
            },
            getSelfUserInfo() {
                this.common.post(this.apilist1.getSelfUserInfo, {}, function (json) {
                    app.userSelfInfo = json.data;
                });
            },
            datasourceChangeEvents() {
                app.nowDatasourceShow = this.choiceDatasourceId;
                var host = "";
                for (var i = 0; i < this.datasourceList.length; i++) {
                    if (this.datasourceList[i].id == this.choiceDatasourceId) {
                        host = this.datasourceList[i].cnName;
                        break;
                    }
                }
                app.loadDatabaseList(this.choiceDatasourceId, host);
            },
            handleNodeClick(node) {
                console.log("点击节点：", node);
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
                        app.loadGetTableList(node);
                    }
                }
            },
            loadGetTableList(node, callback) {
                this.common.post(this.apilist1.tableList, {sourceId: this.choiceDatasourceId, dbName: node.dbName}, function (json) {
                    var pathIndex = [];
                    var result = json.data || [];
                    for (var i = 0; i < result.length; i++) {
                        var item = {
                            id: node.host + "_" + node.dbName + "_" + result[i].tableName, host: node.host,
                            dbName: node.dbName, tableName: result[i].tableName, name: result[i].tableName, type: 2
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
                this.common.post(this.apilist1.datasourceList, {}, function (json) {
                    app.datasourceList = json.data || [];
                    var datasourceOptions = [];
                    for (var i = 0; i < app.datasourceList.length; i++) {
                        datasourceOptions.push({
                            label: app.datasourceList[i].cnName, value: app.datasourceList[i].id
                        });
                    }
                    app.datasourceOptions = datasourceOptions;
                });
            },
            loadDatabaseList(sourceId, host, callback) {
                this.common.post(this.apilist1.databaseList, {sourceId: sourceId}, function (json) {
                    var result = json.data || [];
                    var pathIndex = [];
                    var children = [];
                    for (var i = 0; i < result.length; i++) {
                        var item = {
                            id: host + "_" + result[i].dbName, host: host, dbName: result[i].dbName,
                            name: result[i].dbName, type: 1
                        };
                        item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
                        children.push(item);
                    }
                    pathIndex.push({id: host, host: host, name: host, children: children});
                    app.databaseList = pathIndex;
                    if (typeof callback == 'function') {
                        callback();
                    }
                });
            },
            initLoadDataList(sourceId, host, dbName) {
                if (app.databaseList.length > 0) {
                    return;
                }
                this.choiceDatasourceId = sourceId;
                this.loadDatabaseList(sourceId, host, function () {
                    app.databaseExpandedKeys = [host];
                });
            },
            checkSystemUpgrade() {
                this.common.post(this.apilist1.systemUpgradeInfo, {}, function (json) {
                    if (!!json.data) {
                        app.upgradeInfo = json.data;
                        console.log("zyplayer-doc发现新版本："
                            + "\n升级地址：" + json.data.upgradeUrl
                            + "\n当前版本：" + json.data.nowVersion
                            + "\n最新版本：" + json.data.lastVersion
                            + "\n升级内容：" + json.data.upgradeContent
                        );
                    }
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
    .header-right-user-name{color: #fff;padding-right: 5px;}
    .el-menu-vertical{border-right: 0;background: #fafafa;}
    .el-menu-vertical .el-menu{background: #fafafa;}
    .el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}
</style>
