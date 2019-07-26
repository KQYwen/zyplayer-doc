<template>
    <div class="index-executer-vue">
        <el-card style="margin: 10px;">
            <div style="margin: 10px 0;">
                <span>选择索引：</span>
                <el-select v-model="executeParam.index" filterable placeholder="选择索引">
                    <el-option label="zyplayer_doc_wiki" value="zyplayer_doc_wiki"></el-option>
                </el-select>
            </div>
            <el-input type="textarea" v-model="executeParam.sql" :rows="10" placeholder="请输入"></el-input>
            <div style="text-align: center;margin: 10px 0;">
                <el-button type="primary" v-on:click="submitExecute">执行</el-button>
            </div>
        </el-card>
        <el-card style="margin: 10px;">
            <div style="margin: 10px 0;">
                <span>执行结果：{{executeResult.errCode == 200 ? '成功' : '失败'}}</span>
            </div>
            <div style="margin: 10px 0;" v-if="executeResult.errCode == 200">
                <div style="margin: 10px 0;">返回结果：</div>
                <div v-html="executeResult.data" @click="executeResultClick($event)"></div>
            </div>
            <div style="margin: 10px 0;" v-else>
                <div style="margin: 10px 0;">错误信息：</div>
                <div class="error-text">{{executeResult.errMsg}}</div>
            </div>
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
                indexMappingListLoading: false,
                vueQueryParam: {},
                indexMappingList: [],
                executeParam: {
                    sql: '',
                    index: '',
                },
                executeResult: {},
            };
        },
        beforeRouteUpdate(to, from, next) {
            this.initQueryParam(to);
            next();
        },
        mounted: function () {
            app = this;
            this.initQueryParam(this.$route);
            // 延迟设置展开的目录，edit比app先初始化
            setTimeout(function () {
                //global.vue.$app.initLoadDataList(app.vueQueryParam.host, app.vueQueryParam.dbName);
            }, 500);
        },
        methods: {
            submitExecute() {
                this.indexMappingListLoading = true;
                this.executeResult = {};
                this.common.postNonCheck(this.apilist1.esExecuter, this.executeParam, function (json) {
                    var executeResult = json;
                    try {
                        executeResult.data = formatjson.processObjectToHtmlPre(json.data, 0, false, false, false, false);
                    } catch (e) {
                        executeResult.data = "结果解析失败";
                    }
                    app.executeResult = executeResult;
                    app.indexMappingListLoading = false;
                });
            },
            executeResultClick(e) {
                if (e.target.className.indexOf('option-img') >= 0) {
                    formatjson.expImgClicked(e.target);
                }
            },
            initQueryParam(to) {
                // this.indexMappingListLoading = true;
                // this.vueQueryParam = to.query;
                // this.common.post(this.apilist1.esMappings, this.vueQueryParam, function (json) {
                //     var data = json.data || {};
                //     var properties = data[app.vueQueryParam.index].sourceAsMap.properties;
                //     var propertiesArr = [];
                //     for (var propertiesKey in properties) {
                //         var propertiesItem = properties[propertiesKey];
                //         var item = {
                //             name: propertiesKey, type: propertiesItem.type
                //         };
                //         propertiesArr.push(item);
                //     }
                //     app.indexMappingList = propertiesArr;
                //     app.indexMappingListLoading = false;
                // });
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

