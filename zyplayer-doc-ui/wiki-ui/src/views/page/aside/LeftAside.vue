<template>
	<div class="left-aside-box">
		<div class="left-aside-top-box">
			<el-select :model-value="storeSpace.chooseSpaceId" @change="spaceChangeEvents" filterable
			           placeholder="选择空间" style="width: 100%;margin-bottom: 5px;">
				<el-option-group label="" v-if="!props.readOnly">
					<el-option :key="-1" label="空间管理" :value="-1"></el-option>
				</el-option-group>
				<el-option-group label=""></el-option-group>
				<el-option v-for="item in storeSpace.spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-autocomplete v-model="searchKeywords" v-if="!props.readOnly" :fetch-suggestions="doSearchByKeywords"
			                 @select="handleSearchKeywordsSelect" popper-class="search-autocomplete"
			                 placeholder="在当前空间搜索" style="width: 100%; margin: 10px 0">
				<template v-slot="{ item }">
					<div class="search-option-item">
						<div class="title">
							<span v-html="item.pageTitle || '-'"></span>
						</div>
						<span class="content" v-html="item.previewContent || '-'"></span>
					</div>
				</template>
			</el-autocomplete>
			<div class="space-folder-box" v-if="!props.readOnly">
				<el-row justify="space-between">
					<el-col :span="12">
						<el-tooltip style="margin: 4px" effect="dark" :content="descriptorForTree" placement="top">
							<span style="color:#888;font-size: 12px;cursor: pointer;line-height: 32px;" @click="changeDropWownStatus">空间目录</span>
						</el-tooltip>
					</el-col>
					<el-col :span="12" style="text-align: right;">
						<AddMenu/>
					</el-col>
				</el-row>
			</div>
		</div>
		<div class="wiki-page-tree-box">
			<el-tree ref="wikiPageTreeRef" :current-node-key="props.nowPageId" :data="storePage.wikiPageList"
			         :default-expanded-keys="wikiPageExpandedKeys" :expand-on-click-node="true" :class="explanClass"
			         :filter-node-method="filterPageNode" :props="defaultProps" :draggable="!props.readOnly"
			         @node-click="handleNodeClick" @node-drop="handlePageDrop" node-key="id" highlight-current
			         style="background-color: #fafafa;">
				<template v-slot="{ node, data }">
					<div class="page-tree-node" @mouseover="changeNodeOptionStatus(data) ">
						<div class="node-content">
							<!--图标-->
							<span class="left-icon">
								<template v-if="data.editorType === 0">
									<FolderOpen v-if="node.expanded" class="el-icon"/>
									<FolderClose v-else class="el-icon"/>
								</template>
								<template v-else-if="data.editorType === 1"><IconParkWord class="el-icon"/></template>
								<template v-else-if="data.editorType === 2"><IconDocument class="el-icon"/></template>
							</span>
							<!--标题-->
							<el-tooltip v-if="data.shareStatus > 0" :content="data.tags" placement="top-start" :show-after="500">
								<a-tag color="warning" style="margin-inline-end: 4px;padding-inline: 4px;"> {{data.shareStatus === 1 ? '公共模板' : '个人模板'}}</a-tag>
							</el-tooltip>
							<a-input v-if="data.renaming" v-model:value="data.name" @blur="doRename(node,data)" @click.stop
							         class="rename-input" placeholder="请输入文档名称"/>
							<span v-else style="vertical-align: middle;margin-left: 5px;">
								<el-tooltip :content="node.label" placement="top-start" :show-after="700">{{ node.label }}</el-tooltip>
							</span>
							<!--操作-->
							<div class="page-action-box" :class="data.renaming?'renaming':''" @click.stop>
								<AddMenu :choiceSpace="storeSpace.chooseSpaceId" :choosePageId="storePage.optionPageId"
								         :nowPageId="storePage.choosePageId" :dataId="data.id"
								         @createWikiByTemplate="createWikiByTemplate"
								         @choosePageIdFunc="choosePageIdFunc"/>
								<a-dropdown :trigger="['click']" @click="choosePageIdFunc(data.id)">
									<el-button :icon="MoreFilled" text class="page-action-dropdown-btn"></el-button>
									<template #overlay>
										<a-menu>
											<a-menu-item @click="rename(node,data)">
												<IconParkEditTwo class="el-icon"/> 重命名
											</a-menu-item>
											<a-sub-menu title="移动文档">
												<template #icon>
													<IconParkIntersection/>
												</template>
												<a-menu-item @click="openMoveMenu(false)">
													<IconParkCopy/> 复制文档
												</a-menu-item>
												<a-menu-item @click="openMoveMenu(true)">
													<IconParkCuttingOne/> 迁移文档
												</a-menu-item>
											</a-sub-menu>
											<a-menu-item v-if="data.editorType !== 0" @click="openTemplateCreate(data.shareStatus !== undefined)">
												<IconParkPageTemplate/> 设为模板
											</a-menu-item>
											<a-menu-item @click="deleteWikiPage(data)">
												<IconParkDelete class="el-icon"/> 删除
											</a-menu-item>
										</a-menu>
									</template>
								</a-dropdown>
							</div>
						</div>
					</div>
				</template>
			</el-tree>
		</div>
	</div>
