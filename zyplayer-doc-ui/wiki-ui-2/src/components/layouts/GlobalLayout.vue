<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="leftCollapse" :style="{ width: rightAsideWidth + 'px' }">
				<div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
					<div style="margin-bottom: 10px">
						<el-select :model-value="choiceSpace" filterable placeholder="选择空间" style="width: 100%" @change="spaceChangeEvents">
							<el-option-group label="">
								<el-option key="0" label="创建空间" value="0"></el-option>
								<el-option key="-1" label="空间管理" value="-1"></el-option>
							</el-option-group>
							<el-option-group label=""></el-option-group>
							<el-option v-for="item in spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</div>
					<div align="center">
						<el-button :icon="ElIconPlus" style="width: 100%" v-on:click="createWiki">创建文档</el-button>
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
					<div class="wiki-page-tree-box">
						<el-tree
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
								@node-drop="handlePageDrop"
						>
							<template v-slot="{ node, data }">
				                <span style="font-size: 14px">
				                  <el-icon><el-icon-document/></el-icon>
				                  <span style="margin-left: 6px">{{ node.label }}</span>
				                </span>
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
							<el-popover v-model="userMessagePopVisible" placement="bottom" trigger="click" width="600">
								<template v-slot:reference>
									<el-badge :is-dot="haveNotReadUserMessage" style="margin: 0 20px">
										<el-icon class="head-icon" style="margin-right: 0">
											<el-icon-bell/>
										</el-icon>
									</el-badge>
								</template>
								<div style="margin-bottom: 10px">
									<span style="font-size: 14px; font-weight: bold">通知</span>
									<el-link v-if="haveNotReadUserMessage" :icon="ElIconCheck" style="float: right" type="primary" v-on:click="readAllUserMessage">本页标记已读</el-link>
								</div>
								<div class="header-user-message">
									<el-table :data="userMessageList" border max-height="500" size="small" style="width: 100%; margin-bottom: 5px">
										<el-table-column label="操作人" prop="operatorUserName" width="100px"></el-table-column>
										<el-table-column label="操作时间" prop="creationTime" width="140px"></el-table-column>
										<el-table-column label="内容">
											<template v-slot="scope">
												{{ scope.row.msgContent }}
												<el-badge :is-dot="scope.row.msgStatus == 0" style="line-height: 10px; padding-right: 5px">
													<el-link type="primary" v-on:click="showUserMessage(scope.row)">查看</el-link>
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

let route = useRoute();
let router = useRouter();
const storeDisplay = useStoreDisplay();

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

onMounted(() => {
	loadSpaceList()
	loadUserMessageList()
	getSelfUserInfo()
});
const loadPageList = (param) => {
	param = param || {}
	doGetPageList(param.parentId, param.node)
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
const createWiki = () => {
	if (choiceSpace.value > 0) {
		router.push({
			path: '/page/edit',
			query: {parentId: nowPageId.value}
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
	// console.log('点击节点：', data, nowPageId.value)
	nowPageId.value = data.id
	router.push({path: '/page/show', query: {pageId: data.id}})
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
	if (!value || !data.name) return true
	// issues:I2CG72 忽略大小写
	let name = data.name.toLowerCase()
	return name.indexOf(value.toLowerCase()) !== -1
}
let createSpaceRef = ref();
const spaceChangeEvents = (data) => {
	if (data == 0) {
		// 新建空间
		createSpaceRef.value.show()
	} else if (data == -1) {
		// 管理空间
		router.push({path: '/space/manage'})
	} else {
		choiceSpace.value = data
		for (let i = 0; i < spaceList.value.length; i++) {
			if (spaceList.value[i].id == data) {
				nowSpaceShow.value = spaceList.value[i]
				break
			}
		}
		nowPageId.value = ''
		doGetPageList(null)
		router.push({path: '/home', query: {spaceId: data}})
	}
}
const loadSpaceList = (spaceId) => {
	pageApi.spaceList({}).then((json) => {
		spaceList.value = json.data || []
		let spaceOptionsNew = []
		spaceList.value.forEach((item) =>
			spaceOptionsNew.push({label: item.name, value: item.id})
		)
		spaceOptions.value = spaceOptionsNew
		if (spaceList.value.length > 0) {
			let nowSpaceId = spaceId
			let nowSpaceShow = spaceList.value.find((item) => item.id == spaceId)
			if (!nowSpaceShow) {
				nowSpaceShow = spaceList.value[0]
				nowSpaceId = nowSpaceShow.id
			}
			nowSpaceShow.value = nowSpaceShow
			choiceSpace.value = nowSpaceId
			nowPageId.value = ''
			doGetPageList(null)
			// TODO 在首页时跳转
			try {
				if (route.path === '/home') {
					router.push({path: '/home', query: {spaceId: nowSpaceId}})
				}
			} catch (e) {
				console.log(e)
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
  overflow: auto;
  padding-bottom: 30px;

  .el-tree-node {
	.el-tree-node__content {
	  height: 35px;

	  .el-tree-node__label {
		width: 100%;
	  }
	}
  }
}
</style>
