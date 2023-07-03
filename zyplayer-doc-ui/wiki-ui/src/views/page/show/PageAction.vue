<template>
	<el-row class="page-action-box">
		<el-col :span="12">
			<span v-if="storePage.pageInfo.updateUserName">{{ storePage.pageInfo.updateUserName }}　于　{{storePage.pageInfo.updateTime}}　修改</span>
			<span v-else class="create-user-time">{{ storePage.pageInfo.createUserName }}　于　{{storePage.pageInfo.createTime}}　创建</span>
		</el-col>
		<el-col :span="12" style="text-align: right;">
			<el-link v-if="storePage.pageAuth.canEdit === 1" type="primary" :underline="false" :icon="ElIconEdit" @click="editWiki" style="margin-right: 15px;"> 编辑</el-link>
			<el-link type="primary" :icon="ElIconChatLineRound" :underline="false" @click="showCommentWiki" style="margin-right: 15px"> 评论</el-link>
			<el-upload v-if="storePage.pageAuth.canUploadFile === 1"
			           :on-success="uploadFileSuccess"
			           :on-error="uploadFileError"
			           :action="uploadFileUrl"
			           :data="uploadFormData"
			           :with-credentials="true" class="upload-page-file" name="files"
			           show-file-list multiple :limit="999"
			           style="display: inline; margin-right: 15px;vertical-align: middle;">
				<el-link type="primary" :underline="false" :icon="ElIconUpload"> 上传附件</el-link>
			</el-upload>
			<el-dropdown style="margin-right: 15px;vertical-align: middle;" trigger="click">
				<el-link type="primary" :underline="false">
					更多 <el-icon class="el-icon--right"><el-icon-arrow-down/></el-icon>
				</el-link>
				<template #dropdown>
					<el-dropdown-menu>
						<el-dropdown-item @click="showPageHistory" :icon="ElIconTime">查看历史版本</el-dropdown-item>
						<el-dropdown-item @click="editWikiAuth" v-if="storePage.pageAuth.canConfigAuth === 1" :icon="ElIconSCheck">权限设置</el-dropdown-item>
						<el-dropdown-item @click="showOpenPage" v-if="storePage.spaceInfo.openDoc === 1" :icon="ElIconShare">查看开放文档</el-dropdown-item>
						<el-dropdown-item @click="showMobileView" v-if="storePage.spaceInfo.openDoc === 1" :icon="ElIconMobilePhone">手机端查看</el-dropdown-item>
						<el-dropdown-item @click="exportWord" :icon="ElIconDownload">导出为Word文档</el-dropdown-item>
						<el-dropdown-item @click="deleteWikiPage" v-if="storePage.pageAuth.canDelete === 1" :icon="ElIconDelete">删除</el-dropdown-item>
					</el-dropdown-menu>
				</template>
			</el-dropdown>
		</el-col>
	</el-row>
</template>

<script setup>
import {
	ArrowDown as ElIconArrowDown,
	View as ElIconView,
	Close as ElIconClose,
	Delete as ElIconDelete,
	Loading as ElIconLoading,
	CircleCheck as ElIconCircleCheck,
	CircleClose as ElIconCircleClose,
	ChatLineRound as ElIconChatLineRound,
	Upload as ElIconUpload,
	Edit as ElIconEdit,
	Timer as ElIconTime,
	Stamp as ElIconSCheck,
	Share as ElIconShare,
	Iphone as ElIconMobilePhone,
	Download as ElIconDownload,
} from '@element-plus/icons-vue'
import {toRefs, ref, reactive, onMounted, watch, defineProps, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import { ElMessageBox, ElMessage, ElNotification } from 'element-plus';
import pageApi from '@/assets/api/page'
import {useStorePageData} from "@/store/pageData";

let storePage = useStorePageData();

const editWiki = () => {
	// 锁定页面并进入编辑页面
	let param = {pageId: storePage.pageInfo.id};
	pageApi.pageLock(param).then(() => {
		router.push({path: '/page/edit', query: {pageId: storePage.pageInfo.id}});
	});
}
const showCommentWiki = () => {
	storePage.commentShow = true;
	storePage.commentActiveTab = 'comment';
	// computeNavigationWidth();
}
const editWikiAuth = () => {
	// pageAuthNewUser.value = [];
	// pageAuthUserList.value = [];
	// let param = {pageId: wikiPage.value.id};
	// pageApi.getPageUserAuthList(param).then((json) => {
	// 	pageAuthUserList.value = json.data || [];
	// 	pageAuthDialogVisible.value = true;
	// });
}
const showPageHistory = () => {
	// actionTabVisible.value = true;
	// actionTabActiveName.value = 'history';
	// clearHistory();
	// computeNavigationWidth();
}
const showOpenPage = () => {
	// if (props.spaceInfo.openDoc !== 1) {
	// 	ElMessage.warning('该空间未开放，无法查看开放文档地址')
	// } else {
	// 	let routeUrl = router.resolve({
	// 		path: '/page/share/view',
	// 		query: {pageId: wikiPage.value.id, space: props.spaceInfo.uuid}
	// 	})
	// 	window.open(routeUrl.href, '_blank')
	// }
}
const deleteWikiPage = () => {
	// ElMessageBox.confirm('确定要删除此页面及其所有子页面吗？', '提示', {
	// 	confirmButtonText: '确定',
	// 	cancelButtonText: '取消',
	// 	type: 'warning',
	// }).then(() => {
	// 	let param = {pageId: wikiPage.value.id};
	// 	pageApi.pageDelete(param).then(() => {
	// 		// 重新加载左侧列表，跳转到展示页面
	// 		emit('loadPageList');
	// 		router.push({
	// 			path: '/home',
	// 			query: {spaceId: wikiPage.value.spaceId}
	// 		});
	// 	});
	// }).catch(() => {});
}
let downloadFormRef = ref();
const exportWord = () => {
	// downloadFormParam.value.param = {
	// 	pageId: wikiPage.value.id,
	// }
	// setTimeout(() => downloadFormRef.value.submit(), 0)
}
let qrCodeDivRef = ref();
const showMobileView = () => {
	// if (props.spaceInfo.openDoc !== 1) {
	// 	ElMessage.warning('该空间未开放，无法查看开放文档地址')
	// } else {
	// 	let routeUrl = router.resolve({
	// 		path: '/page/share/mobile/view',
	// 		query: {pageId: wikiPage.value.id, space: props.spaceInfo.uuid}
	// 	})
	// 	mobileScanDialogVisible.value = true
	// 	let hostPath = window.location.href.split('#')[0]
	// 	setTimeout(() => {
	// 		qrCodeUrl.value = hostPath + routeUrl.href
	// 		QRCode.toCanvas(qrCodeDivRef.value, qrCodeUrl.value, {
	// 				scale: 5, height: 250, wight: 250,
	// 			}, (error) => {
	// 				if (error) console.error(error)
	// 			}
	// 		)
	// 	}, 0)
	// }
}
// 上传相关
let uploadFormData = ref({pageId: 0});
let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
const uploadFileError = (err) => {
	ElMessage.error('上传失败，' + err);
}
const uploadFileSuccess = (response) => {
	// if (response.errCode === 200) {
	// 	pageFileList.value.push(response.data);
	// 	ElMessage.success('上传成功！');
	// } else {
	// 	ElMessage('上传失败：' + (response.errMsg || '未知错误'));
	// }
}
</script>

<style scoped lang="scss">
.page-action-box {

}
</style>
