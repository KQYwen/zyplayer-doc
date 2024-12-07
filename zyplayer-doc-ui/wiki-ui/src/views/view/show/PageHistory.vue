<template>
	<div class="action-tab-box">
		<div v-if="props.pageHistoryList.length <= 0" class="action-box-empty">
			暂无修改历史记录
		</div>
		<el-timeline v-else>
			<el-timeline-item v-for="history in props.pageHistoryList">
				<el-tag :type="props.pageHistoryChoice.id === history.id ? history.loading === 3 ? 'danger' : 'success' : 'info'"
						class="history-item" @click="historyClick(history)">
					<div>{{ history.createUserName }}</div>
					<div>{{ history.createTime }}</div>
				</el-tag>
				<el-icon class="history-loading-status" v-show="history.loading===1">
					<el-icon-loading/>
				</el-icon>
				<el-icon class="history-loading-status" v-show="history.loading===2">
					<el-icon-circle-check/>
				</el-icon>
				<el-icon class="history-loading-status" v-show="history.loading===3">
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

import pageApi from '@/assets/api/page'
import {mavonEditor} from "mavon-editor";
import {ref, defineProps, defineEmits} from 'vue';
import {useStorePageData} from "@/store/pageData";
let storePage = useStorePageData();

let props= defineProps({
	pageHistoryList:Array,
	pageHistoryChoice:Object,
	pageHistoryDetail:String,
})
let emit = defineEmits(['historyClickHandle','previewPageImage','createNavigationHeading'])

const historyClick = (history) => {
	if (props.pageHistoryChoice.id === history.id && !!props.pageHistoryDetail.value) {
		return;
	}
	// 缓存一下，但如果历史页面多了而且很大就占内存，也可以每次去拉取，先这样吧
	if (history.content) {
		history.loading = 2;
		emit('historyClickHandle',history)
		setTimeout(() => {
			emit('previewPageImage',history)
			emit('createNavigationHeading',history)
		}, 500)
	} else {
		history.loading = 1
		pageApi.pageHistoryDetail({id: history.id}).then((json) => {
			history.loading = 2;
			history.content = json.data || '--';
			if (storePage.pageInfo.editorType === 2) {
				history.content = mavonEditor.getMarkdownIt().render(history.content);
			}
			emit('historyClickHandle',history)
			setTimeout(() => {
				emit('previewPageImage',history)
				emit('createNavigationHeading',history)
			}, 500);
		}).catch(() => {
			history.loading = 3;
		});
	}
}
</script>

<style lang="scss">
</style>
