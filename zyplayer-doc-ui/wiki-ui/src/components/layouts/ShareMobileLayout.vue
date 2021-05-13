<template>
	<div style="height: 100%;">
		<van-popup v-model="popupShow" closeable position="left" :style="{ height: '100%', width: '80%' }">
			<van-nav-bar :title="nowSpaceShow.name"></van-nav-bar>
			<van-collapse v-model="pageSelect" @change="pageSelectChange">
				<template v-for="page in wikiPageList">
					<div v-if="!page.children" class="van-cell van-cell--clickable">{{page.name}}</div>
					<van-collapse-item :title="page.name" :name="page.id" v-else>
						<template v-for="page1 in page.children">
							<van-collapse v-model="pageSelect" @change="pageSelectChange">
								<div v-if="!page1.children" class="van-cell van-cell--clickable">{{page1.name}}</div>
								<van-collapse-item :title="page1.name" :name="page1.id" v-else>
									<template v-for="page2 in page1.children">
										<div v-if="!page2.children">{{page2.name}}</div>
									</template>
								</van-collapse-item>
							</van-collapse>
						</template>
					</van-collapse-item>
				</template>
			</van-collapse>
		</van-popup>
		<router-view @popupShow="popupShowChange"></router-view>
	</div>
</template>

<script>
	import Vue from 'vue';
	import pageApi from '../../common/api/page'
	import Vant from 'vant';
	import 'vant/lib/index.css';
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
				popupShow: true,
				pageSelect: [],
			}
		},
		components: {
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
			},
			popupShowChange(value) {
				this.popupShow = value;
				console.log(this.pageSelect)
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

