<template>
	<a-menu v-model:selectedKeys="storeUser.menuRouteKey" mode="inline" class="menu-view-box" style="border-right: 0;" >
		<a-menu-item key="wiki-space" :icon="h(IconParkNotebook)">
			<router-link to="/wiki/space">知识库</router-link>
		</a-menu-item>
		<a-sub-menu vxxxx-if="storeUser.userInfo.isManager" title="系统管理" key="manage" :icon="h(IconParkSettingConfig)">
			<a-menu-item key="user-userList" :icon="h(IconParkUser)">
				<router-link to="/user/userList">用户管理</router-link>
			</a-menu-item>
			<a-menu-item key="user-department" :icon="h(IconParkPeoples)">
				<router-link to="/user/department">部门管理</router-link>
			</a-menu-item>
			<a-menu-item key="system-setting" :icon="h(IconParkSettingTwo)">
				<router-link to="/system/setting">系统配置</router-link>
			</a-menu-item>
		</a-sub-menu>
	</a-menu>
</template>

<script setup>
import {
	Peoples as IconParkPeoples,
	User as IconParkUser,
	SettingConfig as IconParkSettingConfig,
	Notebook as IconParkNotebook,
	SettingTwo as IconParkSettingTwo,
} from '@icon-park/vue-next'
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, watch, h, defineEmits, computed} from 'vue';
import { message } from 'ant-design-vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();
const route = useRoute();
const router = useRouter();
onMounted(() => {
});
</script>

<style lang="scss">
.menu-badge .el-badge__content {
	margin-top: 14px;
	right: 0 !important;
}

.menu-view-box {
	.i-icon {
		font-size: 18px;
	}
}

// 左侧菜单收缩时的样式，上面图标、下面文字，样式强制覆盖
.left-layout-sider.ant-layout-sider-collapsed {
	.ant-menu {
		> .ant-menu-item, > .ant-menu-submenu .ant-menu-submenu-title {
			height: 60px;
			line-height: 40px;
			padding-inline: unset;

			.ant-menu-item-icon {
				display: block;
				line-height: 20px;
				margin-top: 10px;
			}

			.ant-menu-title-content {
				display: block;
				opacity: 1;
				margin-inline-start: 8px;
				line-height: 24px;
				width: 56px;
				overflow: hidden;
				text-align: center;
			}
		}
	}
}
</style>
