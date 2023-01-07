<template>
	<div style="height: 100%" class="page-edit-vue">
		<div style="box-sizing: border-box; background: #f5f5f5; overflow: hidden">
			<div style="padding: 8px; font-size: 14px; background: #fff">
				<el-row>
					<el-col :span="16">
						<template v-if="pageId">
							<span>编辑方式：</span>
							<el-select v-model="wikiPageEdit.editorType" v-on:change="editorTypeChange" :disabled="!!pageId" size="small">
								<el-option label="Markdown" :value="2"></el-option>
								<el-option label="HTML" :value="1"></el-option>
							</el-select>
						</template>
						<template v-else>
                            <span style="margin-right: 20px">父级：{{ parentWikiPage.name || '/' }}</span>
							<el-tooltip class="item" content="在根目录创建文档" v-if="parentId">
								<el-link @click="changeToRootPath" size="small" style="padding: 0 10px">根目录</el-link>
							</el-tooltip>
							<span style="margin-left: 50px">编辑方式：</span>
							<el-select v-model="wikiPageEdit.editorType" v-on:change="editorTypeChange" :disabled="!!pageId" size="small">
								<el-option label="Markdown" :value="2"></el-option>
								<el-option label="HTML" :value="1"></el-option>
							</el-select>
						</template>
					</el-col>
					<el-col :span="8" style="text-align: right">
						<el-button type="primary" v-on:click="createWikiSave(1)" size="small" :icon="ElIconDocumentChecked">保存并查看</el-button>
						<el-button type="success" v-on:click="createWikiSave(0)" size="small" :icon="ElIconCheck">仅保存</el-button>
						<el-button v-on:click="createWikiCancel" size="small" :icon="ElIconBack">取消</el-button>
					</el-col>
				</el-row>
			</div>
			<div v-show="wikiPageEdit.editorType === 2" style="padding: 0 10px 10px 10px; background: #fff">
				<el-input v-model="wikiPageEdit.pageTitle" placeholder="请输入标题" class="page-title-input"></el-input>
				<mavonEditor
					ref="mavonEditorRef"
					v-model="markdownContent"
					:toolbars="toolbars"
					:externalLink="false"
					style="height: calc(100vh - 165px)"
					@save="createWikiSave(0)"
					@imgAdd="addMarkdownImage"
					placeholder="请录入文档内容"
					class="page-content-editor wang-editor-body"
				/>
			</div>
			<div v-show="wikiPageEdit.editorType === 1">
				<WangEditor ref="wangEditorRef"></WangEditor>
			</div>
		</div>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, onUnmounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import {
	DocumentChecked as ElIconDocumentChecked,
	Check as ElIconCheck,
	Back as ElIconBack,
} from '@element-plus/icons-vue'
import pageApi from '../../common/api/page'
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import 'mavon-editor/dist/css/index.css'
import axios from 'axios'
import WangEditor from './editor/WangEditor.vue'

