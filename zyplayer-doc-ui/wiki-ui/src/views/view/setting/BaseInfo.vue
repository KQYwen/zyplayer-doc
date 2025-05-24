<template>
	<div class="setting-base-info-box">
		<a-card title="基本信息">
			<template #extra>
				<a-button @click="onSpaceUpdateSubmit" type="primary">保存设置</a-button>
			</template>
			<a-form :model="spaceForm" :rules="spaceFormRules" ref="spaceFormRef" @submit.prevent :label-col="{span: 4}" :wrapper-col="{span: 20}">
				<a-form-item label="空间名" name="name">
					<a-input v-model:value="spaceForm.name" placeholder="请输入空间名" :maxlength="25"></a-input>
				</a-form-item>
				<a-form-item label="空间说明" name="spaceExplain">
					<a-textarea v-model:value="spaceForm.spaceExplain" placeholder="请输入空间说明" :maxlength="250" :autoSize="{ minRows: 6}" type="textarea" resize="none"/>
				</a-form-item>
			</a-form>
		</a-card>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, watch, h, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { message } from 'ant-design-vue';
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";
import {useStoreSpaceData} from "@/store/spaceData";

let storePage = useStorePageData();
let storeSpace = useStoreSpaceData();
watch(() => storeSpace.spaceInfo, (newSpace, oldSpace) => {
	initSpaceForm();
});
onMounted(() => {
	initSpaceForm();
});
let spaceForm = ref({});
let spaceFormRules = ref({
	name: [{required: true, message: '请输入空间名', trigger: 'blur'}],
});
let spaceFormRef = ref();
const initSpaceForm = () => {
	spaceForm.value = {...storeSpace.spaceInfo};
}
const onSpaceUpdateSubmit = () => {
	spaceFormRef.value.validate().then(() => {
		let param = {
			id: spaceForm.value.id,
			name: spaceForm.value.name,
			spaceExplain: spaceForm.value.spaceExplain,
		};
		pageApi.updateSpace(param).then(json => {
			message.success('修改成功');
			storeSpace.spaceInfo.name = spaceForm.value.name;
			storeSpace.spaceInfo.spaceExplain = spaceForm.value.spaceExplain;
		});
	});
}
</script>

<style lang="scss">
.setting-base-info-box {
	width: 80%;
	min-width: 400px;
	max-width: 700px;
	margin-bottom: 40px;
}
</style>
