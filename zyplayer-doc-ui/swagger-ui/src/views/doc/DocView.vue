<template>
	<a-tabs v-model:activeKey="activePage" closable @tab-click="changePage" style="padding: 5px 10px 0;">
		<a-tab-pane tab="接口说明" key="doc"/>
		<a-tab-pane tab="在线调试" key="debug"/>
	</a-tabs>
</template>

<script>
	export default {
		name: 'About',
		components: {},
		data() {
			return {
				activePage: 'doc'
			}
		},
		computed: {},
		mounted() {
			let path = this.$route.query.path;
			let docInfo = this.$store.state.docMap[path];
			if(!docInfo) {
				this.$message.error('没有找到对应的文档');
				return;
			}
			this.$store.commit('addTableName', {key: this.$route.fullPath, val: docInfo.name});
		},
		methods: {
			changePage(){

			}
		}
	}
</script>
