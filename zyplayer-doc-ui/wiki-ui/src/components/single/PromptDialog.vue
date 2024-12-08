<template>
	<!--通用输入弹窗-->
	<a-modal :title="title" v-model:open="editDialogVisible" :destroyOnClose="true" width="420px" class="message-prompt-dialog">
		<a-form layout="vertical" :model="inputForm" :rules="inputFormRules" ref="inputFormRef" @submit.prevent>
			<a-form-item :label="label" name="data">
				<a-input ref="formDataInput" v-model:value="inputForm.data" @keyup.enter.native="editOk" :placeholder="placeholder"></a-input>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button @click="editCancel">{{cancelText}}</a-button>
			<a-button type="primary" @click="editOk">{{okText}}</a-button>
		</template>
	</a-modal>
</template>

<script setup>
import {Modal as AModal, Button as AButton, Form as AForm, FormItem as AFormItem, Input as AInput} from 'ant-design-vue';
import {onBeforeUnmount, ref, shallowRef, watch, onMounted, nextTick, useAttrs, defineProps, defineEmits, defineExpose} from 'vue';

const props = defineProps({
	cancelText: String,
	okText: String,
	title: String,
	label: String,
	placeholder: String,
	value: String,
	validator: Function,
	ok: Function,
	close: Function,
	rules: {type: Object, default: {}}
});
onMounted(() => {
	setTimeout(() => {
		formDataInput.value.focus();
		inputForm.value.data = props.value || '';
	}, 100);
});
let editDialogVisible = ref(true);
watch(editDialogVisible, () => {
	if (!editDialogVisible.value) {
		setTimeout(() => {
			props.close && props.close();
		}, 300);
	}
});
let inputForm = ref({data: ''});
let inputFormRef = ref();
let formDataInput = ref();
let inputFormRules = ref({
	data: [{
		trigger: 'blur',
		validator: (rule, value) => {
			if (props.validator) {
				let res = props.validator(value);
				if (res !== true) {
					return Promise.reject(res);
				}
			}
			return Promise.resolve();
		}
	}],
});
const editCancel = () => {
	editDialogVisible.value = false;
}
const editOk = () => {
	inputFormRef.value.validate().then(() => {
		if (props.ok) {
			props.ok(inputForm.value.data);
		}
		editDialogVisible.value = false;
	}).catch(() => {
	});
}
</script>

<style lang="scss">
.message-prompt-dialog {
}
</style>
