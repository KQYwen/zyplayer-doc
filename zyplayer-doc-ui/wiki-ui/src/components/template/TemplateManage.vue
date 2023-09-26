<template>
	<el-dialog title="设置为模板" v-model="newTemplateDialogVisible" width="600px" :close-on-click-modal="false">
		<el-form label-width="100px" :model="templateNewForm">
			<el-form-item label="模板标签">
				<el-input v-model="templateNewForm.tagName"></el-input>
			</el-form-item>
			<el-form-item label="是否公开">
				<el-switch v-model="templateNewForm.shareStatus" inactive-text="个人模板" :inactive-value="0" active-text="公共模板" :active-value="1"></el-switch>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" v-if="exsit" @click="onNewTemplateSubmit">保存修改</el-button>
				<el-button type="primary" v-else @click="onNewTemplateSubmit">立即创建</el-button>
				<el-button @click="onNewTemplateCancel">取消</el-button>
			</el-form-item>
		</el-form>
	</el-dialog>
	<a-modal
			v-model:open="templateChooseDialogVisible"
			title="模板库"
			width="100%"
			wrapClassName="full-modal"
			:confirm-loading="aModalWaiting"
			:destroyOnClose=true
			:closable=true>
		<div>
			<el-switch v-model="open" inactive-text="个人模板" :inactive-value="0" active-text="公共模板" :active-value="1" @change="filterByOpen"></el-switch>
			<a-divider type="vertical"/>
			<el-input v-model="name" style="width: 30%" @change="filterByName"></el-input>
		</div>
		<a-divider>模板标签</a-divider>
		<a-checkable-tag v-for="tag in tags" @click="filterByTags(tag.tagName,tag.show)" v-model:checked="tag.show" style="margin: 5px" size="big">{{tag.tagName}}</a-checkable-tag>
		<a-divider/>
		<a-list :grid="{gutter:1,column:4,xs:1,sm:1,md:2,lg:2,xl:4,xxl:4}" :data-source="templateList">
			<template #renderItem="{item}">
				<a-list-item>
					<a-card :title="item.name">
						<a-tag color="#f50">{{filterShareStatus(item.shareStatus)}}</a-tag>
						<a-tag color="#87d068">{{item.tags}}</a-tag>
						<br/>
						{{item.createUserName}}
						<br/>
						{{item.createTime}}
						<template #actions>
							<el-tooltip effect="dark" content="转到原文档" placement="top">
								<AimOutlined @click="turnToSource(item)"/>
							</el-tooltip>
							<el-tooltip effect="dark" content="预览模板" placement="top">
								<BorderOutlined @click="showPreview(item)"/>
							</el-tooltip>
							<el-tooltip effect="dark" content="使用模板" placement="top">
								<AlertOutlined @click="chooseTemplate(item)"/>
							</el-tooltip>
						</template>
					</a-card>
				</a-list-item>
			</template>
		</a-list>
		<a-pagination simple v-model:current="nowTemplateNum" :total="totalTemplate" style="float: right"
					  :page-size="8" :hide-on-single-page=true @change="pageUpDown"/>
		<template #footer/>
	</a-modal>
	<a-modal
			v-model:open="previewVisible"
			title="模板预览"
			width="100%"
			wrapClassName="full-modal"
			:destroyOnClose=true
			:closable=true>
		<el-row>
			<div ref="pageContentRef" class="wiki-page-content">
				<div v-html="pageShowDetail" class="markdown-body" v-if="editorType.value === 2" v-highlight></div>
				<div v-html="pageShowDetail" class="wang-editor-body" v-else></div>
			</div>
		</el-row>
		<template #footer/>
	</a-modal>
</template>

<script setup>
import {
	onBeforeUnmount,
	ref,
	onMounted,
	watch,
	defineProps,
	nextTick,
	defineEmits,
	defineExpose,
	computed
} from 'vue';
import {AlertOutlined, AimOutlined, BorderOutlined} from '@ant-design/icons-vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from '../../assets/api/page'
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/markdown/github-markdown.min.css'
import 'mavon-editor/dist/css/index.css'

let emit = defineEmits('doGetPageList');
let router = useRouter()
let nowTemplateNum = ref(1)
let totalTemplate = ref(0)
let exsit = ref(false)

