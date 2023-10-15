<template>
	<!--关于弹窗-->
	<el-dialog title="关于" v-model="aboutDialogVisible" width="600px" class="about-zyplayer-doc">
		<div class="about-content">
			<div class="doc-name">zyplayer-doc</div>
			<div class="doc-desc">
				<div class="slogan">专注于私有化部署的在线知识库管理平台</div>
				<div>当前版本 {{ upgradeInfo.nowVersion || '1.0.0' }}</div>
				<div>
					版权所有 © 2018-2023 <a target="_blank" href="https://doc.zyplayer.com">doc.zyplayer.com</a>
				</div>
			</div>
		</div>
	</el-dialog>
</template>

<script setup>
import {onBeforeUnmount, ref, shallowRef, watch, onMounted, defineProps, defineEmits, defineExpose} from 'vue'
import {useRouter, useRoute} from "vue-router";
import systemApi from '@/assets/api/system'
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();
const props = defineProps({
	visible: Boolean,
});
let aboutDialogVisible = ref(false);
const emit = defineEmits(['update:visible', 'ok']);
watch(aboutDialogVisible, () => {
	emit('update:visible', aboutDialogVisible.value);
});
watch(() => props.visible, () => {
	aboutDialogVisible.value = props.visible;
});
onMounted(() => {
	aboutDialogVisible.value = props.visible;
	checkSystemUpgrade();
});
let upgradeInfo = ref({});
const checkSystemUpgrade = () => {
	systemApi.systemUpgradeInfo({}).then((json) => {
		if (!!json.data) {
			upgradeInfo.value = json.data;
		}
	});
}
</script>

<style lang="scss">
.about-zyplayer-doc {
  text-align: left;
  line-height: normal;

  .el-dialog__body {
	padding: 20px;
  }

  .about-content {
	.doc-name {
	  font-weight: bold;
	  font-size: 25px;
	}

	.doc-desc {
	  line-height: 30px;
	  padding: 10px 0;

	  .slogan {
		margin-bottom: 30px;
	  }
	}
  }
}
</style>
