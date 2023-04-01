<template>
	<div class="page-show-vue">
		<el-row type="border-card" style="height: 100%">
			<el-col :span="actionTabVisible ? 18 : 24" style="padding: 20px;border-right: 1px solid #f1f1f1;height: 100%;overflow: auto;">
				<el-row>
					<el-col :span="navigationList.length > 0 ? 18 : 24">
						<div style="max-width: 1000px; margin: 0 auto; padding-left: 10px">
							<div class="wiki-title" ref="wikiTitleRef">{{ wikiPage.name }}</div>
							<div class="wiki-author">
								<el-row>
									<el-col :span="12">
										<span v-if="wikiPage.updateUserName">{{ wikiPage.updateUserName }}　于　{{wikiPage.updateTime}}　修改</span>
										<span v-else class="create-user-time">{{ wikiPage.createUserName }}　于　{{wikiPage.createTime}}　创建</span>
									</el-col>
									<el-col :span="12" style="text-align: right;">
										<el-link type="primary" :icon="ElIconChatLineRound" :underline="false" @click="showCommentWiki" style="margin-right: 15px"> 评论</el-link>
										<el-upload v-if="wikiPageAuth.canUploadFile === 1"
										           :on-success="uploadFileSuccess"
										           :on-error="uploadFileError"
										           :action="uploadFileUrl"
										           :data="uploadFormData"
										           :with-credentials="true" class="upload-page-file" name="files"
										           show-file-list multiple :limit="999"
										           style="display: inline; margin-right: 15px;vertical-align: middle;">
											<el-link type="primary" :underline="false" :icon="ElIconUpload"> 上传附件</el-link>
										</el-upload>
										<el-link v-if="wikiPageAuth.canEdit === 1" type="primary" :underline="false" :icon="ElIconEdit" @click="editWiki" style="margin-right: 15px;"> 编辑</el-link>
										<el-dropdown style="margin-right: 15px;vertical-align: middle;" trigger="click" @command="handleMoreCommand">
											<el-link type="primary" :underline="false">
												更多
												<el-icon class="el-icon--right"><el-icon-arrow-down/></el-icon>
											</el-link>
											<template v-slot:dropdown>
												<el-dropdown-menu>
													<el-dropdown-item command="showPageHistory" :icon="ElIconTime">查看历史版本</el-dropdown-item>
													<el-dropdown-item command="editAuth" v-if="wikiPageAuth.canConfigAuth == 1" :icon="ElIconSCheck">权限设置</el-dropdown-item>
													<el-dropdown-item command="showOpenPage" v-if="spaceInfo.openDoc == 1" :icon="ElIconShare">查看开放文档</el-dropdown-item>
													<el-dropdown-item command="showMobileView" v-if="spaceInfo.openDoc == 1" :icon="ElIconMobilePhone">手机端查看</el-dropdown-item>
													<el-dropdown-item command="exportWord" :icon="ElIconDownload">导出为Word文档</el-dropdown-item>
													<el-dropdown-item command="deletePage" v-if="wikiPageAuth.canDelete == 1" :icon="ElIconDelete">删除</el-dropdown-item>
												</el-dropdown-menu>
											</template>
										</el-dropdown>
									</el-col>
								</el-row>
							</div>
							<div class="wiki-files">
								<el-table v-show="pageFileList.length > 0" :data="pageFileList" border style="width: 100%; margin-bottom: 5px">
									<el-table-column label="文件名" show-overflow-tooltip>
										<template v-slot="scope">
											<el-link target="_blank" :href="scope.row.fileUrl" type="primary">{{scope.row.fileName }}</el-link>
										</template>
									</el-table-column>
									<el-table-column prop="createUserName" label="创建人" width="110px" show-overflow-tooltip></el-table-column>
									<el-table-column label="文件大小" width="120px">
										<template v-slot="scope">{{computeFileSize(scope.row.fileSize) }}</template>
									</el-table-column>
									<el-table-column prop="createTime" label="创建时间" width="160px"></el-table-column>
									<el-table-column prop="downloadNum" label="下载次数" width="90px">
										<template v-slot="scope">{{scope.row.downloadNum || 0}}</template>
									</el-table-column>
									<el-table-column label="操作" width="90px" v-if="wikiPageAuth.canDeleteFile == 1">
										<template v-slot="scope">
											<el-button v-on:click="deletePageFile(scope.row)">删除</el-button>
										</template>
									</el-table-column>
								</el-table>
							</div>
							<div ref="pageContentRef" class="wiki-page-content">
								<div v-html="pageShowDetail" class="markdown-body" v-if="wikiPage.editorType == 2" v-highlight></div>
								<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
							</div>
							<div style="margin-top: 40px; font-size: 14px">
				                <span style="vertical-align: top" class="is-link">
				                  <span v-show="wikiPage.selfZan == 0" v-on:click="zanPage(1)"><img src="../../assets/img/zan.png" style="vertical-align: middle"/> 赞</span>
				                  <span v-show="wikiPage.selfZan == 1" v-on:click="zanPage(0)"><img src="../../assets/img/zan.png" style="vertical-align: middle; transform: rotateX(180deg)"/> 踩</span>
				                </span>
								<span style="margin-left: 10px; vertical-align: top">
				                  <span v-if="wikiPage.selfZan == 0 && wikiPage.zanNum <= 0">成为第一个赞同者</span>
				                  <span v-else-if="wikiPage.selfZan == 0 && wikiPage.zanNum > 0">
				                    <span class="is-link" v-on:click="showZanPageUser">{{ wikiPage.zanNum }}人</span>赞了它
				                  </span>
				                  <span v-else-if="wikiPage.selfZan == 1 && wikiPage.zanNum <= 1">我赞了它</span>
				                  <span v-else-if="wikiPage.selfZan == 1 && wikiPage.zanNum > 1">
				                    <span class="is-link" v-on:click="showZanPageUser">我和{{ wikiPage.zanNum - 1 }}个其他人</span>赞了它
				                  </span>
				                </span>
								<span style="margin-left: 10px">
                                    <el-icon style="font-size: 16px; color: #666;vertical-align: middle;"><el-icon-view/></el-icon> {{ wikiPage.viewNum }}次阅读
								</span>
							</div>
						</div>
					</el-col>
					<el-col :span="navigationList.length > 0 ? 6 : 0" v-if="navigationList.length > 0">
						<Navigation ref="navigationRef" :heading="navigationList"></Navigation>
					</el-col>
				</el-row>
			</el-col>
			<el-col :span="6" style="height: 100%" v-show="actionTabVisible">
				<el-icon @click="closeActionTab" class="close-action-tab">
					<el-icon-close/>
				</el-icon>
				<el-tabs v-model="actionTabActiveName" @tab-click="actionTabClick">
					<el-tab-pane label="评论" name="comment">
						<Comment/>
					</el-tab-pane>
					<el-tab-pane label="修改历史" name="history">
						<div class="action-tab-box">
							<div v-if="pageHistoryList.length <= 0" class="action-box-empty">
								暂无修改历史记录
							</div>
							<el-timeline v-else>
								<el-timeline-item v-for="history in pageHistoryList">
									<el-tag :type="pageHistoryChoice.id === history.id ? history.loading === 3 ? 'danger' : 'success' : 'info'" class="history-item" @click="historyClick(history)">
										<div>{{ history.createUserName }}</div>
										<div>{{ history.createTime }}</div>
									</el-tag>
									<el-icon class="history-loading-status" v-show="history.loading===1"><el-icon-loading/></el-icon>
									<el-icon class="history-loading-status" v-show="history.loading===2"><el-icon-circle-check/></el-icon>
									<el-icon class="history-loading-status" v-show="history.loading===3"><el-icon-circle-close/></el-icon>
								</el-timeline-item>
							</el-timeline>
						</div>
					</el-tab-pane>
				</el-tabs>
			</el-col>
		</el-row>
		<!--手机扫码查看弹窗-->
		<el-dialog title="手机扫码查看" v-model="mobileScanDialogVisible" width="400px">
			<div style="text-align: center">
				<div class="mobile-qr">
					<canvas ref="qrCodeDivRef"></canvas>
				</div>
				<div>使用微信或手机浏览器扫一扫查看</div>
				<div>或 <a target="_blank" :href="qrCodeUrl">直接访问</a></div>
			</div>
		</el-dialog>
		<!--点赞人员弹窗-->
		<el-dialog title="赞了它的人" v-model="zanUserDialogVisible" width="600px">
			<el-table :data="zanUserList" border :show-header="false" style="width: 100%; margin-bottom: 5px">
				<el-table-column prop="createUserName" label="用户"></el-table-column>
				<el-table-column prop="createTime" label="时间"></el-table-column>
			</el-table>
		</el-dialog>
		<!--人员权限弹窗-->
		<el-dialog title="页面权限" v-model="pageAuthDialogVisible" width="900px">
			<el-row>
				<el-select v-model="pageAuthNewUser" filterable remote reserve-keyword autoComplete="new-password" placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList" :loading="pageAuthUserLoading" style="width: 750px; margin-right: 10px">
					<el-option v-for="item in searchUserList" :key="item.id" :label="item.userName" :value="item.id"></el-option>
				</el-select>
				<el-button v-on:click="addPageAuthUser">添加</el-button>
			</el-row>
			<el-table :data="pageAuthUserList" border style="width: 100%; margin: 10px 0">
				<el-table-column prop="userName" label="用户" width="150"></el-table-column>
				<el-table-column label="权限">
					<template v-slot="scope">
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.editPage">编辑</el-checkbox>
						<!--						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.commentPage">评论</el-checkbox>-->
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.deletePage">删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileUpload">文件上传</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileDelete">文件删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageAuthManage">权限管理</el-checkbox>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="80">
					<template v-slot="scope">
						<el-button size="small" type="danger" plain v-on:click="deleteUserPageAuth(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div>
				<el-button type="primary" v-on:click="saveUserPageAuth">保存配置</el-button>
			</div>
		</el-dialog>
		<el-image-viewer
				v-if="showImagePreview"
				:url-list="showImagePreviewList"
				:initial-index="previewInitialIndex"
				@close="closeImagePreview"
				hide-on-click-modal
		/>
		<form method="post" ref="downloadFormRef" :action="downloadFormParam.url" target="_blank">
			<input type="hidden" :name="key" :value="val" v-for="(val, key) in downloadFormParam.param"/>
		</form>
	</div>
