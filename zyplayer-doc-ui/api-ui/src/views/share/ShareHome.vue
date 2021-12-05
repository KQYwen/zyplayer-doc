<template>
    <div v-if="apiDoc.shareInstruction">
        <div class="markdown-body" v-html="markdownToHtml(apiDoc.shareInstruction)" style="margin: 0 auto;max-width: 1000px;"></div>
    </div>
    <div v-else style="text-align: center;">欢迎访问开放API文档</div>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch, computed} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
    import {markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import swaggerAnalysis from "../../assets/core/SwaggerAnalysis";

    export default {
        setup() {
            const store = useStore();
            const apiDoc = computed(() => store.state.apiDoc);
            onMounted(() => {
            });
            const markdownToHtml = desc => {
                return markdownIt.render(desc || '');
            }
            return {
                apiDoc,
                markdownToHtml,
            };
        },
    };
</script>
