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
             :loading="docListLoading" :pagination="pagination"
             @change="handleTableChange"
             :scroll="{ x: 1400, y: 'calc(100vh - 300px)' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'operation'">
                <a-button size="small" type="link" @click="editDoc(record)">编辑</a-button>
                <a-popconfirm title="确定要删除吗？" @confirm="deleteDoc(record)">
                    <a-button size="small" type="link" danger>删除</a-button>
                </a-popconfirm>
                <a-dropdown :trigger="['click']">
                    <template #overlay>
                        <a-menu @click="handleActionMenuClick($event, record)">
                            <a-menu-item key="shareView"><link-outlined /> 查看开放文档</a-menu-item>
                            <a-menu-item key="shareInstruction"><edit-outlined /> 编辑开放文档说明</a-menu-item>
                        </a-menu>
                    </template>
                    <a-button type="link" size="small">更多<DownOutlined /></a-button>
                </a-dropdown>
            </template>
            <template v-if="column.dataIndex === 'docType'">
                <a-tag color="red" v-if="text === 1">Swagger URL</a-tag>
                <a-tag color="blue" v-else-if="text === 2">Swagger JSON</a-tag>
                <a-tag color="green" v-else-if="text === 4">OpenApi JSON</a-tag>
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
    <a-modal v-model:visible="newDocVisible" :title="docEdit.isNew?'新增文档':'编辑文档'" @ok="handleNewDocOk" :width="800">
        <a-form layout="horizontal" ref="newDocFormRef" :rules="newDocRules" :model="docEdit" :label-col="{span: 4}" :wrapper-col="{span: 20}">
            <a-form-item label="文档名称" required name="name">
                <a-input placeholder="请输入文档名称" v-model:value="docEdit.name"></a-input>
            </a-form-item>
            <a-form-item label="文档类型" required name="docType">
                <a-radio-group v-model:value="docEdit.docType">
                    <a-radio :value="1">Swagger URL</a-radio>
                    <a-radio :value="2">Swagger JSON</a-radio>
                    <a-radio :value="3">OpenApi URL</a-radio>
                    <a-radio :value="4">OpenApi JSON</a-radio>
                    <a-radio :value="5" disabled>自建API</a-radio>
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
<!--                尝试使用ace编辑器，但感觉除了卡顿意义不大，不会在这里编辑json内容，暂时用textarea代替-->
<!--                <ace-editor v-model:value="docEdit.jsonContent" lang="json" theme="monokai" width="100%" height="100" :options="aceEditorConfig"></ace-editor>-->
                <a-textarea placeholder="请输入JSON格式的Swagger文档内容" v-model:value="docEdit.jsonContent" :auto-size="{ minRows: 5, maxRows: 10 }"></a-textarea>
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
            <a-form-item label="文档地址" required name="docUrl" v-if="docEdit.docType === 3">
                <a-input placeholder="请输入文档地址URL" v-model:value="docEdit.docUrl"></a-input>
                <template #extra>
                    查看文档地址
                    <a-popover title="文档地址支持以下任一格式">
                        <template #content>
                            <p>格式一：http://doc.zyplayer.com/v3/api-docs</p>
                        </template>
                        <a>示例</a>
                    </a-popover>
                </template>
            </a-form-item>
            <a-form-item label="文档内容" required name="jsonContent" v-else-if="docEdit.docType === 4">
