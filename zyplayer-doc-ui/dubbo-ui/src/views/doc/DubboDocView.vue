<template>
    <div class="dubbo-doc-view">
		<el-tabs type="border-card" style="width: 100%;box-shadow: none;">
			<el-tab-pane label="接口说明">
				<div v-if="!dubboInfo.interface">请先选择服务</div>
				<el-form v-else label-width="100px">
					<el-form-item label="服务：">
						{{dubboInfo.interface}}
					</el-form-item>
					<el-form-item label="方法：">
						{{dubboInfo.method}}
					</el-form-item>
					<el-form-item label="说明：">
						<div v-if="dubboInfoExplainShow">
							<pre style="margin: 0;">{{dubboInfo.docInfo.explain}}<el-button @click="editDocInfoExplain" style="float: right;">编辑</el-button></pre>
						</div>
						<div v-else>
							<el-input type="textarea" :rows="4" placeholder="维护人员、使用说明、便于搜索的信息" v-model="docInfoExplainInput"></el-input>
							<el-button @click.prevent="dubboInfoExplainShow = true" style="float: right;margin: 5px;">取消</el-button>
							<el-button type="primary" @click.prevent="saveDocInfoExplain" style="float: right;margin: 5px;">保存</el-button>
						</div>
					</el-form-item>
					<el-form-item label="节点：">
						<el-table :data="dubboInfo.nodeList" border style="width: 100%">
							<el-table-column prop="application" label="应用"></el-table-column>
							<el-table-column prop="ip" label="IP"></el-table-column>
							<el-table-column prop="port" label="端口"></el-table-column>
						</el-table>
					</el-form-item>
					<el-form-item label="参数：">
						<el-table :data="docParamList" border style="width: 100%; margin-bottom: 5px;">
							<el-table-column label="顺序" width="100">
								<template slot-scope="scope">{{scope.$index}}</template>
							</el-table-column>
							<el-table-column label="参数名" width="200">
								<template slot-scope="scope">
									<el-input v-model="scope.row.paramName"></el-input>
								</template>
							</el-table-column>
							<el-table-column label="类型" width="300">
								<template slot-scope="scope">{{scope.row.paramType}}</template>
							</el-table-column>
							<el-table-column label="说明">
								<template slot-scope="scope">
									<el-input v-model="scope.row.paramDesc"></el-input>
								</template>
							</el-table-column>
						</el-table>
						<el-button @click.prevent="saveDocInfoParam" type="primary" style="float: right;margin: 5px;">保存</el-button>
					</el-form-item>
					<el-form-item label="返回值：">
						{{dubboInfo.docInfo.resultType}}
					</el-form-item>
					<el-form-item label="结果说明：">
						<div v-if="dubboInfoResultShow">
							<pre style="margin: 0;">{{dubboInfo.docInfo.result}}<el-button @click="editDocInfoResult" style="float: right;">编辑</el-button></pre>
						</div>
						<div v-else>
							<el-input type="textarea" :rows="4" placeholder="结果集说明等" v-model="docInfoResultInput"></el-input>
							<el-button @click.prevent="dubboInfoResultShow = true" style="float: right;margin: 5px;">取消</el-button>
							<el-button type="primary" @click.prevent="saveDocInfoResult" style="float: right;margin: 5px;">保存</el-button>
						</div>
					</el-form-item>
				</el-form>
			</el-tab-pane>
			<el-tab-pane label="在线调试">
				<div v-if="!dubboInfo.interface">请先选择服务</div>
				<div v-loading="onlineDebugLoading" v-else>
					<el-input placeholder="请输入内容" v-model="dubboInfo.function" class="input-with-select">
						<el-select v-model="requestHostValue" slot="prepend" placeholder="请选择" style="width: 200px;">
							<el-option v-for="item in requestHostOptions" :key="item.value" :label="item.value" :value="item.value"></el-option>
						</el-select>
						<el-button slot="append" @click.prevent="requestExecute">执行</el-button>
					</el-input>
					<el-form label-width="100px">
						<el-form-item label="请求参数：">
							<el-table :data="docParamRequestList" border style="width: 100%; margin: 10px 0;">
								<el-table-column label="顺序" width="100">
									<template slot-scope="scope">{{scope.$index}}</template>
								</el-table-column>
								<el-table-column label="参数名">
									<template slot-scope="scope">{{scope.row.paramName}}</template>
								</el-table-column>
								<el-table-column label="类型">
									<template slot-scope="scope">{{scope.row.paramType}}</template>
								</el-table-column>
								<el-table-column label="参数值">
									<template slot-scope="scope">
										<el-input v-model="scope.row.paramValue"></el-input>
									</template>
								</el-table-column>
								<el-table-column label="说明">
									<template slot-scope="scope">{{scope.row.paramDesc}}</template>
								</el-table-column>
							</el-table>
						</el-form-item>
						<el-form-item label="请求结果：">
							<div class="request-result" v-html="requestResult"></div>
						</el-form-item>
					</el-form>
				</div>
			</el-tab-pane>
		</el-tabs>
    </div>
