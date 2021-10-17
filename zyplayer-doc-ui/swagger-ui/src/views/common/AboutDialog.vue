<template>
	<!--关于弹窗-->
	<a-modal v-model:visible="aboutDialogVisible"  title="关于" width="600px" :footer="null">
		<div style="">
			<div style="font-weight: bold;font-size: 25px;">zyplayer-doc</div>
			<div style="line-height: 30px;padding: 10px 0;">
				<div>版本 {{upgradeInfo.nowVersion || '1.0.0'}}</div>
				<div>版权所有 © 2018-2021 <a target="_blank" href="http://doc.zyplayer.com">doc.zyplayer.com</a></div>
			</div>
			<a-tabs type="card">
				<a-tab-pane tab="支持" key="support">
					<div style="line-height: 30px;">
						<div>文档：<a target="_blank" href="http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb">http://doc.zyplayer.com</a></div>
						<div>主页：<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">https://gitee.com/zyplayer/zyplayer-doc</a></div>
						<div>反馈：<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc/issues">https://gitee.com/zyplayer/zyplayer-doc/issues</a></div>
						<div>特性关注&技术交流QQ群：466363173</div>
						<a-divider content-position="left">UI/设计/开发/测试</a-divider>
						<div><a target="_blank" href="http://zyplayer.com">暮光：城中城</a></div>
					</div>
				</a-tab-pane>
				<a-tab-pane tab="开源软件" key="software">
					<div style="line-height: 30px;">
						<div>此项目基于以下开源软件构建</div>
						<a-divider content-position="left">后端</a-divider>
						<div>
							<a target="_blank" href="https://spring.io/projects/spring-boot">Spring-Boot</a>、
							<a target="_blank" href="http://www.mybatis.org">MyBatis</a>、
							<a target="_blank" href="https://github.com/alibaba/druid">Druid</a>、
							<a target="_blank" href="https://mp.baomidou.com">MyBatis-Plus</a>、
							<a target="_blank" href="https://www.hutool.cn">Hutool</a>、
							<a target="_blank" href="https://github.com/alibaba/fastjson">Fastjson</a>、
							<a target="_blank" href="https://alibaba-easyexcel.github.io">Easy Excel</a>、
							<a target="_blank" href="https://swagger.io">Swagger</a>、
							<a target="_blank" href="https://dubbo.io">Dubbo</a>、
							<a target="_blank" href="http://www.eclipse.org/jgit">JGit</a>、...
						</div>
						<a-divider content-position="left">前端</a-divider>
						Vue、element-ui、wangeditor、mavon-editor、qrcodejs2、vant、vue-router、axios、vue-hljs、brace、echarts、sql-formatter、vue-clipboard2、...
						<div>
						</div>
					</div>
				</a-tab-pane>
				<a-tab-pane tab="软件更新" key="upgrade" v-if="upgradeInfo.lastVersion">
					<span slot="label">
						软件更新
						<sup class="el-badge__content el-badge__content--undefined is-fixed is-dot" style="top: 10px;right: 20px;"></sup>
					</span>
					<div style="line-height: 30px;">
						<div>当前版本：{{upgradeInfo.nowVersion}}</div>
						<div>最新版本：{{upgradeInfo.lastVersion}}</div>
						<div>升级地址：<a target="_blank" :href="upgradeInfo.upgradeUrl">{{upgradeInfo.upgradeUrl}}</a></div>
						<div>升级内容：</div>
						<pre style="margin: 0; max-height: 250px; overflow: auto;">{{upgradeInfo.upgradeContent}}</pre>
					</div>
				</a-tab-pane>
			</a-tabs>
		</div>
	</a-modal>
</template>

<script>
    import {zyplayerApi} from "../../api/index";

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
				zyplayerApi.systemUpgradeInfo({}).then(json => {
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

