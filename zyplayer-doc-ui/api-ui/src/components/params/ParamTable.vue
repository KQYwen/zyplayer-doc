<template>
    <div v-if="multilineEdit">
        <div style="text-align: right;">
            <a-button @click="toKeyValueEdit" type="link">表单编辑</a-button>
        </div>
        <a-textarea v-model:value="multilineEditValue" :auto-size="{ minRows: 14, maxRows: 14 }"></a-textarea>
    </div>
    <div v-else>
        <a-table :row-selection="{ selectedRowKeys: queryParamSelectedRowKeys, onChange: queryParamRowSelectionChange }"
                 :dataSource="paramListRef"
                 :columns="paramListColumns" size="small"
                 :pagination="false"
                 :scroll="{ y: '300px' }">
            <template #headerCell="{ column }">
                <template v-if="column.dataIndex === 'action'">
                    <a-button @click="toMultilineEdit" type="link" style="margin-left: -50px;">多行编辑</a-button>
                </template>
            </template>
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
                    <a-select v-if="record.enum && record.type === 'array'" v-model:value="record.value" mode="multiple" :placeholder="record.description || '请选择枚举值'" style="width: 100%;">
                        <a-select-option :value="enums" v-for="enums in record.enum">{{enums}}</a-select-option>
                    </a-select>
                    <a-select v-else-if="record.enum" v-model:value="record.value" :placeholder="record.description || '请选择枚举值'" style="width: 100%;">
                        <a-select-option :value="enums" v-for="enums in record.enum">{{enums}}</a-select-option>
                    </a-select>
                    <a-select v-else-if="record.type==='boolean'" v-model:value="record.value" :placeholder="record.description || '请选择参数值'" style="width: 100%;">
                        <a-select-option value="true">TRUE</a-select-option>
                        <a-select-option value="false">FALSE</a-select-option>
                    </a-select>
                    <a-upload v-else-if="isFileType(record)"
                              :file-list="record.value" name="file" :multiple="record.type === 'array'"
                              :before-upload="file=>{return beforeUpload(file, record)}"
                              :remove="file=>{return handleRemove(file, record)}"
                    >
                        <a-button><upload-outlined/>选择文件</a-button>
                    </a-upload>
                    <a-input v-else :placeholder="record.description || '请输入参数值'" v-model:value="record.value" @change="queryParamChange(record)"></a-input>
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <CloseOutlined v-if="!record.isLastRow" @click="queryParamRemove(record)" style="cursor: pointer;"/>
                </template>
            </template>
        </a-table>
    </div>
</template>

<script>
    import {toRefs, toRef, ref, reactive, onMounted, watch} from 'vue';
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
	        let queryParamSelectedRowKeys = ref([]);
	        let nextIndex = 10000;
	        let paramListRef = ref([]);
	        watch(() => props.paramList, () => {
		        propsParamInit();
	        });
	        onMounted(() => {
		        propsParamInit();
	        });
            const propsParamInit = () => {
	            paramListRef.value = props.paramList;
	            // Query参数处理
	            if (paramListRef.value.length <= 0 || !paramListRef.value[paramListRef.value.length - 1].isLastRow) {
		            props.paramList.push({name: '', value: undefined, type: 'integer', key: ++nextIndex, isLastRow: true});
	            }
	            paramListRef.value.forEach(item => {
		            item.value = item.value || item.example || undefined;
		            if ((item.enum && item.type === 'array') || item.type === 'file' || item.subType === 'MultipartFile') {
			            item.value = [];
		            }
		            queryParamSelectedRowKeys.value.push(item.key);
	            });
            };
            const queryParamRowSelectionChange = (selectedRowKeys, selectedRows) => {
                queryParamSelectedRowKeys.value = selectedRowKeys;
            };
            const queryParamChange = (record) => {
                if (record.isLastRow) {
                    record.isLastRow = false;
                    props.paramList.push({name: '', value: undefined, type: 'integer', key: ++nextIndex, isLastRow: true});
                    queryParamSelectedRowKeys.value.push(nextIndex);
                }
            };
            const queryParamRemove = (record) => {
                if (!record.isLastRow) {
                    let index = props.paramList.findIndex(item => item === record);
                    props.paramList.splice(index, 1);
                }
            };
            let paramListColumns = ref([]);
            paramListColumns.value.push({title: '参数名', dataIndex: 'name', width: 250});
            if (props.showType) {
                // paramListColumns.value.push({title: '类型', dataIndex: 'type', width: 100});
            }
            paramListColumns.value.push({title: '参数值', dataIndex: 'value'});
            paramListColumns.value.push({title: '', dataIndex: 'action', width: 40});
            const beforeUpload = (file, record) => {
	            if (record.type !== 'array' || !(record.value instanceof Array) || record.value.length <= 0) {
		            record.value = [file];
	            } else {
		            record.value = [...record.value, file];
	            }
                return false;
            };
            const handleRemove = (file, record) => {
                record.value = record.value.filter(item => item !== file);
            };
            const isFileType = record => {
                return record.type === 'file' || record.subType === 'file' || record.subType === 'MultipartFile';
            };
            let multilineEdit = ref(false);
            let multilineEditValue = ref('');
            const toMultilineEdit = () => {
                multilineEdit.value = true;
                multilineEditValue.value = paramListRef.value.filter(item => item.name || item.value)
                    .map(item => {
                        // 文件类型多行编辑时不支持值的展示
                        if (isFileType(item)) {
                            return (item.name || '') + ':';
                        }
                        return (item.name || '') + ':' + (item.value || '');
                    }).join('\n');
            };
            const toKeyValueEdit = () => {
                convertKeyValueEdit();
                multilineEdit.value = false;
            };
            // 将多行转换为表格的方式录入
            const convertKeyValueEdit = () => {
                if (!multilineEdit.value) return;
                let paramMap = {};
                props.paramList.forEach(item => paramMap[item.name] = item);
                let lineDataArr = multilineEditValue.value.split('\n');
                let paramListTemp = [];
                lineDataArr.forEach(line => {
                    if (!line) return;
                    let index = line.indexOf(':');
                    if (index >= 0) {
                        let name = line.substring(0, index);
                        let value = line.substring(index + 1);
                        if (name || value) {
                            let newLine = paramMap[name] || {name: name, value: value, type: 'integer', key: ++nextIndex};
                            newLine.value = value;
                            paramListTemp.push(newLine);
                        }
                    } else {
                        let newLine = paramMap[line] || {name: line, value: undefined, type: 'integer', key: ++nextIndex};
                        paramListTemp.push(newLine);
                    }
                });
                paramListTemp.push({name: '', value: undefined, type: 'integer', key: ++nextIndex, isLastRow: true});
                queryParamSelectedRowKeys.value = [];
                // 修改父对象的值，ref引用的值也将一起改变
                props.paramList.splice(0, props.paramList.length);
                paramListTemp.forEach(item => {
                    props.paramList.push(item);
                    queryParamSelectedRowKeys.value.push(item.key);
                });
            };
            const getSelectedRowKeys = () => {
                convertKeyValueEdit();
                return queryParamSelectedRowKeys.value;
            };
            return {
                paramListRef,
                queryParamSelectedRowKeys,
                queryParamRowSelectionChange,
                queryParamChange,
                queryParamRemove,
                beforeUpload,
                handleRemove,
                paramListColumns,
                isFileType,
                // 父组件调用
                getSelectedRowKeys,
                // 多行编辑
                multilineEdit,
                multilineEditValue,
                toMultilineEdit,
                toKeyValueEdit,
                convertKeyValueEdit,
            };
        },
    };
</script>
