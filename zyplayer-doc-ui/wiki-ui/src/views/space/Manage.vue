<template>
	<div style="min-height: 100%;" class="space-manage-vue">
		<div style="max-width: 1200px;margin: 0 auto;background: #fff;padding: 20px;min-height: 100%;box-sizing: border-box;">
			<div style="text-align: right; margin-bottom: 10px">
		        <span style="float: left; line-height: 40px">
		          仅展示我收藏的空间：
		          <el-switch v-model="userSetting.wiki_only_show_favorite" inactive-value="0" active-value="1" @change="wikiOnlyShowFavoriteChange"></el-switch>
		          <el-tooltip class="item" effect="dark" content="控制左上角空间下拉列表仅展示我收藏的空间" placement="top-start">
		            <el-icon style="vertical-align: middle; margin-left: 10px; color: #999"><el-icon-warning-outline/></el-icon>
		          </el-tooltip>
		        </span>
				<el-button @click="loadSpaceList" :icon="ElIconRefresh" :loading="spaceListLoading">刷新</el-button>
				<el-button type="primary" @click="showCreateSpace" :icon="ElIconPlus">创建空间</el-button>
			</div>
			<el-table :data="spaceList" border style="width: 100%; margin-bottom: 5px">
				<el-table-column prop="id" label="ID" width="60"></el-table-column>
				<el-table-column prop="name" label="名字"></el-table-column>
				<el-table-column prop="spaceExplain" label="说明"></el-table-column>
				<el-table-column label="开放地址">
					<template v-slot="scope">
						<el-link @click="showOpenSpace(scope.row.uuid)" v-if="scope.row.openDoc == 1">{{ scope.row.name }}</el-link>
						<span v-else>暂未开放</span>
					</template>
				</el-table-column>
				<el-table-column prop="createUserName" label="创建人"></el-table-column>
				<el-table-column prop="createTime" label="创建时间"></el-table-column>
				<el-table-column prop="favorite" label="收藏" width="60">
					<template v-slot="scope">
						<el-icon v-if="scope.row.favorite === 1" @click="updateSpaceFavorite(scope.row)"  class="favorite-icon el-icon-star-on">
							<el-icon-star-on/>
						</el-icon>
						<el-icon v-else @click="updateSpaceFavorite(scope.row)"  class="favorite-icon el-icon-star-off">
							<el-icon-star-off/>
						</el-icon>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="220">
					<template v-slot="scope">
						<template v-if="userSelfInfo.id == scope.row.createUserId">
							<el-button size="small" type="primary" @click="editSpaceInfo(scope.row)">编辑</el-button>
							<el-button size="small" type="warning" @click="editSpaceAuth(scope.row)">授权</el-button>
							<el-button size="small" type="danger" @click="deleteSpaceInfo(scope.row)">删除</el-button>
						</template>
					</template>
				</el-table-column>
			</el-table>
			<div class="page-info-box">
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:page-sizes="[10, 30, 50]"
						:page-size="10"
						:current-page="searchParam.pageNum"
						layout="prev, pager, next, jumper, sizes, total"
						:total="totalCount">
				</el-pagination>
			</div>
		</div>
		<!--分组权限弹窗-->
		<el-dialog title="权限管理" v-model="spaceAuthDialogVisible" width="900px" :close-on-click-modal="false">
			<el-row>
				<el-select v-model="spaceAuthNewGroupId" filterable placeholder="请选择分组" style="width: 750px; margin-right: 10px">
					<el-option v-for="item in searchGroupList" :key="item.id" :label="searchGroupMap[item.id]" :value="item.id"></el-option>
				</el-select>
				<el-button @click="addSpaceAuthUserGroup">添加</el-button>
			</el-row>
			<el-table :data="spaceAuthGroupList" border style="width: 100%; margin: 10px 0">
				<el-table-column prop="groupId" label="分组名" width="150">
					<template v-slot="scope">{{searchGroupMap[scope.row.groupId]}}</template>
				</el-table-column>
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
						<el-button size="small" type="danger" plain @click="deleteGroupSpaceAuth(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="text-align: right">
				<el-button @click="manageUserGroup">分组管理</el-button>
				<el-button type="primary" @click="saveGroupSpaceAuth">保存配置</el-button>
			</div>
		</el-dialog>
		<create-space ref="createSpaceRef" @success="loadSpaceList"></create-space>
	</div>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRouter, useRoute} from "vue-router";
