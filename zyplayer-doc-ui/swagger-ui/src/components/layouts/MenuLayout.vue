<template>
    <div class="menu-layout">
        <a-menu theme="light" mode="inline" :inline-collapsed="false" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
            <menu-children-layout :menuItem="menuItem" v-for="menuItem in menuData"></menu-children-layout>
        </a-menu>
        <a-divider style="margin: 6px 0;"/>
        <div v-show="!collapsed" class="doc-tree">
            <a-spin tip="加载中..." :spinning="treeDataLoading">
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
            </a-spin>
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
                treeDataLoading: false,
                treeData: [],
                expandedKeys: [],
                swaggerResourceList: [],
                swaggerDocChoice: undefined,
            }
        },
        watch:{
            '$store.state.docChangedNum'() {
                this.getSwaggerResourceList();
            },
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
                        if (this.swaggerResourceList.length > 0 && !this.swaggerDocChoice) {
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
                this.treeDataLoading = true;
                customApi.get(url).then(res => {
                    let v2Doc = this.toJsonObj(res);
                    if (typeof v2Doc !== 'object' || !v2Doc.swagger) {
                        this.$message.error('获取文档数据请求失败');
                        this.treeDataLoading = false;
                        return;
                    }
                    this.$store.commit('setSwaggerDoc', v2Doc);
                    let metaInfo = {url};
                    let treeData = createTreeViewByTag(v2Doc);
                    this.treeData = getTreeDataForTag(v2Doc, treeData.pathIndex, metaInfo);
                    this.$store.commit('setSwaggerTreePathMap', treeData.treePathDataMap);
                    setTimeout(() => this.treeDataLoading = false, 300);
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
