<template>
    <a-tabs v-model:activeKey="activePage" closable @tab-click="changePage" style="padding: 5px 10px 0;">
        <a-tab-pane tab="接口说明" key="doc"/>
        <a-tab-pane tab="在线调试" key="debug"/>
    </a-tabs>
</template>

<script>
    import {toRefs, ref, reactive, onMounted} from 'vue';
    import { useRouter } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'

    export default {
        setup() {
            const router = useRouter();
            const store = useStore()
            let activePage = ref('doc');
            onBeforeRouteUpdate(async (to, from) => {
                let path = to.query.path;
                let docInfo = store.state.swaggerTreePathMap[path];
                if (!docInfo) {
                    message.error('没有找到对应的文档');
                    return;
                }
                store.commit('addTableName', {key: to.fullPath, val: docInfo.summary});
            });
            const changePage = () => {

            }
            return {
                activePage,
                changePage,
            };
        },
    };
</script>

