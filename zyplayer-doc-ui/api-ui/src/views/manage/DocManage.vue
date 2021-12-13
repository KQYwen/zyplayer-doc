<template>
	<DocManageList v-if="showView === 'list'" @edit="editDoc"></DocManageList>
	<div v-else>
		<DocManageEdit v-if="showView === 'edit'" @showDocList="showDocList" :doc="docInfo"></DocManageEdit>
	</div>
</template>

<script>
    import { toRefs, ref, reactive, onMounted } from 'vue';
    import DocManageList from "./DocManageList.vue";
    import DocManageEdit from "./DocManageEdit.vue";

    export default {
        components: {DocManageList, DocManageEdit},
	    setup() {
		    let showView = ref('list');
		    let docInfo = ref({});
		    const editDoc = (type, doc) => {
			    docInfo.value = doc;
			    showView.value = type;
			};
		    const showDocList = () => {
			    showView.value = 'list';
			};
            return {
	            showView,
	            docInfo,
				editDoc,
	            showDocList,
            }
        },
    };
</script>
