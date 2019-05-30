<template>
	<div style="padding: 10px;">
		<el-row type="border-card">
			<div class="wiki-title">
				{{wikiPage.name}}
				<div style="float: right;">
					<el-button type="text" icon="el-icon-edit" v-on:click="editWiki">编辑</el-button>
					<el-button type="text" icon="el-icon-setting" v-on:click="editWikiAuth">访问权限</el-button>
					<el-button type="text" icon="el-icon-delete" v-on:click="deleteWikiPage">删除</el-button>
				</div>
			</div>
			<div class="wiki-author">
				<span>创建：{{wikiPage.createUserName}}　{{wikiPage.createTime}}　　<span v-show="wikiPage.updateUserName">修改：{{wikiPage.updateUserName}}　{{wikiPage.updateTime}}</span></span>
				<div style="float: right;">
					<el-upload class="upload-page-file" action="zyplayer-doc-wiki/common/upload"
							   :on-success="uploadFileSuccess" :on-error="uploadFileError"
							   name="files" show-file-list multiple :data="uploadFormData" :limit="999">
						<el-button icon="el-icon-upload">上传附件</el-button>
					</el-upload>
				</div>
			</div>
			<div class="wiki-files">
				<el-table v-show="pageFileList.length > 0" :data="pageFileList" border style="width: 100%; margin-bottom: 5px;">
					<el-table-column label="文件名">
						<template slot-scope="scope">
							<a target="_blank" :href="scope.row.fileUrl">{{scope.row.fileName}}</a>
						</template>
					</el-table-column>
					<el-table-column prop="createUserName" label="创建人"></el-table-column>
					<el-table-column prop="createTime" label="创建时间"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button size="small" v-on:click="deletePageFile(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<div class="wiki-content w-e-text">
				<div v-html="pageContent.content"></div>
			</div>
			<div style="margin-top: 40px; font-size: 14px;">
				<span style="vertical-align: top;" class="is-link">
					<span v-show="wikiPage.selfZan == 0" v-on:click="zanPage(1)"><img src="../../assets/img/zan.png" style="vertical-align: middle;"> 赞</span>
					<span v-show="wikiPage.selfZan == 1" v-on:click="zanPage(0)"><img src="../../assets/img/zan.png" style="vertical-align: middle;transform: rotateX(180deg);"> 踩</span>
				</span>
				<span style="margin-left: 10px;vertical-align: top;">
					<span v-if="wikiPage.selfZan == 0 && wikiPage.zanNum <= 0">成为第一个赞同者</span>
					<span v-else-if="wikiPage.selfZan == 0 && wikiPage.zanNum > 0"><span class="is-link" v-on:click="showZanPageUser">{{wikiPage.zanNum}}人</span>赞了它</span>
					<span v-else-if="wikiPage.selfZan == 1 && wikiPage.zanNum <= 1">我赞了它</span>
					<span v-else-if="wikiPage.selfZan == 1 && wikiPage.zanNum > 1"><span class="is-link" v-on:click="showZanPageUser">我和{{wikiPage.zanNum-1}}个其他人</span>赞了它</span>
				</span>
			</div>
			<div v-show="commentList.length > 0" class="comment-box" style="margin-top: 20px;">
				<div style="border-bottom: 1px solid #67C23A;padding-bottom: 10px;">评论列表：</div>
				<div v-for="(comment,index) in commentList" :key="comment.id" :data-id="comment.id" :data-index="index" style="border-bottom: 1px solid #eee;padding: 10px;">
					<div>
						<div :style="'background-color: '+comment.color" class="head">{{comment.createUserName.substr(0,1)}}</div>
					</div>
					<div style="padding-left: 55px;">
						{{comment.createUserName}}
						<span style="color: #888;font-size: 13px;padding-left: 10px;">{{comment.createTime}}</span>
						<span style="color: #888;font-size: 13px;margin-left: 10px;cursor: pointer;" @click="recommentUser(comment.id, index)">回复</span>
					</div>
					<pre style="padding: 10px 0 0 55px;">{{comment.content}}</pre>
					<div v-for="(commentSub,indexSub) in comment.commentList" :key="commentSub.id" :data-id="commentSub.id" :data-index="indexSub" style="border-bottom: 1px solid #eee;padding: 10px;margin-left: 40px;">
						<div>
							<div :style="'background-color: '+commentSub.color" class="head">{{commentSub.createUserName.substr(0,1)}}</div>
						</div>
						<div style="padding-left: 55px;">
							{{commentSub.createUserName}}
							<span style="color: #888;font-size: 13px;padding-left: 10px;">{{commentSub.createTime}}</span>
						</div>
						<pre style="padding: 10px 0 0 55px;">{{commentSub.content}}</pre>
					</div>
				</div>
			</div>
			<div style="margin: 20px 0 50px 0;">
				<el-input type="textarea" v-model="commentTextInput" :rows="5" :placeholder="recommentInfo.placeholder || '请输入评论内容'"></el-input>
				<div align="right" style="margin-top: 5px;">
					<el-button type="primary" v-on:click="submitPageComment">提交评论</el-button>
					<el-button v-on:click="cancelCommentUser" v-show="recommentInfo.id > 0">取消回复</el-button>
				</div>
			</div>
		</el-row>
		<!--点赞人员弹窗-->
		<el-dialog title="赞了它的人" :visible.sync="zanUserDialogVisible" width="600px">
			<el-table :data="zanUserList" border :show-header="false" style="width: 100%; margin-bottom: 5px;">
				<el-table-column prop="createUserName" label="用户"></el-table-column>
				<el-table-column prop="createTime" label="时间"></el-table-column>
			</el-table>
		</el-dialog>
	</div>
