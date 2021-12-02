<template>
	<div style="height: 100%;">
		<el-container>
			<el-aside width="300px" style="background-color: #fafafa;" :style="{ width: rightAsideWidth + 'px' }" v-show="leftCollapse">
				<div class="logo">{{nowSpaceShow.name}}</div>
				<div style="padding: 10px;box-sizing: border-box;background: #fafafa;">
					<el-input v-model="searchKeywords" @keyup.enter.native="searchByKeywords" placeholder="搜索文档" style="margin: 10px 0;">
						<el-button slot="append" icon="el-icon-search" v-on:click="searchByKeywords"></el-button>
					</el-input>
					<el-tree :props="defaultProps" :data="wikiPageList" @node-click="handleNodeClick"
							 ref="wikiPageTree" :filter-node-method="filterPageNode" highlight-current
							 :expand-on-click-node="false" :default-expanded-keys="wikiPageExpandedKeys"
							 node-key="id"
							 style="background-color: #fafafa;padding-bottom: 30px;">
					</el-tree>
					<!--请手下留情，别删我(╥╯^╰╥)，给我一个露脸的机会，我长的不碍眼的-->
					<div class="build-info">本文档使用<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>构建</div>
				</div>
			</el-aside>
			<RightResize v-model="rightAsideWidth" v-show="leftCollapse"></RightResize>
			<el-container>
				<el-main class="doc-body-box">
					<router-view></router-view>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script>
	import pageApi from '../../common/api/page'
	import RightResize from './RightResize.vue'

	export default {
		data() {
			return {
				leftCollapse: true,
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
				rightAsideWidth: 300,
			}
		},
		components: {RightResize},
		mounted: function () {
			this.spaceUuid = this.$route.query.space || '';
			this.getSpaceInfo();
			this.doGetPageList(null);
		},
		methods: {
			filterPageNode(value, data) {
				if (!value || !data.name) return true;
				// issues:I2CG72 忽略大小写
				let name = data.name.toLowerCase();
				return name.indexOf(value.toLowerCase()) !== -1;
			},
			handleNodeClick(data) {
				if (this.nowPageId == data.id) {
					return;
				}
				console.log("点击节点：", data);
				this.nowPageId = data.id;
				this.$router.push({path: '/page/share/view', query: {pageId: data.id, space: this.spaceUuid}});
			},
			searchByKeywords() {
				this.$refs.wikiPageTree.filter(this.searchKeywords);
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
		position: fixed;bottom: 0;left: 0;background: #fafafa;width: 240px;text-align: center;
		padding: 5px 0;color: #aaa;font-size: 12px;
	}
	.build-info a{color: #4183c4;cursor: pointer;text-decoration:none;}
</style>

