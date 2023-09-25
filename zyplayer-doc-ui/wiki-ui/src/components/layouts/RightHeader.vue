<template>
	<el-row class="right-header-box">
		<el-col :span="1">
			<el-button @click="turnLeftCollapse" v-if="storeDisplay.showMenu" text :icon="ElIconFold" class="fold-btn"></el-button>
			<el-button @click="turnLeftCollapse" v-else text :icon="ElIconExpand" class="fold-btn"></el-button>
		</el-col>
		<el-col :span="9" class="title-info-view">
			<div class="wikititle" ref="wikiTitleRef" v-if="storePage.pageInfo.name">
				<span class="create-user-time">
					{{storePage.pageInfo.name}}
				</span>
				<br/>
				<span v-if="storePage.pageInfo.updateUserName">
					{{ storePage.pageInfo.updateUserName }}
					<span class="split">于</span>
					{{ storePage.pageInfo.updateTime }}
					<span class="split">修改</span>
				</span>
				<span v-else class="create-user-time">
					{{ storePage.pageInfo.createUserName }}
					<span class="split">于</span>
					{{ storePage.pageInfo.createTime }}
					<span class="split">创建</span>
				</span>
			</div>
		</el-col>
		<el-col :span="12" style="text-align: right" class = "dropdown-menu">
			<el-tooltip v-if="storePage.pageAuth.canEdit === 1 && storePage.pageInfo.name && storePage.pageInfo.editorType !== 0" effect="dark" content="编辑文档"
						placement="top">
				<ElIconEdit @click="editWiki" type="primary" class="right-header-icon"></ElIconEdit>
			</el-tooltip>
			<el-tooltip v-if="storePage.pageAuth.canEdit === 1 && storePage.pageInfo.name && storePage.pageInfo.editorType !== 0" effect="dark" content="文档相关"
						placement="top">
				<ElIconChatLineRound @click="showCommentWiki" class="right-header-icon"></ElIconChatLineRound>
			</el-tooltip>
			<el-tooltip v-if="storePage.pageInfo.name" effect="dark" content="更多操作" placement="top">
				<el-dropdown trigger="click" class="action-btn more-dropdown" >
					<el-icon class="right-header-icon" style="margin-top: 18px;margin-left: 5px">
						<el-icon-more/>
					</el-icon>
					<template #dropdown>
						<el-dropdown-menu>
							<el-dropdown-item @click="editWikiAuth" v-if="storePage.pageAuth.canConfigAuth === 1 && storePage.pageInfo.editorType !== 0"
											  :icon="ElIconSCheck">权限设置
							</el-dropdown-item>
							<el-dropdown-item @click="showOpenPage" v-if="storeSpace.spaceInfo.openDoc === 1 && storePage.pageInfo.editorType !== 0"
											  :icon="ElIconShare">查看开放文档
							</el-dropdown-item>
							<el-dropdown-item @click="showMobileView" v-if="storeSpace.spaceInfo.openDoc === 1  && storePage.pageInfo.editorType !== 0"
											  :icon="ElIconMobilePhone">手机端查看
							</el-dropdown-item>
							<el-dropdown-item @click="exportWord" :icon="ElIconDownload" v-if="storePage.pageInfo.editorType !== 0">导出为Word文档</el-dropdown-item>
							<el-dropdown-item @click="deleteWikiPage" v-if="storePage.pageAuth.canDelete === 1"
											  :icon="ElIconDelete">删除
							</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</el-tooltip>
			<span class="header-right-user-name">{{userSelfInfo.userName}}</span>
			<el-popover v-model:visible="userMessagePopVisible" placement="bottom" trigger="click" width="600">
				<template v-slot:reference>
					<el-badge :is-dot="haveNotReadUserMessage" >
						<el-icon class="right-header-icon" >
							<el-icon-bell/>
						</el-icon>
					</el-badge>
				</template>
				<div style="margin-bottom: 10px">
					<span style="font-size: 14px; font-weight: bold">通知</span>
					<el-link v-if="haveNotReadUserMessage" :icon="ElIconCheck" style="float: right" type="primary"
							 @click="readAllUserMessage">本页标记已读
					</el-link>
				</div>
				<div class="header-user-message">
					<el-table :data="userMessageList" border max-height="500" size="small"
							  style="width: 100%; margin-bottom: 5px">
						<el-table-column label="操作人" prop="operatorUserName" width="100px"></el-table-column>
						<el-table-column label="操作时间" prop="creationTime" width="140px"></el-table-column>
						<el-table-column label="内容">
							<template v-slot="scope">
								{{ scope.row.msgContent }}
								<el-badge :is-dot="scope.row.msgStatus == 0"
										  style="line-height: 10px; padding-right: 5px">
									<el-link type="primary" @click="showUserMessage(scope.row)">查看</el-link>
								</el-badge>
							</template>
						</el-table-column>
					</el-table>
					<div class="page-info-box">
						<el-pagination
								:current-page="userMsgParam.pageNum"
								:page-size="userMsgParam.pageSize"
								:total="userMsgTotalCount"
								layout="prev, pager, next, total"
								@current-change="handleCurrentChange">
						</el-pagination>
					</div>
				</div>
			</el-popover>
			<el-dropdown trigger="click" @command="userSettingDropdown" style="vertical-align: bottom;">
				<el-icon class="right-header-icon">
					<el-icon-setting/>
				</el-icon>
				<template v-slot:dropdown>
					<el-dropdown-menu>
						<el-dropdown-item command="console">控制台</el-dropdown-item>
						<el-dropdown-item command="aboutDoc">关于</el-dropdown-item>
						<el-dropdown-item command="userSignOut" divided>退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</template>
			</el-dropdown>
		</el-col>
	</el-row>
	<MobileQrScanDialog v-model:visible="mobileScanDialogVisible"/>
	<PageAuthDialog v-model:visible="pageAuthDialogVisible"/>
	<about-dialog ref="aboutDialogRef"></about-dialog>
	<form method="post" ref="downloadFormRef" :action="downloadFormParam.url" target="_blank">
		<input type="hidden" :name="key" :value="val" v-for="(val, key) in downloadFormParam.param"/>
	</form>
