<template>
	<div class="wiki-header-view">
		<a-row>
			<a-col :span="18">
				<span class="logo-box">
					<span class="system-name">文档管理系统</span>
					<el-divider direction="vertical" style="margin: 0 10px;"/>
					<span class="company-name">开源版</span>
				</span>
			</a-col>
			<a-col :span="6" style="text-align: right;">
				<UserHead/>
			</a-col>
		</a-row>
	</div>
</template>

<script setup>
import {toRefs, ref, reactive, onMounted, watch, defineEmits, computed} from 'vue';
import {useRouter, useRoute} from "vue-router";
import { message } from 'ant-design-vue';
import UserHead from './UserHead.vue'

let router = useRouter();
onMounted(() => {
});
</script>

<style scoped lang="scss">
.wiki-header-view {
	padding: 0 20px;
	height: 60px;
	line-height: 60px;
	background: #2876d7;

	.logo-box {
		.image {
			height: 41px;
			vertical-align: middle;
			margin-right: 10px;
		}

		.system-name {
			font-size: 21px;
			color: #fff;
			vertical-align: middle;
			margin-top: -2px;
			display: inline-block;
		}

		.company-name {
			font-size: 16px;
			color: #fff;
			vertical-align: middle;
			margin-top: -2px;
			display: inline-block;
		}
	}
}
</style>
