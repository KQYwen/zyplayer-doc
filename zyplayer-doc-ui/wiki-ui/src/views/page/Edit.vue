<template>
	<div style="padding: 10px;">
		<el-row type="border-card">
			<div style="margin-bottom: 10px;padding: 10px;" v-if="wikiPage.id > 0">
				编辑：{{parentPath.path}}
			</div>
			<div style="margin-bottom: 10px;padding: 10px;" v-else>
				父级：{{parentPath.path || '/'}}　
				<el-tooltip class="item" content="在根目录创建文档">
					<el-button type="text" @click="changeToRootPath" style="padding: 0 10px;">根目录</el-button>
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
	import global from '../../common/config/global'
	import WangEditor from 'wangeditor'

	window.onunload = function () {
        app.unlockPage();
	};
	window.onbeforeunload = function () {
        app.unlockPage();
	};

	var app;
	export default {
		data() {
			return {
				editor: {},
				// 编辑相关
				newPageTitle: "",
				parentPath: {},
				wikiPage: {},
                isUnlock: false,
			};
		},
		destroyed: function () {
            this.unlockPage();
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
			changeToRootPath() {
				app.parentPath = {spaceId: this.parentPath.spaceId};
			},
            unlockPage() {
			    // 防止各种事件重复调这个接口，只需要调一次就好了
                if (this.isUnlock) {
                    return;
                }
                this.isUnlock = true;
                var param = {pageId: app.parentPath.pageId};
                this.common.post(this.apilist1.pageUnlock, param, function (json) {});
            },
			createWikiCancel() {
				this.$confirm('确定要取消编辑吗？您编辑的内容将不会被保存哦~', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '继续编辑',
					type: 'warning'
				}).then(() => {
				    app.unlockPage();
					app.$router.back();
				});
			},
			createWikiSave() {
				// 修改内容时强制不能修改父路径，只能在目录上拖动修改
				var parentId = (this.wikiPage.id > 0) ? '' : app.parentPath.parentId;
				if (this.common.isEmpty(app.newPageTitle)) {
					toast.warn("标题不能为空");
					return;
				}
				var preview = this.editor.txt.text();
				var param = {
					spaceId: app.parentPath.spaceId,
					parentId: parentId,
					id: app.wikiPage.id,
					name: app.newPageTitle,
					content: this.editor.txt.html(),
					preview: preview,
				};
				this.common.post(this.apilist1.updatePage, param, function (json) {
					toast.success("保存成功！");
					// 重新加载左侧列表，跳转到展示页面
					global.vue.$app.doGetPageList(null);
					app.parentPath.pageId = json.data.id;
					app.$router.push({path: '/page/show', query: app.parentPath});
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
				this.parentPath = {
					spaceId: to.query.spaceId, pageId: to.query.pageId,
					parentId: to.query.parentId, path: to.query.path
				};
				if (!!this.parentPath.pageId) {
					this.loadPageDetail(this.parentPath.pageId);
				} else {
					this.cleanPage();
				}
				var param = {pageId: app.parentPath.pageId};
				this.common.postNonCheck(this.apilist1.pageLock, param, function (json) {
					if (json.errCode !== 200) {
						app.$alert(json.errMsg || '未知错误', '错误', {
							confirmButtonText: '确定',
							callback: () => {
								app.$router.back();
							}
						});
					}
				});
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

