<template>
    <div>
        <a-tabs v-model:activeKey="activePage" closable @tab-click="" style="padding: 5px 10px 0;">
            <a-tab-pane tab="Body" key="body" forceRender>
                <template v-if="result.data && result.data.data">{{result.data.data}}</template>
                <template v-else-if="result.data">{{result.data}}</template>
                <template v-else>{{result}}</template>
            </a-tab-pane>
            <a-tab-pane tab="Headers" key="headers" forceRender>
                <a-table :dataSource="resultHeaders"
                         :columns="resultHeadersColumns" size="small"
                         :pagination="false"
                         :scroll="{ y: '300px' }">
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="Cookies" key="cookies" forceRender>
                <a-table :dataSource="resultCookies"
                         :columns="resultCookiesColumns" size="small"
                         :pagination="false"
                         :scroll="{ y: '300px' }">
                </a-table>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import {useRouter, useRoute} from "vue-router";
    import {useStore} from 'vuex';
    import {message} from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import ParamTable from '../../../components/params/ParamTable.vue'
    import ParamBody from '../../../components/params/ParamBody.vue'
    import {CloseOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../../api";

    export default {
        props: {
            result: {
                type: Object,
                required: true
            },
        },
        components: {
            CloseOutlined, ParamTable, ParamBody
        },
        setup(props) {
            const { result } = toRefs(props);
            let activePage = ref('body');
            let resultHeaders = ref([]);
            let resultCookies = ref([]);
            const initData = () => {
                if (props.result.data) {
                    if (props.result.data.headers) {
                        resultHeaders.value = props.result.data.headers;
                    }
                    if (props.result.data.cookies) {
                        resultCookies.value = props.result.data.cookies;
                    }
                }
            };
            initData();
            watch(result, () => initData());
            return {
                activePage,
                resultHeaders,
                resultCookies,
                resultHeadersColumns: [
                    {title: 'KEY', dataIndex: 'name'},
                    {title: 'VALUE', dataIndex: 'value'},
                ],
                resultCookiesColumns: [
                    {title: 'KEY', dataIndex: 'name'},
                    {title: 'VALUE', dataIndex: 'value'},
                ],
            };
        },
    };
</script>
