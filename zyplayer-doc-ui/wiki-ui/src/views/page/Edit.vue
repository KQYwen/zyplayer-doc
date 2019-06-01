<template>
	<div style="padding: 10px;">
		<el-row type="border-card">
			<div style="margin-bottom: 10px;padding: 10px;" v-if="wikiPage.id > 0">
				编辑：{{parentPath.path}}
			</div>
			<div style="margin-bottom: 10px;padding: 10px;" v-else>
				父级：{{parentPath.path || '/'}}　
				<el-tooltip class="item" content="在根目录创建文档">
					<el-button type="text" @click="parentPath = {}" style="padding: 0 10px;">根目录</el-button>
				</el-tooltip>
			</div>
			<el-input v-model="newPageTitle" placeholder="请输入标题"></el-input>
			<div id="newPageContentDiv" style="margin: 10px 0;"></div>
			<el-button type="primary" v-on:click="createWikiSave">保存</el-button>
			<el-button v-on:click="createWikiCancel">取消</el-button>
		</el-row>
	</div>
</template>

<script>
	import toast from '../../common/lib/common/toast'
	import WangEditor from 'wangeditor'

	var app;
	export default {
		data() {
			return {
				editor: {},
				// 编辑相关
				nowSpaceId: "",
				newPageId: "",
				newPageTitle: "",
				parentPath: {
					id: '',
					path: '',
				},
				wikiPage: {},
			};
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			app = this;
			this.initEditor();
			this.initQueryParam(this.$route);
		},
		methods: {
			createWikiCancel() {
				this.$confirm('确定要取消编辑吗？您编辑的内容将不会被保存哦~', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '继续编辑',
					type: 'warning'
				}).then(() => {

				});
			},
			createWikiSave() {
				var parentId = app.parentPath.id;
				if (this.wikiPage.id > 0) {
					parentId = "";
				}
				if (this.common.isEmpty(app.newPageTitle)) {
					toast.warn("标题不能为空");
					return;
				}
				var param = {
					spaceId: app.nowSpaceId,
					id: app.wikiPage.id,
					name: app.newPageTitle,
					parentId: parentId,
					content: this.editor.txt.html()
				};
				this.common.post(this.apilist1.updatePage, param, function (json) {
					toast.success("保存成功！");
					// app.urlParamPageId = json.data.id;
					// app.loadPageDetail(json.data.id);
					// app.doGetPageList(null);
				});
			},
			loadPageDetail(pageId) {
				app.rightContentType = 1;
				var param = {id: pageId};
				this.common.post(this.apilist1.pageDetail, param, function (json) {
					app.wikiPage = json.data.wikiPage || {};
					app.pageContent = json.data.pageContent || {};
					app.pageFileList = json.data.fileList || [];
					// 内容
					app.nowSpaceId = app.wikiPage.spaceId;
					app.newPageTitle = app.wikiPage.name;
					app.editor.txt.html(app.pageContent.content || "");
				});
			},
			cleanPage() {
				app.wikiPage = {};
				app.pageContent = {};
				app.pageFileList = [];
				app.newPageTitle = "";
				if (!!app.editor.txt) {
					app.editor.txt.html("");
				}
			},
			initQueryParam(to) {
				this.nowSpaceId = to.query.spaceId;
				var pageId = to.query.pageId;
				if (!!pageId) {
					this.loadPageDetail(pageId);
				} else {
					this.cleanPage();
				}
			},
			initEditor() {
				this.editor = new WangEditor('#newPageContentDiv');
				this.editor.customConfig.uploadImgServer = this.apilist1.HOST + '/zyplayer-doc-wiki/common/wangEditor/upload';
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
	.icon-collapse{float: left;font-size: 25px;color: #aaa;margin-top: 8px;cursor: pointer;}
	.icon-collapse:hover{color: #eee;}
	.wiki-title{font-size: 20px;}
	.wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}
	.wiki-content{font-size: 14px;}
	.wiki-content.w-e-text{overflow-y: auto;}

	.upload-page-file .el-upload-list{display: none;}
	.is-link{color: #1e88e5;cursor: pointer;}
	/*编辑框高度*/
	#newPageContentDiv .w-e-text-container{height: 600px !important;}
	/*评论*/
	.comment-box .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
</style>

