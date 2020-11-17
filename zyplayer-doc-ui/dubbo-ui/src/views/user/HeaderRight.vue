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
		<!--关于弹窗-->
		<el-dialog title="关于zyplayer-doc-dubbo" :visible.sync="aboutDialogVisible" width="600px" style="text-align: left;">
			<el-form>
				<el-form-item label="开源地址：">
					<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">zyplayer-doc</a>
				</el-form-item>
				<el-form-item label="开发人员：">
					<a target="_blank" href="http://zyplayer.com">暮光：城中城</a>
				</el-form-item>
				<template v-if="upgradeInfo.lastVersion">
					<el-form-item label="当前版本：">{{upgradeInfo.nowVersion}}</el-form-item>
					<el-form-item label="最新版本：">{{upgradeInfo.lastVersion}}</el-form-item>
					<el-form-item label="升级地址：">
						<a target="_blank" :href="upgradeInfo.upgradeUrl">{{upgradeInfo.upgradeUrl}}</a>
					</el-form-item>
					<el-form-item label="升级内容：">{{upgradeInfo.upgradeContent}}</el-form-item>
				</template>
				<el-form-item label="">
					欢迎加群讨论，QQ群号：466363173，欢迎提交需求，欢迎使用和加入开发！
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	import dubboApi from '../../common/api/dubbo'
	import userApi from '../../common/api/user'

	export default {
		data() {
			return {
				userSelfInfo: {},
				aboutDialogVisible: false,
				// 升级信息
				upgradeInfo: {},
			};
		},
		mounted: function () {
			this.getSelfUserInfo();
			this.checkSystemUpgrade();
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
					this.aboutDialogVisible = true;
				} else if (command == 'myInfo') {
					this.$router.push({path: '/user/myInfo'});
				} else if (command == 'console') {
					window.location = process.env.VUE_APP_BASE_API;
				} else {
					this.$message.warning("功能暂未开放");
				}
			},
			checkSystemUpgrade() {
				dubboApi.systemUpgradeInfo({}).then(json => {
					if (!!json.data) {
						this.upgradeInfo = json.data;
						console.log("zyplayer-doc发现新版本："
							+ "\n升级地址：" + json.data.upgradeUrl
							+ "\n当前版本：" + json.data.nowVersion
							+ "\n最新版本：" + json.data.lastVersion
							+ "\n升级内容：" + json.data.upgradeContent
						);
					}
				});
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

