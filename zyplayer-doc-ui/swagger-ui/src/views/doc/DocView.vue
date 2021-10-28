<template>
    <a-tabs v-model:activeKey="activePage" closable @tab-click="changePage" style="padding: 5px 10px 0;">
        <a-tab-pane tab="接口说明" key="doc">
            <a-form :label-col="{span: 4}" :wrapper-col="{span: 20}">
                <a-form-item label="接口地址">{{docInfoShow.url}}</a-form-item>
                <a-form-item label="说明">{{docInfoShow.description}}</a-form-item>
                <a-form-item label="请求方式">{{docInfoShow.method}}</a-form-item>
                <a-form-item label="请求数据类型">{{docInfoShow.consumes}}</a-form-item>
                <a-form-item label="响应数据类型">{{docInfoShow.produces}}</a-form-item>
                <a-form-item label="请求参数">
                    <a-table :dataSource="requestParamList" :columns="requestParamListColumns" size="small" :pagination="false" defaultExpandAllRows>
                        <template #bodyCell="{ column, text, record }">
                            <template v-if="column.dataIndex === 'htmlStr'">
                                <div v-html="text"></div>
                            </template>
                        </template>
                    </a-table>
                </a-form-item>
                <a-form-item label="返回结果">
                    <a-table :dataSource="responseParamList" :columns="responseCodeListColumns" size="small" :pagination="false" defaultExpandAllRows>
                        <template #bodyCell="{ column, text, record }">
                            <template v-if="column.dataIndex === 'desc'">
                                <div v-html="text"></div>
                            </template>
                        </template>
                        <template #expandedRowRender="{ record }">
                            <template v-if="record.schemas">
                                <a-table :dataSource="record.schemas" :columns="responseParamListColumns" size="small" :pagination="false" defaultExpandAllRows>
                                </a-table>
                            </template>
                            <div v-else style="text-align: center;padding: 10px 0;">无参数说明</div>
                        </template>
                    </a-table>
                </a-form-item>
            </a-form>
        </a-tab-pane>
        <a-tab-pane tab="在线调试" key="debug">
            {{activePage}}
        </a-tab-pane>
    </a-tabs>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
    import swaggerAnalysis from '../../assets/utils/SwaggerAnalysisV2'

    export default {
        setup() {
            const route = useRoute();
            const store = useStore();
            let activePage = ref('doc');
            let requestParamList = ref([]);
            let responseParamList = ref([]);
            let docInfoShow = ref({
                url: '',
                description: '',
                method: '',
                consumes: '',
                produces: '',
            });
            let isLoadSuccess = ref(false);
            let intervalNum = 0;
            let intervalTimer = undefined;
            const initLoadDocument = () => {
                let path = route.query.path + '.' + route.query.method;
                if (Object.keys(store.state.swaggerTreePathMap).length <= 0) {
                    console.log('文档尚未加载，等待加载完成');
                    if (!intervalTimer) {
                        intervalTimer = setInterval(() => {
                            if (isLoadSuccess.value || intervalNum++ > 50) {
                                clearInterval(intervalTimer);
                                return;
                            }
                            if (Object.keys(store.state.swaggerTreePathMap).length > 0) {
                                console.log('文档内容改变，重新加载文档');
                                initLoadDocument();
                            }
                        }, 1000);
                    }
                    return;
                }
                let docInfo = store.state.swaggerTreePathMap[path];
                if (!docInfo) {
                    message.error('没有找到对应的文档');
                    return;
                }
                isLoadSuccess.value = true;
                store.commit('addTableName', {key: route.fullPath, val: docInfo.summary});
                // 解析接口说明
                let consumes = '', produces = '';
                if (docInfo.consumes && docInfo.consumes.length > 0) {
                    consumes = docInfo.consumes.join(' ');
                }
                if (docInfo.produces && docInfo.produces.length > 0) {
                    produces = docInfo.produces.join(' ');
                }
                docInfoShow.value = {
                    url: docInfo.url,
                    description: (docInfo.description || docInfo.summary),
                    method: docInfo.method || '',
                    consumes: consumes,
                    produces: produces,
                };
                // 解析请求参数
                let definitionsDataMap = store.state.swaggerDefinitions;
                requestParamList.value = swaggerAnalysis.getRequestParamList(docInfo.parameters, definitionsDataMap);
                responseParamList.value = swaggerAnalysis.getResponseParamList(docInfo.responses, definitionsDataMap);
            }
            onMounted(() => {
                initLoadDocument();
            });
            const changePage = () => {
            }
            return {
                docInfoShow,
                activePage,
                changePage,
                requestParamList,
                requestParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 200},
                    {title: '说明', dataIndex: 'description'},
                    {title: '类型', dataIndex: 'type'},
                    {title: '参数位置', dataIndex: 'in'},
                    {title: '是否必填', dataIndex: 'required'},
                ],
                responseParamList,
                responseCodeListColumns: [
                    {title: '状态码', dataIndex: 'code', width: 100},
                    {title: '说明', dataIndex: 'desc'},
                    {title: '类型', dataIndex: 'type'},
                ],
                responseParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 200},
                    {title: '说明', dataIndex: 'description'},
                    {title: '类型', dataIndex: 'type'},
                ],
            };
        },
    };
</script>

<style>
    /* S-JSON展示的样式 */
    pre.json{margin-top:0px;margin-bottom:0px;}
    pre.json .canvas{font:10pt georgia;background-color:#ececec;color:#000000;border:1px solid #cecece;}
    pre.json .object-brace{color:#00aa00;font-weight:bold;}
    pre.json .array-brace{color:#0033ff;font-weight:bold;}
    pre.json .property-name{color:#cc0000;font-weight:bold;}
    pre.json .string{color:#007777;}
    pre.json .number{color:#aa00aa;}
    pre.json .boolean{color:#0000ff;}
    pre.json .function{color:#aa6633;text-decoration:italic;}
    pre.json .null{color:#0000ff;}
    pre.json .comma{color:#000000;font-weight:bold;}
    pre.json .annotation{color:#aaa;}
    pre img{cursor: pointer;}
    /* E-JSON展示的样式 */
</style>
