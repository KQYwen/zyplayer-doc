<template>
	<div style="padding: 10px;" class="page-edit-vue">
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
			<el-button type="primary" v-on:click="createWikiSave(1)">保存并查看</el-button>
			<el-button type="success" v-on:click="createWikiSave(0)">仅保存</el-button>
			<el-button v-on:click="createWikiCancel">取消</el-button>
		</el-row>
	</div>
</template>

<script>
	import WangEditor from 'wangeditor'
	import pageApi from '../../common/api/page'

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
				this.parentPath = {spaceId: this.parentPath.spaceId};
			},
            unlockPage() {
			    // 防止各种事件重复调这个接口，只需要调一次就好了
                if (this.isUnlock) {
                    return;
                }
                this.isUnlock = true;
				var param = {pageId: this.parentPath.pageId};
				pageApi.pageUnlock(param).then(() => {
				});
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
				// 修改内容时强制不能修改父路径，只能在目录上拖动修改
				var parentId = (this.wikiPage.id > 0) ? '' : this.parentPath.parentId;
				if (!this.newPageTitle) {
					this.$message.warning("标题不能为空");
					return;
				}
				var preview = this.editor.txt.text();
				var param = {
					spaceId: this.parentPath.spaceId,
					parentId: parentId,
					id: this.wikiPage.id,
					name: this.newPageTitle,
					content: this.editor.txt.html(),
					preview: preview,
				};
				pageApi.updatePage(param).then(json => {
					this.$message.success("保存成功！");
					// 重新加载左侧列表，跳转到展示页面
					this.$emit('loadPageList');
					this.parentPath.pageId = json.data.id;
					if (saveAfter == 1) {
						this.$router.push({path: '/page/show', query: this.parentPath});
					} else {
						this.loadPageDetail(this.parentPath.pageId);
					}
				});
			},
			loadPageDetail(pageId) {
				this.rightContentType = 1;
				var param = {id: pageId};
				pageApi.pageDetail(param).then(json => {
					this.wikiPage = json.data.wikiPage || {};
					this.pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
					// 内容
					this.newPageTitle = this.wikiPage.name;
					this.editor.txt.html(this.pageContent.content || "");
				});
			},
			cleanPage() {
				this.wikiPage = {};
				this.pageContent = {};
				this.pageFileList = [];
				this.newPageTitle = "";
				if (!!this.editor.txt) {
					this.editor.txt.html("");
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
				let param = {pageId: this.parentPath.pageId};
				pageApi.pageLock(param).then(json => {
					if (json.errCode !== 200) {
						let that = this;
						this.$alert(json.errMsg || '未知错误', '错误', {
							confirmButtonText: '确定',
							callback: () => {
								that.$router.back();
							}
						});
					}
				});
			},
			initEditor() {
				this.editor = new WangEditor('#newPageContentDiv');
				this.editor.customConfig.uploadImgServer = process.env.VUE_APP_BASE_API + '/zyplayer-doc-wiki/common/wangEditor/upload';
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
	.page-edit-vue #newPageContentDiv .w-e-text-container{height: 600px !important;}
	/*评论*/
	.page-edit-vue .comment-box .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
</style>

