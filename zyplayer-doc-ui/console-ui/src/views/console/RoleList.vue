<template>
    <div>
        <div style="border-bottom: 1px solid #eee;padding: 10px;margin-bottom: 10px;">角色管理</div>
        <el-form :inline="true" :model="searchParam" class="search-form-box">
            <el-form-item label="搜索类型">
                <el-select v-model="searchParam.type" placeholder="请选择">
                    <el-option label="角色名" :value="1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="关键字">
                <el-input v-model="searchParam.keyword" placeholder="输入关键字"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="">查询</el-button>
            </el-form-item>
        </el-form>
        <div style="padding: 10px;">
            <el-table :data="searchResultList" border style="width: 100%; margin-bottom: 5px;" max-height="500">
                <el-table-column prop="id" label="编号" width="60"></el-table-column>
                <el-table-column prop="name" label="角色名"></el-table-column>
                <el-table-column prop="spaceExplain" label="CODE"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" plain type="primary" v-on:click="editUserInfo(scope.row)">修改</el-button>
                        <el-button size="small" plain type="primary" v-on:click="resetPassword(scope.row)">权限管理</el-button>
                        <el-button size="small" plain type="warning" v-on:click="editUserInfo(scope.row)">删除</el-button>
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
                    <el-input v-model="editUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="editUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="editUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="editUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="editUserForm.resource">
                        <el-radio label="男"></el-radio>
                        <el-radio label="女"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="editUserForm.xx" multiple filterable placeholder="请选择">
                        <el-option v-for="item in roleOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="">确定</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                editUserDialogVisible: false,
                totalCount: 0,
                searchParam: {
                    type: 1,
                    keyword: '',
                    pageSize: 20,
                    pageNum: 1,
                },
                searchResultList: [
                    {name: '张三'}
                ],
                roleOptions: [
                    {value: '管理员'}
                ],
                editUserForm: {},
            };
        },
        mounted: function () {
        },
        methods: {
            handleSizeChange(val) {
                this.searchParam.pageSize = val;
            },
            handleCurrentChange(val) {
                this.searchParam.pageNum = val;
            },
            editUserInfo() {
                this.editUserDialogVisible = true;
            },
            resetPassword() {
            },
        }
    }

</script>
<style>
    .search-form-box{padding: 10px;}
    .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

