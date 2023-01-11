<template>
	<router-view></router-view>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
</script>

<style>
html,
body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#app,
.el-container,
.el-menu {
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
