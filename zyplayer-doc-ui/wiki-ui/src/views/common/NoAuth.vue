<template>
	<div>没有权限访问该模块</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
</script>
