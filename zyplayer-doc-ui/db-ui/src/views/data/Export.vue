<template>
    <div style="padding: 0 10px;height: 100%;box-sizing: border-box;">
        <el-card>
            <div slot="header" class="clearfix">
                <span>数据库表导出</span>
            </div>
            <div style="margin-bottom: 10px;">
                <el-select v-model="choiceDatasourceId" @change="datasourceChangeEvents" filterable placeholder="请选择数据源">
                    <el-option v-for="item in datasourceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
                <el-select v-model="choiceDatabase" @change="databaseChangeEvents" filterable placeholder="请选择数据库" style="margin: 0 10px;">
                    <el-option v-for="item in databaseList" :key="item.dbName" :label="item.dbName" :value="item.dbName"></el-option>
                </el-select>
                <el-radio-group v-model="exportType">
                    <el-radio :label="1">HTML格式</el-radio>
                    <el-radio :label="2">Excel格式</el-radio>
                    <el-radio :label="3">Word格式</el-radio>
                </el-radio-group>
                <el-button v-on:click="exportChoiceTable" type="primary" style="margin: 0 10px 0 20px;">导出选中的表</el-button>
                <a target="_blank" title="点击查看如何使用" href="http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=117&space=23f3f59a60824d21af9f7c3bbc9bc3cb"><i class="el-icon-info" style="color: #999;"></i></a>
            </div>
            <el-table :data="tableList" stripe border @selection-change="handleSelectionChange" style="width: 100%; margin-bottom: 5px;">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="tableName" label="表名"></el-table-column>
                <el-table-column prop="tableComment" label="表注释"></el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
    import datasourceApi from '../../common/api/datasource'

    var app;
    export default {
        data() {
            return {
                // 数据源相关
                datasourceOptions: [],
                datasourceList: [],
                choiceDatasourceId: "",
                choiceDatabase: "",
                choiceTable: "",
                exportType: 1,
                // 页面展示相关
                nowDatasourceShow: {},
                databaseList: [],
                tableList: [],
                selectTables: [],
            }
        },
        mounted: function () {
            app = this;
            this.loadDatasourceList();
        },
        methods: {
            datasourceChangeEvents() {
                app.nowDatasourceShow = this.choiceDatasourceId;
                app.loadDatabaseList(this.choiceDatasourceId);
            },
            databaseChangeEvents() {
                app.loadGetTableList();
            },
            exportChoiceTable() {
				if (this.selectTables.length <= 0) {
					app.$message.info("请选择需要导出的表");
					return;
				}
				var tableNames = "";
				for (var i = 0; i < this.selectTables.length; i++) {
					if (tableNames !== "") {
						tableNames += ",";
					}
					tableNames += this.selectTables[i].tableName;
				}
				window.open("zyplayer-doc-db/doc-db/exportDatabase?sourceId=" + this.choiceDatasourceId
					+ "&exportType=" + this.exportType
					+ "&dbName=" + this.choiceDatabase
					+ "&tableNames=" + tableNames);
            },
            loadGetTableList() {
                datasourceApi.tableList({sourceId: this.choiceDatasourceId, dbName: this.choiceDatabase}).then(json => {
                    this.tableList = json.data || [];
                });
            },
            loadDatasourceList() {
                datasourceApi.datasourceList({}).then(json => {
                    this.datasourceList = json.data || [];
                    let datasourceOptions = [];
                    for (let i = 0; i < this.datasourceList.length; i++) {
                        datasourceOptions.push({
                            label: this.datasourceList[i].name, value: this.datasourceList[i].id
                        });
                    }
                    this.datasourceOptions = datasourceOptions;
                });
            },
            loadDatabaseList() {
                datasourceApi.databaseList({sourceId: this.choiceDatasourceId}).then(json => {
                    this.databaseList = json.data || [];
                });
            },
            handleSelectionChange(val) {
                this.selectTables = val;
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
    .header-right-user-name{color: #fff;padding-right: 5px;}
    .el-menu-vertical{border-right: 0;background: #fafafa;}
    .el-menu-vertical .el-menu{background: #fafafa;}
    .el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}
</style>
