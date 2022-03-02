<template>
    <div class="menu-layout">
        <a-menu theme="light" mode="inline" :inline-collapsed="false" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-divider style="margin: 6px 0;"/>
        <div v-show="!collapsed" class="doc-tree">
            <a-spin tip="加载中..." :spinning="treeDataLoading">
                <div style="margin-bottom: 10px;">
                    <a-input-search v-model:value="searchKeywords" placeholder="搜索文档内容" style="width: 100%;margin-top: 10px;" @search="docSearch"/>
                </div>
                <template v-if="docChoice && docChoice.docType">
                    <DocTreeSwagger v-if="docChoice.docType === 1 || docChoice.docType === 2" ref="swaggerRef"></DocTreeSwagger>
                    <DocTreeOpenApi v-if="docChoice.docType === 3 || docChoice.docType === 4" ref="openApiRef"></DocTreeOpenApi>
                    <DocTreeCustomRequest v-if="docChoice.docType === 5" ref="customRequestRef"></DocTreeCustomRequest>
                </template>
            </a-spin>
        </div>
    </div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch, nextTick} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import MenuChildrenLayout from './MenuChildrenLayout.vue'
    import {zyplayerApi} from '../../api'
    import DocTreeSwagger from './doc-tree/Swagger.vue'
    import DocTreeOpenApi from './doc-tree/OpenApi.vue'
    import DocTreeCustomRequest from './doc-tree/CustomRequest.vue'

    export default {
        props: {
            collapsed: {
                type: Boolean,
                default: false
            },
        },
        components: {MenuChildrenLayout, DocTreeSwagger, DocTreeOpenApi, DocTreeCustomRequest},
        setup(props) {
            const store = useStore();
            const route = useRoute();
            const router = useRouter();

            let menuData = ref([]);
            let selectedKeys = ref([]);
            let openKeys = ref([]);
            // 文档;
            let treeDataLoading = ref(false);
            let docResourceList = ref([]);
            let docChoiceId = ref();
            let searchKeywords = ref('');
            let docChoice = ref({});

            const getApiDocList = () => {
                zyplayerApi.apiShareDocDetail({shareUuid: docChoiceId.value}).then(res => {
                    docChoice.value = res.data || {};
                    store.commit('setApiDoc', docChoice.value);
                    loadDoc();
                });
            };
            let swaggerRef = ref();
            let openApiRef = ref();
            const loadDoc = async () => {
                treeDataLoading.value = true;
                await nextTick();
                const loadDocCallback = () => {
                    treeDataLoading.value = false;
                };
                // 如果文档是swagger类型
                if (docChoice.value.docType === 1 || docChoice.value.docType === 2) {
                    if (swaggerRef.value) {
                        swaggerRef.value.loadDoc(docChoiceId.value, searchKeywords.value, loadDocCallback);
                    }
                } else if (docChoice.value.docType === 3 || docChoice.value.docType === 4) {
                    if (openApiRef.value) {
                        openApiRef.value.loadDoc(docChoiceId.value, searchKeywords.value, loadDocCallback);
                    }
                }
            };
            const docSearch = () => {
	            // 如果文档是swagger类型
	            if (docChoice.value.docType === 1 || docChoice.value.docType === 2) {
		            if (swaggerRef.value) {
			            swaggerRef.value.loadTreeData(searchKeywords.value);
		            }
	            } else if (docChoice.value.docType === 3 || docChoice.value.docType === 4) {
		            if (openApiRef.value) {
			            openApiRef.value.loadTreeData(searchKeywords.value);
		            }
	            }
            };
            onMounted(() => {
                docChoiceId.value = route.query.uuid;
                if (!docChoiceId.value) {
                    message.error('访问的开放文档参数错误');
                    return;
                }
                // 左侧菜单处理
                // menuData.value = router.options.routes.find((item) => item.path === '/share').children[0].children;
                menuData.value = [
                    {
                        path: '/share/home',
                        name: '开放文档使用说明',
                        meta: {
                            icon: 'FileTextOutlined'
                        },
                        query: {uuid: docChoiceId.value},
                    }
                ];
                let meta = route.meta || {};
                let path = route.path;
                if (!!meta.parentPath) {
                    path = meta.parentPath;
                }
                selectedKeys.value = [path];
                let matched = route.matched;
                if (matched.length >= 1) {
                    openKeys.value = [matched[1].path];
                }
                getApiDocList();
            });

            return {
                menuData,
                selectedKeys,
                openKeys,
                treeDataLoading,
                docResourceList,
                docChoiceId,
                searchKeywords,
                swaggerRef,
                openApiRef,
                docChoice,
                docSearch,
            };
        },
    };
</script>

<style>
    .doc-tree{padding: 10px 4px;}
    .doc-tree .ant-tree-switcher{width: 15px;}
    .doc-tree .ant-tree-switcher-noop{width: 0;}
    .doc-tree .ant-tag{margin-right: 0;}
    .ant-badge-not-a-wrapper:not(.ant-badge-status) {
        vertical-align: text-top;
    }
</style>
