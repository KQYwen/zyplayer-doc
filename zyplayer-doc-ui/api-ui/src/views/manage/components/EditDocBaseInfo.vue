<template>
    <a-form layout="horizontal" ref="newDocFormRef" :rules="newDocRules" :model="docEdit" :label-col="{span: 4}" :wrapper-col="{span: 18}">
        <a-form-item label="文档名称" required name="name">
            <a-input placeholder="请输入文档名称" v-model:value="docEdit.name"></a-input>
        </a-form-item>
        <a-form-item label="文档类型" required name="docType">
            <a-radio-group v-model:value="docEdit.docType">
                <a-radio :value="1">Swagger URL</a-radio>
                <a-radio :value="2">Swagger JSON</a-radio>
                <a-radio :value="3">OpenApi URL</a-radio>
                <a-radio :value="4">OpenApi JSON</a-radio>
	            <a-radio :value="5">自建API</a-radio>
            </a-radio-group>
        </a-form-item>
        <a-form-item label="文档地址" required name="docUrl" v-if="docEdit.docType === 1">
            <a-input placeholder="请输入文档地址URL" v-model:value="docEdit.docUrl"></a-input>
            <template #extra>
                查看文档地址
                <a-popover title="文档地址支持以下任一格式">
                    <template #content>
                        <p>格式一：http://zyplayer.com/v2/api-docs</p>
                        <p>格式二：http://zyplayer.com/swagger-resources</p>
                        <p>格式三：http://zyplayer.com/swagger-ui.html</p>
                    </template>
                    <a>示例</a>
                </a-popover>
            </template>
        </a-form-item>
        <a-form-item label="文档内容" required name="jsonContent" v-else-if="docEdit.docType === 2">
<!--                textarea在内容很多的时候（>300KB）会卡顿，ace不会-->
            <ace-editor v-model:value="docEdit.jsonContent" lang="json" theme="monokai" width="100%" height="100" :options="aceEditorConfig"></ace-editor>
<!--                <a-textarea placeholder="请输入JSON格式的Swagger文档内容" v-model:value="docEdit.jsonContent" :auto-size="{ minRows: 5, maxRows: 10 }"></a-textarea>-->
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
                        <p>格式一：http://zyplayer.com/v3/api-docs</p>
                    </template>
                    <a>示例</a>
                </a-popover>
            </template>
        </a-form-item>
        <a-form-item label="文档内容" required name="jsonContent" v-else-if="docEdit.docType === 4">
            <ace-editor v-model:value="docEdit.jsonContent" lang="json" theme="monokai" width="100%" height="100" :options="aceEditorConfig"></ace-editor>
<!--                <a-textarea placeholder="请输入JSON格式的OpenApi文档内容" v-model:value="docEdit.jsonContent" :auto-size="{ minRows: 5, maxRows: 10 }"></a-textarea>-->
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
                        <p>可便于不同环境间的接口测试，例：http://zyplayer.com</p>
                    </template>
                    <a>说明</a>
                </a-popover>
            </template>
        </a-form-item>
        <a-form-item label="开放访问" required name="openVisit">
            <a-radio-group v-model:value="docEdit.openVisit">
                <a-radio :value="0">否</a-radio>
                <a-radio :value="1">开放访问</a-radio>
            </a-radio-group>
            <template #extra>
                开放访问后无需登录即可通过<a @click="openShareViewWindow(docEdit)">开放文档URL</a>访问该文档信息
            </template>
        </a-form-item>
        <a-form-item label="状态" required name="docStatus">
            <a-radio-group v-model:value="docEdit.docStatus">
                <a-radio :value="1">启用</a-radio>
                <a-radio :value="2">禁用</a-radio>
            </a-radio-group>
        </a-form-item>
    </a-form>
</template>

<script>
    import { toRefs, ref, reactive, onMounted, watch } from 'vue';
    import {zyplayerApi} from '../../../api';
    import {useStore} from 'vuex';
    import aceEditor from "../../../assets/ace-editor";
    import EditShareInstruction from "../components/EditShareInstruction.vue";
    import {getZyplayerApiBaseUrl} from "../../../api/request/utils";
    import {DownOutlined, LinkOutlined, EditOutlined} from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue';

    export default {
	    emits: ['edit'],
        components: {aceEditor, EditShareInstruction, DownOutlined, LinkOutlined, EditOutlined},
        props: {
            doc: {
                type: Object,
                required: true
            },
        },
        setup(props, {emit}) {
            const store = useStore();
            let docEdit = ref({});
            let newDocFormRef = ref();
            watch(() => props.doc, () => {
                editDoc();
            });
            const handleNewDocOk = async () => {
                newDocFormRef.value.validate().then(() => {
                    zyplayerApi.apiDocAdd(docEdit.value).then(res => {
                        // searchDocList();
                        store.commit('addDocChangedNum');
                    });
                }).catch(error => {
                    console.log('error', error);
                });
            };
            const editDoc = () => {
                docEdit.value = props.doc;
            };
            const getDoc = async () => {
                await newDocFormRef.value.validate();
                return docEdit.value;
            };
            const updateDoc = async (id, docStatus, yn) => {
                zyplayerApi.apiDocUpdate({id, docStatus, yn}).then(res => {
                    // searchDocList();
                    store.commit('addDocChangedNum');
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
            onMounted(() => {
                editDoc();
            });
            return {
                docEdit,
                newDocFormRef,
                handleNewDocOk,
                editDoc,
                getDoc,
                openShareViewWindow,
                newDocRules: {
                    name: [{required: true, message: '请输入文档名称', trigger: 'change'}],
                    docUrl: [{required: true, message: '请输入文档地址', trigger: 'change'}],
                    jsonContent: [{required: true, message: '请输入JSON格式的swagger文档内容', trigger: 'change'}],
                    docType: [{type: 'number', required: true, message: '请选择文档类型', trigger: 'change'}],
                    openVisit: [{type: 'number', required: true, message: '请选择是否开放访问', trigger: 'change'}],
                    docStatus: [{type: 'number', required: true, message: '请选择文档状态', trigger: 'change'}],
                },
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
