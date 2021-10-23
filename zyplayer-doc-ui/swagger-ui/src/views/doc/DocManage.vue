<template>
    <a-card>
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
                <a-button @click="searchDocList" type="primary">查询</a-button>
                <a-button @click="openNewDoc" :style="{ marginLeft: '8px' }">新建</a-button>
            </a-form-item>
        </a-form>
        <a-table :dataSource="docList" :columns="docListColumns" size="middle"
                 :loading="docListLoading"
                 :scroll="{ x: 1200, y: 'calc(100vh - 340px)' }">
            <template #bodyCell="{ column, text, row }">
                <template v-if="column.dataIndex === 'operation'">
                    <a-button type="link">编辑</a-button>
                    <a-button type="link" danger @click="updateDocStatus(row)">删除</a-button>
                </template>
                <template v-if="column.dataIndex === 'docType'">
                    <a-tag color="red" v-if="text === 1">URL添加</a-tag>
                    <a-tag color="blue" v-else-if="text === 2">JSON内容</a-tag>
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
    </a-card>
    <a-modal v-model:visible="newDocVisible" title="新增文档" @ok="handleNewDocOk" :width="600">
        <a-form layout="horizontal" ref="newDocFormRef" :rules="newDocRules" :model="docEdit" :label-col="{span: 6}" :wrapper-col="{span: 18}">
            <a-form-item label="文档名称" required name="name">
                <a-input placeholder="请输入文档名称" v-model:value="docEdit.name"></a-input>
            </a-form-item>
            <a-form-item label="文档类型" required name="docType">
                <a-radio-group v-model:value="docEdit.docType">
                    <a-radio :value="1">URL添加</a-radio>
                    <a-radio :value="2">JSON内容</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="文档地址" required name="docUrl" v-if="docEdit.docType === 1">
                <a-input placeholder="请输入文档地址URL" v-model:value="docEdit.docUrl"></a-input>
            </a-form-item>
            <a-form-item label="文档内容" required name="jsonContent" v-else-if="docEdit.docType === 2">
                <a-textarea placeholder="请输入JSON格式的swagger文档内容" v-model:value="docEdit.jsonContent"></a-textarea>
            </a-form-item>
            <a-form-item label="开放访问" required name="openVisit">
                <a-radio-group v-model:value="docEdit.openVisit">
                    <a-radio :value="0">否</a-radio>
                    <a-radio :value="1">开放</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="状态" required name="docStatus">
                <a-radio-group v-model:value="docEdit.docStatus">
                    <a-radio :value="1">启用</a-radio>
                    <a-radio :value="2">禁用</a-radio>
                </a-radio-group>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { toRefs, ref, onMounted } from 'vue';
    import {zyplayerApi} from '../../api';

    export default {
        setup() {
            let docList = ref([]);
            let docListLoading = ref(false);
            let searchParam = ref({docType: '', openVisit: '', docStatus: ''});
            const searchDocList = async () => {
                docListLoading.value = true;
                zyplayerApi.swaggerDocList(searchParam.value).then(res => {
                    setTimeout(() => docListLoading.value = false, 500);
                    docList.value = res.data || [];
                });
            }
            let docEdit = ref({});
            let newDocFormRef = ref();
            let newDocVisible = ref(false);
            const handleNewDocOk = async () => {
                newDocFormRef.value.validate().then(() => {
                    zyplayerApi.swaggerDocAdd(docEdit.value).then(res => {
                        searchDocList();
                        newDocVisible.value = false;
                    });
                }).catch(error => {
                    console.log('error', error);
                });
            }
            const openNewDoc = async () => {
                newDocVisible.value = true;
                docEdit.value = {
                    docType: 1, openVisit: 0, docStatus: 1,
                };
            }
            const updateDocStatus = async (row) => {
                zyplayerApi.swaggerDocUpdate({docStatus: 1}).then(res => {
                    searchDocList();
                });
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
                newDocFormRef,
                searchDocList,
                openNewDoc,
                handleNewDocOk,
                updateDocStatus,
                newDocRules: {
                    name: [{required: true, message: '请输入文档名称', trigger: 'change'}],
                    docUrl: [{required: true, message: '请输入文档地址', trigger: 'change'}],
                    jsonContent: [{required: true, message: '请输入JSON格式的swagger文档内容', trigger: 'change'}],
                    docType: [{type: 'number', required: true, message: '请选择文档类型', trigger: 'change'}],
                    openVisit: [{type: 'number', required: true, message: '请选择是否开放访问', trigger: 'change'}],
                    docStatus: [{type: 'number', required: true, message: '请选择文档状态', trigger: 'change'}],
                },
                docListColumns: [
                    {
                        title: 'ID',
                        dataIndex: 'id',
                        width: 70,
                    }, {
                        title: '文档名称',
                        dataIndex: 'name',
                    }, {
                        title: '文档类型',
                        dataIndex: 'docType',
                        width: 90,
                    }, {
                        title: '文档地址',
                        dataIndex: 'docUrl',
                    }, {
                        title: '目标域名',
                        dataIndex: 'rewriteDomain',
                    }, {
                        title: '开放访问',
                        dataIndex: 'openVisit',
                        width: 90,
                    }, {
                        title: '状态',
                        dataIndex: 'docStatus',
                        width: 90,
                    }, {
                        title: '操作',
                        dataIndex: 'operation',
                        fixed: 'right',
                        width: 170,
                    },
                ],
            };
        },
    };
</script>
