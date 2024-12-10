<template>
	<div class="files-box">
		<div class="upload-file-box">
			<el-upload class="upload-page-file" :action="uploadFileUrl" ref="uploadFileRef"
			           :with-credentials="true" :before-upload="beforeUploadFile"
			           :on-success="uploadFileSuccess" :on-error="uploadFileError"
			           name="files" show-file-list multiple :data="uploadFormData" :limit="999"
			           style="display: inline;vertical-align: sub;">
				<a-button :icon="h(UploadOutlined)" type="primary" style="width: 260px;">上传附件</a-button>
			</el-upload>
		</div>
		<div v-if="storePage.fileList.length <= 0" class="action-box-empty">
			<el-empty description="暂无附件" />
		</div>
		<div v-else class="files-list">
			<template v-for="file in storePage.fileList">
				<a-card class="box-card files-card" :body-style="{ padding: '10px' }">
					<div class="head">{{file.createUserName}}</div>
					<div class="files-user-name">
						<span>{{file.createUserName}}</span>
						<el-tooltip :content="file.createTime" placement="top-start" :show-after="1000">
							<span class="time">{{ file.createTime }}</span>
						</el-tooltip>
						<el-icon v-if="storePage.pageAuth.canDeleteFile === 1" @click="deleteFile(file)" class="icon-delete"><ElIconDelete /></el-icon>
					</div>
					<div class="files-name">
						<a target="_blank" :href="file.fileUrl">{{file.fileName || '--'}}</a>
					</div>
					<div class="files-size">
						<IconParkMemoryOne/><span class="value">{{computeFileSize(file.fileSize)}}</span>
						<IconParkDownload style="margin-left: 15px;"/> <span class="value">{{file.downloadNum || 0}}</span>
					</div>
				</a-card>
			</template>
		</div>
	</div>
</template>

<script setup>
import {Delete as ElIconDelete,} from '@element-plus/icons-vue'
import {UploadOutlined} from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, h, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { message, Modal } from 'ant-design-vue';
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";
import unitUtil from "@/assets/lib/UnitUtil";
import {
	MemoryOne as IconParkMemoryOne,
	Download as IconParkDownload,
} from '@icon-park/vue-next'

let storePage = useStorePageData();

let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
const deleteFile = (file) => {
	Modal.confirm({
		maskClosable: true,
		title: '提示',
		content: '确定要删除此文件吗？',
		okText: '删除',
		cancelText: '确定',
		onOk: () => {
			pageApi.deletePageFile({id: file.id}).then(() => {
				storePage.fileList = storePage.fileList.filter(item => item !== file);
			});
		}
	});
}
let uploadFileRef = ref();
let uploadFormData = computed(() => {
	return {pageId: storePage.pageInfo.id};
});
const beforeUploadFile = (file) => {
}
const uploadFileError = (err) => {
	message.error('上传失败，' + err);
}
let uploadOverStatus = ['success', 'fail'];
const uploadFileSuccess = (response, file, fileList) => {
	if (response.errCode !== 200) {
		message('上传失败：' + (response.errMsg || '未知错误'));
	} else {
		storePage.fileList.push(response.data);
	}
	// 是否全部上传完成
	if (fileList.every(file => uploadOverStatus.includes(file.status))) {
		message.success('上传成功！');
		uploadFileRef.value.clearFiles();
	}
}
const computeFileSize = (fileSize) => {
	return unitUtil.computeFileSize(fileSize);
}
</script>

<style lang="scss">
.files-box {
	.upload-page-file {
		.el-upload-list {
			display: none;
		}
	}
}
</style>

<style scoped lang="scss">
.files-box {
	padding: 50px 8px 8px;
	height: calc(100vh - 130px);
	overflow: auto;
	box-sizing: border-box;
	position: relative;

	.upload-file-box {
		position: absolute;
		top: 0;
		left: 0;
		text-align: center;
		background: #fff;
		width: 100%;
		z-index: 1;
		padding-bottom: 5px;
	}

	ul {
		padding-left: 0;
	}

	.files-card {
		margin-bottom: 10px;
	}

	.files-user-name {
		margin-bottom: 6px;
		font-size: 13px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		position: relative;
	}

	.files-card .files-user-name .icon-delete {
		color: #888;
		font-size: 13px;
		cursor: pointer;
		float: right;
		display: none;
	}

	.files-card .files-user-name .icon-delete {
		color: #888;
		font-size: 13px;
		cursor: pointer;
		display: none;
		position: absolute;
		right: 0;
		top: 2px;
		background: #fff;
	}

	.files-card:hover .files-user-name .icon-delete {
		display: inline-block;
	}

	.head {
		float: left;
		background-color: #ccc;
		border-radius: 50%;
		margin-right: 10px;
		width: 45px;
		height: 45px;
		line-height: 45px;
		text-align: center;
		color: #fff;
	}

	.files-user-name .time {
		color: #888;
		margin-left: 8px;
	}

	.files-name {
		padding: 0;
		color: #666;
		margin: 0;
		line-height: 20px;
		font-size: 14px;
	}

	.files-size {
		margin-left: 50px;
		margin-top: 5px;
		font-size: 14px;

		.value {
			margin-left: 4px;
			vertical-align: middle;
		}
	}
}
</style>
