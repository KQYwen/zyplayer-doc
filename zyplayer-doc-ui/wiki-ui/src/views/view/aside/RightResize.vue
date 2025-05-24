<template>
	<div ref="rightResizeRef" class="right-resize hide-on-mp" :style="{left: (modelValue-4)+'px'}">
		<div class="line"></div>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, watch, defineEmits, computed} from 'vue';

const props = defineProps({
	modelValue: Number,
	max: {type: Number, default: 600},
	min: {type: Number, default: 240}
});
let emit = defineEmits(['update:modelValue', 'change']);

onMounted(() => {
	dragChangeRightAsideWidth();
});
let rightResizeRef = ref();
const dragChangeRightAsideWidth = () => {
	let resize = rightResizeRef.value;
	resize.onmousedown = e => {
		let startX = e.clientX;
		let rightAsideWidth = props.modelValue;
		// 颜色改变提醒
		resize.classList.add('active');
		document.onmousemove = e2 => {
			// 计算并应用位移量
			let endX = e2.clientX;
			let moveLen = startX - endX;
			if ((moveLen < 0 && rightAsideWidth < props.max) || (moveLen > 0 && rightAsideWidth > props.min)) {
				startX = endX;
				rightAsideWidth -= moveLen;
				rightAsideWidth = Math.max(rightAsideWidth, props.min);
				rightAsideWidth = Math.min(rightAsideWidth, props.max);
				emit('update:modelValue', rightAsideWidth);
				emit('change', rightAsideWidth);
			}
		};
		document.onmouseup = () => {
			resize.classList.remove('active');
			document.onmousemove = null;
			document.onmouseup = null;
		};
		return false;
	};
}
</script>

<style scoped>
.right-resize {
	height: 100%;
	padding: 0 4px;
	cursor: w-resize;
	z-index: 200;
	position: absolute;
}

.right-resize .line {
	width: 2px;
	height: 100%;
}

.right-resize:hover .line,
.right-resize.active .line {
	background: #2876d7;
}
</style>

