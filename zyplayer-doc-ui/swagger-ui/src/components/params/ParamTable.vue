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
            <template v-if="column.dataIndex === 'type'">
                <a-select v-if="record.key >= 10000" v-model:value="record.type">
                    <a-select-option value="integer">Integer</a-select-option>
                    <a-select-option value="string">String</a-select-option>
                    <a-select-option value="file">File</a-select-option>
                </a-select>
                <a-tag color="pink" v-else-if="text === 'integer'">Integer</a-tag>
                <a-tag color="red" v-else-if="text === 'string'">String</a-tag>
                <a-tag color="green" v-else>{{text||'-'}}</a-tag>
            </template>
            <template v-if="column.dataIndex === 'value'">
                <a-select v-if="record.enum && record.type === 'array'" v-model:value="record.value" mode="multiple" :placeholder="record.description || '请选择'" style="width: 100%;">
                    <a-select-option :value="enums" v-for="enums in record.enum">{{enums}}</a-select-option>
                </a-select>
                <a-select v-else-if="record.enum" v-model:value="record.value" :placeholder="record.description || '请选择'" style="width: 100%;">
                    <a-select-option :value="enums" v-for="enums in record.enum">{{enums}}</a-select-option>
                </a-select>
                <a-upload v-else-if="record.type==='file' || record.subType === 'file' || record.subType === 'MultipartFile'"
                          :file-list="record.value" name="file" :multiple="record.type === 'array'"
                          :before-upload="file=>{return beforeUpload(file, record)}"
                          :remove="file=>{return handleRemove(file, record)}"
                >
                    <a-button><upload-outlined></upload-outlined>选择文件</a-button>
                </a-upload>
                <a-input v-else :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="queryParamChange(record)"></a-input>
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
    import {CloseOutlined, UploadOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

    export default {
        props: {
            paramList: {
                type: Array,
                required: true
            },
            showType: {
                type: Boolean,
            },
        },
        components: {
            CloseOutlined, UploadOutlined
        },
        emits: ['update:selected'],
        setup(props, { attrs, slots, emit, expose }) {
            let queryParamList = ref(props.paramList);
            let nextIndex = 10000;
            // Query参数处理
            if (queryParamList.value.length <= 0 || !queryParamList.value[queryParamList.value.length - 1].isLastRow) {
                queryParamList.value.push({name: '', value: undefined, type: 'integer', key: ++nextIndex, isLastRow: true});
            }
            let queryParamSelectedRowKeys = ref([]);
            queryParamList.value.forEach(item => {
                item.value = item.example || undefined;
                if ((item.enum && item.type === 'array') || item.type === 'file' || item.subType === 'MultipartFile') {
                    item.value = [];
                }
                queryParamSelectedRowKeys.value.push(item.key);
            });
            const queryParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                queryParamSelectedRowKeys.value = selectedRowKeys;
            };
            const queryParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    queryParamList.value.push({name: '', value: undefined, type: 'integer', key: ++nextIndex, isLastRow: true});
                    queryParamSelectedRowKeys.value.push(nextIndex);
                }
            };
            const queryParamRemove = (record) => {
                if (!record.isLastRow) {
                    queryParamList.value = queryParamList.value.filter(item => item !== record);
                }
            };
            let queryParamListColumns = ref([]);
            queryParamListColumns.value.push({title: '参数名', dataIndex: 'name', width: 250});
            if (props.showType) {
                queryParamListColumns.value.push({title: '类型', dataIndex: 'type', width: 100});
            }
            queryParamListColumns.value.push({title: '参数值', dataIndex: 'value'});
            queryParamListColumns.value.push({title: '', dataIndex: 'action', width: 40});
            const beforeUpload = (file, record) => {
                if (record.type !== 'array') {
                    record.value = [file];
                } else {
                    record.value = [...record.value, file];
                }
                return false;
            };
            const handleRemove = (file, record) => {
                record.value = record.value.filter(item => item !== file);
            };
            const getSelectedRowKeys = () => {
                return queryParamSelectedRowKeys.value;
            };
            return {
                queryParamList,
                queryParamSelectedRowKeys,
                queryParamRowSelectionChange,
                queryParamChange,
                queryParamRemove,
                beforeUpload,
                handleRemove,
                queryParamListColumns,
                // 父组件调用
                getSelectedRowKeys,
            };
        },
    };
</script>
