<template>
	<el-image-viewer
			v-if="imagePreviewVisible"
			:url-list="showImagePreviewList"
			:initial-index="previewInitialIndex"
			@close="closeImagePreview"
			hide-on-click-modal
	/>
</template>

<script setup>
import {onBeforeUnmount, ref, h, shallowRef, nextTick, onMounted, watch, defineProps, defineExpose} from 'vue';
import {showImagePreview} from 'vant';
import {useStoreDisplay} from "@/store/wikiDisplay";

let storeDisplay = useStoreDisplay();
onMounted(() => {
});
let imagePreviewVisible = ref(false);
let previewInitialIndex = ref(0);
let showImagePreviewList = ref([]);
const closeImagePreview = () => {
	imagePreviewVisible.value = false;
}
const initViewer = (dom) => {
	if (!dom) return;
	const imgDomArr = [];
	const imgSelector = dom.querySelectorAll('img');
	imgSelector.forEach((item) => {
		if (item.hasAttribute('not-allow-click')) return;
		imgDomArr.push(item);
		let index = imgDomArr.length - 1;
		item.onclick = () => {
			let imgArr = [];
			// 点击后再去获取最新的url，防止中途有修改，比如plantuml的图
			imgDomArr.forEach(dom => imgArr.push(dom.src));
			if (storeDisplay.isMobile) {
				showImagePreview({
					images: imgArr,
					startPosition: index,
				});
			} else {
				previewInitialIndex.value = index;
				showImagePreviewList.value = imgArr;
				imagePreviewVisible.value = true;
			}
		}
	});
}
defineExpose({initViewer});
</script>
