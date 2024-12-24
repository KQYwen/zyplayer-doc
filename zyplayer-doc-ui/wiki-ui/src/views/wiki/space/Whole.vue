<template>
	<div class="search-space-box">
		<a-input v-model:value="searchValue" @input="searchSpaceInput" class="search-space-input" placeholder="搜索空间" clearable style="width: 250px;">
			<template #prefix>
				<SearchOutlined style="color: #aaa;"/>
			</template>
		</a-input>
	</div>
	<div v-if="storeSpaceData.spaceList.length <= 0" class="wiki-whole-empty-box">
		<el-empty description="暂无空间"/>
	</div>
	<div v-else class="wiki-whole-box">
		<SpaceCard :space="item" v-for="item in storeSpaceData.spaceList"></SpaceCard>
	</div>
</template>

<script setup>
import {SearchOutlined, SettingOutlined} from '@ant-design/icons-vue';
import {toRefs, ref, reactive, onMounted, watch, h, defineProps, defineEmits, computed} from 'vue';
import SpaceCard from './SpaceCard.vue'
import {useStoreSpaceData} from "@/store/spaceData";

let storeSpaceData = useStoreSpaceData();
onMounted(() => {
});
let searchValue = ref('');
const searchSpaceInput = () => {
	if (!searchValue.value) {
		storeSpaceData.spaceList = storeSpaceData.wholeSpaceList;
	} else {
		let searchText = searchValue.value.toLowerCase();
		storeSpaceData.spaceList = storeSpaceData.wholeSpaceList.filter(item => item.searchText.indexOf(searchText) >= 0);
	}
}
</script>

<style lang="scss">
.search-space-box {
	width: 100%;
	background: #fff;
	padding: 20px;
	box-sizing: border-box;
	margin-bottom: 20px;
	border-bottom: 1px solid #f0f0f0;
	text-align: right;

	.search-space-input {
		max-width: 300px;
	}
}

.wiki-whole-box {
	padding: 0 20px;
	display: flex;
	-webkit-box-flex: 1;
	-ms-flex: 1;
	flex: 1;
	-ms-flex-wrap: wrap;
	flex-wrap: wrap;
	-ms-flex-line-pack: start;
	align-content: flex-start;
	-webkit-box-orient: horizontal;
	-webkit-box-direction: normal;
	-ms-flex-direction: row;
	flex-direction: row;
}
</style>
