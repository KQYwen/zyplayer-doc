<template>
	<div class="space-page-recycle-box">
		<a-flex align="center" justify="space-between" style="margin-bottom: 10px;">
			<a-space>
				<a-button @click="restorePageSelected" :disabled="selectedRowKeys.length <= 0" :icon="h(UndoOutlined)">恢复</a-button>
				<a-button @click="deletePageSelected" :disabled="selectedRowKeys.length <= 0" :icon="h(DeleteOutlined)">彻底删除</a-button>
			</a-space>
			<a-button @click="loadPageList" type="primary" :icon="h(SearchOutlined)">查询</a-button>
		</a-flex>
		<a-table :dataSource="recyclePageList" :columns="listColumns" :loading="recycleListLoading"
		         :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: selectionChange }" rowKey="id"
		         :pagination="paginationConfig"  @change="recyclePageSortChange"
		         size="middle" :scroll="{ y: 'calc(100vh - 220px)' }">
			<template #bodyCell="{ column, text, record }">
				<template v-if="column.dataIndex === 'actions'">
					<a-space>
						<a-button @click="restorePage(record)" :icon="h(UndoOutlined)">恢复</a-button>
						<a-button @click="deletePage(record)" :icon="h(DeleteOutlined)">彻底删除</a-button>
					</a-space>
				</template>
			</template>
		</a-table>
	</div>
</template>

<script setup>
import { UndoOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons-vue';
import pageApi from "@/assets/api/page";
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, watch, h, defineEmits, computed} from 'vue';
import {onBeforeRouteUpdate, onBeforeRouteLeave, useRouter, useRoute} from "vue-router";
import { message } from 'ant-design-vue';
import { Modal } from 'ant-design-vue';
import { useStoreDisplay } from '@/store/wikiDisplay.js'
import { useStorePageData } from '@/store/pageData.js'

let route = useRoute();
let router = useRouter();
let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();

onMounted(() => {
	storeDisplay.currentPage = 'recycle';
	storeDisplay.headerShow = true;
	loadPageList();
});
onBeforeUnmount(() => {
	storeDisplay.currentPage = '';
});
let recycleListParam = ref({
	pageNum: 1,
});
let selectedRowKeys = ref([]);
const selectionChange = (rowKeys, selectedRows) => {
	selectedRowKeys.value = rowKeys;
}
let recycleListTotalCount = ref(0);
let recycleListLoading = ref(false);
let recyclePageList = ref([]);
const loadPageList = () => {
	recycleListLoading.value = true;
	let param = {spaceId: route.params.spaceId, ...recycleListParam.value};
	pageApi.recyclePageListList(param).then(json => {
		recyclePageList.value = json.data || [];
		if (recycleListParam.value.pageNum === 1) {
			recycleListTotalCount.value = json.total || 0;
		}
		recycleListLoading.value = false;
	}).catch(() => {
		recycleListLoading.value = false;
	});
}
watch(() => recycleListParam.value.pageNum, loadPageList);
const restorePageSelected = () => {
	restorePageConfirm(selectedRowKeys.value.join(','));
}
const deletePageSelected = () => {
	deletePageConfirm(selectedRowKeys.value.join(','), route.params.spaceId);
}
const restorePage = (row) => {
	restorePageConfirm(row.id);
}
const deletePage = (row) => {
	deletePageConfirm(row.id, row.spaceId);
}
const restorePageConfirm = (ids) => {
	Modal.confirm({
		maskClosable: true,
		title: '提示',
		content: '确定要恢复此页面吗？如果该页面的父页面也被删除，则需父页面恢复后才可看见此页面',
		okText: '确认恢复',
		cancelText: '取消',
		onOk: () => {
			pageApi.pageRestore({pageIds: ids}).then(json => {
				loadPageList();
				message.success('已恢复');
				storePage.eventPageUpdate = !storePage.eventPageUpdate;
			});
		}
	});
}
const deletePageConfirm = (ids, spaceId) => {
	Modal.confirm({
		maskClosable: true,
		title: '提示',
		content: '确定要彻底删除此页面吗?',
		okText: '彻底删除',
		cancelText: '取消',
		onOk: () => {
			pageApi.recycleDeletePage({pageIds: ids, spaceId: spaceId}).then(() => {
				loadPageList();
				message.success('已删除');
			});
		}
	});
}
let paginationConfig = ref({
	total: 0,
	current: 1,
	pageSize: 20,
	showSizeChanger: true,
	pageSizeOptions: ['20', '50', '100'],
	showTotal: total => `共${total}条`
});
const listColumns = computed(() => ([
	{title: '名字', dataIndex: 'name'},
	{title: '删除时间', dataIndex: 'updateTime', width: 180},
	{title: '操作人', dataIndex: 'updateUserName', width: 180},
	{title: '', dataIndex: 'actions', width: 280}
]));
const recyclePageSortChange = (pagination, filters, sorter) => {
	recycleListParam.value.pageNum = pagination.current;
	recycleListParam.value.pageSize = pagination.pageSize;
	paginationConfig.value.current = pagination.current;
	paginationConfig.value.pageSize = pagination.pageSize;
	loadPageList();
}
</script>

<style scoped lang="scss">
.space-page-recycle-box {
	padding: 10px 20px 0;
}
</style>

