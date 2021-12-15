<template>
    <a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
        <template #title="{ title, isLeaf, method, children, key }">
            <template v-if="key === 'info'">
	            <file-text-outlined style="margin-right: 3px;"/>
            </template>
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
    import {toRefs, ref, reactive, onMounted, watch, nextTick} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {InfoCircleOutlined, FileTextOutlined} from '@ant-design/icons-vue';
    import {zyplayerApi} from '../../../api'
    import {analysisSwaggerData, getTreeDataForTag} from '../../../assets/core/SwaggerTreeAnalysis.js'

    export default {
	    components: {InfoCircleOutlined, FileTextOutlined},
        setup() {
            const store = useStore();
            const route = useRoute();
            const router = useRouter();

            let tagPathMap = ref({});
            let swaggerDoc = ref({});
            let treeData = ref([]);
            let expandedKeys = ref([]);
            let choiceDocId = ref('');

            const docChecked = (val, node) => {
                if (node.node.key === 'info') {
                    router.push({path: '/swagger/info'});
                } else if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/swagger/view', query: dataRef.query});
                }
            };
            const loadDoc = (docId, keyword, callback) => {
                choiceDocId.value = docId;
                zyplayerApi.apiDocApisDetail({id: docId}).then(res => {
                    let v2Doc = toJsonObj(res.data);
                    if (typeof v2Doc !== 'object' || !v2Doc.swagger) {
                        callback(false);
                        message.error('获取文档数据失败，请检查文档是否为标准的Swagger文档格式');
                        return;
                    }
                    swaggerDoc.value = v2Doc;
                    store.commit('setSwaggerDoc', v2Doc);
                    let treeData = analysisSwaggerData(v2Doc);
                    store.commit('setSwaggerUrlMethodMap', treeData.urlMethodMap);
                    store.commit('setSwaggerMethodStatistic', treeData.methodStatistic);
                    tagPathMap.value = treeData.tagPathMap;
	                loadTreeData(keyword);
	                callback(true);
                }).catch(() => {
	                callback(false);
                });
            };
            const loadTreeData = async (keyword) => {
                let metaInfo = {id: choiceDocId.value};
                treeData.value = getTreeDataForTag(swaggerDoc.value, tagPathMap.value, keyword, metaInfo);
                treeData.value.unshift({key: 'info', title: '文档说明信息', isLeaf: true});
                await nextTick();
                expandedKeys.value = ['main'];
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
	            loadTreeData,
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
