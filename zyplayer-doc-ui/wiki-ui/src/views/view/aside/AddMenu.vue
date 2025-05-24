<template>
	<a-dropdown :trigger="['click']" placement="bottom">
		<a-button :icon="h(PlusOutlined)" type="text" style="color: #888;"></a-button>
		<template #overlay>
			<a-menu>
				<a-menu-item @click="createPage(1)">
					<IconParkWord fill="#498ba7"/> 新建富文本
				</a-menu-item>
				<a-menu-item @click="createPage(2)">
					<IconDocument fill="#558ff2"/> 新建Markdown
				</a-menu-item>
				<a-menu-item @click="createFolder">
					<FolderOpen fill="#ffd149"/> 新建文件夹
				</a-menu-item>
<!--				<a-menu-item @click="createPageByTemplate()">-->
<!--					<IconParkPageTemplate/> 从模板创建-->
<!--				</a-menu-item>-->
<!--				<a-menu-item key="3">-->
<!--					<el-tooltip content="支持MD，ZIP格式（图片和MD文件请放到同级目录并配置同级相对路径）" placement="right-start" :show-after="300">-->
<!--						<a-upload v-model:file-list="fileList" name="file" :multiple="false" :customRequest="doAUpload" class="import-upload">-->
<!--							<IconParkAfferent/> 导入-->
<!--						</a-upload>-->
<!--					</el-tooltip>-->
<!--				</a-menu-item>-->
			</a-menu>
		</template>
	</a-dropdown>
</template>

<script setup>
import { PlusOutlined } from '@ant-design/icons-vue';
import {FolderOpen, Word as IconParkWord, Afferent as IconParkAfferent, PageTemplate as IconParkPageTemplate,} from '@icon-park/vue-next';
import {ref, defineProps, defineEmits, h} from 'vue';
import {useRouter} from "vue-router";
import {ElMessage} from 'element-plus';
import pageApi from '@/assets/api/page';
import axios from "axios";
import IconDocument from '@/components/base/IconDocument.vue';
import {useStorePageData} from "@/store/pageData";
import {useStoreSpaceData} from "@/store/spaceData";
import MessagePrompt from "@/components/single/MessagePrompt";

let router = useRouter();
let storePage = useStorePageData();
let storeSpace = useStoreSpaceData();

let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/import/upload');
let fileList = ref([]);
let props = defineProps({
	pageId: Number
});
const doAUpload = (data) => {
	let formData = new FormData();
	formData.append('files', data.file);
	formData.append('pageId', props.pageId);
	if (props.pageId === 0) {
		formData.append('id', storeSpace.chooseSpaceId);
	}
	axios({
		url: uploadFileUrl.value,
		method: 'post',
		data: formData,
		headers: {'Content-Type': 'multipart/form-data'},
		timeout: 10000,
		withCredentials: true,
	}).then((res) => {
		fileList.value = [];
		if (res.data.errCode === 200) {
			ElMessage.success('导入成功');
		}
		if (res.data.errCode === 300) {
			ElMessage.warning(res.data.errMsg);
			ElMessage.warning('文件太多可能超时，如果是超时，请稍等后刷新查看列表~');
		}
		storePage.eventPageListUpdate = !storePage.eventPageListUpdate;
	}).catch((e) => {
		fileList.value = [];
		ElMessage.error('导入失败：' + e.message);
	});
}
const createPageByTemplate = () => {
	// TODO
}
const createPage = (editorType, confirmName) => {
	if (!storeSpace.chooseSpaceId) {
		ElMessage.warning('请先选择或创建空间');
		return;
	}
	let name = "新建文档";
	if (editorType === 0) name = confirmName || "新建文件夹";
	pageApi.updatePage({
		spaceId: storeSpace.chooseSpaceId,
		parentId: props.pageId,
		editorType: editorType,
		name: name,
		content: '',
		preview: ''
	}).then((json) => {
		storePage.eventPageListUpdate = !storePage.eventPageListUpdate;
		ElMessage.success('创建成功');
		if (editorType !== 0) {
			let newPageId = json.data.id;
			router.push({path: `/edit/${storeSpace.chooseSpaceId}/${newPageId}`});
		}
	});
}
let createFolder = () => {
	MessagePrompt({
		title: '新建文件夹',
		label: '文件夹名称',
		placeholder: '请输入文件夹名称',
		validator: (value) => {
			if (!value || !value.trim()) return '请输入文件夹名称';
			if (value && value.length > 40) return '文件夹名称不能超过40个字符';
			return true;
		}
	}).then((value) => {
		if (value && value.trim()) {
			createPage(0, value.trim());
		}
	}).catch(() => {
	});
}
</script>

<style lang="scss">
.add-menu-dropdown-btn {
	padding: 0 8px;
	height: 35px;
	margin-top: -1px;
}

.import-upload {
	.ant-upload-select {
		display: block;

		.ant-upload {
			display: block;
		}
	}
}
</style>
