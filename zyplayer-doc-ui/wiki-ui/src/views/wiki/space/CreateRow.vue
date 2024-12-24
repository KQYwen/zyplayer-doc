<template>
	<div class="wiki-create-row">
		<a-row>
			<a-col :span="12"></a-col>
			<a-col :span="12" style="text-align: right;">
				<a-button @click="showCreateSpace" :icon="h(PlusOutlined)" type="primary">新建空间</a-button>
			</a-col>
		</a-row>
	</div>
	<CreateSpace ref="createSpaceRef"></CreateSpace>
</template>

<script setup>
import {SwapOutlined, PlusOutlined} from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, defineEmits, h, computed} from 'vue';
import CreateSpace from './CreateSpace.vue'
import {useStoreDisplay} from "@/store/wikiDisplay";
import {useStoreUserData} from "@/store/userData";

let storeUser = useStoreUserData();
let storeDisplay = useStoreDisplay();
onMounted(() => {
});
let createSpaceRef = ref();
const showCreateSpace = () => {
	createSpaceRef.value.show();
}
</script>

<style lang="scss" scoped>
.wiki-create-row {
	margin: 20px 0 10px 0;
}
</style>

