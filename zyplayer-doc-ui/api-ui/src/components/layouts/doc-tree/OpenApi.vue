<template>
    <a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
        <template #title="{ title, isLeaf, method, children, key }">
            <template v-if="isLeaf">
                <a-tag color="pink" v-if="method === 'get'">get</a-tag>
                <a-tag color="red" v-else-if="method === 'post'">post</a-tag>
                <a-tag color="orange" v-else-if="method === 'put'">put</a-tag>
                <a-tag color="green" v-else-if="method === 'head'">head</a-tag>
                <a-tag color="cyan" v-else-if="method === 'patch'">patch</a-tag>
                <a-tag color="blue" v-else-if="method === 'delete'">delete</a-tag>
                <a-tag color="purple" v-else-if="method === 'options'">options</a-tag>
                <a-tag color="purple" v-else-if="method === 'trace'">trace</a-tag>
            </template>
            <span style="margin: 0 6px 0 3px;">{{title}}</span>
            <a-badge v-if="children" :count="children.length" :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
        </template>
    </a-directory-tree>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {zyplayerApi} from '../../../api'
    import {analysisOpenApiData, getTreeDataForTag} from '../../../assets/core/OpenApiTreeAnalysis.js'

    export default {
        setup() {
            const store = useStore();
            const route = useRoute();
            const router = useRouter();

            let tagPathMap = ref({});
            let openApiDoc = ref({});
            let treeData = ref([]);
            let expandedKeys = ref([]);
            let choiceDocId = ref('');
            let searchKeywords = ref('');

            const docChecked = (val, node) => {
                if (node.node.key === 'main') {
                    router.push({path: '/openapi/info'});
                } else if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/openapi/view', query: dataRef.query});
                }
            };
            const loadDoc = (docId, keyword, callback) => {
                choiceDocId.value = docId;
                searchKeywords.value = keyword;
                zyplayerApi.apiDocApisDetail({id: docId}).then(res => {
                    let v2Doc = toJsonObj(res.data);
                    // os：doc.swagger 和 doc.openapi 的区别
                    if (typeof v2Doc !== 'object' || !v2Doc.openapi) {
                        message.error('获取文档数据请求失败');
                        return;
                    }
                    openApiDoc.value = v2Doc;
                    store.commit('setOpenApiDoc', v2Doc);
                    let treeData = analysisOpenApiData(v2Doc);
                    store.commit('setOpenApiUrlMethodMap', treeData.urlMethodMap);
                    store.commit('setOpenApiMethodStatistic', treeData.methodStatistic);
                    tagPathMap.value = treeData.tagPathMap;
                    loadTreeData();
                    callback();
                    setTimeout(() => {
                        let isViewPage = (route.path === '/openapi/view' && route.query.id);
                        if (!isViewPage) {
                            router.push({path: '/openapi/info'});
                        }
                    }, 0);
                });
            };
            const loadTreeData = () => {
                expandedKeys.value = ['main'];
                let metaInfo = {id: choiceDocId.value};
                treeData.value = getTreeDataForTag(openApiDoc.value, tagPathMap.value, searchKeywords.value, metaInfo);
            };
            const toJsonObj = (value) => {
                if (typeof value !== 'string') {
                    return value;
                }
                try {
                    return JSON.parse(value);
                } catch (e) {
                    try {
                        // 处理变态的单双引号共存字符串
                        return eval('(' + value + ')');
                    } catch (e) {
                        return value || undefined;
                    }
                }
            };
            return {
                expandedKeys,
                docChecked,
                loadDoc,
                treeData,
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