</template>

<script setup>
import {
	FolderClose,
	FolderOpen,
	Word as IconParkWord,
	Copy as IconParkCopy,
	CuttingOne as IconParkCuttingOne,
	Intersection as IconParkIntersection,
	Delete as IconParkDelete,
	EditTwo as IconParkEditTwo,
	PageTemplate as IconParkPageTemplate,
} from '@icon-park/vue-next'
import {MoreFilled} from '@element-plus/icons-vue';
import {ref, defineProps, defineEmits, defineExpose, onMounted, watch} from 'vue';
import {useRouter, useRoute} from "vue-router";
import pageApi from '@/assets/api/page';
import {useStoreDisplay} from "@/store/wikiDisplay";
import {useStorePageData} from "@/store/pageData";
import AddMenu from "./AddMenu.vue";
import IconDocument from "@/components/base/IconDocument.vue";
import {ElMessageBox, ElMessage} from 'element-plus';
import {useStoreSpaceData} from "@/store/spaceData";
import Navigation from "@/views/page/show/Navigation.vue";
import PageZan from "@/views/page/show/PageZan.vue";

let route = useRoute();
let router = useRouter();
let storePage = useStorePageData();
let storeDisplay = useStoreDisplay();
let storeSpace = useStoreSpaceData();

let emit = defineEmits(['spaceChangeEvents', 'setNowPageId']);
let searchKeywords = ref('');
let descriptorForTree = ref("点击收起目录");
let explan = ref(false);
let explanClass = ref("el-tree");
let wikiPageExpandedKeys = ref([]);
let defaultProps = ref({children: 'children', label: 'name',});
let wikiPage = ref({});
let wikiPageTreeRef = ref();
let props = defineProps({
	wikiPageList: Array,
	spaceOptions: Array,
	nowPageId: Number,
	choiceSpace: Number,
	readOnly: Boolean
});
onMounted(() => {
	loadSpaceList();
});
watch(() => storePage.eventPageListUpdate, () => {
	loadSpaceList();
});
let nowSpaceShow = ref({});
const loadSpaceList = (spaceId) => {
	pageApi.spaceList({}).then((json) => {
		storeSpace.spaceList = json.data || [];
		let spaceOptionsNew = [];
		storeSpace.spaceList.forEach((item) => spaceOptionsNew.push({label: item.name, value: item.id}));
		storeSpace.spaceOptions = spaceOptionsNew;
		if (storeSpace.spaceList.length > 0) {
			let nowSpaceId = spaceId;
			let nowSpaceShowTemp = storeSpace.spaceList.find((item) => item.id === spaceId);
			if (!nowSpaceShowTemp) {
				nowSpaceShowTemp = storeSpace.spaceList[0];
				nowSpaceId = nowSpaceShowTemp.id;
			}
			nowSpaceShow.value = nowSpaceShowTemp;
			storeSpace.spaceInfo = nowSpaceShowTemp;
			storeSpace.chooseSpaceId = nowSpaceId;
			storePage.choosePageId = 0;
			doGetPageList();
			// TODO 在首页时跳转
			try {
				if (route.path === '/home') {
					router.push({path: '/home', query: {spaceId: nowSpaceId}});
				}
			} catch (e) {
				console.log(e);
			}
		}
	});
}
let optionPageId = ref('');
const changeNodeOptionStatus = (param) => {
	optionPageId.value = param.id;
}
const assisSetCurrentKey = () => {
	emit('setNowPageId', route.query.pageId, props.readOnly);
	if (props.nowPageId) {
		wikiPageTreeRef.value.setCurrentKey(nowPageId.value);
	}
}

