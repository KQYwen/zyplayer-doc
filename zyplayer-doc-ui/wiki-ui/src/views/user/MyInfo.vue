<template>
	<div class="my-info-vue">
		<div style="margin: 0 auto; max-width: 1000px">
			<el-card class="box-card">
				<template v-slot:header>
					<div class="clearfix">我的信息</div>
				</template>
				<el-form class="search-form-box" label-width="100px">
					<el-form-item label="账号：">{{ userInfo.userNo }}</el-form-item>
					<el-form-item label="用户名：">{{ userInfo.userName }}</el-form-item>
					<el-form-item label="手机号：">{{ userInfo.phone }}</el-form-item>
					<el-form-item label="邮箱：">{{ userInfo.email }}</el-form-item>
					<el-form-item label="状态：">{{userInfo.delFlag == 0 ? '正常' : '停用'}}</el-form-item>
					<el-form-item label="性别：">{{userInfo.sex == 0 ? '女' : '男'}}</el-form-item>
				</el-form>
			</el-card>
		</div>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import userApi from '../../assets/api/user'

let userInfo = ref({});
onMounted(() => {
	getUserInfo();
});
const getUserInfo = () => {
	userApi.getSelfUserInfo().then((json) => {
		userInfo.value = json.data
	})
}
</script>

<style>
.my-info-vue {
}

.my-info-vue .box-card {
	margin: 10px;
}
</style>
