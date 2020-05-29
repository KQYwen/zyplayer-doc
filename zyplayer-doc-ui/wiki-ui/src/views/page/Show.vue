<template>
	<div style="padding: 10px;" class="page-show-vue">
		<el-row type="border-card">
			<div class="wiki-title">
				{{wikiPage.name}}
			</div>
			<div class="wiki-author">
				<div>
					创建：{{wikiPage.createUserName}}　{{wikiPage.createTime}}　　
					<span v-show="wikiPage.updateUserName">修改：{{wikiPage.updateUserName}}　{{wikiPage.updateTime}}</span>
					<div style="float: right;">
						<el-upload class="upload-page-file" :action="apilist1.commonUpload"
								   :with-credentials="true"
								   :on-success="uploadFileSuccess" :on-error="uploadFileError"
								   name="files" show-file-list multiple :data="uploadFormData" :limit="999"
								   style="display: inline;margin-right: 10px;">
							<el-button type="text" icon="el-icon-upload">上传附件</el-button>
						</el-upload>
						<el-button type="text" icon="el-icon-edit" v-on:click="editWiki">编辑</el-button>
						<el-button type="text" icon="el-icon-setting" v-on:click="editWikiAuth">权限设置</el-button>
						<el-button type="text" icon="el-icon-delete" v-on:click="deleteWikiPage">删除</el-button>
					</div>
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
					<el-table-column prop="createTime" label="创建时间" width="180px"></el-table-column>
					<el-table-column prop="downloadNum" label="下载次数" width="80px"></el-table-column>
					<el-table-column label="操作" width="100px">
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
				<span style="margin-left: 10px;">
					<i class="el-icon-view" style="font-size: 16px;color: #666;"></i> {{wikiPage.viewNum}}次阅读
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
		<!--人员权限弹窗-->
		<el-dialog title="页面权限" :visible.sync="pageAuthDialogVisible" width="900px">
			<el-row>
				<el-select v-model="pageAuthNewUser" filterable remote reserve-keyword
						placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList"
					   :loading="pageAuthUserLoading" style="width: 750px;">
					<el-option v-for="item in searchUserList" :key="item.id" :label="item.userName" :value="item.id"></el-option>
				</el-select>
                <el-button v-on:click="addPageAuthUser">添加</el-button>
            </el-row>
			<el-table :data="pageAuthUserList" border style="width: 100%; margin: 10px 0;">
				<el-table-column prop="userName" label="用户" width="150"></el-table-column>
				<el-table-column label="权限">
					<template slot-scope="scope">
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.editPage">编辑</el-checkbox>
<!--						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.commentPage">评论</el-checkbox>-->
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.deletePage">删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileUpload">文件上传</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileDelete">文件删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageAuthManage">权限管理</el-checkbox>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="80">
					<template slot-scope="scope">
						<el-button size="small" type="danger" plain v-on:click="deleteUserPageAuth(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
            <div>
                <el-button type="primary" v-on:click="saveUserPageAuth">保存配置</el-button>
            </div>
		</el-dialog>
	</div>
</template>

