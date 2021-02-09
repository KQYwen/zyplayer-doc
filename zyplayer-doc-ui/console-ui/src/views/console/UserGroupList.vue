<template>
	<div class="user-group-vue">
		<el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 20px 10px;">
			<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>系统管理</el-breadcrumb-item>
			<el-breadcrumb-item>分组管理</el-breadcrumb-item>
		</el-breadcrumb>
		<el-row>
			<el-col :span="6">
				<div class="group-box page-scroll-box">
					<el-popover placement="bottom" width="300" trigger="click" v-model="createUserGroupVisible">
						<el-tag slot="reference" class="group-item" @click="">
							<div style="text-align: center;"><i class="el-icon-plus"></i> 创建分组</div>
						</el-tag>
						<div>
							<el-input v-model="editGroupName" placeholder="请输入新的分组名称" style="width: 220px;margin-right: 10px;"></el-input>
							<el-button plain type="primary" v-on:click="createUserGroup">创建</el-button>
						</div>
					</el-popover>
					<el-tag :type="item.id==checkedUserGroupId?'warning':'info'" class="group-item" @click="loadUserGroupRelation(item.id)" @dblclick.native="item.edit = true" v-for="item in userGroupList">
						<el-input v-if="item.edit" size="mini" v-model="item.editName" @keyup.enter.native="updateUserGroup(item)" @blur="updateUserGroup(item)" class="group-name-input">{{item.name}}</el-input>
						<span v-else>{{item.name}}</span>
						<el-popconfirm title="确定要删除此分组吗？" @confirm="removeUserGroup(item.id)">
							<i slot="reference" class="el-tag__close el-icon-close"></i>
						</el-popconfirm>
					</el-tag>
				</div>
			</el-col>
			<el-col :span="18">
				<div v-if="checkedUserGroupId > 0" class="page-scroll-box" v-loading="searchLoading">
					<div style="margin-bottom: 10px;">
						<el-button size="mini" plain type="primary" @click="showChoiceUserDialog" icon="el-icon-plus">添加用户</el-button>
					</div>
					<el-table :data="userGroupRelationList" border style="width: 100%; margin-bottom: 5px;" :max-height="tableHeight">
						<el-table-column prop="id" label="编号" width="60"></el-table-column>
						<el-table-column prop="userNo" label="账号"></el-table-column>
						<el-table-column prop="email" label="邮箱"></el-table-column>
						<el-table-column prop="userName" label="用户名"></el-table-column>
						<el-table-column prop="phone" label="手机号"></el-table-column>
						<el-table-column label="性别">
							<template slot-scope="scope">{{scope.row.sex==0?'女':'男'}}</template>
						</el-table-column>
						<el-table-column label="操作" width="300">
							<template slot-scope="scope">
								<el-button size="mini" plain type="danger" v-on:click="removeUserRelationFromList(scope.row.id)">移除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</el-col>
		</el-row>
		<!--添加用户到分组弹窗-->
		<el-dialog title="添加用户到分组" :visible.sync="choiceUserVisible" width="600px" @close="closeChoiceUserDialog">
			<el-row>
				<el-select v-model="searchAddNewUser" filterable remote reserve-keyword autoComplete="new-password"
						   placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList"
						   :loading="searchUserLoading" style="width: 450px;margin-right: 10px;">
					<el-option v-for="item in searchUserList" :key="item.id" :label="item.userName" :value="item.id"></el-option>
				</el-select>
				<el-button v-on:click="addSearchChoiceUser">添加</el-button>
			</el-row>
			<div style="margin: 10px 0;">
				<el-tag v-for="item in searchAddUserList" :key="item.userId" closable type="info" style="margin-right: 10px;" @close="removeUserRelationFromSearch(item.userId)">
					{{item.userName}}
				</el-tag>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import consoleApi from '../../common/api/console'

	export default {
		data() {
			return {
				searchLoading: false,
				editUserDialogVisible: false,
				editUserAuthDialogVisible: false,
				totalCount: 0,
				searchParam: {
					type: 1,
					keyword: '',
				},
				searchResultList: [],
				roleOptions: [
					{value: '管理员'}
				],
				editUserForm: {},
				allUserAuth: [],
				editUserAuth: [],

				userGroupRelationList: [],
				userGroupList: [],
				editGroupName: '',
				checkedUserGroupId: '',
				createUserGroupVisible: false,
				choiceUserVisible: false,
				// 添加用户
				searchAddUserList: [],
				searchUserList: [],
				searchAddNewUser: "",
				searchUserLoading: false,
				tableHeight: (document.body.clientHeight - 250),
			};
		},
		mounted() {
			this.getUserGroupList();
		},
		methods: {
			getUserGroupList() {
				this.userGroupList = [];
				consoleApi.userGroupList().then(json => {
					let userGroupList = json.data || [];
					userGroupList.forEach(item => {
						item.edit = false;
						item.checked = false;
						item.editName = item.name;
					});
					this.userGroupList = userGroupList;
				});
			},
			loadUserGroupRelation(groupId, force) {
				if (!force && groupId == this.checkedUserGroupId) return;
				this.checkedUserGroupId = groupId;
				this.searchLoading = true;
				consoleApi.userGroupRelationList({groupId: groupId}).then(json => {
					this.searchLoading = false;
					this.userGroupRelationList = json.data || [];
				});
			},
			updateUserGroup(item) {
				if (item.name == item.editName) {
					item.edit = false;
					return;
				}
				let param = {id: item.id, name: item.editName};
				consoleApi.updateUserGroup(param).then(json => {
					item.edit = false;
					item.name = item.editName;
				});
			},
			createUserGroup() {
				let param = {name: this.editGroupName};
				consoleApi.updateUserGroup(param).then(json => {
					this.editGroupName = '';
					this.createUserGroupVisible = false;
					this.getUserGroupList();
				});
			},
			removeUserGroup(id) {
				consoleApi.deleteUserGroup({id: id}).then(json => {
					this.checkedUserGroupId = '';
					this.getUserGroupList();
				});
			},
			closeChoiceUserDialog() {
				this.loadUserGroupRelation(this.checkedUserGroupId, true);
			},
			showChoiceUserDialog() {
				this.choiceUserVisible = true;
				this.searchAddUserList = [];
				this.userGroupRelationList.forEach(item => {
					this.searchAddUserList.push({userName: item.userName, userId: item.id});
				});
			},
			getSearchUserList(query) {
				if (!query) return;
				this.searchUserLoading = true;
				consoleApi.searchUserInfoList({search: query}).then(json => {
					this.searchUserList = json.data || [];
					this.searchUserLoading = false;
				});
			},
			removeUserRelationFromSearch(userId) {
				let param = {groupId: this.checkedUserGroupId, userId: userId};
				consoleApi.removeUserGroupRelation(param).then(json => {
					this.searchAddUserList = this.searchAddUserList.filter(item => item.userId != userId);
				});
			},
			removeUserRelationFromList(userId) {
				let param = {groupId: this.checkedUserGroupId, userId: userId};
				consoleApi.removeUserGroupRelation(param).then(json => {
					this.loadUserGroupRelation(this.checkedUserGroupId, true);
				});
			},
			addSearchChoiceUser() {
				if (this.searchAddNewUser.length <= 0) {
					this.$message.warning("请先选择用户");
					return;
				}
				if (!!this.searchAddUserList.find(item => item.userId == this.searchAddNewUser)) {
					this.searchAddNewUser = "";
					return;
				}
				let userName = this.searchUserList.find(item => item.id == this.searchAddNewUser).userName;
				let param = {groupId: this.checkedUserGroupId, userId: this.searchAddNewUser};
				consoleApi.updateUserGroupRelation(param).then(json => {
					this.searchAddUserList.push({userName: userName, userId: this.searchAddNewUser});
				});
				this.searchAddNewUser = "";
			},
		}
	}
</script>
<style>
	.user-group-vue .search-form-box{padding: 10px;}
	.user-group-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
	.user-group-vue .el-button+.el-button{margin-left: 5px;}

	.user-group-vue .page-scroll-box{padding: 10px;height: calc(100vh - 200px);overflow: auto;}
	.user-group-vue .group-box .group-item{width: 100%;margin-bottom: 10px;cursor: pointer;}
	.user-group-vue .group-box .group-item .el-icon-close{float: right; top: 6px;}
	.user-group-vue .group-box .group-item .group-name-input{width: calc(100% - 30px);}
	.user-group-vue .group-box .group-item .group-name-input input{border: 0;padding-left: 5px;}
</style>

