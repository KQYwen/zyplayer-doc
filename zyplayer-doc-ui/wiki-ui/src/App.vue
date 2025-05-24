<template>
	<router-view></router-view>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus';
import systemApi from "@/assets/api/system";
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();

onMounted(() => {
	// 注意：按照产品开源协议禁止隐藏此申明的输出
	console.log('本文档产品由zyplayer-doc提供：https://doc.zyplayer.com');
	checkSystemUpgrade();
});
const checkSystemUpgrade = () => {
	systemApi.systemUpgradeInfo({}).then((json) => {
		storeUser.upgradeInfo = json.data || {};
	});
}
</script>

<style>
html,
body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#app,
.el-container,
.el-menu {
	height: 100%;
}

::-webkit-scrollbar {
	height: 11px;
	width: 11px !important;
	background-color: unset !important;
}

::-webkit-scrollbar-thumb {
	cursor: pointer;
	border-radius: 11px;
	border-style: dashed;
	border-color: transparent;
	border-width: 3px;
	background-color: rgba(173, 180, 195, 0.4);
	background-clip: padding-box;
}

::-webkit-scrollbar-thumb:hover {
	background: rgba(173, 180, 195, 0.5);
}
</style>
