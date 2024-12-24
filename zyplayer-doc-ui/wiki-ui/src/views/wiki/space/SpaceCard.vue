<template>
	<div @click="openSpace(space)" class="wiki-space-card can-click black-bg">
		<div class="top-badge external" v-if="space.openDoc === 1">互联网公开</div>
		<div class="top-badge open" v-else-if="space.type === 1">企业公开</div>
		<div class="img-box">
			<img src="@/assets/img/space-bg-1.png"/>
		</div>
		<div class="title ellipsis-multi" :title="space.name">{{ space.name }}</div>
		<div class="desc ellipsis-multi" :title="space.spaceExplain">{{ space.spaceExplain }}</div>
		<div v-if="space.isManager" @click.stop="openSpaceSetting(space)" class="open-space-box">
			<el-icon style="vertical-align: middle;"><ElIconSetting/></el-icon> 空间设置
		</div>
		<div v-else @click.stop="openSpace(space)" class="open-space-box">
			进入空间 <el-icon style="vertical-align: middle;"><ElIconArrowRight/></el-icon>
		</div>
	</div>
</template>

<script setup>
import {Setting as ElIconSetting, ArrowRight as ElIconArrowRight} from '@element-plus/icons-vue'
import {toRefs, ref, reactive, onMounted, watch, defineProps, defineEmits, h, computed} from 'vue';
import { message } from 'ant-design-vue';
import {useRouter, useRoute} from "vue-router";
import pageApi from "@/assets/api/page";
import {useStorePageData} from "@/store/pageData";

const router = useRouter();
let storePage = useStorePageData();

const props = defineProps({
	space: {type: Object},
});
const openSpace = (row) => {
	storePage.currentSpace = {};
	router.push({path: `/view/${row.id}`});
}
const openSpaceSetting = (row) => {
	storePage.currentSpace = {};
	router.push({path: `/view/setting/${row.id}`});
}
</script>

<style lang="scss" scoped>
.wiki-space-card {
	position: relative;
	width: 140px;
	height: 200px;
	margin: 0 20px 20px 0;
	cursor: pointer;
	padding: 16px;
	flex-shrink: 0;

	.top-badge {
		position: absolute;
		border-radius: 8px 0 8px 0;
		left: 0;
		top: 0;
		z-index: 1;
		background: #1384e5;
		padding: 1px 10px;
		font-size: 12px;
		color: #fff;
	}

	.top-badge.open {
		background: #67C23A;
	}

	.top-badge.external {
		background: #409EFF;
	}

	.img-box {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		-webkit-transition: all .3s ease-out;
		-o-transition: all .3s ease-out;
		transition: all .3s ease-out;
		-webkit-transform-origin: center center;
		-ms-transform-origin: center center;
		transform-origin: center center;

		img {
			width: 100%;
			height: 100%;
			border-radius: 8px;
		}

		img.hide-compute-img {
			width: 1px;
			height: 1px;
			opacity: 0;
			position: absolute;
		}
	}

	.ellipsis-multi {
		display: -webkit-box;
		overflow: hidden;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
		word-break: break-word;
	}

	.title {
		position: relative;
		font-weight: 600;
		font-size: 16px;
		line-height: 24px;
		margin-top: 12px;
		letter-spacing: normal;
	}

	.desc {
		position: relative;
		font-size: 12px;
		line-height: 18px;
		font-weight: 400;
		margin: 6px 0;
		letter-spacing: .5px;
	}

	.open-space-box {
		display: none;
		text-align: center;
		position: absolute;
		bottom: 0;
		left: 0;
		width: 100%;
		padding: 8px 0;
		background: #00000055;
		color: #fff;
		font-size: 13px;
		vertical-align: middle;
		transition: all .3s ease-out;
		border-radius: 0 0 8px 8px;
	}
}

.wiki-space-card.white-bg {
	color: #000;
}

.wiki-space-card.black-bg {
	color: #fff;
}

.wiki-space-card.can-click:hover {
	cursor: pointer;
	box-shadow: var(--el-box-shadow-light);

	.img-box:before {
		content: "";
		position: absolute;
		-webkit-box-sizing: border-box;
		box-sizing: border-box;
		-webkit-box-shadow: 0 0 0 2px #0cabed;
		box-shadow: 0 0 0 2px #0cabed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		pointer-events: none;
		border-radius: 8px;
	}

	.open-space-box {
		display: block;
	}
}
</style>

