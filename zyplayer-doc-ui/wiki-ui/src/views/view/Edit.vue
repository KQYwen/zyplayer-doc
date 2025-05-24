<template>
	<div style="height: 100%;" class="page-edit-vue">
		<el-row class="fake-header">
			<el-col style="flex: 0 0 45px;" class="collapse-box">
				<a-button @click="turnLeftCollapse" v-if="storeDisplay.showMenu" type="text" :icon="h(MenuFoldOutlined)"></a-button>
				<a-button @click="turnLeftCollapse" v-else type="text" :icon="h(MenuUnfoldOutlined)"></a-button>
			</el-col>
			<el-col style="flex: 1 1 auto;">
				<el-input v-model="pageTitleEdit" :maxlength="40" placeholder="请输入标题" class="page-title-input" ></el-input>
			</el-col>
			<el-col style="flex: 0 0 190px;text-align: right;">
				<a-button @click="createWikiSave(1)" :icon="h(SaveOutlined)" :loading="saveContentLoading" type="primary">保存</a-button>
				<a-button @click="createWikiCancel" :icon="h(IconParkBack)" style="margin-left: 10px;">取消</a-button>
			</el-col>
		</el-row>
		<div style="box-sizing: border-box;background: #f5f5f5;overflow: hidden">
			<div v-if="wikiPage.editorType === 1">
				<WangEditor ref="wangEditorRef" :pageId="pageId"></WangEditor>
			</div>
			<div v-else-if="wikiPage.editorType === 2" style="padding: 5px 8px 5px; background: #fff;">
				<mavonEditor ref="mavonEditorRef" v-model="markdownContent" :toolbars="toolbars" :externalLink="false"
				             @save="createWikiSave(0)" @imgAdd="addMarkdownImage" placeholder="请录入文档内容"
				             class="page-content-editor wang-editor-body" style="height: calc(100vh - 80px);z-index: 1;"/>
			</div>
		</div>
	</div>
</template>

<script setup>
import {Back as IconParkBack} from '@icon-park/vue-next'
import {MenuFoldOutlined, MenuUnfoldOutlined, SaveOutlined} from '@ant-design/icons-vue';
import {onBeforeUnmount, ref, onMounted, onUnmounted, watch, defineProps, h, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus';
import {
	DocumentChecked as ElIconDocumentChecked,
	Fold as ElIconFold,
	Expand as ElIconExpand,
	Check as ElIconCheck,
	Back as ElIconBack,
} from '@element-plus/icons-vue'
import pageApi from '../../assets/api/page';
import {mavonEditor} from 'mavon-editor';
import 'mavon-editor/dist/markdown/github-markdown.min.css';
import 'mavon-editor/dist/css/index.css';
import axios from 'axios';
import WangEditor from './editor/WangEditor.vue';
import {useStoreSpaceData} from "@/store/spaceData";
import {useStorePageData} from "@/store/pageData";
import {useStoreDisplay} from "@/store/wikiDisplay";

// 编辑相关
let pageTitleEdit = ref('');
let wikiPage = ref({});
let isUnlock = ref(false);
// 页面ID，有值代表编辑
let spaceId = '';
let pageId = ref('');
let markdownContent = ref('');
let fileUploadUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload');
let toolbars = {
	bold: true, // 粗体
	italic: true, // 斜体
	header: true, // 标题
	underline: true, // 下划线
	strikethrough: true, // 中划线
	mark: true, // 标记
	superscript: true, // 上角标
	subscript: true, // 下角标
	quote: true, // 引用
	ol: true, // 有序列表
	ul: true, // 无序列表
	link: true, // 链接
	imagelink: true, // 图片链接
	code: true, // code
	table: true, // 表格
	fullscreen: true, // 全屏编辑
	readmodel: true, // 沉浸式阅读
	/* 1.3.5 */
	undo: true, // 上一步
	redo: true, // 下一步
	trash: true, // 清空
	save: true, // 保存（触发events中的save事件）
	/* 1.4.2 */
	navigation: true, // 导航目录
	/* 2.1.8 */
	alignleft: true, // 左对齐
	aligncenter: true, // 居中
	alignright: true, // 右对齐
	/* 2.2.1 */
	subfield: true, // 单双栏模式
	preview: true, // 预览
};
let storeSpace = useStoreSpaceData();
let storePage = useStorePageData();
let storeDisplay = useStoreDisplay();
let route = useRoute();
let router = useRouter();
onMounted(() => {
	initQueryParam(route);
	storeDisplay.showHeader = false;
	window.onunload = () => unlockPage();
	window.onbeforeunload = () => unlockPage();
});
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
onUnmounted(() => {
	unlockPage();
});
const turnLeftCollapse = () => {
	storeDisplay.showMenu = !storeDisplay.showMenu;
}
const unlockPage = () => {
	// 防止各种事件重复调这个接口，只需要调一次就好了
	if (isUnlock.value) return;
	isUnlock.value = true;
	pageApi.pageUnlock({pageId: pageId.value});
}
const createWikiCancel = () => {
	ElMessageBox.confirm('确定要离开编辑页吗？离开后编辑的内容将不会被保存', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '继续编辑',
		type: 'warning',
	}).then(() => {
		unlockPage();
		router.back();
	});
}
let wangEditorRef = ref();
const createWikiSave = (saveAfter) => {
	let content = '', preview = '';
	if (wikiPage.value.editorType === 1) {
		content = wangEditorRef.value.getContent();
		preview = wangEditorRef.value.getPreview();
	} else if (wikiPage.value.editorType === 2) {
		content = markdownContent.value;
		const showContentSelector = mavonEditorRef.value.$el.querySelectorAll('.v-show-content');
		if (showContentSelector && showContentSelector.length > 0) {
			preview = showContentSelector[0].textContent;
		}
	}
	if (!pageTitleEdit.value) {
		ElMessage.warning('标题不能为空');
		return;
	}
	let param = {id: pageId.value, name: pageTitleEdit.value, content: content, preview: preview};
	pageApi.updatePage(param).then((json) => {
		ElMessage.success('保存成功！');
		storePage.eventPageListUpdate = !storePage.eventPageListUpdate;
		if (saveAfter === 1) {
			router.push({path: `/view/${spaceId}/${pageId.value}`});
		}
	});
}
const loadPageDetail = () => {
	pageApi.pageDetail({id: pageId.value}).then(async (json) => {
		wikiPage.value = json.data.wikiPage || {};
		pageTitleEdit.value = wikiPage.value.name;
		let pageContent = json.data.pageContent || {};
		let pageContentVal = pageContent.content || '';
		await nextTick();
		if (wikiPage.value.editorType === 1) {
			wangEditorRef.value.setContent(pageContentVal);
		} else if (wikiPage.value.editorType === 2) {
			markdownContent.value = pageContentVal;
		}
	});
}
const initQueryParam = (to) => {
	spaceId = parseInt(to.params.spaceId);
	pageId.value = parseInt(to.params.pageId);
	if (!spaceId || !pageId.value) {
		return;
	}
	loadPageDetail();
	pageApi.pageLock({pageId: pageId.value}).catch((json) => {
		ElMessageBox.alert(json.errMsg || '未知错误', '错误', {
			confirmButtonText: '确定',
			callback: () => {
				router.back();
			},
		});
	});
}
let mavonEditorRef = ref();
const addMarkdownImage = (pos, file) => {
	let formData = new FormData();
	formData.append('files', file);
	formData.append('pageId', pageId.value);
	axios({
		url: fileUploadUrl.value, method: 'post', data: formData, timeout: 10000,
		headers: {'Content-Type': 'multipart/form-data'}, withCredentials: true
	}).then((res) => {
		let urlObj = res.data.data || {};
		if (urlObj.url) {
			mavonEditorRef.value.$img2Url(pos, urlObj.url);
		} else {
			ElMessage.warning('上传失败，返回数据为空');
		}
	}).catch((e) => {
		ElMessage.warning('上传失败：' + e.message);
	});
}
</script>

