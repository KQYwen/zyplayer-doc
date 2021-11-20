<template>
	<a-card>
		<a-form :label-col="{span: 4}" :wrapper-col="{span: 20}" v-if="swaggerDocInfo">
			<a-form-item label="标题">{{swaggerDocInfo.title}}</a-form-item>
			<a-form-item label="版本">{{swaggerDocInfo.version}}</a-form-item>
			<a-form-item label="作者" v-if="swaggerDocInfo.contact">
				{{swaggerDocInfo.contact.name}} {{swaggerDocInfo.contact.email}}
				<a :href="swaggerDocInfo.contact.url" target="_blank" v-if="swaggerDocInfo.contact.url">{{swaggerDocInfo.contact.url}}</a>
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
				<span v-html="swaggerDocInfo.description"></span>
			</a-form-item>
			<a-form-item label="接口统计">
				<a-row :gutter="[16, 16]">
					<template v-for="method in ['GET', 'POST', 'PUT', 'DELETE', 'HEAD', 'PATCH', 'OPTIONS', 'TRACE', 'TOTAL']">
						<a-col :span="6" v-if="methodStatistic[method]">
							<a-card size="small">
								<a-statistic :title="method === 'TOTAL'?'总计':method + '方法'" :value="methodStatistic[method]" suffix="个"></a-statistic>
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

	export default {
		setup() {
			const store = useStore()
			const swaggerDoc = computed(() => store.state.swaggerDoc);
			const swaggerDocInfo = computed(() => store.state.swaggerDoc.info);
			const methodStatistic = computed(() => store.state.methodStatistic);
			return {
				swaggerDoc,
				swaggerDocInfo,
				methodStatistic
			};
		},
	};
</script>
