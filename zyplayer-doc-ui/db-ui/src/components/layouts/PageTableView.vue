<template>
    <div>
        <el-tabs v-model="activePage" type="card" closable @tab-click="changePage" @tab-remove="removePageTab" style="padding: 5px 10px 0;">
            <el-tab-pane :label="pageTabNameMap[item.fullPath]||item.name" :name="item.fullPath" v-for="item in pageList"/>
        </el-tabs>
        <keep-alive>
            <router-view :key="$route.fullPath" @initLoadDataList="initLoadDataList" @loadDatasourceList="loadDatasourceList"/>
        </keep-alive>
    </div>
</template>

<script>
    export default {
        name: 'PageTableView',
        components: {},
        data() {
            return {
                pageList: [],
                linkList: [],
                activePage: '',
                multiPage: true,
            }
        },
        computed: {
            pageTabNameMap () {
                return this.$store.state.global.pageTabNameMap;
            }
        },
        created() {
            this.pageList.push(this.$route);
            this.linkList.push(this.$route.fullPath);
            this.activePage = this.$route.fullPath;
        },
        watch: {
            '$route': function (newRoute, oldRoute) {
                this.activePage = newRoute.fullPath;
                if (this.linkList.indexOf(newRoute.fullPath) < 0) {
                    this.linkList.push(newRoute.fullPath);
                    this.pageList.push(newRoute);
                }
            },
            'activePage': function (key) {
                this.$router.push(key)
            },
        },
        methods: {
			initLoadDataList(param) {
                this.$emit('initLoadDataList', param);
            },
			loadDatasourceList() {
                this.$emit('loadDatasourceList');
            },
            changePage(key) {
                this.activePage = key.name;
            },
            editPage(key, action) {
                this[action](key);
            },
            removePageTab(key) {
                if (this.pageList.length === 1) {
                    this.$message.warning('这是最后一页，不能再关闭了啦');
                    return
                }
                this.pageList = this.pageList.filter(item => item.fullPath !== key);
                let index = this.linkList.indexOf(key);
                this.linkList = this.linkList.filter(item => item !== key);
                index = index >= this.linkList.length ? this.linkList.length - 1 : index;
                this.activePage = this.linkList[index];
            },
        }
    }
</script>

<style scoped>

</style>
