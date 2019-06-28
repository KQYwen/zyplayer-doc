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
                    <template slot-scope="scope">
                        <el-switch v-on:change="delFlagChange" v-model="scope.row.delFlag" :active-value="0" active-text="正常" :inactive-value="1" inactive-text="停用"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="240">
                    <template slot-scope="scope">
                        <el-button size="mini" plain type="primary" v-on:click="editUserInfo(scope.row)">修改</el-button>
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
        <!--修改用户弹窗-->
        <el-dialog title="修改用户" :visible.sync="editUserDialogVisible" width="600px">
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
                <el-form-item label="性别">
                    <el-radio-group v-model="editUserForm.sex">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="editUserForm.role" multiple filterable placeholder="请选择">
                        <el-option v-for="item in roleOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateEditUser">确定</el-button>
                    <el-button @click="editUserDialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import toast from '../../common/lib/common/toast'
    var app;

    export default {
        data() {
            return {
                searchLoading: false,
                editUserDialogVisible: false,
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
            };
        },
        mounted: function () {
            app = this;
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
            editUserInfo(row) {
                this.editUserDialogVisible = true;
                this.editUserForm = row;
            },
            resetPassword(row) {
                this.$confirm('确定要重置此用户密码吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.editUserForm = row;
                    this.common.post(this.apilist1.resetPassword, this.editUserForm, function (json) {
                        app.$confirm("重置成功！新的密码为：" + json.data).then(()=> {
                            done();
                        }).catch(()=> {});
                    });
                }).catch(()=>{});
            },
            delFlagChange(row, xx) {
                debugger
                // this.editUserForm = row;
            },
            deleteUser(row) {
                this.$confirm('确定要删除此用户吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.editUserForm = row;
                    this.editUserForm.delFlag = 1;
                    this.updateEditUser();
                }).catch(()=>{});
            },
            updateEditUser() {
                this.common.post(this.apilist1.updateUserInfo, this.editUserForm, function (json) {
                    toast.success("保存成功！");
                    app.editUserDialogVisible = false;
                    app.getUserList();
                });
            },
            getUserList() {
                this.searchLoading = true;
                this.common.post(this.apilist1.getUserInfoList, this.searchParam, function (json) {
                    // 让加载动画停留一会
                    setTimeout(()=>{app.searchLoading = false;}, 500);
                    app.totalCount = json.total;
                    app.searchResultList = json.data;
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

