<template>
	<div class="setting-box">
		<a-tabs v-model:activeKey="activeTabName">
			<a-tab-pane tab="基本信息" key="base">
				<BaseInfo></BaseInfo>
			</a-tab-pane>
			<a-tab-pane tab="更多" key="more">
				<MoreAction></MoreAction>
			</a-tab-pane>
		</a-tabs>
	</div>
</template>

<script setup>
import BaseInfo from './setting/BaseInfo.vue'
import MoreAction from './setting/MoreAction.vue'
import { message } from 'ant-design-vue';
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, watch, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { useStoreDisplay } from '@/store/wikiDisplay'
import { useStorePageData } from '@/store/pageData'

let route = useRoute();
let router = useRouter();
let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();

onMounted(() => {
	storeDisplay.headerShow = true;
	storeDisplay.currentPage = 'setting';
});

onBeforeUnmount(() => {
	storeDisplay.currentPage = '';
});
let activeTabName = ref('base');
</script>

<style scoped>
.setting-box {
	padding: 0 20px;
}
</style>

