<template>
    <div class="menu-layout">
        <a-menu theme="light" mode="inline" :inline-collapsed="false" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-divider style="margin: 6px 0;"/>
        <div v-show="!collapsed" class="doc-tree">
            <a-spin tip="加载中..." :spinning="treeDataLoading">
                <div style="padding: 10px 5px;">
                    <a-select placeholder="请选择分组" v-model:value="docChoiceId" @change="docChoiceChange" style="width: 100%;">
                        <a-select-option :value="item.id" v-for="item in docResourceList">{{item.name}}</a-select-option>
                    </a-select>
                    <a-input-search v-model:value="searchKeywords" placeholder="搜索文档内容" style="width: 100%;margin-top: 10px;" @search="searchDoc"/>
                </div>
                <template v-if="docChoice && docChoice.docType">
                    <DocTreeSwagger v-if="docChoice.docType === 1 || docChoice.docType === 2" ref="swaggerRef"></DocTreeSwagger>
                    <DocTreeOpenApi v-if="docChoice.docType === 3 || docChoice.docType === 4" ref="openApiRef"></DocTreeOpenApi>
                    <CustomRequest v-if="docChoice.docType === 5" ref="customRequestRef"></CustomRequest>
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
    import CustomRequest from './doc-tree/CustomRequest.vue'

    export default {
        props: {
            collapsed: {
                type: Boolean,
                default: false
            },
        },
        components: {MenuChildrenLayout, DocTreeSwagger, DocTreeOpenApi, CustomRequest},
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

            const getGlobalParamList = () => {
                zyplayerApi.docApiGlobalParamList().then(res => {
                    let globalParam = res.data || [];
                    store.commit('setGlobalParam', globalParam);
                });
            };
            const getApiDocList = () => {
                zyplayerApi.apiDocApis().then(res => {
                    docResourceList.value = res.data || [];
                    if (docResourceList.value.length > 0 && !docChoiceId.value) {
                        // 加载初始化的地址
                        if (route.path === '/swagger/view' && route.query.id) {
                            docChoiceId.value = parseInt(route.query.id);
                        } else if (route.path === '/openapi/view' && route.query.id){
                            docChoiceId.value = parseInt(route.query.id);
                        } else if (route.path === '/custom/request' && route.query.id){
                            docChoiceId.value = parseInt(route.query.id);
                        } else {
                            docChoiceId.value = docResourceList.value[0].id;
                        }
                        loadDoc();
                    }
                });
            };
            let swaggerRef = ref();
            let openApiRef = ref();
            let customRequestRef = ref();
            const loadDoc = async () => {
                treeDataLoading.value = true;
                docChoice.value = docResourceList.value.find(item => item.id === docChoiceId.value);
                if (!docChoice.value) {
                    message.error('未找到对应的文档地址信息');
                    return;
                }
                await nextTick();
                const loadDocCallback = (success) => {
                    if (success) {
                        store.commit('setApiDoc', docChoice.value);
                    }
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
                } else if (docChoice.value.docType === 5) {
                    if (customRequestRef.value) {
	                    customRequestRef.value.loadDoc(docChoiceId.value, searchKeywords.value, loadDocCallback);
                    }
                }
	            zyplayerApi.docApiGlobalParamList({docId: docChoiceId.value}).then(res => {
		            let docGlobalParam = res.data || [];
		            store.commit('setDocGlobalParam', docGlobalParam);
	            });
            };
            const docChoiceChange = () => {
                loadDoc();
            };
	        // 搜索文档
            const searchDoc = () => {
	            // 如果文档是swagger类型
	            if (docChoice.value.docType === 1 || docChoice.value.docType === 2) {
		            if (swaggerRef.value) {
			            swaggerRef.value.loadTreeData(searchKeywords.value);
		            }
	            } else if (docChoice.value.docType === 3 || docChoice.value.docType === 4) {
		            if (openApiRef.value) {
			            openApiRef.value.loadTreeData(searchKeywords.value);
		            }
	            } else if (docChoice.value.docType === 5) {
		            if (customRequestRef.value) {
			            customRequestRef.value.loadTreeData(searchKeywords.value);
		            }
	            }
            };
            watch(store.getters.getDocChangedNum, () => {
                getApiDocList();
            });
            onMounted(() => {
                menuData.value = router.options.routes.find((item) => item.path === '/').children[0].children;
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
                getGlobalParamList();
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
	            customRequestRef,
                docChoice,
	            searchDoc,
                docChoiceChange,
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
