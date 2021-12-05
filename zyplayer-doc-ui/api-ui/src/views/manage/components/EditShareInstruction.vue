<template>
    <a-modal v-model:visible="editShareInstructionVisible" @ok="handleNewDocOk" width="90%"
             :bodyStyle="{height: 'calc(100vh - 300px)'}">
        <template #title>
            编辑开放文档说明
            <a-tooltip placement="bottom">
                <template #title>此说明文档将展示在开放文档的首页展示，可点击‘查看开放文档’查看效果</template>
                <info-circle-outlined />
            </a-tooltip>
        </template>
        <mavon-editor ref="mavonEditor" v-model="shareInstruction" :toolbars="toolbars"
                      :externalLink="false"
                      style="height: 100%;"
                      placeholder="请录入文档内容"/>
    </a-modal>
</template>

<script>
    import { toRefs, ref, reactive, onMounted, nextTick } from 'vue';
    import {zyplayerApi} from '../../../api';
    import {useStore} from 'vuex';
    import aceEditor from "../../../assets/ace-editor";
    import {getZyplayerApiBaseUrl} from "../../../api/request/utils";
    import {BranchesOutlined, InfoCircleOutlined} from '@ant-design/icons-vue';
    import {mavonEditor, markdownIt} from 'mavon-editor'
    import { message } from 'ant-design-vue';

    export default {
        components: {aceEditor, BranchesOutlined, mavonEditor, InfoCircleOutlined},
        setup() {
            const store = useStore();
            let docEdit = ref({});
            let shareInstruction = ref('');
            let editShareInstructionVisible = ref(false);
            const handleNewDocOk = async () => {
                if (!shareInstruction.value) {
                    message.error('请输入开放文档的说明');
                    return;
                }
                zyplayerApi.apiDocUpdate({id: docEdit.value.id, shareInstruction: shareInstruction.value}).then(res => {
                    editShareInstructionVisible.value = false;
                });
            };
            const editDoc = async (id) => {
                editShareInstructionVisible.value = true;
                zyplayerApi.apiDocDetail({id: id}).then(res => {
                    docEdit.value = res.data;
                    shareInstruction.value = res.data.shareInstruction;
                });
            };
            onMounted(() => {
            });
            return {
                editShareInstructionVisible,
                docEdit,
                shareInstruction,
                handleNewDocOk,
                editDoc,
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
                    imagelink: true, // 图片链接
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
                    navigation: true, // 导航目录
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
