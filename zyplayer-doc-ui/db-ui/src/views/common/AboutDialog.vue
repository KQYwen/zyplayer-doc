<template>
	<!--关于弹窗-->
	<el-dialog title="关于" :visible.sync="aboutDialogVisible" width="600px" custom-class="about-zyplayer-doc">
		<div style="">
			<div style="font-weight: bold;font-size: 25px;">zyplayer-doc</div>
			<div style="line-height: 30px;padding: 10px 0;">
				<div style="margin-bottom: 30px;">专注于私有化部署的在线知识库管理平台</div>
				<div>当前版本 {{upgradeInfo.nowVersion || '1.0.0'}}</div>
				<div>版权所有 © 2018-2023 <a target="_blank" href="https://doc.zyplayer.com">doc.zyplayer.com</a></div>
			</div>
		</div>
	</el-dialog>
</template>

<script>
    import systemApi from "../../common/api/system";

	export default {
        data() {
            return {
				aboutDialogVisible: false,
				upgradeInfo: {},
			};
        },
		mounted() {
			this.checkSystemUpgrade();
		},
		methods: {
			show() {
				this.aboutDialogVisible = true;
			},
			checkSystemUpgrade() {
				systemApi.systemUpgradeInfo({}).then(json => {
					if (!!json.data) {
						this.upgradeInfo = json.data;
						if (!!this.upgradeInfo.upgradeContent) {
							this.upgradeInfo.upgradeContent = this.upgradeInfo.upgradeContent.replaceAll('；', '\n');
						}
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
	.about-zyplayer-doc{text-align: left; line-height: normal;}
	.about-zyplayer-doc .el-dialog__body{padding: 20px;}
</style>

