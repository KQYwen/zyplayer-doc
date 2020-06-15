<template>
	<div>
		<el-container style="height: 100%;">
			<el-aside width="280px" style="height: 100%;background-color: #fafafa;" v-show="leftCollapse">
				<div class="logo">{{nowSpaceShow.name}}</div>
				<div style="padding: 0 10px 50px 10px;">
					<el-input v-model="searchKeywords" @keyup.enter.native="searchByKeywords" placeholder="搜索文档" style="margin: 10px 0;">
						<el-button slot="append" icon="el-icon-search" v-on:click="searchByKeywords"></el-button>
					</el-input>
					<el-tree :props="defaultProps" :data="wikiPageList" @node-click="handleNodeClick"
							 ref="wikiPageTree" :filter-node-method="filterPageNode" highlight-current
							 :expand-on-click-node="false" :default-expanded-keys="wikiPageExpandedKeys"
							 node-key="id"
							 style="background-color: #fafafa;">
					</el-tree>
					<!--请手下留情，别删我(╥╯^╰╥)，给我一个露脸的机会，我长的不碍眼的-->
					<div class="build-info">本文档使用<span @click="aboutDialogVisible = true">zyplayer-doc</span>构建</div>
				</div>
			</el-aside>
			<el-container>
				<el-main class="doc-body-box">
					<el-row type="border-card" v-show="rightContentType == 0">
						<div style="margin-top: 30px;color: #666; text-align: center; font-size: 30px;">欢迎使用在线文档</div>
						<div style="margin-top: 30px;color: #666; text-align: center;">
							{{nowSpaceShow.name}}
							<span v-show="nowSpaceShow.spaceExplain && nowSpaceShow.spaceExplain.length > 0"> · {{nowSpaceShow.spaceExplain}}</span>
						</div>
					</el-row>
					<el-row type="border-card" v-show="rightContentType == 1">
						<i class="el-icon-menu icon-collapse" @click="leftCollapse = !leftCollapse"></i>
						<div style="max-width: 950px;margin: 0 auto;">
							<div class="wiki-title">{{wikiPage.name}}</div>
							<div class="wiki-author">
								<span v-show="!wikiPage.updateTime">创建时间：{{wikiPage.createTime}}</span>
								<span v-show="wikiPage.updateTime">最后修改：{{wikiPage.updateTime}}</span>
							</div>
							<div class="wiki-files">
								<el-table v-show="pageFileList.length > 0" :data="pageFileList" border style="width: 100%; margin-bottom: 5px;">
									<el-table-column label="文件名">
										<template slot-scope="scope">
											<a target="_blank" :href="scope.row.fileUrl">{{scope.row.fileName}}</a>
										</template>
									</el-table-column>
									<el-table-column prop="createTime" label="创建时间" width="180px"></el-table-column>
									<el-table-column prop="downloadNum" label="下载次数" width="80px"></el-table-column>
								</el-table>
							</div>
							<div class="wiki-content w-e-text">
								<div v-html="pageContent.content"></div>
							</div>
						</div>
					</el-row>
				</el-main>
			</el-container>
		</el-container>
		<!--关于弹窗-->
		<el-dialog title="关于zyplayer-doc-wiki" :visible.sync="aboutDialogVisible" width="600px">
			<el-form>
				<el-form-item label="项目地址：">
					<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>
				</el-form-item>
				<el-form-item label="开发人员：">
					<a target="_blank" href="http://zyplayer.com">暮光：城中城</a>
				</el-form-item>
				<el-form-item label="">
					欢迎加群讨论，QQ群号：466363173，欢迎提交需求，欢迎使用和加入开发！
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	import pageApi from '../../common/api/page'

	export default {
		data() {
			return {
				leftCollapse: true,
				aboutDialogVisible: false,
				rightContentLoading: false,
				rightContentType: 0,// 右侧显示类型，0=欢迎页 1=文章内容 2=编辑或新增文章
				pathIndex: [],
				defaultProps: {
					children: 'children',
					label: 'name'
				},
				// 空间搜索相关
				nowSpaceShow: {},
				// 搜索的输入内容
				searchKeywords: "",
				lastClickNode: {},
				// 页面展示相关
				wikiPageList:[],
				wikiPage: {},
				wikiPageExpandedKeys: [],
				pageContent: {},
				pageFileList: [],
				// 页面跳转相关
				parentPath: {},
			}
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			this.initQueryParam(this.$route);
			this.getSpaceInfo();
			this.doGetPageList(null);
		},
		methods: {
			filterPageNode(value, data) {
				if (!value) return true;
				return data.name.indexOf(value) !== -1;
			},
			handleNodeClick(data) {
				this.rightContentType = 1;
				if (this.lastClickNode.id == data.id) {
					return;
				}
				console.log("点击节点：", data);
				this.lastClickNode = data;
				this.nowClickPath = {pageId: this.lastClickNode.id, space: this.parentPath.space};
				this.$router.push({path: '/page/openView', query: this.nowClickPath});
			},
			loadPageDetail(pageId) {
				this.rightContentType = 1;
				let param = {pageId: pageId, space: this.parentPath.space};
				pageApi.openPageDetail(param).then(json => {
					let wikiPage = json.data.wikiPage || {};
					wikiPage.selfZan = json.data.selfZan || 0;
					this.wikiPage = wikiPage;
					this.pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
				});
			},
			searchByKeywords() {
				this.$refs.wikiPageTree.filter(this.searchKeywords);
			},
			doGetPageList() {
				let nodePath = "/";
				let param = {space: this.parentPath.space};
				pageApi.openPageList(param).then(json => {
					let pathIndex = json.data || [];
					this.createNodePath(pathIndex, nodePath);
					this.wikiPageList = pathIndex;
					this.lastClickNode = {};
				});
			},
			getSpaceInfo() {
				let param = {space: this.parentPath.space};
				pageApi.openSpaceInfo(param).then(json => {
					this.nowSpaceShow = json.data;
				});
			},
			createNodePath(node, nodePath) {
				if (!nodePath.endsWith("/")) {
					nodePath += "/";
				}
				for (let i = 0; i < node.length; i++) {
					let item = node[i];
					item.nodePath = nodePath + item.name;
					if (!!item.children && item.children.length > 0) {
						this.createNodePath(item.children, item.nodePath);
					}
				}
			},
			initQueryParam(to) {
				this.parentPath = {space: to.query.space, pageId: to.query.pageId};
				if (!!this.parentPath.pageId) {
					this.loadPageDetail(this.parentPath.pageId);
				}
			},
		}
	};
