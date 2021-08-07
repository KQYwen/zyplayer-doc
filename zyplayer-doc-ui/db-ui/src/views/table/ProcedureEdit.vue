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
				<el-button type="primary" @click="saveProcedure" icon="el-icon-document-checked" size="mini">保存</el-button>
				<el-button type="" @click="showProcedureLog" icon="el-icon-document" size="mini">修改日志</el-button>
<!--				<el-button type="" @click="" icon="el-icon-video-play" size="mini">运行</el-button>-->
<!--				<el-button type="" @click="" icon="el-icon-video-pause" size="mini">停止</el-button>-->
			</div>
			<ace-editor v-model="sqlExecutorContent" @init="sqlExecutorInit" lang="sql" theme="monokai" width="100%" height="500" :options="sqlEditorConfig" style="margin-bottom: 10px;"></ace-editor>
		</el-card>
		<!--错误信息弹窗-->
		<el-dialog title="保存函数失败" :visible.sync="saveProcedureErrVisible" :footer="null">
			<div style="width: 700px;max-height: 500px;overflow: auto;">
				<pre>{{saveProcedureErrInfo}}</pre>
			</div>
		</el-dialog>
		<!--函数修改日志弹窗-->
		<el-dialog title="函数修改日志" :visible.sync="procLogVisible" :footer="null">
			<el-table :data="procLogList" stripe border style="width: 100%;" height="400">
				<el-table-column prop="id" label="ID" width="100"></el-table-column>
				<el-table-column prop="createUserName" label="修改人"></el-table-column>
				<el-table-column prop="createTime" label="修改时间"></el-table-column>
				<el-table-column prop="status" label="状态">
					<template slot-scope="scope">
						<el-tag type="success" v-if="scope.row.status==1">保存成功</el-tag>
						<el-tag type="danger" v-else-if="scope.row.status==2">保存失败</el-tag>
						<el-tag type="danger" v-else>-</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="action" label="操作" width="130">
					<template slot-scope="scope">
						<el-button type="" @click="reEditProc(scope.row.id)">重新编辑</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
					style="margin-top: 10px;"
					@size-change="handlePageSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage"
					:page-sizes="[10, 30, 50]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="tableTotalCount">
			</el-pagination>
		</el-dialog>
	</div>
</template>

<script>
	import datasourceApi from '../../common/api/datasource'
	import aceEditor from "../../common/lib/ace-editor";

	export default {
		data() {
			return {
				procedureInfoLoading: false,
				vueQueryParam: {},
				procedureInfo: {},
				sqlExecutorEditor: {},
				saveProcedureErrInfo: '',
				saveProcedureErrVisible: false,
				// 日志列表
				procLogVisible: false,
				procLogList: [],
				pageSize: 30,
				currentPage: 1,
				tableTotalCount: 0,
				// 编辑器
				sqlExecutorContent: '',
				sqlEditorConfig: {
					wrap: true,
					autoScrollEditorIntoView: true,
					enableBasicAutocompletion: true,
					enableSnippets: true,
					enableLiveAutocompletion: true,
					minLines: 20,
					maxLines: 40,
				},
			};
		},
		components: {
			'ace-editor': aceEditor
		},
		mounted() {
			// 延迟设置展开的目录，edit比app先初始化
			setTimeout(() => {
				this.$emit('initLoadDataList', {
					sourceId: this.vueQueryParam.sourceId,
					host: this.vueQueryParam.host,
					dbName: this.vueQueryParam.dbName
				});
			}, 500);
			this.initQueryParam(this.$route);
			this.searchProcedureDetail();
		},
		methods: {
			initQueryParam(to) {
				this.vueQueryParam = to.query;
				let newName = {key: this.$route.fullPath, val: '编辑函数'};
				this.$store.commit('global/addTableName', newName);
			},
			sqlExecutorInit(editor) {
				this.sqlExecutorEditor = editor;
				this.sqlExecutorEditor.setFontSize(16);
				let that = this;
				this.sqlExecutorEditor.commands.addCommand({
					name: "execute-sql",
					bindKey: {win: "Ctrl-S", mac: "Command-S"},
					exec: function (editor) {
						that.saveProcedure();
					}
				});
			},
			handleCurrentChange(to) {
				this.currentPage = to;
				this.searchProcedureLogList();
			},
			handlePageSizeChange(to) {
				this.pageSize = to;
				this.searchProcedureLogList();
			},
			showProcedureLog() {
				this.currentPage = 1;
				this.procLogVisible = true;
				this.searchProcedureLogList();
			},
			searchProcedureLogList() {
				let param = {...this.vueQueryParam, ...this.searchParam, pageNum: this.currentPage, pageSize: this.pageSize};
				datasourceApi.procedureLogList(param).then(json => {
					if (this.currentPage == 1) {
						this.tableTotalCount = json.total || 0;
					}
					this.procLogList = json.data || [];
				});
			},
			reEditProc(id) {
				datasourceApi.procedureLogDetail({logId: id}).then(json => {
					let procedureLog = json.data || {};
					this.sqlExecutorEditor.setValue(procedureLog.procBody, 1);
					this.procLogVisible = false;
				});
			},
			searchProcedureDetail() {
				this.procedureInfoLoading = true;
				datasourceApi.procedureDetail(this.vueQueryParam).then(json => {
					this.procedureInfo = json.data || {};
					this.procedureInfoLoading = false;
					this.sqlExecutorEditor.setValue(this.procedureInfo.body, 1);
				}).catch(() => {
					this.procedureInfoLoading = false;
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
				}).catch(() => {
					this.procedureInfoLoading = false;
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

