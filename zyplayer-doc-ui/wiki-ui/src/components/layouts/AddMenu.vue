<template>
	<a-dropdown :trigger="['click']" @click="choosePageIdFunc(props.funcId)">
		<el-button :icon="ElIconPlus" text class="folder-action-dropdown-btn"></el-button>
		<template #overlay>
			<a-menu>
				<a-menu-item key="1" @click="createWiki(1,props.funcId)">
					<el-icon  class="clickAddIcon" style="margin-right: 5px">
						<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><rect x="6" y="6" width="36" height="36" rx="3" fill="none" stroke="currentColor" stroke-width="4"></rect><path d="M14 16L18 32L24 19L30 32L34 16" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
					</el-icon>
					创建富文本
				</a-menu-item>
				<a-menu-item key="2" @click="createWiki(2,props.funcId)">
					<el-icon class="clickAddIcon" style="margin-right: 5px">
						<el-icon-document/>
					</el-icon>
					创建Markdown
				</a-menu-item>
				<a-menu-item key="0" @click="createWiki(0,props.funcId)">
					<el-icon  class="clickAddIcon" style="margin-right: 5px">
						<svg width="1em" height="1em" viewBox="0 0 48 48" fill="none"><path d="M5 8C5 6.89543 5.89543 6 7 6H19L24 12H41C42.1046 12 43 12.8954 43 14V40C43 41.1046 42.1046 42 41 42H7C5.89543 42 5 41.1046 5 40V8Z" fill="none" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M43 22H5" stroke="currentColor" stroke-width="4" stroke-linejoin="round"></path><path d="M5 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path><path d="M43 16V28" stroke="currentColor" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"></path></svg>
					</el-icon>
					创建文件夹
				</a-menu-item>
				<a-menu-item key="3">
					<el-tooltip content="支持MD，ZIP格式（图片和MD文件请放到同级目录并配置同级相对路径）" placement="right-start" :show-after="300">
						<a-upload
								v-model:file-list="fileList"
								name="file"
								:multiple="false"
								:customRequest="doAUpload">
							<el-icon class="clickAddIcon" style="margin-right: 5px" type="primary">
								<ElIconUpload/>
							</el-icon>
							导入
						</a-upload>
					</el-tooltip>
				</a-menu-item>
			</a-menu>
		</template>
	</a-dropdown>
</template>

<script setup>
	import {
		Document as ElIconDocument,
		Upload as ElIconUpload,
		Plus as ElIconPlus,
	} from '@element-plus/icons-vue'
	import {
		ref,
		defineProps,
		defineEmits,
	} from 'vue';
	import {useRouter} from "vue-router";
	import {ElMessage} from 'element-plus'
	import pageApi from '../../assets/api/page'
	import axios from "axios";

	let router = useRouter();
	let uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/zyplayer-doc-wiki/page/file/upload');
	let fileList = ref([]);
	let emit = defineEmits(['choosePageIdFunc', 'doGetPageList'])
	let props = defineProps({
		choiceSpace: String,
		choosePageId: String,
		nowPageId: String,
		funcId: String
	});

	const doAUpload = (data) => {
		let formData = new FormData()
		formData.append('files', data.file)
		formData.append('pageId', props.choosePageId)
		if (props.choosePageId === '0') {
			formData.append('id', props.choiceSpace)
		}
		formData.append('importFlag', true)
		axios({
			url: uploadFileUrl.value,
			method: 'post',
			data: formData,
			headers: {'Content-Type': 'multipart/form-data'},
			timeout: 10000,
			withCredentials: true,
		}).then((res) => {
			fileList.value = []
			if (res.data.errCode === 200) {
				ElMessage.success('导入成功')
			}
			if (res.data.errCode === 300) {
				ElMessage.warning(res.data.errMsg)
				ElMessage.warning('文件太多可能超时，如果是超时，请稍等后刷新查看列表~')
			}
			emit('doGetPageList', null)

		}).catch((e) => {
			fileList.value = []
			emit('doGetPageList', null)
			ElMessage.error('导入失败：' + e.message)
		})
	}


	const choosePageIdFunc = (id) => {
		emit('choosePageIdFunc', id)
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
