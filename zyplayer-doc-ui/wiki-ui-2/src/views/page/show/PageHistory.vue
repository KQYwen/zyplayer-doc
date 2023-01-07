<template>
	<div class="action-tab-box">
		<div v-if="pageHistoryList.length <= 0" class="action-box-empty">
			暂无修改历史记录
		</div>
		<el-timeline v-else>
			<el-timeline-item v-for="history in pageHistoryList">
				<el-tag :type="pageHistoryChoice.id === history.id ? history.loading === 3 ? 'danger' : 'success' : 'info'" class="history-item" @click="historyClick(history)">
					<div>{{ history.createUserName }}</div>
					<div>{{ history.createTime }}</div>
				</el-tag>
				<el-icon class="history-loading-status" v-show="history.loading===1"><el-icon-loading/></el-icon>
				<el-icon class="history-loading-status" v-show="history.loading===2"><el-icon-circle-check/></el-icon>
				<el-icon class="history-loading-status" v-show="history.loading===3"><el-icon-circle-close/></el-icon>
			</el-timeline-item>
		</el-timeline>
	</div>
</template>

<script setup>
import {
	Delete as ElIconDelete,
	Loading as ElIconLoading,
} from '@element-plus/icons-vue'
import {toRefs, ref, reactive, onMounted, watch, defineProps, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import { ElMessageBox, ElMessage, ElNotification } from 'element-plus';
import pageApi from '@/assets/api/page'
import {useStorePageData} from "@/store/pageData";
import {useStoreUserData} from "@/store/userData";

let route = useRoute();
let router = useRouter();
let storePage = useStorePageData();
let storeUser = useStoreUserData();

let pageHistoryDetail = ref('');
let pageHistoryChoice = ref({});
let pageHistoryList = ref([]);
let pageHistoryPageNum = ref(1);

watch(() => storePage.pageInfo, (newVal) => {
})
onMounted(() => {
});
</script>

<style lang="scss">
</style>
