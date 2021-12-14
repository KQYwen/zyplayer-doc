<template>
    <a-page-header title="编辑" :sub-title="docEdit.name||''" @back="showDocList">
        <template #extra></template>
    </a-page-header>
    <a-tabs v-model:activeKey="activeEditTab" style="padding: 5px 10px 0;">
        <a-tab-pane tab="基本信息" key="base" forceRender>
            <a-spin tip="数据加载中..." :spinning="docEditLoading">
                <EditDocBaseInfo ref="docBaseInfoRef" :doc="docEdit"></EditDocBaseInfo>
            </a-spin>
        </a-tab-pane>
        <a-tab-pane v-if="docEdit.id > 0 && docEdit.authType === 1" tab="成员管理" key="members">
            <DocManageMembers :doc="docEdit"></DocManageMembers>
        </a-tab-pane>
	    <a-tab-pane v-if="docEdit.id > 0" tab="文档全局参数" key="globalParam">
		    <EditGlobalParam :dynamic-param="{docId: docEdit.id}"></EditGlobalParam>
	    </a-tab-pane>
        <a-tab-pane v-if="docEdit.openVisit === 1" tab="开放文档说明" key="instruction">
            <EditShareInstruction ref="shareInstructionRef" :doc="docEdit"></EditShareInstruction>
        </a-tab-pane>
        <template #rightExtra>
            <a-button v-if="activeEditTab==='base' && docEdit.authType === 1" @click="saveBaseInfo" :loading="docEditLoading" type="primary">
                <template #icon><save-outlined /></template> 保存基本信息
            </a-button>
            <a-button v-if="activeEditTab==='instruction'" @click="saveShareInstruction" :loading="docEditLoading" type="primary">
                <template #icon><save-outlined /></template> 保存开放文档说明
            </a-button>
        </template>
    </a-tabs>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';
    import {getZyplayerApiBaseUrl} from "../../api/request/utils";
    import {SaveOutlined} from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue';
    import EditDocBaseInfo from "./components/EditDocBaseInfo.vue";
    import EditShareInstruction from "./components/EditShareInstruction.vue";
    import EditGlobalParam from "./components/EditGlobalParam.vue";
    import DocManageMembers from "./DocManageMembers.vue";

    export default {
        emits: ['showDocList'],
        components: {SaveOutlined, EditShareInstruction, EditDocBaseInfo, DocManageMembers, EditGlobalParam},
        props: {
            doc: {
                type: Object,
                required: true
            },
        },
        setup(props, {emit}) {
            const store = useStore();
            const showDocList = () => {
                emit('showDocList');
            }
            let docBaseInfoRef = ref();
            let shareInstructionRef = ref();
            let docEdit = ref({});
            let docEditLoading = ref(false);
            const saveBaseInfo = async () => {
                let docNew = await docBaseInfoRef.value.getDoc();
                docEditLoading.value = true;
                zyplayerApi.apiDocAdd(docNew).then(res => {
                    message.success('保存成功！');
                    docEditLoading.value = false;
                    store.commit('addDocChangedNum');
                    // 需要返回列表
                    if (res.data.id !== docNew.id) {
                        showDocList();
                    }
                }).catch(() => {
                    docEditLoading.value = false;
                });
            };
            const saveShareInstruction = () => {
                let docNew = shareInstructionRef.value.getDoc();
                if (!docNew) {
                    return;
                }
                docEditLoading.value = true;
                zyplayerApi.apiDocUpdate(docNew).then(res => {
                    message.success('保存成功！');
                    docEditLoading.value = false;
                }).catch(() => {
                    docEditLoading.value = false;
                });
            };
            let activeEditTab = ref('base');
            onMounted(() => {
                docEdit.value = props.doc;
                docEditLoading.value = true;
                zyplayerApi.apiDocDetail({id: props.doc.id}).then(res => {
                    docEditLoading.value = false;
                    docEdit.value = res.data;
                }).catch(() => {
                    docEditLoading.value = false;
                });
            });
            return {
                activeEditTab,
                showDocList,
                saveBaseInfo,
                saveShareInstruction,
                docBaseInfoRef,
                shareInstructionRef,
                docEdit,
                docEditLoading,
            };
        },
    };
</script>
