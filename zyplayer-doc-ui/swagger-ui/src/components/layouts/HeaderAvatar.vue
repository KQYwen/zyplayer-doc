<template>
    <a-dropdown trigger="click">
        <a class="ant-dropdown-link" @click.prevent style="display: inline-block; height: 100%; vertical-align: initial;">
            <UserOutlined /> {{currUser.userName || '-'}}
        </a>
        <template #overlay>
            <a-menu >
                <a-menu-item @click="showConsole" key="1">控制台</a-menu-item>
                <a-menu-divider />
                <a-menu-item @click="showAbout" key="2">关于</a-menu-item>
<!--                <a-menu-item @click="showMyInfo" key="3">我的资料</a-menu-item>-->
                <a-menu-item @click="userSignOut" key="4">退出登录</a-menu-item>
            </a-menu>
        </template>
    </a-dropdown>
    <about-dialog ref="aboutDialog"></about-dialog>
</template>

<script>
    import {zyplayerApi} from '../../api/index';
    import {getZyplayerApiBaseUrl} from '../../api/request/utils.js';
    import aboutDialog from '../../views/common/AboutDialog.vue'
    import {DownOutlined, UserOutlined} from '@ant-design/icons-vue';

    export default {
        name: 'HeaderAvatar',
        data() {
            return {
                currUser: {},
            };
        },
        components: {DownOutlined, UserOutlined, aboutDialog},
        mounted() {
            this.getSelfUserInfo();
        },
        methods: {
            showAbout() {
                this.$refs.aboutDialog.show();
            },
            showConsole() {
                window.open(getZyplayerApiBaseUrl(), '_blank');
            },
            showMyInfo() {
                this.$router.push({path: '/user/myInfo'});
            },
            userSignOut() {
                zyplayerApi.userLogout().then(() => {
                    location.reload();
                });
            },
            getSelfUserInfo() {
                zyplayerApi.getSelfUserInfo().then(json=>{
                    this.currUser = json.data;
                });
            },
        },
    }
</script>

<style scoped>
    .avatar {
        margin: 20px 4px 20px 0;
        color: #1890ff;
        background: hsla(0, 0%, 100%, .85);
        vertical-align: middle;
    }
</style>
