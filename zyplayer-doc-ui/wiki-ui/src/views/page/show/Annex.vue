<template>
	<div class="wiki-file">
		<el-upload v-if="storePage.pageAuth.canUploadFile === 1"
				   :on-success="uploadFileSuccess"
				   :on-error="uploadFileError"
				   :before-upload="beforeUpload"
				   :action="uploadFileUrl"
				   :data="uploadFormData"
				   :with-credentials="true" class="action-btn upload-page-file" name="files"
				   show-file-list multiple :limit="999">
			<el-button type="primary" :underline="false" :icon="ElIconUpload" style="margin: 10px;width: 100%"> 上传附件</el-button>
		</el-upload>
			<el-table v-show="storePage.fileList.length > 0" :data="storePage.fileList" border
					  style="width: 100%; margin-bottom: 5px">
				<el-table-column label="文件名" show-overflow-tooltip>
					<template v-slot="scope">
						<el-link target="_blank" :href="scope.row.fileUrl" type="primary">{{scope.row.fileName }}
						</el-link>
					</template>
				</el-table-column>
				<el-table-column prop="createUserName" label="创建人" width="110px"
								 show-overflow-tooltip></el-table-column>
				<el-table-column label="文件大小" width="120px">
					<template v-slot="scope">{{computeFileSize(scope.row.fileSize) }}</template>
				</el-table-column>
				<el-table-column prop="createTime" label="创建时间" width="160px"></el-table-column>
				<el-table-column prop="downloadNum" label="下载次数" width="90px">
					<template v-slot="scope">{{scope.row.downloadNum || 0}}</template>
				</el-table-column>
				<el-table-column label="操作" width="90px" v-if="storePage.pageAuth.canDeleteFile == 1">
					<template v-slot="scope">
						<el-button @click="deletePageFile(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
</template>

<script setup>
	import {Upload as ElIconUpload} from '@element-plus/icons-vue'
	import {useStorePageData} from "@/store/pageData";

	let storePage = useStorePageData();
	import {ref} from 'vue';
	import {ElMessageBox, ElMessage} from 'element-plus';
	import pageApi from "@/assets/api/page";
	import unitUtil from "@/assets/lib/UnitUtil";

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
	const deletePageFile = (row) => {
		ElMessageBox.confirm('确定要删除此文件吗？', '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning',
		}).then(() => {
			let param = {id: row.id};
			pageApi.deletePageFile(param).then(() => {
				storePage.fileList = storePage.fileList.filter(item => item.id !== row.id);
			});
		})
	}
	const computeFileSize = (fileSize) => {
		return unitUtil.computeFileSize(fileSize)
	}
</script>
