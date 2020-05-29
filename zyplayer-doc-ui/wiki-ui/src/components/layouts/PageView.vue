<template>
	<router-view @loadPageList="loadPageList" @changeExpandedKeys="changeExpandedKeys" @switchSpace="switchSpace"/>
</template>

<script>
	export default {
		name: 'PageView',
		components: {},
		data() {
			return {}
		},
		created() {
		},
		watch: {},
		methods: {
			loadPageList(param) {
				this.$emit('loadPageList', param);
			},
			changeExpandedKeys(param) {
				this.$emit('changeExpandedKeys', param);
			},
			switchSpace(param) {
				this.$emit('switchSpace', param);
			},
		}
	}
</script>

<style scoped>

</style>
