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
	        <template v-if="children">
		        <a-badge :count="children.length" :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
		        <a-dropdown :trigger="['click']">
			        <span @click.stop="" style="padding: 3px 10px;"><ellipsis-outlined /></span>
			        <template #overlay>
				        <a-menu>
					        <a-menu-item>
						        <plus-outlined />
						        <a href="javascript:;"> 新建接口</a>
					        </a-menu-item>
					        <a-menu-item>
						        <folder-add-outlined />
						        <a href="javascript:;"> 新建文件夹</a>
					        </a-menu-item>
					        <a-menu-divider />
					        <a-menu-item>
						        <edit-outlined />
						        <a href="javascript:;"> 编辑</a>
					        </a-menu-item>
					        <a-menu-item>
						        <delete-outlined />
						        <a href="javascript:;"> 删除</a>
					        </a-menu-item>
				        </a-menu>
			        </template>
		        </a-dropdown>
	        </template>
        </template>
    </a-directory-tree>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch, nextTick} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {InfoCircleOutlined, FileTextOutlined, EllipsisOutlined, EditOutlined, DeleteOutlined, FolderAddOutlined, ApiOutlined, PlusOutlined} from '@ant-design/icons-vue';
    import {zyplayerApi} from '../../../api'
    import {getTreeDataForTag} from '../../../assets/core/CustomRequestTreeAnalysis.js'

    export default {
	    components: {InfoCircleOutlined, FileTextOutlined, EllipsisOutlined, EditOutlined, DeleteOutlined, FolderAddOutlined, ApiOutlined, PlusOutlined},
        setup() {
            const store = useStore();
            const route = useRoute();
            const router = useRouter();

            let tagPathMap = ref({});
            let customRequestDoc = ref({});
            let treeData = ref([]);
            let expandedKeys = ref([]);
            let choiceDocId = ref('');

            const docChecked = (val, node) => {
                if (node.node.key === 'info') {
                    router.push({path: '/custom/info'});
                } else if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/custom/view', query: dataRef.query});
                }
            };
            const loadDoc = (docId, keyword, callback) => {
                choiceDocId.value = docId;
                zyplayerApi.apiDocApisDetail({id: docId}).then(res => {
                    let v2Doc = res.data;
                    if (!v2Doc && v2Doc.length != 1) {
                        callback(false);
                        message.error('获取文档数据失败');
                        return;
                    }
                    customRequestDoc.value = v2Doc;
                    store.commit('setCustomRequestDoc', v2Doc);
	                loadTreeData(keyword);
	                callback(true);
                }).catch(() => {
	                callback(false);
                });
            };
            const loadTreeData = async (keyword) => {
                let metaInfo = {id: choiceDocId.value};
                treeData.value = getTreeDataForTag(customRequestDoc.value, keyword, metaInfo);
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
