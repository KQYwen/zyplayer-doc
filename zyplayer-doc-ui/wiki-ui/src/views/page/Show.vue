<template>
	<div class="page-show-vue" v-if="storePage.pageInfo.editorType !== 0">
		<el-row type="border-card" style="height: 100%">
			<el-col :span="storeDisplay.commentShow ? 18 : 24" style="padding: 20px;border-right: 1px solid #f1f1f1;height: 100%;overflow: auto;">
				<el-row>
					<el-col :span="navigationList.length > 0 ? 18 : 24">
						<div style="max-width: 1000px; margin: 0 auto; padding-left: 10px">
							<div class="wiki-title" ref="wikiTitleRef">{{ storePage.pageInfo.name }}</div>
							<div ref="pageContentRef" class="wiki-page-content">
								<div v-html="pageShowDetail" class="markdown-body" v-if="wikiPage.editorType == 2" v-highlight></div>
								<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
							</div>
							<PageZan></PageZan>
						</div>
					</el-col>
					<el-col :span="navigationList.length > 0 ? 6 : 0" v-if="navigationList.length > 0">
						<Navigation :heading="navigationList"></Navigation>
					</el-col>
				</el-row>
			</el-col>
			<el-col :span="6" style="height: 100%" v-show="storeDisplay.commentShow">
				<el-icon @click="closeActionTab" class="close-action-tab">
					<el-icon-close/>
				</el-icon>
				<el-tabs v-model="storeDisplay.commentActiveTab">
					<el-tab-pane label="评论" name="comment">
						<Comment/>
					</el-tab-pane>
					<el-tab-pane label="附件" name="annex">
						<Annex/>
					</el-tab-pane>
					<el-tab-pane label="修改历史" name="history">
						<PageHistory
							:pageHistoryList="pageHistoryList"
							:pageHistoryChoice="pageHistoryChoice"
							:pageHistoryDetail="pageHistoryDetail"
							@historyClickHandle="historyClickHandle"
							@previewPageImage="previewPageImage"
							@createNavigationHeading="createNavigationHeading"/>
					</el-tab-pane>
				</el-tabs>
			</el-col>
		</el-row>
		<el-image-viewer
				v-if="showImagePreview"
				:url-list="showImagePreviewList"
				:initial-index="previewInitialIndex"
				@close="closeImagePreview"
				hide-on-click-modal
		/>
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
import Annex from './show/Annex.vue'
import PageHistory from './show/PageHistory.vue'
import Comment from './show/Comment.vue'
import PageZan from './show/PageZan.vue'
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import 'mavon-editor/dist/css/index.css'
import {useStorePageData} from "@/store/pageData";
import {useStoreDisplay} from "@/store/wikiDisplay";

let page = {
	colorArr: ['#67C23A', '#409EFF', '#E6A23C', '#F56C6C', '#909399', '#303133'],
	userHeadColor: {},
}

// 页面展示相关
let wikiPage = ref({});
let wikiPageAuth = ref({});
let pageContent = ref({});
let selfUserId = ref(0);
let uploadFileList = ref([]);
let parentPath = ref({});
// 手机扫码
let qrCodeUrl = ref('');
let mobileScanDialogVisible = ref(false);
// 页面权限
let pageAuthDialogVisible = ref(false);
let pageAuthUserList = ref([]);
let searchUserList = ref([]);
let pageAuthNewUser = ref('');
let pageAuthUserLoading = ref(false);
// 右侧标签页
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

let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();

const props = defineProps({
	spaceInfo: Object,
});
let emit = defineEmits(['switchSpace', 'changeExpandedKeys', 'loadPageList']);
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
onMounted(() => {
	storeDisplay.currentPage = 'view';
	initQueryParam(route);
});

