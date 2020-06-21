<template>
	<div class="page-share-view-vue">
		<el-row type="border-card">
			<div style="max-width: 950px;margin: 0 auto;">
				<div class="wiki-title">{{wikiPage.name}}</div>
				<div class="wiki-author">
					<span v-show="!wikiPage.updateTime">创建时间：{{wikiPage.createTime}}</span>
					<span v-show="wikiPage.updateTime">最后修改：{{wikiPage.updateTime}}</span>
				</div>
				<div class="wiki-files">
					<el-table v-show="pageFileList.length > 0" :data="pageFileList" border
							  style="width: 100%; margin-bottom: 5px;">
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
	</div>
</template>

<script>
	import pageApi from '../../../common/api/page'
	import {mavonEditor, markdownIt} from 'mavon-editor'

	export default {
		data() {
			return {
				spaceUuid: '',
				nowPageId: '',
				// 页面展示相关
				wikiPage: {},
				pageContent: {},
				pageFileList: [],
			};
		},
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
					this.pageContent = json.data.pageContent || {};
					this.pageFileList = json.data.fileList || [];
					if (this.wikiPage.editorType === 2) {
						this.pageContent.content = markdownIt.render(this.pageContent.content);
					}
					document.title = wikiPage.name || 'WIKI-内容展示';
				});
			},
			initQueryParam(to) {
				this.spaceUuid = to.query.space;
				this.nowPageId = to.query.pageId;
				if (!!this.nowPageId) {
					this.loadPageDetail(this.nowPageId);
				}
			},
		}
	}

</script>

<style>
	.page-share-view-vue .wiki-title{font-size: 20px;text-align: center;}
	.page-share-view-vue .wiki-author{font-size: 14px;color: #888;padding: 20px 0;height: 40px;line-height: 40px;}
	.page-share-view-vue .wiki-content{font-size: 14px;}
	.page-share-view-vue .wiki-content.w-e-text{overflow-y: auto;}

	.page-share-view-vue .upload-page-file .el-upload-list{display: none;}
	.page-share-view-vue .is-link{color: #1e88e5;cursor: pointer;}
</style>

