<template>
	<div class="navigation-box">
		<div class="navigation-content-box">
			<div></div>
			<div class="nav-heading" :style="{ width: navigationWidth }">
				<div v-for="item in heading" :class="'heading-item heading-' + item.level" @click="headingItemClick(item)">
					{{ item.text }}
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, watch, defineEmits, defineProps, defineExpose,} from 'vue';
import {useStoreDisplay} from '@/store/wikiDisplay.js';
import {useStorePageData} from "@/store/pageData";
import {useResizeEvent} from "@/composable/windowsScroll";

let storePage = useStorePageData();
const storeDisplay = useStoreDisplay();
let navigationWidth = ref('100px');
const props = defineProps({
	heading: {type: Array, default: []},
});
onMounted(() => {
	setTimeout(() => computeNavigationWidth(), 100);
});
useResizeEvent(() => {
	computeNavigationWidth();
});
const computeNavigationWidth = () => {

}
const headingItemClick = (item) => {
	// 滚动到指定节点
	item.node.scrollIntoView({
		behavior: 'smooth',
		block: 'start',
		inline: 'nearest',
	});
	// 距离顶部高度
	//console.log(item.node.offsetTop - item.node.scrollHeight);
}
</script>

<style lang="scss">
.navigation-box {
	width: 100px;
	position: absolute;
	top: 150px;
	right: 10px;
	z-index: 4;

	.navigation-content-box {
		position: fixed;
		background: #fff;
		border-radius: 8px;
		padding: 16px;
		margin-left: -40px;
	}

	.nav-heading {
		max-height: calc(100vh - 250px);
		overflow-y: auto;

		.heading-item {
			padding: 5px 0;
			cursor: pointer;
			color: #646a73;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;

			&:hover {
				color: #3370ff;
			}
		}

		.heading-1 {
			padding-left: 0;
		}

		.heading-2 {
			padding-left: 16px;
		}

		.heading-3 {
			padding-left: 32px;
		}

		.heading-4 {
			padding-left: 48px;
		}

		.heading-5 {
			padding-left: 64px;
		}

		.heading-6 {
			padding-left: 80px;
		}
	}
}
</style>
