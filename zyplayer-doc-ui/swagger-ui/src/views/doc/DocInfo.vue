<template>
	<a-card>
		<a-form :label-col="{span: 4}" :wrapper-col="{span: 20}" v-if="swaggerDocInfo">
			<a-form-item label="标题">{{swaggerDocInfo.title}}</a-form-item>
			<a-form-item label="版本">{{swaggerDocInfo.version}}</a-form-item>
			<a-form-item label="作者" v-if="swaggerDocInfo.contact">
				{{swaggerDocInfo.contact.name}} {{swaggerDocInfo.contact.email}}
				<a :href="swaggerDocInfo.contact.url" target="_blank" v-if="swaggerDocInfo.contact.url">{{swaggerDocInfo.contact.url}}</a>
			</a-form-item>
			<a-form-item label="host">{{swaggerDoc.host}}</a-form-item>
			<a-form-item label="许可证" v-if="swaggerDocInfo.license">
				<a :href="swaggerDocInfo.license.url" target="_blank">{{swaggerDocInfo.license.name}}</a>
			</a-form-item>
			<a-form-item label="文档说明">
				<span v-html="swaggerDocInfo.description"></span>
			</a-form-item>
		</a-form>
		<div v-else style="text-align: center;">暂无文档信息，请先选择文档</div>
	</a-card>
</template>

<script>
	export default {
		name: 'docInfo',
		components: {},
		data() {
			return {}
		},
		computed: {
			swaggerDoc () {
				return this.$store.state.swaggerDoc || {};
			},
			swaggerDocInfo () {
				return this.$store.state.swaggerDoc.info;
			}
		},
		mounted() {
		},
		methods: {}
	}
</script>
