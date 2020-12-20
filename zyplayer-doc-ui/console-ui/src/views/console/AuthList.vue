<template>
    <div>
        <div style="border-bottom: 1px solid #eee;padding: 10px;margin-bottom: 10px;">权限列表</div>
        <div style="padding: 10px;">
            <el-table :data="searchResultList" border style="width: 100%; margin-bottom: 5px;" max-height="500">
                <el-table-column prop="id" label="编号" width="60"></el-table-column>
                <el-table-column prop="name" label="权限名"></el-table-column>
                <el-table-column prop="spaceExplain" label="权限说明"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
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

