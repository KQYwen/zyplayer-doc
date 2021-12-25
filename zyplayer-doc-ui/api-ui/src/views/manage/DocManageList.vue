<template>
    <a-form layout="inline" style="margin-bottom: 20px;">
        <a-form-item label="文档类型">
            <a-select placeholder="请选择文档类型" v-model:value="searchParam.docType" style="width: 150px;">
                <a-select-option value="">全部</a-select-option>
                <a-select-option :value="1">URL添加</a-select-option>
                <a-select-option :value="2">JSON内容</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="开放访问">
            <a-select placeholder="请选择开放访问" v-model:value="searchParam.openVisit" style="width: 150px;">
                <a-select-option value="">全部</a-select-option>
                <a-select-option :value="0">否</a-select-option>
                <a-select-option :value="1">是</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="状态">
            <a-select placeholder="请选择状态" v-model:value="searchParam.docStatus" style="width: 150px;">
                <a-select-option value="">全部</a-select-option>
                <a-select-option :value="1">启用</a-select-option>
                <a-select-option :value="2">禁用</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item>
            <a-button @click="searchDocList" type="primary"><template #icon><SearchOutlined/></template> 查询</a-button>
            <a-button @click="openNewDoc" :style="{ marginLeft: '8px' }"><template #icon><PlusOutlined/></template> 新建</a-button>
        </a-form-item>
    </a-form>
    <a-table :dataSource="docList" :columns="docListColumns" size="middle"
             :loading="docListLoading" :pagination="pagination"
             @change="handleTableChange"
             :scroll="{ x: 1400, y: 'calc(100vh - 300px)' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'operation'">
                <a-button size="small" type="link" @click="editDoc(record)">编辑</a-button>
	            <template v-if="record.authType === 1">
		            <a-popconfirm title="确定要删除吗？" @confirm="deleteDoc(record)">
			            <a-button size="small" type="link" danger>删除</a-button>
		            </a-popconfirm>
	            </template>
                <a-dropdown :trigger="['click']">
                    <template #overlay>
                        <a-menu @click="handleActionMenuClick($event, record)">
                            <a-menu-item key="shareView"><link-outlined /> 查看开放文档</a-menu-item>
                        </a-menu>
                    </template>
                    <a-button type="link" size="small">更多<DownOutlined /></a-button>
                </a-dropdown>
            </template>
            <template v-if="column.dataIndex === 'docType'">
                <a-tag color="red" v-if="text === 1">Swagger URL</a-tag>
                <a-tag color="blue" v-else-if="text === 2">Swagger JSON</a-tag>
                <a-tag color="blue" v-else-if="text === 3">Swagger URL</a-tag>
                <a-tag color="green" v-else-if="text === 4">OpenApi JSON</a-tag>
                <a-tag color="green" v-else-if="text === 5">自建API</a-tag>
            </template>
            <template v-if="column.dataIndex === 'openVisit'">
                <a-tag color="pink" v-if="text === 0">未开放</a-tag>
                <a-tag color="green" v-else-if="text === 1">已开放</a-tag>
            </template>
            <template v-if="column.dataIndex === 'docStatus'">
                <a-tag color="green" v-if="text === 1">启用</a-tag>
                <a-tag color="pink" v-else-if="text === 2">禁用</a-tag>
            </template>
        </template>
    </a-table>
    <a-modal v-model:visible="newDocVisible" :title="docEdit.isNew?'新增文档':'编辑文档'" @ok="handleNewDocOk" :width="850">
        <EditDocBaseInfo ref="docBaseInfoRef" :doc="docEdit"></EditDocBaseInfo>
    </a-modal>
</template>

