<template>
	<div class="page-show-vue">
		<el-row type="border-card" style="height: 100%;">
			<el-col :span="actionTabVisible?18:24" style="padding: 20px;border-right: 1px solid #f1f1f1;height: 100%;overflow: auto;">
				<div style="max-width: 1000px;margin: 0 auto;">
					<div class="wiki-title">{{wikiPage.name}}</div>
					<div class="wiki-author">
						<div>
							<span v-if="wikiPage.updateUserName">{{wikiPage.updateUserName}}　于　{{wikiPage.updateTime}}　修改</span>
							<span v-else class="create-user-time">{{wikiPage.createUserName}}　于　{{wikiPage.createTime}}　创建</span>
							<div style="float: right;">
								<el-button type="text" icon="el-icon-chat-line-round" @click="showCommentWiki" style="margin-right: 10px;">评论</el-button>
								<el-upload v-if="wikiPageAuth.canUploadFile==1"
										   class="upload-page-file" :action="uploadFileUrl"
										   :with-credentials="true"
										   :on-success="uploadFileSuccess" :on-error="uploadFileError"
										   name="files" show-file-list multiple :data="uploadFormData" :limit="999"
										   style="display: inline;margin-right: 10px;">
									<el-button type="text" icon="el-icon-upload">上传附件</el-button>
								</el-upload>
								<el-button v-if="wikiPageAuth.canEdit==1" type="text" icon="el-icon-edit" @click="editWiki">编辑</el-button>
								<el-dropdown style="margin-left: 10px;" @command="handleMoreCommand">
									<el-button type="text">
										更多<i class="el-icon-arrow-down el-icon--right"></i>
									</el-button>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item command="showPageHistory" icon="el-icon-time">查看历史版本</el-dropdown-item>
										<el-dropdown-item command="deletePage" v-if="wikiPageAuth.canDelete==1" icon="el-icon-delete">删除</el-dropdown-item>
										<el-dropdown-item command="editAuth" v-if="wikiPageAuth.canConfigAuth==1" icon="el-icon-s-check">权限设置</el-dropdown-item>
										<el-dropdown-item command="showOpenPage" v-if="spaceInfo.openDoc == 1" icon="el-icon-share">查看开放文档</el-dropdown-item>
									</el-dropdown-menu>
								</el-dropdown>
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
							<el-table-column label="文件大小">
								<template slot-scope="scope">{{computeFileSize(scope.row.fileSize)}}</template>
							</el-table-column>
							<el-table-column prop="createTime" label="创建时间" width="180px"></el-table-column>
							<el-table-column prop="downloadNum" label="下载次数" width="80px"></el-table-column>
							<el-table-column label="操作" width="100px" v-if="wikiPageAuth.canDeleteFile==1">
								<template slot-scope="scope">
									<el-button size="small" v-on:click="deletePageFile(scope.row)">删除</el-button>
								</template>
							</el-table-column>
						</el-table>
					</div>
					<div ref="pageContent" class="wiki-page-content">
						<div v-html="pageShowDetail" class="markdown-body" v-if="wikiPage.editorType == 2"></div>
						<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
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
				</div>
