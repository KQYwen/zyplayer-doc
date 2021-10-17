<template>
    <div class="menu-layout">
        <a-menu theme="light" mode="inline" :inline-collapsed="collapsed" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-directory-tree :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
            <a-tree-node key="0-0" title="parent 0">
                <a-tree-node key="0-0-0" is-leaf >
                    <template #title>
                        <router-link :to="{path: '/doc/view', query: {id: 1}}">leaf 0-0</router-link>
                    </template>
                </a-tree-node>
                <a-tree-node key="0-0-1" is-leaf >
                    <template #title>
                        <router-link :to="{path: '/doc/view', query: {id: 2}}">leaf 0-1</router-link>
                    </template>
                </a-tree-node>
            </a-tree-node>
            <a-tree-node key="0-1" title="parent 1">
                <a-tree-node key="0-1-0" title="leaf 1-0" is-leaf />
                <a-tree-node key="0-1-1" title="leaf 1-1" is-leaf />
            </a-tree-node>
        </a-directory-tree>
    </div>
</template>

<script>
    import MenuChildrenLayout from './MenuChildrenLayout.vue'

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
            }
        },
        watch:{
            '$store.state.userInfo'(userInfo) {
            }
        },
        components: {MenuChildrenLayout},
        mounted() {
            this.getMenuData();
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
        },
        methods: {
            getMenuData() {
                let menuData = this.$router.options.routes.find((item) => item.path === '/').children[0].children;
                this.menuData = JSON.parse(JSON.stringify(menuData));
                // 模拟数据返回，暂时不以这种展示
                // setTimeout(() => {
                //     this.menuData.push({
                //         name: '用户管理接口',
                //         meta: {icon: 'FileTextOutlined'},
                //         children: [
                //             {
                //                 path: '/doc/view?id=2',
                //                 name: '获取用户信息',
                //                 query: {id: 222}
                //             }, {
                //                 path: '/doc/view?id=3',
                //                 name: '删除用户',
                //                 query: {id: 333}
                //             }
                //         ]
                //     });
                // }, 1000);
            },
            docChecked(val, node) {
                if (node.node.isLeaf) {
                    let dataRef = node.node.dataRef;
                    this.$router.push({path: dataRef.path, query: dataRef.query});
                }
            }
        }
    }
</script>

