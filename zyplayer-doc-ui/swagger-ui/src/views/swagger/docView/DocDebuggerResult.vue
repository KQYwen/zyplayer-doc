<template>
    <div v-if="result.data" style="margin-bottom: 30px;">
        <div v-if="result.data.data || result.data.status === 200" style="margin-bottom: 30px;">
            <a-tabs v-model:activeKey="activePage" @tab-click="" style="padding: 5px 10px 0;">
                <a-tab-pane tab="Body" key="body" forceRender>
                    <div style="margin-bottom: 10px;">
                        <a-radio-group v-model:value="bodyShowType" @change="bodyShowTypeChange" size="small">
                            <a-radio-button value="format">格式化</a-radio-button>
                            <a-radio-button value="row">原始值</a-radio-button>
                            <a-radio-button value="preview">预览</a-radio-button>
                        </a-radio-group>
                        <a-select v-if="bodyShowType === 'format'" v-model:value="bodyShowFormatType" size="small" style="margin-left: 10px;width: 100px;">
                            <a-select-option value="json">JSON</a-select-option>
                            <a-select-option value="html">HTML</a-select-option>
                            <a-select-option value="xml">XML</a-select-option>
                            <a-select-option value="javascript">JavaScript</a-select-option>
                            <a-select-option value="text">TEXT</a-select-option>
                        </a-select>
                    </div>
                    <ace-editor v-if="bodyShowType === 'format'" v-model:value="resultDataContentFormat" @init="resultDataInit" :lang="bodyShowFormatType" theme="monokai" width="100%" height="100" :options="resultDataConfig"></ace-editor>
                    <ace-editor v-else-if="bodyShowType === 'row'" v-model:value="resultDataContentOrigin" @init="resultDataInit" lang="text" theme="chrome" width="100%" height="100" :options="resultDataConfig"></ace-editor>
                    <div v-else-if="bodyShowType === 'preview'">
                        <template v-if="bodyShowFormatPreview === 'html'">
                            <iframe ref="previewHtmlRef" width="100%" height="570px" style="border: 0;"></iframe>
                        </template>
                        <template v-else>{{resultDataContentOrigin}}</template>
                    </div>
                </a-tab-pane>
                <a-tab-pane tab="Headers" key="headers" forceRender>
                    <a-table :dataSource="resultHeaders"
                             :columns="resultHeadersColumns" size="small"
                             :pagination="false"
                             :scroll="{ y: '300px' }">
                    </a-table>
                </a-tab-pane>
                <a-tab-pane tab="Cookies" key="cookies" forceRender>
                    <a-table :dataSource="resultCookies"
                             :columns="resultCookiesColumns" size="small"
                             :pagination="false"
                             :scroll="{ y: '300px' }">
                    </a-table>
                </a-tab-pane>
                <template #rightExtra>
                    <span class="status-info-box">
                        状态码：<span>{{resultData.status||'200'}}</span>
				        <a-divider type="vertical" />
                        耗时：<span>{{unitConvert.formatSeconds(resultData.useTime||0)}}</span>
				        <a-divider type="vertical" />
                        大小：<span>{{unitConvert.formatFileSize(resultData.contentLength||0)}}</span>
                    </span>
                </template>
            </a-tabs>
        </div>
        <div v-else>
            <a-tabs style="padding: 5px 10px 0;">
                <a-tab-pane tab="请求失败" key="body" forceRender>
                    <div style="color: #f00;">{{result.data.errorMsg}}</div>
                </a-tab-pane>
                <template #rightExtra>
                    <span class="status-info-box">
                        耗时：<span>{{unitConvert.formatSeconds(resultData.useTime||0)}}</span>
                    </span>
                </template>
            </a-tabs>
        </div>
    </div>
    <div v-else-if="loading" style="margin-top: 20px;">
        <a-spin tip="请求执行中...">
            <a-skeleton />
        </a-spin>
    </div>
    <div v-else style="margin-top: 20px;color: #aaa;">
        <a-empty description="点击 ‘发送请求’ 获取请求结果" />
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import {useRouter, useRoute} from "vue-router";
    import {useStore} from 'vuex';
    import {message} from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import xmlFormatter from 'xml-formatter'
    import ParamTable from '../../../components/params/ParamTable.vue'
    import ParamBody from '../../../components/params/ParamBody.vue'
    import {CloseOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../../api";
    import aceEditor from "../../../assets/ace-editor";
    import unitConvert from "../../../assets/utils/unitConvert.js";

    export default {
        props: {
            result: {
                type: Object,
                required: true
            },
            loading: {
                type: Boolean,
                required: true
            },
        },
        components: {
            CloseOutlined, ParamTable, ParamBody, aceEditor
        },
        setup(props) {
            const { result } = toRefs(props);
            let activePage = ref('body');
            let bodyShowType = ref('format');
            // 格式化展示的类型，用户可以修改
            let bodyShowFormatType = ref('json');
            // 预览格式，依据返回值的content-type得出，不可修改
            let bodyShowFormatPreview = ref('');
            let resultHeaders = ref([]);
            let resultCookies = ref([]);
            let resultDataContentOrigin = ref('');
            let resultDataContentFormat = ref('');
            let resultData = ref({});
            let previewHtmlRef = ref();
            const bodyShowTypeChange = () => {
                if (bodyShowType.value === 'preview') {
                    setTimeout(() => {
                        if (previewHtmlRef.value) {
                            previewHtmlRef.value.contentDocument.write(resultDataContentOrigin.value);
                        }
                    }, 0);
                }
            }
            const initData = () => {
                resultDataContentOrigin.value = '';
                resultDataContentFormat.value = '';
                if (props.result.data) {
                    resultData.value = props.result.data;
                    if (props.result.data.headers) {
                        resultHeaders.value = props.result.data.headers;
                        // 依据返回值header判断类型
                        let contentType = resultHeaders.value.find(item => item.name === 'Content-Type');
                        if (contentType && contentType.value) {
                            if (contentType.value.indexOf('text/html') >= 0) {
                                bodyShowFormatType.value = 'html';
                            } else if (contentType.value.indexOf('text/plain') >= 0) {
                                bodyShowFormatType.value = 'text';
                            } else if (contentType.value.indexOf('application/json') >= 0) {
                                bodyShowFormatType.value = 'json';
                            } else if (contentType.value.indexOf('application/xml') >= 0 || contentType.value.indexOf('text/xml') >= 0) {
                                bodyShowFormatType.value = 'xml';
                            } else if (contentType.value.indexOf('application/javascript') >= 0) {
                                bodyShowFormatType.value = 'javascript';
                            }
                            bodyShowFormatPreview.value = bodyShowFormatType.value;
                        }
                    }
                    if (props.result.data.cookies) {
                        resultCookies.value = props.result.data.cookies;
                    }
                    if (props.result.data.data || props.result.data.status === 200) {
                        resultDataContentFormat.value = props.result.data.data;
                        resultDataContentOrigin.value = props.result.data.data;
                        try {
                            if (bodyShowFormatType.value === 'xml') {
                                resultDataContentFormat.value = xmlFormatter(resultDataContentOrigin.value);
                            } else if (bodyShowFormatType.value === 'json') {
                                resultDataContentFormat.value = JSON.stringify(JSON.parse(resultDataContentOrigin.value), null, 4);
                            } else if (bodyShowFormatType.value === 'javascript') {
                                // TODO 暂未测试
                                resultDataContentFormat.value = JSON.stringify(resultDataContentOrigin.value, null, 4);
                            }
                        } catch (e) {
                            resultDataContentFormat.value = props.result.data.data;
                        }
                    } else {
                        let errorSuffix = '\n// 请求失败，以下为封装的返回值对象，仅供参考\n\n';
                        resultDataContentOrigin.value = errorSuffix + JSON.stringify(props.result.data);
                        resultDataContentFormat.value = errorSuffix + JSON.stringify(props.result.data, null, 4);
                    }
                    bodyShowTypeChange();
                }
            };
            initData();
            watch(result, () => initData());
            // 编辑器
            const resultDataInit = editor => {
                editor.setFontSize(16);
            }
            return {
                activePage,
                bodyShowType,
                bodyShowTypeChange,
                unitConvert,
                bodyShowFormatType,
                bodyShowFormatPreview,
                previewHtmlRef,
                resultData,
                resultHeaders,
                resultCookies,
                resultHeadersColumns: [
                    {title: 'KEY', dataIndex: 'name'},
                    {title: 'VALUE', dataIndex: 'value'},
                ],
                resultCookiesColumns: [
                    {title: 'KEY', dataIndex: 'name'},
                    {title: 'VALUE', dataIndex: 'value'},
                ],
                // 编辑器
                resultDataInit,
                resultDataContentOrigin,
                resultDataContentFormat,
                resultDataConfig: {
                    wrap: true,
                    readOnly: true,
                    autoScrollEditorIntoView: true,
                    enableBasicAutocompletion: true,
                    enableSnippets: true,
                    enableLiveAutocompletion: true,
                    minLines: 30,
                    maxLines: 30,
                },
            };
        },
    };
</script>
<style>
    .status-info-box{color: #888;}
    .status-info-box span{color: #00aa00;}
    .status-info-box span:last-child{margin-right: 0;}
</style>
