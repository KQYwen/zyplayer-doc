<template>
    <a-textarea placeholder="" v-model:value="bodyRowParam" :auto-size="{ minRows: 15, maxRows: 15 }"></a-textarea>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import {CloseOutlined, UploadOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

    export default {
        props: {
            paramList: {
                type: Array,
                required: true
            },
        },
        components: {
            CloseOutlined, UploadOutlined
        },
        emits: [],
        setup(props, { attrs, slots, emit, expose}) {
            let paramList = props.paramList;
            let bodyParamObj = {};
            let getChildren = paramObj => {
                if (paramObj.children) {
                    let bodyParamObj = {};
                    paramObj.children.forEach(item => {
                        bodyParamObj[item.name] = getChildren(item);
                    });
                    return bodyParamObj;
                }
                return '';
            }
            if (paramList.length === 1) {
                bodyParamObj = getChildren(paramList[0]);
            } else if (paramList.length > 1) {
                paramList.forEach(item => {
                    bodyParamObj[item.name] = getChildren(item);
                });
            }
            let bodyRowParam = ref(JSON.stringify(bodyParamObj, null, 4));
            return {
                bodyRowParam,
            };
        },
    };
</script>
