<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="storeDisplay.showMenu" :style="leftAsideStyle" class="left-aside-outer-box">
				<LeftAside/>
			</el-aside>
			<RightResize v-show="storeDisplay.showMenu" v-model="storeDisplay.rightAsideWidth" @change="rightAsideWidthChange"></RightResize>
			<el-container>
				<el-header v-if="storeDisplay.showHeader">
					<RightHeader ref="rightHeaderRef"/>
				</el-header>
				<el-main style="padding: 0;">
					<router-view/>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, defineProps, watch, defineEmits, computed, defineExpose} from 'vue';
import {useStoreDisplay} from '@/store/wikiDisplay.js'
import LeftAside from './aside/LeftAside.vue'
import RightHeader from './aside/RightHeader.vue'
import RightResize from './aside/RightResize.vue'
import userApi from "@/assets/api/user";
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();
let storeDisplay = useStoreDisplay();
onMounted(() => {
	getSelfUserInfo();
});
const rightAsideWidthChange = (width) => {
	storeDisplay.rightAsideWidth = width;
	storeDisplay.commentShow = width;
}
let leftAsideStyle = computed(() => {
	return {width: storeDisplay.rightAsideWidth + 'px'};
});
const getSelfUserInfo = () => {
	userApi.getSelfUserInfo().then((json) => {
		storeUser.userInfo = json.data || {};
	});
}
</script>

<style lang="scss">
.left-aside-outer-box {
	border-right: 1px solid #eee;
	background: #fafafa;
}
</style>

<style>
html,
body {
	margin: 0;
	padding: 0;
	height: 100%;
}

.global-layout-vue {
	height: 100%;
}

.hidTree {
	display: none;
}

#app, .el-container, .el-menu {
	height: 100%;
}

.el-header {
	background-color: #fff !important;
}

.header-right-user-name {
	color: #000000;
	vertical-align: middle;
}

.el-header {
	color: #333;
	height: 60px !important;
	background-color: #fff !important;
	border-bottom: 1px solid #eee;
}

.head-icon {
	margin-right: 15px;
	margin-top: 15px;
	font-size: 16px;
	cursor: pointer;
	color: #000000;
	vertical-align: middle;
}

.header-user-message .page-info-box {
	text-align: right;
	margin-top: 10px;
}

.upgrade-info {
	max-height: 150px;
	overflow-y: auto;
	word-break: break-all;
	white-space: pre-wrap;
	line-height: 26px;
}

.search-option-item {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.search-option-item .title {
	font-weight: bold;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.search-option-item .content {
	font-size: 12px;
	color: #888;
}

</style>
