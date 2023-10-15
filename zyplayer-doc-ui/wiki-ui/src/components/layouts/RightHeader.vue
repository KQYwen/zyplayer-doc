<template>
	<el-row>
		<el-col :span="12">
			<div class="left-action-box">
				<div class="collapse-box">
					<el-button @click="turnLeftCollapse" v-if="storeDisplay.showMenu" text :icon="ElIconFold" class="fold-btn"></el-button>
					<el-button @click="turnLeftCollapse" v-else text :icon="ElIconExpand" class="fold-btn"></el-button>
				</div>
				<div v-if="storeDisplay.currentPage === 'view'" class="title-time-box">
					<div class="title">
						<span class="text">{{storePage.pageInfo.name || ''}}</span>
					</div>
					<div class="time">最近修改：{{storePage.pageInfo.updateTime || ''}}</div>
				</div>
			</div>
		</el-col>
		<el-col :span="12" style="text-align: right;">
			<div class="header-action-box">
				<template v-if="storeDisplay.currentPage === 'view'">
					<el-tooltip v-if="storePage.pageAuth.canEdit === 1" content="编辑文档">
						<el-button class="hover-button" @click="editWiki" text><IconParkEdit size="18"/></el-button>
					</el-tooltip>
					<el-tooltip content="文档沟通">
						<el-button class="hover-button" @click="showCommentWiki" text><IconParkCommunication size="18"/></el-button>
					</el-tooltip>
					<UserMessagePopover/>
					<a-dropdown trigger="click" placement="bottom" overlayClassName="header-action-more-dropdown">
					<span style="line-height: 60px;display:inline-block;margin: 0 8px;">
						<el-button :icon="ElIconMoreFilled" class="hover-button" text></el-button>
					</span>
						<template #overlay>
							<a-menu>
								<a-menu-item @click="editWikiAuth" v-if="storePage.pageAuth.canConfigAuth === 1"><el-icon><ElIconSCheck/></el-icon> 权限设置</a-menu-item>
								<a-menu-item @click="showOpenPage" v-if="storeSpace.spaceInfo.openDoc === 1"><el-icon><ElIconShare/></el-icon> 查看开放文档</a-menu-item>
								<a-menu-item @click="showMobileView" v-if="storeSpace.spaceInfo.openDoc === 1"><el-icon><ElIconMobilePhone/></el-icon> 手机端查看</a-menu-item>
								<a-menu-item @click="exportWord"><el-icon><ElIconDownload/></el-icon>导出为Word</a-menu-item>
								<a-menu-divider />
								<a-menu-item @click="deleteWikiPage" v-if="storePage.pageAuth.canDelete === 1" class="delete"><el-icon><ElIconDelete/></el-icon> 删除</a-menu-item>
							</a-menu>
						</template>
					</a-dropdown>
				</template>
				<a-dropdown trigger="click" placement="bottom" overlayClassName="header-action-user-dropdown">
					<span style="line-height: 60px;display:inline-block;">
						<el-button :icon="ElIconUserFilled" class="hover-button" text></el-button>
					</span>
					<template #overlay>
						<a-menu>
							<a-menu-item @click="showAbout">关于</a-menu-item>
							<a-menu-item @click="showConsole">控制台</a-menu-item>
							<a-menu-divider />
							<a-menu-item @click="userSignOut">退出登录</a-menu-item>
						</a-menu>
					</template>
				</a-dropdown>
			</div>
		</el-col>
	</el-row>
	<MobileQrScanDialog v-model:visible="mobileScanDialogVisible"/>
	<PageAuthDialog v-model:visible="pageAuthDialogVisible"/>
	<AboutDialog v-model:visible="aboutDialogVisible"/>
	<form method="post" ref="downloadFormRef" :action="downloadFormParam.url" target="_blank">
		<input type="hidden" :name="key" :value="val" v-for="(val, key) in downloadFormParam.param"/>
	</form>
</template>

