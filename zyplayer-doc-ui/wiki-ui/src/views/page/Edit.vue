<template>
	<div style="height: 100%;" class="page-edit-vue">
		<el-row type="border-card" style="height: 100%;overflow: auto;padding: 20px;box-sizing: border-box;">
			<el-row :gutter="20">
				<el-col :span="16">
					<template v-if="pageId">
						<span>编辑方式：</span>
						<el-select v-model="wikiPageEdit.editorType" v-on:change="editorTypeChange" :disabled="!!pageId">
							<el-option label="HTML" :value="1"></el-option>
							<el-option label="Markdown" :value="2"></el-option>
						</el-select>
					</template>
					<template v-else>
						<span style="margin-right: 20px;">父级：{{parentWikiPage.name || '/'}}</span>
						<el-tooltip class="item" content="在根目录创建文档" v-if="parentId">
							<el-button type="text" @click="changeToRootPath" style="padding: 0 10px;">根目录</el-button>
						</el-tooltip>
						<span style="margin-left: 50px;">编辑方式：</span>
						<el-select v-model="wikiPageEdit.editorType" v-on:change="editorTypeChange" :disabled="!!pageId">
							<el-option label="HTML" :value="1"></el-option>
							<el-option label="Markdown" :value="2"></el-option>
						</el-select>
					</template>
				</el-col>
				<el-col :span="8" style="text-align: right;">
					<el-button type="primary" v-on:click="createWikiSave(1)" icon="el-icon-document-checked">保存并查看</el-button>
					<el-button type="success" v-on:click="createWikiSave(0)" icon="el-icon-check">仅保存</el-button>
					<el-button v-on:click="createWikiCancel" icon="el-icon-back">取消</el-button>
				</el-col>
			</el-row>
			<el-input v-model="wikiPageEdit.pageTitle" placeholder="请输入标题" class="page-title-input"></el-input>
			<mavon-editor v-show="wikiPageEdit.editorType===2" ref="mavonEditor" v-model="markdownContent" :toolbars="toolbars"
						  :externalLink="false"
						  @save="createWikiSave(0)" @imgAdd="addMarkdownImage"
						  placeholder="请录入文档内容" class="page-content-editor wang-editor-body"/>
			<div v-show="wikiPageEdit.editorType===1" id="newPageContentDiv" class="page-content-editor" style="height: calc(100vh - 250px);"></div>
		</el-row>
	</div>
</template>

