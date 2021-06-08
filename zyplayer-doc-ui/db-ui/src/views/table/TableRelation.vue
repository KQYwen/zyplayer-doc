<template>
	<div class="data-executor-vue">
		<div style="width: max(100%, 1000px); height: calc(100vh - 160px);overflow: auto;">
			<div id="relationChart" style="width: 1500px; height: 1000px;"></div>
		</div>
		<!--选择导出为update的条件列弹窗-->
		<el-dialog :visible.sync="choiceRelationColumnVisible" width="750px" :title="'选择关联的表字段 '+editNodeInfo.tableName+'.'+editNodeInfo.columnName">
			<div v-for="item in editRelationArr" style="margin-top: 10px;">
				<el-select v-model="item.dbName" @change="dbNameChangeEvents(item)" filterable placeholder="请选择数据库" style="margin-right: 10px;width: 200px;">
					<el-option v-for="db in databaseList" :key="db.dbName" :label="db.dbName" :value="db.dbName"></el-option>
				</el-select>
				<el-select v-model="item.tableName" @change="tableNameChangeEvents(item)" filterable placeholder="请选择数据表" style="margin-right: 10px;width: 200px;">
					<el-option v-for="table in dbTableList[item.dbName]" :key="table.tableName" :label="table.tableName" :value="table.tableName"></el-option>
				</el-select>
				<el-select v-model="item.columnName" filterable placeholder="请选择表字段" style="margin-right: 10px;width: 200px;">
					<el-option v-for="columns in dbTableColumnList[item.dbName+'.'+item.tableName]" :key="columns.name" :label="columns.name" :value="columns.name"></el-option>
				</el-select>
				<el-button icon="el-icon-delete" circle @click="delTableColumnRelation(item)"></el-button>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addTableColumnRelation">添加关联</el-button>
				<el-button type="primary" @click="doUpdateTableColumnRelation">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import * as echarts from 'echarts';
	import datasourceApi from '../../common/api/datasource'

	export default {
		name: 'tableRelation',
		data() {
			return {
				pageParam: {},
				relationChart: {},
				relationChartData: {},
				relationChartOption: {},
				editNodeInfo: {},
				editRelationArr: [],
				databaseList: [],
				dbTableList: {},
				dbTableColumnList: {},
				// 选择复制
				choiceRelationColumnVisible: false,
				columnListLoading: false,
			}
		},
		mounted () {
		},
		methods: {
			init(param) {
				if (this.pageParam.sourceId == param.sourceId) {
					return;
				}
				this.pageParam = param;
				this.relationChartData.name = param.tableName;
				// 基于准备好的dom，初始化echarts实例
				this.relationChart = echarts.init(document.getElementById('relationChart'));
				this.relationChartOption = {
					tooltip: {
						trigger: 'item',
						triggerOn: 'mousemove'
					},
					series: [
						{
							type: 'tree',
							id: 0,
							name: 'tree1',
							data: [this.relationChartData],
							top: '10%',
							left: '8%',
							bottom: '22%',
							right: '20%',
							symbolSize: 7,
							edgeShape: 'polyline',
							edgeForkPosition: '63%',
							initialTreeDepth: 3,
							lineStyle: {
								width: 2
							},
							label: {
								backgroundColor: '#fff',
								position: 'right',
								verticalAlign: 'middle',
								align: 'left'
							},
							leaves: {
								label: {
									position: 'right',
									verticalAlign: 'middle',
									align: 'left'
								}
							},
							emphasis: {
								focus: 'descendant'
							},
							expandAndCollapse: false,
							animationDuration: 550,
							animationDurationUpdate: 750
						}
					]
				};
				// 使用刚指定的配置项和数据显示图表。
				this.relationChart.setOption(this.relationChartOption);
				this.relationChart.on('click', (params) => {
					if (params.data.nodeType == 1) {
						this.editNodeInfo = params.data;
						let children = this.editNodeInfo.children || [];
						this.editRelationArr = [];
						if (children.length <= 0) {
							this.editRelationArr.push({dbName: this.pageParam.dbName, tableName: '', columnName: ''});
						} else {
							children.forEach(item => {
								this.editRelationArr.push({dbName: item.dbName, tableName: item.tableName, columnName: item.columnName});
							});
						}
						let dbNameArr = [], tableNameArr = [];
						this.editRelationArr.forEach(item => {
							if (dbNameArr.indexOf(item.dbName) < 0) {
								dbNameArr.push(item.dbName);
								this.dbNameChangeEvents(item);
							}
							if (tableNameArr.indexOf(item.dbName + '.' + item.tableName) < 0) {
								tableNameArr.push(item.dbName + '.' + item.tableName);
								this.tableNameChangeEvents(item);
							}
						});
						this.choiceRelationColumnVisible = true;
					}
				});
				this.relationChart.on('contextmenu', function (params) {
					console.log(params);
				});
				this.doGetTableColumnRelation();
				datasourceApi.databaseList({sourceId: this.pageParam.sourceId}).then(json => {
					this.databaseList = json.data || [];
				});
			},
			doGetTableColumnRelation() {
				// 先清空，不然直接重新渲染会有连接线清除不掉
				this.relationChart.clear();
				datasourceApi.getTableColumnRelation(this.pageParam).then(json => {
					this.relationChartData = json.data || {};
					this.relationChartOption.series[0].data = [json.data || {}];
					this.columnListLoading = false;
					this.relationChart.setOption(this.relationChartOption);
					setTimeout(() => this.relationChart.resize(), 0);
				});
			},
			delTableColumnRelation(item) {
				this.editRelationArr = this.editRelationArr.filter(re => re !== item);
			},
			addTableColumnRelation() {
				this.editRelationArr.push({dbName: this.pageParam.dbName, tableName: '', columnName: ''});
			},
			doUpdateTableColumnRelation() {
				let param = {
					dbName: this.editNodeInfo.dbName,
					tableName: this.editNodeInfo.tableName,
					columnName: this.editNodeInfo.columnName,
					sourceId: this.pageParam.sourceId,
					relation: JSON.stringify(this.editRelationArr)
				}
				datasourceApi.updateTableColumnRelation(param).then(json => {
					this.choiceRelationColumnVisible = false;
					this.doGetTableColumnRelation();
				});
			},
			dbNameChangeEvents(item) {
				datasourceApi.tableList({sourceId: this.pageParam.sourceId, dbName: item.dbName}).then(json => {
					this.$set(this.dbTableList, item.dbName, json.data || []);
				});
			},
			tableNameChangeEvents(item) {
				datasourceApi.tableColumnList({
					sourceId: this.pageParam.sourceId,
					dbName: item.dbName,
					tableName: item.tableName
				}).then(json => {
					this.$set(this.dbTableColumnList, item.dbName + '.' + item.tableName, json.data.columnList || []);
				});
			},
		}
	}
</script>

<style>
	.data-executor-vue .ace-monokai .ace_print-margin {
		display: none;
	}

	.data-executor-vue .el-card__body {
		padding: 10px;
	}

	.data-executor-vue .el-table td, .el-table th {
		padding: 6px 0;
	}

	.data-executor-vue .execute-result-table .el-input__inner {
		height: 25px;
		line-height: 25px;
		padding: 0 5px;
	}

	.data-executor-vue .execute-result-table .el-textarea__inner {
		height: 27px;
		min-height: 27px;
		line-height: 25px;
		padding: 0 5px;
		resize: none;
	}

	.data-executor-vue .execute-use-time {
		font-size: 12px;
		margin-right: 10px;
	}

	.data-executor-vue-out .el-tabs__nav-scroll {
		padding-left: 20px;
	}

	.data-executor-vue-out .el-button + .el-button {
		margin-left: 0px;
	}

	.data-executor-vue-out .el-table__body-wrapper {
		height: calc(100vh - 180px);
		overflow-y: auto;
	}
</style>
