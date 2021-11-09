<template>
	<router-view v-slot="{ Component, route }">
		<keep-alive>
			<component :is="Component" :key="route.fullPath" />
		</keep-alive>
	</router-view>
</template>

<script>
	export default {
		name: 'EmptyLayout',
		components: {},
		props: [],
		data() {
			return {}
		},
		methods: {}
	}
</script>
