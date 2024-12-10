<template>
	<div class="action-tab-box">
		<div v-if="history.length <= 0" class="action-box-empty">
			暂无修改历史记录
		</div>
		<el-timeline v-else>
			<el-timeline-item v-for="history in history">
				<el-tag :type="pageHistoryChoice.id === history.id ? (history.loading === 3 ? 'danger' : 'success') : 'info'"
						class="history-item" @click="historyClick(history)">
					<div>{{ history.createUserName }}</div>
					<div>{{ history.createTime }}</div>
				</el-tag>
				<el-icon class="history-loading-status" v-if="history.loading===1">
					<el-icon-loading/>
				</el-icon>
				<el-icon class="history-loading-status" v-else-if="history.loading===2">
					<el-icon-circle-check/>
				</el-icon>
				<el-icon class="history-loading-status" v-else-if="history.loading===3">
					<el-icon-circle-close/>
				</el-icon>
			</el-timeline-item>
		</el-timeline>
	</div>
</template>

<script setup>
import {
	CircleCheck as ElIconCircleCheck,
	CircleClose as ElIconCircleClose,
	Loading as ElIconLoading,
} from '@element-plus/icons-vue'

import pageApi from '@/assets/api/page';
import {mavonEditor} from "mavon-editor";
import {ref, defineProps, defineEmits} from 'vue';
import {useStorePageData} from "@/store/pageData";
let storePage = useStorePageData();

let props = defineProps({
	history: Array,
});
let emit = defineEmits(['choice']);

let pageHistoryChoice = ref({});
const historyClick = (history) => {
	if (pageHistoryChoice.value.id === history.id && !!pageHistoryChoice.value.content) {
		return;
	}
	pageHistoryChoice.value.loading = 0;
	pageHistoryChoice.value = history;
	// 缓存一下，但如果历史页面多了而且很大就占内存，也可以每次去拉取，先这样吧
	if (history.content) {
		history.loading = 2;
		emit('choice', history);
	} else {
		history.loading = 1;
		pageApi.pageHistoryDetail({id: history.id}).then((json) => {
			history.loading = 2;
			history.content = json.data || '--';
			if (storePage.pageInfo.editorType === 2) {
				history.content = mavonEditor.getMarkdownIt().render(history.content);
			}
			emit('choice', history);
		}).catch(() => {
			history.loading = 3;
		});
	}
}
</script>

<style lang="scss" scoped>
.action-tab-box {
	height: calc(100vh - 120px);
	overflow: auto;
	padding: 20px 10px;

	.action-box-empty {
		text-align: center;
		padding-top: 30px;
		color: #888;
		font-size: 14px;
	}

	.history-item {
		height: 55px;
		line-height: 25px;
		cursor: pointer;
		vertical-align: middle;
	}

	.history-loading-status {
		margin-left: 5px;
		color: #67c23a;
	}

	.history-loading-status.el-icon-circle-close {
		color: #f56c6c;
	}
}
</style>
