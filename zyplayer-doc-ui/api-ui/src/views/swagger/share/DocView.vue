<template>
    <template v-if="isLoadSuccess">
        <DocContent :docInfoShow="docInfoShow" :requestParamList="requestParamList" :responseParamList="responseParamList"></DocContent>
    </template>
    <a-spin v-else tip="文档数据加载中...">
        <div style="padding: 20px 0;height: 100px;"></div>
    </a-spin>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
    import swaggerAnalysis from '../../../assets/core/SwaggerAnalysis.js'
    import DocContent from '../docView/DocContent.vue'
    import {markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

    export default {
        components: { DocContent },
        setup() {
            const route = useRoute();
            const store = useStore();
            let activePage = ref('doc');
            let requestParamList = ref([]);
            let responseParamList = ref([]);
            let docInfoShow = ref({
                url: '',
                description: '',
                method: '',
                consumes: '',
                produces: '',
            });
            let isLoadSuccess = ref(false);
            let intervalNum = 0;
            let intervalTimer = undefined;
            const initLoadDocument = () => {
                let path = route.query.path + '.' + route.query.method;
                if (Object.keys(store.state.swaggerUrlMethodMap).length <= 0) {
                    console.log('文档尚未加载，等待加载完成');
                    if (!intervalTimer) {
                        intervalTimer = setInterval(() => {
                            if (isLoadSuccess.value || intervalNum++ > 50) {
                                clearInterval(intervalTimer);
                                return;
                            }
                            if (Object.keys(store.state.swaggerUrlMethodMap).length > 0) {
                                console.log('文档内容改变，重新加载文档');
                                initLoadDocument();
                            }
                        }, 1000);
                    }
                    return;
                }
                let docInfo = store.state.swaggerUrlMethodMap[path];
                if (!docInfo) {
                    message.error('没有找到对应的文档');
                    return;
                }
                isLoadSuccess.value = true;
                store.commit('addTableName', {key: route.fullPath, val: docInfo.summary});
                // 解析接口说明
                let consumes = '', produces = '';
                if (docInfo.consumes && docInfo.consumes.length > 0) {
                    consumes = docInfo.consumes.join(' ');
                }
                if (docInfo.produces && docInfo.produces.length > 0) {
                    produces = docInfo.produces.join(' ');
                }
                let description = markdownIt.render(docInfo.description || docInfo.summary || '');
                docInfoShow.value = {
                    url: docInfo.url,
                    description: description,
                    method: docInfo.method || '',
                    consumes: consumes,
                    produces: produces,
                };
                // 解析请求参数
                let definitionsDataMap = store.state.swaggerDefinitions;
                requestParamList.value = swaggerAnalysis.getRequestParamList(docInfo.parameters, definitionsDataMap);
                responseParamList.value = swaggerAnalysis.getResponseParamList(docInfo.responses, definitionsDataMap);
            }
            onMounted(() => {
                initLoadDocument();
            });
            const changePage = () => {
            }
            return {
                docInfoShow,
                activePage,
                changePage,
                isLoadSuccess,
                requestParamList,
                responseParamList,
            };
        },
    };
</script>
