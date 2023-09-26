<template>
	<div class="share-mobile-layout">
		<van-popup v-model:show="popupShow" closeable position="left" class="popup-module" :style="{ height: '100%', width: '80%' }">
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

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from '../../assets/api/page'
import PageTree from '../shareLayout/PageTree.vue'
import 'vant/es/icon/style/index';
import 'vant/es/popup/style/index';
import 'vant/es/cell/style/index';
import 'vant/es/nav-bar/style/index';
import 'vant/es/image-preview/style/index';
import 'vant/es/collapse-item/style/index';
import 'vant/es/dialog/style/index';
import 'vant/es/checkbox/style/index';

let defaultProps = ref({children: 'children', label: 'name',});
// 空间搜索相关
let spaceUuid = ref('');
let nowPageId = ref('');
let nowSpaceShow = ref({});
// 页面展示相关
let wikiPageList = ref([]);
let popupShow = ref(false);
let pageSelect = ref([]);

let route = useRoute();
let router = useRouter();
onMounted(() => {
	spaceUuid.value = route.query.space || ''
	getSpaceInfo()
	doGetPageList(null)
});
const filterPageNode = (value, data) => {
	if (!value) return true
	return data.name.indexOf(value) !== -1
}
const pageSelectChange = (value) => {
	// console.log('页面修改：' + value)
	popupShow.value = false
	router.replace({
		path: '/page/share/mobile/view',
		query: {pageId: value, space: spaceUuid.value}
	})
}
const popupShowChange = (value) => {
	popupShow.value = value
	// console.log(pageSelect.value)
}
const doGetPageList = () => {
	pageApi.openPageList({space: spaceUuid.value}).then((json) => {
		wikiPageList.value = json.data || []
		nowPageId.value = ''
	})
}
const getSpaceInfo = () => {
	pageApi.openSpaceInfo({space: spaceUuid.value}).then((json) => {
		nowSpaceShow.value = json.data
	})
}
</script>

<style scoped>
html,
body,
#app {
	margin: 0;
	padding: 0;
	height: 100%;
}

.share-mobile-layout {
	height: 100%;
}

.popup-module .header {
	width: 100%;
	height: 46px;
}

.popup-module .main {
	position: absolute;
	top: 46px;
	bottom: 0;
	right: 0;
	left: 0;
	overflow: auto;
}

.popup-module .footer {
	width: 100%;
	height: 26px;
	position: fixed;
	bottom: 0;
}

pre {
	margin: 0;
	white-space: pre-wrap;
	font-size: 14px;
	font-family: auto;
}

.el-menu {
	box-sizing: border-box;
	border-right: 0;
	margin-right: 3px;
}

.el-header {
	background-color: #409eff;
	color: #333;
	line-height: 40px;
	text-align: right;
	height: 40px !important;
}

.doc-body-box {
	overflow-x: hidden;
	overflow-y: auto;
	width: 100%;
	padding: 10px;
	border-left: 1px solid #f1f1f1;
	box-sizing: border-box;
}

.el-tree {
	margin-right: 3px;
}

.logo {
	border-bottom: 1px solid #f1f1f1;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	padding: 5px 10px;
	width: 260px;
	height: 40px;
	line-height: 40px;
	font-size: 25px;
	color: #666;
	text-align: center;
}

.icon-collapse {
	float: left;
	font-size: 25px;
	color: #aaa;
	cursor: pointer;
	position: fixed;
}

.icon-collapse:hover {
	color: #ccc;
}

.comment-box .head {
	float: left;
	background-color: #ccc;
	border-radius: 50%;
	margin-right: 10px;
	width: 45px;
	height: 45px;
	line-height: 45px;
	text-align: center;
	color: #fff;
}

.build-info {
	text-align: center;
	padding: 5px 0;
	color: #aaa;
	font-size: 12px;
	margin: 10px 0;
}

.build-info a {
	color: #4183c4;
	cursor: pointer;
	text-decoration: none;
}
</style>
