<template>
	<!--人员权限弹窗-->
	<el-dialog title="页面权限" v-model="dataItemEditVisible" width="800px" class="page-auth-dialog">
		<el-row>
			<el-select v-model="pageAuthNewUser" filterable remote reserve-keyword autoComplete="new-password" placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList" :loading="pageAuthUserLoading" style="width: 690px; margin-right: 10px">
				<el-option v-for="item in searchUserList" :key="item.id" :label="item.userName" :value="item.id"></el-option>
			</el-select>
			<el-button @click="addPageAuthUser">添加</el-button>
		</el-row>
		<el-table :data="pageAuthUserList" border style="width: 100%; margin: 10px 0">
			<el-table-column prop="userName" label="用户" width="150"></el-table-column>
			<el-table-column label="权限">
				<template v-slot="scope">
					<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.editPage">编辑</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.deletePage">删除</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileUpload">文件上传</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileDelete">文件删除</el-checkbox>
					<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageAuthManage">权限管理</el-checkbox>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="80">
				<template v-slot="scope">
					<el-button size="small" type="danger" plain @click="deleteUserPageAuth(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<template #footer>
			<el-button @click="cancelAuth">取消</el-button>
			<el-button type="primary" @click="saveUserPageAuth">保存配置</el-button>
		</template>
	</el-dialog>
</template>

<script setup>
import {
	Search as ElIconSearch,
} from '@element-plus/icons-vue'
import {markRaw} from 'vue'
import {toRefs, ref, reactive, onMounted, onBeforeUnmount, defineProps, watch, defineEmits, computed, defineExpose} from 'vue';
import {useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage, ElLoading, ElNotification} from 'element-plus'
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";
import userApi from "@/assets/api/user";

let storePage = useStorePageData();

const route = useRoute();
const router = useRouter();

const props = defineProps({
	visible: Boolean,
	data: Object,
});

let dataItemEditVisible = ref(false);
const emit = defineEmits(['update:visible', 'ok']);
watch(dataItemEditVisible, () => {
	emit('update:visible', dataItemEditVisible.value);
});
watch(() => props.visible, () => {
	dataItemEditVisible.value = props.visible;
	initPageAuth();
});
onMounted(() => {
	dataItemEditVisible.value = props.visible;
	initPageAuth();
});

// 页面权限
let pageAuthUserList = ref([]);
let searchUserList = ref([]);
let pageAuthNewUser = ref();
let pageAuthUserLoading = ref(false);
const initPageAuth = () => {
	if (!dataItemEditVisible.value) return;
	pageAuthNewUser.value = '';
	pageAuthUserList.value = [];
	let param = {pageId: storePage.pageInfo.id};
	pageApi.getPageUserAuthList(param).then((json) => {
		pageAuthUserList.value = json.data || [];
	});
}
const addPageAuthUser = () => {
	if (!pageAuthNewUser.value) {
		ElMessage.warning('请先选择用户');
		return;
	}
	// 已添加过的用户不再添加
	if (pageAuthUserList.value.find((item) => item.userId === pageAuthNewUser.value)) {
		pageAuthNewUser.value = undefined;
		return;
	}
	// 获取用户
	let findUser = searchUserList.value.find((item) => item.id === pageAuthNewUser.value);
	if (findUser) {
		return;
	}
	// 添加用户
	pageAuthUserList.value.push({
		userId: findUser.id,
		userName: findUser.userName,
		editPage: 0,
		commentPage: 0,
		deletePage: 0,
		pageFileUpload: 0,
		pageFileDelete: 0,
		pageAuthManage: 0,
	})
	pageAuthNewUser.value = '';
}
const getSearchUserList = (query) => {
	if (!query) return;
	pageAuthUserLoading.value = true;
	userApi.getUserBaseInfo({search: query}).then((json) => {
		searchUserList.value = json.data || [];
		pageAuthUserLoading.value = false;
	});
}
const saveUserPageAuth = () => {
	let param = {
		pageId: storePage.pageInfo.id,
		authList: JSON.stringify(pageAuthUserList.value),
	}
	pageApi.assignPageUserAuth(param).then(() => {
		ElMessage.success('保存成功！');
	});
}
const cancelAuth = () => {
	dataItemEditVisible.value = false;
}
const deleteUserPageAuth = (row) => {
	pageAuthUserList.value = pageAuthUserList.value.filter((item) => item.userId !== row.userId);
}
</script>

<style lang="scss">
.page-auth-dialog {
}
</style>
