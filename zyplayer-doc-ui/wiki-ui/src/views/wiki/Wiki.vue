<template>
	<div class="space-create-box">
		<CreateRow></CreateRow>
	</div>
	<div class="space-view-box">
		<Whole></Whole>
	</div>
</template>

<script setup>
import CreateRow from './space/CreateRow.vue'
import Whole from './space/Whole.vue'
import {onMounted, watch} from 'vue';
import {useRoute} from "vue-router";

let route = useRoute();
onMounted(() => {
});
</script>

<style lang="scss" scoped>
.space-create-box {
	z-index: 101;
	position: relative;
	padding: 0 10px;
}

.space-view-box {
	height: calc(100vh - 130px);
	overflow-y: auto;
}
</style>
