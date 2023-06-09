<template>
    <div class="table-info-vue">
		<el-tabs v-model="tabActiveName" @tab-click="tabActiveNameChange">
			<el-tab-pane label="表字段" name="columns">
				<div v-loading="columnListLoading">
					<el-row type="flex" class="row-bg" justify="space-between" style="margin-bottom: 10px;">
						<el-col style="color: #606266;line-height: 40px;">
							<span>表名：{{tableStatusInfo.name}}</span>
							<span style="margin-left: 20px;">
								<span v-if="tableInfo.inEdit == 1">
									<el-input v-model="tableInfo.newDesc" placeholder="输入表注释" @keyup.enter.native="saveTableDescription" v-on:blur="saveTableDescription" style="width: 500px;"></el-input>
								</span>
								<span v-else>{{tableInfo.description || '暂无表注释'}} <i class="el-icon-edit edit-table-desc" @click="tableInfo.inEdit = 1"></i></span>
							</span>
						</el-col>
						<el-col style="width: 100px;text-align: right;">
							<el-button plain type="primary" @click="showCreateTableDdl" style="margin-left: 10px;" icon="el-icon-magic-stick">DDL</el-button>
						</el-col>
					</el-row>
					<el-table :data="columnList" stripe border :height="height" style="width: 100%; margin-bottom: 5px;">
						<el-table-column prop="name" label="字段名" width="220"></el-table-column>
						<el-table-column label="自增" width="50">
							<template slot-scope="scope">{{scope.row.selfIncrement ? '是' : '否'}}</template>
						</el-table-column>
						<el-table-column prop="type" label="类型" width="110"></el-table-column>
						<el-table-column prop="length" label="长度" width="110"></el-table-column>
						<el-table-column prop="numericScale" label="小数点" width="80">
							<template slot-scope="scope">{{scope.row.numericScale==0 ? '' : scope.row.numericScale}}</template>
						</el-table-column>
						<el-table-column prop="nullable" label="空值" width="80">
							<template slot-scope="scope">{{scope.row.nullable==1 ? '允许' : '不允许'}}</template>
						</el-table-column>
						<el-table-column label="主键" width="50">
							<template slot-scope="scope">{{scope.row.primaryKey==1 ? '是' : '否'}}</template>
						</el-table-column>
						<el-table-column>
							<template slot="header" slot-scope="scope">
								注释
								<el-tooltip effect="dark" content="点击注释列可编辑字段注释" placement="top">
									<i class="el-icon-info" style="color: #999;"></i>
								</el-tooltip>
							</template>
							<template slot-scope="scope">
								<div v-if="scope.row.inEdit == 1">
									<el-input v-model="scope.row.newDesc" placeholder="输入字段注释" @keyup.enter.native="saveColumnDescription(scope.row)" v-on:blur="saveColumnDescription(scope.row)"></el-input>
								</div>
								<div v-else class="description" v-on:click="descBoxClick(scope.row)">{{scope.row.description}}</div>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</el-tab-pane>
			<el-tab-pane label="表信息" name="tableInfo">
				<el-row class="status-info-row">
					<el-col :span="24"><span class="label">数据源：</span>{{vueQueryParam.host}}</el-col>
				</el-row>
				<el-row class="status-info-row">
					<el-col :span="6"><span class="label">数据库：</span>{{vueQueryParam.dbName}}</el-col>
					<el-col :span="6"><span class="label">数据表：</span>{{tableStatusInfo.name}}</el-col>
					<el-col :span="6"><span class="label">引擎：</span>{{tableStatusInfo.engine}}</el-col>
				</el-row>
				<el-row class="status-info-row">
					<el-col :span="6"><span class="label">行数：</span>{{tableStatusInfo.rows||0}}</el-col>
					<el-col :span="6"><span class="label">自动递增：</span>{{tableStatusInfo.autoIncrement||0}}</el-col>
					<el-col :span="6"><span class="label">行格式：</span>{{tableStatusInfo.rowFormat}}</el-col>
					<el-col :span="6"><span class="label">排序规则：</span>{{tableStatusInfo.collation}}</el-col>
				</el-row>
				<el-row class="status-info-row">
					<el-col :span="6"><span class="label">索引长度：</span>{{getBytesSize(tableStatusInfo.indexLength)}}</el-col>
					<el-col :span="6"><span class="label">数据长度：</span>{{getBytesSize(tableStatusInfo.dataLength)}}</el-col>
					<el-col :span="6"><span class="label">最大长度：</span>{{getBytesSize(tableStatusInfo.maxDataLength)}}</el-col>
					<el-col :span="6"><span class="label">可用空间：</span>{{getBytesSize(tableStatusInfo.dataFree)}}</el-col>
				</el-row>
				<el-row class="status-info-row">
					<el-col :span="6"><span class="label">创建时间：</span>{{tableStatusInfo.createTime}}</el-col>
					<el-col :span="6"><span class="label">修改时间：</span>{{tableStatusInfo.updateTime}}</el-col>
					<el-col :span="6"><span class="label">检查时间：</span>{{tableStatusInfo.checkTime}}</el-col>
					<el-col :span="6"><span class="label">创建选项：</span>{{tableStatusInfo.createOptions}}</el-col>
				</el-row>
				<el-row class="status-info-row">
					<el-col :span="24">
						<span class="label">表注释：</span>
						<span v-if="tableInfo.inEdit == 1">
                        <el-input v-model="tableInfo.newDesc" placeholder="输入表注释" @keyup.enter.native="saveTableDescription" v-on:blur="saveTableDescription" style="width: 500px;"></el-input>
                    </span>
						<span v-else>{{tableInfo.description || '暂无注释'}} <i class="el-icon-edit edit-table-desc" v-on:click="tableInfo.inEdit = 1"></i></span>
					</el-col>
				</el-row>
			</el-tab-pane>
			<el-tab-pane label="关系图" name="relationChart">
				<table-relation-charts ref="relationChart"></table-relation-charts>
			</el-tab-pane>
			<el-tab-pane label="表数据" name="tableData">
				<data-preview ref="dataPreview"></data-preview>
			</el-tab-pane>
		</el-tabs>
        <!--增加数据源弹窗-->
        <el-dialog :visible.sync="tableDDLInfoDialogVisible" :footer="null" width="760px">
			<div slot="title">
				<span style="margin-right: 15px;">DDL</span>
				<el-button size="small" icon="el-icon-document-copy" v-clipboard:copy="tableDDLInfo[tableDDLInfoTab]" v-clipboard:success="onCopySuccess" v-clipboard:error="onCopyError">复制</el-button>
			</div>
			<el-tabs v-model="tableDDLInfoTab">
				<el-tab-pane label="mysql" name="mysql" v-if="!!tableDDLInfo.mysql">
					<div v-highlight>
						<pre><code v-html="tableDDLInfo.mysql"></code></pre>
					</div>
				</el-tab-pane>
				<el-tab-pane label="sqlserver" name="sqlserver" v-if="!!tableDDLInfo.sqlserver">
					<div v-highlight>
						<pre><code v-html="tableDDLInfo.sqlserver"></code></pre>
					</div>
				</el-tab-pane>
				<el-tab-pane label="oracle" name="oracle" v-if="!!tableDDLInfo.oracle">
					<div v-highlight>
						<pre><code v-html="tableDDLInfo.oracle"></code></pre>
					</div>
				</el-tab-pane>
				<el-tab-pane label="postgresql" name="postgresql" v-if="!!tableDDLInfo.postgresql">
					<div v-highlight>
						<pre><code v-html="tableDDLInfo.postgresql"></code></pre>
					</div>
				</el-tab-pane>
				<el-tab-pane label="hive" name="hive" v-if="!!tableDDLInfo.hive">
					<div v-highlight>
						<pre><code v-html="tableDDLInfo.hive"></code></pre>
					</div>
				</el-tab-pane>
			</el-tabs>
        </el-dialog>
    </div>
