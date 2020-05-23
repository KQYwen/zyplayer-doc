<template>
    <div style="padding-top: 50px;">
        <el-form :model="loginParam" :rules="loginRules" ref="loginParam" label-position="left" label-width="0px"
                 class="demo-ruleForm login-container">
            <h3 class="title">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginParam.username" auto-complete="off" placeholder="账号" @keyup.enter="loginSubmit"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginParam.password" auto-complete="off" placeholder="密码" @keyup.enter="loginSubmit"></el-input>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;" @click.native.prevent="loginSubmit" :loading="logining">登录</el-button>
                <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import userApi from '../../common/api/user'
    export default {
        data() {
            return {
                logining: false,
                redirect: '',
                loginParam: {
                    username: '',
                    password: ''
                },
                loginRules: {
                    username: [
                        {required: true, message: '请输入账号', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ]
                },
                checked: true
            };
        },
        mounted: function () {
            this.redirect = this.$route.query.redirect;
        },
        methods: {
            loginSubmit() {
                this.$refs.loginParam.validate((valid) => {
                    if (!valid) return;
                    userApi.userLogin(this.loginParam).then(() => {
                        if (!!this.redirect) {
                            location.href = decodeURIComponent(this.redirect);
                        } else {
                            this.$router.back();
                        }
                    });
                });
            }
        }
    }

</script>
<style>
    .login-container {
        -webkit-border-radius: 5px;
        border-radius: 5px;
        -moz-border-radius: 5px;
        background-clip: padding-box;
        margin: 0 auto;
        width: 350px;
        padding: 35px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
    }

    .remember {
        margin: 0px 0px 35px 0px;
    }

</style>

