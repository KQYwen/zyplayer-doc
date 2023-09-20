<template>
	<div style="padding: 10px" class="home-vue">
		<div style="max-width: 800px; margin: 0 auto">
			<el-tabs model-value="first">
				<el-tab-pane :label="newsTypesMap[searchParam.newsType]" name="first">
					<div v-if="spacePageNews.length <= 0" class="empty-news">
						暂无数据
					</div>
					<div v-else class="line-box" v-for="item in spacePageNews">
						<div class="line-title">
							<span class="text-link">{{ item.createUserName }}</span> 发布于
							<span class="text-link">{{ item.spaceName }}</span>
						</div>
						<div class="page-preview-box">
							<div class="page-preview-title" @click="showPageDetail(item)" v-html="item.pageTitle"></div>
							<div class="page-preview-content" v-html="item.previewContent"></div>
							<div>
								<span><img src="../../assets/img/zan.png" class="zan-img"/>{{ item.zanNum }}　</span>
								<span><el-icon class="view-img"><el-icon-view/></el-icon>{{ item.viewNum }}　</span>
								<span>{{ item.updateTime || item.createTime }}</span>
							</div>
						</div>
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import {View as ElIconView} from '@element-plus/icons-vue'
import pageApi from '../../assets/api/page'

let totalCount = ref(0);
let searchParam = ref({spaceId: '', newsType: 1, pageNum: 1, pageSize: 20,});
let spacePageNews = ref([]);
// 列表类型
let newsTypesArr = ref([
	{key: 1, val: '最近更新'},
	{key: 2, val: '最新创建'},
	{key: 3, val: '查看最多'},
	{key: 4, val: '点赞最多'},
	{key: 5, val: '查看+点赞最多'},
]);
let newsTypesMap = ref({});

onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
let route = useRoute();
let router = useRouter();
onMounted(() => {
	initQueryParam(route);
});
const getSpacePageNews = () => {
	pageApi.pageNews(searchParam.value).then((json) => {
		spacePageNews.value = json.data || []
	})
}
const handleSizeChange = (val) => {
	searchParam.value.pageSize = val
	getSpacePageNews()
}
const showPageDetail = (row) => {
	let nowClickPath = {pageId: row.pageId}
	router.push({path: '/page/show', query: nowClickPath})
}
const handleCurrentChange = (val) => {
	searchParam.value.pageNum = val
	getSpacePageNews()
}
const initQueryParam = (to) => {
	searchParam.value = {
		spaceId: to.query.spaceId,
		newsType: 1,
		pageNum: 1,
		pageSize: 20,
		dirId: to.query.dirId
	}
	if (!!searchParam.value.spaceId) {
		getSpacePageNews()
	}
	newsTypesMap.value = {}
	newsTypesArr.value.forEach(
		(item) => (newsTypesMap.value[item.key] = item.val)
	)
}
</script>

<style>
.home-vue .empty-news {
	text-align: center;
	padding: 100px;
}

.home-vue .text-link {
	color: #444;
}

.home-vue .line-box {
	color: #666;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
}

.home-vue .line-title {
	font-size: 14px;
}

.home-vue .page-preview-box {
}

.home-vue .page-preview-title {
	cursor: pointer;
	font-size: 20px;
	margin: 10px 0 5px 0;
	color: #3a8ee6;
}

.home-vue .page-preview-content {
	font-size: 16px;
	margin-bottom: 5px;
}

.home-vue .zan-img {
	vertical-align: middle;
	margin-top: -3px;
}

.home-vue .view-img {
	font-size: 16px;
	color: #666;
	vertical-align: middle;
}

.home-vue .page-info-box {
	text-align: right;
	margin: 20px 0 50px 0;
}
</style>
