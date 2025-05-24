<template>
	<div :style="navigationStyle" class="navigation-box">
		<div v-if="navigationMin">
			<a-button @click="navigationToMax" class="navigation-max-btn" size="large" type="text" shape="circle">
				<IconParkViewList :size="18"/>
			</a-button>
		</div>
		<div v-else :style="navigationStyle" @mouseover="navigationMouseover" @mouseleave="navigationMouseleave"
		     :class="navigationShow?'':'box-shadow'" class="navigation-content-box">
			<div class="nav-heading">
				<div v-for="item in heading" :class="'heading-item heading-' + item.level" @click="headingItemClick(item)">
					{{ item.text }}
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import {ViewList as IconParkViewList} from '@icon-park/vue-next'
import {toRefs, ref, reactive, onMounted, watch, defineEmits, defineProps, defineExpose,} from 'vue';
import {useResizeEvent} from "@/composable/windowsScroll";
import applyOnce from "@/assets/js/applyOnce";
import {useStoreDisplay} from "@/store/wikiDisplay";

let storeDisplay = useStoreDisplay();
const props = defineProps({
	heading: {type: Array, default: []},
});
onMounted(() => {
	setTimeout(() => computeNavigationWidth(), 100);
});
useResizeEvent(() => {
	computeNavigationWidth();
});
watch(() => storeDisplay.rightAsideWidth, (newVal) => {
	computeNavigationWidth();
});
let isLeave = false;
let leaveTimer = undefined;
const navigationMouseover = () => {
	isLeave = false;
	if (leaveTimer) {
		clearTimeout(leaveTimer);
		leaveTimer = undefined;
	}
}
const navigationMouseleave = () => {
	if (isLeave) return;
	isLeave = true;
	leaveTimer = setTimeout(() => {
		isLeave = false;
		navigationMin.value = !navigationShow.value;
	}, 500);
}
const navigationToMax = () => {
	navigationMin.value = false;
}
let navigationMin = ref(false);
let navigationShow = ref(true);
let navigationStyle = ref({width: '200px'});
const computeNavigationWidth = applyOnce(() => {
	let pageViewContent = document.getElementById('pageContentBox');
	let pageContentScrollBox = document.getElementById('pageContentScrollBox');
	// pageContentScrollBox的宽度减去pageViewContent的宽度除以2
	if (pageViewContent && pageContentScrollBox) {
		let outerWidth = pageContentScrollBox.clientWidth - pageViewContent.clientWidth;
		if (outerWidth > 160) {
			navigationShow.value = true;
			navigationStyle.value.width = (outerWidth / 2 - 50) + 'px';
		} else {
			navigationStyle.value.width = '200px';
			navigationShow.value = false;
		}
		navigationMin.value = !navigationShow.value;
	}
}, 500);
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
	position: absolute;
	top: 60px;
	right: 10px;
	z-index: 4;

	.navigation-max-btn {
		position: fixed;
		margin-left: 160px;
		opacity: 0.7;
		box-shadow: var(--el-box-shadow-light);

		&:hover {
			opacity: 1;
			background: #fff;
		}
	}

	.navigation-content-box {
		position: fixed;
		background: #fff;
		border-radius: 8px;
		padding: 16px;
		margin-left: -30px;

		&.box-shadow {
			box-shadow: var(--el-box-shadow-lighter);
		}
	}

	.nav-heading {
		max-height: calc(100vh - 200px);
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
