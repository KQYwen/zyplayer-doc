<template>
    <div v-if="apiDoc.shareInstruction">
        <a-row>
            <a-col :xs="0" :sm="4" :md="4" :lg="6" :xl="6" v-if="navigationList.length > 0">
                <Navigation ref="navigationRef" :heading="navigationList"></Navigation>
            </a-col>
            <a-col :xs="24" :sm="navigationList.length > 0?20:24" :md="navigationList.length > 0?20:24" :lg="navigationList.length > 0?18:24" :xl="navigationList.length > 0?18:24">
                <div class="markdown-body share-instruction" v-html="markdownToHtml(apiDoc.shareInstruction)" style="margin: 0 auto;max-width: 1000px;"></div>
            </a-col>
        </a-row>
    </div>
    <div v-else style="text-align: center;">欢迎访问开放API文档</div>
</template>

<script>
    import {computed, onMounted, ref, watch} from 'vue';
    import {useStore} from 'vuex';
    import {markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import Navigation from './components/Navigation.vue'

    export default {
        components: {Navigation},
        setup() {
            const store = useStore();
            const apiDoc = computed(() => store.state.apiDoc);
            let navigationRef = ref();
            watch(store.getters.getApiDoc, () => {
                setTimeout(() => {
                    createNavigationHeading('.share-instruction');
                }, 100);
            });
            const markdownToHtml = desc => {
                return markdownIt.render(desc || '');
            }
            let navigationList = ref([]);
            const createNavigationHeading = (domClass) => {
                if (!document.querySelector(domClass)) {
                    return [];
                }
                let headNodeArr = document.querySelector(domClass).querySelectorAll('h1,h2,h3,h4,h5,h6');
                if (headNodeArr.length <= 0) {
                    return [];
                }
                let headArr = [];
                headNodeArr.forEach(node => {
                    let text = node.innerHTML.replace(/^\s+/g, '').replace(/\s+$/g, '').replace(/<\/?[^>]+(>|$)/g, '');
                    headArr.push({
                        node: node,
                        level: parseInt(node.tagName.replace(/[h]/i, ''), 10),
                        text: text
                    });
                });
                navigationList.value =  headArr;
            };
            onMounted(() => {
            });
            return {
                apiDoc,
                navigationRef,
                navigationList,
                markdownToHtml,
            };
        },
    };
</script>
<style>
    .share-instruction{padding: 0 10px;}
</style>
