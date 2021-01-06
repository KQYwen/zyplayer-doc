<template>
	<div style="padding: 10px;" class="page-share-home-vue">
		<div style="max-width: 800px;margin: 0 auto;">
			<div v-if="spacePageNews.length <= 0" class="empty-news">暂无数据</div>
			<div v-else class="line-box" v-for="item in spacePageNews">
				<div class="line-title">
					<span class="text-link">{{item.createUserName}}</span> 发布于 <span class="text-link">{{item.spaceName}}</span>
				</div>
				<div class="page-preview-box">
					<div class="page-preview-title" v-on:click="showPageDetail(item)" v-html="item.pageTitle"></div>
					<div class="page-preview-content" v-html="item.previewContent"></div>
					<div>
						<span><img src="../../../../assets/img/zan.png" class="zan-img"> {{item.zanNum}}　</span>
						<span><i class="el-icon-view view-img"></i> {{item.viewNum}}　</span>
						<span>{{item.updateTime||item.createTime}}</span>
					</div>
				</div>
			</div>
			<div class="page-info-box">
				<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:page-sizes="[20, 50, 100]"
					:page-size="20"
					:current-page="searchParam.pageNum"
					layout="prev, pager, next, jumper, sizes, total"
					:total="totalCount"
				>
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import pageApi from '../../../../common/api/page'

	export default {
		data() {
			return {
				totalCount: 0,
				searchParam: {
					spaceId: '',
					newsType: 1,
					pageNum: 1,
					pageSize: 20,
				},
				spacePageNews: [],
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
			getSpacePageNews() {
				pageApi.openPageNews(this.searchParam).then(json => {
					this.spacePageNews = json.data || [];
					this.totalCount = json.total;
				});
			},
			handleSizeChange(val) {
				this.searchParam.pageSize = val;
				this.getSpacePageNews();
			},
			showPageDetail(row) {
				this.nowClickPath = {space: row.space, pageId: row.pageId};
				this.$router.push({path: '/page/share/view', query: this.nowClickPath});
			},
			handleCurrentChange(val) {
				this.searchParam.pageNum = val;
				this.getSpacePageNews();
			},
			initQueryParam(to) {
				this.searchParam = {
					space: to.query.space,
					newsType: 1,
					pageNum: 1,
					pageSize: 20,
				};
				if (!!this.searchParam.space) {
					this.getSpacePageNews();
				}
			},
		}
	}
</script>
<style>
	.page-share-home-vue .empty-news{text-align: center;padding: 100px;}
	.page-share-home-vue .text-link {
		color: #444;
	}
	.page-share-home-vue .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
	.page-share-home-vue .line-title{font-size: 14px;}
	.page-share-home-vue .page-preview-box{}
	.page-share-home-vue .page-preview-title{cursor: pointer;font-size: 20px;margin: 10px 0 5px 0;color: #3a8ee6;}
	.page-share-home-vue .page-preview-content{font-size: 16px;margin-bottom: 5px;}
	.page-share-home-vue .zan-img{vertical-align: middle;margin-top: -3px;}
	.page-share-home-vue .view-img{font-size: 16px;color: #666;}

	.page-share-home-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