import {ElMessageBox, ElMessage} from 'element-plus'
import {
	Warning as ElIconWarningOutline,
	StarFilled as ElIconStarOn,
	Star as ElIconStarOff,
	Refresh as ElIconRefresh,
	Plus as ElIconPlus,
} from '@element-plus/icons-vue'
import pageApi from '../../assets/api/page'
import userApi from '../../assets/api/user'
import CreateSpace from '../../components/space/CreateSpace'
import {useStoreSpaceData}from '@/store/spaceData'
import {useStorePageData}from '@/store/pageData'
import {useStoreDisplay} from "@/store/wikiDisplay";

let spaceListLoading = ref(false);
let spaceOptions = ref([]);
let spaceList = ref([]);
let choiceSpace = ref('');
let nowSpaceShow = ref({});
let newSpaceForm = ref({id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1,});
let userSelfInfo = ref({});
// 空间授权
let editSpaceId = ref('');
let spaceAuthDialogVisible = ref(false);
let spaceAuthNewGroupId = ref('');
let searchGroupList = ref([]);
let searchGroupMap = ref({});
let spaceAuthGroupList = ref([]);
// 设置
let userSetting = ref({wiki_only_show_favorite: 0,});

let route = useRoute();
let router = useRouter();
let storeDisplay = useStoreDisplay();
let storePage = useStorePageData();
let storeSpace = useStoreSpaceData();

onMounted(() => {
	storeDisplay.currentPage = 'space';
	loadSpaceList()
	getSelfUserInfo()
	getSpaceSettingList()
});

