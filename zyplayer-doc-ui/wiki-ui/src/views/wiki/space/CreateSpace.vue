<template>
	<!--新建空间弹窗-->
	<a-modal :title="newSpaceForm.id?'编辑空间':'新建空间'" v-model:open="newSpaceDialogVisible" width="600px" :maskClosable="false" class="create-space-vue">
		<a-form :model="newSpaceForm" :rules="newSpaceFormRules" ref="newSpaceFormRef" @submit.prevent :label-col="{span: 5}" :wrapper-col="{span: 19}">
			<a-form-item label="空间名" name="name">
				<a-input v-model:value="newSpaceForm.name" placeholder="请输入空间名" :maxlength="25"></a-input>
			</a-form-item>
			<a-form-item label="空间描述" name="spaceExplain">
				<a-textarea v-model:value="newSpaceForm.spaceExplain" placeholder="请输入空间简介" :maxlength="250" :autoSize="{ minRows: 6}" type="textarea" resize="none"/>
			</a-form-item>
			<a-form-item label="空间类型">
				<a-radio-group v-model:value="newSpaceForm.type">
					<a-radio :value="2">仅空间成员可见</a-radio>
					<a-radio :value="1">所有登录用户可见</a-radio>
				</a-radio-group>
			</a-form-item>
			<a-form-item label="空间唯一编码" name="uuid">
				<a-input v-model:value="newSpaceForm.uuid" placeholder="请输入空间唯一编码" :disabled="!!newSpaceForm.id" :maxlength="30"/>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button @click="onNewSpaceCancel">取消</a-button>
			<a-button type="primary" v-if="newSpaceForm.id" @click="onNewSpaceSubmit()" :loading="newSpaceSubmitLoading">保存修改</a-button>
			<a-button type="primary" v-else @click="onNewSpaceSubmit()" :loading="newSpaceSubmitLoading">立即创建</a-button>
		</template>
	</a-modal>
</template>

<script setup>
import pageApi from '@/assets/api/page'
import { message } from 'ant-design-vue';
import {toRefs, ref, defineExpose, reactive, onMounted, watch, defineEmits, computed} from 'vue';
import { useStoreSpaceData } from '@/store/spaceData.js'

let storeSpaceData = useStoreSpaceData();
let newSpaceDialogVisible = ref(false);
let newSpaceForm = ref({id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1});
let newSpaceFormRules = computed(() => ({
	name: [
		{required: true, message: '请输入空间名', trigger: 'blur'},
		{max: 25, message: '最多25个字符', trigger: 'blur'},
	],
	uuid: [{
		required: false,
		trigger: 'blur',
		validator: (rule, value, callback) => {
			if (!value || /^[a-zA-Z0-9-_]+$/.test(value)) {
				callback();
			}
			callback(new Error('空间编码只能由大小写字母、数字、中横线和下划线组成'));
		}
	}],
}));
const show = (spaceId) => {
	newSpaceForm.value = {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 2};
	let editSpaceId = spaceId || '';
	if (!!editSpaceId) {
		pageApi.spaceDetail({id: editSpaceId}).then(json => {
			newSpaceForm.value = json.data || {};
			newSpaceForm.value.groupId = newSpaceForm.value.groupId || '';
		});
	}
	newSpaceDialogVisible.value = true;
}
defineExpose({show});
let newSpaceFormRef = ref();
let newSpaceSubmitLoading = ref(false);
const onNewSpaceSubmit = () => {
	newSpaceFormRef.value.validate().then(() => {
		let param = {
			id: newSpaceForm.value.id,
			name: newSpaceForm.value.name,
			type: newSpaceForm.value.type,
			uuid: newSpaceForm.value.uuid,
			openDoc: newSpaceForm.value.openDoc,
			groupId: newSpaceForm.value.groupId,
			spaceExplain: newSpaceForm.value.spaceExplain,
			treeLazyLoad: newSpaceForm.value.treeLazyLoad,
		};
		newSpaceSubmitLoading.value = true;
		pageApi.updateSpace(param).then(json => {
			message.success(newSpaceForm.value.id ? '修改成功' : '创建成功');
			newSpaceSubmitLoading.value = false;
			newSpaceDialogVisible.value = false;
			storeSpaceData.eventSpaceUpdate = !storeSpaceData.eventSpaceUpdate;
		}).catch(() => {
			newSpaceSubmitLoading.value = false;
		});
	});
}
const onNewSpaceCancel = () => {
	newSpaceDialogVisible.value = false;
}
</script>

<style lang="scss">
.create-space-vue {
	.warning-icon {
		height: 32px;
		line-height: 32px;
		margin-right: 5px;
		margin-top: 1px;
	}
}
</style>

