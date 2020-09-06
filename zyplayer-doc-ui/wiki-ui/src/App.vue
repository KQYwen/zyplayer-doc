<template>
	<div id="app">
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name: 'app',
		components: {},
		data() {
			return {};
		},
		methods: {}
	}
</script>

<style>
	html, body {
		margin: 0;
		padding: 0;
		height: 100%;
	}

	#app, .el-container, .el-menu {
		height: 100%;
	}
	::-webkit-scrollbar {
		width: 6px;
		height: 9px;
		-webkit-appearance: none;
	}
	::-webkit-scrollbar-thumb {
		background: #ddd;
		border-radius: 10px;
	}
	::-webkit-scrollbar-track-piece {
		background: #eee;
	}
</style>
