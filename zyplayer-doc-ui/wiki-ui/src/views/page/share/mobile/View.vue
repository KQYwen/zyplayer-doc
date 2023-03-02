<template>
	<div class="mobile-share-view-vue">
		<van-nav-bar :title="wikiPage.name" class="header">
			<template v-slot:left>
				<van-icon name="wap-nav" size="20" @click="popupShowChange"></van-icon>
			</template>
		</van-nav-bar>
		<el-row type="border-card" class="main">
			<div style="max-width: 950px; margin: 0 auto">
				<div class="wiki-author">
					<span v-if="wikiPage.updateTime">最后修改：{{ wikiPage.updateTime }}</span>
					<span v-else>创建时间：{{ wikiPage.createTime }}</span>
				</div>
				<div class="wiki-files">
					<el-table v-show="pageFileList.length > 0" :data="pageFileList" border style="width: 100%; margin-bottom: 5px">
						<el-table-column label="文件名">
							<template v-slot="scope">
								<a target="_blank" :href="scope.row.fileUrl">{{scope.row.fileName }}</a>
							</template>
						</el-table-column>
						<el-table-column label="文件大小">
							<template v-slot="scope">{{computeFileSize(scope.row.fileSize) }}</template>
						</el-table-column>
						<el-table-column prop="createTime" label="创建时间" width="180px"></el-table-column>
						<el-table-column prop="downloadNum" label="下载次数" width="80px"></el-table-column>
					</el-table>
				</div>
				<div ref="pageContentRef" class="wiki-page-content">
					<div v-html="pageShowDetail" class="markdown-body" v-if="wikiPage.editorType == 2"></div>
					<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
				</div>
			</div>
		</el-row>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from '../../../../assets/api/page'
import {mavonEditor} from 'mavon-editor'
import {ImagePreview} from 'vant'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import 'mavon-editor/dist/css/index.css'

let spaceUuid = ref('');
let nowPageId = ref('');
// 页面展示相关
let wikiPage = ref({});
let pageFileList = ref([]);
let pageShowDetail = ref('');
// 大图预览
let previewInitialIndex = ref(0);
let showImagePreviewList = ref([]);

let emit = defineEmits(['popupShow']);
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
let route = useRoute();
let router = useRouter();
onMounted(() => {
	initQueryParam(route);
});
const popupShowChange = () => {
	emit('popupShow', true)
}
const loadPageDetail = (pageId) => {
	let param = {pageId: pageId, space: spaceUuid.value}
	pageApi.openPageDetail(param).then((json) => {
		let wikiPageRes = json.data.wikiPage || {}
		wikiPageRes.selfZan = json.data.selfZan || 0
		wikiPage.value = wikiPageRes
		let pageContent = json.data.pageContent || {}
		pageFileList.value = json.data.fileList || []
		if (wikiPage.value.editorType === 2) {
			pageContent.content = mavonEditor.getMarkdownIt().render(pageContent.content)
		}
		pageShowDetail.value = pageContent.content
		document.title = wikiPageRes.name || 'WIKI-内容展示'
		setTimeout(() => previewPageImage(), 500)
	})
}
const initQueryParam = (to) => {
	spaceUuid.value = to.query.space
	nowPageId.value = to.query.pageId
	if (!!nowPageId.value) {
		loadPageDetail(nowPageId.value)
	}
}
const computeFileSize = (fileSize) => {
	if (!fileSize) {
		return '-'
	}
	let size = ''
	if (fileSize < 0.1 * 1024) {
		size = fileSize.toFixed(2) + 'B'
	} else if (fileSize < 0.1 * 1024 * 1024) {
		size = (fileSize / 1024).toFixed(2) + 'KB'
	} else if (fileSize < 0.1 * 1024 * 1024 * 1024) {
		size = (fileSize / (1024 * 1024)).toFixed(2) + 'MB'
	} else {
		size = (fileSize / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
	}
	let sizeStr = size + ''
	let index = sizeStr.indexOf('.')
	let dou = sizeStr.substr(index + 1, 2)
	if (dou == '00') {
		return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
	}
	return size
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
			ImagePreview({
				images: imgArr,
				startPosition: index,
			})
		}
	})
}
</script>

<style>
@import '../../../../assets/lib/wangEditor.css';

.mobile-share-view-vue {
}

.mobile-share-view-vue .wiki-title {
	font-size: 2em;
	text-align: center;
	font-weight: bold;
}

.mobile-share-view-vue .wiki-author {
	font-size: 14px;
	color: #888;
	height: 40px;
	line-height: 40px;
}

.mobile-share-view-vue .wiki-page-content img {
	cursor: pointer;
	max-width: 100%;
}

.mobile-share-view-vue .wiki-page-content img:hover {
	box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.3);
}

.mobile-share-view-vue .upload-page-file .el-upload-list {
	display: none;
}

.mobile-share-view-vue .is-link {
	color: #1e88e5;
	cursor: pointer;
}

.mobile-share-view-vue .header {
	width: 100%;
	height: 46px;
}

.mobile-share-view-vue .main {
	position: absolute;
	top: 46px;
	bottom: 0;
	right: 0;
	left: 0;
	overflow: auto;
	padding: 10px;
}

.mobile-share-view-vue .footer {
	width: 100%;
	height: 26px;
	position: fixed;
	bottom: 0;
}
</style>
