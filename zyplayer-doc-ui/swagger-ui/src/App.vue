<template>
    <el-config-provider :locale="localeEl">
        <a-config-provider :locale="locale">
            <router-view></router-view>
        </a-config-provider>
    </el-config-provider>
</template>

<script>
    import zhCN from 'ant-design-vue/es/locale/zh_CN';
    import zhCnEl from 'element-plus/lib/locale/lang/zh-cn';
    import 'moment/dist/locale/zh-cn';
    export default {
        name: 'app',
        components: {
        },
        data() {
            return {
                locale: zhCN,
                localeEl: zhCnEl,
            };
        },
        methods: {}
    }
</script>

<style>
    html, body, #app {
        height: 100%;
        background: #f0f2f5;
    }
    .ant-btn+.ant-btn{
        margin-left: 10px;
    }
</style>
