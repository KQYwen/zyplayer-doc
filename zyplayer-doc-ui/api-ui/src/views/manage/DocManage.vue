<template>
	<DocManageList v-if="showView === 'list'" @showMembers="showMembers"></DocManageList>
	<div v-else>
		<DocManageMembers v-if="showView === 'members'" @showDocList="showDocList" :doc="docInfo"></DocManageMembers>
	</div>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import DocManageList from "./DocManageList.vue";
    import DocManageMembers from "./DocManageMembers.vue";

    export default {
        components: {DocManageList, DocManageMembers},
	    setup() {
		    let showView = ref('list');
		    let docInfo = ref({});
		    const showMembers = (doc) => {
			    docInfo.value = doc;
			    showView.value = 'members';
			};
		    const showDocList = () => {
			    showView.value = 'list';
			};
            return {
	            showView,
	            docInfo,
	            showMembers,
	            showDocList,
            }
        },
    };
</script>