<!--				<div v-show="commentList.length > 0" class="comment-box" style="margin-top: 20px;">-->
<!--					<div style="border-bottom: 1px solid #67C23A;padding-bottom: 10px;">评论列表：</div>-->
<!--					<div v-for="(comment,index) in commentList" :key="comment.id" :data-id="comment.id" :data-index="index" style="border-bottom: 1px solid #eee;padding: 10px;">-->
<!--						<div>-->
<!--							<div :style="'background-color: '+comment.color" class="head">{{comment.createUserName.substr(0,1)}}</div>-->
<!--						</div>-->
<!--						<div style="padding-left: 55px;">-->
<!--							{{comment.createUserName}}-->
<!--							<span style="color: #888;font-size: 13px;padding-left: 10px;">{{comment.createTime}}</span>-->
<!--							<span style="color: #888;font-size: 13px;margin-left: 10px;cursor: pointer;" @click="recommentUser(comment.id, index)">回复</span>-->
<!--							<span style="color: #888;font-size: 13px;margin-left: 10px;cursor: pointer;" @click="deleteComment(comment.id)" v-if="canDeleteComment(comment)">删除</span>-->
<!--						</div>-->
<!--						<pre style="padding: 10px 0 0 55px;">{{comment.content}}</pre>-->
<!--						<div v-for="(commentSub,indexSub) in comment.commentList" :key="commentSub.id" :data-id="commentSub.id" :data-index="indexSub" style="border-bottom: 1px solid #eee;padding: 10px;margin-left: 40px;">-->
<!--							<div>-->
<!--								<div :style="'background-color: '+commentSub.color" class="head">{{commentSub.createUserName.substr(0,1)}}</div>-->
<!--							</div>-->
<!--							<div style="padding-left: 55px;">-->
<!--								{{commentSub.createUserName}}-->
<!--								<span style="color: #888;font-size: 13px;padding-left: 10px;">{{commentSub.createTime}}</span>-->
<!--								<span style="color: #888;font-size: 13px;margin-left: 10px;cursor: pointer;" @click="deleteComment(commentSub.id)" v-if="canDeleteComment(commentSub)">删除</span>-->
<!--							</div>-->
<!--							<pre style="padding: 10px 0 0 55px;">{{commentSub.content}}</pre>-->
<!--						</div>-->
<!--					</div>-->
<!--				</div>-->
<!--				<div style="margin: 20px 0 50px 0;">-->
<!--					<el-input type="textarea" v-model="commentTextInput" :rows="5" :placeholder="recommentInfo.placeholder || '请输入评论内容'"></el-input>-->
<!--					<div align="right" style="margin-top: 5px;">-->
<!--						<el-button type="primary" v-on:click="submitPageComment">提交评论</el-button>-->
<!--						<el-button v-on:click="cancelCommentUser" v-show="recommentInfo.id > 0">取消回复</el-button>-->
<!--					</div>-->
<!--				</div>-->
			</el-col>
			<el-col :span="6" style="height: 100%;" v-show="actionTabVisible">
				<i class="el-icon-close close-action-tab" @click="closeActionTab"></i>
				<el-tabs v-model="actionTabActiveName" @tab-click="actionTabClick">
					<el-tab-pane label="评论" name="comment">
						<div class="action-tab-box" ref="actionTabComment" style="padding-bottom: 130px;box-sizing: border-box;height: calc(100vh - 80px);">
							<div v-if="commentList.length <= 0" class="action-box-empty">暂无评论</div>
							<el-timeline v-else>
								<el-timeline-item :timestamp="comment.createTime" placement="top" v-for="comment in commentList">
									<el-card class="box-card comment-card" :body-style="{ padding: '10px' }">
										<div :style="'background-color: '+comment.color" class="head">{{comment.createUserName.substr(0,1)}}</div>
										<div class="comment-user-name">
											{{comment.createUserName}}
											<el-popover placement="top" width="160" v-model="comment.visible" v-if="canDeleteComment(comment)">
												<p>确定要除删此评论吗？</p>
												<div style="text-align: right; margin: 0">
													<el-button size="mini" type="text" @click="comment.visible=false">取消</el-button>
													<el-button type="primary" size="mini" @click="deleteComment(comment.id)">确定</el-button>
												</div>
												<i slot="reference" class="el-icon-delete"></i>
											</el-popover>
										</div>
										<pre class="comment-content">{{comment.content}}</pre>
									</el-card>
								</el-timeline-item>
							</el-timeline>
						</div>
						<div class="comment-input-box">
							<textarea rows="5" placeholder="发表评论" v-model="commentTextInput"></textarea>
							<el-button style="float: right;margin: 2px 5px;" type="primary" size="mini" v-on:click="submitPageComment">发送</el-button>
						</div>
					</el-tab-pane>
					<el-tab-pane label="修改历史" name="history">
						<div class="action-tab-box" v-infinite-scroll="getPageHistoryByScroll">
							<div v-if="pageHistoryList.length <= 0" class="action-box-empty">暂无修改历史记录</div>
							<el-timeline v-else>
								<el-timeline-item v-for="history in pageHistoryList">
									<el-tag :type="pageHistoryChoice.id == history.id ? history.loading==3?'danger':'success':'info'" class="history-item" @click="historyClick(history)">
										<div>{{history.createUserName}}</div>
										<div>{{history.createTime}}</div>
									</el-tag>
									<i class="el-icon-loading history-loading-status" v-show="history.loading==1"></i>
									<i class="el-icon-circle-check history-loading-status" v-show="history.loading==2"></i>
									<i class="el-icon-circle-close history-loading-status" v-show="history.loading==3"></i>
								</el-timeline-item>
							</el-timeline>