let props = defineProps({
	pageId: Number,
	spaceId: Number
})
let templateNewForm = ref({
	pageId: 0,
	spaceId: 0,
	tagName: '',
	shareStatus: false
})
let newTemplateDialogVisible = ref(false);
const showTemplateCreate = (exsited) => {
	exsit.value = exsited
	templateNewForm.value = {
		pageId: props.pageId,
		spaceId: props.spaceId,
		tagName: '',
		shareStatus: false
	}
	newTemplateDialogVisible.value = true
}
const filterShareStatus = (data) => {
	if (data === 1) {
		return '公共模板'
	}
	return '个人模板'
}
const onNewTemplateSubmit = () => {
	pageApi.addTemplate(templateNewForm.value).then((json) => {
		ElMessage.success('模板记录成功')
		emit('doGetPageList', null)
	})
	newTemplateDialogVisible.value = false
}
const onNewTemplateCancel = () => {
	newTemplateDialogVisible.value = false
}

let templateChooseDialogVisible = ref(false)
let previewVisible = ref(false)
let aModalWaiting = ref(false)
let tags = ref([])
let filterTags = ref([])
let open = ref(false)
let name = ref('')
let templateList = ref()
let editorType = ref(1)


const showTemplateManage = () => {
	templateChooseDialogVisible.value = true
	filterTags.value = [{show: true, tagName: ''}]
	totalTemplate.value = 0
	nowTemplateNum.value = 1
	templateList.value = []
	filterByOpen()
}
const chooseTemplate = (item) => {
	pageApi.useTemplate({
		spaceId: props.spaceId,
		parentId: props.pageId,
		templateId: item.templateId
	}).then((json) => {
		templateChooseDialogVisible.value = false
		emit('doGetPageList', null)
		ElMessage.success('创建成功')
		router.push({
			path: '/page/edit',
			query: {parentId: props.pageId, pageId: json.data.id}
		})
	})
}
const turnToSource = (item) => {
	templateChooseDialogVisible.value = false
	router.push({
		path: '/page/show',
		query: {spaceId: item.spaceId, pageId: item.id}
	})
}
const pageUpDown = () => {
	templateList.value = []
	pageApi.getTemplate({
		name: name.value,
		open: open.value,
		tags: filterTags.value,
		pageNum: nowTemplateNum.value
	}).then((json) => {
		totalTemplate.value = json.total || 0
		templateList.value = json.data || []
	})
}
const simpleQryTemplate = () => {
	templateList.value = []
	pageApi.getTemplate({
		name: name.value,
		open: open.value,
		tags: filterTags.value,
	}).then((json) => {
		totalTemplate.value = json.total || 0
		templateList.value = json.data || []
		nowTemplateNum.value = 1
	})
}
const filterByOpen = () => {
	pageApi.getTags({open: open.value}).then((json) => {
		tags.value = json.data || []
		filterTags.value = json.data || []
		simpleQryTemplate()
	})
}
const filterByTags = () => {
	filterTags.value = tags.value.filter((item) => {
		return item.show
	})
	if (filterTags.value.length === 0) {
		filterTags.value = ['']
	}
	setTimeout(simpleQryTemplate(), 200)
}
const filterByName = () => {
	simpleQryTemplate()
}

let pageShowDetail = ref('')
let pageContentRef = ref(null)
const showPreview = (item) => {
	editorType.value = item.editorType
	if (item.editorType === 1) {
		pageShowDetail.value = item.content
	}
	if (item.editorType === 2) {
		pageShowDetail.value = mavonEditor.getMarkdownIt().render(item.content)
	}
	setTimeout(previewPageImage(), 500);
	previewVisible.value = true
}
const previewPageImage = () => {
	const imgArr = []
	if (pageContentRef.value !== undefined || pageContentRef.value !== '') {
		return
	}
	const imgSelector = pageContentRef.value.querySelectorAll('img')
	imgSelector.forEach((item, index) => {
		imgArr.push(item.src)
		item.onclick = () => {
			previewInitialIndex.value = index
			showImagePreviewList.value = imgArr
			showImagePreview.value = true
		}
	})
}
defineExpose({showTemplateCreate, showTemplateManage});
</script>

<style>
.template-manage .wiki-page-content {
	margin-top: 5px;
	height: calc(100vh);
	overflow: hidden;
	position: relative
}

.template-manage .markdown-body table {
	display: table;
}

.template-manage .wiki-page-content img {
	cursor: pointer;
	max-width: 100%;
}

.template-manage .wiki-page-content img:hover {
	box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.3);
}
</style>
<style lang="less">
.full-modal {
  .ant-modal {
	max-width: 100%;
	top: 0;
	padding-bottom: 0;
	margin: 0;
  }

  .ant-modal-content {
	display: flex;
	flex-direction: column;
	height: calc(100vh);
	overflow: auto;
	position: relative
  }

  .ant-modal-body {
	flex: 1;
  }
}
</style>
<style lang="scss">
.template-manage {
  height: 100%;
  overflow: hidden;

  .wiki-page-content {
	ol {
	  list-style: decimal;
	}

	ul {
	  list-style: disc;
	}
  }
}
</style>
