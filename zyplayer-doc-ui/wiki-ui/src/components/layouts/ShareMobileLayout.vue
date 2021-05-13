<template>
	<div style="height: 100%;">
		<nut-icon type="top"></nut-icon>
		<nut-popup position="left" v-model="popupShow" :style="{ height: '100%', width: '80%' }">
			<nut-sidenavbar>
				<nut-sidenavbar>
					<nut-sidenavbaritem ikey="1" title="人脸识别"></nut-sidenavbaritem>
					<nut-sidenavbaritem ikey="2" title="云存自然语言处理"></nut-sidenavbaritem>
					<nut-subsidenavbar title="图像理解" ikey="3" :open="false">
						<nut-sidenavbaritem ikey="4" title="菜品识别"></nut-sidenavbaritem>
						<nut-sidenavbaritem ikey="5" title="拍照购"></nut-sidenavbaritem>
					</nut-subsidenavbar>
					<nut-sidenavbaritem ikey="313" title="人脸识别"></nut-sidenavbaritem>
					<nut-sidenavbaritem ikey="3232" title="云存自然语言处理"></nut-sidenavbaritem>
					<nut-subsidenavbar title="智能城市AI" ikey="6">
						<nut-sidenavbaritem ikey="7" title="企业风险预警模型"></nut-sidenavbaritem>
						<nut-sidenavbaritem ikey="8" title="水质量检测"></nut-sidenavbaritem>
						<nut-subsidenavbar title="人体识别" ikey="9">
							<nut-sidenavbaritem ikey="10" title="人体检测"></nut-sidenavbaritem>
							<nut-sidenavbaritem ikey="11" title="细粒度人像分割"></nut-sidenavbaritem>
						</nut-subsidenavbar>
					</nut-subsidenavbar>
					<nut-subsidenavbar title="自然语言处理" ikey="12">
						<nut-sidenavbaritem ikey="13" title="词法分析"></nut-sidenavbaritem>
						<nut-sidenavbaritem ikey="14" title="句法分析"></nut-sidenavbaritem>
					</nut-subsidenavbar>
				</nut-sidenavbar>
<!--				<nut-sidenavbaritem data-v-7bf5e136="" ikey="1" title="关于zyplayer-doc工具"></nut-sidenavbaritem>-->
<!--				<nut-subsidenavbar data-v-7bf5e136="" title="dsadadsadas" ikey="16">-->
<!--					<nut-sidenavbaritem data-v-7bf5e136="" ikey="17" title="dsadasd"></nut-sidenavbaritem>-->
<!--				</nut-subsidenavbar>-->
<!--				<nut-sidenavbaritem data-v-7bf5e136="" ikey="2" title="开发规划"></nut-sidenavbaritem>-->
<!--				<nut-sidenavbaritem data-v-7bf5e136="" ikey="3" title="升级日志"></nut-sidenavbaritem>-->
<!--				<nut-sidenavbaritem data-v-7bf5e136="" ikey="4" title="贡献人员列表"></nut-sidenavbaritem>-->
<!--				<nut-subsidenavbar data-v-7bf5e136="" title="xx" ikey="8">-->
<!--					<nut-sidenavbaritem data-v-7bf5e136="" ikey="9" title="xxxxx"></nut-sidenavbaritem>-->
<!--				</nut-subsidenavbar>-->
<!--				<nut-subsidenavbar data-v-7bf5e136="" title="zyplayer-doc-swagger" ikey="5">-->
<!--					<nut-sidenavbaritem data-v-7bf5e136="" ikey="6" title="如何使用"></nut-sidenavbaritem>-->
<!--					<nut-sidenavbaritem data-v-7bf5e136="" ikey="11" title="发发沙发沙发是否"></nut-sidenavbaritem>-->
<!--				</nut-subsidenavbar>-->
				<!--				<template v-for="page in wikiPageList">-->
<!--					<nut-sidenavbaritem1 :ikey="page.id" :title="page.name" v-if="!page.children"></nut-sidenavbaritem1>-->
<!--					<nut-subsidenavbar1 :title="page.name" :ikey="page.id" v-else>-->
<!--						<template v-for="page1 in page.children">-->
<!--							<nut-sidenavbaritem1 :ikey="page1.id" :title="page1.name" v-if="!page1.children"></nut-sidenavbaritem1>-->
<!--							<nut-subsidenavbar1 :title="page1.name" :ikey="page1.id" v-else>-->
<!--								<template v-for="page2 in page1.children">-->
<!--									<nut-sidenavbaritem1 :ikey="page2.id" :title="page2.name" v-if="!page2.children"></nut-sidenavbaritem1>-->
<!--								</template>-->
<!--							</nut-subsidenavbar1>-->
<!--						</template>-->
<!--					</nut-subsidenavbar1>-->
<!--				</template>-->
			</nut-sidenavbar>
		</nut-popup>
		<router-view @popupShow="popupShowChange"></router-view>
	</div>
</template>

<script>
	import Vue from 'vue';
	import pageApi from '../../common/api/page'
	import NutUI from '@nutui/nutui';
	import '@nutui/nutui/dist/nutui.css';
	NutUI.install(Vue);

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
			popupShowChange(value) {
				this.popupShow = value;
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

