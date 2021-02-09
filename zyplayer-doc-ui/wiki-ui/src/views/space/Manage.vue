<template>
	<div style="min-height: 100%;" class="space-manage-vue">
		<el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 20px 10px;">
			<el-breadcrumb-item>WIKI文档</el-breadcrumb-item>
			<el-breadcrumb-item>空间管理</el-breadcrumb-item>
		</el-breadcrumb>
		<div style="max-width: 1200px;margin: 0 auto;background: #fff;padding: 20px;min-height: 100%;box-sizing: border-box;">
			<div style="text-align: right;margin-bottom: 10px;">
				<span style="float:left;line-height: 40px;">
					仅展示我收藏的空间：
					<el-switch v-model="userSetting.wiki_only_show_favorite" inactive-value="0" active-value="1" @change="wikiOnlyShowFavoriteChange"></el-switch>
					<el-tooltip class="item" effect="dark" content="控制左上角空间下拉列表仅展示我收藏的空间" placement="top-start">
						<i class="el-icon-warning-outline" style="vertical-align: middle;margin-left: 10px;color: #999;"></i>
					</el-tooltip>
				</span>
				<el-button @click="loadSpaceList" icon="refresh" :loading="spaceListLoading">刷新</el-button>
				<el-button type="primary" @click="showCreateSpace" icon="el-icon-plus">创建空间</el-button>
			</div>
			<el-table :data="spaceList" border style="width: 100%; margin-bottom: 5px;">
				<el-table-column prop="id" label="ID" width="60"></el-table-column>
				<el-table-column prop="name" label="名字"></el-table-column>
				<el-table-column prop="spaceExplain" label="说明"></el-table-column>
				<el-table-column label="开放地址">
					<template slot-scope="scope">
						<el-button type="text" @click="showOpenSpace(scope.row.uuid)" v-if="scope.row.openDoc == 1">{{scope.row.name}}</el-button>
						<span v-else>暂未开放</span>
					</template>
				</el-table-column>
				<el-table-column prop="createUserName" label="创建人"></el-table-column>
				<el-table-column prop="createTime" label="创建时间"></el-table-column>
				<el-table-column prop="favorite" label="收藏" width="60">
					<template slot-scope="scope">
						<i class="el-icon-star-on favorite-icon" v-if="scope.row.favorite == 1" @click="updateSpaceFavorite(scope.row)"></i>
						<i class="el-icon-star-off favorite-icon" v-else @click="updateSpaceFavorite(scope.row)"></i>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="220">
					<template slot-scope="scope" v-if="userSelfInfo.id == scope.row.createUserId">
						<el-button size="small" type="primary" v-on:click="editSpaceInfo(scope.row)">编辑</el-button>
						<el-button size="small" type="warning" v-on:click="editSpaceAuth(scope.row)">授权</el-button>
						<el-button size="small" type="danger" v-on:click="deleteSpaceInfo(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!--分组权限弹窗-->
		<el-dialog title="权限管理" :visible.sync="spaceAuthDialogVisible" width="900px">
			<el-row>
				<el-select v-model="spaceAuthNewGroupId" filterable placeholder="请选择分组" style="width: 750px;margin-right: 10px;">
					<el-option v-for="item in searchGroupList" :key="item.id" :label="searchGroupMap[item.id]" :value="item.id"></el-option>
				</el-select>
				<el-button v-on:click="addSpaceAuthUserGroup">添加</el-button>
			</el-row>
			<el-table :data="spaceAuthGroupList" border style="width: 100%; margin: 10px 0;">
				<el-table-column prop="groupId" label="分组名" width="150">
					<template slot-scope="scope">{{searchGroupMap[scope.row.groupId]}}</template>
				</el-table-column>
				<el-table-column label="权限">
					<template slot-scope="scope">
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.editPage">编辑</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.deletePage">删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileUpload">文件上传</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageFileDelete">文件删除</el-checkbox>
						<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.pageAuthManage">权限管理</el-checkbox>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="80">
					<template slot-scope="scope">
						<el-button size="small" type="danger" plain @click="deleteGroupSpaceAuth(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="text-align: right;">
				<el-button v-on:click="manageUserGroup">分组管理</el-button>
				<el-button type="primary" v-on:click="saveGroupSpaceAuth">保存配置</el-button>
			</div>
		</el-dialog>
		<create-space ref="createSpace" @success="loadSpaceList"></create-space>
	</div>
</template>

