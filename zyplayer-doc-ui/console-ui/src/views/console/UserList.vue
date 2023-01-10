<template>
    <div class="user-list-vue">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 20px 10px;">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>系统管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form :inline="true" :model="searchParam" class="search-form-box">
            <el-form-item label="搜索类型">
                <el-select v-model="searchParam.type" placeholder="请选择">
                    <el-option label="ID" :value="1"></el-option>
                    <el-option label="账号" :value="2"></el-option>
                    <el-option label="用户名" :value="3"></el-option>
                    <el-option label="手机" :value="4"></el-option>
                    <el-option label="邮箱" :value="5"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="关键字">
                <el-input v-model="searchParam.keyword" placeholder="输入关键字"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getUserList">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="addUserInfo"><i class="el-icon-plus"></i> 添加</el-button>
            </el-form-item>
        </el-form>
        <div style="padding: 10px;" v-loading="searchLoading">
            <el-table :data="searchResultList" border style="width: 100%; margin-bottom: 5px;" max-height="500">
                <el-table-column prop="id" label="编号" width="60"></el-table-column>
                <el-table-column prop="userNo" label="账号"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="userName" label="用户名"></el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>
                <el-table-column label="性别">
                    <template slot-scope="scope">{{scope.row.sex==0?'女':'男'}}</template>
                </el-table-column>
                <el-table-column prop="creationTime" label="创建时间"></el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">{{scope.row.delFlag==0?'正常':'停用'}}</template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button size="mini" plain type="primary" v-on:click="editUserInfo(scope.row)">修改</el-button>
                        <el-button size="mini" plain type="success" v-on:click="editUserAuthFun(scope.row)">权限</el-button>
                        <el-button size="mini" plain type="warning" v-on:click="resetPassword(scope.row)">重置密码</el-button>
                        <el-button size="mini" plain type="danger" v-on:click="deleteUser(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="page-info-box">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-sizes="[20, 50, 100]"
                    :page-size="20"
                    :current-page="searchParam.pageNum"
                    layout="prev, pager, next, jumper, sizes, total"
                    :total="totalCount"
            >
            </el-pagination>
        </div>
        <!--修改用户权限弹窗-->
        <el-dialog title="权限编辑" :visible.sync="editUserAuthDialogVisible" width="600px" :close-on-click-modal="false">
            <el-form ref="form" label-width="80px">
                <el-form-item label="账号">
                    <el-input v-model="editUserForm.userNo" disabled></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="editUserForm.userName" disabled></el-input>
                </el-form-item>
                <el-form-item label="权限">
                    <el-select v-model="editUserAuth" multiple filterable placeholder="请选择" style="width: 100%;">
                        <el-option v-for="item in allUserAuth" :key="item.id" :label="item.authDesc" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="editUserAuthSave">确定</el-button>
                    <el-button @click="editUserAuthDialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--修改用户弹窗-->
        <el-dialog :title="editUserForm.id > 0 ? '修改用户':'创建用户'" :visible.sync="editUserDialogVisible" width="600px" :close-on-click-modal="false">
            <el-form ref="form" :model="editUserForm" label-width="80px">
                <el-form-item label="账号">
                    <el-input v-model="editUserForm.userNo"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="editUserForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="editUserForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="editUserForm.email"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="editUserForm.password" placeholder="为空代表不修改密码"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch v-model="editUserForm.delFlag" :active-value="0" active-text="正常" :inactive-value="2" inactive-text="停用"></el-switch>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="editUserForm.sex">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
<!--                先不做角色，想清楚了再做-->
<!--                <el-form-item label="角色">-->
<!--                    <el-select v-model="editUserForm.role" multiple filterable placeholder="请选择">-->
<!--                        <el-option v-for="item in roleOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
                <el-form-item>
                    <el-button type="primary" @click="updateEditUser">确定</el-button>
                    <el-button @click="editUserDialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
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
                    pageSize: 20,
                    pageNum: 1,
                },
                searchResultList: [],
                roleOptions: [
                    {value: '管理员'}
                ],
                editUserForm: {},
                allUserAuth: [],
                editUserAuth: [],
            };
        },
        mounted: function () {
            this.getUserList();
        },
        methods: {
            handleSizeChange(val) {
                this.searchParam.pageSize = val;
                this.getUserList();
            },
            handleCurrentChange(val) {
                this.searchParam.pageNum = val;
                this.getUserList();
            },
            editUserAuthFun(row) {
                this.allUserAuth = [];
                this.editUserAuth = [];
                var param = {userIds: row.id};
				consoleApi.userAuthList(param).then(json => {
					this.editUserAuth = [];
					this.allUserAuth = json.data;
					this.editUserAuthDialogVisible = true;
					this.editUserForm = JSON.parse(JSON.stringify(row));
					for (var i = 0; i < this.allUserAuth.length; i++) {
						if (this.allUserAuth[i].checked == 1) {
							this.editUserAuth.push(this.allUserAuth[i].id);
						}
					}
				});
            },
            editUserAuthSave() {
                var param = {
                    userIds: this.editUserForm.id,
                    authIds: this.editUserAuth.join(","),
                };
				consoleApi.updateUserAuth(param).then(json => {
					this.$message.success("保存成功！");
					this.editUserAuthDialogVisible = false;
                });
            },
            editUserInfo(row) {
                this.editUserDialogVisible = true;
                this.editUserForm = JSON.parse(JSON.stringify(row));
            },
            addUserInfo() {
                this.editUserDialogVisible = true;
                this.editUserForm = {};
            },
            resetPassword(row) {
                this.$confirm('确定要重置此用户密码吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.editUserForm = JSON.parse(JSON.stringify(row));
					consoleApi.resetPassword(this.editUserForm).then(json => {
						this.$confirm("重置成功！新的密码为：" + json.data).then(()=> {
						}).catch(()=> {});
					});
                }).catch(()=>{});
            },
            deleteUser(row) {
                this.$confirm('确定要删除此用户吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
					this.editUserForm = JSON.parse(JSON.stringify(row));
					consoleApi.deleteUserInfo(this.editUserForm).then(json => {
						this.$message.success("删除成功！");
						this.getUserList();
					});
                }).catch(()=>{});
            },
            updateEditUser() {
				consoleApi.updateUserInfo(this.editUserForm).then(json => {
					this.$message.success("保存成功！");
					this.editUserDialogVisible = false;
					this.getUserList();
				});
            },
            getUserList() {
                this.searchLoading = true;
				consoleApi.getUserInfoList(this.searchParam).then(json => {
					// 让加载动画停留一会
					setTimeout(()=>{this.searchLoading = false;}, 500);
					this.totalCount = json.total;
					this.searchResultList = json.data;
				});
            },
        }
    }

</script>
<style>
    .user-list-vue .search-form-box{padding: 10px;}
    .user-list-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
    .user-list-vue .el-button+.el-button{margin-left: 5px;}
</style>

