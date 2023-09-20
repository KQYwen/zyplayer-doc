<template>
	<!--手机扫码查看弹窗-->
	<el-dialog title="手机扫码查看" v-model="dataItemEditVisible" width="400px">
		<div style="text-align: center">
			<div class="mobile-qr">
				<canvas ref="qrCodeDivRef"></canvas>
			</div>
			<div>使用微信或手机浏览器扫一扫查看</div>
			<div>或 <a target="_blank" :href="qrCodeUrl">直接访问</a></div>
		</div>
	</el-dialog>
</template>

<script setup>
import {
	Search as ElIconSearch,
} from '@element-plus/icons-vue'
import {markRaw} from 'vue'
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, defineProps, watch, defineEmits, computed, defineExpose} from 'vue';
import {useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage, ElLoading, ElNotification} from 'element-plus'
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";
import userApi from "@/assets/api/user";
import QRCode from 'qrcode'
import {useStoreSpaceData} from "@/store/spaceData";

let storePage = useStorePageData();
let storeSpace = useStoreSpaceData();

const route = useRoute();
const router = useRouter();

const props = defineProps({
	visible: Boolean,
	data: Object,
});

let dataItemEditVisible = ref(false);
const emit = defineEmits(['update:visible', 'ok']);
watch(dataItemEditVisible, () => {
	emit('update:visible', dataItemEditVisible.value);
});
watch(() => props.visible, () => {
	dataItemEditVisible.value = props.visible;
	initMobileQrScan();
});
onMounted(() => {
	dataItemEditVisible.value = props.visible;
	initMobileQrScan();
});
let qrCodeUrl = ref('');
let qrCodeDivRef = ref();
const initMobileQrScan = () => {
	if (!dataItemEditVisible.value) return;
	let routeUrl = router.resolve({
		path: '/page/share/mobile/view',
		query: {pageId: storePage.pageInfo.id, space: storeSpace.spaceInfo.uuid}
	});
	let hostPath = window.location.href.split('#')[0];
	setTimeout(() => {
		qrCodeUrl.value = hostPath + routeUrl.href
		QRCode.toCanvas(qrCodeDivRef.value, qrCodeUrl.value, {
				scale: 5, height: 250, wight: 250,
			}, (error) => {
				if (error) console.error(error);
			}
		)
	}, 0);
}
</script>

<style lang="scss">
.page-auth-dialog {
}
</style>
