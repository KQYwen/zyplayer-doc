<template>
	<div class="share-mobile-layout">
		<van-popup v-model="popupShow" closeable position="left" class="popup-module" :style="{ height: '100%', width: '80%' }">
			<div class="header">
				<van-nav-bar :title="nowSpaceShow.name"></van-nav-bar>
			</div>
			<div class="main">
				<page-tree :page-list="wikiPageList" @pageChange="pageSelectChange"></page-tree>
				<div class="build-info">本文档使用<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>构建</div>
			</div>
		</van-popup>
		<router-view @popupShow="popupShowChange"></router-view>
	</div>
</template>

<script>
	import Vue from 'vue';
	import pageApi from '../../common/api/page'
	import PageTree from './PageTree'
	import Vant from 'vant';
	import 'vant/lib/icon/index.css';
	import 'vant/lib/popup/index.css';
	import 'vant/lib/cell/index.css';
	import 'vant/lib/nav-bar/index.css';
	import 'vant/lib/collapse-item/index.css';
	Vue.use(Vant);

	export default {
		data() {
			return {
				defaultProps: {
					children: 'children',
					label: 'name'
				},
				// 空间搜索相关
				spaceUuid: '',
				nowPageId: '',
				nowSpaceShow: {},
				// 搜索的输入内容
				searchKeywords: "",
				// 页面展示相关
				wikiPageList:[],
				wikiPageExpandedKeys: [],
				popupShow: false,
				pageSelect: [],
			}
		},
		components: {
			PageTree: PageTree
		},
		mounted: function () {
			this.spaceUuid = this.$route.query.space || '';
			this.getSpaceInfo();
			this.doGetPageList(null);
		},
		methods: {
			filterPageNode(value, data) {
				if (!value) return true;
				return data.name.indexOf(value) !== -1;
			},
			pageSelectChange(value) {
				console.log("页面修改：" + value);
				this.popupShow = false;
				this.$router.replace({path: '/page/share/mobile/view', query: {pageId: value, space: this.spaceUuid}});
			},
			popupShowChange(value) {
				this.popupShow = value;
				console.log(this.pageSelect)
			},
			doGetPageList() {
				pageApi.openPageList({space: this.spaceUuid}).then(json => {
					this.wikiPageList = json.data || [];
					this.nowPageId = '';
				});
			},
			getSpaceInfo() {
				pageApi.openSpaceInfo({space: this.spaceUuid}).then(json => {
					this.nowSpaceShow = json.data;
				});
			},
		}
	};
</script>

<style scoped>
	html,body,#app {margin: 0; padding: 0; height: 100%;}

	.share-mobile-layout{height: 100%;}
	.popup-module .header{width:100%;height:46px;}
	.popup-module .main{position:absolute;top:46px;bottom: 0;right:0;left:0;overflow:auto;}
	.popup-module .footer{width:100%;height:26px;position:fixed;bottom:0}

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
	/*评论*/
	.comment-box .head{
		float: left;background-color: #ccc;border-radius: 50%;margin-right: 10px;
		width: 45px; height: 45px; line-height: 45px;text-align: center;color: #fff;
	}
	.build-info{
		text-align: center; padding: 5px 0;color: #aaa;font-size: 12px; margin: 10px 0;
	}
	.build-info a{color: #4183c4;cursor: pointer;text-decoration:none;}
</style>

