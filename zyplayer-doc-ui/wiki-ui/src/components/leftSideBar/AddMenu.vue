<template>
	<a-dropdown :trigger="['click']" @click="choosePageIdFunc(props.funcId)">
		<el-button :icon="ElIconPlus" text class="add-menu-dropdown-btn"></el-button>
		<template #overlay>
			<a-menu>
				<a-menu-item key="1" @click="createWiki(1, props.funcId)">
					<IconParkWord fill="#498ba7"/> 创建富文本
				</a-menu-item>
				<a-menu-item key="2" @click="createWiki(2, props.funcId)">
					<IconDocument fill="#558ff2"/> 创建Markdown
				</a-menu-item>
				<a-menu-item key="0" @click="createWiki(0, props.funcId)">
					<FolderOpen fill="#ffd149"/> 创建文件夹
				</a-menu-item>
				<a-menu-item key="4" @click="createWikiByTemplate(props.funcId)">
					<IconParkPageTemplate/> 从模板创建
				</a-menu-item>
				<a-menu-item key="3">
					<el-tooltip content="支持MD，ZIP格式（图片和MD文件请放到同级目录并配置同级相对路径）" placement="right-start" :show-after="300">
						<a-upload v-model:file-list="fileList" name="file" :multiple="false" :customRequest="doAUpload" class="import-upload">
							<IconParkAfferent/> 导入
						</a-upload>
					</el-tooltip>
				</a-menu-item>
			</a-menu>
		</template>
	</a-dropdown>
</template>

<script setup>
import {
	Plus as ElIconPlus,
} from '@element-plus/icons-vue'
import {
	FolderOpen,
	Word as IconParkWord,
	Afferent as IconParkAfferent,
	PageTemplate as IconParkPageTemplate,
} from '@icon-park/vue-next'
import {ref, defineProps, defineEmits} from 'vue';
import {useRouter} from "vue-router";
import {ElMessage} from 'element-plus'
import pageApi from '../../assets/api/page'
import axios from "axios";
import IconDocument from '@/components/base/IconDocument.vue'

let router = useRouter();
let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/import/upload');
let fileList = ref([]);
let emit = defineEmits(['choosePageIdFunc', 'doGetPageList', 'createWikiByTemplate'])
let props = defineProps({
	choiceSpace: Number,
	choosePageId: Number,
	nowPageId: Number,
	funcId: Number
});
const doAUpload = (data) => {
	let formData = new FormData();
	formData.append('files', data.file);
	formData.append('pageId', props.choosePageId);
	if (props.choosePageId === 0) {
		formData.append('id', props.choiceSpace);
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
		emit('doGetPageList', null);

	}).catch((e) => {
		fileList.value = [];
		emit('doGetPageList', null);
		ElMessage.error('导入失败：' + e.message);
	});
}
const choosePageIdFunc = (id) => {
	emit('choosePageIdFunc', id)
}
const createWikiByTemplate = (id) => {
	emit('createWikiByTemplate', id)
}
const createWiki = (editorType, parentId) => {
	if (props.choiceSpace > 0) {
		let name = "新建文档"
		if (editorType === 0) {
			name = "新建文件夹"
		}
		pageApi.updatePage({
			spaceId: props.choiceSpace,
			parentId: parentId,
			editorType: editorType,
			name: name,
			content: '',
			preview: ''
		}).then((json) => {
			emit('doGetPageList', null)
			ElMessage.success('创建成功')
			if (editorType !== 0) {
				router.push({
					path: '/page/edit',
					query: {parentId: props.nowPageId.value, pageId: json.data.id}
				})
			}
		})
	} else {
		ElMessage.warning('请先选择或创建空间')
	}
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
