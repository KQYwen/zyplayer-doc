<template>
	<div class="wang-editor-box">
		<div class="editor-toolbar-box fix-top">
			<Toolbar class="editor-toolbar"
			         :defaultConfig="toolbarConfig"
			         :mode="mode"
			         :editor="editorRef"
			/>
		</div>
		<div class="wang-editor-content">
			<div class="editor-container">
				<div class="title-container">
					<input v-model="pageTitle" placeholder="请输入标题" :maxlength="40">
				</div>
				<div @click="pageEditorBodyClick" class="page-editor-body">
					<Editor :defaultConfig="editorConfig" :mode="mode" @onCreated="handleCreated"/>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css'
import {DomEditor} from '@wangeditor/editor'
import {onBeforeUnmount, ref, shallowRef, onMounted, watch, defineProps, defineExpose} from 'vue'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";

const props = defineProps({
});

let toolbarConfig = {
	excludeKeys: [
		"fullScreen", "undo", "redo", "emotion", "|", "lineHeight", "fontFamily"
	],
};
let editorPageId = ref('');
let route = useRoute();
let editorConfig = ref({
	placeholder: '请输入文档内容',
	scroll: false,
	MENU_CONF: {
		uploadImage: {
			server: import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload',
			fieldName: 'files',
			// 最大支持50M图片上传
			maxFileSize: 50 * 1024 * 1024,
			withCredentials: true,
			meta: {
				pageId: editorPageId,
			},
		},
		uploadVideo: {
			server: import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload',
			fieldName: 'files',
			// 最大支持300M图片上传
			maxFileSize: 300 * 1024 * 1024,
			withCredentials: true,
			meta: {
				pageId: editorPageId,
			},
		}
	}
});
let mode = 'default';
let defaultHtml = '';
const editorRef = shallowRef()
let pageTitle = ref('');

const handleCreated = (editor) => {
	editorRef.value = editor;
}

const pageEditorBodyClick = (e) => {
	if (e.target.classList && e.target.classList.contains('page-editor-body')) {
		editorRef.value.blur();
		editorRef.value.focus(true);
	}
}

const getPageData = () => {
	return {
		title: pageTitle.value,
		html: editorRef.value.getHtml(),
		text: editorRef.value.getText(),
	};
}
const setTitle = (title) => {
	pageTitle.value = title;
}
const setPageId = (id) => {
	editorPageId.value = id;
}

const setHtml = (content) => {
	editorRef.value.select([]);
	editorRef.value.deleteFragment();
	editorRef.value.dangerouslyInsertHtml(content);
}

onBeforeUnmount(() => {
	const editor = editorRef.value;
	if (editor == null) return;
	editor.destroy();
});

defineExpose({setTitle,setPageId, setHtml, getPageData});
</script>

<style lang="scss">
.w-e-bar-item-group {
  .w-e-bar-item-menus-container {
    /**不知为何按钮和下拉之间总是差了那么一点，导致没法点击到下拉框，给他移上去一点*/
	top: -2px;
  }
}
</style>

<style>
.wang-editor-box {
	background-color: #f5f5f5;
}
.wang-editor-box .top-container {
	border-bottom: 1px solid #e8e8e8;
	padding-left: 30px;
}

.wang-editor-box .editor-toolbar .w-e-bar {
	background: #FCFCFC;
}

.wang-editor-box .editor-toolbar {
	width: 980px;
	background-color: #FCFCFC;
	margin: 0 auto;
}
.wang-editor-box .editor-toolbar-box {
	border-bottom: 1px solid #e8e8e8;
	background-color: #FCFCFC;
}
.wang-editor-box .editor-toolbar-box.fix-top {
	/*position: fixed;*/
	/*top: 40px;*/
	/*z-index: 1;*/
	/*text-align: center;*/
	/*background: #fff;*/
	/*width: 100%;*/
}
.wang-editor-box .wang-editor-content {
	padding: 20px 0;
	overflow: auto;
	height: calc(100vh - 140px);
}
.wang-editor-box .w-e-bar-item {
	height: 39px;
}

.wang-editor-box .editor-container {
	width: 850px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px 50px 50px 50px;
	border: 1px solid #e8e8e8;
	box-shadow: 0 2px 10px rgb(0 0 0 / 12%);
}

.wang-editor-box .title-container {
	padding: 20px 0;
	border-bottom: 1px solid #e8e8e8;
}

.wang-editor-box .title-container input {
	font-size: 30px;
	border: 0;
	outline: none;
	width: 100%;
	line-height: 1;
}

.wang-editor-box .page-editor-body {
	margin-top: 20px;
	min-height: 600px;
}
</style>

