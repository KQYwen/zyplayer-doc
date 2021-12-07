<template>
    <a-layout class="api-menu-trigger">
        <a-layout-sider theme="light" :trigger="null" collapsible v-model:collapsed="appMenuCollapsed" :width="leftAsideWidth" style="height: 100vh;overflow: auto;">
            <div class="header-border logo">
                <img src="../../assets/api-logo.png">
                <h1>API接口文档管理</h1>
            </div>
            <menu-layout :collapsed="appMenuCollapsed"></menu-layout>
        </a-layout-sider>
        <LeftResize v-model:value="leftAsideWidth" v-show="!appMenuCollapsed"></LeftResize>
        <a-layout>
            <a-layout-header class="header-border">
                <a-row type="flex">
                    <a-col flex="auto">
                        <MenuUnfoldOutlined class="trigger" v-if="appMenuCollapsed" @click="appMenuCollapsed = !appMenuCollapsed"/>
                        <MenuFoldOutlined class="trigger" v-else @click="appMenuCollapsed = !appMenuCollapsed"/>
                    </a-col>
                    <a-col flex="400px" style="text-align: right;padding-right: 20px;">
                        <header-avatar></header-avatar>
                    </a-col>
                </a-row>
            </a-layout-header>
            <a-layout-content style="height: calc(100vh - 80px);overflow: auto;background: #fff;">
                <router-view></router-view>
            </a-layout-content>
        </a-layout>
    </a-layout>
</template>

<script>
    import HeaderAvatar from './HeaderAvatar.vue'
    import MenuLayout from './MenuLayout.vue'
    import GlobalFooter from './GlobalFooter.vue'
    import LeftResize from './components/LeftResize.vue'
    import {BarChartOutlined, MenuFoldOutlined, MenuUnfoldOutlined} from '@ant-design/icons-vue';

    const minHeight = window.innerHeight - 64 - 122;
    export default {
        name: 'GlobalLayout',
        components: {
            LeftResize,
            HeaderAvatar,
            MenuLayout,
            GlobalFooter,
            BarChartOutlined,
            MenuFoldOutlined,
            MenuUnfoldOutlined
        },
        data() {
            return {
                minHeight: minHeight + 'px',
                appMenuCollapsed: false,
                leftAsideWidth: 300
            }
        },
        computed: {},
        mounted() {
        },
        methods: {},
    }
</script>

<style scoped>
    .trigger {
        font-size: 20px;
        line-height: 64px;
        padding: 0 24px;
        cursor: pointer;
        transition: color .3s;
    }

    .trigger:hover {
        color: #1890ff;
    }

    .header-border {
        border-bottom: 2px solid #eee;
        background: #fff;
        padding: 0;
        box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
        -webkit-box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    }

    .logo {
        height: 64px;
        position: relative;
        line-height: 64px;
        padding-left: 24px;
        -webkit-transition: all .3s;
        transition: all .3s;
        overflow: hidden;
        /*background: #fff;*/
        background: #1d4e89;
    }

    .logo h1 {
        color: #fff;
        /*color: #2c3e50;*/
        font-size: 20px;
        margin: 0 0 0 12px;
        font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica, sans-serif;
        font-weight: 600;
        display: inline-block;
        height: 32px;
        line-height: 32px;
        vertical-align: middle;
    }

    .logo img {
        width: 32px;
        display: inline-block;
        vertical-align: middle;
    }

    .api-menu-trigger {
        min-height: 100%;
    }
</style>

