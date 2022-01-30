<template>
	<div class="page-share-view-vue">
		<el-row type="border-card">
			<el-row>
				<el-col :xs="0" :sm="4" :md="4" :lg="6" :xl="6" v-if="navigationList.length > 0">
					<Navigation :heading="navigationList"></Navigation>
				</el-col>
				<el-col :xs="24" :sm="navigationList.length > 0?20:24" :md="navigationList.length > 0?20:24" :lg="navigationList.length > 0?18:24" :xl="navigationList.length > 0?18:24">
					<div style="max-width: 1000px;padding-left: 10px;margin: 0 auto;">
						<div class="wiki-title" ref="wikiTitle">{{wikiPage.name}}</div>
						<div class="wiki-author">
							<span v-if="wikiPage.updateTime">最后修改：{{wikiPage.updateTime}}</span>
							<span v-else>创建时间：{{wikiPage.createTime}}</span>
						</div>
						<div class="wiki-files">
							<el-table v-show="pageFileList.length > 0" :data="pageFileList" border style="width: 100%; margin-bottom: 5px;">
								<el-table-column label="文件名">
									<template slot-scope="scope">
										<a target="_blank" :href="scope.row.fileUrl">{{scope.row.fileName}}</a>
									</template>
								</el-table-column>
								<el-table-column label="文件大小">
									<template slot-scope="scope">{{computeFileSize(scope.row.fileSize)}}</template>
								</el-table-column>
								<el-table-column prop="createTime" label="创建时间" width="180px"></el-table-column>
								<el-table-column prop="downloadNum" label="下载次数" width="80px"></el-table-column>
							</el-table>
						</div>
						<div ref="pageContent" class="wiki-page-content">
							<div v-html="pageShowDetail" class="markdown-body" v-if="wikiPage.editorType == 2"></div>
							<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
						</div>
					</div>
				</el-col>
			</el-row>
		</el-row>
		<div ref="imagePreview">
			<el-image-viewer v-if="showImagePreview" :url-list="showImagePreviewList" :on-close="closeImagePreview" :initial-index="previewInitialIndex"></el-image-viewer>
		</div>
	</div>
</template>

<script>
	import pageApi from '../../../../common/api/page'
	import {mavonEditor} from 'mavon-editor'
	import unitUtil from '../../../../common/lib/UnitUtil.js'
	import htmlUtil from '../../../../common/lib/HtmlUtil.js'
	import Navigation from '../../components/Navigation.vue'
	import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
	import 'mavon-editor/dist/markdown/github-markdown.min.css'
	import 'mavon-editor/dist/css/index.css'

	export default {
		data() {
			return {
				spaceUuid: '',
				nowPageId: '',
				// 页面展示相关
				wikiPage: {},
				pageFileList: [],
				pageShowDetail: '',
				// 大图预览
				previewInitialIndex: 0,
				showImagePreview: false,
				showImagePreviewList: [],
				navigationList: [],
			};
		},
		components: {'el-image-viewer': ElImageViewer, Navigation},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted() {
			this.initQueryParam(this.$route);
		},
		methods: {
			loadPageDetail(pageId) {
				let param = {pageId: pageId, space: this.spaceUuid};
				pageApi.openPageDetail(param).then(json => {
					let wikiPage = json.data.wikiPage || {};
					wikiPage.selfZan = json.data.selfZan || 0;
					this.wikiPage = wikiPage;
					let pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
					if (this.wikiPage.editorType === 2) {
						pageContent.content = mavonEditor.getMarkdownIt().render(pageContent.content);
					}
					this.pageShowDetail = pageContent.content;
					let wikiTile = wikiPage.name || 'WIKI-内容展示';
					document.title = wikiTile;
					setTimeout(() => {
						this.previewPageImage();
						let navigationList = htmlUtil.createNavigationHeading();
						// 标题加到导航里面去
						if (navigationList.length > 0) {
							navigationList.unshift({
								level: 1,
								node: this.$refs.wikiTitle,
								text: wikiTile
							});
						}
						this.navigationList = navigationList;
					}, 500);
				});
			},
			initQueryParam(to) {
				this.spaceUuid = to.query.space;
				this.nowPageId = to.query.pageId;
				if (!!this.nowPageId) {
					this.loadPageDetail(this.nowPageId);
				}
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
					item.onclick = () => this.showImagePreview = false;
				});
			},
		}
	}
</script>

<style>
	@import "../../../../common/lib/wangEditor.css";

	.page-share-view-vue .wiki-title{font-size: 20px;text-align: center;}
	.page-share-view-vue .wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}

	.page-share-view-vue .wiki-page-content img{cursor: pointer;max-width: 100%;}
	.page-share-view-vue .wiki-page-content img:hover{box-shadow: 0 2px 6px 0 rgba(0,0,0,.3);}

	.page-share-view-vue .upload-page-file .el-upload-list{display: none;}
	.page-share-view-vue .is-link{color: #1e88e5;cursor: pointer;}
</style>

