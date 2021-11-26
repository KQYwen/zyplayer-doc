<template>
	<div>
		控制台{{swaggerDoc.info}}
	</div>
</template>

<script>
	export default {
		name: 'About',
		components: {},
		data() {
			return {}
		},
		computed: {
			swaggerDoc () {
				return this.$store.state.swaggerDoc;
			}
		},
		mounted() {
		},
		methods: {}
	}
</script>
