<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="leftCollapse" :style="{ width: rightAsideWidth + 'px' }">
				<div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
					<div style="margin-bottom: 5px">
						<el-select :model-value="choiceSpace" filterable placeholder="选择空间" style="width: 100%" @change="spaceChangeEvents">
							<el-option-group label="">
								<el-option :key="0" label="创建空间" :value="0"></el-option>
								<el-option :key="-1" label="空间管理" :value="-1"></el-option>
							</el-option-group>
							<el-option-group label=""></el-option-group>
							<el-option v-for="item in spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</div>
					<el-autocomplete v-model="searchKeywords" :fetch-suggestions="doSearchByKeywords" placeholder="在当前空间搜索" popper-class="search-autocomplete" style="width: 100%; margin: 10px 0" @select="handleSearchKeywordsSelect">
						<template v-slot="{ item }">
							<div class="search-option-item">
								<div class="title">
									<span v-html="item.pageTitle || '-'"></span>
								</div>
								<span class="content" v-html="item.previewContent || '-'"></span>
							</div>
						</template>
					</el-autocomplete>
					<div style="margin-left: 10px;margin-bottom: 10px">
						<el-tooltip style="margin: 4px" effect="dark" :content="descriptorForTree" placement="top">
							<span  style="color:#888;font-size: 12px;cursor: pointer" @click="changeDropWownStatus">空间目录</span>
						</el-tooltip>
						<a-dropdown :trigger="['click']"  @click="choosePageIdFunc(0)">
							<a class="ant-dropdown-link" @click.stop style="float:right">
								<el-icon   style="margin-right: 5px">
									<el-icon-plus />
								</el-icon>
							</a>
							<template #overlay>
								<a-menu>
									<a-menu-item key="0" @click="createWiki(0,0)">
										<el-icon  class="clickAddIcon" style="margin-right: 5px">
											<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
										</el-icon>
										创建文件夹
									</a-menu-item>
									<a-menu-item key="1" @click="createWiki(1,0)">
										<el-icon  class="clickAddIcon" style="margin-right: 5px">
											<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor" stroke-width="4"></rect><path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
										</el-icon>
										创建富文本
									</a-menu-item>
									<a-menu-item key="2" @click="createWiki(2,0)">
										<el-icon  class="clickAddIcon" style="margin-right: 5px"><el-icon-document/></el-icon>创建Markdown
									</a-menu-item>
									<a-menu-item key="3" >
										<el-tooltip content="支持MD，ZIP格式（图片和MD文件请放到同级目录并配置同级相对路径）" placement="right-start" :show-after="300">
											<a-upload
													v-model:file-list="fileList"
													name="file"
													:multiple="false"
													:customRequest="doAUpload">
												<el-icon  class="clickAddIcon" style="margin-right: 5px" type="primary"><ElIconUpload/></el-icon>导入
											</a-upload>
										</el-tooltip>
									</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</div>
					<div class="wiki-page-tree-box">
						<el-tree
								:class="explanClass"
								ref="wikiPageTreeRef"
								:current-node-key="nowPageId"
								:data="wikiPageList"
								:default-expanded-keys="wikiPageExpandedKeys"
								:expand-on-click-node="true"
								:filter-node-method="filterPageNode"
								:props="defaultProps"
								draggable
								highlight-current
								node-key="id"
								style="background-color: #fafafa"
								@node-click="handleNodeClick"
								@node-expand="handleNodeExpand"
								@node-drop="handlePageDrop">
							<template v-slot="{ node, data }">
								<div class="page-tree-node" @mouseover="changeNodeOptionStatus(data,1,false)" @mouseleave="changeNodeOptionStatus(data,-1,false) ">
									<el-tooltip :content="node.label" placement="top-start" :show-after="1000">
										<span >
											<a-row type="flex">
												<a-col :flex="1" style="margin-top: 6px">
													<el-icon  v-if="data.editorType === 0" class="clickAddIcon" style="margin-right: 5px">
													<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
												</el-icon>
													<el-icon  v-if="data.editorType === 1" class="clickAddIcon" style="margin-right: 5px">
														<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor" stroke-width="4"></rect><path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
													</el-icon>
													<el-icon v-if="data.editorType === 2" class="clickAddIcon" style="margin-right: 5px"><el-icon-document/></el-icon>
												</a-col>
												<a-col :flex="15" style="margin-top: 6px" >
													<a-input v-if="data.renaming" v-model:value="data.name" placeholder="Basic usage" v-on:blur="doRename(node,data)" @click.stop/>
													<span  v-else>
														{{ node.label }}
													</span>
												</a-col>
												<a-col :flex="3" style="margin-top: 6px" v-if="changeNodeOptionStatus(data,0,true)">
													<span class="el-dropdown-link"  style="float:right">
														<a-dropdown :trigger="['click']" style="float:right" @click="choosePageIdFunc(data.id)">
															<a class="ant-dropdown-link" @click.stop>
																<el-icon   style="margin-right: 5px">
																	<el-icon-plus />
																</el-icon>
															</a>
															<template #overlay>
																<a-menu>
																	<a-menu-item key="0" @click="createWiki(0,data.id)">
																		<el-icon  class="clickAddIcon" style="margin-right: 5px">
																			<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
																		</el-icon>
																		创建文件夹
																	</a-menu-item>
																	<a-menu-item key="1" @click="createWiki(1,data.id)">
																		<el-icon  class="clickAddIcon" style="margin-right: 5px">
																			<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor" stroke-width="4"></rect><path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
																		</el-icon>
																		创建富文本
																	</a-menu-item>
																	<a-menu-item key="2" @click="createWiki(2,data.id)">
																		<el-icon  class="clickAddIcon" style="margin-right: 5px">
																			<el-icon-document/>
																		</el-icon>
																		创建Markdown
																	</a-menu-item>
																	<a-menu-item key="3" >
																		<el-tooltip content="支持MD，ZIP格式（图片和MD文件请放到同级目录并配置同级相对路径）" placement="right-start" :show-after="300">
																			 <a-upload
																					 v-model:file-list="fileList"
																					 name="file"
																					 :multiple="false"
																					 :customRequest="doAUpload">
																					<el-icon  class="clickAddIcon" style="margin-right: 5px" type="primary"><ElIconUpload/></el-icon>导入
																			  </a-upload>
																		</el-tooltip>
																	</a-menu-item>
																</a-menu>
															</template>
														</a-dropdown>
														<a-dropdown :trigger="['click']" style="float:right">
															<a class="ant-dropdown-link" @click.stop>
																<el-icon   style="margin-right: 5px">
																	<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M176 416a112 112 0 1 1 0 224 112 112 0 0 1 0-224zm336 0a112 112 0 1 1 0 224 112 112 0 0 1 0-224zm336 0a112 112 0 1 1 0 224 112 112 0 0 1 0-224z"></path></svg>
																</el-icon>
															</a>
															<template #overlay>
																<a-menu>
																	<a-menu-item key="0" @click="rename(node,data)">
																		<el-icon  class="clickAddIcon" style="margin-right: 5px">
																			<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M42 26V40C42 41.1046 41.1046 42 40 42H8C6.89543 42 6 41.1046 6 40V8C6 6.89543 6.89543 6 8 6L22 6" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M14 26.7199V34H21.3172L42 13.3081L34.6951 6L14 26.7199Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path></svg>
																		</el-icon>
																		重命名
																	</a-menu-item>
																</a-menu>
															</template>
														</a-dropdown>
													</span>
												</a-col>
											</a-row>
										</span>
									</el-tooltip>
								</div>
							</template>
						</el-tree>
					</div>
				</div>
			</el-aside>
			<RightResize v-show="leftCollapse" v-model:value="rightAsideWidth" @change="rightAsideWidthChange"></RightResize>
			<el-container>
				<el-header>
					<el-row>
						<el-col :span="12">
							<el-button @click="turnLeftCollapse" v-if="leftCollapse" text :icon="ElIconFold" class="fold-btn"></el-button>
							<el-button @click="turnLeftCollapse" v-else text :icon="ElIconExpand" class="fold-btn"></el-button>
						</el-col>
						<el-col :span="12" style="text-align: right;">
							<span class="header-right-user-name">{{userSelfInfo.userName}}</span>
							<el-popover v-model:visible="userMessagePopVisible" placement="bottom" trigger="click" width="600">
								<template v-slot:reference>
									<el-badge :is-dot="haveNotReadUserMessage" style="margin: 0 20px">
										<el-icon class="head-icon" style="margin-right: 0">
											<el-icon-bell/>
										</el-icon>
									</el-badge>
								</template>
								<div style="margin-bottom: 10px">
									<span style="font-size: 14px; font-weight: bold">通知</span>
									<el-link v-if="haveNotReadUserMessage" :icon="ElIconCheck" style="float: right" type="primary" @click="readAllUserMessage">本页标记已读</el-link>
								</div>
								<div class="header-user-message">
									<el-table :data="userMessageList" border max-height="500" size="small" style="width: 100%; margin-bottom: 5px">
										<el-table-column label="操作人" prop="operatorUserName" width="100px"></el-table-column>
										<el-table-column label="操作时间" prop="creationTime" width="140px"></el-table-column>
										<el-table-column label="内容">
											<template v-slot="scope">
												{{ scope.row.msgContent }}
												<el-badge :is-dot="scope.row.msgStatus == 0" style="line-height: 10px; padding-right: 5px">
													<el-link type="primary" @click="showUserMessage(scope.row)">查看</el-link>
												</el-badge>
											</template>
										</el-table-column>
									</el-table>
									<div class="page-info-box">
										<el-pagination
												:current-page="userMsgParam.pageNum"
												:page-size="userMsgParam.pageSize"
												:total="userMsgTotalCount"
												layout="prev, pager, next, total"
												@current-change="handleCurrentChange">
										</el-pagination>
									</div>
								</div>
							</el-popover>
							<el-dropdown trigger="click" @command="userSettingDropdown" style="vertical-align: middle;">
								<el-icon class="head-icon">
									<el-icon-setting/>
								</el-icon>
								<template v-slot:dropdown>
									<el-dropdown-menu>
										<el-dropdown-item command="console">控制台</el-dropdown-item>
										<el-dropdown-item command="aboutDoc">关于</el-dropdown-item>
										<el-dropdown-item command="userSignOut" divided>退出登录</el-dropdown-item>
									</el-dropdown-menu>
								</template>
							</el-dropdown>
						</el-col>
					</el-row>
				</el-header>
				<el-main style="padding: 0; border-left: 1px solid #dcdfe6">
					<router-view
							v-slot="{ Component }"
							:spaceId="choiceSpace"
							:spaceInfo="getSpaceInfo(choiceSpace)"
							@changeExpandedKeys="changeWikiPageExpandedKeys"
							@loadPageList="loadPageList"
							@loadSpace="loadSpaceList"
							@switchSpace="switchSpacePage">
						<component :is="Component"></component>
					</router-view>
				</el-main>
			</el-container>
		</el-container>
		<create-space ref="createSpaceRef" @success="loadSpaceList"></create-space>
		<about-dialog ref="aboutDialogRef"></about-dialog>
	</div>
