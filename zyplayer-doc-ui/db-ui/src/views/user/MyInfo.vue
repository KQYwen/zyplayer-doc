<template>
    <div class="my-info-vue">
        <div style="margin: 0 auto;max-width: 1000px;">
            <el-card class="box-card">
                <div slot="header" class="clearfix">我的信息</div>
                <el-form class="search-form-box" label-width="100px">
                    <el-form-item label="账号：">{{userInfo.userNo}}</el-form-item>
                    <el-form-item label="用户名：">{{userInfo.userName}}</el-form-item>
                    <el-form-item label="手机号：">{{userInfo.phone}}</el-form-item>
                    <el-form-item label="邮箱：">{{userInfo.email}}</el-form-item>
                    <el-form-item label="状态：">{{userInfo.delFlag==0?'正常':'停用'}}</el-form-item>
                    <el-form-item label="性别：">{{userInfo.sex==0?'女':'男'}}</el-form-item>
                </el-form>
            </el-card>
        </div>
    </div>
</template>

<script>
    import userApi from '../../common/api/user'
    export default {
        data() {
            return {
                userInfo: {}
            };
        },
        mounted: function () {
            this.getUserInfo();
        },
        methods: {
            getUserInfo() {
                userApi.getSelfUserInfo().then(json => {
                    this.userInfo = json.data;
                });
            },
        }
    }

</script>
<style>
    .my-info-vue{}
    .my-info-vue .box-card{margin: 10px;}
</style>

