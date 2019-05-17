<template>
    <div>欢迎使用wiki文档管理工具</div>
</template>

<script>
    export default {
        data() {
            return {};
        },
        mounted: function () {
            // this.getUserInfo();
        },
        methods: {
            getUserInfo: function () {
                this.common.post(this.apilist1.getUserInfo, {}, function (json) {});
            }
        }
    }
</script>
<style>

</style>

