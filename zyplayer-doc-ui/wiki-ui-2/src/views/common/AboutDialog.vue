<template>
	<!--关于弹窗-->
	<el-dialog title="关于" v-model="aboutDialogVisible" width="600px" class="about-zyplayer-doc">
		<div style="">
			<div style="font-weight: bold; font-size: 25px">zyplayer-doc</div>
			<div style="line-height: 30px; padding: 10px 0">
				<div>版本 {{ upgradeInfo.nowVersion || '1.0.0' }}</div>
				<div>
					版权所有 © 2018-2021
					<a target="_blank" href="http://doc.zyplayer.com">doc.zyplayer.com</a>
				</div>
			</div>
			<el-tabs type="border-card">
				<el-tab-pane label="支持">
					<div style="line-height: 30px">
						<div>文档：<a target="_blank" href="http://doc.zyplayer.com/doc-wiki#/docs/w4eSzPWvQRSBvaCHZS8t6d">http://doc.zyplayer.com</a></div>
						<div>主页：<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc">https://gitee.com/zyplayer/zyplayer-doc</a></div>
						<div>反馈：<a target="_blank" href="https://gitee.com/zyplayer/zyplayer-doc/issues">https://gitee.com/zyplayer/zyplayer-doc/issues</a></div>
						<div>特性关注&技术交流QQ群：466363173</div>
						<el-divider content-position="left">UI/设计/开发/测试</el-divider>
						<div>
							<a target="_blank" href="http://zyplayer.com">暮光：城中城</a>
						</div>
					</div>
				</el-tab-pane>
				<el-tab-pane label="开源软件">
					<div style="line-height: 30px">
						<div>此项目基于以下开源软件构建</div>
						<el-divider content-position="left">后端</el-divider>
						<div>
							<a target="_blank" href="https://spring.io/projects/spring-boot">Spring-Boot</a>、
							<a target="_blank" href="http://www.mybatis.org">MyBatis</a>、
							<a target="_blank" href="https://github.com/alibaba/druid">Druid</a>、
							<a target="_blank" href="https://mp.baomidou.com">MyBatis-Plus</a>、
							<a target="_blank" href="https://www.hutool.cn">Hutool</a>、
							<a target="_blank" href="https://github.com/alibaba/fastjson">Fastjson</a>、
							<a target="_blank" href="https://alibaba-easyexcel.github.io">Easy Excel</a>、
							<a target="_blank" href="https://swagger.io">Swagger</a>、
							<a target="_blank" href="http://www.eclipse.org/jgit">JGit</a>、...
						</div>
						<el-divider content-position="left">前端</el-divider>
						<div>
							Vue、element-ui、wangeditor、mavon-editor、qrcodejs2、vant、vue-router、axios、vue-hljs、brace、echarts、sql-formatter、vue-clipboard2、...
						</div>
					</div>
				</el-tab-pane>
				<el-tab-pane label="软件更新" v-if="upgradeInfo.lastVersion">
					<template v-slot:label>
			            <span>
			              软件更新
			              <sup class="el-badge__content el-badge__content--undefined is-fixed is-dot" style="top: 10px; right: 20px"></sup></span>
					</template>
					<div style="line-height: 30px">
						<div>当前版本：{{ upgradeInfo.nowVersion }}</div>
						<div>最新版本：{{ upgradeInfo.lastVersion }}</div>
						<div>
							升级地址：<a target="_blank" :href="upgradeInfo.upgradeUrl">{{upgradeInfo.upgradeUrl}}</a>
						</div>
						<div>升级内容：</div>
						<pre style="margin: 0; max-height: 250px; overflow: auto">{{upgradeInfo.upgradeContent}}</pre>
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>
	</el-dialog>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import systemApi from '../../assets/api/system'

onMounted(() => {
	checkSystemUpgrade()
});

let aboutDialogVisible = ref(false);
let upgradeInfo = ref({});
const show = () => {
	aboutDialogVisible.value = true
}
const checkSystemUpgrade = () => {
	systemApi.systemUpgradeInfo({}).then((json) => {
		if (!!json.data) {
			upgradeInfo.value = json.data
			if (!!upgradeInfo.value.upgradeContent) {
				upgradeInfo.value.upgradeContent = upgradeInfo.value.upgradeContent.replaceAll('；', '\n')
				console.log('zyplayer-doc发现新版本：'
					+ '\n升级地址：' + json.data.upgradeUrl
					+ '\n当前版本：' + json.data.nowVersion
					+ '\n最新版本：' + json.data.lastVersion
					+ '\n升级内容：' + json.data.upgradeContent
				)
			}
		}
	})
}
defineExpose({show});
</script>

<style>
.about-zyplayer-doc {
	text-align: left;
	line-height: normal;
}

.about-zyplayer-doc .el-dialog__body {
	padding: 20px;
}
</style>