<script>
	import WangEditor from 'wangeditor'
	import pageApi from '../../common/api/page'
	import {mavonEditor, markdownIt} from 'mavon-editor'
	import 'mavon-editor/dist/markdown/github-markdown.min.css'
	import 'mavon-editor/dist/css/index.css'
	import "../../common/lib/wangEditor.css";
	import axios from 'axios'

	export default {
		props: ['spaceId'],
		data() {
			return {
				editor: {},
				// 编辑相关
				wikiPageEdit: {
					editorType: 1,
					pageTitle: "",
				},
				wikiPage: {},
				parentWikiPage: {},
                isUnlock: false,
				// 页面ID，有值代表编辑
				pageId: '',
				// 父级，有值代表在此父级新建文档
				parentId: '',
				markdownContent: '',
				toolbars: {
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
				},
				fileUploadUrl: process.env.VUE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload',
			};
		},
		components: {
			'mavon-editor': mavonEditor
		},
		destroyed: function () {
            this.unlockPage();
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			this.initEditor();
			this.initQueryParam(this.$route);
			let that = this;
			window.onunload = function () {
				that.unlockPage();
			};
			window.onbeforeunload = function () {
				that.unlockPage();
			};
		},
		methods: {
			changeToRootPath() {
				// 没有父级，就是在根目录创建
				this.parentId = '';
				this.parentWikiPage = {};
			},
			editorTypeChange() {
			},
            unlockPage() {
			    // 防止各种事件重复调这个接口，只需要调一次就好了
				if (this.isUnlock) return;
				this.isUnlock = true;
				pageApi.pageUnlock({pageId: this.pageId});
            },
			createWikiCancel() {
				this.$confirm('确定要取消编辑吗？您编辑的内容将不会被保存哦~', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '继续编辑',
					type: 'warning'
				}).then(() => {
					this.unlockPage();
					this.$router.back();
				});
			},
			createWikiSave(saveAfter) {
				if (!this.wikiPageEdit.pageTitle) {
					this.$message.warning("标题不能为空");
					return;
				}
				let content = '', preview = '';
				if (this.wikiPageEdit.editorType === 2) {
					content = this.markdownContent;
					preview = this.markdownContent;
				} else {
					content = this.editor.txt.html();
					preview = this.editor.txt.text();
				}
				// 修改内容时强制不能修改父路径，只能在目录上拖动修改
				let parentId = (this.pageId > 0) ? '' : this.parentId;
				let param = {
					spaceId: this.spaceId,
					parentId: parentId,
					id: this.wikiPage.id,
					name: this.wikiPageEdit.pageTitle,
					editorType: this.wikiPageEdit.editorType,
					content: content,
					preview: preview,
				};
				pageApi.updatePage(param).then(json => {
					this.$message.success("保存成功！");
					// 重新加载左侧列表，跳转到展示页面
					this.$emit('loadPageList');
					this.pageId = json.data.id;
					if (saveAfter == 1) {
						this.$router.push({path: '/page/show', query: {pageId: this.pageId}});
					} else {
						this.loadPageDetail(this.pageId);
					}
				});
			},
			loadPageDetail(pageId) {
				pageApi.pageDetail({id: pageId}).then(json => {
					this.wikiPage = json.data.wikiPage || {};
					this.pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
					// 内容
					this.wikiPageEdit.pageTitle = this.wikiPage.name;
					this.wikiPageEdit.editorType = this.wikiPage.editorType;
					if (this.wikiPageEdit.editorType === 2) {
						this.markdownContent = this.pageContent.content || "";
					} else {
						this.editor.txt.html(this.pageContent.content || "");
					}
				});
			},
			loadParentPageDetail(pageId) {
				if (!pageId) return;
				pageApi.pageDetail({id: pageId}).then(json => {
					this.parentWikiPage = json.data.wikiPage || {};
				});
			},
			cleanPage() {
				this.wikiPage = {};
				this.pageContent = {};
				this.pageFileList = [];
				this.wikiPageEdit.pageTitle = "";
				if (!!this.editor.txt) {
					this.editor.txt.html("");
				}
			},
			initQueryParam(to) {
				// pageId和parentId二选一，传了pageId代表编辑页面，否则代表新建页面
				this.pageId = to.query.pageId;
				this.parentId = to.query.parentId;
				if (!!this.pageId) {
					this.loadPageDetail(this.pageId);
					pageApi.pageLock({pageId: this.pageId}).catch(json => {
						let that = this;
						this.$alert(json.errMsg || '未知错误', '错误', {
							confirmButtonText: '确定',
							callback: () => {
								that.$router.back();
							}
						});
					});
				} else {
					this.loadParentPageDetail(this.parentId);
					this.cleanPage();
				}
			},
			addMarkdownImage(pos, file) {
				let formData = new FormData();
				formData.append('files', file);
				axios({
					url: this.fileUploadUrl,
					method: 'post',
					data: formData,
					headers: {'Content-Type': 'multipart/form-data'},
					timeout: 10000,
					withCredentials: true
				}).then(res => {
					let urlArr = res.data.data || [];
					if (urlArr.length > 0) {
						this.$refs.mavonEditor.$img2Url(pos, urlArr[0]);
					} else {
						this.$message.warning("上传失败，返回数据为空");
					}
				}).catch(e => {
					this.$message.warning("上传失败：" + e.message);
				});
			},
			initEditor() {
				this.editor = new WangEditor('#newPageContentDiv');
				this.editor.customConfig.uploadImgServer = process.env.VUE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/wangEditor/upload';
				this.editor.customConfig.zIndex = 100;
				this.editor.customConfig.uploadFileName = 'files';
				this.editor.customConfig.uploadImgMaxLength = 1;
				this.editor.customConfig.pasteFilterStyle = false;
				this.editor.customConfig.withCredentials = true;
				this.editor.create();
			},
		}
	}

</script>
<style>
	.page-edit-vue .icon-collapse{float: left;font-size: 25px;color: #aaa;margin-top: 8px;cursor: pointer;}
	.page-edit-vue .icon-collapse:hover{color: #eee;}
	.page-edit-vue .wiki-title{font-size: 20px;}
	.page-edit-vue .wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}
	.page-edit-vue .wiki-content{font-size: 14px;}
	.page-edit-vue .wiki-content.w-e-text{overflow-y: auto;}

	.page-edit-vue .upload-page-file .el-upload-list{display: none;}
	.page-edit-vue .is-link{color: #1e88e5;cursor: pointer;}
	/*编辑框高度*/
	.page-edit-vue #newPageContentDiv .w-e-text-container{height: 100% !important;}
	/*评论*/
	.page-edit-vue .comment-box .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
	.page-edit-vue .page-content-editor{
		padding: 10px 0;
	}
	.page-edit-vue .page-title-input{
		padding: 10px 0;
	}
</style>

