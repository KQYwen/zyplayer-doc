<template>
	<a-card>
		<a-form :label-col="{span: 4}" :wrapper-col="{span: 20}" v-if="swaggerDocInfo">
			<a-form-item label="标题">{{swaggerDocInfo.title}}</a-form-item>
			<a-form-item label="版本">{{swaggerDocInfo.version}}</a-form-item>
			<a-form-item label="作者" v-if="swaggerDocInfo.contact">
				<template v-if="swaggerDocInfo.contact.name">
					{{swaggerDocInfo.contact.name}}
				</template>
				<template v-if="swaggerDocInfo.contact.email">
					<a-divider type="vertical" />{{swaggerDocInfo.contact.email}}
				</template>
				<template v-if="swaggerDocInfo.contact.url">
					<a-divider type="vertical" />
					<a :href="swaggerDocInfo.contact.url" target="_blank">{{swaggerDocInfo.contact.url}}</a>
				</template>
			</a-form-item>
			<a-form-item label="host">{{swaggerDoc.host}}</a-form-item>
			<a-form-item label="许可证" v-if="swaggerDocInfo.license">
				<a :href="swaggerDocInfo.license.url" target="_blank">{{swaggerDocInfo.license.name}}</a>
			</a-form-item>
			<a-form-item label="服务条款" v-if="swaggerDocInfo.termsOfService">
				<a :href="swaggerDocInfo.termsOfService" target="_blank">{{swaggerDocInfo.termsOfService}}</a>
			</a-form-item>
			<a-form-item label="文档说明">
				<div class="markdown-body" v-html="getDescription(swaggerDocInfo.description)"></div>
			</a-form-item>
			<a-form-item label="接口统计">
				<a-row :gutter="[16, 16]">
					<template v-for="method in ['get', 'post', 'put', 'delete', 'head', 'patch', 'options', 'trace', 'total']">
						<a-col :span="6" v-if="swaggerMethodStatistic[method]">
							<a-card size="small">
								<a-statistic :title="method === 'total'?'总计':method.toUpperCase() + '方法'" :value="swaggerMethodStatistic[method]" suffix="个"></a-statistic>
							</a-card>
						</a-col>
					</template>
				</a-row>
			</a-form-item>
		</a-form>
		<div v-else style="text-align: center;">暂无文档信息，请先选择文档</div>
	</a-card>
</template>

<script>
	import { toRefs, ref, reactive, onMounted, computed } from 'vue';
	import {useStore} from 'vuex';
	import {markdownIt} from 'mavon-editor'
	import 'mavon-editor/dist/markdown/github-markdown.min.css'
	import 'mavon-editor/dist/css/index.css'

	export default {
		setup() {
			const store = useStore();
			const swaggerDoc = computed(() => store.state.swaggerDoc);
			const swaggerDocInfo = computed(() => store.state.swaggerDoc.info);
			const swaggerMethodStatistic = computed(() => store.state.swaggerMethodStatistic);
			const getDescription = description => {
				return markdownIt.render(description || '');
			};
			return {
				swaggerDoc,
				swaggerDocInfo,
				swaggerMethodStatistic,
				getDescription,
			};
		},
	};
</script>