<!--							<div v-for="i in 100">{{i}}</div>-->
						</div>
					</el-tab-pane>
				</el-tabs>
			</el-col>
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
				<el-select v-model="pageAuthNewUser" filterable remote reserve-keyword autoComplete="new-password"
						placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList"
					   :loading="pageAuthUserLoading" style="width: 750px;margin-right: 10px;">
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
		<div ref="imagePreview">
			<el-image-viewer v-if="showImagePreview" :url-list="showImagePreviewList" :on-close="closeImagePreview" :initial-index="previewInitialIndex"/>
		</div>
	</div>
</template>

<script>
	import common from '../../common/lib/common'
	import pageApi from '../../common/api/page'
	import userApi from '../../common/api/user'
	import {mavonEditor, markdownIt} from 'mavon-editor'
	import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
	import 'mavon-editor/dist/markdown/github-markdown.min.css'
	import 'mavon-editor/dist/css/index.css'

	var page = {
		colorArr: ["#67C23A", "#409EFF", "#E6A23C", "#F56C6C", "#909399", "#303133"],
		userHeadColor: {},
	};
	export default {
		props: ['spaceInfo'],
		components: {'el-image-viewer': ElImageViewer},
		data() {
			return {
				// 页面展示相关
				wikiPage: {},
				wikiPageAuth: {},
				pageContent: {},
				pageFileList: [],
				selfUserId: 0,
				uploadFileList: [],
				uploadFormData: {pageId: 0},
				zanUserDialogVisible: false,
				zanUserList: [],
				parentPath: {},
				// 评论相关
				commentTextInput: "",
				commentList: [],
				recommentInfo: {},
				uploadFileUrl: process.env.VUE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload',
				// 页面权限
				pageAuthDialogVisible: false,
				pageAuthUserList: [],
                searchUserList: [],
                pageAuthNewUser: "",
				pageAuthUserLoading: false,
				// 右侧标签页
				actionTabVisible: false,
				actionTabActiveName: 'comment',
				pageHistoryDetail: '',
				pageShowDetail: '',
				pageHistoryChoice: {},
				pageHistoryList: [],
				pageHistoryPageNum: 1,
				// 大图预览
				previewInitialIndex: 0,
				showImagePreview: false,
				showImagePreviewList: [],
			};
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			this.initQueryParam(this.$route);
		},
		methods: {
			editWiki() {
				var param = {pageId: this.parentPath.pageId};
				pageApi.pageLock(param).then(() => {
					this.$router.push({path: '/page/edit', query: {pageId: this.parentPath.pageId}});
				});
			},
			getSearchUserList(query) {
				if (query == '') return;
				this.pageAuthUserLoading = true;
				userApi.getUserBaseInfo({search: query}).then(json => {
					this.searchUserList = json.data || [];
					this.pageAuthUserLoading = false;
				});
			},
			handleMoreCommand(val) {
				if (val == 'editAuth') {
					this.editWikiAuth();
				} else if (val == 'deletePage') {
					this.deleteWikiPage();
				} else if (val == 'showPageHistory') {
					this.showPageHistory();
				} else if (val == 'showOpenPage') {
					if (this.spaceInfo.openDoc != 1) {
						this.$message.warning("该空间未开放，无法查看开放文档地址");
					} else {
						let routeUrl = this.$router.resolve({
							path: '/page/share/view',
							query: {pageId: this.wikiPage.id, space: this.spaceInfo.uuid}
						});
						window.open(routeUrl.href, '_blank');
					}
				}
			},
            addPageAuthUser() {
                if (this.pageAuthNewUser.length <= 0) {
					this.$message.warning("请先选择用户");
                    return;
                }
				if (!!this.searchUserList.find(item => item.userId == this.pageAuthNewUser)) {
					this.pageAuthNewUser = "";
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
			actionTabClick(tab) {
				if (tab.name == 'comment') {
					this.showCommentWiki();
				} else if (tab.name == 'history') {
					this.showPageHistory();
				}
			},
			closeActionTab() {
				this.actionTabVisible = false;
				this.clearHistory();
			},
			showCommentWiki() {
				this.actionTabVisible = true;
				this.actionTabActiveName = 'comment';
				this.scrollActionTabComment();
			},
			showPageHistory() {
				this.actionTabVisible = true;
				this.actionTabActiveName = 'history';
				this.clearHistory();
			},
			getPageHistoryByScroll() {
				if (this.pageHistoryPageNum <= 0) {
					return;
				}
				this.pageHistoryPageNum++;
				this.getPageHistory(this.wikiPage.id, this.pageHistoryPageNum);
			},
			getPageHistory(pageId, pageNum) {
				if (pageNum == 1) {
					this.pageHistoryList = [];
					this.pageHistoryPageNum = 1;
				}
				let param = {pageId: pageId, pageNum: pageNum};
				pageApi.pageHistoryList(param).then(json => {
					let pageHistoryList = json.data || [];
					if (pageHistoryList.length <= 0) {
						this.pageHistoryPageNum = 0;
					} else {
						pageHistoryList.forEach(item => item.loading = 0);
						this.pageHistoryList = this.pageHistoryList.concat(pageHistoryList);
					}
				});
			},
			historyClick(history) {
				if (this.pageHistoryChoice.id == history.id && !!this.pageHistoryDetail) {
					return;
				}
				this.pageHistoryChoice.loading = 0;
				this.pageHistoryChoice = history;
				// 缓存一下，但如果历史页面多了而且很大就占内存，也可以每次去拉取，先这样吧
				if (history.content) {
					history.loading = 2;
					this.pageHistoryDetail = history.content;
					this.pageShowDetail = history.content;
					setTimeout(() => this.previewPageImage(), 500);
				} else {
					history.loading = 1;
					pageApi.pageHistoryDetail({id: history.id}).then(json => {
						history.loading = 2;
						history.content = json.data || '--';
						if (this.wikiPage.editorType === 2) {
							history.content = markdownIt.render(history.content);
						}
						this.pageHistoryDetail = history.content;
						this.pageShowDetail = history.content;
						setTimeout(() => this.previewPageImage(), 500);
					}).catch(() => {
						history.loading = 3;
					});
				}
			},
			clearHistory() {
				this.pageHistoryChoice.loading = 0;
				this.pageHistoryDetail = '';
				this.pageHistoryChoice = {};
				this.pageHistoryList.forEach(item => item.loading = 0);
				this.pageShowDetail = this.pageContent.content;
			},
			computeFileSize(fileSize) {
				if (!fileSize) {
					return '-';
				}
				let size = "";
				if (fileSize < 0.1 * 1024) {
					size = fileSize.toFixed(2) + "B"
				} else if (fileSize < 0.1 * 1024 * 1024) {
					size = (fileSize / 1024).toFixed(2) + "KB"
				} else if (fileSize < 0.1 * 1024 * 1024 * 1024) {
					size = (fileSize / (1024 * 1024)).toFixed(2) + "MB"
				} else {
					size = (fileSize / (1024 * 1024 * 1024)).toFixed(2) + "GB"
				}
				let sizeStr = size + "";
				let index = sizeStr.indexOf(".");
				let dou = sizeStr.substr(index + 1, 2);
				if (dou == "00") {
					return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
				}
				return size;
			},
			loadPageDetail(pageId) {
				this.clearHistory();
				pageApi.pageDetail({id: pageId}).then(json => {
					let result = json.data || {};
					let wikiPage = result.wikiPage || {};
					wikiPage.selfZan = result.selfZan || 0;
					this.wikiPage = wikiPage;
					this.pageContent = result.pageContent || {};
					this.pageFileList = result.fileList || [];
					this.selfUserId = result.selfUserId || 0;
					this.uploadFormData = {pageId: this.wikiPage.id};
					this.wikiPageAuth = {
						canEdit: result.canEdit,
						canDelete: result.canDelete,
						canUploadFile: result.canUploadFile,
						canDeleteFile: result.canDeleteFile,
						canConfigAuth: result.canConfigAuth,
					};
					if (this.wikiPage.editorType === 2) {
						this.pageContent.content = markdownIt.render(this.pageContent.content);
					}
					this.pageShowDetail = this.pageContent.content;
					// 修改标题
					document.title = wikiPage.name || 'WIKI-内容展示';
					// 修改最后点击的项，保证刷新后点击编辑能展示编辑的项
					// if (!this.lastClickNode.id) {
					// 	this.lastClickNode = {id: wikiPage.id, nodePath: wikiPage.name};
					// }
					// 调用父方法切换选择的空间
					this.$emit('switchSpace', this.wikiPage.spaceId);
					// 调用父方法展开目录树
					this.$emit('changeExpandedKeys', pageId);
					setTimeout(() => this.previewPageImage(), 500);
				});
				this.loadCommentList(pageId);
				this.getPageHistory(pageId, 1);
			},
			closeImagePreview() {
				this.showImagePreview = false;
			},
			previewPageImage() {
				const imgArr = [];
				const imgSelector = this.$refs.pageContent.querySelectorAll('img');
				imgSelector.forEach((item, index) => {
					imgArr.push(item.src);
					item.onclick = () => {
						this.previewInitialIndex = index;
						this.showImagePreviewList = imgArr;
						this.showImagePreview = true;
						setTimeout(() => this.initImageViewerMask(), 0);
					}
				});
			},
			initImageViewerMask() {
				// 图片预览遮罩点击隐藏预览框
				let imageViewerMask = this.$refs.imagePreview.querySelectorAll('.el-image-viewer__mask');
				imageViewerMask.forEach(item => {
					item.onclick = () => {
						this.showImagePreview = false;
					}
				});
			},
			loadCommentList(pageId) {
				this.cancelCommentUser();
				pageApi.pageCommentList({pageId: pageId}).then(json => {
					let commentList = json.data || [];
					for (let i = 0; i < commentList.length; i++) {
						commentList[i].color = this.getUserHeadBgColor(commentList[i].createUserId);
						let subCommentList = commentList[i].commentList || [];
						for (let j = 0; j < subCommentList.length; j++) {
							let subItem = subCommentList[j];
							subItem.color = this.getUserHeadBgColor(subItem.createUserId);
						}
						commentList[i].commentList = subCommentList;
						commentList[i].visible = false;
					}
					this.commentList = commentList;
					this.scrollActionTabComment();
				});
			},
			scrollActionTabComment() {
				setTimeout(() => {
					let actionTabComment = this.$refs.actionTabComment;
					actionTabComment.scrollTop = actionTabComment.scrollHeight;
				}, 0);
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
			canDeleteComment(row) {
				return this.selfUserId == row.createUserId || this.wikiPage.createUserId == this.selfUserId;
			},
			deleteComment(id) {
				pageApi.deletePageComment({id: id}).then(() => {
					// this.$message.success("删除成功！");
					this.loadCommentList(this.parentPath.pageId);
				});
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
					this.loadCommentList(this.parentPath.pageId);
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
					let param = {id: row.id};
					pageApi.deletePageFile(param).then(() => {
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
				this.parentPath = {pageId: to.query.pageId};
				if (!!this.parentPath.pageId) {
					this.loadPageDetail(this.parentPath.pageId);
				}
			},
		}
	}
</script>
<style>
	@import "../../common/lib/wangEditor.css";
	/*覆盖样式*/
	.el-main{overflow: hidden;}
	.page-show-vue {height: 100%;}
	.page-show-vue .icon-collapse{float: left;font-size: 25px;color: #aaa;margin-top: 8px;cursor: pointer;}
	.page-show-vue .icon-collapse:hover{color: #eee;}
	.page-show-vue .wiki-title{font-size: 20px;text-align: center;font-weight: bold;}
	.page-show-vue .create-user-time{margin-right: 20px;}
	.page-show-vue .wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}
	.page-show-vue .wiki-page-content img{cursor: pointer;max-width: 100%;}
	.page-show-vue .wiki-page-content img:hover{box-shadow: 0 2px 6px 0 rgba(0,0,0,.3);}

	.page-show-vue .upload-page-file .el-upload-list{display: none;}
	.page-show-vue .is-link{color: #1e88e5;cursor: pointer;}
	/*编辑框高度*/
	.page-show-vue #newPageContentDiv .w-e-text-container{height: 600px !important;}
	/*评论*/
	.page-show-vue .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
	.page-show-vue .el-tabs__header{
		margin: 0;
	}
	.page-show-vue .el-tabs__nav-wrap{
		padding: 0 20px;
	}
	.page-show-vue .close-action-tab{
		position: absolute;right: 15px;top: 12px;cursor: pointer;z-index: 1;
	}
	.page-show-vue .action-tab-box{
		height: calc(100vh - 120px);overflow: auto;padding: 20px 10px;
	}
	.page-show-vue .action-box-empty{
		text-align: center;padding-top: 30px;color: #888;font-size: 14px;
	}
	.page-show-vue .history-item{
		height: 55px;line-height: 25px;cursor: pointer;vertical-align: middle;
	}
	.page-show-vue .history-loading-status{
		margin-left: 5px;color: #67c23a;
	}
	.page-show-vue .history-loading-status.el-icon-circle-close{
		color: #f56c6c;
	}
	.page-show-vue .el-timeline{
		padding-inline-start: 0;
	}
	.page-show-vue .comment-user-name{
		margin-bottom: 10px;
	}
	.page-show-vue .comment-content{
		padding: 0;color: #666;margin: 0;white-space: pre-wrap;word-wrap: break-word;line-height: 20px;
	}
	.page-show-vue .comment-input-box{
		position: absolute;bottom: 0;width: 100%;background: #fff;border-top: 1px solid #f1f1f1;
	}
	.page-show-vue .comment-input-box textarea{
		resize: none;width: 100%;box-sizing: border-box;border: 0;outline:none !important;padding: 10px;
	}
	.page-show-vue .comment-card .comment-user-name .el-icon-delete{
		color: #888;font-size: 13px;cursor: pointer;float: right;display: none;
	}
	.page-show-vue .comment-card .comment-user-name .el-icon-delete{
		color: #888;font-size: 13px;cursor: pointer;float: right;display: none;
	}
	.page-show-vue .comment-card:hover .comment-user-name .el-icon-delete{
		display: inline-block;
	}
</style>

