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
                <ParamTable v-model:selected="urlParamChecked" :paramList="urlParamList"></ParamTable>
            </a-tab-pane>
            <a-tab-pane tab="请求参数" key="bodyParam">
                <a-radio-group v-model:value="bodyParamType" style="margin-bottom: 5px;">
                    <a-radio value="none">none</a-radio>
                    <a-radio value="form">form-data</a-radio>
                    <a-radio value="formUrlEncode">x-www-form-urlencoded</a-radio>
                    <a-radio value="row">row</a-radio>
                    <a-radio value="binary">binary</a-radio>
                </a-radio-group>
                <div v-show="bodyParamType === 'form'">
                    <ParamTable v-model:selected="formParamChecked" :paramList="formParamList"></ParamTable>
                </div>
                <div v-show="bodyParamType === 'formUrlEncode'">
                    <ParamTable v-model:selected="formEncodeParamChecked" :paramList="formEncodeParamList"></ParamTable>
                </div>
                <div v-show="bodyParamType === 'row'">
                    <a-textarea placeholder="" v-model:value="bodyRowParam" :auto-size="{ minRows: 15, maxRows: 15 }"></a-textarea>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="Header参数" key="headerParam">
                <ParamTable v-model:selected="headerParamChecked" :paramList="headerParamList"></ParamTable>
            </a-tab-pane>
            <a-tab-pane tab="Cookie参数" key="cookieParam">
                <ParamTable v-model:selected="cookieParamChecked" :paramList="cookieParamList"></ParamTable>
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
    import ParamTable from '../../../components/table/ParamTable.vue'
    import {CloseOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

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
            CloseOutlined, ParamTable
        },
        setup(props) {
            let docUrl = ref(props.docInfoShow.url);
            let activePage = ref('urlParam');
            // URL参数处理
            const urlParamChecked = ref([]);
            let urlParamListProp = props.requestParamList.filter(item => item.in === 'query');
            let urlParamList = ref(JSON.parse(JSON.stringify(urlParamListProp)));
            // Header参数处理
            const headerParamChecked = ref([]);
            let headerParamListProp = props.requestParamList.filter(item => item.in === 'header');
            let headerParamList = ref(JSON.parse(JSON.stringify(headerParamListProp)));
            // cookie参数处理
            const cookieParamChecked = ref([]);
            let cookieParamListProp = props.requestParamList.filter(item => item.in === 'cookie');
            let cookieParamList = ref(JSON.parse(JSON.stringify(cookieParamListProp)));
            // form参数处理
            const formParamChecked = ref([]);
            let formParamListProp = props.requestParamList.filter(item => item.in === 'formData');
            let formParamList = ref([]);
            // form参数处理
            const formEncodeParamChecked = ref([]);
            let formEncodeParamList = ref([]);
            // body 参数
            let bodyParamType = ref('form');
            let bodyRowParam = ref('');
            // x-www-form-urlencoded
            if (props.docInfoShow.consumes.indexOf('x-www-form-urlencoded') >= 0) {
                bodyParamType.value = 'formUrlEncode';
                formEncodeParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
            } else if (props.docInfoShow.consumes.indexOf('application/json') >= 0) {
                bodyParamType.value = 'row';
            } else {
                formParamList = ref(JSON.parse(JSON.stringify(formParamListProp)));
            }
            // 发送请求
            const sendRequest = () => {
                console.log('urlParamChecked', urlParamChecked.value, urlParamList.value);
                message.info('暂未开放此功能，敬请期待');
            };
            return {
                docUrl,
                activePage,
                sendRequest,
                // url参数
                urlParamChecked,
                urlParamList,
                // header参数
                headerParamChecked,
                headerParamList,
                // cookie参数
                cookieParamChecked,
                cookieParamList,
                // form参数
                formParamChecked,
                formParamList,
                // form-encode参数
                formEncodeParamChecked,
                formEncodeParamList,
                // body参数
                bodyParamType,
                bodyRowParam,
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
