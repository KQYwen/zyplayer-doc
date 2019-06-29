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
                        <a target="_blank" href="http://doc.zyplayer.com/zyplayer-doc-manage/open-wiki.html?pageId=76&space=23f3f59a60824d21af9f7c3bbc9bc3cb"><i class="el-icon-info" style="color: #999;"></i></a>
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
            };
        },
        mounted: function () {
            app = this;
        },
        methods: {
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

