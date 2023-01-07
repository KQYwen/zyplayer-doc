<template>
	<van-collapse v-model="pageSelect">
		<template v-for="page in pageList">
			<div v-if="!page.children" @click="pageSelectChange(page.id)" class="van-cell van-cell--clickable">
				{{ page.name }}
			</div>
			<van-collapse-item :name="page.id" v-else>
				<template v-slot:title>
					<span @click="pageSelectChange(page.id)">{{ page.name }}</span>
				</template>
				<page-tree :page-list="page.children" @pageChange="pageSelectChange"></page-tree>
			</van-collapse-item>
		</template>
	</van-collapse>
</template>

<script setup>
import {onBeforeUnmount, ref, onMounted, watch, defineProps, nextTick, defineEmits, defineExpose, computed} from 'vue';

let emit = defineEmits(['pageChange']);

const props = defineProps({
	pageList: Array,
});
const pageSelectChange = (value) => {
	emit('pageChange', value);
}
let pageSelect = ref([]);
</script>
