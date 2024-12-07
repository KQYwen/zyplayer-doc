<template>
	<div style="margin-top: 40px; font-size: 14px">
		<span style="vertical-align: top" class="is-link">
		  <span v-show="storePage.pageInfo.selfZan == 0" @click="zanPage(1)"><img src="../../../assets/img/zan.png" style="vertical-align: middle"/> 赞</span>
		  <span v-show="storePage.pageInfo.selfZan == 1" @click="zanPage(0)"><img src="../../../assets/img/zan.png" style="vertical-align: middle; transform: rotateX(180deg)"/> 踩</span>
		</span>
		<span style="margin-left: 10px; vertical-align: top">
		  <span v-if="storePage.pageInfo.selfZan == 0 && storePage.pageInfo.zanNum <= 0">成为第一个赞同者</span>
		  <span v-else-if="storePage.pageInfo.selfZan == 0 && storePage.pageInfo.zanNum > 0">
			<span class="is-link" @click="showZanPageUser">{{ storePage.pageInfo.zanNum }}人</span>赞了它
		  </span>
		  <span v-else-if="storePage.pageInfo.selfZan == 1 && storePage.pageInfo.zanNum <= 1">我赞了它</span>
		  <span v-else-if="storePage.pageInfo.selfZan == 1 && storePage.pageInfo.zanNum > 1">
			<span class="is-link" @click="showZanPageUser">我和{{ storePage.pageInfo.zanNum - 1 }}个其他人</span>赞了它
		  </span>
		</span>
		<span style="margin-left: 10px">
			<el-icon style="font-size: 16px; color: #666;vertical-align: middle;"><el-icon-view/></el-icon> {{ storePage.pageInfo.viewNum }}次阅读
		</span>
	</div>
	<el-dialog title="赞了它的人" v-model="zanUserDialogVisible" width="600px">
		<el-table :data="zanUserList" border :show-header="false" style="width: 100%; margin-bottom: 5px">
			<el-table-column prop="createUserName" label="用户"></el-table-column>
			<el-table-column prop="createTime" label="时间"></el-table-column>
		</el-table>
	</el-dialog>
</template>

<script setup>
import pageApi from '../../../assets/api/page'
import {ref} from 'vue';
import {useStorePageData} from "@/store/pageData";
import {
	View as ElIconView,
} from '@element-plus/icons-vue'
let zanUserList = ref([]);
let zanUserDialogVisible = ref(false);
let storePage = useStorePageData();

const zanPage = (yn) => {
	let param = {yn: yn, pageId: storePage.pageInfo.id}
	pageApi.updatePageZan(param).then(() => {
		storePage.pageInfo.selfZan = yn
		storePage.pageInfo.zanNum = storePage.pageInfo.zanNum + (yn == 1 ? 1 : -1)
	})
}
const showZanPageUser = () => {
	zanUserDialogVisible.value = true
	zanUserList.value = []
	let param = {pageId: storePage.pageInfo.id}
	pageApi.pageZanList(param).then((json) => {
		zanUserList.value = json.data
	})
}
</script>
