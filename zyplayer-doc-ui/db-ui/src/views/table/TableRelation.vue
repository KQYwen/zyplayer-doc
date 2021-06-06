<template>
	<div class="data-executor-vue">
		<div id="relationChart" style="width: 100%; height: max(calc(100vh - 160px), 600px);"></div>
		<!--选择导出为update的条件列弹窗-->
		<el-dialog :visible.sync="choiceRelationColumnVisible" width="500px" title="选择关联的表字段">
			<div>
				更新条件列：
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="choiceRelationColumnVisible = false">取 消</el-button>
				<el-button type="primary" @click="">确 定</el-button>
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
								position: 'left',
								verticalAlign: 'middle',
								align: 'right'
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
					console.log(params);
					var dataIndex = params.data.index;
					var description = params.data.description;
					if (!!params.data.columnName) {
						this.choiceRelationColumnVisible = true;
					} else {
						params.data.collapsed = true;
					}
				});
				this.relationChart.on('contextmenu', function (params) {
					console.log(params);
				});
				datasourceApi.tableColumnList(this.pageParam).then(json => {
					this.columnList = json.data.columnList || [];
					this.tableInfo = json.data.tableInfo || {};
					this.columnListLoading = false;
					this.changeTableRelationOption(this.relationChartData, this.columnList);
					this.relationChart.setOption(this.relationChartOption);
					setTimeout(() => this.relationChart.resize(), 0);
				});
			},
			changeTableRelationOption(data, columnList) {
				let childrenArr = [];
				for (let i = 0; i < columnList.length; i++) {
					let item = columnList[i];
					childrenArr.push({
						name: item.name,
						tableName: item.tableName,
						columnName: item.name,
						children: [],
						collapsed: false,
					});
				}
				data.children = childrenArr;
			}
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