</template>

<script>
	import toast from '../../common/lib/common/toast'
	import jquery from '../../common/lib/jquery/jquery-3.1.0.min'
	import wangEditorJs from '../../common/lib/wangEditor/wangEditor.min.js'
	import wangEditorCss from '../../common/lib/wangEditor/wangEditor.min.css'
	var app;
	var page = {
		newPageContentEditor: '',
		colorArr: ["#67C23A", "#409EFF", "#E6A23C", "#F56C6C", "#909399", "#303133"],
		userHeadColor: {},
	};
	export default {
		data() {
			return {
				// 页面展示相关
				wikiPage: {},
				pageContent: {},
				pageFileList: [],
				uploadFileList: [],
				uploadFormData: {pageId: 0},
				zanUserDialogVisible: false,
				zanUserList: [],
				// 评论相关
				commentTextInput: "",
				commentList: [],
				recommentInfo: {},
			};
		},
		mounted: function () {
			app = this;
			var pageId = this.$route.query.pageId;
			if (!!pageId) {
				this.loadPageDetail(pageId);
			}
		},
		methods: {
			editWiki() {
				// this.rightContentType = 2;
				// this.newPageId = app.wikiPage.id;
				// this.newPageTitle = app.wikiPage.name;
				// page.newPageContentEditor.txt.html(app.pageContent.content || "");
			},
			editWikiAuth() {
				toast.notOpen();
			},
			notOpen() {
				toast.notOpen();
			},
			deleteWikiPage() {
				this.$confirm('确定要删除此页面吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {id: app.wikiPage.id, delFlag: 1};
					this.common.post(this.apilist1.updatePage, param, function (json) {
						app.rightContentType = 0;
						app.wikiPage = {};
						app.doGetPageList(null);
					});
				});
			},
			loadPageDetail(pageId) {
				app.rightContentType = 1;
				var param = {id: pageId};
				this.common.post(this.apilist1.updateDetail, param, function (json) {
					var wikiPage = json.data.wikiPage || {};
					wikiPage.selfZan = json.data.selfZan || 0;
					app.wikiPage = wikiPage;
					app.pageContent = json.data.pageContent || {};
					app.pageFileList = json.data.fileList || [];
					app.uploadFormData = {pageId: app.wikiPage.id};
					// 修改最后点击的项，保证刷新后点击编辑能展示编辑的项
					// if (!app.lastClickNode.id) {
					// 	app.lastClickNode = {id: wikiPage.id, nodePath: wikiPage.name};
					// }
				});
				this.loadCommentList(pageId);
			},
			loadCommentList(pageId) {
				app.commentList = [];
				app.cancelCommentUser();
				var param = {pageId: pageId};
				this.common.post(this.apilist1.pageCommentList, param, function (json) {
					var commentList = json.data || [];
					for (var i = 0; i < commentList.length; i++) {
						commentList[i].color = app.getUserHeadBgColor(commentList[i].createUserId);
						var subCommentList = commentList[i].commentList || [];
						for (var j = 0; j < subCommentList.length; j++) {
							var subItem = subCommentList[j];
							subItem.color = app.getUserHeadBgColor(subItem.createUserId);
						}
						commentList[i].commentList = subCommentList;
					}
					app.commentList = commentList;
				});
			},
			zanPage(yn) {
				var param = {yn: yn, pageId: app.wikiPage.id};
				this.common.post(this.apilist1.updatePageZan, param, function (json) {
					app.wikiPage.selfZan = yn;
					app.wikiPage.zanNum = app.wikiPage.zanNum + (yn == 1 ? 1 : -1);
				});
			},
			showZanPageUser() {
				app.zanUserDialogVisible = true;
				app.zanUserList = [];
				var param = {pageId: app.wikiPage.id};
				this.common.post(this.apilist1.pageZanList, param, function (json) {
					app.zanUserList = json.data;
				});
			},
			recommentUser(id, index) {
				this.recommentInfo = {id: id, index: index, placeholder: '回复' + (index + 1) + '楼'};
			},
			cancelCommentUser() {
				this.recommentInfo = {};
			},
			submitPageComment() {
				var param = {
					pageId: app.wikiPage.id, content: app.commentTextInput,
					parentId: app.recommentInfo.id
				};
				this.common.post(this.apilist1.updatePageComment, param, function (json) {
					var data = json.data;
					data.color = app.getUserHeadBgColor(data.createUserId);
					app.commentTextInput = "";
					app.commentList.push(data);
				});
			},
			uploadFileError(err) {
				toast.success("上传失败，" + err);
			},
			uploadFileSuccess(response) {
				if (this.common.validateResult(response)) {
					app.pageFileList.push(response.data);
					toast.success("上传成功！");
				}
			},
			deletePageFile(row) {
				this.$confirm('确定要删除此文件吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {id: row.id, delFlag: 1};
					this.common.post(this.apilist1.updatePageFile, param, function (json) {
						var pageFileList = [];
						for (var i = 0; i < app.pageFileList.length; i++) {
							if (app.pageFileList[i].id != row.id) {
								pageFileList.push(app.pageFileList[i]);
							}
						}
						app.pageFileList = pageFileList;
					});
				});
			},
			getUserHeadBgColor(userId) {
				var color = page.userHeadColor[userId];
				if (!color) {
					color = page.colorArr[Math.ceil(Math.random() * page.colorArr.length) - 1];
					page.userHeadColor[userId] = color;
				}
				return color;
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

