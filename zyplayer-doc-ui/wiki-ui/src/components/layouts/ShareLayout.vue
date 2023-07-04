<template>
	<div style="height: 100%">
		<el-container>
			<el-aside width="300px" style="background-color: #fafafa" :style="{ width: rightAsideWidth + 'px' }" v-show="leftCollapse">
				<div class="logo">{{ nowSpaceShow.name }}</div>
				<div style="padding: 10px; box-sizing: border-box; background: #fafafa">
					<el-input v-model="searchKeywords" @keyup.enter="searchByKeywords" placeholder="搜索文档" style="margin: 10px 0">
						<template v-slot:append>
							<el-button :icon="ElIconSearch" @click="searchByKeywords"></el-button>
						</template>
					</el-input>
					<div class="wiki-page-tree-box">
						<el-tree
								ref="wikiPageTreeRef"
								:current-node-key="nowPageId"
								:data="wikiPageList"
								:default-expanded-keys="wikiPageExpandedKeys"
								:expand-on-click-node="true"
								:filter-node-method="filterPageNode"
								:props="defaultProps"
								draggable
								highlight-current
								node-key="id"
								style="background-color: #fafafa"
								@node-click="handleNodeClick">
							<template v-slot="{ node, data }">
								<div class="page-tree-node">
									<el-tooltip :content="node.label" placement="top-start" :show-after="1000">
										<span class="label">
											<el-icon><el-icon-document/></el-icon>
											<span class="text">{{ node.label }}</span>
										</span>
									</el-tooltip>
								</div>
							</template>
						</el-tree>
					</div>
					<!--请保留申明-->
					<div class="build-info">本文档使用<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>构建</div>
				</div>
			</el-aside>
			<RightResize v-model:value="rightAsideWidth" v-show="leftCollapse"></RightResize>
			<el-container>
				<el-main class="doc-body-box">
					<router-view></router-view>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import {
	Document as ElIconDocument,
	Search as ElIconSearch,
} from '@element-plus/icons-vue'
import pageApi from '../../assets/api/page'
import RightResize from './RightResize.vue'

let leftCollapse = ref(true);
let defaultProps = ref({children: 'children', label: 'name',});
// 空间搜索相关
let spaceUuid = ref('');
let nowPageId = ref('');
let nowSpaceShow = ref({});
// 搜索的输入内容
let searchKeywords = ref('');
// 页面展示相关
let wikiPageList = ref([]);
let wikiPageExpandedKeys = ref([]);
let rightAsideWidth = ref(300);

let route = useRoute();
let router = useRouter();
onMounted(() => {
	spaceUuid.value = route.query.space || ''
	getSpaceInfo()
	doGetPageList(null)
});
const filterPageNode = (value, data) => {
	if (!value || !data.name) return true
	// issues:I2CG72 忽略大小写
	let name = data.name.toLowerCase()
	return name.indexOf(value.toLowerCase()) !== -1
}
const handleNodeClick = (data) => {
	if (nowPageId.value == data.id) {
		return
	}
	// console.log('点击节点：', data)
	nowPageId.value = data.id
	router.push({
		path: '/page/share/view',
		query: {pageId: data.id, space: spaceUuid.value}
	})
}
let wikiPageTreeRef = ref();
const searchByKeywords = () => {
	wikiPageTreeRef.value.filter(searchKeywords.value)
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
	position: fixed;
	bottom: 0;
	left: 0;
	background: #fafafa;
	width: 240px;
	text-align: center;
	padding: 5px 0;
	color: #aaa;
	font-size: 12px;
}

.build-info a {
	color: #4183c4;
	cursor: pointer;
	text-decoration: none;
}
</style>
