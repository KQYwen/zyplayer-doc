<template>
	<a-dropdown trigger="click" placement="bottomRight" arrow overlayClassName="header-action-user-dropdown">
		<a-button :icon="h(UserOutlined)" size="large" type="text" style="color: #fff;"></a-button>
		<template #overlay>
			<a-menu>
				<a-menu-item @click="showAbout">关于</a-menu-item>
				<a-menu-divider />
				<a-menu-item @click="userSignOut" danger>退出登录</a-menu-item>
			</a-menu>
		</template>
	</a-dropdown>
	<AboutDialog v-model:visible="aboutDialogVisible"/>
</template>

<script setup>
import {UserOutlined} from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, defineEmits, h, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import userApi from "@/assets/api/user";
import AboutDialog from "@/views/common/AboutDialog.vue";

let router = useRouter();
const userSignOut = () => {
	userApi.userLogout().then(() => {
		location.reload();
	});
}
let aboutDialogVisible = ref(false);
const showAbout = () => {
	aboutDialogVisible.value = true;
}
</script>

<style lang="scss">
.header-action-user-dropdown {
	width: 120px;
}
</style>
