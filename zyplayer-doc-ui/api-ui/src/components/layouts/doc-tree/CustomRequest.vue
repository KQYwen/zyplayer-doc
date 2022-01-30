<template>
	<div class="doc-tree-box">
		<a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys"
		                  v-model:selectedKeys="selectedKeys"
		                  draggable @drop="treeDataDragEnd"
		                  @select="docChecked">
			<template #title="record">
				<!--说明-->
				<div v-if="record.key === 'info'" class="api-title-line">
					<file-text-outlined style="margin-right: 3px;"/>
					<span class="tree-title-text">{{record.title}}</span>
				</div>
				<div v-if="record.key === 'main'" class="api-title-line">
					<span class="tree-title-text">{{record.title}}</span>
					<a-badge :count="record.children.length" showZero :number-style="{backgroundColor: '#fff', color: '#999', boxShadow: '0 0 0 1px #d9d9d9 inset'}"/>
					<a-dropdown :trigger="['click']">
						<span @click.stop="" style="padding: 3px 10px;"><ellipsis-outlined /></span>
						<template #overlay>
							<a-menu @click="handleFolderDropdownClick($event, record)">
								<a-menu-item key="newRequest"><plus-outlined /> 新建接口</a-menu-item>
								<a-menu-item key="newFolder"><folder-add-outlined /> 新建文件夹</a-menu-item>
								<a-menu-item key="refresh"><reload-outlined /> 刷新</a-menu-item>
							</a-menu>
						</template>
					</a-dropdown>
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
						<a-dropdown :trigger="['click']" class="api-title-dropdown">
							<span @click.stop="" style="padding: 3px 10px;"><ellipsis-outlined /></span>
							<template #overlay>
								<a-menu @click="handleApiTitleDropdownClick($event, record)">
									<a-menu-item key="delete"><delete-outlined /> 删除</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</span>
				</div>
				<!--文件夹-->
				<div v-else-if="record.nodeId" class="api-title-line">
					<a-popover v-model:visible="record.data.editing" placement="rightTop" title="编辑名称" trigger="click" @visibleChange="editFolderVisibleChange($event, record)">
						<template #content>
							<a-input v-model:value="record.data.titleEditing" v-autofocus></a-input>
							<div style="margin-top: 10px;text-align: right;">
								<a-button @click="editFolderSave(record)" type="primary">保存</a-button>
							</div>
						</template>
						<span class="tree-title-text">{{record.title}}</span>
					</a-popover>
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
	</div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, createVNode, watch, nextTick} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message, Modal } from 'ant-design-vue';
    import {
	    InfoCircleOutlined,
	    FileTextOutlined,
	    EllipsisOutlined,
	    EditOutlined,
	    DeleteOutlined,
	    FolderAddOutlined,
	    ApiOutlined,
	    PlusOutlined,
	    ExclamationCircleOutlined,
	    ReloadOutlined
    } from '@ant-design/icons-vue';
    import {zyplayerApi} from '../../../api'
    import {getTreeDataForTag} from '../../../assets/core/CustomRequestTreeAnalysis.js'

    export default {
	    components: {
		    InfoCircleOutlined,
		    FileTextOutlined,
		    EllipsisOutlined,
		    EditOutlined,
		    DeleteOutlined,
		    FolderAddOutlined,
		    ApiOutlined,
		    PlusOutlined,
		    ReloadOutlined,
	    },
	    setup() {
            const store = useStore();
            const route = useRoute();
            const router = useRouter();

            let customRequestDoc = {};
            let treeData = ref([]);
            let expandedKeys = ref(['main']);
            let selectedKeys = ref([]);
            let choiceDocId = '';
            let searchKeyword = '';

            const docChecked = (val, node) => {
                if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    router.push({path: '/custom/request', query: dataRef.query});
                }
            };
	        const loadDoc = (docId, keyword, callback = () => {}) => {
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
	        let requestInfoMap = {
				// 渲染到界面的数据
		        treeRequestMap: {},
		        // 原始map，用于搜索后的重新渲染
		        originNodeMap: {},
	        };
	        const getNodeIdMap = (tree) => {
		        tree.forEach(item => {
			        if (item.isLeaf && item.nodeId) {
				        requestInfoMap.treeRequestMap[item.nodeId] = item;
			        } else if (item.children) {
				        getNodeIdMap(item.children);
			        }
		        });
	        };
	        const loadTreeData = async (keyword) => {
		        let metaInfo = {id: choiceDocId};
		        searchKeyword = keyword;
		        treeData.value = getTreeDataForTag(customRequestDoc, keyword, metaInfo, requestInfoMap);
		        getNodeIdMap(treeData.value);
	        };
			// 监听自定义请求被修改了的事件
	        watch(store.getters.getCustomRequestChange, () => {
		        let requestChange = store.state.customRequestChange;
		        if (requestChange && requestChange.nodeId && requestChange.nodeName) {
					// 展示的tree
			        let requestItem = requestInfoMap.treeRequestMap[requestChange.nodeId];
			        if (requestItem) {
				        requestItem.method = requestChange.method;
				        requestItem.title = requestChange.nodeName;
			        }
					// 原始对象
			        let requestOriginItem = requestInfoMap.originNodeMap[requestChange.nodeId];
			        if (requestOriginItem) {
				        requestOriginItem.method = requestChange.method;
				        requestOriginItem.nodeName = requestChange.nodeName;
			        }
		        }
	        });
			// 监听Tab页面切换
	        watch(store.getters.getActivePage, () => {
		        let activePage = store.state.activePage;
		        changeSelectedRequestKeys(activePage.query.nodeId);
	        });
	        const editFolderVisibleChange = (visible, record) => {
				// 点击触发弹出事件不处理
		        if (visible) {
			        record.data.editing = false;
		        }
	        };
	        const editFolderSave = (record) => {
		        // 没做修改不处理
		        let title = record.data.titleEditing;
		        if (title === record.data.title) {
			        return;
		        }
		        zyplayerApi.apiCustomNodeUpdate({id: record.data.nodeId, nodeName: title}).then(res => {
			        record.data.title = title;
			        // 修改原始的文件夹名称
			        let folderItem = requestInfoMap.originNodeMap[record.data.nodeId];
			        if (folderItem) {
				        folderItem.name = title;
			        }
			        record.data.editing = false;
			        message.success('修改成功');
		        });
	        };
	        const handleApiTitleDropdownClick = (event, record) => {
		        if (event.key === 'delete') {
			        Modal.confirm({
				        title: '删除确认',
				        maskClosable: true,
				        icon: createVNode(ExclamationCircleOutlined),
				        content: '你确定要删除此接口吗？',
				        okText: '删除',
				        cancelText: '取消',
				        onOk() {
					        zyplayerApi.apiCustomNodeDelete({id: record.data.nodeId}).then(res => {
						        message.success('删除成功');
						        loadDoc(choiceDocId, searchKeyword);
					        });
				        },
			        });
		        }
	        };
	        const handleFolderDropdownClick = (event, record) => {
		        if (event.key === 'newFolder') {
			        let params = {
				        nodeType: 0,
				        docId: choiceDocId,
				        parentId: record.nodeId,
				        nodeName: '新建文件夹',
				        nodeDesc: '',
			        };
			        zyplayerApi.apiCustomNodeAdd(params).then(res => {
				        loadDoc(choiceDocId, searchKeyword);
			        });
				} else if (event.key === 'newRequest') {
			        let params = {
				        nodeType: 1,
				        docId: choiceDocId,
				        parentId: record.nodeId,
				        nodeName: '新建接口',
				        method: 'get',
				        apiUrl: '',
			        };
			        zyplayerApi.apiCustomNodeAdd(params).then(res => {
				        let requestSaved = res.data;
				        let queryInfo = {
					        id: choiceDocId,
					        nodeId: requestSaved.id,
				        };
				        router.push({path: '/custom/request', query: queryInfo});
				        loadDoc(choiceDocId, searchKeyword, () => {
					        changeSelectedRequestKeys(requestSaved.id);
				        });
			        });
		        } else if (event.key === 'refresh') {
			        loadDoc(choiceDocId, searchKeyword, res => {
						if(res) {
							message.success('重新加载成功');
						}
			        });
		        } else if (event.key === 'edit') {
			        record.data.editing = true;
		        } else if (event.key === 'delete') {
			        Modal.confirm({
				        title: '删除确认',
				        maskClosable: true,
				        icon: createVNode(ExclamationCircleOutlined),
				        content: '你确定要删除此文件夹及目录下所有二级目录和接口吗？',
				        okText: '删除',
				        cancelText: '取消',
				        onOk() {
					        zyplayerApi.apiCustomNodeDelete({id: record.data.nodeId}).then(res => {
						        message.success('删除成功');
						        loadDoc(choiceDocId, searchKeyword);
					        });
				        },
			        });
				}
	        };
            const changeSelectedRequestKeys = (nodeId) => {
	            let treeData = requestInfoMap.treeRequestMap[nodeId];
	            if (treeData) {
		            selectedKeys.value = [treeData.key];
	            }
	        };
            const treeDataDragEnd = (event) => {
	            let param = {
		            id: event.dragNode.nodeId,
		            parentId: event.node.nodeId,
		            targetType: 0,
	            };
	            if (event.dropToGap) {
		            // 放入event.node的后面
		            console.log(`放入${event.node.key}的后面`);
		            param.targetType = 1;
	            } else {
		            // 放入event.node文件夹的第一个位置
		            console.log(`放入${event.node.key}文件夹内`);
	            }
	            zyplayerApi.apiCustomNodeChangeParent(param).then(res => {
		            message.success('修改排序成功');
		            loadDoc(choiceDocId, searchKeyword);
	            });
				console.log(event);
	        };
            return {
                expandedKeys,
	            selectedKeys,
	            editFolderVisibleChange,
	            editFolderSave,
                docChecked,
	            treeDataDragEnd,
                loadDoc,
	            loadTreeData,
                treeData,
	            handleFolderDropdownClick,
	            handleApiTitleDropdownClick,
            };
        },
    };
</script>

<style>
    /*.doc-tree{padding: 10px 4px;}*/
    /*.doc-tree .ant-tree-switcher{width: 15px;}*/
    /*.doc-tree .ant-tree-switcher-noop{width: 0;}*/
    /*.doc-tree .ant-tag{margin-right: 0;}*/
    /*.ant-badge-not-a-wrapper:not(.ant-badge-status) {*/
    /*    vertical-align: text-top;*/
    /*}*/
    .api-title-line .tree-title-text{
	    margin: 0 6px 0 3px;
    }
    .api-title-line .api-title-dropdown{
	    display: none;
    }
    .api-title-line:hover .api-title-dropdown{
	    display: unset;
    }
</style>