</template>

<script setup>
	import {
		Setting as ElIconSetting,
		ArrowDown as ElIconArrowDown,
		View as ElIconView,
		Close as ElIconClose,
		Delete as ElIconDelete,
		Loading as ElIconLoading,
		CircleCheck as ElIconCircleCheck,
		CircleClose as ElIconCircleClose,
		ChatLineRound as ElIconChatLineRound,
		Upload as ElIconUpload,
		Edit as ElIconEdit,
		Timer as ElIconTime,
		More as ElIconMore,
		Stamp as ElIconSCheck,
		Bell as ElIconBell,
		Share as ElIconShare,
		Iphone as ElIconMobilePhone,
		Download as ElIconDownload,
		Fold as ElIconFold,
		Expand as ElIconExpand,
		Check as ElIconCheck,
		Back as ElIconBack,
	} from '@element-plus/icons-vue'
	import {toRefs, ref, reactive, onMounted, watch, computed} from 'vue';
	import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
	import {ElMessageBox, ElMessage, ElNotification} from 'element-plus';
	import pageApi from '@/assets/api/page'
	import PageAuthDialog from '../../views/page/show/PageAuthDialog.vue'
	import MobileQrScanDialog from '../../views/page/show/MobileQrScanDialog.vue'
	import {useStorePageData} from "@/store/pageData";
	import {useStoreDisplay} from "@/store/wikiDisplay";
	import {useStoreSpaceData} from "@/store/spaceData";
	import {useStoreUserData} from "@/store/userData";
	import userApi from "@/assets/api/user";
	import AboutDialog from "../../views/common/AboutDialog"

	let storePage = useStorePageData();
	let storeDisplay = useStoreDisplay();
	let router = useRouter();
	let storeSpace = useStoreSpaceData();
	let storeUser = useStoreUserData();

	let userSelfInfo = ref({});
	let userMessageList = ref([]);
	let haveNotReadUserMessage = ref(false);
	let userMessagePopVisible = ref(false);
	let userMsgTotalCount = ref(0);
	let userMsgParam = ref({sysType: 2, pageNum: 1, pageSize: 20,});
	let titleInput = ref('')

	onMounted(()=>{
		init()
	})

	const init=()=>{
		getSelfUserInfo()
		loadUserMessageList();
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
	const showCommentWiki = () => {
		storePage.commentShow = true;
		storePage.commentActiveTab = 'comment';
	}
	const editWiki = () => {
		// 锁定页面并进入编辑页面
		storePage.pageIsUnlock = false
		titleInput.value = storePage.pageInfo.name
		let param = {pageId: storePage.pageInfo.id};
		pageApi.pageLock(param).then(() => {
			router.push({path: '/page/edit', query: {pageId: storePage.pageInfo.id}});
		});
	}
	const turnLeftCollapse = () => {
		storeDisplay.showMenu = !storeDisplay.showMenu
		setTimeout(() => {
			if (storeDisplay.showMenu) {
				storeDisplay.rightAsideWidth = 301
			} else {
				storeDisplay.rightAsideWidth = 1
			}
		}, 100)
	}
	const loadUserMessageIfPopVisible = () => {
		if (!userMessagePopVisible.value) {
			loadUserMessageList()
		}
	}
	const loadUserMessageList = () => {
		userApi.getUserMessageList(userMsgParam.value).then((res) => {
			userMessageList.value = res.data || []
			userMsgTotalCount.value = res.total || 0
			haveNotReadUserMessage.value =
					userMessageList.value.filter((item) => item.msgStatus == 0).length > 0
		})
	}
	let aboutDialogRef = ref()
	const userSettingDropdown = (command) => {
		console.log('command:' + command)
		if (command == 'userSignOut') {
			userSignOut()
		} else if (command == 'aboutDoc') {
			aboutDialogRef.value.show()
		} else if (command == 'myInfo') {
			router.push({path: '/user/myInfo'})
		} else if (command == 'console') {
			window.open(import.meta.env.VITE_APP_BASE_API, '_blank')
		} else {
			ElMessage.warning('暂未开放')
		}
	}
	const userSignOut = () => {
		userApi.userLogout().then(() => {
			location.reload()
		})
	}

	const readAllUserMessage = () => {
		let msgIds = []
		userMessageList.value
				.filter((item) => item.msgStatus == 0)
				.forEach((item) => {
					msgIds.push(item.id)
				})
		if (msgIds.length <= 0) return
		userApi.readUserMessage({ids: msgIds.join(',')}).then(() => {
			ElMessage.success('标记成功')
			loadUserMessageList()
		})
	}




	const showUserMessage = (row) => {
		if (row.msgStatus == 0) {
			userApi.readUserMessage({ids: row.id}).then(() => {
				loadUserMessageList()
			})
		}
		if (row.msgType >= 2 && row.msgType <= 14) {
			router.push({path: '/page/show', query: {pageId: row.dataId}})
			userMessagePopVisible.value = false
		}
	}



	const handleCurrentChange = (val) => {
		userMsgParam.value.pageNum = val
		loadUserMessageList()
	}
	const getSelfUserInfo = () => {
		userApi.getSelfUserInfo().then((json) => {
			userSelfInfo.value = json.data
			storeUser.userInfo = json.data
		})
	}
	defineExpose({init})
</script>

<style scoped lang="scss">
	.page-action-box {
		padding: 30px 0;

		.page-create-info {
			font-size: 14px;
			color: #888;

			.split {
				padding: 0 4px;
			}
		}

		.page-action-list {
			text-align: right;
		}
	}
</style>

<style lang="scss">
	.right-header-box {
		.page-action-list {
			.header-right-user-name{
				margin-left: 15px;
				margin-right: 10px;
			}
			.dropdown-menu{
				display: flex;
				align-items: center;
				position: absolute;
				top: 10px;
				right:0;
			}
			text-align: right;

			.el-icon {
				margin-right: 4px;
			}

			.action-btn + .action-btn {
				margin-left: 15px;
			}

			.upload-page-file {
				display: inline;
				vertical-align: middle;
			}

			.more-dropdown {
				vertical-align: middle;
			}
		}
	}
</style>
	<style lang="scss">
	.right-header-box{
		padding: 5px 0;
		overflow: hidden;;
		white-space: nowrap;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		line-height: 28px;
		.fold-btn{
			margin-top: 7px;
			padding: 10px 0;
			color: #3d3a3a !important;
			font-size: 18px;
		}
		.title-info-view{
			font-size: 14px;
			color: #454343;
			.split{
				padding: 0 4px;
			}
		}
		.title-info-view-right{
			text-align: right;
			margin-left: 5px;
			font-size: 14px;
			color: #454343;
			.split{
				padding: 0 4px;
			}
		}
		.page-title-input{
			margin-top: 3px;
			margin-left: 5px;
			width: 100%;
			height: 45px;
		}
		.page-info-news{
			font-size: 14px;
			line-height: 20px;
			margin-top: 3px
		}
		.right-header-icon{
			cursor: pointer;
			width: 20px;
			margin: 15px 10px;
			vertical-align: bottom;
		}
	}
</style>
