<template>
	<div class="create-space-vue">
		<!--新建空间弹窗-->
		<el-dialog title="创建空间" v-model="newSpaceDialogVisible" width="600px" :close-on-click-modal="false">
			<el-form label-width="100px" :model="newSpaceForm" :rules="newSpaceFormRules" ref="newSpaceFormRef">
				<el-form-item label="空间名：" prop="name">
					<el-input v-model="newSpaceForm.name"></el-input>
				</el-form-item>
				<el-form-item label="空间描述：" prop="spaceExplain">
					<el-input v-model="newSpaceForm.spaceExplain"></el-input>
				</el-form-item>
				<el-form-item label="空间开放：">
					<el-switch v-model="newSpaceForm.openDoc" inactive-text="需要登录" :inactive-value="0" active-text="开放访问" :active-value="1"></el-switch>
				</el-form-item>
				<el-form-item label="空间类型：">
					<el-select v-model="newSpaceForm.type" filterable placeholder="选择类型" style="width: 100%">
						<el-option :key="1" label="公共空间" :value="1">
							<span style="float: left">公共空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px">属于公共，登录用户可访问、编辑</span>
						</el-option>
						<el-option :key="2" label="个人空间" :value="2">
							<span style="float: left">个人空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px">属于个人，所有登录用户可访问</span>
						</el-option>
						<el-option :key="3" label="隐私空间" :value="3">
							<span style="float: left">隐私空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px">属于个人，仅创建者可访问</span>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-if="newSpaceForm.id > 0" @click="onNewSpaceSubmit">保存修改</el-button>
					<el-button type="primary" v-else @click="onNewSpaceSubmit">立即创建</el-button>
					<el-button @click="onNewSpaceCancel">取消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from '../../assets/api/page'

let editSpaceId = ref('');
let newSpaceFormRules = ref({
	name: [
		{required: true, message: '请输入空间名', trigger: 'blur'},
		{
			min: 2,
			max: 25,
			message: '长度在 2 到 25 个字符',
			trigger: 'blur',
		},
	],
});
let newSpaceForm = ref({
	id: '',
	name: '',
	spaceExplain: '',
	treeLazyLoad: 0,
	openDoc: 0,
	uuid: '',
	type: 1,
})
let newSpaceDialogVisible = ref(false);
let manageSpaceDialogVisible = ref(false);
let emit = defineEmits(['success']);
const show = (spaceId) => {
	newSpaceForm.value = {
		id: '',
		name: '',
		spaceExplain: '',
		treeLazyLoad: 0,
		openDoc: 0,
		uuid: '',
		type: 1,
	}
	editSpaceId.value = spaceId || ''
	if (!!editSpaceId.value) {
		pageApi.spaceList({id: editSpaceId.value}).then((json) => {
			let spaceList = json.data || []
			if (spaceList.length > 0) {
				newSpaceForm.value = spaceList[0]
			}
		})
	}
	newSpaceDialogVisible.value = true
}
let newSpaceFormRef = ref();
const onNewSpaceSubmit = () => {
	newSpaceFormRef.value.validate((valid) => {
		if (valid) {
			let param = {
				id: newSpaceForm.value.id,
				name: newSpaceForm.value.name,
				type: newSpaceForm.value.type,
				openDoc: newSpaceForm.value.openDoc,
				spaceExplain: newSpaceForm.value.spaceExplain,
				treeLazyLoad: newSpaceForm.value.treeLazyLoad,
			}
			pageApi.updateSpace(param).then((json) => {
				ElMessage.success('创建成功')
				newSpaceDialogVisible.value = false
				emit('success', json.data.id)
			})
		}
	})
}
const onNewSpaceCancel = () => {
	newSpaceDialogVisible.value = false
}
defineExpose({show});
</script>

<style>
.create-space-vue .empty-news {
	text-align: center;
	padding: 100px;
}

.create-space-vue .text-link {
	color: #444;
}

.create-space-vue .line-box {
	color: #666;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
}

.create-space-vue .line-title {
	font-size: 14px;
}

.create-space-vue .page-preview-box {
}

.create-space-vue .page-preview-title {
	font-size: 18px;
	margin: 10px 0 5px 0;
	color: #3a8ee6;
	cursor: pointer;
}

.create-space-vue .page-preview-content {
	font-size: 16px;
	margin-bottom: 5px;
}

.create-space-vue .zan-img {
	vertical-align: middle;
	margin-top: -3px;
}

.create-space-vue .view-img {
	font-size: 16px;
	color: #666;
}

.create-space-vue .page-info-box {
	text-align: right;
	margin: 20px 0 50px 0;
}
</style>
