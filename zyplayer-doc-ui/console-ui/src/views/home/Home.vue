<template>
    <div style="padding: 10px;">
        <div style="max-width: 800px;margin: 20px auto;">
            <div style="text-align: center;">欢迎使用ヾ(๑╹◡╹)ﾉ" - 今天也要加油鸭</div>
            <div style="padding: 20px;">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>吾家产品线</span>
                    </div>
                    <div class="product-list">
                        <div class="item" v-on:click="jumpToDocPage('doc-swagger')">
                            <div class="logo-text text1">swagger</div>
                            <div>swagger文档</div>
                        </div>
                        <div class="item" v-on:click="jumpToDocPage('doc-db')">
                            <div class="logo-text text2">DB</div>
                            <div>数据库文档</div>
                        </div>
                        <div class="item" v-on:click="jumpToDocPage('doc-wiki')">
                            <div class="logo-text text3">WIKI</div>
                            <div>WIKI文档</div>
                        </div>
                        <div class="item" v-on:click="jumpToDocPage('doc-dubbo')">
                            <div class="logo-img"><img src="../../assets/img/dubbo.png"></div>
                            <div>dubbo文档</div>
                        </div>
                        <el-tooltip effect="dark" content="不成熟，欢迎完善" placement="top-start">
                            <div class="item disabled">
                                <div class="logo-text text4">GRPC</div>
                                <div>GRPC文档</div>
                            </div>
                        </el-tooltip>
                    </div>
                </el-card>
            </div>
            <div style="padding: 20px;">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>二方库-集成代理版</span>
                        <a target="_blank" href="http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/show?spaceId=1&pageId=76"><i class="el-icon-info" style="color: #999;"></i></a>
                    </div>
                    <div class="product-list">
                        <el-tooltip effect="dark" content="swagger的原生官方文档" placement="top-start">
                            <div class="item" v-on:click="jumpToDocPage('swagger-ui.html')">
                                <div class="logo-text text1">swagger</div>
                                <div>原生文档</div>
                            </div>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="swagger-bootstrap-ui文档" placement="top-start">
                            <div class="item" v-on:click="jumpToDocPage('doc.html')">
                                <div class="logo-text text2">swagger</div>
                                <div>二方文档</div>
                            </div>
                        </el-tooltip>
                    </div>
                </el-card>
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
            jumpToDocPage(val) {
                window.open(val);
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
    .product-list{text-align: left;}
    .product-list .item{
        text-align: center;display: inline-block;padding: 10px;border-radius: 5px;cursor: pointer;
        width: 110px; height: 100px;color: #666;
    }
    .product-list .item:hover{background: #ddd;}
    .product-list .item.disabled{background: #fff;cursor: auto;}
    .product-list .item.disabled .logo-text{background: #909399;}
    .product-list .item .logo-text{
        width: 80px; height: 80px;line-height: 80px;text-align: center; color: #fff;
        margin: 0 auto;background: #67C23A; border-radius: 50%;overflow: hidden;
        font-weight: bold;
    }
    .product-list .item .logo-text.text1{background: #67C23A;}
    .product-list .item .logo-text.text2{background: #E6A23C;}
    .product-list .item .logo-text.text3{background: #F56C6C;}
    .product-list .item .logo-img{width: 80px; height: 80px;margin: 0 auto;}
    .product-list .item .logo-img img{width: 65px; height: 65px; margin: 7px;}
</style>

