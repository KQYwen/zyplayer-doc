<template>
	<div class="left-aside-box">
		<div class="left-aside-top-box">
			<a-row type="flex" style="flex-flow: row nowrap;">
				<a-col flex="32px" style="margin-right: -1px;">
					<a-tooltip title="返回首页" placement="right" :mouseEnterDelay="0.5">
						<a-button @click="openHomePage" :icon="h(HomeOutlined)" class="home-page-btn"/>
					</a-tooltip>
				</a-col>
				<a-col flex="auto">
					<el-select v-model="storeSpace.chooseSpaceId" @change="spaceChangeEvents" filterable
					           placeholder="选择空间" class="choice-space-select">
						<el-option v-for="item in storeSpace.spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</a-col>
			</a-row>
			<el-autocomplete v-model="searchKeywords" v-if="!props.readOnly" :fetch-suggestions="doSearchByKeywords"
			                 @select="handleSearchKeywordsSelect" placeholder="在当前空间搜索"
			                 popper-class="search-autocomplete-popper" class="search-autocomplete">
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
				<a-flex align="center" justify="space-between">
					<div class="page-group-tag">
						<span class="label">空间目录</span>
					</div>
					<AddMenu/>
				</a-flex>
			</div>
		</div>
		<div class="wiki-page-tree-box">
			<el-tree ref="wikiPageTreeRef" :current-node-key="props.nowPageId" :data="storePage.pageList"
			         :default-expanded-keys="wikiPageExpandedKeys" :expand-on-click-node="true"
			         :props="defaultProps" :draggable="!props.readOnly"
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
							<span style="margin-left: 5px;">
								<el-tooltip :content="node.label" placement="top-start" :show-after="700">{{ node.label }}</el-tooltip>
							</span>
							<!--操作-->
							<div @click.stop class="page-action-box">
								<AddMenu :pageId="data.id"/>
								<a-dropdown @click="choosePageIdFunc(data.id)" :trigger="['click']" placement="bottom">
									<a-button :icon="h(EllipsisOutlined)" type="text" style="color: #888;"></a-button>
									<template #overlay>
										<a-menu>
											<a-menu-item @click="renamePage(node,data)">
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
		<div class="wiki-page-footer">
			<div style="flex: 1;">
				<a-tooltip title="空间设置" placement="top">
					<a-button @click="openSetting" :icon="h(SettingOutlined)" class="footer-btn">设置</a-button>
				</a-tooltip>
			</div>
			<el-divider direction="vertical" style="margin-top: 8px;"/>
			<div style="flex: 1;">
				<a-tooltip title="回收站" placement="top">
					<a-button @click="openRecycle" :icon="h(DeleteOutlined)" class="footer-btn">回收站</a-button>
				</a-tooltip>
			</div>
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
import { EllipsisOutlined, HomeOutlined, SettingOutlined, DeleteOutlined } from '@ant-design/icons-vue';
import {ref, defineProps, defineEmits, defineExpose, onMounted, h, watch} from 'vue';
import {useRouter, useRoute} from "vue-router";
import pageApi from '@/assets/api/page';
import {useStoreDisplay} from "@/store/wikiDisplay";
import {useStorePageData} from "@/store/pageData";
import AddMenu from "./AddMenu.vue";
import IconDocument from "@/components/base/IconDocument.vue";
import {ElMessageBox, ElMessage} from 'element-plus';
import {useStoreSpaceData} from "@/store/spaceData";
import MessagePrompt from "@/components/single/MessagePrompt";

let route = useRoute();
let router = useRouter();
let storePage = useStorePageData();
let storeDisplay = useStoreDisplay();
let storeSpace = useStoreSpaceData();