<script>
    import { toRefs, ref, reactive, onMounted, createVNode, defineComponent } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';
    import aceEditor from "../../assets/ace-editor";
    import EditDocBaseInfo from "./components/EditDocBaseInfo.vue";
    import {getZyplayerApiBaseUrl} from "../../api/request/utils";
    import {DownOutlined, LinkOutlined, EditOutlined, SearchOutlined, PlusOutlined, ExclamationCircleOutlined} from '@ant-design/icons-vue';
    import { message, Modal } from 'ant-design-vue';

    export default {
	    emits: ['edit'],
        components: {aceEditor, DownOutlined, LinkOutlined, EditOutlined, SearchOutlined, PlusOutlined, EditDocBaseInfo},
        setup(props, {emit}) {
            const store = useStore();
            let docList = ref([]);
            let docListLoading = ref(false);
            let searchParam = ref({docType: '', openVisit: '', docStatus: '', pageNum: 1, pageSize: 20});
            let pagination = ref({
                pageSize: 20,
                pageNum: 1,
                total: 0,
                showSizeChanger: true,
                pageSizeOptions: ['20', '50', '100'],
                showTotal: total => `共${total}条`
            });
            const handleTableChange = (paginationNew, filters, sorter) => {
                pagination.value.pageNum = paginationNew.current;
                pagination.value.pageSize = paginationNew.pageSize;
                searchParam.value.pageNum = paginationNew.current;
                searchParam.value.pageSize = paginationNew.pageSize;
                searchDocList();
            };
            const searchDocList = async () => {
                docListLoading.value = true;
                zyplayerApi.apiDocList(searchParam.value).then(res => {
                    setTimeout(() => docListLoading.value = false, 500);
                    docList.value = res.data || [];
                    pagination.value.total = res.total || 0;
                });
            };
            let docEdit = ref({});
            let docBaseInfoRef = ref();
            let newDocVisible = ref(false);
            const handleNewDocOk = async () => {
	            let docNew = await docBaseInfoRef.value.getDoc();
                zyplayerApi.apiDocAdd(docNew).then(res => {
                    newDocVisible.value = false;
                    store.commit('addDocChangedNum');
	                searchDocList();
                });
            };
	        const openNewDoc = async () => {
		        newDocVisible.value = true;
		        docEdit.value = {docType: 1, openVisit: 0, docStatus: 1, isNew: 1};
	        };
	        const editDoc = (record) => {
                emit('edit', 'edit', record);
            };
            const updateDoc = (id, docStatus, yn) => {
                zyplayerApi.apiDocUpdate({id, docStatus, yn}).then(res => {
                    store.commit('addDocChangedNum');
                    searchDocList();
                });
            };
            const deleteDoc = (row) => {
                Modal.confirm({
                    title: '再次确认',
                    icon: createVNode(ExclamationCircleOutlined),
                    content: '你真的确定要删除此文档吗？',
                    okText: '确认',
                    cancelText: '取消',
                    onOk() {
                        updateDoc(row.id, null, 0);
                    },
                });
            };
            // 打开开放文档新窗口
            const openShareViewWindow = record => {
                if (!record.shareUuid) {
                    message.warning('请先保存文档后再试');
                } else if (record.openVisit !== 1) {
                    message.warning('该文档尚未开启开放访问功能，请在编辑页选择开放后再试');
                } else {
                    window.open(getZyplayerApiBaseUrl() + '/doc-api#/share/home?uuid=' + record.shareUuid);
                }
            };
            const handleActionMenuClick = (item, record) => {
                if (item.key === 'shareView') {
                    openShareViewWindow(record);
                }
            }
            onMounted(() => {
                searchDocList();
            });
            return {
                searchParam,
                docList,
                docListLoading,
                newDocVisible,
                docEdit,
	            docBaseInfoRef,
                searchDocList,
                openNewDoc,
                handleNewDocOk,
                deleteDoc,
                editDoc,
                handleTableChange,
                openShareViewWindow,
                handleActionMenuClick,
                pagination,
                newDocRules: {
                    name: [{required: true, message: '请输入文档名称', trigger: 'change'}],
                    docUrl: [{required: true, message: '请输入文档地址', trigger: 'change'}],
                    jsonContent: [{required: true, message: '请输入JSON格式的swagger文档内容', trigger: 'change'}],
                    docType: [{type: 'number', required: true, message: '请选择文档类型', trigger: 'change'}],
                    openVisit: [{type: 'number', required: true, message: '请选择是否开放访问', trigger: 'change'}],
                    docStatus: [{type: 'number', required: true, message: '请选择文档状态', trigger: 'change'}],
                },
                docListColumns: [
                    {title: 'ID', dataIndex: 'id', width: 70},
                    {title: '文档名称', dataIndex: 'name', width: 250},
                    {title: '文档类型', dataIndex: 'docType', width: 120},
                    {title: '开放访问', dataIndex: 'openVisit', width: 90},
                    {title: '状态', dataIndex: 'docStatus', width: 90},
                    {title: '文档地址', dataIndex: 'docUrl'},
                    {title: '目标域名', dataIndex: 'rewriteDomain', width: 250},
                    {title: '操作', dataIndex: 'operation', fixed: 'right', width: 200},
                ],
            };
        },
    };
</script>
