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
            <a-button @click="searchDocList" type="primary">查询</a-button>
            <a-button @click="openNewDoc" :style="{ marginLeft: '8px' }">新建</a-button>
        </a-form-item>
    </a-form>
    <a-table :dataSource="docList" :columns="docListColumns" size="middle"
             :loading="docListLoading" :pagination="false"
             :scroll="{ x: 1400, y: 'calc(100vh - 300px)' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'operation'">
                <a-button type="link" @click="editDoc(record)">编辑</a-button>
                <a-popconfirm title="确定要删除吗？" @confirm="deleteDoc(record)">
                    <a-button type="link" danger>删除</a-button>
                </a-popconfirm>
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
    <a-modal v-model:visible="newDocVisible" :title="docEdit.isNew?'新增文档':'编辑文档'" @ok="handleNewDocOk" :width="600">
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
                <template #extra>
                    查看文档地址
                    <a-popover title="文档地址支持以下任一格式">
                        <template #content>
                            <p>格式一：http://doc.zyplayer.com/v2/api-docs</p>
                            <p>格式二：http://doc.zyplayer.com/swagger-resources</p>
                            <p>格式三：http://doc.zyplayer.com/swagger-ui.html</p>
                        </template>
                        <a>示例</a>
                    </a-popover>
                </template>
            </a-form-item>
            <a-form-item label="文档内容" required name="jsonContent" v-else-if="docEdit.docType === 2">
                <a-textarea placeholder="请输入JSON格式的swagger文档内容" v-model:value="docEdit.jsonContent"></a-textarea>
                <template #extra>
                    查看文档内容
                    <a-popover title="文档内容说明">
                        <template #content>
                            <div>支持以下格式的Swagger文档内容输入，其中 {"swagger": "2.0"} 为必要属性</div>
                            <div v-highlight>
                                <pre><code class="lang-json">{{swaggerDocDemo}}</code></pre>
                            </div>
                        </template>
                        <a>说明</a>
                    </a-popover>
                </template>
            </a-form-item>
            <a-form-item label="目标域名" name="rewriteDomain">
                <a-input placeholder="请输入目标域名" v-model:value="docEdit.rewriteDomain"></a-input>
                <template #extra>
                    目标域名
                    <a-popover title="目标域名说明">
                        <template #content>
                            <p>在文档的在线调试界面，访问的域名可以初始为此处录入的域名，而非文档本身的域名地址</p>
                            <p>可便于不同环境间的接口测试，例：http://doc.zyplayer.com</p>
                        </template>
                        <a>说明</a>
                    </a-popover>
                </template>
            </a-form-item>
<!--            <a-form-item label="开放访问" required name="openVisit">-->
<!--                <a-radio-group v-model:value="docEdit.openVisit">-->
<!--                    <a-radio :value="0">否</a-radio>-->
<!--                    <a-radio :value="1">开放</a-radio>-->
<!--                </a-radio-group>-->
<!--            </a-form-item>-->
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
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';

    export default {
        setup() {
            const store = useStore()
            let docList = ref([]);
            let docListLoading = ref(false);
            let searchParam = ref({docType: '', openVisit: '', docStatus: ''});
            const searchDocList = async () => {
                docListLoading.value = true;
                zyplayerApi.swaggerDocList(searchParam.value).then(res => {
                    setTimeout(() => docListLoading.value = false, 500);
                    docList.value = res.data || [];
                });
            };
            let docEdit = ref({});
            let newDocFormRef = ref();
            let newDocVisible = ref(false);
            const handleNewDocOk = async () => {
                newDocFormRef.value.validate().then(() => {
                    zyplayerApi.swaggerDocAdd(docEdit.value).then(res => {
                        searchDocList();
                        newDocVisible.value = false;
                        store.commit('addDocChangedNum');
                    });
                }).catch(error => {
                    console.log('error', error);
                });
            };
            const openNewDoc = async () => {
                newDocVisible.value = true;
                docEdit.value = {
                    docType: 1, openVisit: 0, docStatus: 1, isNew: 1
                };
            };
            const editDoc = (record) => {
                docEdit.value = {...record};
                newDocVisible.value = true;
            };
            const updateDoc = async (id, docStatus, yn) => {
                zyplayerApi.swaggerDocUpdate({id, docStatus, yn}).then(res => {
                    searchDocList();
                    store.commit('addDocChangedNum');
                });
            };
            const deleteDoc = async (row) => updateDoc(row.id, null, 0);
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
                deleteDoc,
                editDoc,
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
                    {title: '文档类型', dataIndex: 'docType', width: 90},
                    {title: '开放访问', dataIndex: 'openVisit', width: 90},
                    {title: '状态', dataIndex: 'docStatus', width: 90},
                    {title: '文档地址', dataIndex: 'docUrl'},
                    {title: '目标域名', dataIndex: 'rewriteDomain', width: 250},
                    {title: '操作', dataIndex: 'operation', fixed: 'right', width: 170},
                ],
                swaggerDocDemo:
                    '{\n'
                    + '    "swagger": "2.0",\n'
                    + '    "info": {},\n'
                    + '    "host": "doc.zyplayer.com",\n'
                    + '    "basePath":"/",\n'
                    + '    "tags": [],\n'
                    + '    "paths": {},\n'
                    + '    "definitions": {}\n'
                    + '}'
            };
        },
    };
</script>
