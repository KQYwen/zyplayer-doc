<template>
	<div style="padding: 10px;" class="page-editor-vue">
		<mg-editor></mg-editor>
	</div>
</template>

<script>
	import mgEditor from '../../components/editor2/MgEditor'

	export default {
		data() {
			return {
				editor: {},
			};
		},
		components: {
			'mg-editor': mgEditor,
		},
		mounted: function () {
		},
		methods: {
		}
	}
</script>
<style>
</style>

