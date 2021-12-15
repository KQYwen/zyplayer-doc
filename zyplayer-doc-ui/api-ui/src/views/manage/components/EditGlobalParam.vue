<template>
    <div style="margin-bottom: 10px;text-align: right;">
        <a-button @click="searchDocList" type="primary"><template #icon><reload-outlined /></template> 刷新</a-button>
        <a-button @click="addDocLine"><template #icon><plus-outlined /></template> 新建</a-button>
    </div>
    <a-table :dataSource="docList" :columns="docListColumns" size="middle" id="paramTable"
             :loading="docListLoading" :pagination="false"
             :scroll="{ x: 1000, y: 'calc(100vh - 240px)' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'paramKey'">
                <a-input v-if="record.isEdit" placeholder="请输入参数名称" v-model:value="docEdit.paramKey"></a-input>
                <span v-else>{{text}}</span>
            </template>
            <template v-if="column.dataIndex === 'paramValue'">
                <a-input v-if="record.isEdit" :rows="1" placeholder="请输入参数值" v-model:value="docEdit.paramValue"></a-input>
                <span v-else>{{text}}</span>
            </template>
            <template v-if="column.dataIndex === 'paramType'">
                <template v-if="record.isEdit">
                    <a-select placeholder="参数位置" v-model:value="docEdit.paramType" style="width: 110px;">
                        <a-select-option :value="1">Form</a-select-option>
                        <a-select-option :value="2">Header</a-select-option>
                        <a-select-option :value="3">Cookie</a-select-option>
                    </a-select>
                </template>
                <template v-else>
                    <a-tag color="green" v-if="text === 1">Form</a-tag>
                    <a-tag color="pink" v-else-if="text === 2">Header</a-tag>
                    <a-tag color="pink" v-else-if="text === 3">Cookie</a-tag>
                </template>
            </template>
            <template v-if="column.dataIndex === 'operation'">
                <template v-if="record.isEdit">
                    <a-button type="link" @click="cancelEditDoc(record)">取消</a-button>
                    <a-button type="link" @click="saveEditDoc(record)">保存</a-button>
                </template>
                <template v-else>
                    <a-button type="link" @click="editDoc(record)">编辑</a-button>
                    <a-popconfirm title="确定要删除吗？" @confirm="deleteDoc(record)">
                        <a-button type="link" danger>删除</a-button>
                    </a-popconfirm>
                </template>
            </template>
        </template>
    </a-table>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import {zyplayerApi} from '../../../api';
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {SearchOutlined, PlusOutlined, ReloadOutlined} from '@ant-design/icons-vue';

    export default {
	    components: {PlusOutlined, SearchOutlined, ReloadOutlined},
	    props: {
		    dynamicParam: {
			    type: Object,
			    required: true
		    },
	    },
        setup(props) {
            const store = useStore();
            let docList = ref([]);
            let docListLoading = ref(false);
            const searchDocList = async () => {
                docEdit.value = {};
                docListLoading.value = true;
                zyplayerApi.docApiGlobalParamList(props.dynamicParam).then(res => {
                    setTimeout(() => docListLoading.value = false, 500);
                    docList.value = res.data || [];
	                store.commit('setGlobalParamOnChange', docList.value, props.dynamicParam.id);
                });
            };
            let docEdit = ref({});
            let tableRef = ref();
            const addDocLine = () => {
                if (docEdit.value.isEdit) {
                    cancelEditDoc(docEdit.value);
                }
                let newLine = {isEdit: true, paramType: 1};
                docList.value.unshift(newLine);
                docEdit.value = newLine;
                // 渲染完滚动到底部
                setTimeout(() => {
                    let container = document.getElementsByClassName('ant-table-body')[0];
                    container.scrollTop = 0;
                }, 0);
            };
            const editDoc = (record) => {
                if (docEdit.value.isEdit) {
                    cancelEditDoc(docEdit.value);
                }
                record.isEdit = true;
                docEdit.value = {...record};
            };
            const cancelEditDoc = (record) => {
                record.isEdit = false;
                if (!record.id) {
                    docList.value = docList.value.filter(item => item !== record);
                } else {
                    docList.value.forEach(item => item.isEdit = false);
                }
                docEdit.value = {};
            };
            const saveEditDoc = (record) => {
                if (!docEdit.value.paramKey || !docEdit.value.paramValue) {
                    message.error('参数名或参数值不能为空');
                    return;
                }
				let params = {...props.dynamicParam, ...docEdit.value};
                zyplayerApi.docApiGlobalParamUpdate(params).then(res => {
                    record.isEdit = false;
                    searchDocList();
                });
            };
            const deleteDoc = async (record) => {
                zyplayerApi.docApiGlobalParamUpdate({id: record.id, yn: 0}).then(res => {
                    searchDocList();
                });
            };
            onMounted(() => {
                searchDocList();
            });
            return {
                docList,
                docListLoading,
                docEdit,
                tableRef,
                searchDocList,
                deleteDoc,
                editDoc,
                saveEditDoc,
                cancelEditDoc,
                addDocLine,
                docListColumns: [
                    {title: '参数名称', dataIndex: 'paramKey', width: 250},
                    {title: '参数值', dataIndex: 'paramValue'},
                    {title: '参数位置', dataIndex: 'paramType', width: 120},
                    {title: '操作', dataIndex: 'operation', fixed: 'right', width: 170},
                ],
            };
        },
    };
</script>
