<template>
	<div style="background: #f1f1f1; min-height: 100%" class="page-search-vue">
		<div style="max-width: 1200px;margin: 0 auto;background: #fff;padding: 20px;min-height: 100%;box-sizing: border-box;">
			<div style="margin-bottom: 20px">
				<el-row :gutter="20" style="max-width: 700px; margin: 0 auto">
					<el-col :span="20">
						<el-input v-model="searchParam.keywords" @keyup.enter="getSpacePageNews" placeholder="" style="width: 100%"></el-input>
					</el-col>
					<el-col :span="4">
						<el-button type="primary" @click="getSpacePageNews" :icon="ElIconSearch">搜索一下</el-button>
					</el-col>
				</el-row>
			</div>
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
                    <span><img src="../../assets/img/zan.png" class="zan-img"/>{{ item.zanNum }}　</span>
						<span><el-icon class="view-img"><el-icon-view/></el-icon>{{ item.viewNum }}　</span>
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
import {View as ElIconView, Search as ElIconSearch} from '@element-plus/icons-vue'
import pageApi from '../../assets/api/page'

let route = useRoute();
let router = useRouter();
onMounted(() => {
	initQueryParam(route);
});
let totalCount = ref(0);
let searchParam = ref({
	spaceId: '',
	keywords: '',
	newsType: 1,
	pageNum: 1,
	pageSize: 20,
});
let spacePageNews = ref([]);
const getSpacePageNews = () => {
	pageApi.pageSearchByEs(searchParam.value).then((json) => {
		spacePageNews.value = json.data || []
		totalCount.value = json.total
	})
}
const handleSizeChange = (val) => {
	searchParam.value.pageSize = val
	getSpacePageNews()
}
const showPageDetail = (row) => {
	window.open('#/page/show?pageId=' + row.pageId)
}
const handleCurrentChange = (val) => {
	searchParam.value.pageNum = val
	getSpacePageNews()
}
const initQueryParam = (to) => {
	searchParam.value = {
		keywords: to.query.keywords,
		spaceId: to.query.spaceId,
		newsType: 1,
		pageNum: 1,
		pageSize: 20,
	}
	getSpacePageNews()
}
</script>

<style>
.page-search-vue .empty-news {
	text-align: center;
	padding: 100px;
}

.page-search-vue .text-link {
	color: #444;
}

.page-search-vue .line-box {
	color: #666;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
}

.page-search-vue .line-title {
	font-size: 14px;
}

.page-search-vue .page-preview-box {
}

.page-search-vue .page-preview-title {
	font-size: 18px;
	margin: 10px 0 5px 0;
	color: #3a8ee6;
	cursor: pointer;
}

.page-search-vue .page-preview-content {
	font-size: 16px;
	margin-bottom: 5px;
}

.page-search-vue .zan-img {
	vertical-align: middle;
	margin-top: -3px;
}

.page-search-vue .view-img {
	font-size: 16px;
	color: #666;
}

.page-search-vue .page-info-box {
	text-align: right;
	margin: 20px 0 50px 0;
}
</style>
