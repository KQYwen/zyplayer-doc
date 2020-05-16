<template>
    <div class="data-executor-vue">
        <div style="padding: 0 10px 10px;height: 100%;box-sizing: border-box;">
            <el-card style="margin-bottom: 10px;">
                <pre id="sqlExecutorEditor" style="width: 100%;height: 500px;margin-top: 0;"></pre>
                <div>
                    <el-button v-if="sqlExecuting" v-on:click="cancelExecutorSql" type="primary" plain  size="small" icon="el-icon-video-pause">取消执行</el-button>
                    <el-tooltip v-else effect="dark" content="Ctrl+R、Ctrl+Enter" placement="top">
                        <el-button v-on:click="doExecutorClick" type="primary" plain  size="small" icon="el-icon-video-play">执行</el-button>
                    </el-tooltip>
                    <el-button icon="el-icon-brush" size="small" @click="formatterSql">SQL美化</el-button>
                    <el-button icon="el-icon-refresh-left" size="small" @click="refreshData">重置</el-button>
                </div>
            </el-card>
            <el-card>
                <div v-if="!!executeError" style="color: #f00;">{{executeError}}</div>
                <div v-else-if="sqlExecuting" v-loading="sqlExecuting" style="padding: 20px 0;">数据加载中...</div>
                <div v-else-if="executeResultList.length <= 0" v-loading="sqlExecuting" style="padding: 20px 0;">暂无数据</div>
                <div v-else>
                    <el-tabs :value="executeShowTable">
                        <el-tab-pane label="信息" name="table0">
                            <pre>{{executeResultInfo}}</pre>
                        </el-tab-pane>
                        <el-tab-pane :label="'结果'+resultItem.index" :name="'table'+resultItem.index" v-for="resultItem in executeResultList" v-if="!!resultItem.index">
                            <div v-if="!!resultItem.errMsg" style="color: #f00;">{{resultItem.errMsg}}</div>
<!--                            <pl-table v-else-->
<!--                                      :datas="resultItem.dataList"-->
<!--                                      :height="tableMaxHeight"-->
<!--                                      header-drag-style-->
<!--                                      :row-height="40">-->
<!--                                <template slot="empty">暂无数据</template>-->
<!--                                <pl-table-column width="60px" v-if="resultItem.dataCols.length > 0">-->
<!--                                    <template slot-scope="scope">{{scope.row._index}}</template>-->
<!--                                </pl-table-column>-->
<!--                                <pl-table-column sortable v-for="item in resultItem.dataCols" :prop="item.prop" :label="item.prop" :width="item.width">-->
<!--                                    <template slot-scope="scope">-->
<!--                                        <textarea readonly :value="scope.row[item.prop]" class="el-textarea__inner" rows="1"></textarea>-->
<!--                                    </template>-->
<!--                                </pl-table-column>-->
<!--                            </pl-table>-->
                            <el-table v-else :data="resultItem.dataList" stripe border
                                      style="width: 100%; margin-bottom: 5px;"
                                      class="execute-result-table" :max-height="tableMaxHeight"
                                      @sort-change="tableSortChange"
                                      :default-sort="tableSort">
                                <el-table-column width="60px" v-if="resultItem.dataCols.length > 0">
                                    <template slot-scope="scope">{{scope.row._index}}</template>
                                </el-table-column>
                                <el-table-column sortable v-for="item in resultItem.dataCols" :prop="item.prop" :label="item.prop" :width="item.width">
                                    <template slot-scope="scope">
                                        <textarea readonly :value="scope.row[item.prop]" class="el-textarea__inner" rows="1"></textarea>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-pagination
                                    v-show="needShowPage"
                                    style="margin-top: 10px;"
                                    @size-change="handlePageSizeChange"
                                    @current-change="handleCurrentChange"
                                    :current-page="currentPage"
                                    :page-sizes="[50, 100, 300, 500]"
                                    :page-size="pageSize"
                                    layout="total, sizes, prev, pager, next, jumper"
                                    :total="tableTotalCount">
                            </el-pagination>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-card>
        </div>
        <span id="widthCalculate" style="visibility: hidden; white-space: nowrap;position: fixed;"></span>
    </div>
</template>

