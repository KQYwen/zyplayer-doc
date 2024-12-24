<template>
	<div class="login-page-view login-background" style="background-image: url('src/assets/img/login-bg.jpg')">
		<div class="login-warp-flex">
			<div class="login-content">
				<a-form :model="loginParam" :rules="loginRules" layout="vertical" ref="loginParamRef" label-position="left" label-width="0px" class="login-form">
					<h3 class="login-title">文档管理系统 - 账号登录</h3>
					<a-form-item name="username">
						<a-input type="text" v-model:value="loginParam.username" auto-complete="off" size="large"
						          placeholder="请输入账号" @keyup.enter.native="loginSubmit()"/>
					</a-form-item>
					<a-form-item name="password">
						<a-input type="password" v-model:value="loginParam.password" auto-complete="off" size="large"
						          placeholder="请输入密码" @keyup.enter.native="loginSubmit()"/>
					</a-form-item>
					<a-form-item style="width: 100%">
						<a-button type="primary" style="width: 100%" size="large" @click.native.prevent="loginSubmit()" :loading="loginLoading">登录</a-button>
					</a-form-item>
				</a-form>
			</div>
		</div>
		<LoginFooter/>
	</div>
</template>

<script setup>
import {onMounted, ref, computed, h} from 'vue';
import {useRoute, useRouter} from "vue-router";
import userApi from '@/assets/api/user.js'
import {useStoreUserData} from "@/store/userData";
import LoginFooter from './LoginFooter.vue'

let storeUser = useStoreUserData();
let route = useRoute();
let router = useRouter();
onMounted(() => {
	redirect.value = route.query.redirect;
});
let loginLoading = ref(false);
let redirect = ref('');
let loginParam = ref({
	username: '',
	password: '',
});
let loginRules = computed(() => ({
	username: [{required: true, message: '请输入账号或邮箱', trigger: 'blur'}],
	password: [{required: true, message: '请输入密码', trigger: 'blur'}],
}));
let loginParamRef = ref();
const loginSubmit = () => {
	loginParamRef.value.validate().then(() => {
		loginLoading.value = true;
		userApi.userLogin(loginParam.value).then(() => {
			// 跳转回之前的页面
			loginLoading.value = false;
			if (!!redirect.value) {
				location.href = decodeURIComponent(redirect.value);
			} else {
				// 没有目标页面跳至文档首页
				router.replace({path: '/'});
			}
		}).catch((e) => {
			console.log('登录失败', e);
			loginLoading.value = false;
		});
	});
}
</script>

<style lang="scss">
.login-page-view {
	.el-form-item {
		margin-bottom: 25px;
	}
}
</style>

<style lang="scss" scoped>
.login-page-view {
	height: 100%;
	width: 100%;

	.login-warp-flex {
		display: flex;
		justify-content: center;
		align-items: center;
		height: 80%;

		.login-content {
			width: 400px;
			max-width: 96%;
			padding: 35px;
			background: #ffffff99;
			backdrop-filter: blur(10px);
			border-radius: 10px;
			box-sizing: border-box;
			box-shadow: 0 1px 2px -2px rgba(0, 0, 0, 0.16), 0 3px 6px 0 rgba(0, 0, 0, 0.12), 0 5px 12px 4px rgba(0, 0, 0, 0.09);

			.login-form {
				.login-title {
					margin: 0 auto 40px auto;
					text-align: center;
					color: #505458;
				}
			}
		}
	}
}
</style>
