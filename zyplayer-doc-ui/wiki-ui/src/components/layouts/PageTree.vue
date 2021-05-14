<template>
	<van-collapse v-model="pageSelect">
		<template v-for="page in pageList">
			<div v-if="!page.children" @click="pageSelectChange(page.id)" class="van-cell van-cell--clickable">{{page.name}}</div>
			<van-collapse-item :name="page.id" v-else>
				<span slot="title" @click="pageSelectChange(page.id)">{{page.name}}</span>
				<page-tree :page-list="page.children" @pageChange="pageSelectChange"></page-tree>
			</van-collapse-item>
		</template>
	</van-collapse>
</template>

<script>
	export default {
		name: 'pageTree',
		data() {
			return {
				pageSelect: [],
			}
		},
		props: {pageList: Array},
		mounted () {
		},
		methods: {
			pageSelectChange(value) {
				this.$emit('pageChange', value);
			},
		}
	};
</script>

<style scoped>
</style>