let searchKeywords = ref('');
let wikiPageExpandedKeys = ref([]);
let defaultProps = ref({children: 'children', label: 'name',});
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
watch(() => storeSpace.spaceInfo, () => {
	doGetPageList();
});
let openHomePage = (event) => {
	if (event.ctrlKey) {
		let routeUrl = router.resolve({path: `/wiki/space`});
		window.open(routeUrl.href, '_blank');
	} else {
		router.push({path: '/wiki/space'});
	}
}
let nowSpaceShow = ref({});
const loadSpaceList = () => {
	let spaceId = parseInt(route.params.spaceId);
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
		}
	});
}
let optionPageId = ref('');
const changeNodeOptionStatus = (param) => {
	optionPageId.value = param.id;
}
const assisSetCurrentKey = () => {
	// emit('setNowPageId', route.query.pageId, props.readOnly);
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
const renamePage = (node, data) => {
	MessagePrompt({
		title: '重命名',
		label: '文档名称',
		placeholder: '请输入文档名称',
		value: data.name,
		validator: (value) => {
			if (!value || !value.trim()) return '文档名称不能为空';
			if (value && value.length > 255) return '文档名称不能超过255个字符';
			return true;
		}
	}).then((value) => {
		if (value && value.trim()) {
			let name = value.trim();
			if (data.name !== name) {
				pageApi.renamePage({id: data.id, name: name}).then(json => {
					data.name = name;
					ElMessage.success('重命名成功');
				});
			}
		}
	}).catch(() => {
	});
}
const openMoveMenu = (onlyMove) => {
	// TODO
	// onlyMoveMode.value = onlyMove;
	// visibleMoveMenu.value = true;
	// moveToPageId.value = storePage.choosePageId
	// moveToSpaceId.value = storeSpace.chooseSpaceId
	// moveToWikiPageList.value = storePage.pageList
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
	let nowSpaceShowTemp = storeSpace.spaceList.find((item) => item.id === data);
	nowSpaceShow.value = nowSpaceShowTemp;
	storeSpace.spaceInfo = nowSpaceShowTemp;
	storeSpace.chooseSpaceId = data;
	storePage.choosePageId = 0;
	router.push({path: `/view/${data}`});
}
const doRename = (node, data) => {
	pageApi.renamePage({"id": data.id, "name": data.name}).then((json) => {
		doGetPageList();
		ElMessage.success('重命名成功');
	});
}
const doGetPageList = () => {
	storePage.pageList = [];
	storePage.favoritePageList = [];
	let spaceId = storeSpace.chooseSpaceId;
	pageApi.pageList({spaceId: spaceId}).then((json) => {
		storePage.pageList = json.data || [];
		if (route.path.startsWith('/view')) {
			if (route.path.startsWith('/view/setting') || route.path.startsWith('/view/recycle')) {
				return;
			}
			// 查看页面
			if (storePage.pageList.length <= 0) {
				router.push({path: `/view/${spaceId}`});
			} else {
				let routePageId = parseInt(route.params.pageId);
				let findPage = storePage.getPageById(routePageId);
				if (findPage) {
					router.replace({path: `/view/${spaceId}/${routePageId}`});
				} else {
					let firstPage = storePage.getFirstViewPage();
					if (firstPage) {
						router.replace({path: `/view/${spaceId}/${firstPage.id}`});
					}
				}
			}
		}
	});
}
const doSearchByKeywords = (queryString, callback) => {
	if (!queryString || !queryString.trim()) {
		callback([]);
		return;
	}
	pageApi.pageNews({spaceId: props.choiceSpace, keywords: queryString}).then((json) => {
		let spacePageNews = json.data || [];
		callback(spacePageNews);
	});
}
const handleSearchKeywordsSelect = (item) => {
	searchKeywords.value = '';
	router.push({path: `/view/${item.spaceId}/${item.pageId}`});
}
const searchByKeywords = () => {
	wikiPageTreeRef.value.filter(searchKeywords.value);
}
const handleNodeClick = (data) => {
	//console.log('点击节点：', data, props.nowPageId);
	if (data.editorType !== 0) {
		router.push({path: `/view/${data.spaceId}/${data.id}`});
	}
}
const handlePageDrop = (draggingNode, dropNode, dropType, ev) => {
	// console.log('tree drop: ', draggingNode.data, dropNode.data, dropType);
	// 'prev'、'inner'、'next'
	// before、after、inner
	var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId};
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
// 打开设置页面
let openSetting = () => {
	router.push({path: `/view/setting/${storeSpace.chooseSpaceId}`});
}
// 打开回收站页面
let openRecycle = () => {
	router.push({path: `/view/recycle/${storeSpace.chooseSpaceId}`});
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

		.home-page-btn {
			border-radius: 4px 0 0 4px;
			z-index: 0;

			&:hover, &:focus, &:active {
				z-index: 1;
			}
		}

		.choice-space-select {
			width: 100%;
			margin-bottom: 5px;

			.el-input__wrapper {
				border-radius: 0 4px 4px 0;
			}
		}

		.search-autocomplete {
			width: 100%;
			margin: 10px 0;
		}

		.page-group-tag {
			.label {
				font-size: 12px;
				padding: 4px 8px;
				color: #888;
				line-height: 32px;
				height: 32px;
				border-radius: 3px;
				box-sizing: border-box;
			}
		}
	}

	.wiki-page-tree-box {
		overflow-y: auto;
		overflow-x: hidden;
		padding-bottom: 10px;
		height: calc(100vh - 140px);

		.el-tree-node__content {
			height: 35px;
			position: relative;

			.page-tree-node {
				width: 100%;

				.label {
					.text {
						margin-left: 5px;
						max-width: calc(100% - 40px);
						display: inline-block;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
					}
				}

				.page-action-box {
					position: absolute;
					right: 0;
					top: 0;
					height: 35px;
					line-height: 35px;
					background: #fafafa;
					border-radius: 4px;
					display: none;

					button {
						padding: 5px 8px;
						height: 35px;
					}
				}
			}

			&:hover .page-action-box {
				display: block;
			}
		}
	}

	.wiki-page-footer {
		padding: 5px;
		display: flex;
		border-top: 1px solid #eee;

		.footer-btn {
			width: 100%;
			border: 0;
			color: #666;
			background: #fafafa;
			box-shadow: unset;

			&:focus {
				background: #fafafa;
				color: #666;
			}

			&:hover {
				background: #eaeaea;
			}
		}
	}
}

.search-autocomplete-popper {
	width: 600px !important;

	.search-option-item {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;

		.title {
			font-weight: bold;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}

		.content {
			font-size: 12px;
			color: #888;
		}
	}
}

.space-folder-box {
	position: relative;
}
</style>
