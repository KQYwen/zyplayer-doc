<template>
	<div ref="rightResizeRef" class="right-resize"></div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';

let rightAsideWidth = 300;
let emit = defineEmits(['update:value', 'change']);
onMounted(() => {
	dragChangeRightAsideWidth();
});
let rightResizeRef = ref();
const dragChangeRightAsideWidth = () => {
	// 保留this引用
	let resize = rightResizeRef.value
	resize.onmousedown = (e) => {
		let startX = e.clientX
		// 颜色改变提醒
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
			document.onmousemove = null
			document.onmouseup = null
		}
		return false
	}
}
</script>

<style scoped lang="scss">
.right-resize {
  width: 5px;
  height: 100%;
  cursor: w-resize;
  background: #fafafa;

  &:hover {
	background: #2a85f6;
  }
}
</style>
