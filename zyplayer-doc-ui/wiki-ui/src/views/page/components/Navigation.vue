<template>
	<div class="navigation">
		<div ref="navigation" style="display: inline-block;width: 100%;"></div>
		<div class="navigation-heading" :style="{width: navigationWidth}">
			<div v-for="item in heading" :class="'heading-item heading-'+item.level" @click="headingItemClick(item)">
				{{item.text}}
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		props: {
			heading: {
				type: Array,
				default: []
			},
		},
		data() {
			return {
				navigationWidth: '100px'
			};
		},
		mounted() {
			window.onresize = () => {
				this.computeNavigationWidth();
			}
			setTimeout(() => this.computeNavigationWidth(), 100);
		},
		methods: {
			computeNavigationWidth() {
				this.navigationWidth = window.getComputedStyle(this.$refs.navigation, null).width;
			},
			headingItemClick(item) {
				// 滚动到指定节点
				item.node.scrollIntoView({behavior: "smooth", block: "start", inline: "nearest"});
				// 距离顶部高度
				//console.log(item.node.offsetTop - item.node.scrollHeight)
			},
		}
	}
</script>
<style>
	.navigation {
		width: 100%;
	}

	.navigation-heading {
		position: fixed;
		z-index: 4;
		top: 150px;
		height: calc(100vh - 250px);
		width: 100%;
		overflow-y: auto;
		padding-left: 16px;
		box-sizing: border-box;
	}

	.navigation-heading .heading-item {
		padding: 5px 0;
		cursor: pointer;
		color: #646a73;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.navigation-heading .heading-item:hover {
		color: #3370ff;
	}

	.navigation-heading .heading-1 {
		padding-left: 0;
	}

	.navigation-heading .heading-2 {
		padding-left: 16px;
	}

	.navigation-heading .heading-3 {
		padding-left: 32px;
	}

	.navigation-heading .heading-4 {
		padding-left: 48px;
	}

	.navigation-heading .heading-5 {
		padding-left: 64px;
	}

	.navigation-heading .heading-6 {
		padding-left: 80px;
	}
</style>

