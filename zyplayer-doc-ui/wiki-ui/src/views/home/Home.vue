<template>
    <div style="padding: 10px;">
        <div style="max-width: 800px;margin: 0 auto;">
            <el-select v-model="searchParam.newsType" v-on:change="getSpacePageNews" placeholder="请选择查看方式" style="float: right;z-index: 1;">
                <el-option :label="val" :value="index+1" v-for="(val, index) in newsTypes"></el-option>
            </el-select>
            <el-tabs value="first">
                <el-tab-pane :label="newsTypes[searchParam.newsType-1]" name="first">
                    <div v-if="spacePageNews.length <= 0" class="empty-news">暂无数据</div>
                    <div v-else class="line-box" v-for="item in spacePageNews">
                        <div class="line-title">
                            <span class="text-link">{{item.createUserName}}</span> 发布于 <span class="text-link">{{item.spaceName}}</span>
                        </div>
                        <div class="page-preview-box">
                            <div class="page-preview-title" v-on:click="showPageDetail(item)" v-html="item.pageTitle"></div>
                            <div class="page-preview-content" v-html="item.previewContent"></div>
                            <div>
                                <span><img src="../../assets/img/zan.png" class="zan-img"> {{item.zanNum}}　</span>
                                <span><i class="el-icon-view view-img"></i> {{item.viewNum}}　</span>
                                <span>{{item.updateTime||item.createTime}}</span>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
            <div class="page-info-box">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :page-sizes="[20, 50, 100]"
                        :page-size="20"
                        :current-page="searchParam.pageNum"
                        layout="prev, pager, next, jumper, sizes, total"
                        :total="totalCount"
                        >
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import toast from '../../common/lib/common/toast'
    import global from '../../common/config/global'

    var app;

    export default {
        data() {
            return {
                totalCount: 0,
                searchParam: {
                    spaceId: '',
                    newsType: 1,
                    pageNum: 1,
                    pageSize: 20,
                },
                spacePageNews:[],
                newsTypes:["最近更新", "最新创建", "查看最多", "点赞最多", "查看+点赞最多"],
            };
        },
        beforeRouteUpdate(to, from, next) {
            this.initQueryParam(to);
            next();
        },
        mounted: function () {
            this.initQueryParam(this.$route);
            app = this;
        },
        methods: {
            getSpacePageNews() {
                this.common.post(this.apilist1.pageNews, this.searchParam, function (json) {
                    app.spacePageNews = json.data || [];
                    app.totalCount = json.total;
                });
            },
            handleSizeChange(val) {
                this.searchParam.pageSize = val;
                this.getSpacePageNews();
            },
            showPageDetail(row) {
                this.nowClickPath = {spaceId: row.spaceId, pageId: row.pageId};
                this.$router.push({path: '/page/show', query: this.nowClickPath});
            },
            handleCurrentChange(val) {
                this.searchParam.pageNum = val;
                this.getSpacePageNews();
            },
            initQueryParam(to) {
                this.searchParam = {
                    spaceId: to.query.spaceId,
                    newsType: 1,
                    pageNum: 1,
                    pageSize: 20,
                };
                if (!!this.searchParam.spaceId) {
                    this.getSpacePageNews();
                }
            },
        }
    }
</script>
<style>
    .empty-news{text-align: center;padding: 100px;}

    .text-link {
        color: #444;
        /*cursor: pointer;*/
        /*font-weight: bold;*/
    }
    .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
    .line-title{font-size: 14px;}
    .page-preview-box{}
    .page-preview-title{cursor: pointer;font-size: 20px;margin: 10px 0 5px 0;color: #3a8ee6;}
    .page-preview-content{font-size: 16px;margin-bottom: 5px;}
    .zan-img{vertical-align: middle;margin-top: -3px;}
    .view-img{font-size: 16px;color: #666;}

    .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

