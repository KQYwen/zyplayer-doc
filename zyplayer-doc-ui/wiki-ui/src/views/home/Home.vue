<template>
    <div v-on:click="sendMsgToParent">
        <div style="margin-top: 30px;color: #666; text-align: center; font-size: 30px;">欢迎使用在线文档</div>
        <div style="margin-top: 30px;color: #666; text-align: center;">
            {{nowSpaceShow.name}}
            <span v-show="nowSpaceShow.spaceExplain && nowSpaceShow.spaceExplain.length > 0"> · {{nowSpaceShow.spaceExplain}}</span>
        </div>
    </div>
</template>

<script>
    import global from '../../common/config/global'

    export default {
        data() {
            return {
                nowSpaceShow: {
                    name: '',
                    spaceExplain: '',
                }
            };
        },
        mounted: function () {
            // this.getUserInfo();
        },
        methods: {
            getUserInfo: function () {
                // this.common.post(this.apilist1.getUserInfo, {}, function (json) {});
            },
            sendMsgToParent: function () {
                global.vue.$app.sendMsgToParent("xxx");
            }
        }
    }
</script>
<style>

</style>

