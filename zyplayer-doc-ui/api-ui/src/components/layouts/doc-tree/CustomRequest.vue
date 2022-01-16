<template>
    <a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
        <template #title="record">
            <template v-if="record.key === 'info'">
	            <file-text-outlined style="margin-right: 3px;"/>
            </template>
            <template v-if="record.isLeaf">
                <a-tag color="pink" v-if="record.method === 'get'">get</a-tag>
                <a-tag color="red" v-else-if="record.method === 'post'">post</a-tag>
                <a-tag color="orange" v-else-if="record.method === 'put'">put</a-tag>
                <a-tag color="green" v-else-if="record.method === 'head'">head</a-tag>
                <a-tag color="cyan" v-else-if="record.method === 'patch'">patch</a-tag>
                <a-tag color="blue" v-else-if="record.method === 'delete'">delete</a-tag>
                <a-tag color="purple" v-else-if="record.method === 'options'">options</a-tag>
                <a-tag color="purple" v-else-if="record.method === 'trace'">trace</a-tag>
            </template>
            <span style="margin: 0 6px 0 3px;">{{record.title}}</span>
	        <template v-if="record.children">
		        <a-badge :count="record.children.length" showZero :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
		        <a-dropdown :trigger="['click']">
			        <span @click.stop="" style="padding: 3px 10px;"><ellipsis-outlined /></span>
			        <template #overlay>
				        <a-menu @click="handleMenuClick($event, record)">
					        <a-menu-item key="newRequest">
						        <plus-outlined /> 新建接口
					        </a-menu-item>
					        <a-menu-item key="newFolder">
						        <folder-add-outlined /> 新建文件夹
					        </a-menu-item>
					        <a-menu-divider />
					        <a-menu-item key="edit">
						        <edit-outlined /> 编辑
					        </a-menu-item>
					        <a-menu-item key="delete">
						        <delete-outlined /> 删除
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
            let customRequestDoc = {};
            let treeData = ref([]);
            let expandedKeys = ref(['main']);
            let choiceDocId = '';
            let searchKeyword = '';

            const docChecked = (val, node) => {
                if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/custom/request', query: dataRef.query});
                }
            };
            const loadDoc = (docId, keyword, callback) => {
                choiceDocId = docId;
                zyplayerApi.apiDocApisDetail({id: docId}).then(res => {
                    let v2Doc = res.data;
                    if (!v2Doc && v2Doc.length != 1) {
                        callback(false);
                        message.error('获取文档数据失败');
                        return;
                    }
                    customRequestDoc = v2Doc;
                    store.commit('setCustomRequestDoc', v2Doc);
	                loadTreeData(keyword);
	                callback(true);
                }).catch(() => {
	                callback(false);
                });
            };
            const loadTreeData = async (keyword) => {
                let metaInfo = {id: choiceDocId};
	            searchKeyword = keyword;
                treeData.value = getTreeDataForTag(customRequestDoc, keyword, metaInfo);
                treeData.value.unshift({key: 'info', title: '文档说明信息', isLeaf: true});
            };
	        const handleMenuClick = (event, record) => {
		        if (event.key === 'newFolder') {
			        let params = {
				        // id: '',
				        docId: choiceDocId,
				        parentFolderId: record.folderId,
				        folderName: '新建文件夹',
				        folderDesc: '',
			        };
			        zyplayerApi.apiCustomFolderAdd(params).then(res => {
				        loadDoc(choiceDocId, searchKeyword);
			        });
				} else if (event.key === 'newRequest') {
			        let params = {
				        // id: '',
				        docId: choiceDocId,
				        folderId: record.folderId,
				        apiName: '新建接口',
				        method: 'get',
				        apiUrl: '',
				        // formData: '测试xxx',
				        // bodyData: '测试xxx',
				        // headerData: '测试xxx',
				        // cookieData: '测试xxx',
			        };
			        zyplayerApi.apiCustomRequestAdd(params).then(res => {
				        loadDoc(choiceDocId, searchKeyword);
				        let requestSaved = res.data;
				        let queryInfo = {
					        id: choiceDocId,
					        requestId: requestSaved.id,
				        };
				        router.push({path: '/custom/request', query: queryInfo});
			        });
				}
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
	            handleMenuClick,
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
