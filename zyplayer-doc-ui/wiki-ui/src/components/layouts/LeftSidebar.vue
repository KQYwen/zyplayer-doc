<template>
	<LeftSidebarCli
			:readOnly=false
			:wikiPageList="storePage.wikiPageList"
			:spaceOptions="storeSpace.spaceOptions"
			:nowPageId="storePage.choosePageId"
			:choiceSpace="storeSpace.chooseSpaceId"
			@setNowPageId="setNowPageId"
			@doGetPageList="doGetPageList"
			@spaceChangeEvents="spaceChangeEvents">
		<template v-slot:addMenuDir>
			<AddMenu
					:choiceSpace="storeSpace.chooseSpaceId"
					:choosePageId="storePage.optionPageId"
					:nowPageId="storePage.choosePageId"
					:funcId="0"
					@createWikiByTemplate="createWikiByTemplate"
					@choosePageIdFunc="choosePageIdFunc"
					@doGetPageList="doGetPageList"
			/>
		</template>
		<template v-slot:addMenuNode="{node,data}">
			<div class="page-tree-node" @mouseover="changeNodeOptionStatus(data) ">
				<div style="width: calc(100% - 30px);overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
					<!--图标-->
					<el-icon v-if="data.editorType === 0" class="clickAddIcon"
							 style="margin-right: 5px;vertical-align: middle;">
						<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none">
							<path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z"
								  fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path>
							<path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path>
							<path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round"
								  stroke-linejoin="round"></path>
							<path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round"
								  stroke-linejoin="round"></path>
						</svg>
					</el-icon>
					<el-icon v-else-if="data.editorType === 1" class="clickAddIcon"
							 style="margin-right: 5px;vertical-align: middle;">
						<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none">
							<rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor"
								  stroke-width="4"></rect>
							<path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4"
								  stroke-linecap="round" stroke-linejoin="round"></path>
						</svg>
					</el-icon>
					<el-icon v-else-if="data.editorType === 2" class="clickAddIcon"
							 style="margin-right: 5px;vertical-align: middle;">
						<el-icon-document/>
					</el-icon>
					<!--标题-->
					<el-tooltip :content="data.tags" placement="top-start" :show-after="500"
								v-if="data.shareStatus !== undefined">
						<a-tag color="#f50">{{filterShareStatus(data.shareStatus)}}</a-tag>
					</el-tooltip>
					<a-input v-if="data.renaming" v-model:value="data.name" class="rename-input" placeholder="请输入文档名称"
							 @blur="doRename(node,data)" @click.stop/>
					<span v-else style="vertical-align: middle;margin-right: 5px">
									<el-tooltip :content="node.label" placement="top-start" :show-after="700">{{ node.label }}</el-tooltip>
								</span>
					<!--操作-->
					<div class="page-action-box" :class="data.renaming?'renaming':''" @click.stop>
						<AddMenu
								:choiceSpace="storeSpace.chooseSpaceId"
								:choosePageId="storePage.optionPageId"
								:nowPageId="storePage.choosePageId"
								:funcId="data.id"
								@createWikiByTemplate="createWikiByTemplate"
								@choosePageIdFunc="choosePageIdFunc"
								@doGetPageList="doGetPageList"
						/>
						<a-dropdown :trigger="['click']" @click="choosePageIdFunc(data.id)">
							<el-button :icon="MoreFilled" text class="page-action-dropdown-btn"></el-button>
							<template #overlay>
								<a-menu>
									<a-menu-item key="0" @click="rename(node,data)">
										<el-icon class="clickAddIcon" style="margin-right: 5px">
											<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none">
												<path d="M42 26V40C42 41.1046 41.1046 42 40 42H8C6.89543 42 6 41.1046 6 40V8C6 6.89543 6.89543 6 8 6L22 6"
													  stroke="currentColor" stroke-width="4" stroke-linecap="round"
													  stroke-linejoin="round"></path>
												<path d="M14 26.7199V34H21.3172L42 13.3081L34.6951 6L14 26.7199Z"
													  fill="none" stroke="currentColor" stroke-width="4"
													  stroke-linejoin="round"></path>
											</svg>
										</el-icon>
										重命名
									</a-menu-item>
									<a-menu-item key="1" @click="deleteWikiPage(data.shareStatus)">
										<el-icon class="clickAddIcon" style="margin-right: 5px">
											<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none">
												<path d="M9 10V44H39V10H9Z" fill="none" stroke="currentColor"
													  stroke-width="4" stroke-linejoin="round"></path>
												<path d="M20 20V33" stroke="currentColor" stroke-width="4"
													  stroke-linecap="round" stroke-linejoin="round"></path>
												<path d="M28 20V33" stroke="currentColor" stroke-width="4"
													  stroke-linecap="round" stroke-linejoin="round"></path>
												<path d="M4 10H44" stroke="currentColor" stroke-width="4"
													  stroke-linecap="round" stroke-linejoin="round"></path>
												<path d="M16 10L19.289 4H28.7771L32 10H16Z" fill="none"
													  stroke="currentColor" stroke-width="4"
													  stroke-linejoin="round"></path>
											</svg>
										</el-icon>
										删除
									</a-menu-item>

									<a-sub-menu key="2" title="移动文档">
										<template #icon>
											<BlockOutlined/>
										</template>
										<a-menu-item key="3" @click="openMoveMenu(false)">
											<el-icon class="clickAddIcon" style="margin-right: 5px">
												<DocumentCopy/>
											</el-icon>
											复制文档
										</a-menu-item>
										<a-menu-item key="4" @click="openMoveMenu(true)">
											<el-icon class="clickAddIcon" style="margin-right: 5px">
												<Scissor/>
											</el-icon>
											迁移文档
										</a-menu-item>
									</a-sub-menu>
									<a-menu-item key="5" @click="openTemplateCreate(data.shareStatus !== undefined)"
												 v-if="data.editorType !== 0">
										<BuildOutlined/>
										设为模板
									</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</div>
				</div>
			</div>
		</template>
	</LeftSidebarCli>
	<templateManage ref="templateManageRef" :pageId="storePage.optionPageId" :spaceId="storeSpace.chooseSpaceId"
					@doGetPageList="doGetPageList"/>
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
		<LeftSidebarCli
				:readOnly=true
				:wikiPageList="moveToWikiPageList"
				:spaceOptions="storeSpace.spaceOptions"
				:nowPageId="moveToPageId"
				:choiceSpace="moveToSpaceId"
				@setNowPageId="setNowPageId"
				@doGetPageList="doGetPageList"
				@spaceChangeEvents="spaceChangeEvents"/>
	</a-modal>
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

	import {ref, onMounted,} from 'vue';
	import { useRouter, useRoute} from "vue-router";
	import {ElMessageBox, ElMessage} from 'element-plus'
	import pageApi from '../../assets/api/page'
	import CreateSpace from '../space/CreateSpace'
	import TemplateManage from '../template/TemplateManage'
	import RightResize from './RightResize.vue'
	import AddMenu from '../LeftSidebar/AddMenu.vue'
	import LeftSidebarCli from '../LeftSidebar/LeftSidebarCli.vue'
	import {useStoreDisplay} from '@/store/wikiDisplay.js'
	import {useStorePageData} from "@/store/pageData";
	import {DownOutlined, BuildOutlined, BlockOutlined} from '@ant-design/icons-vue';
	import {useStoreSpaceData} from "@/store/spaceData";

	let route = useRoute();
	let router = useRouter();
	let storePage = useStorePageData();
	let storeDisplay = useStoreDisplay();
	let storeSpace = useStoreSpaceData();

	// 空间搜索相关
	let nowSpaceShow = ref({});
	let moveToPageId = ref(0);
	let moveToSpaceId = ref(0);
	let moveToWikiPageList = ref([]);

	// 页面展示相关
	let wikiPage = ref({});
	let wikiPageExpandedKeys = ref([]);
	let rightAsideWidth = ref(300);
	let optionPageId = ref('');
	let visibleMoveMenu = ref(false);
	let onlyMoveMode = ref(false);
	let aModalWaiting = ref(false);
	let templateManageRef = ref(null)

	onMounted(()=>{
		init()
	})
	const init = () => {
		loadSpaceList()
	}

	const openTemplateCreate = (exsit) => {
		templateManageRef.value.showTemplateCreate(exsit)
	}

	const createWikiByTemplate = () => {
		templateManageRef.value.showTemplateManage()

	}

	const filterShareStatus = (data) => {
		if (data === 1) {
			return '公共模板'
		}
		return '个人模板'
	}

	const openMoveMenu = (onlyMove) => {
		onlyMoveMode.value = onlyMove
		visibleMoveMenu.value = true
		moveToPageId.value = storePage.choosePageId
		moveToSpaceId.value = storeSpace.chooseSpaceId
		moveToWikiPageList.value = storePage.wikiPageList
	}
	const handleOk = (onlyMove) => {
		aModalWaiting.value = true
		if (onlyMoveMode.value) {
			pageApi.movePage({
				"id": storePage.optionPageId,
				"spaceId": storeSpace.chooseSpaceId,
				"moveToPageId": moveToPageId.value,
				"moveToSpaceId": moveToSpaceId.value
			})
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
		pageApi.copyPage({
			"id": storePage.optionPageId,
			"spaceId": storeSpace.chooseSpaceId,
			"moveToPageId": moveToPageId.value,
			"moveToSpaceId": moveToSpaceId.value
		})
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
	const handleCancel = () => {
		visibleMoveMenu.value = false
		moveToPageId.value = 0
		moveToSpaceId.value = 0
		moveToWikiPageList.value = []
	}


	const deleteWikiPage = (share) => {
		let msg = '确定要删除此页面及其所有子页面吗？'
		if (share !== undefined) {
			msg = '选中的页面是：' + filterShareStatus(share) + '删除后无法使用此模板！ 确定要删除此页面及其所有子页面吗？'
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
		}).catch(() => {
		});
	}

	const choosePageIdFunc = (id) => {
		storePage.optionPageId = id
	}

	const setNowPageId = (id, readOnly) => {
		if (readOnly) {
			moveToPageId.value = id
			return
		}
		storePage.choosePageId = id
	}

	const rename = (node, data) => {
		data.renaming = true
	}
	const doRename = (node, data) => {
		pageApi.renamePage({"id": data.id, "name": data.name})
				.then((json) => {
					doGetPageList(null)
					ElMessage.success('重命名成功')
					data.renaming = false
				})

	}

	const changeNodeOptionStatus = (param) => {
		optionPageId.value = param.id
	}

	let createSpaceRef = ref();
	const spaceChangeEvents = (data, readonly) => {
		storePage.pageInfo = {}
		if (readonly) {
			moveToSpaceId.value = data
			setNowPageId(0, readonly)
			let param = {spaceId: moveToSpaceId.value}
			pageApi.pageList(param).then((json) => {
				moveToWikiPageList.value = json.data || []
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
			storePage.choosePageId = 0;
			storeSpace.chooseSpaceId = Number(data);
			nowSpaceShow.value = storeSpace.spaceList.find((item) => item.id === data);
			storeSpace.spaceInfo = nowSpaceShow.value;
			doGetPageList(null);
			router.push({path: '/home', query: {spaceId: data}});
		}
	}
	const loadSpaceList = (spaceId) => {
		pageApi.spaceList({}).then((json) => {
			storeSpace.spaceList = json.data || [];
			let spaceOptionsNew = [];
			storeSpace.spaceList.forEach((item) => spaceOptionsNew.push({label: item.name, value: item.id}));
			storeSpace.spaceOptions = spaceOptionsNew;
			if (storeSpace.spaceList.length > 0) {
				let nowSpaceId = spaceId;
				let nowSpaceShowTemp = storeSpace.spaceList.find((item) => item.id === spaceId);
				if (!nowSpaceShowTemp) {
					nowSpaceShowTemp = storeSpace.spaceList[0];
					nowSpaceId = nowSpaceShowTemp.id;
				}
				nowSpaceShow.value = nowSpaceShowTemp;
				storeSpace.spaceInfo = nowSpaceShowTemp;
				storeSpace.chooseSpaceId = nowSpaceId;
				storePage.choosePageId = 0;
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
		let param = {spaceId: storeSpace.chooseSpaceId}
		pageApi.pageList(param).then((json) => {
			storePage.wikiPageList = json.data || []
		})
	}
	defineExpose({init})
</script>


