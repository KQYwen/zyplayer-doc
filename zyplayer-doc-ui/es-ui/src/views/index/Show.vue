<template>
    <div class="index-show-vue">
        <el-card style="margin: 10px;">
            <div slot="header" class="clearfix">字段信息</div>
            <div style="padding: 10px;" v-loading="indexMappingListLoading">
                <el-table :data="indexMappingList" stripe border style="width: 100%; margin-bottom: 5px;">
                    <el-table-column prop="name" label="字段名"></el-table-column>
                    <el-table-column prop="type" label="类型"></el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>

<script>
    import global from '../../common/config/global'
    var app;

    export default {
        data() {
            return {
                indexMappingListLoading: false,
                vueQueryParam: {},
                indexMappingList: [],
            };
        },
        beforeRouteUpdate(to, from, next) {
            this.initQueryParam(to);
            next();
        },
        mounted: function () {
            app = this;
            this.initQueryParam(this.$route);
            // 延迟设置展开的目录，edit比app先初始化
            setTimeout(function () {
                //global.vue.$app.initLoadDataList(app.vueQueryParam.host, app.vueQueryParam.dbName);
            }, 500);
        },
        methods: {
            initQueryParam(to) {
                this.indexMappingListLoading = true;
                this.vueQueryParam = to.query;
                this.common.post(this.apilist1.esMappings, this.vueQueryParam, function (json) {
                    var data = json.data || {};
                    var properties = data[app.vueQueryParam.index].sourceAsMap.properties;
                    var propertiesArr = [];
                    for (var propertiesKey in properties) {
                        var propertiesItem = properties[propertiesKey];
                        var item = {
                            name: propertiesKey, type: propertiesItem.type
                        };
                        propertiesArr.push(item);
                    }
                    app.indexMappingList = propertiesArr;
                    app.indexMappingListLoading = false;
                });
            },
        }
    }
</script>
<style>
    .index-show-vue .el-form-item{margin-bottom: 5px;}
    .index-show-vue .edit-table-desc{cursor: pointer; color: #409EFF;}
    .index-show-vue .description{cursor: pointer;}
    .index-show-vue .el-table td, .table-info-vue .el-table th{padding: 5px 0;}
</style>

