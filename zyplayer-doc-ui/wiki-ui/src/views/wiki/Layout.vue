<template>
	<a-layout class="wiki-container">
		<a-layout-header theme="light" class="wiki-container-header">
			<HeaderView/>
		</a-layout-header>
		<a-layout>
			<a-layout-sider v-model:collapsed="storeUser.menuCollapsed" collapsible theme="light" class="left-layout-sider">
				<ConsoleMenu></ConsoleMenu>
				<template #trigger>
					<div class="bottom-toggle-btn">
						<a-button v-if="storeUser.menuCollapsed" :icon="h(DoubleRightOutlined)" type="text" size="large" block></a-button>
						<a-button v-else :icon="h(DoubleLeftOutlined)" type="text" size="large" block></a-button>
					</div>
				</template>
			</a-layout-sider>
			<a-layout>
				<div class="right-content-box">
					<router-view></router-view>
				</div>
			</a-layout>
		</a-layout>
	</a-layout>
</template>

<script setup>
import { message } from 'ant-design-vue';
import { DoubleLeftOutlined, DoubleRightOutlined } from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, h, defineEmits, computed} from 'vue';
import {onBeforeRouteUpdate, onBeforeRouteLeave, useRouter, useRoute} from "vue-router";
import HeaderView from "./aside/HeaderView.vue";
import ConsoleMenu from "./aside/ConsoleMenu.vue";
import pageApi from "@/assets/api/page";
import {useStoreUserData} from "@/store/userData";
import {useStoreSpaceData} from "@/store/spaceData";
import userApi from "@/assets/api/user";

let route = useRoute();
let router = useRouter();
let storeUser = useStoreUserData();
let storeSpace = useStoreSpaceData();
onMounted(() => {
	getSelfUserInfo();
	loadSpaceList();
	computeMenuKey(route);
});
onBeforeRouteUpdate((updateGuard) => {
	computeMenuKey(updateGuard);
});
// 空间创建或修改
watch(() => storeSpace.eventSpaceUpdate, (newVal) => {
	loadSpaceList();
});
const computeMenuKey = (to) => {
	// 末级菜单key，暂时只有控制台需要
	if (to.matched && to.matched.length >= 2) {
		let keyIndex = to.matched.length - 1;
		let pathArr = to.matched[keyIndex].path.split('/');
		let menuKey = '', menuKeyArr = [];
		pathArr.filter(item => !!item && item.length > 0).forEach((item, index) => {
			if (index > 0) menuKey += '-';
			menuKey += item;
			menuKeyArr.push(menuKey);
		});
		storeUser.menuRouteKey = menuKeyArr;
	}
}
const getSelfUserInfo = () => {
	userApi.getSelfUserInfo().then((json) => {
		storeUser.userInfo = json.data || {};
	}).catch((e) => {
	});
}
const loadSpaceList = () => {
	pageApi.spaceList().then(json => {
		let resList = json.data || [];
		resList.forEach(item => {
			item.searchText = ((item.name || '') + ' ' + (item.spaceExplain || '')).toLowerCase();
		});
		storeSpace.spaceList = resList;
		storeSpace.wholeSpaceList = resList;
	});
}
</script>

<style lang="scss">
.wiki-container {
	height: 100%;

	.bottom-toggle-btn {
		padding: 0 4px;
		border-right: 1px solid #eee;

		.ant-btn {
			width: 100%;
		}
	}

	.wiki-container-header {
		padding-inline: 0;
		height: 60px;
		line-height: 60px;
	}

	.ant-layout {
		background: #fff;

		.left-layout-sider {
			height: 100%;
			background: #fff;
			border-right: 1px solid #eee;
			transition: unset;

			> .ant-layout-sider-children {
				overflow: auto;
			}
		}
	}

	.right-content-box {
		height: 100%;
		overflow: auto;
		background: #fff;
	}
}
</style>