<!--                <ace-editor v-model:value="docEdit.jsonContent" lang="json" theme="monokai" width="100%" height="100" :options="aceEditorConfig"></ace-editor>-->
                <a-textarea placeholder="请输入JSON格式的OpenApi文档内容" v-model:value="docEdit.jsonContent" :auto-size="{ minRows: 5, maxRows: 10 }"></a-textarea>
                <template #extra>
                    查看文档内容
                    <a-popover title="文档内容说明">
                        <template #content>
                            <div>支持以下格式的OpenApi文档内容输入，其中 {"openapi": "3.x.x"} 为必要属性</div>
                            <div v-highlight>
                                <pre><code class="lang-json">{{openApiDocDemo}}</code></pre>
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
            <a-form-item label="开放访问" required name="openVisit">
                <a-radio-group v-model:value="docEdit.openVisit">
                    <a-radio :value="0">否</a-radio>
                    <a-radio :value="1">开放</a-radio>
                </a-radio-group>
                <template #extra>
                    开放访问后无需登录即可通过<a @click="openShareViewWindow(docEdit)">开放文档URL</a>访问改文档信息
                </template>
            </a-form-item>
            <a-form-item label="状态" required name="docStatus">
                <a-radio-group v-model:value="docEdit.docStatus">
                    <a-radio :value="1">启用</a-radio>
                    <a-radio :value="2">禁用</a-radio>
                </a-radio-group>
            </a-form-item>
        </a-form>
    </a-modal>
    <EditShareInstruction ref="instruction"></EditShareInstruction>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';
    import aceEditor from "../../assets/ace-editor";
    import EditShareInstruction from "./components/EditShareInstruction.vue";
    import {getZyplayerApiBaseUrl} from "../../api/request/utils";
    import {DownOutlined, LinkOutlined, EditOutlined} from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue';

    export default {
        components: {aceEditor, EditShareInstruction, DownOutlined, LinkOutlined, EditOutlined},
        setup() {
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
            let newDocFormRef = ref();
            let newDocVisible = ref(false);
            const handleNewDocOk = async () => {
                newDocFormRef.value.validate().then(() => {
                    zyplayerApi.apiDocAdd(docEdit.value).then(res => {
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
                zyplayerApi.apiDocDetail({id: record.id}).then(res => {
                    docEdit.value = res.data;
                    newDocVisible.value = true;
                });
            };
            const updateDoc = async (id, docStatus, yn) => {
                zyplayerApi.apiDocUpdate({id, docStatus, yn}).then(res => {
                    searchDocList();
                    store.commit('addDocChangedNum');
                });
            };
            const deleteDoc = async (row) => updateDoc(row.id, null, 0);

            const openShareViewWindow = record => {
                if (!record.shareUuid) {
                    message.warning('请先保存文档后再试');
                } else if (record.openVisit === 1) {
                    window.open(getZyplayerApiBaseUrl() + '/doc-api#/share/home?uuid=' + record.shareUuid);
                } else {
                    message.warning('改文档尚未开启开放访问功能，请在编辑页选择开放后再试');
                }
            };

            const handleActionMenuClick = (item, record) => {
                if (item.key === 'shareView') {
                    openShareViewWindow(record);
                } else if (item.key === 'shareInstruction') {
                    instruction.value.editDoc(record.id);
                }
            }
            let instruction = ref();
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
                handleTableChange,
                openShareViewWindow,
                handleActionMenuClick,
                pagination,
                instruction,
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
                aceEditorConfig: {
                    wrap: true,
                    autoScrollEditorIntoView: true,
                    enableBasicAutocompletion: true,
                    enableSnippets: true,
                    enableLiveAutocompletion: true,
                    minLines: 10,
                    maxLines: 15,
                },
                swaggerDocDemo:
                    '{\n'
                    + '    "swagger": "2.0",\n'
                    + '    "info": {},\n'
                    + '    "host": "doc.zyplayer.com",\n'
                    + '    "basePath":"/",\n'
                    + '    "tags": [],\n'
                    + '    "paths": {},\n'
                    + '    "definitions": {}\n'
                    + '}',
                openApiDocDemo:
                    '{\n'
                    + '    "openapi": "3.0.3",\n'
                    + '    "components": {}\n'
                    + '    "servers": [],\n'
                    + '    "paths": {},\n'
                    + '    "info": {},\n'
                    + '}',
            };
        },
    };
</script>
