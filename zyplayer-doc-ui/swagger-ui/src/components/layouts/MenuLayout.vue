<template>
    <div class="menu-layout">
        <a-menu theme="light" mode="inline" :inline-collapsed="false" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-divider style="margin: 6px 0;"/>
        <div v-show="!collapsed" class="doc-tree">
            <div style="padding: 10px 5px;">
                <a-select placeholder="请选择分组" v-model:value="swaggerDocChoice" @change="swaggerDocChoiceChange" style="width: 100%;">
                    <a-select-option :value="item.url" v-for="item in swaggerResourceList">{{item.name}}</a-select-option>
                </a-select>
            </div>
            <a-directory-tree :showIcon="false" :tree-data="treeData" v-model:expandedKeys="expandedKeys" @select="docChecked">
                <template #title="{ title, isLeaf, method }">
                    <template v-if="isLeaf">
                        <a-tag color="pink" v-if="method === 'get'">get</a-tag>
                        <a-tag color="red" v-else-if="method === 'post'">post</a-tag>
                        <a-tag color="orange" v-else-if="method === 'put'">put</a-tag>
                        <a-tag color="green" v-else-if="method === 'head'">head</a-tag>
                        <a-tag color="cyan" v-else-if="method === 'patch'">patch</a-tag>
                        <a-tag color="blue" v-else-if="method === 'delete'">delete</a-tag>
                        <a-tag color="purple" v-else-if="method === 'options'">options</a-tag>
                        <a-tag color="purple" v-else-if="method === 'trace'">trace</a-tag>
                    </template>
                    {{title}}
                </template>
            </a-directory-tree>
        </div>
    </div>
</template>

<script>
    import MenuChildrenLayout from './MenuChildrenLayout.vue'
    import {customApi} from '../../api'
    import {createTreeViewByTag, getTreeDataForTag} from '../../store/SwaggerDocUtil'

    export default {
        name: 'MenuLayout',
        props: {
            collapsed: {
                type: Boolean,
                default: false
            },
        },
        data() {
            return {
                menuData: [],
                selectedKeys: [],
                openKeys: [],
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
                                    {title: '/getUserInfo', key: '0-0-0-0', isLeaf: true, query: {path: '/getUserInfo'}},
                                    {title: '/deleteUserInfo', key: '0-0-0-1', isLeaf: true, query: {path: '/deleteUserInfo'}},
                                    {title: '/updateUserInfo', key: '0-0-0-2', isLeaf: true, query: {path: '/updateUserInfo'}},
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
            },
            collapsed(x) {
                console.log(x, this.collapsed)
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
                    this.$router.push({path: '/doc/view', query: dataRef.query});
                }
            },
            getSwaggerResourceList() {
                customApi.get('./swagger-resources').then(res => {
                    if (res instanceof Array) {
                        this.swaggerResourceList = res || [];
                        if (this.swaggerResourceList.length > 0) {
                            this.swaggerDocChoice = this.swaggerResourceList[0].url;
                            this.swaggerDocChoiceChange();
                        }
                    } else {
                        this.$message.error('获取文档列表请求失败');
                    }
                });
            },
            swaggerDocChoiceChange() {
                this.loadV2Doc(this.swaggerDocChoice);
            },
            loadV2Doc(url) {
                customApi.get(url).then(res => {
                    let v2Doc = this.toJsonObj(res);
                    if (typeof v2Doc !== 'object') {
                        this.$message.error('获取文档数据请求失败');
                    }
                    this.$store.commit('setSwaggerDoc', v2Doc);
                    let metaInfo = {url};
                    let treeData = createTreeViewByTag(v2Doc);
                    this.treeData = getTreeDataForTag(v2Doc, treeData.pathIndex, metaInfo);
                    this.$store.commit('setSwaggerTreePathMap', treeData.treePathDataMap);
                });
            },
            toJsonObj(value) {
                if (typeof value !== 'string') {
                    return value;
                }
                try {
                    return JSON.parse(value);
                } catch (e) {
                    try {
                        return eval('(' + value + ')');// 处理变态的单双引号共存字符串
                    } catch (e) {
                        return value || undefined;
                    }
                }
            }
        }
    }
</script>

<style>
    .doc-tree{padding: 10px 4px;}
    .doc-tree .ant-tree-switcher{width: 15px;}
    .doc-tree .ant-tree-switcher-noop{width: 0;}
    .doc-tree .ant-tag{margin-right: 0;}
</style>
