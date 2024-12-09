<template>
	<div class="page-show-vue" v-if="storePage.pageInfo.editorType !== 0">
		<a-row class="view-body-comment-box">
			<a-col id="pageContentScrollBox" flex="auto" class="view-body-outer-box">
				<div class="view-body-box">
					<div class="wiki-title" ref="wikiTitleRef">{{ storePage.pageInfo.name }}</div>
					<div id="pageContentBox" ref="pageContentRef" class="wiki-page-content">
						<div v-if="wikiPage.editorType === 2" v-html="pageContentShow" class="page-view-content markdown-body" v-highlight></div>
						<div v-else v-html="pageContentShow" class="page-view-content wang-editor-body"></div>
					</div>
					<PageZan></PageZan>
				</div>
				<Navigation :heading="navigationList"></Navigation>
			</a-col>
			<a-col v-if="storeDisplay.commentShow" flex="280px">
				<a-tabs v-model:activeKey="actionTabActiveName" class="action-tabs-box">
					<a-tab-pane tab="评论" key="comment">
						<Comment></Comment>
					</a-tab-pane>
					<a-tab-pane tab="附件" key="files">
						<Files/>
					</a-tab-pane>
					<a-tab-pane tab="修改历史" key="history">
						<PageHistory :history="pageHistoryList" @choice="historyClickHandle"/>
					</a-tab-pane>
					<template #rightExtra>
						<el-tooltip content="关闭" placement="top">
							<a-button @click="closeActionTab" type="text" :icon="h(CloseOutlined)"></a-button>
						</el-tooltip>
					</template>
				</a-tabs>
			</a-col>
		</a-row>
		<ImageViewer ref="imageViewerRef"/>
	</div>
</template>

<script setup>
import { CloseOutlined } from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, defineProps, h, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import { ElMessageBox, ElMessage, ElNotification } from 'element-plus';
import QRCode from 'qrcode';
import unitUtil from '../../assets/lib/UnitUtil.js';
import htmlUtil from '../../assets/lib/HtmlUtil.js';
import pageApi from '../../assets/api/page';
import userApi from '../../assets/api/user';
import Navigation from './show/Navigation.vue';
import Files from './show/Files.vue';
import PageHistory from './show/PageHistory.vue';
import Comment from './show/Comment.vue';
import PageZan from './show/PageZan.vue';
import {mavonEditor} from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
import {useStorePageData} from "@/store/pageData";
import {useStoreDisplay} from "@/store/wikiDisplay";
import ImageViewer from "@/components/base/ImageViewer.vue";

let route = useRoute();
let router = useRouter();
let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();
const props = defineProps({
	spaceInfo: Object,
});
let emit = defineEmits(['switchSpace', 'changeExpandedKeys', 'loadPageList']);
onMounted(() => {
	storeDisplay.currentPage = 'view';
	initQueryParam(route);
});
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
// 页面展示相关
let wikiPage = ref({});
let wikiPageAuth = ref({});
let pageContent = ref('');
let pageContentShow = ref('');
let pageHistoryList = ref([]);
let navigationList = ref([]);
let actionTabActiveName = ref('comment');
let imageViewerRef = ref();
const previewPageImage = () => {
	if (imageViewerRef.value) {
		imageViewerRef.value.initViewer(pageContentRef.value);
	}
}
const closeActionTab = () => {
	storeDisplay.commentShow = false;
	clearHistory();
}
const getPageHistory = (pageId) => {
	pageHistoryList.value = [];
	pageApi.pageHistoryList({pageId: pageId}).then((json) => {
		let historyList = json.data || [];
		historyList.forEach((item) => (item.loading = 0));
		pageHistoryList.value = historyList;
	});
}
const historyClickHandle = (history) => {
	pageContentShow.value = history.content;
	afterLoadPage();
}
const clearHistory = () => {
	pageHistoryList.value.forEach((item) => (item.loading = 0));
	pageContentShow.value = pageContent.value;
	afterLoadPage();
}
const afterLoadPage = () => {
	setTimeout(() => {
		previewPageImage();
		createNavigationHeading();
	}, 500);
}
const loadPageDetail = (pageId) => {
	clearHistory();
	pageContent.value = '';
	pageApi.pageDetail({id: pageId}).then(async (json) => {
		let result = json.data || {};
		let wikiPageRes = result.wikiPage || {};
		wikiPageRes.selfZan = result.selfZan || 0;
		wikiPageRes.zanNum = wikiPageRes.zanNum || 0;
		wikiPage.value = wikiPageRes;
		storePage.fileList = result.fileList || [];
		wikiPageAuth.value = {
			canEdit: result.canEdit,
			canDelete: result.canDelete,
			canUploadFile: result.canUploadFile,
			canDeleteFile: result.canDeleteFile,
			canConfigAuth: result.canConfigAuth,
		}
		if (result.pageContent) {
			pageContent.value = result.pageContent.content || '';
		}
		if (wikiPage.value.editorType === 2) {
			pageContent.value = mavonEditor.getMarkdownIt().render(pageContent.value);
		}
		pageContentShow.value = pageContent.value;
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
		storePage.pageInfo = wikiPageRes;
		storePage.pageAuth = wikiPageAuth.value;
		afterLoadPage();
	});
	getPageHistory(pageId);
}
let wikiTitleRef = ref();
const createNavigationHeading = () => {
	let navigationListVal = htmlUtil.createNavigationHeading();
	// 标题加到导航里面去
	if (navigationListVal.length > 0) {
		let wikiTile = wikiPage.value.name || 'WIKI-内容展示'
		navigationListVal.unshift({
			level: 1,
			node: wikiTitleRef.value,
			text: wikiTile,
		});
	}
	navigationList.value = navigationListVal;
}
let spaceId = '';
let pageId = '';
let pageContentRef = ref();
const initQueryParam = (to) => {
	spaceId = parseInt(to.params.spaceId);
	pageId = parseInt(to.params.pageId);
	if (!!pageId) {
		loadPageDetail(pageId);
	}
}
</script>

<style lang="scss">
.page-show-vue {
	height: 100%;
	overflow: hidden;

	.view-body-comment-box {
		height: 100%;
		flex-flow: row nowrap;

		.view-body-outer-box {
			height: 100%;
			overflow: auto;
			padding: 30px 20px;
			position: relative;
			border-right: 1px solid #eee;

			.view-body-box {
				max-width: 840px;
				margin: 0 auto;

				.wiki-page-content {
					margin-top: 30px;
				}
			}
		}

		.ant-tabs-nav {
			padding: 0 15px;
		}
	}
}
</style>

<style>
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
