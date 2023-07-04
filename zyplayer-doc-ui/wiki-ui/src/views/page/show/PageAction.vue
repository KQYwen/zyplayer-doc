<template>
	<el-row class="page-action-box">
		<el-col :span="12" class="page-create-info">
			<span v-if="storePage.pageInfo.updateUserName">
				{{ storePage.pageInfo.updateUserName }}
				<span class="split">于</span>
				{{ storePage.pageInfo.updateTime }}
				<span class="split">修改</span>
			</span>
			<span v-else class="create-user-time">
				{{ storePage.pageInfo.createUserName }}
				<span class="split">于</span>
				{{ storePage.pageInfo.createTime }}
				<span class="split">创建</span>
			</span>
		</el-col>
		<el-col :span="12" class="page-action-list">
			<el-link v-if="storePage.pageAuth.canEdit === 1" @click="editWiki" type="primary" :underline="false" :icon="ElIconEdit" class="action-btn">编辑</el-link>
			<el-link type="primary" :icon="ElIconChatLineRound" :underline="false" @click="showCommentWiki" class="action-btn">评论</el-link>
			<el-upload v-if="storePage.pageAuth.canUploadFile === 1"
			           :on-success="uploadFileSuccess"
			           :on-error="uploadFileError"
			           :before-upload="beforeUpload"
			           :action="uploadFileUrl"
			           :data="uploadFormData"
			           :with-credentials="true" class="action-btn upload-page-file" name="files"
			           show-file-list multiple :limit="999">
				<el-link type="primary" :underline="false" :icon="ElIconUpload"> 上传附件</el-link>
			</el-upload>
			<el-dropdown trigger="click" class="action-btn more-dropdown">
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
		<MobileQrScanDialog v-model:visible="mobileScanDialogVisible"/>
		<PageAuthDialog v-model:visible="pageAuthDialogVisible"/>
		<form method="post" ref="downloadFormRef" :action="downloadFormParam.url" target="_blank">
			<input type="hidden" :name="key" :value="val" v-for="(val, key) in downloadFormParam.param"/>
		</form>
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
import PageAuthDialog from './PageAuthDialog.vue'
import MobileQrScanDialog from './MobileQrScanDialog.vue'
import {useStorePageData} from "@/store/pageData";

let storePage = useStorePageData();
let router = useRouter();
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
}
let pageAuthDialogVisible = ref(false);
const editWikiAuth = () => {
	pageAuthDialogVisible.value = true;
}
const showPageHistory = () => {
	storePage.commentShow = true;
	storePage.commentActiveTab = 'history';
}
const showOpenPage = () => {
	if (storePage.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址');
	} else {
		let routeUrl = router.resolve({
			path: '/page/share/view',
			query: {pageId: storePage.pageInfo.id, space: storePage.spaceInfo.uuid}
		});
		window.open(routeUrl.href, '_blank');
	}
}
const deleteWikiPage = () => {
	ElMessageBox.confirm('确定要删除此页面及其所有子页面吗？', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		let param = {pageId: storePage.pageInfo.id};
		pageApi.pageDelete(param).then(() => {
			// 重新加载左侧列表，跳转到展示页面
			// emit('loadPageList'); TODO
			router.push({path: '/home', query: {spaceId: storePage.pageInfo.spaceId}});
		});
	}).catch(() => {});
}
// 下载为Word
let downloadFormRef = ref();
let downloadFormParam = ref({url: 'zyplayer-doc-wiki/page/download', param: {}});
const exportWord = () => {
	downloadFormParam.value.param = {pageId: storePage.pageInfo.id};
	setTimeout(() => downloadFormRef.value.submit(), 0);
}
// 手机扫码
let mobileScanDialogVisible = ref(false);
const showMobileView = () => {
	if (storePage.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址');
	} else {
		mobileScanDialogVisible.value = true;
	}
}
// 上传相关
let uploadFormData = ref({pageId: 0});
let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
const beforeUpload = () => {
	uploadFormData.value.pageId = storePage.pageInfo.id;
}
const uploadFileError = (err) => {
	ElMessage.error('上传失败，' + err);
}
const uploadFileSuccess = (response) => {
	if (response.errCode === 200) {
		storePage.fileList.push(response.data);
		ElMessage.success('上传成功！');
	} else {
		ElMessage('上传失败：' + (response.errMsg || '未知错误'));
	}
}
</script>

<style scoped lang="scss">
.page-action-box {
  padding: 30px 0;

  .page-create-info {
	font-size: 14px;
	color: #888;

	.split {
	  padding: 0 4px;
	}
  }

  .page-action-list {
	text-align: right;
  }
}
</style>

<style lang="scss">
.page-action-box {
  .page-action-list {
	.el-icon {
	  margin-right: 4px;
	}

	.action-btn + .action-btn {
	  margin-left: 15px;
	}

	.upload-page-file {
	  display: inline;
	  vertical-align: middle;
	}

	.more-dropdown {
	  vertical-align: middle;
	}
  }
}
</style>
