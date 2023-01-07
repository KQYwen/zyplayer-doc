<template>
	<div ref="rightResizeRef" class="right-resize">
		<i ref="rightResizeBarRef">...</i>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';

let rightAsideWidth = 300;
let emit = defineEmits(['update:value', 'change']);
onMounted(() => {
	dragChangeRightAsideWidth();
});
let rightResizeRef = ref();
let rightResizeBarRef = ref();
const dragChangeRightAsideWidth = () => {
	// 保留this引用
	let resize = rightResizeRef.value
	let resizeBar = rightResizeBarRef.value
	resize.onmousedown = (e) => {
		let startX = e.clientX
		// 颜色改变提醒
		resize.style.background = '#ccc'
		resizeBar.style.background = '#aaa'
		resize.left = resize.offsetLeft
		document.onmousemove = (e2) => {
			// 计算并应用位移量
			let endX = e2.clientX
			let moveLen = startX - endX
			if ((moveLen < 0 && rightAsideWidth < 600) || (moveLen > 0 && rightAsideWidth > 300)) {
				startX = endX
				rightAsideWidth -= moveLen
				if (rightAsideWidth < 300) {
					rightAsideWidth = 300
				}
				emit('update:value', rightAsideWidth)
				emit('change', rightAsideWidth)
			}
		}
		document.onmouseup = () => {
			// 颜色恢复
			resize.style.background = '#fafafa'
			resizeBar.style.background = '#ccc'
			document.onmousemove = null
			document.onmouseup = null
		}
		return false
	}
}
</script>

<style scoped>
.right-resize {
	width: 5px;
	height: 100%;
	cursor: w-resize;
	background: #fafafa;
}

.right-resize i {
	margin-top: 300px;
	width: 5px;
	height: 35px;
	display: inline-block;
	word-wrap: break-word;
	word-break: break-all;
	line-height: 8px;
	border-radius: 5px;
	background: #ccc;
	color: #888;
}
</style>
