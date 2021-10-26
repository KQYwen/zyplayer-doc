<template>
    <a-tabs v-model:activeKey="activePage" closable @tab-click="changePage" style="padding: 5px 10px 0;">
        <a-tab-pane tab="接口说明" key="doc">
            {{activePage}}
        </a-tab-pane>
        <a-tab-pane tab="在线调试" key="debug">
            {{activePage}}
        </a-tab-pane>
    </a-tabs>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, onActivated} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'

    export default {
        setup() {
            const route = useRoute();
            const store = useStore();
            let activePage = ref('doc');
            onMounted(() => {
                console.log('DocView onMounted', route.query);
                let path = route.query.path + '.' + route.query.method;
                let docInfo = store.state.swaggerTreePathMap[path];
                if (!docInfo) {
                    message.error('没有找到对应的文档');
                    return;
                }
                store.commit('addTableName', {key: route.fullPath, val: docInfo.summary});
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