</template>

<script setup>
	import {
		Document as ElIconDocument,
		Fold as ElIconFold,
		Expand as ElIconExpand,
		Upload as ElIconUpload,
		Bell as ElIconBell,
		Setting as ElIconSetting,
		Plus as ElIconPlus,
		Check as ElIconCheck,
	} from '@element-plus/icons-vue'
	import {onBeforeUnmount, toRefs, ref, reactive, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
	import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
	import {ElMessageBox, ElMessage} from 'element-plus'
	import userApi from '../../assets/api/user'
	import pageApi from '../../assets/api/page'
	import CreateSpace from '../space/CreateSpace'
	import RightResize from './RightResize.vue'
	import AboutDialog from '../../views/common/AboutDialog'
	import {useStoreDisplay} from '@/store/wikiDisplay.js'
	import {useStoreUserData} from "@/store/userData";
	import {useStorePageData} from "@/store/pageData";
	import { defineComponent } from 'vue';
	import { DownOutlined } from '@ant-design/icons-vue';
	import axios from "axios";

	let route = useRoute();
	let router = useRouter();
	let storePage = useStorePageData();
	let storeDisplay = useStoreDisplay();

	let leftCollapse = ref(true);
	let rightContentLoading = ref(false);
	let pathIndex = ref([]);
	let defaultProps = ref({children: 'children', label: 'name',});
	// 空间搜索相关
	let spaceOptions = ref([]);
	let spaceList = ref([]);
	let choiceSpace = ref('');
	let nowSpaceShow = ref({});
	let nowPageId = ref('');
	// 依据目录树存储的map全局对象
	let treePathDataMap = ref(new Map());
	// 搜索的输入内容
	let searchKeywords = ref('');
	// 页面展示相关
	let wikiPageList = ref([]);
	let wikiPage = ref({});
	let wikiPageExpandedKeys = ref([]);
	let userSelfInfo = ref({});
	let userMessageList = ref([]);
	let haveNotReadUserMessage = ref(false);
	let userMessagePopVisible = ref(false);
	let userMsgTotalCount = ref(0);
	let userMsgParam = ref({sysType: 2, pageNum: 1, pageSize: 20,});
	let rightAsideWidth = ref(300);
	let optionStatus = ref(false);
	let optionPageId = ref('');
	let optionLock = ref(false);
	let descriptorForTree = ref("点击收起目录");
	let explan = ref(false);
	let explanClass = ref("el-tree");
	let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
	let fileList = ref([]);
	let choosePageId = ref([]);

	onMounted(() => {
		loadSpaceList()
		loadUserMessageList()
		getSelfUserInfo()
	});
	const doAUpload = (data) => {
		let formData = new FormData()
		formData.append('files', data.file)
		formData.append('pageId',choosePageId.value)
		if (choosePageId.value === 0){
			formData.append('id',choiceSpace.value)
		}
		formData.append('importFlag',true)
		axios({
			url: uploadFileUrl.value,
			method: 'post',
			data: formData,
			headers: {'Content-Type': 'multipart/form-data'},
			timeout: 10000,
			withCredentials: true,
		}).then((res) => {
			fileList.value = []
			if (res.data.errCode === 200) {
				ElMessage.success('导入成功')
			}
			if (res.data.errCode === 300){
				ElMessage.warning(res.data.errMsg)
				ElMessage.warning('文件太多可能超时，如果是超时，请稍等后刷新查看列表~')
			}
			doGetPageList(null)

		}).catch((e) => {
			fileList.value = []
			doGetPageList(null)
			ElMessage.error('导入失败：' + e.message)
		})
	}

	const choosePageIdFunc = (id) => {
		optionLock.value= true
		choosePageId.value = id
	}
	const rename = (node,data) => {
		data.renaming = true
	}
	const doRename = (node,data) => {
		pageApi.renamePage({"id": data.id,"name": data.name})
				.then((json) => {
					doGetPageList(null)
					ElMessage.success('重命名成功')
					data.renaming = false
				})

	}
	const changeDropWownStatus = () => {
		if(explan.value){
			explanClass.value = "el-tree"
			descriptorForTree.value ="点击收起目录"
			explan.value = false
		}else {
			explanClass.value = "hidTree"
			descriptorForTree.value ="点击展开目录"
			explan.value = true
		}
	}
	const loadPageList = (param) => {
		param = param || {}
		doGetPageList(param.parentId, param.node)
	}


	const changeNodeOptionStatus = (param,type) => {
		if (optionLock.value && optionPageId.value === param.id){
			return true
		}
		if (type > 0){
			optionStatus.value = true
			optionPageId.value = param.id
		}
		if (type < 0 ){
			optionStatus.value = false
			optionPageId.value = param.id
		}
		if (type === 0 && optionPageId.value === param.id && optionStatus.value){
			return true
		}
	}
	const turnLeftCollapse = () => {
		leftCollapse.value = !leftCollapse.value
		setTimeout(() => {
			if (leftCollapse.value) {
				rightAsideWidthChange(rightAsideWidth.value + 1)
			} else {
				rightAsideWidthChange(1)
			}
		}, 100)
	}
	const createWiki = (editorType,parentId) => {
		if (choiceSpace.value > 0) {
			let name = "新建文档"
			if(editorType === 0){
				name = "新建文件夹"
			}
			pageApi.updatePage({spaceId: choiceSpace.value,parentId: parentId,editorType:editorType,name:name,content:'',preview:''})
					.then((json) => {
						doGetPageList(null)
						ElMessage.success('创建成功')
						if (editorType !== 0){
							router.push({
								path: '/page/edit',
								query: {parentId: nowPageId.value, pageId: json.data.id}
							})
						}
					})

		} else {
			ElMessage.warning('请先选择或创建空间')
		}
	}
	const changeWikiPageExpandedKeys = (pageId) => {
		// 展开没有触发子节点的加载，如果去加载子节点有还找不到当前的node，暂不展开
		// wikiPageExpandedKeys.value= [pageId];
	}
	const doSearchByKeywords = (queryString, callback) => {
		if (!queryString || !queryString.trim()) {
			callback([])
			return
		}
		pageApi
				.pageNews({spaceId: choiceSpace.value, keywords: queryString})
				.then((json) => {
					let spacePageNews = json.data || []
					callback(spacePageNews)
				})
	}
	const handleSearchKeywordsSelect = (item) => {
		searchKeywords.value = ''
		router.push({path: '/page/show', query: {pageId: item.pageId}})
	}
	let wikiPageTreeRef = ref();
	const searchByKeywords = () => {
		wikiPageTreeRef.value.filter(searchKeywords.value)
	}
	const searchByKeywordsNewPage = () => {
		var routeUrl = router.resolve({
			path: '/page/search',
			query: {keywords: searchKeywords.value}
		})
		window.open(routeUrl.href, '_blank')
	}
	const handleNodeClick = (data) => {
		//console.log('点击节点：', data, nowPageId.value)

		nowPageId.value = data.id
		if (data.editorType !== 0) {
			router.push({path: '/page/show', query: {pageId: data.id}})
		}
		handleNodeExpand(data)
	}
	const handleNodeExpand = (node) => {

		if (
				node.children &&
				node.children.length > 0 &&
				node.children[0].needLoad
		) {
			console.log('加载节点：', node)
			doGetPageList(node.id, node)
		}
	}
	const handlePageDrop = (draggingNode, dropNode, dropType, ev) => {

		console.log('tree drop: ', draggingNode.data, dropNode.data, dropType)
		// 'prev'、'inner'、'next'
		// before、after、inner
		var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId}
		if (dropType == 'inner') {
			param.parentId = dropNode.data.id
		} else if (dropType == 'before') {
			param.beforeSeq = dropNode.data.seqNo
		} else if (dropType == 'after') {
			param.afterSeq = dropNode.data.seqNo
		}
		pageApi.pageChangeParent(param).then((res) => {
			doGetPageList(null)
		})
	}
	const loadUserMessageIfPopVisible = () => {
		if (!userMessagePopVisible.value) {
			loadUserMessageList()
		}
	}
	const loadUserMessageList = () => {
		userApi.getUserMessageList(userMsgParam.value).then((res) => {
			userMessageList.value = res.data || []
			userMsgTotalCount.value = res.total || 0
			haveNotReadUserMessage.value =
					userMessageList.value.filter((item) => item.msgStatus == 0).length > 0
		})
	}
	const showUserMessage = (row) => {
		if (row.msgStatus == 0) {
			userApi.readUserMessage({ids: row.id}).then(() => {
				loadUserMessageList()
			})
		}
		if (row.msgType >= 2 && row.msgType <= 12) {
			router.push({path: '/page/show', query: {pageId: row.dataId}})
			userMessagePopVisible.value = false
		}
	}
	const readAllUserMessage = () => {
		let msgIds = []
		userMessageList.value
				.filter((item) => item.msgStatus == 0)
				.forEach((item) => {
					msgIds.push(item.id)
				})
		if (msgIds.length <= 0) return
		userApi.readUserMessage({ids: msgIds.join(',')}).then(() => {
			ElMessage.success('标记成功')
			loadUserMessageList()
		})
	}
	const handleCurrentChange = (val) => {
		userMsgParam.value.pageNum = val
		loadUserMessageList()
	}
	const filterPageNode = (value, data) => {
		if (!value || !data.name) return true;
		// issues:I2CG72 忽略大小写
		let name = data.name.toLowerCase();
		return name.indexOf(value.toLowerCase()) !== -1;
	}
	let createSpaceRef = ref();
	const spaceChangeEvents = (data) => {
		if (data === 0) {
			// 新建空间
			createSpaceRef.value.show();
		} else if (data === -1) {
			// 管理空间
			router.push({path: '/space/manage'});
		} else {
			nowPageId.value = '';
			choiceSpace.value = data;
			nowSpaceShow.value = spaceList.value.find((item) => item.id === data);
			storePage.spaceInfo = nowSpaceShow.value;
			doGetPageList(null);
			router.push({path: '/home', query: {spaceId: data}});
		}
	}
	const loadSpaceList = (spaceId) => {
		pageApi.spaceList({}).then((json) => {
			spaceList.value = json.data || [];
			let spaceOptionsNew = [];
			spaceList.value.forEach((item) => spaceOptionsNew.push({label: item.name, value: item.id}));
			spaceOptions.value = spaceOptionsNew;
			if (spaceList.value.length > 0) {
				let nowSpaceId = spaceId;
				let nowSpaceShowTemp = spaceList.value.find((item) => item.id === spaceId);
				if (!nowSpaceShowTemp) {
					nowSpaceShowTemp = spaceList.value[0];
					nowSpaceId = nowSpaceShowTemp.id;
				}
				nowSpaceShow.value = nowSpaceShowTemp;
				storePage.spaceInfo = nowSpaceShowTemp;
				choiceSpace.value = nowSpaceId;
				nowPageId.value = '';
				doGetPageList(null);
				// TODO 在首页时跳转
				try {
					if (route.path === '/home') {
						router.push({path: '/home', query: {spaceId: nowSpaceId}});
					}
				} catch (e) {
					console.log(e);
				}
			}
		})
	}
	const doGetPageList = (parentId, node) => {
		let param = {spaceId: choiceSpace.value}
		pageApi.pageList(param).then((json) => {
			wikiPageList.value = json.data || []
			// 设置默认选中效果
			nextTick(() => {
				nowPageId.value = route.query.pageId
				if (nowPageId.value) {
					wikiPageTreeRef.value.setCurrentKey(nowPageId.value)
				}
			})
		})
	}
	let aboutDialogRef = ref();
	const userSettingDropdown = (command) => {
		console.log('command:' + command)
		if (command == 'userSignOut') {
			userSignOut()
		} else if (command == 'aboutDoc') {
			aboutDialogRef.value.show()
		} else if (command == 'myInfo') {
			router.push({path: '/user/myInfo'})
		} else if (command == 'console') {
			window.open(import.meta.env.VITE_APP_BASE_API, '_blank')
		} else {
			ElMessage.warning('暂未开放')
		}
	}
	const userSignOut = () => {
		userApi.userLogout().then(() => {
			location.reload()
		})
	}
	let storeUser = useStoreUserData();
	const getSelfUserInfo = () => {
		userApi.getSelfUserInfo().then((json) => {
			userSelfInfo.value = json.data;
			storeUser.userInfo = json.data;
		})
	}
	const getSpaceInfo = (spaceId) => {
		for (let i = 0; i < spaceList.value.length; i++) {
			if (spaceList.value[i].id == spaceId) {
				return spaceList.value[i]
			}
		}
		return {}
	}
	const switchSpacePage = (spaceId) => {
		spaceId = parseInt(spaceId)
		if (choiceSpace.value == spaceId) {
			return
		}
		choiceSpace.value = spaceId
		doGetPageList(null)
	}
	const rightAsideWidthChange = (width) => {
		storeDisplay.viewMenuWidth = width
	}