</script>

<style scoped>
	html,body,#app {margin: 0; padding: 0; height: 100%;}
	pre{margin: 0;white-space: pre-wrap;font-size: 14px; font-family: auto;}
	.el-menu {box-sizing: border-box;border-right: 0;margin-right: 3px;}
	.el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}
	.doc-body-box{
		overflow-x: hidden;overflow-y: auto;width: 100%;
		padding: 10px;border-left: 1px solid #f1f1f1; box-sizing: border-box;
	}
	.el-tree{margin-right: 3px;}
	.logo{
		/*background: #409EFF; cursor: pointer;*/
		border-bottom: 1px solid #f1f1f1;
		overflow: hidden;white-space: nowrap;text-overflow: ellipsis; padding: 5px 10px;
		width: 260px; height:40px;line-height:40px;font-size: 25px;color: #666;text-align: center;
	}
	.icon-collapse{float: left;font-size: 25px;color: #aaa;cursor: pointer;position: fixed;}
	.icon-collapse:hover{color: #ccc;}
	.wiki-title{font-size: 20px;text-align: center;}
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
	.build-info{
		position: fixed;bottom: 0;left: 0;background: #fafafa;width: 280px;text-align: center;
		padding: 5px 0;color: #aaa;font-size: 12px;
	}
	.build-info span{color: #4183c4;cursor: pointer;}
	/* S-JSON展示的样式 */
	pre.json {
		display: block;
		padding: 9.5px;
		margin: 0 0 0 10px;
		font-size: 12px;
		line-height: 1.38461538;
		color: #333;
		word-break: break-all;
		word-wrap: break-word;
		background-color: #f5f5f5;
		border: 1px solid #ccc;
		border-radius: 4px;
	}
	pre.json .canvas{font:10pt georgia;background-color:#ececec;color:#000000;border:1px solid #cecece;}
	pre.json .object-brace{color:#00aa00;font-weight:bold;}
	pre.json .array-brace{color:#0033ff;font-weight:bold;}
	pre.json .property-name{color:#cc0000;font-weight:bold;}
	pre.json .string{color:#007777;}
	pre.json .number{color:#aa00aa;}
	pre.json .boolean{color:#0000ff;}
	pre.json .function{color:#aa6633;text-decoration:initial;}
	pre.json .null{color:#0000ff;}
	pre.json .comma{color:#000000;font-weight:bold;}
	pre.json .annotation{color:#aaa;}
	pre img{cursor: pointer;}
	/* E-JSON展示的样式 */
</style>

