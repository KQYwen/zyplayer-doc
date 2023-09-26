<template>
	<LeftSidebarCli
			:readOnly=false
			:wikiPageList="storePage.wikiPageList"
			:spaceOptions="storeSpace.spaceOptions"
			:nowPageId="storePage.choosePageId"
			:choiceSpace="storeSpace.chooseSpaceId"
			@setNowPageId="setNowPageId"
			@doGetPageList="doGetPageList"
			@spaceChangeEvents="spaceChangeEvents">
		<template v-slot:addMenuDir>
			<AddMenu
					:choiceSpace="storeSpace.chooseSpaceId"
					:choosePageId="storePage.optionPageId"
					:nowPageId="storePage.choosePageId"
					:funcId="0"
					@createWikiByTemplate="createWikiByTemplate"
					@choosePageIdFunc="choosePageIdFunc"
					@doGetPageList="doGetPageList"
			/>
		</template>
		<template v-slot:addMenuNode="{node,data}">
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
					<el-tooltip :content="data.tags" placement="top-start" :show-after="500" v-if="data.shareStatus !== undefined">
						<a-tag color="warning" style="margin-inline-end: 4px;padding-inline: 4px;">{{filterShareStatus(data.shareStatus)}}</a-tag>
					</el-tooltip>
					<a-input v-if="data.renaming" v-model:value="data.name" class="rename-input" placeholder="请输入文档名称" @blur="doRename(node,data)" @click.stop/>
					<span v-else style="vertical-align: middle;margin-right: 5px">
						<el-tooltip :content="node.label" placement="top-start" :show-after="700">{{ node.label }}</el-tooltip>
					</span>
					<!--操作-->
					<div class="page-action-box" :class="data.renaming?'renaming':''" @click.stop>
						<AddMenu
								:choiceSpace="storeSpace.chooseSpaceId"
								:choosePageId="storePage.optionPageId"
								:nowPageId="storePage.choosePageId"
								:funcId="data.id"
								@createWikiByTemplate="createWikiByTemplate"
								@choosePageIdFunc="choosePageIdFunc"
								@doGetPageList="doGetPageList"
						/>
						<a-dropdown :trigger="['click']" @click="choosePageIdFunc(data.id)">
							<el-button :icon="MoreFilled" text class="page-action-dropdown-btn"></el-button>
							<template #overlay>
								<a-menu>
									<a-menu-item @click="rename(node,data)">
										<IconParkEditTwo class="el-icon"/> 重命名
									</a-menu-item>
									<a-sub-menu title="移动文档">
										<template #icon><IconParkIntersection/></template>
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
									<a-menu-item @click="deleteWikiPage(data.shareStatus)">
										<IconParkDelete class="el-icon"/> 删除
									</a-menu-item>
								</a-menu>
							</template>
						</a-dropdown>
					</div>
				</div>
			</div>
		</template>
	</LeftSidebarCli>
	<TemplateManage ref="templateManageRef" :pageId="storePage.optionPageId" :spaceId="storeSpace.chooseSpaceId" @doGetPageList="doGetPageList"/>
	<create-space ref="createSpaceRef" @success="loadSpaceList"/>
	<a-modal v-model:open="visibleMoveMenu"
	         title="选择"
	         @ok="handleOk"
	         @cancel="handleCancel"
	         ok-text="确认"
	         cancel-text="取消"
	         :confirm-loading="aModalWaiting"
	         :destroyOnClose="true"
	         :closable="false">
		<LeftSidebarCli :readOnly="true"
		                :wikiPageList="moveToWikiPageList"
		                :spaceOptions="storeSpace.spaceOptions"
		                :nowPageId="moveToPageId"
		                :choiceSpace="moveToSpaceId"
		                @setNowPageId="setNowPageId"
		                @doGetPageList="doGetPageList"
		                @spaceChangeEvents="spaceChangeEvents"/>
	</a-modal>
</template>