const getSearchUserList = (query) => {
	if (query == '') return
	pageAuthUserLoading.value = true
	userApi.getUserBaseInfo({search: query}).then((json) => {
		searchUserList.value = json.data || []
		pageAuthUserLoading.value = false
	})
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
	let pageAuthUserList = [];
	for (let i = 0; i < pageAuthUserList.value.length; i++) {
		let item = pageAuthUserList.value[i];
		if (item.userId !== row.userId) {
			pageAuthUserList.push(pageAuthUserList.value[i]);
		}
	}
	pageAuthUserList.value = pageAuthUserList;
}
const closeActionTab = () => {
	storeDisplay.commentShow = false;
	clearHistory();
}
const getPageHistoryByScroll = () => {
	if (pageHistoryPageNum.value <= 0) {
		return;
	}
	pageHistoryPageNum.value++;
	getPageHistory(wikiPage.value.id, pageHistoryPageNum.value);
}
const getPageHistory = (pageId, pageNum) => {
	if (pageNum === 1) {
		pageHistoryList.value = [];
		pageHistoryPageNum.value = 1;
	}
	let param = {pageId: pageId, pageNum: pageNum};
	pageApi.pageHistoryList(param).then((json) => {
		let historyList = json.data || [];
		if (historyList.length <= 0) {
			pageHistoryPageNum.value = 0;
		} else {
			historyList.forEach((item) => (item.loading = 0));
			pageHistoryList.value = pageHistoryList.value.concat(historyList);
		}
	})
}
const historyClickHandle = (history) => {
	pageHistoryChoice.value.loading = 0;
	pageHistoryChoice.value = history;
	pageHistoryDetail.value = history.content;
	pageShowDetail.value =history.content;
}
const clearHistory = () => {
	pageHistoryChoice.value.loading = 0;
	pageHistoryDetail.value = '';
	pageHistoryChoice.value = {};
	pageHistoryList.value.forEach((item) => (item.loading = 0));
	pageShowDetail.value = pageContent.value.content;
}
const computeFileSize = (fileSize) => {
	return unitUtil.computeFileSize(fileSize)
}
const loadPageDetail = (pageId) => {
	clearHistory()
	pageApi.pageDetail({id: pageId}).then((json) => {
		let result = json.data || {};
		let wikiPageRes = result.wikiPage || {};
		wikiPageRes.selfZan = result.selfZan || 0;
		wikiPage.value = wikiPageRes;
		pageContent.value = result.pageContent || {};
		storePage.fileList = result.fileList || [];
		selfUserId.value = result.selfUserId || 0;
		wikiPageAuth.value = {
			canEdit: result.canEdit,
			canDelete: result.canDelete,
			canUploadFile: result.canUploadFile,
			canDeleteFile: result.canDeleteFile,
			canConfigAuth: result.canConfigAuth,
		}
		if (wikiPage.value.editorType === 2) {
			pageContent.value.content = mavonEditor.getMarkdownIt().render(pageContent.value.content);
		}
		pageShowDetail.value = pageContent.value.content;
		// 修改标题
		document.title = wikiPageRes.name || 'WIKI-内容展示';
		// 修改最后点击的项，保证刷新后点击编辑能展示编辑的项
		// if (!lastClickNode.value.id) {
		// 	lastClickNode.value = {id: wikiPage.id, nodePath: wikiPage.name};
		// }
		// 调用父方法切换选择的空间
		emit('switchSpace', wikiPage.value.spaceId);
		// 调用父方法展开目录树
		emit('changeExpandedKeys', pageId);
		setTimeout(() => {
			if (storePage.pageInfo.editorType !== 0){
				previewPageImage();
			}
			createNavigationHeading();
		}, 500);
		storePage.pageInfo = wikiPageRes;
		storePage.pageAuth = wikiPageAuth.value;
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

<style lang="scss">
.page-show-vue {
  height: 100%;
  overflow: hidden;

  .wiki-page-content {
	ol {
	  list-style: decimal;
	}

	ul {
	  list-style: disc;
	}
  }
}
</style>

<style>
@import '../../assets/lib/wangEditor.css';

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

.page-show-vue .markdown-body table {
	display: table;
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
