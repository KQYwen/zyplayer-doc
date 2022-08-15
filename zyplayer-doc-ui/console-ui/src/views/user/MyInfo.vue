<template>
	<div class="my-info-vue">
		<div style="margin: 0 auto;max-width: 1000px;">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<el-row>
						<el-col :span="12" style="line-height: 40px;">我的信息</el-col>
						<el-col :span="12" style="text-align: right;">
							<el-button type="primary" @click="showUpdatePasswordDialog"><i class="el-icon-edit"></i> 修改密码</el-button>
						</el-col>
					</el-row>
				</div>
				<el-form class="search-form-box" label-width="100px">
					<el-form-item label="账号：">{{userInfo.userNo}}</el-form-item>
					<el-form-item label="用户名：">{{userInfo.userName}}</el-form-item>
					<el-form-item label="手机号：">{{userInfo.phone}}</el-form-item>
					<el-form-item label="邮箱：">{{userInfo.email}}</el-form-item>
					<el-form-item label="状态：">{{userInfo.delFlag==0?'正常':'停用'}}</el-form-item>
					<el-form-item label="性别：">{{userInfo.sex==0?'女':'男'}}</el-form-item>
				</el-form>
			</el-card>
		</div>
		<el-dialog title="修改密码" :visible.sync="updatePasswordDialogVisible" width="500px">
			<el-form label-width="120px" :model="updatePassword" status-icon :rules="updatePasswordRules" ref="passwordForm">
				<el-form-item label="当前密码" prop="currentPwd">
					<el-input type="password" v-model="updatePassword.currentPwd" placeholder="请输入当前密码"></el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="newPwd">
					<el-input type="password" v-model="updatePassword.newPwd" placeholder="请输入新密码"></el-input>
				</el-form-item>
				<el-form-item label="确认新密码" prop="repeatPwd">
					<el-input type="password" v-model="updatePassword.repeatPwd" placeholder="请再次输入新密码"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitUpdatePasswordForm">修改密码</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	import consoleApi from '../../common/api/console'
	export default {
		data() {
			return {
				userInfo: {},
				updatePasswordDialogVisible: false,
				updatePassword: {
					currentPwd: '',
					newPwd: '',
					repeatPwd: '',
				},
				updatePasswordRules: {
					currentPwd: [{validator: this.validateCurrentPwd, trigger: 'blur'}],
					newPwd: [{validator: this.validateNewPwd, trigger: 'blur'}],
					repeatPwd: [{validator: this.validateRepeatPwd, trigger: 'blur'}],
				},
			};
		},
		mounted: function () {
			this.getUserInfo();
		},
		methods: {
			getUserInfo() {
				consoleApi.getSelfUserInfo().then(json => {
					this.userInfo = json.data;
				});
			},
			showUpdatePasswordDialog() {
				this.updatePasswordDialogVisible = true;
			},
			submitUpdatePasswordForm() {
				this.$refs.passwordForm.validate((valid) => {
					if (!valid) {
						return false;
					}
					consoleApi.updateSelfPwd(this.updatePassword).then(json => {
						this.$message.success("修改成功！请重新登录");
						setTimeout(() => {
							consoleApi.userLogout().then(() => {
								location.reload();
							}).catch(e => {
								console.log("退出登录失败", e);
							});
						}, 500);
					});
				});
			},
			validateCurrentPwd(rule, value, callback) {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else {
					callback();
				}
			},
			validateNewPwd(rule, value, callback) {
				if (value === '') {
					callback(new Error('请输入新密码'));
				} else {
					if (this.updatePassword.newPwd !== '') {
						this.$refs.passwordForm.validateField('repeatPwd');
					}
					callback();
				}
			},
			validateRepeatPwd(rule, value, callback) {
				if (value === '') {
					callback(new Error('请再次输入新密码'));
				} else {
					if (this.updatePassword.repeatPwd !== this.updatePassword.newPwd) {
						callback(new Error('两次输入的密码不一致'));
					} else {
						callback();
					}
				}
			},
		}
	}

</script>
<style>
	.my-info-vue{}
	.my-info-vue .box-card{margin: 10px;}
</style>

