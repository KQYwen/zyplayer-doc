<template>
    <a-layout class="api-menu-trigger">
        <a-layout-sider theme="light" :trigger="null" collapsible v-model:collapsed="appMenuCollapsed" :width="leftAsideWidth" style="height: 100vh;overflow: auto;">
            <div class="logo">
                <img src="../../assets/api-logo.png">
                <h1>API开放文档</h1>
            </div>
            <menu-layout :collapsed="appMenuCollapsed"></menu-layout>
        </a-layout-sider>
        <div ref="rightResize" class="right-resize" v-show="!appMenuCollapsed">
            <i ref="rightResizeBar">...</i>
        </div>
        <a-layout>
            <a-layout-header style="border-bottom: 2px solid #eee;background: #fff; padding: 0; box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);-webkit-box-shadow:0 1px 4px rgba(0, 21, 41, 0.08);">
                <a-row type="flex">
                    <a-col flex="auto">
                        <MenuUnfoldOutlined class="trigger" v-if="appMenuCollapsed" @click="turnLeftCollapse"/>
                        <MenuFoldOutlined class="trigger" v-else @click="turnLeftCollapse"/>
                    </a-col>
                    <a-col flex="400px" style="text-align: right;padding-right: 20px;">
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
    import MenuLayout from './MenuLayout.vue'
    import {BarChartOutlined, MenuFoldOutlined, MenuUnfoldOutlined} from '@ant-design/icons-vue';

    const minHeight = window.innerHeight - 64 - 122;
    export default {
        components: {MenuLayout, BarChartOutlined, MenuFoldOutlined, MenuUnfoldOutlined},
        data() {
            return {
                minHeight: minHeight + 'px',
                appMenuCollapsed: false,
                leftAsideWidth: 300
            }
        },
        computed: {},
        mounted() {
            this.dragChangeLeftAsideWidth();
        },
        methods: {
            turnLeftCollapse() {
                this.appMenuCollapsed = !this.appMenuCollapsed;
                setTimeout(() => {
                    if (this.appMenuCollapsed) {
                        this.leftAsideWidthChange(this.leftAsideWidth + 1);
                    } else {
                        this.leftAsideWidthChange(1);
                    }
                }, 100);
            },
            leftAsideWidthChange(width) {
                this.$store.commit('setLeftAsideWidth', width);
            },
            dragChangeLeftAsideWidth: function() {
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
                        if ((moveLen < 0 && this.leftAsideWidth < 600) || (moveLen > 0 && this.leftAsideWidth > 280)) {
                            startX = endX;
                            this.leftAsideWidth -= moveLen;
                            if (this.leftAsideWidth < 280) {
                                this.leftAsideWidth = 280;
                            }
                            this.leftAsideWidthChange(this.leftAsideWidth);
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

    .api-menu-trigger {
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
