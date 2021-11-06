<template>
<!--    <a-textarea placeholder="" v-model:value="bodyRowParam" :auto-size="{ minRows: 15, maxRows: 15 }"></a-textarea>-->
    <ace-editor v-model:value="bodyRowParam" @init="rowParamInit" lang="json" theme="monokai" width="100%" height="100" :options="rowParamConfig"></ace-editor>
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
    import aceEditor from "../../assets/ace-editor";

    export default {
        props: {
            paramList: {
                type: Array,
                required: true
            },
        },
        components: {
            CloseOutlined, UploadOutlined, aceEditor
        },
        emits: [],
        setup(props, { attrs, slots, emit, expose}) {
            let paramList = props.paramList;
            let bodyParamObj = '';
            let getChildren = paramObj => {
                if (paramObj.children) {
                    let bodyParamObj = {};
                    paramObj.children.forEach(item => {
                        bodyParamObj[item.name] = getChildren(item);
                    });
                    return bodyParamObj;
                }
                return '';
            }
            if (paramList.length === 1) {
                bodyParamObj = getChildren(paramList[0]);
            } else if (paramList.length > 1) {
                paramList.forEach(item => {
                    bodyParamObj[item.name] = getChildren(item);
                });
            }
            let bodyRowParam = ref('');
            if (bodyParamObj) {
                bodyRowParam.value = JSON.stringify(bodyParamObj, null, 4);
            }
            const getParam = () => {
                return bodyRowParam.value;
            }
            // 编辑器
            let rowParamEditor = ref();
            const rowParamInit = editor => {
                rowParamEditor.value = editor;
                rowParamEditor.value.setFontSize(16);
            }
            return {
                getParam,
                // 编辑器
                rowParamInit,
                bodyRowParam,
                rowParamConfig: {
                    wrap: true,
                    autoScrollEditorIntoView: true,
                    enableBasicAutocompletion: true,
                    enableSnippets: true,
                    enableLiveAutocompletion: true,
                    minLines: 18,
                    maxLines: 18,
                },
            };
        },
    };
</script>
