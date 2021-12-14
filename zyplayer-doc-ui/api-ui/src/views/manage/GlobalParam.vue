<template>
	<EditGlobalParam :dynamic-param="{docId: 0}"></EditGlobalParam>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import EditGlobalParam from "./components/EditGlobalParam.vue";

    export default {
	    components: {EditGlobalParam},
        setup() {
            return {
            };
        },
    };
</script>
