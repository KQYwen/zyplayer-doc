<template>
    <div class="menu-layout">
        <div style="padding: 10px 5px;">
            <a-select placeholder="请选择分组" v-model:value="swaggerDocChoice" style="width: 100%;">
                <a-select-option :value="item.url" v-for="item in swaggerResourceList">{{item.name}}</a-select-option>
            </a-select>
        </div>
        <a-menu theme="light" mode="inline" :inline-collapsed="collapsed" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-directory-tree :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked"></a-directory-tree>
    </div>
</template>

<script>
    import MenuChildrenLayout from './MenuChildrenLayout.vue'
    import {customApi} from '../../api'

    export default {
        name: 'MenuLayout',
        data() {
            return {
                menuData: [],
                selectedKeys: [],
                openKeys: [],
                collapsed: false,
                // 文档树
                treeData: [
                    {
                        title: '用户管理接口文档',
                        key: '0-0',
                        children: [
                            {
                                title: '用户信息管理',
                                key: '0-0-0',
                                children: [
                                    {title: '/getUserInfo', key: '0-0-0-0', isLeaf: true, path: '/doc/view', query: {path: '/getUserInfo'}},
                                    {title: '/deleteUserInfo', key: '0-0-0-1', isLeaf: true, path: '/doc/view', query: {path: '/deleteUserInfo'}},
                                    {title: '/updateUserInfo', key: '0-0-0-2', isLeaf: true, path: '/doc/view', query: {path: '/updateUserInfo'}},
                                ],
                            },
                        ],
                    },
                ],
                expandedKeys: [],
                swaggerResourceList: [],
                swaggerDocChoice: undefined,
            }
        },
        watch:{
            '$store.state.userInfo'(userInfo) {
            }
        },
        components: {MenuChildrenLayout},
        mounted() {
            this.menuData = this.$router.options.routes.find((item) => item.path === '/').children[0].children;
            let meta = this.$route.meta || {};
            let path = this.$route.path;
            if (!!meta.parentPath) {
                path = meta.parentPath;
            }
            this.selectedKeys = [path];
            let matched = this.$route.matched;
            if (matched.length >= 1) {
                this.openKeys = [matched[1].path];
            }
            this.getSwaggerResourceList();
        },
        methods: {
            docChecked(val, node) {
                if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    this.$router.push({path: dataRef.path, query: dataRef.query});
                }
            },
            getSwaggerResourceList() {
                customApi.post('./swagger-resources').then(res => {
                    this.swaggerResourceList = res || [];
                    if (this.swaggerResourceList.length > 0) {
                        this.swaggerDocChoice = this.swaggerResourceList[0].url;
                    }
                });
            }
        }
    }
</script>