</template>

<script setup>
import {
	ArrowDown as ElIconArrowDown,
	View as ElIconView,
	Close as ElIconClose,
	Delete as ElIconDelete,
	Loading as ElIconLoading,
	CircleCheck as ElIconCircleCheck,
	CircleClose as ElIconCircleClose,
	ChatLineRound as ElIconChatLineRound,
	Upload as ElIconUpload,
	Edit as ElIconEdit,
	Timer as ElIconTime,
	Stamp as ElIconSCheck,
	Share as ElIconShare,
	Iphone as ElIconMobilePhone,
	Download as ElIconDownload,
} from '@element-plus/icons-vue'
import {toRefs, ref, reactive, onMounted, watch, defineProps, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import { ElMessageBox, ElMessage, ElNotification } from 'element-plus';
import QRCode from 'qrcode'
import unitUtil from '../../assets/lib/UnitUtil.js'
import htmlUtil from '../../assets/lib/HtmlUtil.js'
import pageApi from '../../assets/api/page'
import userApi from '../../assets/api/user'
import Navigation from './components/Navigation.vue'
import Comment from './show/Comment.vue'
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import 'mavon-editor/dist/css/index.css'
import {useStorePageData} from "@/store/pageData";

let page = {
	colorArr: ['#67C23A', '#409EFF', '#E6A23C', '#F56C6C', '#909399', '#303133'],
	userHeadColor: {},
}

// 页面展示相关
let wikiPage = ref({});
let wikiPageAuth = ref({});
let pageContent = ref({});
let pageFileList = ref([]);
let selfUserId = ref(0);
let uploadFileList = ref([]);
let uploadFormData = ref({pageId: 0});
let zanUserDialogVisible = ref(false);
let zanUserList = ref([]);
let parentPath = ref({});
// 手机扫码
let qrCodeUrl = ref('');
let mobileScanDialogVisible = ref(false);
let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
// 页面权限
let pageAuthDialogVisible = ref(false);
let pageAuthUserList = ref([]);
let searchUserList = ref([]);
let pageAuthNewUser = ref('');
let pageAuthUserLoading = ref(false);
// 右侧标签页
let actionTabVisible = ref(false);
let actionTabActiveName = ref('comment');
let pageHistoryDetail = ref('');
let pageShowDetail = ref('');
let pageHistoryChoice = ref({});
let pageHistoryList = ref([]);
let pageHistoryPageNum = ref(1);
// 左侧导航菜单
let navigationList = ref([]);
// 大图预览
let previewInitialIndex = ref(0);
let showImagePreview = ref(false);
let showImagePreviewList = ref([]);
let markdownToolbars = ref({fullscreen: true, readmodel: true,});
// 下载为Word
let downloadFormParam = ref({url: 'zyplayer-doc-wiki/page/download', param: {},});

let route = useRoute();
let router = useRouter();

let storePage = useStorePageData();

const props = defineProps({
	spaceInfo: Object,
});
let emit = defineEmits(['switchSpace', 'changeExpandedKeys', 'loadPageList']);
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
onMounted(() => {
	initQueryParam(route);
});

const editWiki = () => {
	let param = {pageId: parentPath.value.pageId}
	pageApi.pageLock(param).then(() => {
		router.push({
			path: '/page/edit',
			query: {pageId: parentPath.value.pageId}
		})
	})
}
const getSearchUserList = (query) => {
	if (query == '') return
	pageAuthUserLoading.value = true
	userApi.getUserBaseInfo({search: query}).then((json) => {
		searchUserList.value = json.data || []
		pageAuthUserLoading.value = false
	})
}
const handleMoreCommand = (val) => {
	if (val == 'editAuth') {
		editWikiAuth()
	} else if (val == 'deletePage') {
		deleteWikiPage()
	} else if (val == 'showPageHistory') {
		showPageHistory()
	} else if (val == 'showOpenPage') {
		showOpenPage()
	} else if (val == 'showMobileView') {
		showMobileView()
	} else if (val == 'exportWord') {
		exportWord()
	}
}
let downloadFormRef = ref();
const exportWord = () => {
	downloadFormParam.value.param = {
		pageId: wikiPage.value.id,
	}
	setTimeout(() => downloadFormRef.value.submit(), 0)
}
const showOpenPage = () => {
	if (props.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址')
	} else {
		let routeUrl = router.resolve({
			path: '/page/share/view',
			query: {pageId: wikiPage.value.id, space: props.spaceInfo.uuid}
		})
		window.open(routeUrl.href, '_blank')
	}
}
let qrCodeDivRef = ref();
const showMobileView = () => {
	if (props.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址')
	} else {
		let routeUrl = router.resolve({
			path: '/page/share/mobile/view',
			query: {pageId: wikiPage.value.id, space: props.spaceInfo.uuid}
		})
		mobileScanDialogVisible.value = true
		let hostPath = window.location.href.split('#')[0]
		setTimeout(() => {
			qrCodeUrl.value = hostPath + routeUrl.href
			QRCode.toCanvas(qrCodeDivRef.value, qrCodeUrl.value, {
					scale: 5, height: 250, wight: 250,
				}, (error) => {
					if (error) console.error(error)
				}
			)
		}, 0)
	}
}
const addPageAuthUser = () => {
	if (pageAuthNewUser.value.length <= 0) {
		ElMessage.warning('请先选择用户')
		return
	}
	if (
		!!searchUserList.value.find(
			(item) => item.userId == pageAuthNewUser.value
		)
	) {
		pageAuthNewUser.value = ''
		return
	}
	let userName = ''
	for (let i = 0; i < searchUserList.value.length; i++) {
		if (pageAuthNewUser.value == searchUserList.value[i].id) {
			userName = searchUserList.value[i].userName
			break
		}
	}
	pageAuthUserList.value.push({
		userName: userName,
		userId: pageAuthNewUser.value,
		editPage: 0,
		commentPage: 0,
		deletePage: 0,
		pageFileUpload: 0,
		pageFileDelete: 0,
		pageAuthManage: 0,
	})
	pageAuthNewUser.value = ''
}
const editWikiAuth = () => {
	pageAuthNewUser.value = []
	pageAuthUserList.value = []
	let param = {pageId: wikiPage.value.id}
	pageApi.getPageUserAuthList(param).then((json) => {
		pageAuthUserList.value = json.data || []
		pageAuthDialogVisible.value = true
	})
}
const saveUserPageAuth = () => {
	let param = {
		pageId: wikiPage.value.id,
		authList: JSON.stringify(pageAuthUserList.value),
	}
	pageApi.assignPageUserAuth(param).then(() => {
		ElMessage.success('保存成功！')
	})
}
const notOpen = () => {
	ElMessage.warning('暂未开放')
}
const deleteUserPageAuth = (row) => {
	let pageAuthUserList = []
	for (let i = 0; i < pageAuthUserList.value.length; i++) {
		let item = pageAuthUserList.value[i]
		if (item.userId != row.userId) {
			pageAuthUserList.push(pageAuthUserList.value[i])
		}
	}
	pageAuthUserList.value = pageAuthUserList
}
const deleteWikiPage = () => {
	ElMessageBox.confirm('确定要删除此页面及其所有子页面吗？', '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning',
		})
		.then(() => {
			let param = {pageId: wikiPage.value.id}
			pageApi.pageDelete(param).then(() => {
				// 重新加载左侧列表，跳转到展示页面
				emit('loadPageList');
				router.push({
					path: '/home',
					query: {spaceId: wikiPage.value.spaceId}
				})
			})
		})
		.catch(() => {
		})
}
const actionTabClick = (tab) => {
	if (tab.name === 'comment') {
		showCommentWiki()
	} else if (tab.name === 'history') {
		showPageHistory()
	}
}
const closeActionTab = () => {
	actionTabVisible.value = false
	clearHistory()
	computeNavigationWidth()
}
const showCommentWiki = () => {
	actionTabVisible.value = true
	actionTabActiveName.value = 'comment'
	computeNavigationWidth()
}
const showPageHistory = () => {
	actionTabVisible.value = true
	actionTabActiveName.value = 'history'
	clearHistory()
	computeNavigationWidth()
}
let navigationRef = ref();
const computeNavigationWidth = () => {
	setTimeout(() => {
		if (navigationRef.value) {
			navigationRef.value.computeNavigationWidth()
		}
	}, 100)
}
const getPageHistoryByScroll = () => {
	if (pageHistoryPageNum.value <= 0) {
		return
	}
	pageHistoryPageNum.value++
	getPageHistory(wikiPage.value.id, pageHistoryPageNum.value)
}
const getPageHistory = (pageId, pageNum) => {
	if (pageNum == 1) {
		pageHistoryList.value = []
		pageHistoryPageNum.value = 1
	}
	let param = {pageId: pageId, pageNum: pageNum}
	pageApi.pageHistoryList(param).then((json) => {
		let historyList = json.data || []
		if (historyList.length <= 0) {
			pageHistoryPageNum.value = 0
		} else {
			historyList.forEach((item) => (item.loading = 0))
			pageHistoryList.value = pageHistoryList.value.concat(historyList)
		}
	})
}
const historyClick = (history) => {
	if (pageHistoryChoice.value.id === history.id && !!pageHistoryDetail.value) {
		return
	}
	pageHistoryChoice.value.loading = 0
	pageHistoryChoice.value = history
	// 缓存一下，但如果历史页面多了而且很大就占内存，也可以每次去拉取，先这样吧
	if (history.content) {
		history.loading = 2
		pageHistoryDetail.value = history.content
		pageShowDetail.value = history.content
		setTimeout(() => {
			previewPageImage()
			createNavigationHeading()
		}, 500)
	} else {
		history.loading = 1
		pageApi
			.pageHistoryDetail({id: history.id})
			.then((json) => {
				history.loading = 2
				history.content = json.data || '--'
				if (wikiPage.value.editorType === 2) {
					history.content = mavonEditor.getMarkdownIt().render(history.content)
				}
				pageHistoryDetail.value = history.content
				pageShowDetail.value = history.content
				setTimeout(() => {
					previewPageImage()
					createNavigationHeading()
				}, 500)
			})
			.catch(() => {
				history.loading = 3
			})
	}
}
const clearHistory = () => {
	pageHistoryChoice.value.loading = 0
	pageHistoryDetail.value = ''
	pageHistoryChoice.value = {}
	pageHistoryList.value.forEach((item) => (item.loading = 0))
	pageShowDetail.value = pageContent.value.content
}
const computeFileSize = (fileSize) => {
	return unitUtil.computeFileSize(fileSize)
}
const loadPageDetail = (pageId) => {
	clearHistory()
	pageApi.pageDetail({id: pageId}).then((json) => {
		let result = json.data || {}
		let wikiPageRes = result.wikiPage || {}
		wikiPageRes.selfZan = result.selfZan || 0
		wikiPage.value = wikiPageRes
		pageContent.value = result.pageContent || {}
		pageFileList.value = result.fileList || []
		selfUserId.value = result.selfUserId || 0
		uploadFormData.value = {pageId: wikiPage.value.id}
		wikiPageAuth.value = {
			canEdit: result.canEdit,
			canDelete: result.canDelete,
			canUploadFile: result.canUploadFile,
			canDeleteFile: result.canDeleteFile,
			canConfigAuth: result.canConfigAuth,
		}
		if (wikiPage.value.editorType === 2) {
			pageContent.value.content = mavonEditor.getMarkdownIt().render(pageContent.value.content)
		}
		pageShowDetail.value = pageContent.value.content
		// 修改标题
		document.title = wikiPageRes.name || 'WIKI-内容展示'
		// 修改最后点击的项，保证刷新后点击编辑能展示编辑的项
		// if (!lastClickNode.value.id) {
		// 	lastClickNode.value = {id: wikiPage.id, nodePath: wikiPage.name};
		// }
		// 调用父方法切换选择的空间
		emit('switchSpace', wikiPage.value.spaceId);
		// 调用父方法展开目录树
		emit('changeExpandedKeys', pageId);
		setTimeout(() => {
			previewPageImage()
			createNavigationHeading()
		}, 500);
		storePage.pageInfo = wikiPageRes;
	})
	getPageHistory(pageId, 1)
}
let wikiTitleRef = ref();
const createNavigationHeading = () => {
	let navigationListVal = htmlUtil.createNavigationHeading()
	// 标题加到导航里面去
	if (navigationListVal.length > 0) {
		let wikiTile = wikiPage.value.name || 'WIKI-内容展示'
		navigationListVal.unshift({
			level: 1,
			node: wikiTitleRef.value,
			text: wikiTile,
		})
	}
	navigationList.value = navigationListVal;
}
const closeImagePreview = () => {
	showImagePreview.value = false
}
let pageContentRef = ref();
const previewPageImage = () => {
	const imgArr = []
	const imgSelector = pageContentRef.value.querySelectorAll('img')
	imgSelector.forEach((item, index) => {
		imgArr.push(item.src)
		item.onclick = () => {
			previewInitialIndex.value = index
			showImagePreviewList.value = imgArr
			showImagePreview.value = true
		}
	})
}
const zanPage = (yn) => {
	let param = {yn: yn, pageId: wikiPage.value.id}
	pageApi.updatePageZan(param).then(() => {
		wikiPage.value.selfZan = yn
		wikiPage.value.zanNum = wikiPage.value.zanNum + (yn == 1 ? 1 : -1)
	})
}
const showZanPageUser = () => {
	zanUserDialogVisible.value = true
	zanUserList.value = []
	let param = {pageId: wikiPage.value.id}
	pageApi.pageZanList(param).then((json) => {
		zanUserList.value = json.data
	})
}
const uploadFileError = (err) => {
	ElMessage.error('上传失败，' + err)
}
const uploadFileSuccess = (response) => {
	if (response.errCode == 200) {
		pageFileList.value.push(response.data)
		ElMessage.success('上传成功！')
	} else {
		ElMessage('上传失败：' + (response.errMsg || '未知错误'))
	}
}
const deletePageFile = (row) => {
	ElMessageBox.confirm('确定要删除此文件吗？', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		let param = {id: row.id}
		pageApi.deletePageFile(param).then(() => {
			let pageFileList = []
			for (let i = 0; i < pageFileList.value.length; i++) {
				if (pageFileList.value[i].id != row.id) {
					pageFileList.push(pageFileList.value[i])
				}
			}
			pageFileList.value = pageFileList
		})
	})
}
const getUserHeadBgColor = (userId) => {
	let color = page.userHeadColor[userId]
	if (!color) {
		color =
			page.colorArr[Math.ceil(Math.random() * page.colorArr.length) - 1]
		page.userHeadColor[userId] = color
	}
	return color
}
const initQueryParam = (to) => {
	parentPath.value = {pageId: to.query.pageId}
	if (!!parentPath.value.pageId) {
		loadPageDetail(parentPath.value.pageId)
	}
}
</script>