<script setup>
import {
	MoreFilled
} from '@element-plus/icons-vue'
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
import {ref, onMounted,} from 'vue';
import {useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import pageApi from '../../assets/api/page'
import CreateSpace from '../space/CreateSpace.vue'
import TemplateManage from '../template/TemplateManage.vue'
import AddMenu from '../leftSideBar/AddMenu.vue'
import IconDocument from '@/components/base/IconDocument.vue'
import LeftSidebarCli from '../leftSideBar/LeftSidebarCli.vue'
import {useStoreDisplay} from '@/store/wikiDisplay.js'
import {useStorePageData} from "@/store/pageData";
import {DownOutlined, BuildOutlined, BlockOutlined} from '@ant-design/icons-vue';
import {useStoreSpaceData} from "@/store/spaceData";

let route = useRoute();
let router = useRouter();
let storePage = useStorePageData();
let storeDisplay = useStoreDisplay();
let storeSpace = useStoreSpaceData();

// 空间搜索相关
let nowSpaceShow = ref({});
let moveToPageId = ref(0);
let moveToSpaceId = ref(0);
let moveToWikiPageList = ref([]);

// 页面展示相关
let wikiPage = ref({});
let wikiPageExpandedKeys = ref([]);
let rightAsideWidth = ref(300);
let optionPageId = ref('');
let visibleMoveMenu = ref(false);
let onlyMoveMode = ref(false);
let aModalWaiting = ref(false);
let templateManageRef = ref(null)

onMounted(() => {
	init()
})
const init = () => {
	loadSpaceList()
}

const openTemplateCreate = (exsit) => {
	templateManageRef.value.showTemplateCreate(exsit)
}

const createWikiByTemplate = () => {
	templateManageRef.value.showTemplateManage()

}

const filterShareStatus = (data) => {
	if (data === 1) {
		return '公共模板'
	}
	return '个人模板'
}

const openMoveMenu = (onlyMove) => {
	onlyMoveMode.value = onlyMove
	visibleMoveMenu.value = true
	moveToPageId.value = storePage.choosePageId
	moveToSpaceId.value = storeSpace.chooseSpaceId
	moveToWikiPageList.value = storePage.wikiPageList
}
const handleOk = (onlyMove) => {
	aModalWaiting.value = true
	if (onlyMoveMode.value) {
		pageApi.movePage({
				"id": storePage.optionPageId,
				"spaceId": storeSpace.chooseSpaceId,
				"moveToPageId": moveToPageId.value,
				"moveToSpaceId": moveToSpaceId.value
			})
			.then((json) => {
				doGetPageList(null)
				ElMessage.success('迁移成功')
				handleCancel()
				aModalWaiting.value = false
			}).catch((e) => {
			aModalWaiting.value = false
		})
		return
	}
	pageApi.copyPage({
			"id": storePage.optionPageId,
			"spaceId": storeSpace.chooseSpaceId,
			"moveToPageId": moveToPageId.value,
			"moveToSpaceId": moveToSpaceId.value
		})
		.then((json) => {
			doGetPageList(null)
			ElMessage.success('复制成功')
			handleCancel()
			aModalWaiting.value = false
		}).catch((e) => {
		aModalWaiting.value = false
	})
	return
}
const handleCancel = () => {
	visibleMoveMenu.value = false
	moveToPageId.value = 0
	moveToSpaceId.value = 0
	moveToWikiPageList.value = []
}


const deleteWikiPage = (share) => {
	let msg = '确定要删除此页面及其所有子页面吗？'
	if (share !== undefined) {
		msg = '选中的页面是：' + filterShareStatus(share) + '删除后无法使用此模板！ 确定要删除此页面及其所有子页面吗？'
	}
	ElMessageBox.confirm(msg, '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		let param = {pageId: optionPageId.value};
		pageApi.pageDelete(param).then(() => {
			ElMessage.success('已删除')
			doGetPageList(null)
		});
	}).catch(() => {
	});
}

const choosePageIdFunc = (id) => {
	storePage.optionPageId = id
}

const setNowPageId = (id, readOnly) => {
	if (readOnly) {
		moveToPageId.value = id
		return
	}
	storePage.choosePageId = id
}

const rename = (node, data) => {
	data.renaming = true
}
const doRename = (node, data) => {
	pageApi.renamePage({"id": data.id, "name": data.name})
		.then((json) => {
			doGetPageList(null)
			ElMessage.success('重命名成功')
			data.renaming = false
		})

}

const changeNodeOptionStatus = (param) => {
	optionPageId.value = param.id
}

let createSpaceRef = ref();
const spaceChangeEvents = (data, readonly) => {
	storePage.pageInfo = {}
	if (readonly) {
		moveToSpaceId.value = data
		setNowPageId(0, readonly)
		let param = {spaceId: moveToSpaceId.value}
		pageApi.pageList(param).then((json) => {
			moveToWikiPageList.value = json.data || []
		})
		return
	}
	if (data === 0) {
		// 新建空间
		createSpaceRef.value.show();
	} else if (data === -1) {
		// 管理空间
		router.push({path: '/space/manage'});
	} else {
		storePage.choosePageId = 0;
		storeSpace.chooseSpaceId = Number(data);
		nowSpaceShow.value = storeSpace.spaceList.find((item) => item.id === data);
		storeSpace.spaceInfo = nowSpaceShow.value;
		doGetPageList(null);
		router.push({path: '/home', query: {spaceId: data}});
	}
}
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
			doGetPageList(null);
			// TODO 在首页时跳转
			try {
				if (route.path === '/home') {
					router.push({path: '/home', query: {spaceId: nowSpaceId}});
				}
			} catch (e) {
				console.log(e);
			}
		}
	})
}

const doGetPageList = (parentId, node) => {
	let param = {spaceId: storeSpace.chooseSpaceId}
	pageApi.pageList(param).then((json) => {
		storePage.wikiPageList = json.data || []
	})
}
defineExpose({init})
</script>

<style lang="scss">
.page-tree-node {
  .node-content {
	width: calc(100% - 30px);
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;

    .left-icon {
      margin-right: 6px;
    }
  }
}
</style>

