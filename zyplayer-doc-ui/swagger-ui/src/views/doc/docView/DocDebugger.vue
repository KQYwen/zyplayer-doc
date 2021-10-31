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
                <a-table :row-selection="{ selectedRowKeys: urlParamSelectedRowKeys, onChange: urlParamRowSelectionChange }" :dataSource="urlParamList" :columns="urlParamListColumns" size="small" :pagination="false">
                    <template #bodyCell="{ column, text, record }">
                        <template v-if="column.dataIndex === 'name'">
                            <a-input placeholder="请输入参数名" v-model:value="record.name" @change="urlParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'value'">
                            <a-input :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="urlParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'action'">
                            <CloseOutlined v-if="!record.isLastRow" @click="urlParamRemove(record)" style="cursor: pointer;"/>
                        </template>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="请求参数" key="bodyParam">
                <a-radio-group v-model:value="bodyParamType">
                    <a-radio value="none">none</a-radio>
                    <a-radio value="form">form-data</a-radio>
                    <a-radio value="formUrlEncode">x-www-form-urlencoded</a-radio>
                    <a-radio value="row">row</a-radio>
                    <a-radio value="binary">binary</a-radio>
                </a-radio-group>
            </a-tab-pane>
            <a-tab-pane tab="Header参数" key="headerParam">
                <a-table :row-selection="{ selectedRowKeys: headerParamSelectedRowKeys, onChange: headerParamRowSelectionChange }" :dataSource="headerParamList" :columns="headerParamListColumns" size="small" :pagination="false">
                    <template #bodyCell="{ column, text, record }">
                        <template v-if="column.dataIndex === 'name'">
                            <a-input placeholder="请输入参数名" v-model:value="record.name" @change="headerParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'value'">
                            <a-input :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="headerParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'action'">
                            <CloseOutlined v-if="!record.isLastRow" @click="headerParamRemove(record)" style="cursor: pointer;"/>
                        </template>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="Cookie参数" key="cookieParam">
                <a-table :row-selection="{ selectedRowKeys: cookieParamSelectedRowKeys, onChange: cookieParamRowSelectionChange }" :dataSource="cookieParamList" :columns="cookieParamListColumns" size="small" :pagination="false">
                    <template #bodyCell="{ column, text, record }">
                        <template v-if="column.dataIndex === 'name'">
                            <a-input placeholder="请输入参数名" v-model:value="record.name" @change="cookieParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'value'">
                            <a-input :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="cookieParamChange(record)"></a-input>
                        </template>
                        <template v-if="column.dataIndex === 'action'">
                            <CloseOutlined v-if="!record.isLastRow" @click="cookieParamRemove(record)" style="cursor: pointer;"/>
                        </template>
                    </template>
                </a-table>
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
            CloseOutlined
        },
        setup(props) {
            let docUrl = ref(props.docInfoShow.url);
            let activePage = ref('urlParam');
            let paramKeyIndex = 10000;
            // URL参数处理
            let urlParamListProp = props.requestParamList.filter(item => item.in === 'query');
            urlParamListProp.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
            let urlParamSelectedRowKeys = ref([]);
            urlParamListProp.forEach(item => {
                item.value = item.example || '';
                urlParamSelectedRowKeys.value.push(item.key);
            });
            let urlParamList = ref(JSON.parse(JSON.stringify(urlParamListProp)));
            const urlParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                urlParamSelectedRowKeys.value = selectedRowKeys;
            };
            const urlParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    urlParamList.value.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
                    urlParamSelectedRowKeys.value.push(paramKeyIndex);
                }
            };
            const urlParamRemove = (record) => {
                if (!record.isLastRow) {
                    urlParamList.value = urlParamList.value.filter(item => item != record);
                }
            };
            // Header参数处理
            let headerParamListProp = props.requestParamList.filter(item => item.in === 'query');
            headerParamListProp.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
            let headerParamSelectedRowKeys = ref([]);
            headerParamListProp.forEach(item => {
                item.value = item.example || '';
                headerParamSelectedRowKeys.value.push(item.key);
            });
            let headerParamList = ref(JSON.parse(JSON.stringify(headerParamListProp)));
            const headerParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                headerParamSelectedRowKeys.value = selectedRowKeys;
            };
            const headerParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    headerParamList.value.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
                    headerParamSelectedRowKeys.value.push(paramKeyIndex);
                }
            };
            const headerParamRemove = (record) => {
                if (!record.isLastRow) {
                    headerParamList.value = headerParamList.value.filter(item => item != record);
                }
            };
            // cookie参数处理
            let cookieParamListProp = props.requestParamList.filter(item => item.in === 'query');
            cookieParamListProp.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
            let cookieParamSelectedRowKeys = ref([]);
            cookieParamListProp.forEach(item => {
                item.value = item.example || '';
                cookieParamSelectedRowKeys.value.push(item.key);
            });
            let cookieParamList = ref(JSON.parse(JSON.stringify(cookieParamListProp)));
            const cookieParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                cookieParamSelectedRowKeys.value = selectedRowKeys;
            };
            const cookieParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    cookieParamList.value.push({name: '', value: '', key: ++paramKeyIndex, isLastRow: true});
                    cookieParamSelectedRowKeys.value.push(paramKeyIndex);
                }
            };
            const cookieParamRemove = (record) => {
                if (!record.isLastRow) {
                    cookieParamList.value = cookieParamList.value.filter(item => item != record);
                }
            };
            // body 参数
            let bodyParamType = ref('form');
            // 发送请求
            const sendRequest = () => {
                message.info('暂未开放此功能，敬请期待');
            };
            return {
                docUrl,
                activePage,
                sendRequest,
                // url参数
                urlParamList,
                urlParamSelectedRowKeys,
                urlParamRowSelectionChange,
                urlParamChange,
                urlParamRemove,
                urlParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '参数值', dataIndex: 'value'},
                    {title: '', dataIndex: 'action', width: 40},
                ],
                // header参数
                headerParamList,
                headerParamSelectedRowKeys,
                headerParamRowSelectionChange,
                headerParamChange,
                headerParamRemove,
                headerParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '参数值', dataIndex: 'value'},
                    {title: '', dataIndex: 'action', width: 40},
                ],
                // cookie参数
                cookieParamList,
                cookieParamSelectedRowKeys,
                cookieParamRowSelectionChange,
                cookieParamChange,
                cookieParamRemove,
                cookieParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '参数值', dataIndex: 'value'},
                    {title: '', dataIndex: 'action', width: 40},
                ],
                // body参数
                bodyParamType,
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
