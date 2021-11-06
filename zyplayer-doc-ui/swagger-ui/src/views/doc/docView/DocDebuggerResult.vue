<template>
    <div style="margin-bottom: 30px;">
        <a-tabs v-model:activeKey="activePage" closable @tab-click="" style="padding: 5px 10px 0;">
            <a-tab-pane tab="Body" key="body" forceRender>
                <div style="margin-bottom: 10px;">
                    <a-radio-group v-model:value="bodyShowType" @change="bodyShowTypeChange" size="small">
                        <a-radio-button value="format">格式化</a-radio-button>
                        <a-radio-button value="row">原始值</a-radio-button>
                        <a-radio-button value="preview">预览</a-radio-button>
                    </a-radio-group>
                    <a-select v-if="bodyShowType === 'format'" placeholder="格式化" v-model:value="bodyShowFormatType" size="small" style="margin-left: 10px;">
                        <a-select-option value="json">JSON</a-select-option>
                        <a-select-option value="html">HTML</a-select-option>
                        <a-select-option value="xml">XML</a-select-option>
                        <a-select-option value="text">TEXT</a-select-option>
                    </a-select>
                </div>
                <ace-editor v-if="bodyShowType === 'format'" v-model:value="resultDataContent" @init="resultDataInit" :lang="bodyShowFormatType" theme="monokai" width="100%" height="100" :options="resultDataConfig"></ace-editor>
                <ace-editor v-else-if="bodyShowType === 'row'" v-model:value="resultDataContent" @init="resultDataInit" lang="text" theme="chrome" width="100%" height="100" :options="resultDataConfig"></ace-editor>
                <div v-else-if="bodyShowType === 'preview'">
                    <template v-if="bodyShowFormatPreview === 'html'">
                        <iframe ref="previewHtmlRef" width="100%" height="570px" style="border: 0;"></iframe>
                    </template>
                    <template v-else>{{resultDataContent}}</template>
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
                <span class="status-info-box">状态码：<span>{{resultData.status||'200'}}</span>耗时：<span>{{resultData.useTime||0}} ms</span>大小：<span>{{resultData.bodyLength||0}} B</span></span>
            </template>
        </a-tabs>
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import {useRouter, useRoute} from "vue-router";
    import {useStore} from 'vuex';
    import {message} from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import ParamTable from '../../../components/params/ParamTable.vue'
    import ParamBody from '../../../components/params/ParamBody.vue'
    import {CloseOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../../api";
    import aceEditor from "../../../assets/ace-editor";

    export default {
        props: {
            result: {
                type: Object,
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
            let bodyShowFormatType = ref('json');
            let bodyShowFormatPreview = ref('');
            let resultHeaders = ref([]);
            let resultCookies = ref([]);
            let resultDataContent = ref('');
            let resultData = ref({});
            let previewHtmlRef = ref();
            const initData = () => {
                if (props.result.data) {
                    resultData.value = props.result.data;
                    if (props.result.data.data) {
                        try {
                            let realData = JSON.parse(props.result.data.data);
                            resultDataContent.value = JSON.stringify(realData, null, 4);
                        } catch (e) {
                            resultDataContent.value = props.result.data.data;
                        }
                    } else {
                        resultDataContent.value = JSON.stringify(props.result.data, null, 4);
                    }
                    if (props.result.data.headers) {
                        resultHeaders.value = props.result.data.headers;
                        // 依据返回值header判断类型
                        let contentType = resultHeaders.value.find(item => item.name === 'Content-Type');
                        if (contentType && contentType.value) {
                            if (contentType.value.indexOf('text/html') >= 0) {
                                bodyShowFormatType.value = 'html';
                                bodyShowFormatPreview.value = 'html';
                            } else if (contentType.value.indexOf('json') >= 0) {
                                bodyShowFormatType.value = 'json';
                                bodyShowFormatPreview.value = 'json';
                            }
                        }
                    }
                    if (props.result.data.cookies) {
                        resultCookies.value = props.result.data.cookies;
                    }
                }
            };
            initData();
            watch(result, () => initData());
            // 编辑器
            const resultDataInit = editor => {
                editor.setFontSize(16);
            }
            const bodyShowTypeChange = () => {
                if (bodyShowType.value === 'preview') {
                    setTimeout(() => {
                        previewHtmlRef.value.contentDocument.write(resultDataContent.value);
                    }, 0);
                }
            }
            return {
                activePage,
                bodyShowType,
                bodyShowTypeChange,
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
                resultDataContent,
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
    .status-info-box span{color: #00aa00; margin-right: 15px;}
    .status-info-box span:last-child{margin-right: 0;}
</style>