<script>
	import common from '../../common/lib/common'
	import pageApi from '../../common/api/page'
	import userApi from '../../common/api/user'
	var page = {
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
				parentPath: {},
				// 评论相关
				commentTextInput: "",
				commentList: [],
				recommentInfo: {},
				// 页面权限
				pageAuthDialogVisible: false,
				pageAuthUserList: [],
                searchUserList: [],
                pageAuthNewUser: "",
				pageAuthUserLoading: false,
			};
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			this.initQueryParam(this.$route);
			if (!!this.parentPath.pageId) {
				// 延迟设置展开的目录，edit比app先初始化
				setTimeout(() => {
					if (!!this.parentPath.spaceId) {
						// 调用父方法切换选择的空间
						this.$emit('switchSpace', this.parentPath.spaceId);
					}
					this.$emit('changeExpandedKeys', this.parentPath.pageId);
				}, 500);
			}
		},
		methods: {
			editWiki() {
				var param = {pageId: this.parentPath.pageId};
				pageApi.pageLock(param).then(() => {
					this.$router.push({path: '/page/edit', query: this.parentPath});
				});
			},
			getSearchUserList(query) {
				if (query == '') {
					return;
				}
				this.pageAuthUserLoading = true;
				var param = {search: query};
				userApi.getUserBaseInfo(param).then(json => {
					this.searchUserList = json.data || [];
					this.pageAuthUserLoading = false;
				});
			},
            addPageAuthUser() {
                if (this.pageAuthNewUser.length <= 0) {
					this.$message.warning("请先选择用户");
                    return;
                }
				var userName = "";
				for (var i = 0; i < this.searchUserList.length; i++) {
					if (this.pageAuthNewUser == this.searchUserList[i].id) {
						userName = this.searchUserList[i].userName;
						break;
					}
				}
				this.pageAuthUserList.push({
					userName: userName,
                    userId: this.pageAuthNewUser,
					editPage: 0,
                    commentPage: 0,
                    deletePage: 0,
                    pageFileUpload: 0,
                    pageFileDelete: 0,
                    pageAuthManage: 0,
                });
				this.pageAuthNewUser = "";
			},
			editWikiAuth() {
				this.pageAuthNewUser = [];
				this.pageAuthUserList = [];
				var param = {pageId: this.wikiPage.id};
				pageApi.getPageUserAuthList(param).then(json => {
					this.pageAuthUserList = json.data || [];
					this.pageAuthDialogVisible = true;
				});
			},
			saveUserPageAuth() {
				var param = {pageId: this.wikiPage.id, authList: JSON.stringify(this.pageAuthUserList)};
				pageApi.assignPageUserAuth(param).then(() => {
					this.$message.success("保存成功！");
				});
			},
			notOpen() {
				this.$message.warning("暂未开放");
			},
            deleteUserPageAuth(row) {
                var pageAuthUserList = [];
                for (var i = 0; i < this.pageAuthUserList.length; i++) {
                    var item = this.pageAuthUserList[i];
                    if (item.userId != row.userId) {
                        pageAuthUserList.push(this.pageAuthUserList[i]);
                    }
                }
                this.pageAuthUserList = pageAuthUserList;
			},
			deleteWikiPage() {
				this.$confirm('确定要删除此页面及其所有子页面吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {pageId: this.wikiPage.id};
					pageApi.pageDelete(param).then(() => {
						// 重新加载左侧列表，跳转到展示页面
						this.$emit('loadPageList');
						this.$router.push({path: '/home', query: {spaceId: this.wikiPage.spaceId}});
					});
				}).catch(()=>{});
			},
			loadPageDetail(pageId) {
				this.rightContentType = 1;
				var param = {id: pageId};
				pageApi.pageDetail(param).then(json => {
					var wikiPage = json.data.wikiPage || {};
					wikiPage.selfZan = json.data.selfZan || 0;
					this.wikiPage = wikiPage;
					this.pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
					this.uploadFormData = {pageId: this.wikiPage.id};
					this.parentPath.spaceId = wikiPage.spaceId;
					// 修改最后点击的项，保证刷新后点击编辑能展示编辑的项
					// if (!this.lastClickNode.id) {
					// 	this.lastClickNode = {id: wikiPage.id, nodePath: wikiPage.name};
					// }
				});
				this.loadCommentList(pageId);
			},
			loadCommentList(pageId) {
				this.commentList = [];
				this.cancelCommentUser();
				var param = {pageId: pageId};
				pageApi.pageCommentList(param).then(json => {
					var commentList = json.data || [];
					for (var i = 0; i < commentList.length; i++) {
						commentList[i].color = this.getUserHeadBgColor(commentList[i].createUserId);
						var subCommentList = commentList[i].commentList || [];
						for (var j = 0; j < subCommentList.length; j++) {
							var subItem = subCommentList[j];
							subItem.color = this.getUserHeadBgColor(subItem.createUserId);
						}
						commentList[i].commentList = subCommentList;
					}
					this.commentList = commentList;
				});
			},
			zanPage(yn) {
				var param = {yn: yn, pageId: this.wikiPage.id};
				pageApi.updatePageZan(param).then(() => {
					this.wikiPage.selfZan = yn;
					this.wikiPage.zanNum = this.wikiPage.zanNum + (yn == 1 ? 1 : -1);
				});
			},
			showZanPageUser() {
				this.zanUserDialogVisible = true;
				this.zanUserList = [];
				var param = {pageId: this.wikiPage.id};
				pageApi.pageZanList(param).then(json => {
					this.zanUserList = json.data;
				});
			},
			recommentUser(id, index) {
				this.recommentInfo = {id: id, index: index, placeholder: '回复' + (index + 1) + '楼'};
			},
			cancelCommentUser() {
				this.recommentInfo = {};
			},
			submitPageComment() {
				if (this.commentTextInput.length <= 0) {
					this.$message.error("请输入评论内容");
					return;
				}
				var param = {
					pageId: this.wikiPage.id, content: this.commentTextInput,
					parentId: this.recommentInfo.id
				};
				pageApi.updatePageComment(param).then(json => {
					var data = json.data;
					data.color = this.getUserHeadBgColor(data.createUserId);
					this.commentTextInput = "";
					this.commentList.push(data);
				});
			},
			uploadFileError(err) {
				this.$message.error("上传失败，" + err);
			},
			uploadFileSuccess(response) {
				common.validateResult({data: response}).then(() => {
					this.pageFileList.push(response.data);
					this.$message.success("上传成功！");
				});
			},
			deletePageFile(row) {
				this.$confirm('确定要删除此文件吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let param = {id: row.id, delFlag: 1};
					pageApi.updatePageFile(param).then(() => {
						let pageFileList = [];
						for (let i = 0; i < this.pageFileList.length; i++) {
							if (this.pageFileList[i].id != row.id) {
								pageFileList.push(this.pageFileList[i]);
							}
						}
						this.pageFileList = pageFileList;
					});
				});
			},
			getUserHeadBgColor(userId) {
				let color = page.userHeadColor[userId];
				if (!color) {
					color = page.colorArr[Math.ceil(Math.random() * page.colorArr.length) - 1];
					page.userHeadColor[userId] = color;
				}
				return color;
			},
			initQueryParam(to) {
				this.parentPath = {
					spaceId: to.query.spaceId, pageId: to.query.pageId,
					parentId: to.query.parentId, path: to.query.path
				};
				if (!!this.parentPath.pageId) {
					this.loadPageDetail(this.parentPath.pageId);
				}
			},
		}
	}

</script>
<style>
	.page-show-vue .icon-collapse{float: left;font-size: 25px;color: #aaa;margin-top: 8px;cursor: pointer;}
	.page-show-vue .icon-collapse:hover{color: #eee;}
	.page-show-vue .wiki-title{font-size: 20px;}
	.page-show-vue .wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}
	.page-show-vue .wiki-content{font-size: 14px;}
	.page-show-vue .wiki-content.w-e-text{overflow-y: auto;}
	.page-show-vue .wiki-content.w-e-text img{cursor: auto;}
	.page-show-vue .wiki-content.w-e-text img:hover{box-shadow: unset;}

	.page-show-vue .upload-page-file .el-upload-list{display: none;}
	.page-show-vue .is-link{color: #1e88e5;cursor: pointer;}
	/*编辑框高度*/
	.page-show-vue #newPageContentDiv .w-e-text-container{height: 600px !important;}
	/*评论*/
	.page-show-vue .comment-box .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
</style>

