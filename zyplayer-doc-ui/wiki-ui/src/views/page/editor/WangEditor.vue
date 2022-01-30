<template>
	<div class="wang-editor-box">
		<div class="editor-toolbar-box fix-top">
			<Toolbar class="editor-toolbar"
					:editorId="editorId"
					:defaultConfig="toolbarConfig"
					:mode="mode"
			/>
		</div>
		<div class="wang-editor-content">
			<div class="editor-container">
				<div class="title-container">
					<input v-model="pageTitle" placeholder="请输入标题">
				</div>
				<Editor ref="editorTextArea" class="editor-text-area"
						:editorId="editorId"
						:defaultConfig="editorConfig"
						:mode="mode"
				/>
			</div>
		</div>
	</div>
</template>

<script>
	import '@wangeditor/editor/dist/css/style.css'
	import { Editor, Toolbar, getEditor, removeEditor } from '@wangeditor/editor-for-vue'
	export default {
		props: ['spaceId'],
		data() {
			return {
				editorId: `w-e-${Math.random().toString().slice(-5)}`,
				toolbarConfig: {
					excludeKeys: [
						"fullScreen", "undo" , "redo"
					],
				},
				editorConfig: {
					placeholder: '请输入文档内容',
					scroll: false,
					MENU_CONF: {
						uploadImage: {
							server: process.env.VUE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload',
							fieldName: 'files',
							// 最大支持50M图片上传
							maxFileSize: 50 * 1024 * 1024,
							withCredentials: true,
						}
					}
				},
				mode: 'default', // or 'simple'
				defaultHtml: '',
				editor: {},
				pageTitle: '',
			};
		},
		components: {
			Editor, Toolbar,
		},
		beforeDestroy() {
			let editor = getEditor(this.editorId);
			if (editor == null) return;
			editor.destroy();
			removeEditor(this.editorId);
		},
		mounted: function () {
			setTimeout(() => {
				this.initEditor();
			}, 0);
		},
		methods: {
			initEditor() {
				// 点击空白处 focus 编辑器
				let editorTextArea = this.$refs.editorTextArea.$el;
				editorTextArea.addEventListener('click', e => {
					if (e.target.className === 'editor-text-area') {
						let editor = getEditor(this.editorId);
						editor.blur();
						editor.focus(true); // focus 到末尾
					}
				});
			},
			getPageData() {
				let editor = getEditor(this.editorId);
				return {
					title: this.pageTitle,
					html: editor.getHtml(),
					text: editor.getText(),
				};
			},
			setTitle(title) {
				this.pageTitle = title;
			},
			setHtml(content) {
				let editor = getEditor(this.editorId);
				editor.select([]);
				editor.deleteFragment();
				editor.dangerouslyInsertHtml(content);
			},
		}
	}

</script>
<style>
.wang-editor-box {
	background-color: #f5f5f5;
}
.wang-editor-box .top-container {
	border-bottom: 1px solid #e8e8e8;
	padding-left: 30px;
}

.wang-editor-box .editor-toolbar {
	width: 1200px;
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
	height: calc(100vh - 170px);
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

.wang-editor-box .editor-text-area {
	margin-top: 20px;
	min-height: 600px;
}
</style>

