<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="leftCollapse" :style="{ width: rightAsideWidth + 'px' }">
				<LeftSidebar
						ref="leftSidebarDir"
						:readOnly=false
						:wikiPageList="wikiPageList"
						:spaceOptions="spaceOptions"
						:nowPageId="nowPageId"
						:choiceSpace="choiceSpace"
						@setNowPageId="setNowPageId"
						@doGetPageList="doGetPageList"
						@spaceChangeEvents="spaceChangeEvents">
					<template v-slot:addMenuDir>
						<AddMenu
								:choiceSpace="choiceSpace"
								:choosePageId="choosePageId"
								:nowPageId = "nowPageId"
								:funcId = "0"
								@createWikiByTemplate="createWikiByTemplate"
								@choosePageIdFunc="choosePageIdFunc"
								@doGetPageList="doGetPageList"
						/>
					</template>
					<template v-slot:addMenuNode="{node,data}">
						<div class="page-tree-node" @mouseover="changeNodeOptionStatus(data) ">
							<div style="width: calc(100% - 30px);overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
								<!--图标-->
								<el-icon  v-if="data.editorType === 0" class="clickAddIcon" style="margin-right: 5px;vertical-align: middle;">
									<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
								</el-icon>
								<el-icon  v-else-if="data.editorType === 1" class="clickAddIcon" style="margin-right: 5px;vertical-align: middle;">
									<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor" stroke-width="4"></rect><path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
								</el-icon>
								<el-icon v-else-if="data.editorType === 2" class="clickAddIcon" style="margin-right: 5px;vertical-align: middle;">
									<el-icon-document/>
								</el-icon>
								<!--标题-->
								<el-tooltip :content="data.tags" placement="top-start" :show-after="500">
									<a-tag color="#f50" v-if="data.shareStatus !== undefined">{{filterShareStatus(data.shareStatus)}}</a-tag>
								</el-tooltip>
								<a-input v-if="data.renaming" v-model:value="data.name" class="rename-input" placeholder="请输入文档名称" @blur="doRename(node,data)" @click.stop/>
								<span v-else style="vertical-align: middle;margin-right: 5px">
									<el-tooltip :content="node.label" placement="top-start" :show-after="700">{{ node.label }}</el-tooltip>
								</span>
								<!--操作-->
								<div class="page-action-box" :class="data.renaming?'renaming':''" @click.stop>
									<AddMenu
											:choiceSpace="choiceSpace"
											:choosePageId="choosePageId"
											:nowPageId = "nowPageId"
											:funcId = "data.id"
											@createWikiByTemplate="createWikiByTemplate"
											@choosePageIdFunc="choosePageIdFunc"
											@doGetPageList="doGetPageList"
									/>
									<a-dropdown :trigger="['click']" @click="choosePageIdFunc(data.id)">
										<el-button :icon="MoreFilled" text class="page-action-dropdown-btn"></el-button>
										<template #overlay>
											<a-menu>
												<a-menu-item key="0" @click="rename(node,data)">
													<el-icon  class="clickAddIcon" style="margin-right: 5px">
														<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M42 26V40C42 41.1046 41.1046 42 40 42H8C6.89543 42 6 41.1046 6 40V8C6 6.89543 6.89543 6 8 6L22 6" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M14 26.7199V34H21.3172L42 13.3081L34.6951 6L14 26.7199Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path></svg>
													</el-icon>
													重命名
												</a-menu-item>
												<a-menu-item key="1" @click="deleteWikiPage(data.shareStatus)">
													<el-icon  class="clickAddIcon" style="margin-right: 5px">
														<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M9 10V44H39V10H9Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M20 20V33" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M28 20V33" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M4 10H44" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M16 10L19.289 4H28.7771L32 10H16Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path></svg>
													</el-icon>
													删除
												</a-menu-item>

												<a-sub-menu key="2" title="移动文档"  >
													<template #icon>
														<BlockOutlined  />
													</template>
													<a-menu-item key="3" @click="openMoveMenu(false)">
														<el-icon  class="clickAddIcon" style="margin-right: 5px">
															<DocumentCopy/>
														</el-icon>
														复制文档
													</a-menu-item>
													<a-menu-item key="4" @click="openMoveMenu(true)">
														<el-icon  class="clickAddIcon" style="margin-right: 5px">
															<Scissor/>
														</el-icon>
														迁移文档
													</a-menu-item>
												</a-sub-menu>
												<a-menu-item key="5" @click="openTemplateCreate(data.shareStatus !== undefined)" v-if="data.editorType !== 0">
													<BuildOutlined />
													设为模板
												</a-menu-item>
											</a-menu>
										</template>
									</a-dropdown>
								</div>
							</div>
						</div>
					</template>
				</LeftSidebar>
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
		<templateManage ref="templateManageRef" :pageId="choosePageId" :spaceId="choiceSpace" @doGetPageList="doGetPageList" />
		<create-space ref="createSpaceRef" @success="loadSpaceList"></create-space>
		<a-modal
				v-model:open="visibleMoveMenu"
				title="选择"
				@ok="handleOk"
				@cancel="handleCancel"
				ok-text="确认"
				cancel-text="取消"
				:confirm-loading="aModalWaiting"
				:destroyOnClose=true
				:closable=false>
			<LeftSidebar
					:readOnly=true
					:wikiPageList="moveToWikiPageList"
					:spaceOptions="spaceOptions"
					:nowPageId="moveToPageId"
					:choiceSpace="moveToSpaceId"
					@setNowPageId="setNowPageId"
					@doGetPageList="doGetPageList"
					@spaceChangeEvents="spaceChangeEvents"/>
		</a-modal>
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
		Files,
			Scissor,
		DocumentCopy,
		MoreFilled
	} from '@element-plus/icons-vue'

	import {onBeforeUnmount, toRefs, ref, reactive, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
	import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
	import {ElMessageBox, ElMessage} from 'element-plus'
	import userApi from '../../assets/api/user'
	import pageApi from '../../assets/api/page'
	import CreateSpace from '../space/CreateSpace'
	import TemplateManage from '../template/TemplateManage'
	import RightResize from './RightResize.vue'
	import AddMenu from './AddMenu.vue'
	import LeftSidebar from './LeftSidebar.vue'
	import AboutDialog from '../../views/common/AboutDialog'
	import {useStoreDisplay} from '@/store/wikiDisplay.js'
	import {useStoreUserData} from "@/store/userData";
	import {useStorePageData} from "@/store/pageData";
	import { defineComponent } from 'vue';
	import { DownOutlined,BuildOutlined,BlockOutlined } from '@ant-design/icons-vue';

	let route = useRoute();
	let router = useRouter();
	let storePage = useStorePageData();
	let storeDisplay = useStoreDisplay();

	let leftCollapse = ref(true);
	let rightContentLoading = ref(false);
	let pathIndex = ref([]);
	let defaultProps = ref({children: 'children', label: 'name',});
	// 空间搜索相关
	let spaceOptions = ref();
	let spaceList = ref([]);
	let choiceSpace = ref(undefined);
	let nowSpaceShow = ref({});
	let nowPageId = ref(0);
	let moveToPageId = ref(0);
	let moveToSpaceId = ref(0);
	let moveToWikiPageList = ref([]);
	let leftSideBarDir = ref(null);

	// 页面展示相关
	let wikiPageList = ref();
	let wikiPage = ref({});
	let wikiPageExpandedKeys = ref([]);
	let userSelfInfo = ref({});
	let userMessageList = ref([]);
	let haveNotReadUserMessage = ref(false);
	let userMessagePopVisible = ref(false);
	let userMsgTotalCount = ref(0);
	let userMsgParam = ref({sysType: 2, pageNum: 1, pageSize: 20,});
	let rightAsideWidth = ref(300);
	let optionPageId = ref('');
	let choosePageId = ref(0);
	let visibleMoveMenu = ref(false);
	let onlyMoveMode = ref(false);
	let aModalWaiting = ref(false);
	let templateManageRef=ref(null)

	watch(()=>nowPageId ,()=>{
		leftSideBarDir.value.assisSetCurrentKey();
	})

	//useless function
	/*

	// 依据目录树存储的map全局对象
	let treePathDataMap = ref(new Map());
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
	 */

	onMounted(() => {
		loadSpaceList()
		loadUserMessageList()
		getSelfUserInfo()
	});

	const openTemplateCreate = (exsit) =>{
		templateManageRef.value.showTemplateCreate(exsit)
	}

	const createWikiByTemplate = () =>{
		templateManageRef.value.showTemplateManage()

	}

	const filterShareStatus = (data) =>{
		if (data === 1){
			return '公共模板'
		}
		return '个人模板'
	}

	const openMoveMenu = (onlyMove) =>{
		onlyMoveMode.value = onlyMove
		visibleMoveMenu.value =true
		moveToPageId.value = nowPageId.value
		moveToSpaceId.value = choiceSpace.value
		moveToWikiPageList.value = wikiPageList.value
	}
	const handleOk = (onlyMove) =>{
		aModalWaiting.value = true
		if (onlyMoveMode.value){
			pageApi.movePage({"id": choosePageId.value,"spaceId": choiceSpace.value,"moveToPageId":moveToPageId.value,"moveToSpaceId":moveToSpaceId.value})
					.then((json) => {
						doGetPageList(null)
						ElMessage.success('迁移成功')
						handleCancel()
						aModalWaiting.value = false
					}).catch((e) => {
						aModalWaiting.value = false
					})
			return
		}
		pageApi.copyPage({"id": choosePageId.value,"spaceId": choiceSpace.value,"moveToPageId":moveToPageId.value,"moveToSpaceId":moveToSpaceId.value})
				.then((json) => {
					doGetPageList(null)
					ElMessage.success('复制成功')
					handleCancel()
					aModalWaiting.value = false
				}).catch((e) => {
					aModalWaiting.value = false
				})
		return
	}
	const handleCancel = () =>{
		visibleMoveMenu.value =false
		moveToPageId.value = 0
		moveToSpaceId.value = 0
		moveToWikiPageList.value = []
	}


	const deleteWikiPage = (share) => {
		let msg = '确定要删除此页面及其所有子页面吗？'
		if (share !== undefined){
			msg='选中的页面是：' +filterShareStatus(share)+'删除后无法使用此模板！ 确定要删除此页面及其所有子页面吗？'
		}
		ElMessageBox.confirm(msg, '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning',
		}).then(() => {
			let param = {pageId: optionPageId.value};
			pageApi.pageDelete(param).then(() => {
				ElMessage.success('已删除')
				doGetPageList(null)
			});
		}).catch(() => {});
	}

	const choosePageIdFunc = (id) => {
		choosePageId.value = id
	}

	const setNowPageId = (id,readOnly)=>{
		if (readOnly){
			moveToPageId.value = id
			return
		}
		nowPageId.value = id
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

	const loadPageList = (param) => {
		param = param || {}
		doGetPageList(param.parentId, param.node)
	}

	const changeNodeOptionStatus = (param) => {
		optionPageId.value = param.id
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

	const changeWikiPageExpandedKeys = (pageId) => {
		// 展开没有触发子节点的加载，如果去加载子节点有还找不到当前的node，暂不展开
		// wikiPageExpandedKeys.value= [pageId];
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
		if (row.msgType >= 2 && row.msgType <= 14) {
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

	let createSpaceRef = ref();
	const spaceChangeEvents = (data,readonly) => {
		if (readonly){
			moveToSpaceId.value = data
			setNowPageId(0,readonly)
			let param = {spaceId:moveToSpaceId.value}
			pageApi.pageList(param).then((json)=>{
				moveToWikiPageList.value = json.data||[]
			})
			return
		}
		if (data === 0) {
			// 新建空间
			createSpaceRef.value.show();
		} else if (data === -1) {
			// 管理空间
			router.push({path: '/space/manage'});
		} else {
			nowPageId.value = 0;
			choiceSpace.value = Number(data);
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
				nowPageId.value = 0;
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
	.space-folder-box {
		margin-left: 10px;
		margin-bottom: 10px;
		position: relative;

		.folder-action-dropdown-btn {
			padding: 0 8px;
			height: 25px;
			position: absolute;
			right: 0;
		}
	}

	.wiki-page-tree-box {
		overflow-y: auto;
		overflow-x: hidden;
		padding-bottom: 30px;

		.el-tree-node__content {
			height: 35px;
			position: relative;

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

				.rename-input {
					width: 90%;
				}

				.page-action-box {
					position: absolute;
					right: 0;
					top: 0;
					height: 35px;
					line-height: 35px;
					background: #fff;
					border-radius: 4px;
					display: none;

					.page-action-dropdown-btn {
						padding: 0 8px;
						height: 35px;
						margin-top: -1px;
					}

					.el-button+.el-button {
						margin-left: 0;
					}
				}

				.page-action-box.renaming {
					display: none !important;
				}
			}

			&:hover .page-action-box {
				display: block;
			}
		}
	}
</style>
