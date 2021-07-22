<template>
	<div id="app">
		<template v-if="fullscreen">
			<router-view @loginSuccess="getSelfUserInfo"></router-view>
		</template>
		<el-container v-else>
			<el-aside>
				<div class="menu-box">
					<el-menu default-active="1-4-1" :router="true" class="el-menu-vertical" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
						<el-menu-item index="/"><i class="el-icon-s-home"></i>控制台</el-menu-item>
						<el-submenu index="1" v-if="userAuth.userManage">
							<template slot="title">
								<i class="el-icon-s-platform"></i>
								<span slot="title">系统管理</span>
							</template>
							<el-menu-item index="/console/userList">
								<people theme="filled" size="16" fill="#909399"></people>
								<span>用户管理</span>
							</el-menu-item>
							<el-menu-item index="/console/userGroupList">
								<peoples theme="filled" size="16" fill="#909399"></peoples>
								<span>分组管理</span>
							</el-menu-item>
						</el-submenu>
					</el-menu>
				</div>
			</el-aside>
			<el-container>
				<el-header>
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
				</el-header>
				<el-main style="padding: 0;">
					<router-view></router-view>
				</el-main>
			</el-container>
		</el-container>
		<about-dialog ref="aboutDialog"></about-dialog>
	</div>
</template>

<script>
	import consoleApi from './common/api/console'
	import {Peoples, People} from '@icon-park/vue';
	import aboutDialog from './views/common/AboutDialog'

	export default {
		data() {
			return {
				isCollapse: false,
				userSelfInfo: {},
				userAuth: {
					userManage: false
				},
			}
		},
		components: {
			"peoples": Peoples,
			"people": People,
			'about-dialog': aboutDialog
		},
		computed: {
			fullscreen () {
				return this.$store.state.global.fullscreen;
			}
		},
		mounted() {
			this.getSelfUserInfo();
		},
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
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
					this.$message.warn("功能暂未开放");
				}
			},
			userSignOut() {
				consoleApi.userLogout().then(() => {
					location.reload();
				}).catch(e => {
					console.log("退出登录失败", e);
				});
			},
			getSelfUserInfo() {
				consoleApi.selfInfoWithAuth().then(json => {
					let infoVo = json.data || {};
					this.userSelfInfo = infoVo.userInfo || {};
					this.userAuth = infoVo.userAuth || {};
				}).catch(e => {
					console.log("获取用户信息失败", e);
				});
			},
		}
	}
</script>

<style>
	html, body {
		margin: 0;
		padding: 0;
		height: 100%;
	}
	#app, .el-container, .el-menu {
		height: 100%;
	}
	.el-header {
		background-color: #1D4E89 !important;
	}
	.header-right-user-name{color: #fff;padding-right: 5px;}
	.el-menu-vertical{border-right: 0;background: #fafafa;}
	.el-menu-vertical .el-menu{background: #fafafa;}
	.el-header {background-color: #409EFF; color: #333; line-height: 40px; text-align: right;height: 40px !important;}

	.menu-box{padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;}
	.menu-box .i-icon{line-height: 1;margin-right: 5px;}
</style>
