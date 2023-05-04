<template>
    <div class="data-transfer-vue">
        <div style="padding: 0 10px;height: 100%;box-sizing: border-box;">
            <el-card>
                <div slot="header" class="clearfix">
                    <span>数据互导工具</span>
                    <a target="_blank" title="点击查看如何使用" href="http://doc.zyplayer.com/#/integrate/zyplayer-doc/opensource/128"><i class="el-icon-info" style="color: #999;"></i></a>
                </div>
                <div style="margin-bottom: 10px;text-align: right;">
                    <el-button type="success" icon="el-icon-plus" v-on:click="createNewTask">新建</el-button>
                    <el-button type="primary" v-on:click="loadGetTaskList" :loading="loadDataListLoading" icon="el-icon-refresh">查询</el-button>
                </div>
                <el-table :data="taskList" stripe border style="width: 100%; margin-bottom: 5px;">
                    <el-table-column prop="id" label="ID" width="55"></el-table-column>
                    <el-table-column prop="name" label="任务名称"></el-table-column>
<!--                    <el-table-column prop="queryDatasourceId" label="查询数据源">-->
<!--                        <template slot-scope="scope">{{datasourceMap[scope.row.queryDatasourceId]}}</template>-->
<!--                    </el-table-column>-->
<!--                    <el-table-column prop="storageDatasourceId" label="入库数据源">-->
<!--                        <template slot-scope="scope">{{datasourceMap[scope.row.storageDatasourceId]}}</template>-->
<!--                    </el-table-column>-->
                    <el-table-column label="条数查询">
                        <template slot-scope="scope">{{scope.row.needCount==1?'是':'否'}}</template>
                    </el-table-column>
                    <el-table-column prop="lastExecuteStatus" label="最后执行状态">
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.lastExecuteStatus==0">未执行</el-tag>
                            <el-tag type="info" v-else-if="scope.row.lastExecuteStatus==1">执行中</el-tag>
                            <el-tag type="success" v-else-if="scope.row.lastExecuteStatus==2">执行成功</el-tag>
                            <el-tag type="danger" v-else-if="scope.row.lastExecuteStatus==3">执行失败</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.lastExecuteStatus==4">取消执行</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="lastExecuteTime" label="最后执行时间"></el-table-column>
                    <el-table-column prop="createUserName" label="创建人"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column label="操作" width="290">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" v-on:click="viewTask(scope.row.id)">查看</el-button>
                            <el-button size="mini" type="success" v-on:click="editTask(scope.row)">编辑</el-button>
                            <el-button size="mini" type="warning" v-on:click="executeTask(scope.row.id)">执行</el-button>
                            <el-button size="mini" type="danger" v-on:click="deleteTask(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </div>
        <!--任务编辑弹窗-->
        <el-dialog :visible.sync="taskEditDialogVisible" width="900px" :close-on-click-modal="false">
            <span slot="title" v-if="!!taskEditInfo.id">编辑任务</span>
            <span slot="title" v-else>新建任务</span>
            <el-form label-width="120px">
                <el-form-item label="任务名称：">
                    <el-input v-model="taskEditInfo.name" placeholder="任务名称"></el-input>
                </el-form-item>
                <el-form-item label="查询数据源：">
                    <el-select v-model="taskEditInfo.queryDatasourceId" filterable placeholder="请选择查询数据源" style="width: 100%;">
                        <el-option v-for="item in datasourceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="查询SQL：">
					<ace-editor v-model="querySqlContent" @init="querySqlInit" lang="sql" theme="monokai" width="100%" height="100" :options="editSqlConfig"></ace-editor>
                </el-form-item>
                <el-form-item label="总条数查询：">
                    <el-radio v-model="taskEditInfo.needCount" :label="0">不查询</el-radio>
                    <el-radio v-model="taskEditInfo.needCount" :label="1">自动查询</el-radio>
                </el-form-item>
                <el-form-item label="入库数据源：">
                    <el-select v-model="taskEditInfo.storageDatasourceId" filterable placeholder="请选择写入数据源" style="width: 100%;">
                        <el-option v-for="item in datasourceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入库SQL：">
					<ace-editor v-model="storageSqlContent" @init="storageSqlInit" lang="sql" theme="monokai" width="100%" height="100" :options="editSqlConfig"></ace-editor>
                    <el-button v-on:click="autoFillStorageSql" style="margin-top: 10px;">智能填充</el-button>
                </el-form-item>
            </el-form>
            <div style="text-align: center;">
                <el-button type="primary" v-on:click="saveEditTask">保存</el-button>
                <el-button type="warning" v-on:click="taskEditDialogVisible=false">取消</el-button>
            </div>
        </el-dialog>
        <!--任务查看弹窗-->
        <el-dialog :visible.sync="taskViewDialogVisible" width="80%">
            <span slot="title">查看任务</span>
            <el-form label-width="120px">
                <el-form-item label="任务名称：">{{taskEditInfo.name}}</el-form-item>
                <el-form-item label="最后执行状态：">
                    <el-tag v-if="taskEditInfo.lastExecuteStatus==0">未执行</el-tag>
                    <el-tag type="info" v-else-if="taskEditInfo.lastExecuteStatus==1">执行中</el-tag>
                    <el-tag type="success" v-else-if="taskEditInfo.lastExecuteStatus==2">执行成功</el-tag>
                    <el-tag type="danger" v-else-if="taskEditInfo.lastExecuteStatus==3">执行失败</el-tag>
                    <el-tag type="warning" v-else-if="taskEditInfo.lastExecuteStatus==4">取消执行</el-tag>
                </el-form-item>
                <el-form-item label="最后执行时间：">{{taskEditInfo.lastExecuteTime}}</el-form-item>
                <el-form-item label="执行信息：">
                    <pre style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap;line-height: 22px;">{{taskEditInfo.lastExecuteInfo}}</pre>
                </el-form-item>
                <el-form-item label="操作：" v-if="taskEditInfo.lastExecuteStatus==1">
                    <el-button type="success" v-on:click="viewTask(taskEditInfo.id)" icon="el-icon-refresh" :loading="viewTaskLoading">刷新</el-button>
                    <el-button type="danger" v-on:click="cancelTask">取消执行</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import datasourceApi from '../../common/api/datasource'
	import aceEditor from "../../common/lib/ace-editor";

    export default {
        data() {
            return {
                viewTaskLoading: false,
                loadDataListLoading: false,
                // 数据源相关
                datasourceOptions: [],
                queryDatasourceId: "",
                storageDatasourceId: "",
                // 页面展示相关
                taskList: [],
                datasourceMap: {},
                // 弹窗
                taskViewDialogVisible: false,
                taskEditDialogVisible: false,
                taskEditInfo: {},
				// 编辑器
				querySqlEditor: {},
				storageSqlEditor: {},
				querySqlContent: '',
				storageSqlContent: '',
				editSqlConfig: {
					wrap: true,
					autoScrollEditorIntoView: true,
					enableBasicAutocompletion: true,
					enableSnippets: true,
					enableLiveAutocompletion: true,
					minLines: 10,
					maxLines: 25,
				},
            }
		},
		components: {
			'ace-editor': aceEditor
		},
		mounted() {
			this.loadDatasourceList();
		},
		methods: {
			editTask(row) {
				this.taskEditInfo = JSON.parse(JSON.stringify(row));
				this.taskEditDialogVisible = true;
				setTimeout(() => {
					this.querySqlEditor.setValue(this.taskEditInfo.querySql, 1);
					this.storageSqlEditor.setValue(this.taskEditInfo.storageSql, 1);
				}, 200);
			},
			querySqlInit(editor) {
				this.querySqlEditor = editor;
				this.querySqlEditor.setFontSize(16);
			},
			storageSqlInit(editor) {
				this.storageSqlEditor = editor;
				this.storageSqlEditor.setFontSize(16);
			},
            createNewTask() {
                this.taskEditInfo = {querySql: '', storageSql: '', name: '', needCount: 1, queryDatasourceId: '', storageDatasourceId: ''};
                this.taskEditDialogVisible = true;
                setTimeout(() => {
                    this.querySqlEditor.setValue('', 1);
                    this.storageSqlEditor.setValue('', 1);
                }, 200);
            },
            deleteTask(id) {
                this.$confirm('确定要删除此任务吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    datasourceApi.transferUpdate({id: id, delFlag: 1}).then(() => {
                        this.$message.success("删除成功");
                        this.loadGetTaskList();
                    });
                }).catch(()=>{});
            },
            executeTask(id) {
                this.$confirm('确定要执行一次此任务吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    datasourceApi.transferStart({id: id}).then(() => {
                        this.$message.success("任务提交成功");
                    });
                }).catch(()=>{});
            },
            saveEditTask() {
                this.taskEditInfo.querySql = this.querySqlEditor.getValue();
                this.taskEditInfo.storageSql = this.storageSqlEditor.getValue();
                datasourceApi.transferUpdate(this.taskEditInfo).then(() => {
                    this.$message.success("保存成功");
                    this.taskEditDialogVisible = false;
                    this.loadGetTaskList();
                });
            },
            viewTask(id) {
                this.viewTaskLoading = true;
                this.taskViewDialogVisible = true;
                datasourceApi.transferDetail({id: id}).then(json => {
                    this.taskEditInfo = json.data || {};
                    setTimeout(() => {
						this.viewTaskLoading = false;
                    }, 300);
                });
            },
            cancelTask() {
                this.$confirm('确定要取消执行此任务吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    datasourceApi.transferCancel({id: this.taskEditInfo.id}).then(() => {
                        this.$message.success("取消成功");
                        this.viewTask(this.taskEditInfo.id);
                    });
                }).catch(()=>{});
            },
            loadGetTaskList() {
                this.loadDataListLoading = true;
                datasourceApi.transferList({}).then(json => {
                    this.taskList = json.data || [];
                    setTimeout(()=>{this.loadDataListLoading = false;}, 800);
                });
            },
            autoFillStorageSql() {
                let sqlStr = this.querySqlEditor.getValue();
                datasourceApi.transferSqlColumns({sql: sqlStr}).then(json => {
                    let resultData = json.data || [];
                    if (resultData.length <= 0) {
                        this.$message.error("查询的字段不明确，不能自动填充");
                        return;
                    }
                    let storageSql = "\n";
                    storageSql += "insert into TableName (\n";
                    for (let i = 0; i < resultData.length; i++) {
                        storageSql += "\t" + ((i === 0) ? "" : ",") + resultData[i] + "\n";
                    }
                    storageSql += ") values (\n";
                    for (let i = 0; i < resultData.length; i++) {
                        storageSql += "\t" + ((i === 0) ? "" : ",") + "#{" + resultData[i] + "}\n";
                    }
                    storageSql += ")\n\n";
                    this.storageSqlEditor.setValue(storageSql, 1);
                });
            },
            loadDatasourceList() {
                datasourceApi.datasourceList({}).then(json => {
                    this.datasourceList = json.data || [];
                    let datasourceOptions = [], datasourceMap = {};
                    for (let i = 0; i < this.datasourceList.length; i++) {
                        datasourceMap[this.datasourceList[i].id] = this.datasourceList[i].name;
                        datasourceOptions.push({
                            label: this.datasourceList[i].name,
                            value: this.datasourceList[i].id
                        });
                    }
                    this.datasourceMap = datasourceMap;
                    this.datasourceOptions = datasourceOptions;
                    this.loadGetTaskList();
                });
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
                    maxLines: 25,
                });
            },
        }
    }
</script>

<style>
    .data-transfer-vue .el-button+.el-button {
        margin-left: 4px;
    }
</style>