<script>
	import pageApi from '../../common/api/page'
	import userApi from '../../common/api/user'
	import CreateSpace from '../../components/space/CreateSpace'

	export default {
		data() {
			return {
				spaceListLoading: false,
				spaceOptions: [],
				spaceList:[],
				choiceSpace: "",
				nowSpaceShow: {},
				newSpaceDialogVisible: false,
				manageSpaceDialogVisible: false,
				newSpaceForm: {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1},
				newSpaceFormRules: {
					name: [
						{required: true, message: '请输入空间名', trigger: 'blur'},
						{min: 2, max: 25, message: '长度在 2 到 25 个字符', trigger: 'blur'}
					],
				},
				userSelfInfo: {},
				// 空间授权
				editSpaceId: '',
				spaceAuthDialogVisible: false,
				spaceAuthNewGroupId: '',
				spaceAuthGroupLoading: false,
				searchGroupList: [],
				searchGroupMap: {},
				spaceAuthGroupList: [],
				// 设置
				userSetting: {
					wiki_only_show_favorite: 0,
				},
			};
		},
		components: {
			"create-space": CreateSpace,
		},
		mounted() {
			this.loadSpaceList();
			this.getSelfUserInfo();
			this.getSpaceSettingList();
		},
		methods: {
			showOpenSpace(space) {
				let routeUrl = this.$router.resolve({path: '/page/share/home', query: {space: space}});
				window.open(routeUrl.href, '_blank');
			},
			showCreateSpace() {
				this.$refs.createSpace.show();
			},
			editSpaceInfo(row) {
				this.$refs.createSpace.show(row.id);
			},
			addSpaceAuthUserGroup() {
				if (!this.spaceAuthNewGroupId) {
					this.$message.warning("请先选择分组");
					return;
				}
				if (!!this.spaceAuthGroupList.find(item => item.groupId == this.spaceAuthNewGroupId)) {
					this.spaceAuthNewGroupId = "";
					return;
				}
				this.spaceAuthGroupList.push({
					groupId: this.spaceAuthNewGroupId,
					editPage: 0,
					commentPage: 0,
					deletePage: 0,
					pageFileUpload: 0,
					pageFileDelete: 0,
					pageAuthManage: 0,
				});
				this.spaceAuthNewGroupId = '';
			},
			updateSpaceFavorite(row) {
				let delFlag = (row.favorite == 1) ? 1 : 0;
				pageApi.spaceFavoriteUpdate({spaceId: row.id, delFlag: delFlag}).then(json => {
					row.favorite = (row.favorite == 1) ? 0 : 1;
				});
			},
			saveGroupSpaceAuth() {
				let param = {spaceId: this.editSpaceId, authList: JSON.stringify(this.spaceAuthGroupList)};
				pageApi.spaceAuthAssign(param).then(json => {
					this.$message.success("授权成功！");
				});
			},
			manageUserGroup() {
				let manageUrl = location.href.substring(0, location.href.indexOf("/doc-wiki")) + '#/console/userGroupList';
				window.open(manageUrl, '_blank');
			},
			deleteGroupSpaceAuth(row) {
				this.spaceAuthGroupList = this.spaceAuthGroupList.filter(item => item.groupId != row.groupId);
			},
			editSpaceAuth(row) {
				this.editSpaceId = row.id;
				this.spaceAuthNewGroupId = '';
				this.spaceAuthGroupList = [];
				userApi.userGroupList().then(json => {
					this.searchGroupList = json.data || [];
					this.searchGroupList.forEach(item => this.searchGroupMap[item.id] = item.name);
				});
				pageApi.spaceAuthList({spaceId: row.id}).then(json => {
					this.spaceAuthGroupList = json.data || [];
					this.spaceAuthDialogVisible = true;
				});
			},
			deleteSpaceInfo(row) {
				this.$confirm('确定要删除此空间及下面的所有文档吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let param = {id: row.id, delFlag: 1};
					pageApi.updateSpace(param).then(() => {
						this.$message.success("删除成功");
						this.loadSpaceList();
						this.$emit('loadSpace');
					});
				});
			},
			loadSpaceList() {
				this.spaceListLoading = true;
				pageApi.spaceList({ignoreFavorite: 1}).then(json => {
					this.spaceList = json.data || [];
					setTimeout(() => this.spaceListLoading = false, 500);
				});
			},
			wikiOnlyShowFavoriteChange() {
				let param = {name: 'wiki_only_show_favorite', value: this.userSetting.wiki_only_show_favorite};
				pageApi.spaceSettingUpdate(param).then(json => {
					this.$emit('loadSpace');
				});
			},
			getSpaceSettingList() {
				pageApi.spaceSettingList().then(json => {
					let result = json.data || {};
					this.userSetting = {
						wiki_only_show_favorite: result.wiki_only_show_favorite || 0,
					};
				});
			},
			getSelfUserInfo() {
				userApi.getSelfUserInfo().then(json=>{
					this.userSelfInfo = json.data;
				});
			},
		}
	}
</script>
<style>
	.space-manage-vue .empty-news{text-align: center;padding: 100px;}

	.space-manage-vue .text-link {
		color: #444;
		/*cursor: pointer;*/
		/*font-weight: bold;*/
	}
	.space-manage-vue .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
	.space-manage-vue .line-title{font-size: 14px;}
	.space-manage-vue .page-preview-box{}
	.space-manage-vue .page-preview-title{font-size: 18px;margin: 10px 0 5px 0;color: #3a8ee6;cursor: pointer;}
	.space-manage-vue .page-preview-content{font-size: 16px;margin-bottom: 5px;}
	.space-manage-vue .zan-img{vertical-align: middle;margin-top: -3px;}
	.space-manage-vue .view-img{font-size: 16px;color: #666;}

	.space-manage-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
	.space-manage-vue .favorite-icon{cursor: pointer; font-size: 20px;}
	.space-manage-vue .favorite-icon.el-icon-star-on{color: #E6A23C; font-size: 24px;}
</style>

