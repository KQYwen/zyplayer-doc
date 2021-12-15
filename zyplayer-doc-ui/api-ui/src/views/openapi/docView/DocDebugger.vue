<template>
    <div>
        <a-input-search :addon-before="docInfoShow.method.toUpperCase()" v-model:value="docUrl" @search="sendRequest" placeholder="请输入目标URL地址">
            <template #enterButton>
                <a-button type="primary" :loading="requestLoading">{{isDownloadRequest?'下载文件':'发送请求'}}</a-button>
            </template>
        </a-input-search>
        <a-tabs v-model:activeKey="activePage" closable @tab-click="activePageChange" style="padding: 5px 10px 0;">
            <a-tab-pane tab="URL参数" key="urlParam" forceRender>
                <div v-show="queryParamVisible">
                    <ParamTable ref="urlParamRef" :paramList="urlParamList"></ParamTable>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="Body参数" key="bodyParam" v-if="docInfoShow.method !== 'get'" forceRender>
                <div v-show="queryParamVisible">
                    <div style="margin-bottom: 6px;">
                        <a-radio-group v-model:value="bodyParamType">
                            <a-radio value="none">none</a-radio>
                            <a-radio value="form">form-data</a-radio>
                            <a-radio value="formUrlEncode">x-www-form-urlencoded</a-radio>
                            <a-radio value="row">row</a-radio>
                            <!--                            <a-radio value="binary">binary</a-radio>-->
                        </a-radio-group>
                        <a-select v-if="bodyParamType === 'row'" v-model:value="consumesParamType" size="small" style="margin-left: 10px;vertical-align: top;width: 100px;">
                            <a-select-option value="json">JSON</a-select-option>
                            <a-select-option value="html">HTML</a-select-option>
                            <a-select-option value="xml">XML</a-select-option>
                            <a-select-option value="javascript">JavaScript</a-select-option>
                            <a-select-option value="text">TEXT</a-select-option>
                        </a-select>
                    </div>
                    <div v-show="bodyParamType === 'form'">
                        <ParamTable ref="formParamRef" :paramList="formParamList" showType></ParamTable>
                    </div>
                    <div v-show="bodyParamType === 'formUrlEncode'">
                        <ParamTable ref="formEncodeParamRef" :paramList="formEncodeParamList"></ParamTable>
                    </div>
                    <div v-show="bodyParamType === 'row'">
                        <ParamBody ref="bodyParamRef" :rowLang="consumesParamType" :paramList="bodyRowParamList"></ParamBody>
                    </div>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="Header参数" key="headerParam" forceRender>
                <div v-show="queryParamVisible">
                    <ParamTable ref="headerParamRef" :paramList="headerParamList"></ParamTable>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="Cookie参数" key="cookieParam" forceRender>
                <div v-show="queryParamVisible">
                    <ParamTable ref="cookieParamRef" :paramList="cookieParamList"></ParamTable>
                </div>
            </a-tab-pane>
            <template #rightExtra>
                <a-button v-if="queryParamVisible" @click="hideQueryParam" type="link">收起参数</a-button>
                <a-button v-else @click="showQueryParam" type="link">展开参数</a-button>
            </template>
        </a-tabs>
        <DocDebuggerResult v-if="!isDownloadRequest" :result="requestResult" :loading="requestLoading"></DocDebuggerResult>
        <form method="post" ref="downloadFormRef" :action="downloadFormParam.url" target="_blank">
            <input type="hidden" :name="key" :value="val" v-for="(val,key) in downloadFormParam.param">
        </form>
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import DocDebuggerResult from './DocDebuggerResult.vue'
    import ParamTable from '../../../components/params/ParamTable.vue'
    import ParamBody from '../../../components/params/ParamBody.vue'
    import {CloseOutlined, VerticalAlignTopOutlined, VerticalAlignBottomOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../../api";
    import {getZyplayerApiBaseUrl} from "../../../api/request/utils.js";

    export default {
        props: {
            docInfoShow: {
                type: Object,
                required: true
            },
            requestParamList: {
                type: Array,
                required: true
            },
            responseParamList: {
                type: Array,
                required: true
            },
        },
        components: {
            VerticalAlignTopOutlined, VerticalAlignBottomOutlined, CloseOutlined, ParamTable, ParamBody, DocDebuggerResult,
        },
        setup(props) {
            const store = useStore();
            let apiDoc = store.state.apiDoc || {};
            let userGlobalParam = store.state.globalParam || [];
            let docGlobalParam = store.state.docGlobalParam || [];
	        let openApiDoc = store.state.openApiDoc || {};
            let urlDomain = apiDoc.rewriteDomain || '';
	        // os：取服务地址不一样 todo 多服务地址的情况处理
	        let servers = openApiDoc.servers || [];
	        if (!urlDomain && servers.length > 0 && servers[0].url) {
		        urlDomain = servers[0].url;
	        }
            let docUrl = ref(urlDomain + props.docInfoShow.url);
            let activePage = ref('urlParam');
            let globalParam = [].concat(userGlobalParam, docGlobalParam);
            // URL参数处理
            const urlParamRef = ref();
            let urlParamListProp = props.requestParamList.filter(item => item.in === 'query' || item.in === 'path');
            let urlParamList = ref([]);
            // Header参数处理
            const headerParamRef = ref();
            let headerParamListGlobal = globalParam.filter(item => item.paramType === 2);
            let headerParamListProp = props.requestParamList.filter(item => item.in === 'header');
            let nextIndex = 1;
            headerParamListGlobal.forEach(item => {
                headerParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
            });
            let headerParamList = ref(JSON.parse(JSON.stringify(headerParamListProp)));
            // cookie参数处理
            const cookieParamRef = ref();
            let cookieParamListGlobal = globalParam.filter(item => item.paramType === 3);
            let cookieParamListProp = props.requestParamList.filter(item => item.in === 'cookie');
            cookieParamListGlobal.forEach(item => {
                cookieParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
            });
            let cookieParamList = ref(JSON.parse(JSON.stringify(cookieParamListProp)));
            // form参数处理
            const formParamRef= ref();
            let formParamListGlobal = globalParam.filter(item => item.paramType === 1);
            let formParamListProp = props.requestParamList.filter(item => item.in === 'formData');
            formParamListGlobal.forEach(item => {
                formParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
            });
            let formParamList = ref([]);
            if (props.docInfoShow.method === 'post') {
                // post的时候参数否放到form里面
                formParamListProp = formParamListProp.concat(urlParamListProp);
            } else {
                // 否则放到URL参数里面
                urlParamList = ref(JSON.parse(JSON.stringify(urlParamListProp)));
            }
            // form参数处理
            const formEncodeParamRef = ref();
            let formEncodeParamList = ref([]);
            // body 参数
            let bodyParamRef = ref();
            let bodyParamType = ref('form');
            let consumesParamType = ref('json');
            let bodyRowListProp = props.requestParamList.filter(item => item.in === 'body');
            let bodyRowParamList = ref(JSON.parse(JSON.stringify(bodyRowListProp)));
            // x-www-form-urlencoded
            if (props.docInfoShow.consumes.indexOf('application/x-www-form-urlencoded') >= 0) {
                bodyParamType.value = 'formUrlEncode';
                formEncodeParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
            } else if (props.docInfoShow.consumes.indexOf('multipart/form-data') >= 0) {
                bodyParamType.value = 'form';
                formParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
            } else if (props.docInfoShow.consumes.indexOf('application/json') >= 0) {
                bodyParamType.value = 'row';
                consumesParamType.value = 'json';
                formEncodeParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
                if (formParamListProp.length > 0) {
                    bodyParamType.value = 'formUrlEncode';
                }
            } else if (props.docInfoShow.consumes.indexOf('application/xml') >= 0 || props.docInfoShow.consumes.indexOf('text/xml') >= 0) {
                bodyParamType.value = 'row';
                consumesParamType.value = 'xml';
                formEncodeParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
                if (formParamListProp.length > 0) {
                    bodyParamType.value = 'formUrlEncode';
                }
            } else {
                formParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
            }
            if (formParamList.value.length > 0) {
                activePage.value = 'urlParam';
            } else if (formParamListProp.length > 0 || bodyRowListProp.length > 0) {
                activePage.value = 'bodyParam';
            } else if (headerParamListProp.length > 0) {
                activePage.value = 'headerParam';
            }
            const isFileType = record => {
                return record.type === 'file' || record.subType === 'file' || record.subType === 'MultipartFile';
            };
            // 发送请求
            let requestResult = ref({});
            let requestLoading = ref(false);
            let downloadFormParam = ref({url: getZyplayerApiBaseUrl() + '/doc-swagger/proxy/download', param: {}});
            let downloadFormRef = ref();
            let isDownloadRequest = (props.docInfoShow.produces === 'application/octet-stream');
            const sendRequest = () => {
                if (!docUrl.value) {
                    message.error('请输入请求的目标URL地址');
                    return;
                }
                // 用于替换URL上的path参数
                let formObjData = {};
                // 代理请求发送给后端的对象
                const formData = new FormData();
                const appendData = item => {
                    if (isFileType(item)) {
                        // 防止参数重名，加个前缀
                        let name = '_file_' + item.name;
                        if (item.type === 'array') {
                            item.value.forEach(file => formData.append(name, file));
                        } else {
                            if (item.value instanceof Array && item.value.length > 0) {
                                formData.append(name, item.value[0]);
                            }
                        }
                    } else {
                        formObjData[item.name] = item.value;
                        formData.append(item.name, item.value);
                    }
                };
                let urlParamSelected = urlParamRef.value.getSelectedRowKeys();
                let urlParamStr = urlParamList.value.filter(item => urlParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    appendData(item);
                    return item.name + '=' + encodeURIComponent(item.value);
                }).join('&');
                let headerParamSelected = headerParamRef.value.getSelectedRowKeys();
                let headerParamArr = headerParamList.value.filter(item => headerParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return {code: item.name, value: item.value};
                });
                let cookieParamSelected = cookieParamRef.value.getSelectedRowKeys();
                let cookieParamArr = cookieParamList.value.filter(item => cookieParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return {code: item.name, value: item.value};
                });
                let formParamArr = [];
                if (formParamRef.value) {
                    let formParamSelected = formParamRef.value.getSelectedRowKeys();
                    formParamArr = formParamList.value.filter(item => formParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                        // todo 判断处理文件格式
                        appendData(item);
                        return {code: item.name, value: item.value};
                    });
                }
                let formEncodeParamArr = [];
                if (formEncodeParamRef.value) {
                    let formEncodeParamSelected = formEncodeParamRef.value.getSelectedRowKeys();
                    formEncodeParamArr = formEncodeParamList.value.filter(item => formEncodeParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                        // todo 判断处理文件格式
                        appendData(item);
                        return {code: item.name, value: item.value};
                    });
                }
                let bodyParamStr = '';
                if (bodyParamRef.value) {
                    bodyParamStr = bodyParamRef.value.getParam();
                }
                let url = urlParamStr ? (docUrl.value + '?' + urlParamStr) : docUrl.value;
                // 替换path参数
                Object.keys(formObjData).forEach((key) => {
                    url = url.replace("{" + key + "}", formObjData[key]);
                });
                // 下载请求
                if (isDownloadRequest) {
                    downloadFormParam.value.param = {
                        url: url,
                        host: urlDomain,
                        method: props.docInfoShow.method,
                        contentType: props.docInfoShow.consumes,
                        headerParam: JSON.stringify(headerParamArr),
                        cookieParam: JSON.stringify(cookieParamArr),
                        formParam: JSON.stringify(formParamArr),
                        formEncodeParam: JSON.stringify(formEncodeParamArr),
                        bodyParam: bodyParamStr,
                    };
                    setTimeout(() => downloadFormRef.value.submit(), 0);
                } else {
                    // 正常请求
                    formData.append('url', url);
                    formData.append('host', urlDomain);
                    formData.append('method', props.docInfoShow.method);
                    formData.append('contentType', props.docInfoShow.consumes);
                    formData.append('headerParam', JSON.stringify(headerParamArr));
                    formData.append('cookieParam', JSON.stringify(cookieParamArr));
                    formData.append('formParam', JSON.stringify(formParamArr));
                    formData.append('formEncodeParam', JSON.stringify(formEncodeParamArr));
                    formData.append('bodyParam', bodyParamStr);
                    requestLoading.value = true;
                    requestResult.value = {};
                    zyplayerApi.requestUrl(formData).then(res => {
                        requestResult.value = res;
                        requestLoading.value = false;
                    }).catch(e => {
                        requestLoading.value = false;
                    });
                }
            };
            let queryParamVisible = ref(true);
            const hideQueryParam = () => {
                queryParamVisible.value = false;
            }
            const showQueryParam = () => {
                queryParamVisible.value = true;
            }
            const activePageChange = () => {
                queryParamVisible.value = true;
            }
            return {
                docUrl,
                activePage,
                activePageChange,
                requestLoading,
                sendRequest,
                requestResult,
                consumesParamType,
                downloadFormParam,
                downloadFormRef,
                isDownloadRequest,
                // url参数
                urlParamRef,
                urlParamList,
                // header参数
                headerParamRef,
                headerParamList,
                // cookie参数
                cookieParamRef,
                cookieParamList,
                // form参数
                formParamRef,
                formParamList,
                // form-encode参数
                formEncodeParamRef,
                formEncodeParamList,
                // body参数
                bodyParamRef,
                bodyParamType,
                bodyRowParamList,
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
                // 界面控制
                queryParamVisible,
                hideQueryParam,
                showQueryParam,
            };
        },
    };
</script>