const changeWikiPageExpandedKeys = (pageId) => {
	// 展开没有触发子节点的加载，如果去加载子节点有还找不到当前的node，暂不展开
	// wikiPageExpandedKeys.value= [pageId];
}
const createWikiByTemplate = () => {
	// TODO
	// templateManageRef.value.showTemplateManage();
}
const choosePageIdFunc = (id) => {
	storePage.optionPageId = id;
}
const rename = (node, data) => {
	data.renaming = true;
}
const openMoveMenu = (onlyMove) => {
	// TODO
	// onlyMoveMode.value = onlyMove;
	// visibleMoveMenu.value = true;
	// moveToPageId.value = storePage.choosePageId
	// moveToSpaceId.value = storeSpace.chooseSpaceId
	// moveToWikiPageList.value = storePage.wikiPageList
}
const openTemplateCreate = (exsit) => {
	// TODO
	// templateManageRef.value.showTemplateCreate(exsit);
}
const deleteWikiPage = (data) => {
	let msg = '确定要删除此页面及其所有子页面吗？'
	ElMessageBox.confirm(msg, '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		let param = {pageId: data.id};
		pageApi.pageDelete(param).then(() => {
			ElMessage.success('已删除');
			doGetPageList();
		});
	}).catch(() => {
	});
}
const spaceChangeEvents = (data) => {
	emit('spaceChangeEvents', data, props.readOnly);
}
const doRename = (node, data) => {
	pageApi.renamePage({"id": data.id, "name": data.name});
		.then((json) => {
			doGetPageList();
			ElMessage.success('重命名成功');
			data.renaming = false;
		});
}
const doGetPageList = () => {
	let param = {spaceId: storeSpace.chooseSpaceId}
	pageApi.pageList(param).then((json) => {
		storePage.wikiPageList = json.data || [];
	});
}
const doSearchByKeywords = (queryString, callback) => {
	if (!queryString || !queryString.trim()) {
		callback([]);
		return;
	}
	pageApi
		.pageNews({spaceId: props.choiceSpace, keywords: queryString});
		.then((json) => {
			let spacePageNews = json.data || [];
			callback(spacePageNews);
		});
}
const handleSearchKeywordsSelect = (item) => {
	searchKeywords.value = '';
	router.push({path: '/page/show', query: {pageId: item.pageId}});
}

const changeDropWownStatus = () => {
	if (explan.value) {
		explanClass.value = "el-tree"
		descriptorForTree.value = "点击收起目录"
		explan.value = false;
	} else {
		explanClass.value = "hidTree"
		descriptorForTree.value = "点击展开目录"
		explan.value = true;
	}
}

const filterPageNode = (value, data) => {
	if (!value || !data.name) return true;
	// issues:I2CG72 忽略大小写
	let name = data.name.toLowerCase();
	return name.indexOf(value.toLowerCase()) !== -1;
}
const searchByKeywords = () => {
	wikiPageTreeRef.value.filter(searchKeywords.value);
}
const handleNodeClick = (data) => {
	//console.log('点击节点：', data, props.nowPageId);
	storeDisplay.showHeader = true;
	emit('setNowPageId', data.id, props.readOnly);
	if (props.readOnly) {
		return;
	}
	if (data.editorType !== 0) {
		router.push({path: '/page/show', query: {pageId: data.id}});
	}
}
const handlePageDrop = (draggingNode, dropNode, dropType, ev) => {
	console.log('tree drop: ', draggingNode.data, dropNode.data, dropType);
	// 'prev'、'inner'、'next'
	// before、after、inner
	var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId}
	if (dropType == 'inner') {
		param.parentId = dropNode.data.id;
	} else if (dropType == 'before') {
		param.beforeSeq = dropNode.data.seqNo;
	} else if (dropType == 'after') {
		param.afterSeq = dropNode.data.seqNo;
	}
	pageApi.pageChangeParent(param).then((res) => {
		doGetPageList();
	});
}
defineExpose({searchByKeywords});
</script>

<style lang="scss">
.left-aside-box {
	height: 100%;
	box-sizing: border-box;
	background: #fafafa;
	display: flex;
	flex-direction: column;

	.left-aside-top-box {
		padding: 10px;
	}

	.wiki-page-tree-box {
		overflow-y: auto;
		overflow-x: hidden;
		padding-bottom: 10px;
	}
}
</style>
