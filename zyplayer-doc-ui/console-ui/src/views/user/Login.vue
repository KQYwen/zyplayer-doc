<template>
	<div :class="'login-background linear-gradient-'+bgImgRandom">
		<div class="login-box">
			<el-form :model="loginParam" :rules="loginRules" ref="loginParam" label-position="left" label-width="0px" class="demo-ruleForm login-container">
				<h3 class="title">文档管理-系统登录</h3>
				<el-form-item prop="username">
					<el-input type="text" v-model="loginParam.username" auto-complete="off" placeholder="账号" @keyup.enter.native="loginSubmit"></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" v-model="loginParam.password" auto-complete="off" placeholder="密码" @keyup.enter.native="loginSubmit"></el-input>
				</el-form-item>
				<el-form-item style="width:100%;">
					<el-button type="primary" style="width:100%;" @click.native.prevent="loginSubmit" :loading="loginLoading">登录</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div class="power-by">Powered By <a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a></div>
	</div>
</template>

<script>
	import consoleApi from '../../common/api/console'
	export default {
		data() {
			return {
				loginLoading: false,
				redirect: '',
				loginParam: {
					username: '',
					password: ''
				},
				loginRules: {
					username: [
						{required: true, message: '请输入账号', trigger: 'blur'},
					],
					password: [
						{required: true, message: '请输入密码', trigger: 'blur'},
					]
				},
				bgImgRandom: Math.ceil(Math.random() * 5),
			};
		},
		mounted: function () {
			this.redirect = this.$route.query.redirect;
		},
		methods: {
			loginSubmit() {
				this.$refs.loginParam.validate((valid) => {
					if (!valid) return;
					this.loginLoading = true;
					consoleApi.userLogin(this.loginParam).then(() => {
						this.$emit('loginSuccess');
						this.loginLoading = false;
						if (!!this.redirect) {
							location.href = decodeURIComponent(this.redirect);
						} else {
							this.$router.back();
						}
					}).catch(e => {
						console.log("登录失败", e);
						this.loginLoading = false;
					});
				});
			}
		}
	}

</script>
<style>
	.login-container {
		-webkit-border-radius: 5px;
		border-radius: 5px;
		-moz-border-radius: 5px;
		background-clip: padding-box;
		margin: 0 auto;
		width: 350px;
		padding: 35px 35px 15px 35px;
		background: #fff;
		border: 1px solid #eaeaea;
		box-shadow: 0 0 25px #cac6c6;
	}
	.title {margin: 0px auto 40px auto;text-align: center;color: #505458;}
	.remember {margin: 0px 0px 35px 0px;}

	.login-background{height: 100%;}
	.linear-gradient-0{background: linear-gradient(to top, #a8edea 0%, #fed6e3 100%);}
	.linear-gradient-1{background: linear-gradient(to top, #9795f0 0%, #fbc8d4 100%);}
	.linear-gradient-2{background: linear-gradient(to top, #fad0c4 0%, #ffd1ff 100%);}
	.linear-gradient-3{background: linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%);}
	.linear-gradient-4{background: linear-gradient(120deg, #a6c0fe 0%, #f68084 100%);}
	.linear-gradient-5{background: linear-gradient(120deg, #e0c3fc 0%, #8ec5fc 100%);}

	.login-box{padding-top: 50px;}
	.power-by{position: absolute; bottom: 0; text-align: center; color: #888; padding: 10px 0;width: 100%;}
	.power-by a{color: #888;padding: 10px 0; width: 100%;}
</style>

