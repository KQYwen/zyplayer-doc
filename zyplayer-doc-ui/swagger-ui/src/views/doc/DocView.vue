<template>
    <a-tabs v-model:activeKey="activePage" closable @tab-click="changePage" style="padding: 5px 10px 0;">
        <a-tab-pane tab="接口说明" key="doc">
            <a-form :label-col="{span: 4}" :wrapper-col="{span: 20}">
                <a-form-item label="接口地址">{{docInfoShow.url}}</a-form-item>
                <a-form-item label="说明">
                    <a-card size=small><div class="markdown-body" v-html="docInfoShow.description" v-highlight></div></a-card>
                </a-form-item>
                <a-form-item label="请求方式">{{docInfoShow.method}}</a-form-item>
                <a-form-item label="请求数据类型">{{docInfoShow.consumes}}</a-form-item>
                <a-form-item label="响应数据类型">{{docInfoShow.produces}}</a-form-item>
                <a-form-item label="请求参数">
                    <a-table :dataSource="requestParamList" :columns="requestParamListColumns" size="small" :pagination="false" defaultExpandAllRows>
                        <template #bodyCell="{ column, text, record }">
                            <template v-if="column.dataIndex === 'type'">
                                {{text}}
                                <template v-if="record.subType">[{{record.subType}}]</template>
                                <template v-if="record.format">({{record.format}})</template>
                            </template>
                            <template v-if="column.dataIndex === 'in'">
                                <a-tag color="pink" v-if="text === 'header'">header</a-tag>
                                <a-tag color="red" v-else-if="text === 'body'">body</a-tag>
                                <a-tag color="orange" v-else-if="text === 'query'">query</a-tag>
                                <a-tag color="green" v-else-if="text === 'formData'">formData</a-tag>
                                <template v-else-if="!text">-</template>
                                <a-tag color="purple" v-else>{{text}}</a-tag>
                            </template>
                            <template v-if="column.dataIndex === 'required'">
                                <span v-if="text === '是'" style="color: #f00;">是</span>
                                <template v-else-if="text === '否'">否</template>
                                <template v-else>-</template>
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
                                    <template #bodyCell="{ column, text, record }">
                                        <template v-if="column.dataIndex === 'type'">
                                            {{text}}
                                            <template v-if="record.subType">[{{record.subType}}]</template>
                                            <template v-if="record.format">({{record.format}})</template>
                                        </template>
                                    </template>
                                </a-table>
                            </template>
                            <div v-else style="text-align: center;padding: 10px 0;">无参数说明</div>
                        </template>
                    </a-table>
                </a-form-item>
            </a-form>
        </a-tab-pane>
        <a-tab-pane tab="在线调试" key="debug">
            暂未开放
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
    import {markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

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
                let description = markdownIt.render(docInfo.description || docInfo.summary || '');
                docInfoShow.value = {
                    url: docInfo.url,
                    description: description,
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
                    {title: '类型', dataIndex: 'type', width: 150},
                    {title: '参数位置', dataIndex: 'in', width: 100},
                    {title: '必填', dataIndex: 'required', width: 60},
                    {title: '说明', dataIndex: 'description'},
                ],
                responseParamList,
                responseCodeListColumns: [
                    {title: '状态码', dataIndex: 'code', width: 100},
                    {title: '类型', dataIndex: 'type', width: 250},
                    {title: '说明', dataIndex: 'desc'},
                ],
                responseParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '类型', dataIndex: 'type', width: 250},
                    {title: '说明', dataIndex: 'description'},
                ],
            };
        },
    };
</script>