const showOpenSpace = (space) => {
	let routeUrl = router.resolve({
		path: '/page/share/home',
		query: {space: space}
	})
	window.open(routeUrl.href, '_blank')
}
let createSpaceRef = ref();
const showCreateSpace = () => {
	createSpaceRef.value.show()
}
const editSpaceInfo = (row) => {
	createSpaceRef.value.show(row.id)
}
const addSpaceAuthUserGroup = () => {
	if (!spaceAuthNewGroupId.value) {
		ElMessage.warning('请先选择分组')
		return
	}
	if (
		!!spaceAuthGroupList.value.find(
			(item) => item.groupId == spaceAuthNewGroupId.value
		)
	) {
		spaceAuthNewGroupId.value = ''
		return
	}
	spaceAuthGroupList.value.push({
		groupId: spaceAuthNewGroupId.value,
		editPage: 0,
		commentPage: 0,
		deletePage: 0,
		pageFileUpload: 0,
		pageFileDelete: 0,
		pageAuthManage: 0,
	})
	spaceAuthNewGroupId.value = ''
}
const updateSpaceFavorite = (row) => {
	let delFlag = row.favorite == 1 ? 1 : 0
	pageApi.spaceFavoriteUpdate({spaceId: row.id, delFlag: delFlag}).then((json) => {
		row.favorite = row.favorite == 1 ? 0 : 1
	})
}
const saveGroupSpaceAuth = () => {
	let param = {
		spaceId: editSpaceId.value,
		authList: JSON.stringify(spaceAuthGroupList.value),
	}
	pageApi.spaceAuthAssign(param).then((json) => {
		ElMessage.success('授权成功！')
	})
}
const manageUserGroup = () => {
	let manageUrl = location.href.substring(0, location.href.indexOf('/doc-wiki')) + '#/console/userGroupList'
	window.open(manageUrl, '_blank')
}
const deleteGroupSpaceAuth = (row) => {
	spaceAuthGroupList.value = spaceAuthGroupList.value.filter(
		(item) => item.groupId != row.groupId
	)
}
const editSpaceAuth = (row) => {
	editSpaceId.value = row.id
	spaceAuthNewGroupId.value = ''
	spaceAuthGroupList.value = []
	userApi.userGroupList().then((json) => {
		searchGroupList.value = json.data || []
		searchGroupList.value.forEach((item) => (searchGroupMap.value[item.id] = item.name))
	})
	pageApi.spaceAuthList({spaceId: row.id}).then((json) => {
		spaceAuthGroupList.value = json.data || []
		spaceAuthDialogVisible.value = true
	})
}
const deleteSpaceInfo = (row) => {
	ElMessageBox.confirm('确定要删除此空间及下面的所有文档吗？', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		let param = {id: row.id, delFlag: 1}
		pageApi.updateSpace(param).then(() => {
			ElMessage.success('删除成功')
			loadSpaceList()
			loadSpace()
		})
	})
}
let totalCount = ref(0);
let searchParam = ref({
	ignoreFavorite: 1,
	pageNum: 1,
	pageSize: 10,
});
const loadSpaceList = () => {
	spaceListLoading.value = true
	pageApi.spaceList(searchParam.value).then((json) => {
		spaceList.value = json.data || []
		if (searchParam.value.pageNum === 1) {
			totalCount.value = json.total;
		}
		setTimeout(() => (spaceListLoading.value = false), 500)
	})
}
const handleSizeChange = (val) => {
	searchParam.value.pageSize = val
	loadSpaceList()
}
const handleCurrentChange = (val) => {
	searchParam.value.pageNum = val
	loadSpaceList()
}
const wikiOnlyShowFavoriteChange = () => {
	let param = {
		name: 'wiki_only_show_favorite',
		value: userSetting.value.wiki_only_show_favorite,
	}
	pageApi.spaceSettingUpdate(param).then((json) => {
		loadSpace()
	})
}
const getSpaceSettingList = () => {
	pageApi.spaceSettingList().then((json) => {
		let result = json.data || {}
		userSetting.value = {
			wiki_only_show_favorite: result.wiki_only_show_favorite || 0,
		}
	})
}
const getSelfUserInfo = () => {
	userApi.getSelfUserInfo().then((json) => {
		userSelfInfo.value = json.data
	})
}

const loadSpace = (spaceId) => {
	pageApi.spaceList({}).then((json) => {
		storeSpace.spaceList = json.data || [];
		let spaceOptionsNew = [];
		storeSpace.spaceList.forEach((item) => spaceOptionsNew.push({label: item.name, value: item.id}));
		storeSpace.spaceOptions = spaceOptionsNew;
		if (spaceList.value.length > 0) {
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
</script>

<style lang="scss">
.space-manage-vue {
  .page-info-box {
	margin-top: 10px;

	.el-pagination {
	  justify-content: end;
	}
  }
}
</style>

<style>
.space-manage-vue .empty-news {
	text-align: center;
	padding: 100px;
}

.space-manage-vue .text-link {
	color: #444;
}

.space-manage-vue .line-box {
	color: #666;
	border-bottom: 1px solid #eee;
	padding: 20px 0;
}

.space-manage-vue .line-title {
	font-size: 14px;
}

.space-manage-vue .page-preview-box {
}

.space-manage-vue .page-preview-title {
	font-size: 18px;
	margin: 10px 0 5px 0;
	color: #3a8ee6;
	cursor: pointer;
}

.space-manage-vue .page-preview-content {
	font-size: 16px;
	margin-bottom: 5px;
}

.space-manage-vue .zan-img {
	vertical-align: middle;
	margin-top: -3px;
}

.space-manage-vue .view-img {
	font-size: 16px;
	color: #666;
}

.space-manage-vue .page-info-box {
	text-align: right;
	margin: 20px 0 50px 0;
}

.space-manage-vue .favorite-icon {
	cursor: pointer;
	font-size: 20px;
}

.space-manage-vue .favorite-icon.el-icon-star-on {
	color: #e6a23c;
	font-size: 24px;
}
</style>
