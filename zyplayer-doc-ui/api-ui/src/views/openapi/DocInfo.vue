<template>
	<a-card>
		<a-form :label-col="{span: 4}" :wrapper-col="{span: 20}" v-if="openApiDocInfo">
			<a-form-item label="标题">{{openApiDocInfo.title}}</a-form-item>
			<a-form-item label="版本">{{openApiDocInfo.version}}</a-form-item>
			<a-form-item label="作者" v-if="openApiDocInfo.contact">
				<template v-if="openApiDocInfo.contact.name">
					{{openApiDocInfo.contact.name}}
				</template>
				<template v-if="openApiDocInfo.contact.email">
					<a-divider type="vertical" />{{openApiDocInfo.contact.email}}
				</template>
				<template v-if="openApiDocInfo.contact.url">
					<a-divider type="vertical" />
					<a :href="openApiDocInfo.contact.url" target="_blank">{{openApiDocInfo.contact.url}}</a>
				</template>
			</a-form-item>
			<a-form-item label="host">{{openApiDoc.host}}</a-form-item>
			<a-form-item label="许可证" v-if="openApiDocInfo.license">
				<a :href="openApiDocInfo.license.url" target="_blank">{{openApiDocInfo.license.name}}</a>
			</a-form-item>
			<a-form-item label="服务条款" v-if="openApiDocInfo.termsOfService">
				<a :href="openApiDocInfo.termsOfService" target="_blank">{{openApiDocInfo.termsOfService}}</a>
			</a-form-item>
			<a-form-item label="文档说明">
				<div class="markdown-body" v-html="getDescription(openApiDocInfo.description)"></div>
			</a-form-item>
			<a-form-item label="接口统计">
				<a-row :gutter="[16, 16]">
					<template v-for="method in ['get', 'post', 'put', 'delete', 'head', 'patch', 'options', 'trace', 'total']">
						<a-col :span="6" v-if="openApiMethodStatistic[method]">
							<a-card size="small">
								<a-statistic :title="method === 'total'?'总计':method.toUpperCase() + '方法'" :value="openApiMethodStatistic[method]" suffix="个"></a-statistic>
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
			const openApiDoc = computed(() => store.state.openApiDoc);
			const openApiDocInfo = computed(() => store.state.openApiDoc.info);
			const openApiMethodStatistic = computed(() => store.state.openApiMethodStatistic);
			const getDescription = description => {
				return markdownIt.render(description || '');
			};
			return {
				openApiDoc,
				openApiDocInfo,
				openApiMethodStatistic,
				getDescription,
			};
		},
	};
</script>
