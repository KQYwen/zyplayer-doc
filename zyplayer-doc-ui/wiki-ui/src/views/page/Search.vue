<template>
	<div style="background: #f1f1f1;min-height: 100%;" class="page-search-vue">
		<div style="max-width: 1200px;margin: 0 auto;background: #fff;padding: 20px;min-height: 100%;box-sizing: border-box;">
			<div style="margin-bottom: 20px;">
				<el-row :gutter="20" style="max-width: 700px; margin: 0 auto;">
					<el-col :span="20">
						<el-input v-model="searchParam.keywords" @keyup.enter.native="getSpacePageNews" placeholder="" style="width: 100%;"></el-input>
					</el-col>
					<el-col :span="4">
						<el-button type="primary" @click="getSpacePageNews" icon="el-icon-search">搜索一下</el-button>
					</el-col>
				</el-row>
			</div>
			<div v-if="spacePageNews.length <= 0" class="empty-news">暂无数据</div>
			<div v-else class="line-box" v-for="item in spacePageNews">
				<div class="line-title">
					<span class="text-link">{{item.createUserName}}</span> 发布于 <span class="text-link">{{item.spaceName}}</span>
				</div>
				<div class="page-preview-box">
					<div class="page-preview-title" v-on:click="showPageDetail(item)" v-html="item.pageTitle"></div>
					<div class="page-preview-content" v-html="item.previewContent"></div>
					<div>
						<span><img src="../../assets/img/zan.png" class="zan-img"> {{item.zanNum}}　</span>
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
	import pageApi from '../../common/api/page'

	export default {
		data() {
			return {
				totalCount: 0,
				searchParam: {
					spaceId: '',
					keywords: '',
					newsType: 1,
					pageNum: 1,
					pageSize: 20,
				},
				spacePageNews:[],
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
				pageApi.pageSearchByEs(this.searchParam).then(json => {
					this.spacePageNews = json.data || [];
					this.totalCount = json.total;
				});
			},
			handleSizeChange(val) {
				this.searchParam.pageSize = val;
				this.getSpacePageNews();
			},
			showPageDetail(row) {
				window.open('#/page/show?spaceId=' + row.spaceId + "&pageId=" + row.pageId);
			},
			handleCurrentChange(val) {
				this.searchParam.pageNum = val;
				this.getSpacePageNews();
			},
			initQueryParam(to) {
				this.searchParam = {
					keywords: to.query.keywords,
					spaceId: to.query.spaceId,
					newsType: 1,
					pageNum: 1,
					pageSize: 20,
				};
				this.getSpacePageNews();
			},
		}
	}
</script>
<style>
	.page-search-vue .empty-news{text-align: center;padding: 100px;}

	.page-search-vue .text-link {
		color: #444;
		/*cursor: pointer;*/
		/*font-weight: bold;*/
	}
	.page-search-vue .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
	.page-search-vue .line-title{font-size: 14px;}
	.page-search-vue .page-preview-box{}
	.page-search-vue .page-preview-title{font-size: 18px;margin: 10px 0 5px 0;color: #3a8ee6;cursor: pointer;}
	.page-search-vue .page-preview-content{font-size: 16px;margin-bottom: 5px;}
	.page-search-vue .zan-img{vertical-align: middle;margin-top: -3px;}
	.page-search-vue .view-img{font-size: 16px;color: #666;}

	.page-search-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

