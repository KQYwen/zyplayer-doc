<template>
	<div ref="rightResize" class="right-resize">
		<i ref="rightResizeBar">...</i>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				rightAsideWidth: 300,
			}
		},
		mounted() {
			this.dragChangeRightAsideWidth();
		},
		methods: {
			dragChangeRightAsideWidth() {
				// 保留this引用
				let resize = this.$refs.rightResize;
				let resizeBar = this.$refs.rightResizeBar;
				resize.onmousedown = e => {
					let startX = e.clientX;
					// 颜色改变提醒
					resize.style.background = "#ccc";
					resizeBar.style.background = "#aaa";
					resize.left = resize.offsetLeft;
					document.onmousemove = e2 => {
						// 计算并应用位移量
						let endX = e2.clientX;
						let moveLen = startX - endX;
						if ((moveLen < 0 && this.rightAsideWidth < 600) || (moveLen > 0 && this.rightAsideWidth > 300)) {
							startX = endX;
							this.rightAsideWidth -= moveLen;
							if (this.rightAsideWidth < 300) {
								this.rightAsideWidth = 300;
							}
							this.$emit('input', this.rightAsideWidth);
							this.$emit('change', this.rightAsideWidth);
						}
					};
					document.onmouseup = () => {
						// 颜色恢复
						resize.style.background = "#fafafa";
						resizeBar.style.background = "#ccc";
						document.onmousemove = null;
						document.onmouseup = null;
					};
					return false;
				};
			}
		}
	};
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

