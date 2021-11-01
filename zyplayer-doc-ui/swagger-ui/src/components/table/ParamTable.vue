<template>
    <a-table :row-selection="{ selectedRowKeys: queryParamSelectedRowKeys, onChange: queryParamRowSelectionChange }"
             :dataSource="queryParamList"
             :columns="queryParamListColumns" size="small"
             :pagination="false"
             :scroll="{ y: '300px' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'name'">
                <a-input placeholder="请输入参数名" v-model:value="record.name" @change="queryParamChange(record)"></a-input>
            </template>
            <template v-if="column.dataIndex === 'value'">
                <a-input :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="queryParamChange(record)"></a-input>
            </template>
            <template v-if="column.dataIndex === 'action'">
                <CloseOutlined v-if="!record.isLastRow" @click="queryParamRemove(record)" style="cursor: pointer;"/>
            </template>
        </template>
    </a-table>
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
            paramList: {
                type: Array,
                required: true
            },
        },
        components: {
            CloseOutlined
        },
        emits: ['update:selected'],
        setup(props, { attrs, slots, emit, expose }) {
            let queryParamList = ref(props.paramList);
            let nextIndex = 10000;
            // Query参数处理
            queryParamList.value.push({name: '', value: '', key: ++nextIndex, isLastRow: true});
            let queryParamSelectedRowKeys = ref([]);
            queryParamList.value.forEach(item => {
                item.value = item.example || '';
                queryParamSelectedRowKeys.value.push(item.key);
            });
            emit('update:selected', queryParamSelectedRowKeys.value);
            const queryParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                queryParamSelectedRowKeys.value = selectedRowKeys;
                emit('update:selected', selectedRowKeys);
            };
            const queryParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    queryParamList.value.push({name: '', value: '', key: ++nextIndex, isLastRow: true});
                    queryParamSelectedRowKeys.value.push(nextIndex);
                    emit('update:selected', queryParamSelectedRowKeys.value);
                }
            };
            const queryParamRemove = (record) => {
                if (!record.isLastRow) {
                    queryParamList.value = queryParamList.value.filter(item => item !== record);
                }
            };
            return {
                queryParamList,
                queryParamSelectedRowKeys,
                queryParamRowSelectionChange,
                queryParamChange,
                queryParamRemove,
                queryParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '参数值', dataIndex: 'value'},
                    {title: '', dataIndex: 'action', width: 40},
                ],
            };
        },
    };
</script>
