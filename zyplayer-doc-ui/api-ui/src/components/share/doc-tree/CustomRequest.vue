<template>
    <a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
	    <template #title="record">
	        <!--说明-->
	        <div v-if="record.key === 'info'" class="api-title-line">
		        <file-text-outlined style="margin-right: 3px;"/>
		        <span class="tree-title-text">{{record.title}}</span>
	        </div>
	        <div v-if="record.key === 'main'" class="api-title-line">
		        <span class="tree-title-text">{{record.title}}</span>
		        <a-badge :count="record.children.length" showZero :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
	        </div>
	        <!--接口-->
	        <div v-if="record.isLeaf" class="api-title-line">
		        <a-tag color="pink" v-if="record.method === 'get'">get</a-tag>
		        <a-tag color="red" v-else-if="record.method === 'post'">post</a-tag>
		        <a-tag color="orange" v-else-if="record.method === 'put'">put</a-tag>
		        <a-tag color="green" v-else-if="record.method === 'head'">head</a-tag>
		        <a-tag color="cyan" v-else-if="record.method === 'patch'">patch</a-tag>
		        <a-tag color="blue" v-else-if="record.method === 'delete'">delete</a-tag>
		        <a-tag color="purple" v-else-if="record.method === 'options'">options</a-tag>
		        <a-tag color="purple" v-else-if="record.method === 'trace'">trace</a-tag>
		        <span class="tree-title-text">
					{{record.title}}
				</span>
	        </div>
	        <!--文件夹-->
	        <div v-else-if="record.nodeId" class="api-title-line">
		        <span class="tree-title-text">{{record.title}}</span>
		        <a-badge :count="record.children.length" showZero :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
		        <a-dropdown :trigger="['click']">
			        <span @click.stop="" style="padding: 3px 10px;"><ellipsis-outlined /></span>
			        <template #overlay>
				        <a-menu @click="handleFolderDropdownClick($event, record)">
					        <a-menu-item key="newRequest"><plus-outlined /> 新建接口</a-menu-item>
					        <a-menu-item key="newFolder"><folder-add-outlined /> 新建文件夹</a-menu-item>
					        <a-menu-divider />
					        <a-menu-item key="edit"><edit-outlined /> 编辑</a-menu-item>
					        <a-menu-item key="delete"><delete-outlined /> 删除</a-menu-item>
				        </a-menu>
			        </template>
		        </a-dropdown>
	        </div>
        </template>
    </a-directory-tree>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch, nextTick} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {zyplayerApi} from '../../../api'
    import {getTreeDataForTag} from '../../../assets/core/CustomRequestTreeAnalysis.js'

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

            const docChecked = (val, node) => {
                if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/share/openapi/view', query: dataRef.query});
                }
            };
            const loadDoc = (docId, keyword, callback) => {
                choiceDocId.value = docId;
                zyplayerApi.apiShareDocApisDetail({shareUuid: docId}).then(res => {
	                let v2Doc = res.data;
	                if (!v2Doc && v2Doc.length !== 1) {
		                callback(false);
		                message.error('获取文档数据失败，请检查文档是否为标准的OpenApi文档格式');
		                return;
	                }
                    openApiDoc.value = v2Doc;
	                store.commit('setCustomRequestDoc', v2Doc);
                    loadTreeData(keyword);
                    callback(true);
                }).catch(() => {
	                callback(false);
                });
            };
            const loadTreeData = async (keyword) => {
                let metaInfo = {uuid: choiceDocId.value};
                treeData.value = getTreeDataForTag(openApiDoc.value, tagPathMap.value, keyword, metaInfo);
                await nextTick();
                expandedKeys.value = ['main'];
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
