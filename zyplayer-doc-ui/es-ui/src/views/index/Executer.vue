<template>
    <div class="index-executer-vue">
        <el-card style="margin: 10px;">
            <div style="margin-bottom: 20px;">
                <el-form :model="executeParam" :inline="true" :rules="paramRules" ref="paramRulesForm">
                    <el-form-item label="选择数据源：" prop="id">
                        <el-select v-model="executeParam.id" @change="loadIndexList" filterable placeholder="选择数据源">
                            <el-option v-for="item in datasourceOptions" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="选择索引：">
                        <el-select v-model="executeParam.index" filterable placeholder="选择索引">
                            <el-option label="全部" value=""></el-option>
                            <el-option v-for="item in esIndexList" :label="item.name" :value="item.index"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
<!--            例：{"_source":["id","name","createUserName","viewNum"]}-->
            <el-input type="textarea" v-model="executeParam.sql" :rows="10" placeholder="请输入DSL查询条件"></el-input>
            <div style="text-align: center;margin-top: 10px;">
                <el-button type="primary" icon="el-icon-video-play" v-on:click="submitExecute">执行</el-button>
            </div>
        </el-card>
        <el-card style="margin: 10px;" v-loading="executeLoading">
            <el-tabs value="first">
                <el-tab-pane label="返回结果" name="first">
                    <div v-if="!!executeResult.errCode">
                        <div v-if="executeResult.errCode == 200">
                            <el-table :data="executeResult.data" stripe border style="width: 100%;">
                                <el-table-column v-for="item in executeResultCols" :prop="item.prop" :label="item.prop"></el-table-column>
                            </el-table>
                        </div>
                        <div class="error-text" v-else>{{executeResult.errMsg}}</div>
                    </div>
                    <div v-else>请先执行</div>
                </el-tab-pane>
                <el-tab-pane label="JSON结果" name="second">
                    <div v-if="!!executeResult.errCode">
                        <div v-html="executeResult.jsonHtmlStr" @click="executeResultClick($event)" v-if="executeResult.errCode == 200"></div>
                        <div class="error-text" v-else>{{executeResult.errMsg}}</div>
                    </div>
                    <div v-else>请先执行</div>
                </el-tab-pane>
                <el-tab-pane label="执行状态" name="third">
                    <div v-if="!!executeResult.errCode">{{executeResult.errCode == 200 ? '成功' : '失败'}}</div>
                    <div v-else>请先执行</div>
                </el-tab-pane>
            </el-tabs>
        </el-card>
    </div>
</template>

<script>
    import global from '../../common/config/global'
    import formatjson from '../../common/lib/common/formatjson'
    var app;

    export default {
        data() {
            return {
                vueQueryParam: {},
                datasourceOptions: [],
                esIndexList: [],
                executeParam: {
                    id: '',
                    sql: '',
                    index: '',
                },
                executeResult: {},
                executeResultCols: [],
                executeLoading: false,
                paramRules: {
                    id: [
                        {required: true, message: '请选择数据源', trigger: 'change'},
                    ],
                },
            };
        },
        beforeRouteUpdate(to, from, next) {
            next();
        },
        mounted: function () {
            app = this;
            this.loadDatasourceList();
        },
        methods: {
            submitExecute() {
                this.$refs.paramRulesForm.validate((valid) => {
                    if (!valid) return;
                    app.doSubmitExecute();
                });
            },
            doSubmitExecute() {
                this.executeLoading = true;
                this.executeResult = {};
                this.common.postNonCheck(this.apilist1.esExecuter, this.executeParam, function (json) {
                    var executeResult = json;
                    try {
                        executeResult.jsonHtmlStr = formatjson.processObjectToHtmlPre(json.data, 0, false, false, false, false);
                    } catch (e) {
                        executeResult.jsonHtmlStr = "结果解析失败";
                    }
                    app.executeResult = executeResult;
                    var executeResultCols = [];
                    if (!!json.data && json.data.length > 0) {
                        var colItem = json.data[0];
                        for (var key in colItem) {
                            executeResultCols.push({prop: key});
                        }
                    }
                    // reverse 反转下，不然查询出来的结果字段反的
                    app.executeResultCols = executeResultCols.reverse();
                    // 防止遮罩消失太快~
                    setTimeout(()=>{app.executeLoading = false;}, 500);
                });
            },
            executeResultClick(e) {
                if (e.target.className.indexOf('option-img') >= 0) {
                    formatjson.expImgClicked(e.target);
                }
            },
            loadDatasourceList() {
                this.common.post(this.apilist1.manageDatasourceList, {}, function (json) {
                    app.datasourceOptions = json.data || [];
                });
            },
            loadIndexList() {
                var param = {id: this.executeParam.id};
                this.common.post(this.apilist1.esMappings, param, function (json) {
                    var result = json.data || {};
                    var propertiesArr = [];
                    for (var key in result) {
                        propertiesArr.push({name: key, index: key});
                    }
                    app.esIndexList = propertiesArr;
                });
            },
        }
    }
</script>
<style>
    .index-executer-vue .el-form-item{margin-bottom: 5px;}
    .index-executer-vue .edit-table-desc{cursor: pointer; color: #409EFF;}
    .index-executer-vue .description{cursor: pointer;}
    .index-executer-vue .error-text{color: #f00;}
    .index-executer-vue .el-table td, .table-info-vue .el-table th{padding: 5px 0;}

    /* S-JSON展示的样式 */
    .index-executer-vue pre.json {
        display: block;
        padding: 9.5px;
        margin: 0 0 0 10px;
        font-size: 12px;
        line-height: 1.38461538;
        color: #333;
        word-break: break-all;
        word-wrap: break-word;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .index-executer-vue pre.json .option-img{
        display: inline-block;cursor: pointer;
        background: url('../../assets/img/collapsed.png') no-repeat center;
        width: 28px; height: 11px; background-size: 28px 11px;
    }
    .index-executer-vue pre.json .img-expanded{
        background: url('../../assets/img/expanded.png') no-repeat center;
    }
    .index-executer-vue pre.json .canvas{font:10pt georgia;background-color:#ececec;color:#000000;border:1px solid #cecece;}
    .index-executer-vue pre.json .object-brace{color:#00aa00;font-weight:bold;}
    .index-executer-vue pre.json .array-brace{color:#0033ff;font-weight:bold;}
    .index-executer-vue pre.json .property-name{color:#cc0000;font-weight:bold;}
    .index-executer-vue pre.json .string{color:#007777;}
    .index-executer-vue pre.json .number{color:#aa00aa;}
    .index-executer-vue pre.json .boolean{color:#0000ff;}
    .index-executer-vue pre.json .function{color:#aa6633;text-decoration:italic;}
    .index-executer-vue pre.json .null{color:#0000ff;}
    .index-executer-vue pre.json .comma{color:#000000;font-weight:bold;}
    .index-executer-vue pre.json .annotation{color:#aaa;}
    .index-executer-vue pre img{cursor: pointer;}
    /* E-JSON展示的样式 */
</style>

