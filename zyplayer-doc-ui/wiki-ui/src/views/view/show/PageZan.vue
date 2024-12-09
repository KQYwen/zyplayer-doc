<template>
	<div class="zan-box">
		<div class="zan-btn-box">
			<VueStarPlus v-model="starActive" animate="animated tada">
				<template #icon>
					<a-button @click="zanPage()" :type="starActive?'primary':'default'" shape="circle" class="zan-btn">
						<IconParkThumbsUp :size="30"/>
					</a-button>
				</template>
			</VueStarPlus>
		</div>
		<template v-if="storePage.pageInfo.zanNum <= 0">
			<el-divider>为它点赞</el-divider>
		</template>
		<template v-else-if="storePage.pageInfo.zanNum > 0">
			<template v-if="storePage.pageInfo.selfZan === 1">
				<template v-if="storePage.pageInfo.zanNum === 1">
					<el-divider>我赞了它</el-divider>
				</template>
				<el-divider v-else>我和<a @click="showZanPageUser">{{storePage.pageInfo.zanNum - 1}} 人</a> 赞了它</el-divider>
			</template>
			<el-divider v-else><a @click="showZanPageUser">{{storePage.pageInfo.zanNum}} 人</a> 赞了它</el-divider>
		</template>
	</div>
	<el-dialog title="赞了它的人" v-model="zanUserDialogVisible" width="600px">
		<el-table :data="zanUserList" border :show-header="false" style="width: 100%; margin-bottom: 5px;">
			<el-table-column prop="createUserName" label="用户"></el-table-column>
			<el-table-column prop="createTime" label="时间"></el-table-column>
		</el-table>
	</el-dialog>
</template>

<script setup>
import {ThumbsUp as IconParkThumbsUp} from '@icon-park/vue-next'
import pageApi from '../../../assets/api/page';
import {ref, watch, onMounted} from 'vue';
import {useStorePageData} from "@/store/pageData";
import VueStarPlus from 'vue-star-plus'
import 'vue-star-plus/style.css'

let storePage = useStorePageData();

let zanUserList = ref([]);
let zanUserDialogVisible = ref(false);
let starActive = ref(false);
watch(() => storePage.pageInfo, () => {
	starActive.value = (storePage.pageInfo.selfZan === 1);
});
onMounted(() => {
	starActive.value = (storePage.pageInfo.selfZan === 1);
});
const zanPage = () => {
	let yn = storePage.pageInfo.selfZan === 1 ? 0 : 1;
	let param = {yn: yn, pageId: storePage.pageInfo.id};
	pageApi.updatePageZan(param).then(() => {
		starActive.value = (yn === 1);
		storePage.pageInfo.selfZan = yn;
		storePage.pageInfo.zanNum = storePage.pageInfo.zanNum + (yn === 1 ? 1 : -1);
	});
}
const showZanPageUser = () => {
	zanUserDialogVisible.value = true;
	zanUserList.value = [];
	let param = {pageId: storePage.pageInfo.id};
	pageApi.pageZanList(param).then((json) => {
		zanUserList.value = json.data || [];
	});
}
</script>

<style lang="scss">
.zan-box {
	margin-top: 50px;
	text-align: center;

	.zan-btn-box {
		position: relative;

		.vue-star-plus {
			width: 100%;
			position: unset;

			.vue-star-plus__ground {
				margin: 0 auto;
			}
		}

		.zan-btn {
			height: 60px;
			width: 60px;
		}
	}

	.el-divider {
		width: 300px;
		margin: 20px auto;
	}
}
</style>
