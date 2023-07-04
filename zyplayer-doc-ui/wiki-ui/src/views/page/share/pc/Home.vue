<template>
	<div style="padding: 10px" class="page-share-home-vue">
		<div style="max-width: 800px; margin: 0 auto">
			<div v-if="spacePageNews.length <= 0" class="empty-news">暂无数据</div>
			<div v-else class="line-box" v-for="item in spacePageNews">
				<div class="line-title">
					<span class="text-link">{{ item.createUserName }}</span> 发布于
					<span class="text-link">{{ item.spaceName }}</span>
				</div>
				<div class="page-preview-box">
					<div class="page-preview-title" @click="showPageDetail(item)" v-html="item.pageTitle"></div>
					<div class="page-preview-content" v-html="item.previewContent"></div>
					<div>
						<span><img src="../../../../assets/img/zan.png" class="zan-img"/>{{ item.zanNum }}　</span>
						<span><el-icon class="view-img"><el-icon-view/></el-icon> {{ item.viewNum }}　</span>
						<span>{{ item.updateTime || item.createTime }}</span>
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
					:total="totalCount">
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import {View as ElIconView} from '@element-plus/icons-vue'
import pageApi from '../../../../assets/api/page'

let totalCount = ref(0);
let searchParam = ref({spaceId: '', newsType: 1, pageNum: 1, pageSize: 20,});
let spacePageNews = ref([]);
onBeforeRouteUpdate((to) => {
	initQueryParam(to);
});
let route = useRoute();
let router = useRouter();
onMounted(() => {
	initQueryParam(route);
});
const getSpacePageNews = () => {
	pageApi.openPageNews(searchParam.value).then((json) => {
		spacePageNews.value = json.data || []
		totalCount.value = json.total
	})
}
const handleSizeChange = (val) => {
	searchParam.value.pageSize = val
	getSpacePageNews()
}
const showPageDetail = (row) => {
	let nowClickPath = {space: row.space, pageId: row.pageId}
	router.push({path: '/page/share/view', query: nowClickPath})
}
const handleCurrentChange = (val) => {
	searchParam.value.pageNum = val
	getSpacePageNews()
}
const initQueryParam = (to) => {
	searchParam.value = {
		space: to.query.space,
		newsType: 1,
		pageNum: 1,
		pageSize: 20,
	}
	if (!!searchParam.value.space) {
		getSpacePageNews()
	}
}
</script>

<style>
.page-share-home-vue .empty-news {
	text-align: center;
	padding: 100px;
}

.page-share-home-vue .text-link {
	color: #444;
}

.page-share-home-vue .line-box {
	color: #666;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
}

.page-share-home-vue .line-title {
	font-size: 14px;
}

.page-share-home-vue .page-preview-box {
}

.page-share-home-vue .page-preview-title {
	cursor: pointer;
	font-size: 20px;
	margin: 10px 0 5px 0;
	color: #3a8ee6;
}

.page-share-home-vue .page-preview-content {
	font-size: 16px;
	margin-bottom: 5px;
}

.page-share-home-vue .zan-img {
	vertical-align: middle;
	margin-top: -3px;
}

.page-share-home-vue .view-img {
	font-size: 16px;
	color: #666;
}

.page-share-home-vue .page-info-box {
	text-align: right;
	margin: 20px 0 50px 0;
}
</style>
