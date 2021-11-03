<template>
    <div>
        <a-input-search
                :addon-before="docInfoShow.method.toUpperCase()"
                v-model:value="docUrl"
                enter-button="发送请求"
                @search="sendRequest"
        />
        <a-tabs v-model:activeKey="activePage" closable @tab-click="" style="padding: 5px 10px 0;">
            <a-tab-pane tab="URL参数" key="urlParam">
                <ParamTable ref="urlParamRef" v-model:selected="urlParamChecked" :paramList="urlParamList"></ParamTable>
            </a-tab-pane>
            <a-tab-pane tab="请求参数" key="bodyParam" v-if="docInfoShow.method !== 'get'">
                <a-radio-group v-model:value="bodyParamType" style="margin-bottom: 5px;">
                    <a-radio value="none">none</a-radio>
                    <a-radio value="form">form-data</a-radio>
                    <a-radio value="formUrlEncode">x-www-form-urlencoded</a-radio>
                    <a-radio value="row">row</a-radio>
                    <a-radio value="binary">binary</a-radio>
                </a-radio-group>
                <div v-show="bodyParamType === 'form'">
                    <ParamTable ref="formParamRef" :paramList="formParamList" showType></ParamTable>
                </div>
                <div v-show="bodyParamType === 'formUrlEncode'">
                    <ParamTable ref="formEncodeParamRef" :paramList="formEncodeParamList"></ParamTable>
                </div>
                <div v-show="bodyParamType === 'row'">
                    <ParamBody ref="bodyParamRef" :paramList="bodyRowParamList"></ParamBody>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="Header参数" key="headerParam">
                <ParamTable ref="headerParamRef" :paramList="headerParamList"></ParamTable>
            </a-tab-pane>
            <a-tab-pane tab="Cookie参数" key="cookieParam">
                <ParamTable ref="cookieParamRef" :paramList="cookieParamList"></ParamTable>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import ParamTable from '../../../components/params/ParamTable.vue'
    import ParamBody from '../../../components/params/ParamBody.vue'
    import {CloseOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../../api";

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
            CloseOutlined, ParamTable, ParamBody
        },
        setup(props) {
            const store = useStore();
            let swaggerResource = store.state.swaggerResource || {};
            let swaggerDoc = store.state.swaggerDoc || {};
            let urlDomain = swaggerResource.rewriteDomain || swaggerDoc.host;
            let docUrl = ref(urlDomain + props.docInfoShow.url);
            let activePage = ref('urlParam');
            // URL参数处理
            const urlParamRef = ref();
            const urlParamChecked = ref([]);
            let urlParamListProp = props.requestParamList.filter(item => item.in === 'query');
            let urlParamList = ref([]);
            // Header参数处理
            const headerParamRef = ref();
            const headerParamChecked = ref([]);
            let headerParamListProp = props.requestParamList.filter(item => item.in === 'header');
            let headerParamList = ref(JSON.parse(JSON.stringify(headerParamListProp)));
            // cookie参数处理
            const cookieParamRef = ref();
            const cookieParamChecked = ref([]);
            let cookieParamListProp = props.requestParamList.filter(item => item.in === 'cookie');
            let cookieParamList = ref(JSON.parse(JSON.stringify(cookieParamListProp)));
            // form参数处理
            const formParamRef= ref();
            const formParamChecked = ref([]);
            let formParamListProp = props.requestParamList.filter(item => item.in === 'formData');
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
            const formEncodeParamChecked = ref([]);
            let formEncodeParamList = ref([]);
            // body 参数
            let bodyParamRef = ref();
            let bodyParamType = ref('form');
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
            // 发送请求
            const sendRequest = () => {
                const formData = new FormData();
                let selectedRowKeys = urlParamRef.value.getSelectedRowKeys();
                let urlParamStr = urlParamList.value.filter(item => selectedRowKeys.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return item.name + '=' + encodeURIComponent(item.value);
                }).join('&');
                console.log('urlParamStr', urlParamStr);
                // fileList.value.forEach(file => {
                //     formData.append('files[]', file);
                // });
                formData.append('url', docUrl.value + '?' + urlParamStr);
                zyplayerApi.requestUrl(formData).then(res => {
                    debugger
                });
                message.info('暂未开放此功能，敬请期待');
            };
            return {
                docUrl,
                activePage,
                sendRequest,
                // url参数
                urlParamRef,
                urlParamChecked,
                urlParamList,
                // header参数
                headerParamRef,
                headerParamChecked,
                headerParamList,
                // cookie参数
                cookieParamRef,
                cookieParamChecked,
                cookieParamList,
                // form参数
                formParamRef,
                formParamChecked,
                formParamList,
                // form-encode参数
                formEncodeParamRef,
                formEncodeParamChecked,
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
            };
        },
    };
</script>
