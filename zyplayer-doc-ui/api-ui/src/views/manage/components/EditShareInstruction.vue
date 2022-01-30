<template>
    <mavon-editor ref="mavonEditor" v-model="shareInstruction" :toolbars="toolbars"
                  :externalLink="false" @imgAdd="addMarkdownImage" :imageFilter="imageFilter"
                  style="height: calc(100vh - 80px);"
                  placeholder="请录入开放文档说明"/>
</template>

<script>
    import { toRefs, ref, reactive, onMounted, nextTick, watch } from 'vue';
    import {zyplayerApi} from '../../../api';
    import {useStore} from 'vuex';
    import aceEditor from "../../../assets/ace-editor";
    import {getZyplayerApiBaseUrl} from "../../../api/request/utils";
    import {BranchesOutlined, InfoCircleOutlined} from '@ant-design/icons-vue';
    import {mavonEditor, markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import { message } from 'ant-design-vue';

    export default {
        components: {aceEditor, BranchesOutlined, mavonEditor, InfoCircleOutlined},
        props: {
            doc: {
                type: Object,
                required: true
            },
        },
        setup(props, {emit}) {
            const store = useStore();
            let docEdit = ref({});
            let shareInstruction = ref('');
            watch(() => props.doc, () => {
                editDoc();
            });
            const getDoc = () => {
                if (!shareInstruction.value) {
                    message.error('请输入开放文档的说明');
                    return false;
                }
                return {
                    id: docEdit.value.id,
                    shareInstruction: shareInstruction.value
                };
            };
            const editDoc = async () => {
                docEdit.value = props.doc;
                shareInstruction.value = props.doc.shareInstruction;
            };
            const addMarkdownImage = (pos, file) => {
            };
            const imageFilter = (pos, file) => {
                message.error('暂不支持图片上传');
                return false;
            };
            onMounted(() => {
                editDoc();
            });
            return {
                docEdit,
                shareInstruction,
                getDoc,
                editDoc,
                imageFilter,
                addMarkdownImage,
                toolbars: {
                    bold: true, // 粗体
                    italic: true, // 斜体
                    header: true, // 标题
                    underline: true, // 下划线
                    strikethrough: true, // 中划线
                    mark: true, // 标记
                    superscript: true, // 上角标
                    subscript: true, // 下角标
                    quote: true, // 引用
                    ol: true, // 有序列表
                    ul: true, // 无序列表
                    link: true, // 链接
                    imagelink: false, // 图片链接
                    code: true, // code
                    table: true, // 表格
                    fullscreen: true, // 全屏编辑
                    readmodel: true, // 沉浸式阅读
                    /* 1.3.5 */
                    undo: true, // 上一步
                    redo: true, // 下一步
                    trash: true, // 清空
                    save: true, // 保存（触发events中的save事件）
                    /* 1.4.2 */
                    navigation: false, // 导航目录
                    /* 2.1.8 */
                    alignleft: true, // 左对齐
                    aligncenter: true, // 居中
                    alignright: true, // 右对齐
                    /* 2.2.1 */
                    subfield: true, // 单双栏模式
                    preview: true, // 预览
                },
            };
        },
    };
</script>