</script>

<style>
	html,
	body {
		margin: 0;
		padding: 0;
		height: 100%;
	}

	.global-layout-vue {
		height: 100%;
	}

	.hidTree {
		display: none;
	}

	#app,
	.el-container,
	.el-menu {
		height: 100%;
	}

	.el-header {
		background-color: #1d4e89 !important;
	}

	.header-right-user-name {
		color: #fff;
		padding-right: 5px;
		vertical-align: middle;
	}

	.el-header {
		color: #333;
		line-height: 40px;
		height: 40px !important;
	}

	.fold-btn {
		color: #ccc !important;
		font-size: 18px;
	}

	.head-icon {
		margin-right: 15px;
		font-size: 16px;
		cursor: pointer;
		color: #fff;
		vertical-align: middle;
	}

	.header-user-message .page-info-box {
		text-align: right;
		margin-top: 10px;
	}

	.upgrade-info {
		max-height: 150px;
		overflow-y: auto;
		word-break: break-all;
		white-space: pre-wrap;
		line-height: 26px;
	}

	.search-option-item {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.search-option-item .title {
		font-weight: bold;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.search-option-item .content {
		font-size: 12px;
		color: #888;
	}

	.search-autocomplete {
		width: 600px !important;
	}
</style>

<style lang="scss">
	.wiki-page-tree-box {
		overflow-y: auto;
		overflow-x: hidden;
		padding-bottom: 30px;

		.el-tree-node__content {
			height: 35px;

			.page-tree-node {
				width: 100%;

				.label {
					.el-icon {
						vertical-align: middle;
					}

					.text {
						margin-left: 5px;
						vertical-align: middle;

						max-width: calc(100% - 40px);
						display: inline-block;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
					}
				}
			}
		}
	}
</style>
