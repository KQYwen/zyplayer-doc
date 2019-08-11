<template>
    <div id="app">
        <template v-if="global.fullscreen">
            <router-view></router-view>
        </template>
        <el-container v-else>
            <el-aside>
                <div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
                    <el-tree :props="defaultProps" :data="databaseList" @node-click="handleNodeClick"
                             ref="databaseTree" highlight-current draggable
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
                            <el-dropdown-item command="aboutDoc">关于</el-dropdown-item>
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
                choiceDatasource: "",
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
            this.loadDatasourceList();
        },
        methods: {
            userSettingDropdown(command) {
                console.log("command:" + command);
                if (command == 'aboutDoc') {
                    app.aboutDialogVisible = true;
                } else {
                    toast.notOpen();
                }
            },
            datasourceChangeEvents() {
                app.nowDatasourceShow = this.choiceDatasource;
                app.loadDatabaseList(this.choiceDatasource);
            },
            handleNodeClick(node) {
                console.log("点击节点：", node);
                if (node.type == 2) {
                    this.nowClickPath = {host: node.host, dbName: node.dbName, tableName: node.tableName};
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
                var pathIndex = [];
                var result = docDbDatabase.tableList || [];
                for (var i = 0; i < result.length; i++) {
                    var item = {
                        id: result[i].tableName, host: "",
                        dbName: "", tableName: result[i].tableName, name: result[i].tableName, type: 2
                    };
                    // item.children = [{label: '', needLoad: true}];// 初始化一个对象，点击展开时重新查询加载
                    pathIndex.push(item);
                }
                node.children = pathIndex;
                if (typeof callback == 'function') {
                    callback(pathIndex);
                }
            },
            loadDatasourceList() {
                app.datasourceOptions = [{label: "数据源", value: ""}];
                this.datasourceChangeEvents();
            },
            loadDatabaseList(host, callback) {
                var pathIndex = [];

                pathIndex.push({
                    id: "1", host: host, dbName: "数据库表",
                    name: "数据库表", type: 1, children: [{label: '', needLoad: true}]
                });
                app.databaseList = pathIndex;
                if (typeof callback == 'function') {
                    callback();
                }
            },
            initLoadDataList(host, dbName) {
                if (app.databaseList.length > 0) {
                    return;
                }
                this.loadDatabaseList(host, function () {
                    app.databaseExpandedKeys = [host];
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