<script>
    import '../../common/lib/ace/ace'
    import '../../common/lib/ace/theme-monokai'
    import '../../common/lib/ace/mode-sql'
    import '../../common/lib/ace/ext-language_tools'
    import '../../common/lib/ace/snippets/sql'
    import global from '../../common/config/global'
    import {queryExecuteSql} from '../../common/api/datasource'
    import sqlFormatter from "sql-formatter";

    export default {
        data() {
            return {
                sqlExecuting: false,
                executeResultList: [],
                executeResultInfo: "",
                executeShowTable: "table1",
                sqlExecutorEditor: {},
                nowExecutorId: 1,
                executeError: "",
                vueQueryParam: {},
                pageSize: 50,
                currentPage: 1,
                tableTotalCount: 0,
                tableSort: {},
                tableMaxHeight: 600,
                needShowPage: true,
            }
        },
        mounted: function () {
            let that = this;
            this.sqlExecutorEditor = this.initAceEditor("sqlExecutorEditor", 6);
            this.sqlExecutorEditor.setFontSize(16);
            this.sqlExecutorEditor.commands.addCommand({
                name: "execute-sql",
                bindKey: {win: "Ctrl-R|Ctrl-Shift-R|Ctrl-Enter", mac: "Command-R|Command-Shift-R|Command-Enter"},
                exec: function (editor) {
                    that.doExecutorClick();
                }
            });
            // 延迟设置展开的目录，edit比app先初始化
            setTimeout(()=> {
                this.doExecutorSql();
                global.vue.$app.initLoadDataList(this.vueQueryParam.sourceId, this.vueQueryParam.host, this.vueQueryParam.dbName);
            }, 500);
        },
        activated: function () {
            this.initQueryParam(this.$route);
        },
        methods: {
            initQueryParam(to) {
                this.vueQueryParam = to.query;
                let newName = {key: this.$route.fullPath, val: '数据-'+this.vueQueryParam.tableName};
                this.$store.commit('global/addTableName', newName);
            },
            handleCurrentChange(to) {
                this.currentPage = to;
                this.doExecutorSql();
            },
            handlePageSizeChange(to) {
                this.pageSize = to;
                this.doExecutorSql();
            },
            tableSortChange(sort) {
                if (this.tableSort.prop === sort.prop && this.tableSort.order === sort.order) return;
                this.tableSort = {prop: sort.prop, order: sort.order};
                this.doExecutorSql();
            },
            refreshData() {
                this.tableSort = {};
                this.currentPage = 1;
                this.doExecutorSql();
            },
            formatterSql() {
                let dataSql = this.sqlExecutorEditor.getSelectedText();
                if (!!dataSql) {
                    let range = this.sqlExecutorEditor.getSelectionRange();
                    this.sqlExecutorEditor.remove(range);
                } else {
                    dataSql = this.sqlExecutorEditor.getValue();
                    this.sqlExecutorEditor.setValue('', 1);
                }
                if (!!dataSql) {
                    dataSql = sqlFormatter.format(dataSql);
                    this.sqlExecutorEditor.insert(dataSql);
                }
            },
            cancelExecutorSql() {
                let that = this;
                this.common.post(this.apilist1.executeSqlCancel, {executeId: this.nowExecutorId}, function (json) {
                    that.$message.success("取消成功");
                });
            },
            doExecutorClick() {
                let dataSql = this.sqlExecutorEditor.getSelectedText();
                if (!dataSql) {
                    dataSql = this.sqlExecutorEditor.getValue();
                }
                if (!dataSql) {
                    this.$message.error("查询SQL不能为空");
                    return;
                }
                this.needShowPage = false;
                this.doExecutorSqlCommon(dataSql);
            },
            doExecutorSql() {
                let dataSql = this.getExecuteSql();
                let countSql = this.getExecuteCountSql();
                this.sqlExecutorEditor.setValue(dataSql, 1);
                this.needShowPage = true;
                this.doExecutorSqlCommon(dataSql, countSql);
            },
            doExecutorSqlCommon(dataSql, countSql) {
                let that = this;
                if (!this.vueQueryParam.sourceId) {
                    this.$message.error("请先选择数据源");
                    return;
                }
                this.executeError = "";
                this.executeUseTime = "";
                this.executeResultList = [];
                this.tableMaxHeight = document.body.clientHeight  - 230;

                this.nowExecutorId = (new Date()).getTime() + Math.ceil(Math.random() * 1000);
                this.sqlExecuting = true;
                let param = {
                    sourceId: this.vueQueryParam.sourceId,
                    executeId: this.nowExecutorId,
                    sql: countSql,
                    params: '',
                };
                // 第一页才查询总条数
                if (!!countSql && this.currentPage == 1) {
                    queryExecuteSql(param).then(res => {
                        if (res.errCode != 200 || !res.data || res.data.length <= 0) return;
                        let objItem = JSON.parse(res.data[0]);
                        if(!objItem.result || objItem.result.length <= 0) return;
                        this.tableTotalCount = objItem.result[0].counts || 0;
                    });
                }
                param.sql = dataSql;
                this.common.postNonCheck(this.apilist1.executeSql, param, function (json) {
                    if (json.errCode != 200) {
                        that.executeError = json.errMsg;
                        that.sqlExecuting = false;
                        return;
                    }
                    var resultList = json.data || [];
                    var executeResultList = [];
                    var executeResultInfo = "", itemIndex = 1;
                    for (var i = 0; i < resultList.length; i++) {
                        var objItem = JSON.parse(resultList[i]);
                        executeResultInfo += that.getExecuteInfoStr(objItem);
                        var resultItem = that.dealExecuteResult(objItem);
                        if (resultItem.updateCount < 0) {
                            resultItem.index = itemIndex;
                            itemIndex++;
                        }
                        executeResultList.push(resultItem);
                    }
                    that.executeShowTable = (itemIndex === 1) ? "table0" : "table1";
                    that.executeResultInfo = executeResultInfo;
                    that.executeResultList = executeResultList;
                    that.sqlExecuting = false;
                });
            },
            getExecuteSql() {
                if (this.vueQueryParam.dbType == 'mysql') {
                    let tableSort = ' ';
                    if (!!this.tableSort.prop && !!this.tableSort.order) {
                        tableSort = ' order by ' + this.tableSort.prop + ' ' + (this.tableSort.order === 'ascending' ? 'asc' : 'desc');
                    }
                    return 'select * from ' + this.vueQueryParam.dbName + '.' + this.vueQueryParam.tableName
                        + tableSort
                        + ' limit ' + this.pageSize + ' offset ' + ((this.currentPage - 1) * this.pageSize);
                }
                this.$message.error("暂未支持的数据库类型表数据预览");
                return '';
            },
            getExecuteCountSql() {
                if (this.vueQueryParam.dbType == 'mysql') {
                    return 'select count(1) as counts from ' + this.vueQueryParam.dbName + '.' + this.vueQueryParam.tableName;
                }
                return '';
            },
            getExecuteInfoStr(resultData) {
                var resultStr = resultData.sql;
                resultStr += "\n> 状态：" + ((!!resultData.errMsg) ? "ERROR" : "OK");
                if (resultData.updateCount >= 0) {
                    resultStr += "\n> 影响行数：" + resultData.updateCount;
                }
                resultStr += "\n> 耗时：" + (resultData.useTime || 0) / 1000 + "s";
                resultStr += "\n\n";
                return resultStr;
            },
            dealExecuteResult(resultData) {
                var dataList = resultData.result || [];
                var executeResultCols = [];
                if (dataList.length > 0) {
                    var propData = dataList[0];
                    for (var key in propData) {
                        // 动态计算宽度~自己想的一个方法，666
                        document.getElementById("widthCalculate").innerText = key;
                        var width1 = document.getElementById("widthCalculate").offsetWidth;
                        document.getElementById("widthCalculate").innerText = propData[key];
                        var width2 = document.getElementById("widthCalculate").offsetWidth;
                        var width = (width1 > width2) ? width1 : width2;
                        width = (width < 50) ? 50 : width;
                        width = (width > 200) ? 200 : width;
                        executeResultCols.push({prop: key, width: width + 50});
                    }
                    for (var i = 0; i < dataList.length; i++) {
                        dataList[i]._index = i + 1;
                    }
                }
                var resultObj = {};
                resultObj.dataList = dataList;
                resultObj.dataCols = executeResultCols;
                resultObj.useTime = resultData.useTime || 0;
                resultObj.errMsg = resultData.errMsg || "";
                resultObj.updateCount = resultData.updateCount;
                return resultObj;
            },
            initAceEditor(editor, minLines) {
                return ace.edit(editor, {
                    theme: "ace/theme/monokai",
                    mode: "ace/mode/sql",
                    wrap: true,
                    autoScrollEditorIntoView: true,
                    enableBasicAutocompletion: true,
                    enableSnippets: true,
                    enableLiveAutocompletion: true,
                    minLines: minLines,
                    maxLines: 40,
                });
            },
        }
    }
</script>

<style>
    .data-executor-vue .ace-monokai .ace_print-margin{
        display: none;
    }
    .data-executor-vue .el-card__body{
        padding: 10px;
    }
    .data-executor-vue .el-table td, .el-table th{
        padding: 6px 0;
    }
    .data-executor-vue .execute-result-table .el-input__inner{
        height: 25px;
        line-height: 25px;
        padding: 0 5px;
    }
    .data-executor-vue .execute-result-table .el-textarea__inner{
        height: 27px;
        min-height: 27px;
        line-height: 25px;
        padding: 0 5px;
        resize: none;
    }
    .data-executor-vue .execute-use-time{
        font-size: 12px;margin-right: 10px;
    }
    .data-executor-vue-out .el-tabs__nav-scroll{
        padding-left: 20px;
    }
    .data-executor-vue-out .el-button+.el-button{
        margin-left: 0px;
    }
    .data-executor-vue-out .el-table__body-wrapper{
        height: calc(100vh - 180px);
        overflow-y: auto;
    }
</style>
