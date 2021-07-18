<template>
	<div class="header-right">
		<span class="header-right-user-name">{{userSelfInfo.userName}}</span>
		<el-dropdown @command="userSettingDropdown" trigger="click">
			<i class="el-icon-setting" style="margin-right: 15px; font-size: 16px;cursor: pointer;color: #fff;"> </i>
			<el-dropdown-menu slot="dropdown">
				<el-dropdown-item command="console">控制台</el-dropdown-item>
				<el-dropdown-item command="aboutDoc" divided>关于</el-dropdown-item>
				<el-dropdown-item command="myInfo">我的资料</el-dropdown-item>
				<el-dropdown-item command="userSignOut">退出登录</el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>
		<about-dialog ref="aboutDialog"></about-dialog>
	</div>
</template>

<script>
	import dubboApi from '../../common/api/dubbo'
	import userApi from '../../common/api/user'
	import aboutDialog from '../common/AboutDialog'

	export default {
		data() {
			return {
				userSelfInfo: {},
			};
		},
		components: {
			'about-dialog': aboutDialog
		},
		mounted() {
			this.getSelfUserInfo();
		},
		methods: {
			userSignOut() {
				userApi.userLogout().then(() => {
					location.reload();
				});
			},
			getSelfUserInfo() {
				userApi.getSelfUserInfo().then(json => {
					this.userSelfInfo = json.data;
				});
			},
			userSettingDropdown(command) {
				console.log("command:" + command);
				if (command == 'userSignOut') {
					this.userSignOut();
				} else if (command == 'aboutDoc') {
					this.$refs.aboutDialog.show();
				} else if (command == 'myInfo') {
					this.$router.push({path: '/user/myInfo'});
				} else if (command == 'console') {
					window.open(process.env.VUE_APP_BASE_API, '_blank');
				} else {
					this.$message.warning("功能暂未开放");
				}
			},
		}
	}

</script>
<style>
	.header-right-user-name{color: #fff;padding-right: 5px;}
	.el-menu-vertical{border-right: 0;background: #fafafa;}
	.el-menu-vertical .el-menu{background: #fafafa;}
	.el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}
</style>