<script setup>
import {
	Fold as ElIconFold,
	Expand as ElIconExpand,
	Delete as ElIconDelete,
	Stamp as ElIconSCheck,
	Share as ElIconShare,
	Iphone as ElIconMobilePhone,
	Download as ElIconDownload,
	MoreFilled as ElIconMoreFilled,
	Setting as ElIconSetting,
	UserFilled as ElIconUserFilled,
} from '@element-plus/icons-vue'
import {
	Star as IconParkStar,
	Edit as IconParkEdit,
	Communication as IconParkCommunication,
} from '@icon-park/vue-next'
import {toRefs, ref, reactive, onMounted, watch, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { ElMessageBox, ElMessage } from 'element-plus'
import { useStoreDisplay } from '@/store/wikiDisplay.js'
import { useStorePageData } from '@/store/pageData.js'
import { useStoreUserData } from '@/store/userData.js'
import pageApi from "@/assets/api/page";
import {useStoreSpaceData} from "@/store/spaceData";
import userApi from "@/assets/api/user";
import PageAuthDialog from '@/views/page/show/PageAuthDialog.vue'
import MobileQrScanDialog from '@/views/page/show/MobileQrScanDialog.vue'
import AboutDialog from "@/views/common/AboutDialog.vue"
import UserMessagePopover from "./UserMessagePopover.vue"

let router = useRouter();
let storePage = useStorePageData();
let storeDisplay = useStoreDisplay();
let storeUser = useStoreUserData();
let storeSpace = useStoreSpaceData();
const emit = defineEmits(['collapse']);

let turnLeftCollapse = () => {
	storeDisplay.showMenu = !storeDisplay.showMenu;
	setTimeout(() => {
		if (storeDisplay.showMenu) {
			storeDisplay.rightAsideWidth = 301;
		} else {
			storeDisplay.rightAsideWidth = 1;
		}
	}, 100);
};

// 清除控制台，和设置逻辑放远一点不容易被察觉
watch(() => storeUser.ts, () => {
	console.clear();
});

watch(() => storePage.favoritePageChange, (newVal) => {
	if (storePage.needVersion) {
		ElMessage.warning("不支持在指定版本后收藏页面");
		return;
	}
});

let favoritePage = (favorite) => {
	storePage.favoritePageChange = {
		id: storePage.pageInfo.id,
		favorite: favorite,
	};
};
const editWiki = () => {
	// 锁定页面并进入编辑页面
	storePage.pageIsUnlock = false;
	let param = {pageId: storePage.pageInfo.id};
	pageApi.pageLock(param).then(() => {
		router.push({path: '/page/edit', query: {pageId: storePage.pageInfo.id}});
	});
}
const showCommentWiki = () => {
	storeDisplay.commentShow = !storeDisplay.commentShow;
}
let pageAuthDialogVisible = ref(false);
const editWikiAuth = () => {
	pageAuthDialogVisible.value = true;
}
const showOpenPage = () => {
	if (storeSpace.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址');
	} else {
		let routeUrl = router.resolve({
			path: '/page/share/view',
			query: {pageId: storePage.pageInfo.id, space: storeSpace.spaceInfo.uuid}
		});
		window.open(routeUrl.href, '_blank');
	}
}
const deleteWikiPage = () => {
	ElMessageBox.confirm('确定要删除此页面及其所有子页面吗？', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		pageApi.pageDelete({pageId: storePage.pageInfo.id}).then(() => {
			pageApi.pageList({spaceId: storeSpace.chooseSpaceId}).then((json) => {
				storePage.wikiPageList = json.data || []
			}).then(()=>{
				router.push({path: '/home', query: {spaceId: storePage.pageInfo.spaceId}});
			})
		});
	}).catch((e) => {
		console.log(e)
	});
}
// 下载为Word
let downloadFormRef = ref();
let downloadFormParam = ref({url: 'zyplayer-doc-wiki/page/download', param: {}});
const exportWord = () => {
	downloadFormParam.value.param = {pageId: storePage.pageInfo.id};
	setTimeout(() => downloadFormRef.value.submit(), 0);
}
// 手机扫码
let mobileScanDialogVisible = ref(false);
const showMobileView = () => {
	if (storeSpace.spaceInfo.openDoc !== 1) {
		ElMessage.warning('该空间未开放，无法查看开放文档地址');
	} else {
		mobileScanDialogVisible.value = true;
	}
}
const userSignOut = () => {
	userApi.userLogout().then(() => {
		location.reload();
	});
}
let aboutDialogVisible = ref(false);
const showAbout = () => {
	aboutDialogVisible.value = true;
}
const showConsole = () => {
	window.open(import.meta.env.VITE_APP_BASE_API, '_blank')
}
</script>

<style lang="scss" scoped>
.left-action-box {
  display: flex;

  .collapse-box {
	line-height: 60px;

	.fold-btn {
	  font-size: 18px;
	  padding: 4px 10px;
	  color: #888 !important;
	}
  }

  .title-time-box {
	flex: 1;
	padding: 5px 0 6px 10px;
	overflow: hidden;

	.title {
	  overflow: hidden;
	  white-space: nowrap;
	  -o-text-overflow: ellipsis;
	  text-overflow: ellipsis;
	  line-height: 28px;

	  .text {
		vertical-align: middle;
	  }
	}

	.time {
	  font-size: 12px;
	  overflow: hidden;
	  white-space: nowrap;
	  -o-text-overflow: ellipsis;
	  text-overflow: ellipsis;
	}
  }

  .title-setting-box {
	.setting-title {
	  font-size: 18px;
	  line-height: 60px;
	  padding-left: 10px;
	  .text {
		display: inline-block;
	  }
	}
  }
}
</style>

<style lang="scss">
.left-action-box {
  .title-time-box {
	.title {
	  .i-icon {
		vertical-align: middle;

		svg {
		  vertical-align: unset;
		}
	  }
	}
  }
}

.title-setting-box {
  .setting-title {
	.tips-icon {
	  vertical-align: 0.1em;
	  margin-left: 6px;
	}
  }
}

.header-action-box {
  display: inline-block;
  line-height: 60px;

  .disabled-btn-box {
	margin-right: 10px;
  }

  .hover-button {
	border: 0;
	color: #888;

	.i-icon svg {
	  vertical-align: middle;
	}
  }

  .hover-button:focus {
	color: #888;
	background: #fff;
  }

  .hover-button:hover {
	color: #888;
	background: #eaeaea;
  }
}

.header-action-user-dropdown {
  width: 120px;
}

.header-action-more-dropdown {
  width: 140px;

  .delete {
	color: #f00;
  }

  .delete.disabled {
	cursor: not-allowed;
	color: var(--el-text-color-disabled);
  }

  .cant-hover {
	cursor: default;
  }

  .cant-hover:hover {
	background: #fff;
  }
}
</style>
