<template>
	<div style="padding: 10px;" class="page-share-home-vue">
		<nut-button @click="popupShowChange">测试</nut-button>
	</div>
</template>

<script>
	import pageApi from '../../../../common/api/page';
	import Button from '@nutui/nutui/dist/packages/button/button.js';
	import '@nutui/nutui/dist/packages/button/button.css';
	import Popup from '@nutui/nutui/dist/packages/popup/popup.js';
	import '@nutui/nutui/dist/packages/popup/popup.css';

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
		components: {
			'nut-button': Button,
			'nut-popup': Popup,
		},
		beforeRouteUpdate(to, from, next) {
			this.initQueryParam(to);
			next();
		},
		mounted: function () {
			this.initQueryParam(this.$route);
		},
		methods: {
			popupShowChange() {
				this.$emit("popupShow", true);
			},
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