let editor = ref({});
// 编辑相关
let wikiPageEdit = ref({editorType: 2, pageTitle: '',});
let wikiPage = ref({});
let pageContent = ref({});
let pageFileList = ref([]);
let parentWikiPage = ref({});
let isUnlock = ref(false);
// 页面ID，有值代表编辑
let pageId = ref('');
// 父级，有值代表在此父级新建文档
let parentId = ref('');
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
const props = defineProps({
	spaceId: Number,
});
let emit = defineEmits(['loadPageList']);
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
let route = useRoute();
let router = useRouter();
onUnmounted(() => {
	unlockPage()
});
onMounted(() => {
	initEditor()
	initQueryParam(route)
	let that = this
	window.onunload = function () {
		that.unlockPage()
	}
	window.onbeforeunload = function () {
		that.unlockPage()
	}
})
const changeToRootPath = () => {
	// 没有父级，就是在根目录创建
	parentId.value = ''
	parentWikiPage.value = {}
}
const editorTypeChange = () => {
}
const unlockPage = () => {
	// 防止各种事件重复调这个接口，只需要调一次就好了
	if (isUnlock.value) return
	isUnlock.value = true
	pageApi.pageUnlock({pageId: pageId.value})
}
const createWikiCancel = () => {
	ElMessageBox.confirm('确定要取消编辑吗？您编辑的内容将不会被保存哦~', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '继续编辑',
		type: 'warning',
	}).then(() => {
		unlockPage()
		router.back()
	})
}
let wangEditorRef = ref();
const createWikiSave = (saveAfter) => {
	let content = '',
		preview = ''
	if (wikiPageEdit.value.editorType === 2) {
		content = markdownContent.value
		preview = markdownContent.value
	} else {
		let pageData = wangEditorRef.value.getPageData()
		content = pageData.html
		preview = pageData.text
		wikiPageEdit.value.pageTitle = pageData.title
	}
	if (!wikiPageEdit.value.pageTitle) {
		ElMessage.warning('标题不能为空')
		return
	}
	// 修改内容时强制不能修改父路径，只能在目录上拖动修改
	let parentIdVal = pageId.value > 0 ? '' : parentId.value
	let param = {
		spaceId: props.spaceId,
		parentId: parentIdVal,
		id: wikiPage.value.id,
		name: wikiPageEdit.value.pageTitle,
		editorType: wikiPageEdit.value.editorType,
		content: content,
		preview: preview,
	}
	pageApi.updatePage(param).then((json) => {
		ElMessage.success('保存成功！')
		// 重新加载左侧列表，跳转到展示页面
		emit('loadPageList')
		pageId.value = json.data.id
		if (saveAfter == 1) {
			router.push({
				path: '/page/show',
				query: {pageId: pageId.value},
			})
		} else {
			loadPageDetail.value(pageId.value)
		}
	})
}
const loadPageDetail = (pageId) => {
	pageApi.pageDetail({id: pageId}).then((json) => {
		wikiPage.value = json.data.wikiPage || {}
		pageContent.value = json.data.pageContent || {}
		pageFileList.value = json.data.fileList || []
		// 内容
		wikiPageEdit.value.pageTitle = wikiPage.value.name
		wikiPageEdit.value.editorType = wikiPage.value.editorType
		if (wikiPageEdit.value.editorType === 2) {
			markdownContent.value = pageContent.value.content || ''
		} else {
			// editor.value.txt.html(pageContent.value.content || "");
			setTimeout(() => {
				wangEditorRef.value.setTitle(wikiPage.value.name || '')
				wangEditorRef.value.setHtml(pageContent.value.content || '')
			}, 0)
		}
	})
}
const loadParentPageDetail = (pageId) => {
	if (!pageId) return
	pageApi.pageDetail({id: pageId}).then((json) => {
		parentWikiPage.value = json.data.wikiPage || {}
	})
}
const cleanPage = () => {
	wikiPage.value = {}
	pageContent.value = {}
	pageFileList.value = []
	wikiPageEdit.value.pageTitle = ''
	if (!!editor.value.txt) {
		editor.value.txt.html('')
	}
}
const initQueryParam = (to) => {
	// pageId和parentId二选一，传了pageId代表编辑页面，否则代表新建页面
	pageId.value = to.query.pageId
	parentId.value = to.query.parentId
	if (!!pageId.value) {
		loadPageDetail(pageId.value)
		pageApi.pageLock({pageId: pageId.value}).catch((json) => {
			ElMessageBox.alert(json.errMsg || '未知错误', '错误', {
				confirmButtonText: '确定',
				callback: () => {
					router.back()
				},
			})
		})
	} else {
		loadParentPageDetail(parentId.value)
		cleanPage()
	}
}
let mavonEditorRef = ref();
const addMarkdownImage = (pos, file) => {
	let formData = new FormData()
	formData.append('files', file)
	axios({
		url: fileUploadUrl.value,
		method: 'post',
		data: formData,
		headers: {'Content-Type': 'multipart/form-data'},
		timeout: 10000,
		withCredentials: true,
	}).then((res) => {
		let urlObj = res.data.data || {}
		if (urlObj.url) {
			mavonEditorRef.value.$img2Url(pos, urlObj.url)
		} else {
			ElMessage.warning('上传失败，返回数据为空')
		}
	}).catch((e) => {
		ElMessage.warning('上传失败：' + e.message)
	})
}
const initEditor = () => {
}
</script>

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

.page-edit-vue .page-title-input {
	padding-bottom: 10px;
}
</style>
