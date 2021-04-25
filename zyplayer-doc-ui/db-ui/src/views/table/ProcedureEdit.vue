<template>
	<div class="table-procedure-edit-vue">
		<el-card style="margin: 10px;" shadow="never">
			<div slot="header" class="clearfix">函数信息</div>
			<el-row>
				<el-col :span="6"><span class="label">数据源：</span>{{vueQueryParam.host}}</el-col>
				<el-col :span="6"><span class="label">数据库：</span>{{vueQueryParam.dbName}}</el-col>
				<el-col :span="6"><span class="label">类型：</span>{{vueQueryParam.typeName}}</el-col>
				<el-col :span="6"><span class="label">名称：</span>{{vueQueryParam.procName}}</el-col>
			</el-row>
		</el-card>
		<el-card style="margin: 10px;" shadow="never" v-loading="procedureInfoLoading">
			<div slot="header" class="clearfix">
				<span style="margin-right: 20px;">编辑函数</span>
				<el-button type="" @click="saveProcedure" icon="el-icon-document-checked" size="mini">保存</el-button>
<!--				<el-button type="" @click="" icon="el-icon-video-play" size="mini">运行</el-button>-->
<!--				<el-button type="" @click="" icon="el-icon-video-pause" size="mini">停止</el-button>-->
			</div>
			<pre id="sqlExecutorEditor" style="width: 100%;height: 500px;margin-top: 0;"></pre>
		</el-card>
		<!--错误信息弹窗-->
		<el-dialog title="保存函数失败" :visible.sync="saveProcedureErrVisible" :footer="null">
			<div style="width: 700px;max-height: 500px;overflow: auto;">
				<pre>{{saveProcedureErrInfo}}</pre>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import '../../common/lib/ace/ace'
	import '../../common/lib/ace/theme-monokai'
	import '../../common/lib/ace/mode-sql'
	import '../../common/lib/ace/ext-language_tools'
	import '../../common/lib/ace/snippets/sql'
	import datasourceApi from '../../common/api/datasource'

	export default {
		data() {
			return {
				procedureInfoLoading: false,
				vueQueryParam: {},
				procedureInfo: {},
				sqlExecutorEditor: {},
				saveProcedureErrInfo: '',
				saveProcedureErrVisible: false,
			};
		},
		mounted: function () {
			// 延迟设置展开的目录，edit比app先初始化
			setTimeout(() => {
				this.$emit('initLoadDataList', {
					sourceId: this.vueQueryParam.sourceId,
					host: this.vueQueryParam.host,
					dbName: this.vueQueryParam.dbName
				});
			}, 500);
			let that = this;
			this.sqlExecutorEditor = this.initAceEditor("sqlExecutorEditor", 20);
			this.sqlExecutorEditor.setFontSize(16);
			this.sqlExecutorEditor.commands.addCommand({
				name: "execute-sql",
				bindKey: {win: "Ctrl-S", mac: "Command-S"},
				exec: function (editor) {
					that.saveProcedure();
				}
			});
			this.initQueryParam(this.$route);
			this.searchProcedureDetail();
		},
		methods: {
			initQueryParam(to) {
				this.vueQueryParam = to.query;
				let newName = {key: this.$route.fullPath, val: '编辑函数-' + this.vueQueryParam.procName};
				this.$store.commit('global/addTableName', newName);
			},
			searchProcedureDetail() {
				this.procedureInfoLoading = true;
				datasourceApi.procedureDetail(this.vueQueryParam).then(json => {
					this.procedureInfo = json.data || {};
					this.procedureInfoLoading = false;
					this.sqlExecutorEditor.setValue(this.procedureInfo.body, 1);
				});
			},
			saveProcedure() {
				this.procedureInfoLoading = true;
				let param = {...this.vueQueryParam, procSql: this.sqlExecutorEditor.getValue()};
				datasourceApi.saveProcedure(param).then(json => {
					this.procedureInfoLoading = false;
					let executeInfo = json.data || {};
					if (!!executeInfo.errMsg) {
						this.saveProcedureErrInfo = executeInfo.errMsg;
						this.saveProcedureErrVisible = true;
					} else {
						this.$message.success("保存成功！");
					}
				});
			},
			doDeleteProc(item) {
				let param = {
					sourceId: this.vueQueryParam.sourceId,
					dbName: this.vueQueryParam.dbName,
					typeName: item.type,
					procName: item.name
				};
				this.$confirm('确定要删除此存储过程吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					datasourceApi.deleteProcedure(param).then(json => {
						this.$message.success("删除成功！");
					});
				}).catch(() => {});
			},
			initAceEditor(editor, minLines) {
				return ace.edit(editor, {
					theme: "ace/theme/monokai",
					mode: "ace/mode/sql",
					wrap: true,
					autoScrollEditorIntoView: true,
					enableBasicAutocompletion: true,
					enableSnippets: true,
					enableLiveAutocompletion: true,
					minLines: minLines,
					maxLines: 40,
				});
			},
		}
	}
</script>
<style>
	.table-procedure-edit-vue .el-table td, .table-database-vue .el-table th{padding: 5px 0;}
</style>

