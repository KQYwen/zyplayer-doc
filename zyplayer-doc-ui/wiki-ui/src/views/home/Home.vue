<template>
    <div style="padding: 10px;" class="home-vue">
        <div style="max-width: 800px;margin: 0 auto;">
            <el-select v-model="searchParam.newsType" v-on:change="getSpacePageNews" placeholder="请选择查看方式" style="float: right;z-index: 1;">
                <el-option :label="item.val" :value="item.key" :key="item.key" v-for="item in newsTypesArr"></el-option>
            </el-select>
            <el-tabs value="first">
                <el-tab-pane :label="newsTypesMap[searchParam.newsType]" name="first">
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
	import pageApi from '../../common/api/page'

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
				spacePageNews: [],
				// 列表类型
				newsTypesArr: [
					{key: 1, val: '最近更新'}, {key: 2, val: '最新创建'}, {key: 3, val: '查看最多'},
					{key: 4, val: '点赞最多'}, {key: 5, val: '查看+点赞最多'}
				],
				newsTypesMap: {},
            };
        },
        beforeRouteUpdate(to, from, next) {
            this.initQueryParam(to);
            next();
        },
        mounted: function () {
            this.initQueryParam(this.$route);
        },
        methods: {
            getSpacePageNews() {
				pageApi.pageNews(this.searchParam).then(json => {
					this.spacePageNews = json.data || [];
					this.totalCount = json.total;
				});
            },
            handleSizeChange(val) {
                this.searchParam.pageSize = val;
                this.getSpacePageNews();
            },
            showPageDetail(row) {
                this.nowClickPath = {pageId: row.pageId};
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
				this.newsTypesMap = {};
				this.newsTypesArr.forEach(item => this.newsTypesMap[item.key] = item.val);
            },
        }
    }
</script>
<style>
    .home-vue .empty-news{text-align: center;padding: 100px;}

    .home-vue .text-link {
        color: #444;
        /*cursor: pointer;*/
        /*font-weight: bold;*/
    }
    .home-vue .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
    .home-vue .line-title{font-size: 14px;}
    .home-vue .page-preview-box{}
    .home-vue .page-preview-title{cursor: pointer;font-size: 20px;margin: 10px 0 5px 0;color: #3a8ee6;}
    .home-vue .page-preview-content{font-size: 16px;margin-bottom: 5px;}
    .home-vue .zan-img{vertical-align: middle;margin-top: -3px;}
    .home-vue .view-img{font-size: 16px;color: #666;}

    .home-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