<style lang="scss" scoped>
.page-show-vue {
  .wiki-page-content {
    margin-top: 20px;
  }
}
</style>

<style>
@import '../../assets/lib/wangEditor.css';

.page-show-vue {
	height: 100%;
	overflow: hidden;
}

.page-show-vue .icon-collapse {
	float: left;
	font-size: 25px;
	color: #aaa;
	margin-top: 8px;
	cursor: pointer;
}

.page-show-vue .icon-collapse:hover {
	color: #eee;
}

.page-show-vue .wiki-title {
	font-size: 2em;
	text-align: center;
	font-weight: bold;
}

.page-show-vue .create-user-time {
	margin-right: 20px;
}

.page-show-vue .wiki-author {
	font-size: 14px;
	color: #888;
	padding: 20px 0;
	height: 40px;
	line-height: 40px;
}

.page-show-vue .wiki-page-content img {
	cursor: pointer;
	max-width: 100%;
}

.page-show-vue .wiki-page-content img:hover {
	box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.3);
}

.page-show-vue .upload-page-file .el-upload-list {
	display: none;
}

.page-show-vue .is-link {
	color: #1e88e5;
	cursor: pointer;
}

.page-show-vue #newPageContentDiv .w-e-text-container {
	height: 600px !important;
}

.page-show-vue .head {
	float: left;
	background-color: #ccc;
	border-radius: 50%;
	margin-right: 10px;
	width: 45px;
	height: 45px;
	line-height: 45px;
	text-align: center;
	color: #fff;
}

.page-show-vue .el-tabs__header {
	margin: 0;
}

.page-show-vue .el-tabs__nav-wrap {
	padding: 0 20px;
}

.page-show-vue .close-action-tab {
	position: absolute;
	right: 15px;
	top: 12px;
	cursor: pointer;
	z-index: 1;
}

.page-show-vue .action-tab-box {
	height: calc(100vh - 120px);
	overflow: auto;
	padding: 20px 10px;
}

.page-show-vue .action-box-empty {
	text-align: center;
	padding-top: 30px;
	color: #888;
	font-size: 14px;
}

.page-show-vue .history-item {
	height: 55px;
	line-height: 25px;
	cursor: pointer;
	vertical-align: middle;
}

.page-show-vue .history-loading-status {
	margin-left: 5px;
	color: #67c23a;
}

.page-show-vue .history-loading-status.el-icon-circle-close {
	color: #f56c6c;
}

.page-show-vue .el-timeline {
	padding-inline-start: 0;
}

.mobile-qr {
	width: 250px;
	height: 250px;
	border: 1px solid #ccc;
	display: inline-block;
	border-radius: 4px;
	margin-bottom: 10px;
	padding: 5px;
}
</style>