</template>

<script>
	import dubboApi from '../../common/api/dubbo'
	import formatJson from '../../common/js/format-json'

	export default {
		props: ["dubboDocMap", "treePathDataMap"],
		data() {
			return {
				vueQueryParam: {},
				dubboInfo: {},
				requestHostOptions: [],
				onlineDebugLoading: false,
				// 展示的信息
				dubboInfoExplainShow: true,
				docInfoExplainInput: "",
				dubboInfoResultShow: true,
				docInfoResultInput: "",
				requestHostValue: "",
				requestResult: "",
				docParamList: [],
				docParamRequestList: [],
			};
		},
		mounted() {
		},
		activated() {
			this.initQueryParam(this.$route);
		},
		methods: {
			initQueryParam(to) {
				this.vueQueryParam = to.query;
				let newName = {key: this.$route.fullPath, val: this.vueQueryParam.method};
				this.$store.commit('global/addTableName', newName);
				let path = this.vueQueryParam.path;
				let method = this.vueQueryParam.method;
				let application = this.vueQueryParam.application;
				let docInfo = this.dubboDocMap[path];
				if (!!docInfo) {
					this.createDocInfo(path, method);
				} else {
					let service = path.substring(0, path.lastIndexOf("."));
					let method = path.substring(path.lastIndexOf(".") + 1, path.length);
					let param = {service: service, method: method, application: application};
					dubboApi.findDocInfo(param).then(json => {
						if (!!json.data) {
							this.dubboDocMap[json.data.function] = json.data;
						}
						this.createDocInfo(path, method);
					});
				}
			},
			createDocInfo(path, method, isRetry) {
				let docInfo = this.dubboDocMap[path];
				let dubboInfo = this.treePathDataMap.get(path);
				if (!docInfo || !dubboInfo) {
					if (!isRetry) {
						// 打开一个方法后，刷新页面后等待数据加载完成再初始化一次
						setTimeout(() => this.createDocInfo(path, method, true), 1500);
					}
					return;
				}
				dubboInfo.method = method;
				dubboInfo.function = path;
				dubboInfo.docInfo = docInfo || {};
				// 清空再赋值才会重新渲染
				this.dubboInfo = {};
				this.dubboInfo = dubboInfo;
				// 请求相关
				this.requestHostOptions = [];
				let optionSet = {};
				for (let i = 0; i < dubboInfo.nodeList.length; i++) {
					let item = dubboInfo.nodeList[i];
					let option = item.ip + ":" + item.port;
					if (!!item.version) option += " V" + item.version;
					if (!!item.group) option += " G" + item.group;
					if (!optionSet[option]) {
						optionSet[option] = 1;
						this.requestHostOptions.push({value: option});
					}
				}
				this.requestHostValue = "";
				this.docInfoExplainInput = this.dubboInfo.docInfo.explain;
				if (this.requestHostOptions.length > 0) {
					this.requestHostValue = this.requestHostOptions[0].value;
				}
				this.docParamList = [];
				this.docParamList = this.dubboInfo.docInfo.params || [];
				this.createDocParamRequestList();
			},
			saveDocInfoExplain(){
				this.doSaveDocInfo(this.docInfoExplainInput, null, null, true);
			},
			saveDocInfoResult(){
				this.doSaveDocInfo(null, null, this.docInfoResultInput, true);
			},
			editDocInfoResult() {
				this.dubboInfoResultShow = false;
				this.docInfoResultInput = this.dubboInfo.docInfo.result || '';
			},
			editDocInfoExplain() {
				this.dubboInfoExplainShow = false;
				this.docInfoExplainInput = this.dubboInfo.docInfo.explain || '';
			},
			saveDocInfoParam() {
				var docParamList = [];
				for (var i = 0; i < this.docParamList.length; i++) {
					var item = this.docParamList[i];
					if (!!item.paramType) {
						docParamList.push(item);
					}
				}
				var paramsJson = JSON.stringify(docParamList);
				this.doSaveDocInfo(null, paramsJson, null, true);
			},
			createDocParamRequestList() {
				var docParamList = [];
				for (var i = 0; i < this.docParamList.length; i++) {
					var item = this.docParamList[i];
					if (!!item.paramType || !!item.paramDesc) {
						docParamList.push(item);
					}
				}
				this.docParamRequestList = docParamList;
			},
			doSaveDocInfo(explain, params, result, showSuccess){
				var param = {
					service: this.dubboInfo.interface,
					method: this.dubboInfo.method,
					resultType: this.dubboInfo.resultType,
					paramValue: this.dubboInfo.paramValue,
					version: this.dubboInfo.docInfo.version || 0,
					explain: explain,
					result: result,
					paramsJson: params,
				};
				dubboApi.saveDoc(param).then(json => {
					this.dubboDocMap[json.data.function] = json.data;
					this.dubboInfo.docInfo = json.data;
					this.dubboInfoExplainShow = true;
					this.dubboInfoResultShow = true;
					this.docParamList = json.data.params || [];
					this.createDocParamRequestList();
					if (showSuccess) {
						this.$message({message: '保存成功！', type: 'success'});
					}
				});
			},
			requestExecute() {
				var fuc = this.dubboInfo.function;
				var hostValue = this.requestHostValue;
				var service = fuc.substring(0, fuc.lastIndexOf("."));
				var method = fuc.substring(fuc.lastIndexOf(".") + 1, fuc.length);
				var paramArr = hostValue.split(" ");
				var ipPortArr = paramArr[0].split(":");
				var version = '', group = '';
				paramArr.forEach(item => {
					if (item.startsWith("V")) version = item.substring(1, item.length);
					if (item.startsWith("G")) group = item.substring(1, item.length);
				});
				var paramTypes = [];
				var params = [];
				for (var i = 0; i < this.docParamList.length; i++) {
					var item = this.docParamList[i];
					paramTypes.push(item.paramType);
					params.push(item.paramValue || '');
				}
				var param = {
					service: service,
					method: method,
					ip: ipPortArr[0],
					port: ipPortArr[1],
					version: version,
					group: group,
					paramTypes: JSON.stringify(paramTypes),
					params: JSON.stringify(params),
				};
				this.requestResult = "";
				this.onlineDebugLoading = true;
				dubboApi.request(param).then(json => {
					this.onlineDebugLoading = false;
					if (json.errCode == 200) {
						try {
							this.requestResult = formatJson.processObjectToHtmlPre(JSON.parse(json.data), 0, false, false, false, false);
						} catch (e) {
							try {
								this.requestResult = formatJson.processObjectToHtmlPre(json.data, 0, false, false, false, false);
							} catch (e) {
								this.requestResult = json.data;
							}
						}
						var paramsJson = JSON.stringify(this.docParamRequestList);
						this.doSaveDocInfo(null, paramsJson, null, false);
					} else {
						this.requestResult = json.errMsg;
					}
				}, err => {
					this.onlineDebugLoading = false;
					this.requestResult = err || '';
					if (!!err.responseJSON && err.responseJSON.message) {
						this.requestResult = err.responseJSON.message;
					}
				});
			},
		}
    }
</script>
<style>
	.dubbo-doc-view{padding: 0 10px;}
	.dubbo-doc-view .el-tabs--border-card>.el-tabs__content{
		height: calc(100vh - 180px);overflow-y: auto;
	}
	.dubbo-doc-view .request-result pre{
		margin: 0;
	}
</style>