</template>

<script>
    import datasourceApi from '../../common/api/datasource'
    import dataPreview from '../data/DataPreview'
    import tableRelation from './TableRelation'

    export default {
        data() {
            return {
                columnListLoading: false,
                vueQueryParam: {},
                tableStatusInfo: {},
                columnList: [],
                tableInfo: {},
                nowExecutorId: 1,
				tableDDLInfoTab: '',
                tableDDLInfo: '',
                tableDDLInfoDialogVisible: false,
				// 标签
				tabActiveName: 'columns',
				height: document.body.clientHeight - 200
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
		},
		activated() {
			this.initQueryParam(this.$route);
		},
		components: {
			'data-preview': dataPreview,
			'table-relation-charts': tableRelation,
		},
        methods: {
            initQueryParam(to) {
                if (this.columnListLoading) {
                    return;
                }
                this.columnListLoading = true;
                this.vueQueryParam = to.query;
                datasourceApi.tableColumnList(this.vueQueryParam).then(json => {
                    let columnList = json.data.columnList || [];
                    for (let i = 0; i < columnList.length; i++) {
                        columnList[i].inEdit = 0;
                        columnList[i].description = columnList[i].description || '';
                        columnList[i].newDesc = columnList[i].description;
                    }
                    this.columnList = columnList;
                    let tableInfo = json.data.tableInfo || {};
                    tableInfo.inEdit = 0;
                    tableInfo.newDesc = tableInfo.description;
                    this.tableInfo = tableInfo;
                    let newName = {key: this.$route.fullPath, val: tableInfo.tableName};
                    this.$store.commit('global/addTableName', newName);
                    this.columnListLoading = false;
                });
                datasourceApi.tableStatus(this.vueQueryParam).then(json => {
                    this.tableStatusInfo = json.data || {};
                });
            },
			tabActiveNameChange() {
				if (this.tabActiveName == 'relationChart') {
					this.$refs.relationChart.init({
						sourceId: this.vueQueryParam.sourceId,
						dbName: this.vueQueryParam.dbName,
						tableName: this.vueQueryParam.tableName,
					});
				} else if (this.tabActiveName == 'tableData') {
					if (!this.columnList || this.columnList.length <= 0) {
						this.$message.error("字段信息尚未加载成功，请稍候...");
						setTimeout(() => this.tabActiveName = 'columns', 0);
						return;
					}
					let primaryColumn = this.columnList.find(item => item.primaryKey == 1) || this.columnList[0];
					this.$refs.dataPreview.init({
						sourceId: this.vueQueryParam.sourceId,
						dbName: this.vueQueryParam.dbName,
						tableName: this.vueQueryParam.tableName,
						host: this.vueQueryParam.host,
						dbType: this.tableStatusInfo.dbType,
						// 默认排序字段，先随便取一个，impala等数据库必须排序后才能分页查
						orderColumn: primaryColumn.name,
					}, this.columnList);
				}
            },
			onCopySuccess(e) {
				this.$message.success("内容已复制到剪切板！");
			},
			onCopyError(e) {
				this.$message.error("抱歉，复制失败！");
			},
            showCreateTableDdl() {
                this.tableDDLInfo = '';
                let param = {
                    sourceId: this.vueQueryParam.sourceId,
                    dbName: this.vueQueryParam.dbName,
                    tableName: this.vueQueryParam.tableName,
                };
				let loading = this.$loading({
					lock: true,
					text: '加载中...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				this.tableDDLInfoTab = '';
				this.tableDDLInfoDialogVisible = false;
				datasourceApi.queryTableDdl(param).then(res => {
					loading.close();
					this.tableDDLInfo = res.data || {};
					this.tableDDLInfoTab = this.tableDDLInfo.current;
					setTimeout(() => this.tableDDLInfoDialogVisible = true, 0);
				}).catch(() => {
					loading.close();
				});
            },
            descBoxClick(row) {
                // row.newDesc = row.description;
                row.inEdit = 1;
            },
            getBytesSize(size) {
                if (!size) return "0 bytes";
                var num = 1024.00;
                if (size < num) return size + " bytes";
                if (size < Math.pow(num, 2)) return (size / num).toFixed(2) + "KB";
                if (size < Math.pow(num, 3)) return (size / Math.pow(num, 2)).toFixed(2) + "MB";
                if (size < Math.pow(num, 4)) return (size / Math.pow(num, 3)).toFixed(2) + "GB";
                return (size / Math.pow(num, 4)).toFixed(2) + "TB";
            },
            saveColumnDescription(row) {
                if (row.inEdit == 0 || row.description == row.newDesc) {
                    row.inEdit = 0;
                    return;
                }
                row.inEdit = 0;
                this.vueQueryParam.columnName = row.name;
                this.vueQueryParam.newDesc = row.newDesc;
                datasourceApi.updateTableColumnDesc(this.vueQueryParam).then(() => {
                    row.description = row.newDesc;
                    this.$message.success("修改成功");
                });
            },
            saveTableDescription() {
                if (this.tableInfo.inEdit == 0 || this.tableInfo.description == this.tableInfo.newDesc) {
                    this.tableInfo.inEdit = 0;
                    return;
                }
                this.tableInfo.inEdit = 0;
                this.vueQueryParam.newDesc = this.tableInfo.newDesc;
                datasourceApi.updateTableDesc(this.vueQueryParam).then(() => {
                    this.tableInfo.description = this.tableInfo.newDesc;
                    this.$message.success("修改成功");
                });
            },
        }
    }
</script>

<style>
    .table-info-vue{padding: 0 20px;}
    .table-info-vue .el-dialog__body{padding: 0 20px 10px;}
    .table-info-vue .el-form-item{margin-bottom: 5px;}
    .table-info-vue .edit-table-desc{cursor: pointer; color: #409EFF;}
    .table-info-vue .description{cursor: pointer;min-height: 23px;}
    .table-info-vue .el-table td, .table-info-vue .el-table th{padding: 5px 0;}
    .table-info-vue .status-info-row{padding: 8px 0;}
    .table-info-vue .status-info-row .label{width: 80px;display: inline-block;text-align: right;color: #606266;}
</style>