<style lang="scss" scoped>
.page-edit-vue {
	.parent-name {
		margin-right: 20px;
		max-width: 300px;
		display: inline-block;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		vertical-align: middle;
	}
}
</style>

<style lang="scss">
.fake-header {
	color: #333;
	height: 50px !important;
	line-height: 50px !important;
	padding: 0 8px 0 8px;
	font-size: 14px;
	background: #fff;

	.fold-btn {
		font-size: 18px;
		padding: 4px 10px;
		margin-left: 6px;
		color: #888 !important;
	}

	.page-title-input {
		padding: 0 6px;
		width: 100%;
		font-size: 20px;
		border-bottom: 1px solid #ddd;

		.el-input__wrapper {
			border: 0;
			box-shadow: unset;
		}
	}

	.title-info-view-right {
		text-align: right;
		margin-top: 10px;
		font-size: 14px;
		color: #454343;

		.split {
			padding: 0 4px;
		}
	}
}

.page-edit-vue {
	.page-content-editor {
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
.page-edit-vue .icon-collapse {
	float: left;
	font-size: 25px;
	color: #aaa;
	margin-top: 8px;
	cursor: pointer;
}

.page-edit-vue .icon-collapse:hover {
	color: #eee;
}

.page-edit-vue .wiki-title {
	font-size: 20px;
}

.page-edit-vue .wiki-author {
	font-size: 14px;
	color: #888;
	padding: 20px 0;
	height: 40px;
	line-height: 40px;
}

.page-edit-vue .wiki-content {
	font-size: 14px;
}

.page-edit-vue .wiki-content.w-e-text {
	overflow-y: auto;
}

.page-edit-vue .upload-page-file .el-upload-list {
	display: none;
}

.page-edit-vue .is-link {
	color: #1e88e5;
	cursor: pointer;
}

.page-edit-vue #newPageContentDiv .w-e-text-container {
	height: 100% !important;
}

.page-edit-vue .comment-box .head {
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

.page-edit-vue .page-content-editor {
	padding: 10px 0;
}

.page-edit-vue .markdown-body table {
	display: table;
}
</style>
