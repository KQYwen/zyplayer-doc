<template>
    <a-layout class="swagger-menu-trigger">
        <a-layout-sider theme="light" :trigger="null" collapsible v-model:collapsed="appMenuCollapsed" :width="rightAsideWidth" style="height: 100vh;overflow: auto;">
            <div class="logo">
                <img src="../../assets/logo.png">
                <h1>swagger文档管理</h1>
            </div>
            <menu-layout></menu-layout>
        </a-layout-sider>
        <div ref="rightResize" class="right-resize" v-show="!appMenuCollapsed">
            <i ref="rightResizeBar">...</i>
        </div>
        <a-layout>
            <a-layout-header style="border-bottom: 2px solid #eee;background: #fff; padding: 0; box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);-webkit-box-shadow:0 1px 4px rgba(0, 21, 41, 0.08);">
                <a-row type="flex">
                    <a-col flex="60px">
                        <MenuUnfoldOutlined class="trigger" v-if="appMenuCollapsed" @click="appMenuCollapsed = !appMenuCollapsed"/>
                        <MenuFoldOutlined class="trigger" v-else @click="appMenuCollapsed = !appMenuCollapsed"/>
                    </a-col>
                    <a-col flex="auto" style="text-align: center;">
                        <span v-if="initialEnv === 'newGray'" class="initial-env">当前环境：灰度</span>
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
    import {BarChartOutlined, MenuFoldOutlined, MenuUnfoldOutlined} from '@ant-design/icons-vue';

    const minHeight = window.innerHeight - 64 - 122;
    export default {
        name: 'GlobalLayout',
        components: {HeaderAvatar, MenuLayout, GlobalFooter, BarChartOutlined, MenuFoldOutlined, MenuUnfoldOutlined},
        data() {
            return {
                minHeight: minHeight + 'px',
                appMenuCollapsed: false,
                rightAsideWidth: 250
            }
        },
        computed: {
            initialEnv () {
                return this.$store.state.initialEnv;
            }
        },
        mounted() {
            this.dragChangeRightAsideWidth();
        },
        methods: {
            dragChangeRightAsideWidth: function() {
                // 保留this引用
                let resize = this.$refs.rightResize;
                let resizeBar = this.$refs.rightResizeBar;
                resize.onmousedown = e => {
                    let startX = e.clientX;
                    // 颜色改变提醒
                    resize.style.background = "#ccc";
                    resizeBar.style.background = "#aaa";
                    resize.left = resize.offsetLeft;
                    document.onmousemove = e2 => {
                        // 计算并应用位移量
                        let endX = e2.clientX;
                        let moveLen = startX - endX;
                        if ((moveLen < 0 && this.rightAsideWidth < 600) || (moveLen > 0 && this.rightAsideWidth > 250)) {
                            startX = endX;
                            this.rightAsideWidth -= moveLen;
                        }
                    };
                    document.onmouseup = () => {
                        // 颜色恢复
                        resize.style.background = "#fafafa";
                        resizeBar.style.background = "#ccc";
                        document.onmousemove = null;
                        document.onmouseup = null;
                    };
                    return false;
                };
            }
        },
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

    .logo {
        height: 64px;
        position: relative;
        line-height: 64px;
        padding-left: 24px;
        -webkit-transition: all .3s;
        transition: all .3s;
        overflow: hidden;
        background: #1d4e89;
    }

    .logo h1 {
        color: #fff;
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

    .swagger-menu-trigger {
        min-height: 100%;
    }
    .right-resize {
        width: 5px;
        cursor: w-resize;
        background: #fafafa;
    }
    .right-resize i{
        margin-top: 300px;
        width: 5px;
        height: 35px;
        display: inline-block;
        word-wrap: break-word;
        word-break: break-all;
        line-height: 8px;
        border-radius: 5px;
        background: #ccc;
        color: #888;
    }
</style>

<style>
    .ant-layout-sider {
        transition: none;
    }
</style>
