<template>
	<div class="setting-more-action-box">
		<a-card title="空间管理" style="margin-top: 20px;">
			<a-form :label-col="{span: 4}" :wrapper-col="{span: 20}">
				<a-form-item label="删除空间">
					<div style="width: 100%;">
						<a-button @click="deleteSpaceInfo" type="primary" danger :icon="h(DeleteOutlined)">删除空间</a-button>
						<el-alert title="删除该空间及其所有页面，请谨慎操作" show-icon type="warning" :closable="false" style="margin-top: 10px;line-height: normal;"/>
					</div>
				</a-form-item>
			</a-form>
		</a-card>
		<a-card title="更多信息" class="create-info-card">
			<a-row class="create-info-line">
				<a-col :span="12">
					<div>创建人：{{storeSpace.spaceInfo.createUserName}}</div>
				</a-col>
				<a-col :span="12">
					<div>创建时间：{{storeSpace.spaceInfo.createTime}}</div>
				</a-col>
			</a-row>
			<a-row v-if="storeSpace.spaceInfo.modified" class="create-info-line">
				<a-col :span="12">
					<div>修改人：{{storeSpace.spaceInfo.modifyUser}}</div>
				</a-col>
				<a-col :span="12">
					<div>修改时间：{{storeSpace.spaceInfo.modified}}</div>
				</a-col>
			</a-row>
		</a-card>
	</div>
</template>

<script setup>
import {
	SyncOutlined, DeleteOutlined, CopyOutlined, DownOutlined,
	DownloadOutlined, UploadOutlined, ExclamationCircleOutlined
} from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, h, watch, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { message } from 'ant-design-vue';
import { Modal } from 'ant-design-vue';
import {useStoreDisplay} from '@/store/wikiDisplay.js'
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";
import {useStoreSpaceData} from "@/store/spaceData";
import {useStoreUserData} from "@/store/userData";

let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();
let storeSpace = useStoreSpaceData();
let storeUser = useStoreUserData();

let route = useRoute();
let router = useRouter();
onMounted(() => {
});
const deleteSpaceInfo = () => {
	Modal.confirm({
		maskClosable: true,
		title: '提示',
		content: '确定要删除此空间及下面的所有文档吗？',
		okText: '确定',
		cancelText: '取消',
		onOk: () => {
			setTimeout(() => {
				Modal.confirm({
					maskClosable: true,
					title: '二次确认',
					content: '这是一个有风险的操作，需要您再次确认，确定要删除此空间及下面的所有文档吗？',
					okText: '确定删除',
					cancelText: '我再想想',
					okType: 'danger',
					onOk: () => {
						let param = {id: storeSpace.spaceInfo.id, delFlag: 1};
						pageApi.updateSpaceAuth(param).then(() => {
							message.success('删除成功');
							router.push({path: '/view'});
							storeSpace.eventSpaceDelete = !storeSpace.eventSpaceDelete;
						});
					}
				});
			}, 500);
		}
	});
}
</script>

<style lang="scss" scoped>
.setting-more-action-box {
	width: 80%;
	min-width: 500px;
	max-width: 800px;
	margin-bottom: 40px;

	.create-info-card {
		margin-top: 20px;

		.create-info-line {
			margin-bottom: 20px;

			&:last-child {
				margin-bottom: 0;
			}
		}
	}
}
</style>
