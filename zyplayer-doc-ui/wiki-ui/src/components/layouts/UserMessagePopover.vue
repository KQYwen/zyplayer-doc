<template>
	<span class="user-message-popover">
		<el-tooltip content="文档通知">
			<el-badge :value="notReadMessageNum" :max="99" :hidden="notReadMessageNum <= 0">
				<el-button ref="remindButtonRef" class="hover-button" text><IconParkRemind size="18"/></el-button>
			</el-badge>
		</el-tooltip>
		<el-popover v-model:visible="userMessagePopVisible" placement="bottom" :width="700" trigger="click"
		            popper-class="header-user-remind" ref="popoverRef"
		            :virtual-ref="remindButtonRef" virtual-triggering>
			<div class="header">
				<span class="title">文档通知</span>
				<el-link v-if="notReadMessageNum > 0" :icon="ElIconCheck" type="primary" @click="readAllUserMessage">本页标记已读</el-link>
			</div>
			<div class="header-user-message">
				<el-table :data="userMessageList" stripe max-height="400" style="width: 100%; margin-bottom: 5px">
					<el-table-column label="操作时间" prop="creationTime" width="150px"></el-table-column>
					<el-table-column label="内容" prop="msgContent" show-overflow-tooltip></el-table-column>
					<el-table-column width="60px">
						<template v-slot="scope">
							<el-badge :is-dot="scope.row.msgStatus === 0" style="line-height: 10px; padding-right: 5px">
								<el-link type="primary" @click="showUserMessage(scope.row)">查看</el-link>
							</el-badge>
						</template>
					</el-table-column>
				</el-table>
				<div class="page-info-box">
					<el-pagination background
							:current-page="userMsgParam.pageNum"
							:page-size="userMsgParam.pageSize"
							:total="userMsgTotalCount"
							layout="prev, pager, next, total"
							@current-change="handleCurrentChange">
					</el-pagination>
				</div>
			</div>
		</el-popover>
	</span>
</template>

<script setup>
import {
	Document as ElIconDocument,
	Fold as ElIconFold,
	Expand as ElIconExpand,
	Bell as ElIconBell,
	Setting as ElIconSetting,
	Plus as ElIconPlus,
	Check as ElIconCheck,
} from '@element-plus/icons-vue'
import {
	Remind as IconParkRemind,
} from '@icon-park/vue-next'
import {onBeforeUnmount, toRefs, ref, reactive, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from "@/assets/api/page";
import userApi from "@/assets/api/user";
import {useStoreDisplay} from '@/store/wikiDisplay.js'

let route = useRoute();
let router = useRouter();
const storeDisplay = useStoreDisplay();

let remindButtonRef = ref();
let userMessageList = ref([]);
let notReadMessageNum = ref(0);
let userMessagePopVisible = ref(false);
let userMsgTotalCount = ref(0);
let userMsgParam = ref({sysType: 2, pageNum: 1, pageSize: 20});
let messageInterval;
onMounted(() => {
	loadUserMessageList();
	messageInterval = setInterval(() => {
		loadUserMessageList();
	}, 10 * 1000);
});
onBeforeUnmount(() => {
	if (messageInterval) {
		clearInterval(messageInterval);
	}
});
const loadUserMessageList = () => {
	userApi.getUserMessageList(userMsgParam.value).then((res) => {
		userMessageList.value = res.data || [];
		userMsgTotalCount.value = res.total || 0;
		notReadMessageNum.value = userMessageList.value.filter((item) => item.msgStatus === 0).length;
	});
}
const showUserMessage = (row) => {
	if (row.msgStatus === 0) {
		userApi.readUserMessage({ids: row.id}).then(() => {
			loadUserMessageList()
		})
	}
	if (row.msgType >= 2 && row.msgType <= 14) {
		router.push({path: '/page/show', query: {pageId: row.dataId}})
		userMessagePopVisible.value = false
	}
}
const readAllUserMessage = () => {
	let msgIds = []
	userMessageList.value.filter((item) => item.msgStatus === 0).forEach((item) => {
		msgIds.push(item.id)
	})
	if (msgIds.length <= 0) return
	userApi.readUserMessage({ids: msgIds.join(',')}).then(() => {
		ElMessage.success('标记成功')
		loadUserMessageList()
	})
}
const handleCurrentChange = (val) => {
	userMsgParam.value.pageNum = val
	loadUserMessageList()
}
</script>

<style lang="scss">
.user-message-popover {
  margin-left: 12px;
}

.header-user-remind {
  .header {
	margin-bottom: 10px;
	display: flex;
	align-items: center;
	justify-content: space-between;

	.title {
	  font-size: 14px;
	  font-weight: bold;
	}
  }

  .header-user-message {
	.el-table {
	  .cell {
		padding: 0 6px;
	  }

	  /**覆盖箭头颜色*/
	  .el-popper__arrow::before {
		border: 1px solid var(--el-text-color-primary);
		background: var(--el-text-color-primary);
	  }
	}

	.page-info-box {
	  .el-pagination {
		justify-content: end;
	  }
	}
  }
}
</style>
