<template>
	<div style="padding: 0 10px;">
		<el-card>
			<el-form :inline="true">
				<el-form-item label="名字">
					<el-input v-model="searchParam.name" placeholder="名字"></el-input>
				</el-form-item>
				<el-form-item label="分组">
					<el-select v-model="searchParam.groupName" placeholder="分组">
						<el-option value="">全部</el-option>
						<el-option :value="item" v-for="(item,index) in datasourceGroupList" :key="index"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" :loading="loadDataListLoading" @click="getDatasourceList"
							   icon="el-icon-search">查询
					</el-button>
					<el-button @click="addDatasource" icon="el-icon-circle-plus-outline">新增</el-button>
				</el-form-item>
			</el-form>
			<el-table :data="datasourceList" stripe border style="width: 100%; margin-bottom: 5px;">
				<el-table-column prop="name" label="名字"></el-table-column>
				<el-table-column prop="groupName" label="分组"></el-table-column>
				<el-table-column prop="driverClassName" label="驱动类"></el-table-column>
				<el-table-column prop="sourceName" label="账号"></el-table-column>
				<el-table-column label="操作" width="220">
					<template slot-scope="scope">
						<el-button v-on:click="editDatasource(scope.row)" type="primary" size="mini">修改</el-button>
						<el-button v-on:click="editDbAuth(scope.row)" type="success" size="mini">权限</el-button>
						<el-button v-on:click="deleteDatasource(scope.row)" type="danger" size="mini">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				style="margin: 10px 0 20px 0;text-align: right;"
				@size-change="handlePageSizeChange"
				@current-change="handleCurrentChange"
				:current-page="currentPage"
				:page-sizes="[10, 30, 50]"
				:page-size="pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="tableTotalCount">
			</el-pagination>
			<!--增加数据源弹窗-->
			<el-dialog :inline="true" :title="newDatasource.id>0?'编辑数据源':'新增数据源'"
					   :visible.sync="datasourceDialogVisible" width="760px" :close-on-click-modal="false">
				<el-form label-width="120px">
					<el-form-item label="分组：">
						<el-select v-model="newDatasource.groupName" placeholder="请选择或输入新的分组名字"
								   style="width: 100%" filterable allow-create>
							<el-option value="">未分组</el-option>
							<el-option :value="item" v-for="(item,index) in datasourceGroupList"
									   :key="index"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="数据源名称：">
						<el-input v-model="newDatasource.name" placeholder="给数据源起个中文名称"></el-input>
					</el-form-item>
					<el-form-item label="驱动类：">
						<el-select v-model="newDatasource.driverClassName" @change="driverClassNameChange"
								   placeholder="驱动类" style="width: 100%">
							<el-option label="MYSQL: com.mysql.jdbc.Driver" value="com.mysql.jdbc.Driver"></el-option>
							<el-option label="SQLSERVER: net.sourceforge.jtds.jdbc.Driver"
									   value="net.sourceforge.jtds.jdbc.Driver"></el-option>
							<el-option label="ORACLE: oracle.jdbc.driver.OracleDriver"
									   value="oracle.jdbc.driver.OracleDriver"></el-option>
							<el-option label="POSTGRESQL: org.postgresql.Driver"
									   value="org.postgresql.Driver"></el-option>
							<el-option label="HIVE: org.apache.hive.jdbc.HiveDriver"
									   value="org.apache.hive.jdbc.HiveDriver"></el-option>
							<el-option label="达梦: dm.jdbc.driver.DmDriver"
									   value="dm.jdbc.driver.DmDriver"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="数据源URL：">
						<el-input v-model="newDatasource.sourceUrl" :placeholder="urlPlaceholder"
								  :disabled="sourceUrlDisabled&&!newDatasource.id>0">
							<el-button slot="append" @click="autoFillDialog"
									   :disabled="sourceUrlDisabled&&!newDatasource.id>0">智能填入
							</el-button>
						</el-input>
					</el-form-item>
					<el-form-item label="账号：">
						<el-input v-model="newDatasource.sourceName" placeholder="账号"></el-input>
					</el-form-item>
					<el-form-item label="密码：">
						<el-input v-model="newDatasource.sourcePassword" placeholder="密码"></el-input>
					</el-form-item>
					<el-form-item label="测试连接：">
						<el-button v-on:click="testDatasource" type="primary" v-loading="testDatasourceErrLoading">
							测试数据源
						</el-button>
					</el-form-item>
				</el-form>
				<div slot="footer" style="text-align: center;">
					<el-button v-on:click="saveDatasource" type="primary">保存</el-button>
					<el-button v-on:click="datasourceDialogVisible=false" plain>取消</el-button>
				</div>
			</el-dialog>
			<!--人员权限弹窗-->
			<el-dialog :visible.sync="dbSourceAuthDialogVisible" width="900px" :close-on-click-modal="false">
            <span slot="title">
                <span>权限编辑</span>
                <span style="margin-left: 10px;color: #999;font-size: 12px;"><i class="el-icon-info"></i> 添加、删除或编辑之后记得点击保存哦~</span>
            </span>
				<el-row>
					<el-select v-model="dbSourceAuthNewUser" filterable remote reserve-keyword
							   autoComplete="new-password"
							   placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList"
							   :loading="dbSourceAuthUserLoading" style="width: 750px;margin-right: 10px;">
						<el-option v-for="item in searchUserList" :key="item.id" :label="item.userName"
								   :value="item.id"></el-option>
					</el-select>
					<el-button v-on:click="addDbSourceAuthUser">添加</el-button>
				</el-row>
				<el-table :data="dbSourceAuthUserList" border style="width: 100%; margin: 10px 0;" size="mini">
					<el-table-column prop="userName" label="用户" width="150"></el-table-column>
					<el-table-column label="权限">
						<template slot-scope="scope">
							<el-select v-model="scope.row.executeAuth" placeholder="选择权限"
									   style="width: 150px;margin-right: 10px;">
								<el-option value="">无权限</el-option>
								<el-option :value="1" label="库表查看权"></el-option>
								<el-option :value="2" label="数据查询权"></el-option>
								<el-option :value="3" label="所有权限"></el-option>
							</el-select>
							<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.descEditAuth">
								表字段注释修改权
							</el-checkbox>
							<el-checkbox :true-label="1" :false-label="0" v-model="scope.row.procEditAuth">函数修改权
							</el-checkbox>
						</template>
					</el-table-column>
					<el-table-column label="操作" width="80">
						<template slot-scope="scope">
							<el-button size="small" type="danger" plain v-on:click="deleteUserDbSourceAuth(scope.row)">
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
				<div>
					<el-button type="primary" v-on:click="saveUserDbSourceAuth">保存配置</el-button>
				</div>
			</el-dialog>
			<!--错误信息弹窗-->
			<el-dialog title="测试数据源失败" :visible.sync="testDatasourceErrVisible" :footer="null" width="760px">
				<div v-highlight>
					<pre><code v-html="testDatasourceErrInfo"></code></pre>
				</div>
			</el-dialog>
			<!--数据源url地址自动输入弹窗-->
			<el-dialog
				title="智能填入"
				:visible.sync="autoFillDialogVisible"
				width="30%" :close-on-click-modal="false">
				<el-form :model="autoFillForm" :rules="rules" label-width="90px" ref="autoFillForm">
					<el-form-item label="主机地址" prop="hostIp">
						<el-input v-model="autoFillForm.hostIp" placeholder="请输入主机地址"></el-input>
					</el-form-item>
					<el-form-item label="端口号" prop="port">
						<el-input v-model="autoFillForm.port" placeholder="请输入数据库端口号"></el-input>
					</el-form-item>
					<el-form-item label="初始数据库" prop="initDatabaseName" v-if="initDatabaseNameShow">
						<el-input v-model="autoFillForm.initDatabaseName" placeholder="请输入初始数据库"></el-input>
					</el-form-item>
					<el-form-item label="服务名" prop="serverName" v-if="oracleServerNameShow">
						<template slot="label">
										<span>服务名
											<el-tooltip class="item" effect="dark" placement="right">
												<i class="el-icon-question"
												   style="font-size: 16px; vertical-align: middle;"></i>
												<div slot="content">
													<p>oracle数据库服务名默认为ORCL</p>
													<p>可使用下面的命令来查看服务名</p>
													<p>select global_name from global_name;</p>
												</div>
											</el-tooltip>
										</span>
						</template>

						<el-input v-model="autoFillForm.serverName" placeholder="请输入服务名"></el-input>
						<el-tooltip class="item" effect="dark" content="Top Left 提示文字" placement="top-start">
							<i class="el-icon-question-solid"/>
						</el-tooltip>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
    				<el-button @click="autoFillDialogVisible = false">取 消</el-button>
    				<el-button type="primary" @click="autoFill('autoFillForm')">确 定</el-button>
  				</span>
			</el-dialog>
		</el-card>
	</div>
</template>

<script>
import datasourceApi from '../../common/api/datasource'
import userApi from '../../common/api/user'

export default {
	data() {
		return {
			loadDataListLoading: false,
			datasourceDialogVisible: false,
			autoFillDialogVisible: false,
			datasourceList: [],
			searchParam: {
				name: '',
				groupName: ''
			},
			pageSize: 30,
			currentPage: 1,
			tableTotalCount: 0,
			newDatasource: {},
			urlPlaceholder: "数据源URL",

			dbSourceAuthDialogVisible: false,
			dbSourceAuthUserList: [],
			dbSourceAuthUserLoading: false,
			searchUserList: [],
			dbSourceAuthNewUser: "",
			// 测试数据源
			testDatasourceErrInfo: "",
			testDatasourceErrVisible: false,
			testDatasourceErrLoading: false,
			// 数据源分组
			datasourceGroupList: [],

			sourceUrlDisabled: true,

			//自动填入参数
			autoFillForm: {
				hostIp: null,
				port: null,
				serverName: null,
				initDatabaseName: null
			},
			//oracle数据库服务名是否显示
			oracleServerNameShow: false,
			//初始数据库
			initDatabaseNameShow: false,

			rules: {
				hostIp: [
					{required: true, message: '请输入主机地址', trigger: 'blur'}
				],
				port: [
					{required: true, message: '请输入数据库端口号', trigger: 'blur'}
				],
				serverName: [
					{required: true, message: '请输入数据库服务名', trigger: 'blur'}
				],
			}

		};
	},
	mounted: function () {
		this.getDatasourceList();
		this.getDatasourceGroupList();
	},
	methods: {
		editDbAuth(row) {
			this.newDatasource = JSON.parse(JSON.stringify(row));
			this.dbSourceAuthDialogVisible = true;
			this.loadDbAuthUserList();
		},
		loadDbAuthUserList() {
			this.dbSourceAuthNewUser = [];
			this.dbSourceAuthUserList = [];
			let param = {sourceId: this.newDatasource.id};
			datasourceApi.dbUserAuthList(param).then(json => {
				this.dbSourceAuthUserList = json.data || [];
			});
		},
		saveUserDbSourceAuth() {
			let param = {sourceId: this.newDatasource.id, authList: JSON.stringify(this.dbSourceAuthUserList)};
			datasourceApi.assignDbUserAuth(param).then(() => {
				this.$message.success("保存成功");
			});
		},

		autoFillDialog() {
			this.autoFillDialogVisible = true;
			let thatClassName = this.newDatasource.driverClassName;
			if (thatClassName === 'com.mysql.jdbc.Driver') {
				this.autoFillForm.port = "3306";
			} else if (thatClassName === 'net.sourceforge.jtds.jdbc.Driver') {
				this.autoFillForm.port = "1433";
				this.initDatabaseNameShow = true;
				this.autoFillForm.initDatabaseName = "master";
			} else if (thatClassName === 'oracle.jdbc.driver.OracleDriver') {
				this.autoFillForm.port = "1521";
				this.oracleServerNameShow = true;
				this.autoFillForm.serverName = "ORCL";
			} else if (thatClassName === 'org.postgresql.Driver') {
				this.autoFillForm.port = "5432";
				this.initDatabaseNameShow = true;
				this.autoFillForm.initDatabaseName = "postgres";
			} else if (thatClassName === 'org.apache.hive.jdbc.HiveDriver') {
				this.autoFillForm.port = "21050";
			} else if (thatClassName === 'dm.jdbc.driver.DmDriver') {
				this.autoFillForm.port = "5236";
			}
		},
		autoFill(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.autoFillDialogVisible = false;
					//拼接地址
					let thatClassName = this.newDatasource.driverClassName;
					let hostIp = this.autoFillForm.hostIp;
					let port = this.autoFillForm.port;
					let serverName = this.autoFillForm.serverName;
					let initDatabaseName = this.autoFillForm.initDatabaseName;
					if (thatClassName === 'com.mysql.jdbc.Driver') {
						this.newDatasource.sourceUrl = "jdbc:mysql://" + hostIp + ":" + port;
					} else if (thatClassName === 'net.sourceforge.jtds.jdbc.Driver') {
						this.newDatasource.sourceUrl = "jdbc:jtds:sqlserver://" + hostIp + ":" + port +";DatabaseName=" + initDatabaseName;
					} else if (thatClassName === 'oracle.jdbc.driver.OracleDriver') {
						this.newDatasource.sourceUrl = "jdbc:oracle:thin:@" + hostIp + ":" + port + "/" + serverName;
					} else if (thatClassName === 'org.postgresql.Driver') {
						this.newDatasource.sourceUrl = "jdbc:postgresql://" + hostIp + ":" + port+"/" + initDatabaseName;
					} else if (thatClassName === 'org.apache.hive.jdbc.HiveDriver') {
						this.newDatasource.sourceUrl = "jdbc:hive2://" + hostIp + ":" + port;
					} else if (thatClassName === 'dm.jdbc.driver.DmDriver') {
						this.newDatasource.sourceUrl = "jdbc:dm://" + hostIp + ":" + port;
					}
				} else {
					return false;
				}
			});

		},
		deleteUserDbSourceAuth(row) {
			var dbSourceAuthUserList = [];
			for (var i = 0; i < this.dbSourceAuthUserList.length; i++) {
				var item = this.dbSourceAuthUserList[i];
				if (item.userId != row.userId) {
					dbSourceAuthUserList.push(this.dbSourceAuthUserList[i]);
				}
			}
			this.dbSourceAuthUserList = dbSourceAuthUserList;
		},
		addDbSourceAuthUser() {
			if (this.dbSourceAuthNewUser.length <= 0) {
				this.$message.warning("请先选择用户");
				return;
			}
			var userName = "";
			for (var i = 0; i < this.searchUserList.length; i++) {
				if (this.dbSourceAuthNewUser == this.searchUserList[i].id) {
					userName = this.searchUserList[i].userName;
					break;
				}
			}
			this.dbSourceAuthUserList.push({
				userName: userName,
				userId: this.dbSourceAuthNewUser,
				executeAuth: '',
				descEditAuth: 0,
				procEditAuth: 0,
			});
			this.dbSourceAuthNewUser = "";
		},
		getSearchUserList(query) {
			if (!query) return;
			this.dbSourceAuthUserLoading = true;
			userApi.getUserBaseInfo({search: query}).then(json => {
				this.searchUserList = json.data || [];
				this.dbSourceAuthUserLoading = false;
			});
		},
		addDatasource() {
			this.datasourceDialogVisible = true;
			this.testDatasourceErrLoading = false;
			this.newDatasource = {
				name: "",
				driverClassName: "",
				sourceUrl: "",
				sourceName: "",
				sourcePassword: "",
				groupName: ""
			};
			this.sourceUrlDisabled = true;
		},
		editDatasource(row) {
			this.newDatasource = JSON.parse(JSON.stringify(row));
			this.datasourceDialogVisible = true;
			this.testDatasourceErrLoading = false;
		},
		deleteDatasource(row) {
			this.$confirm('确定要删除此数据源吗？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				row.yn = 0;
				datasourceApi.manageUpdateDatasource(row).then(() => {
					this.$message.success("删除成功！");
					this.$emit('loadDatasourceList');
					this.getDatasourceList();
				});
			}).catch(() => {
			});
		},
		saveDatasource() {
			datasourceApi.manageUpdateDatasource(this.newDatasource).then(() => {
				this.datasourceDialogVisible = false;
				this.$message.success("保存成功！");
				this.$emit('loadDatasourceList');
				this.getDatasourceList();
			});
		},
		testDatasource() {
			this.testDatasourceErrLoading = true;
			datasourceApi.queryTestDatasource(this.newDatasource).then(res => {
				this.testDatasourceErrLoading = false;
				if (res.errCode == 200) {
					this.$message.success("连接成功！");
				} else {
					this.testDatasourceErrVisible = true;
					this.testDatasourceErrInfo = res.errMsg || '';
				}
			}).catch(err => {
				this.testDatasourceErrLoading = false;
				this.testDatasourceErrVisible = true;
				this.testDatasourceErrInfo = err.message || '请求出错';
			});
		},
		driverClassNameChange() {
			let thatClassName = this.newDatasource.driverClassName;
			if (thatClassName === 'com.mysql.jdbc.Driver') {
				this.urlPlaceholder = "例：jdbc:mysql://127.0.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8";
			} else if (thatClassName === 'net.sourceforge.jtds.jdbc.Driver') {
				this.urlPlaceholder = "例：jdbc:jtds:sqlserver://127.0.0.1:1433;DatabaseName=user_info;socketTimeout=60;";
			} else if (thatClassName === 'oracle.jdbc.driver.OracleDriver') {
				this.urlPlaceholder = "例：jdbc:oracle:thin:@127.0.0.1:1521/serverName";
			} else if (thatClassName === 'org.postgresql.Driver') {
				this.urlPlaceholder = "例：jdbc:postgresql://127.0.0.1:5432/user_info";
			} else if (thatClassName === 'org.apache.hive.jdbc.HiveDriver') {
				this.urlPlaceholder = "例：jdbc:hive2://127.0.0.1:21050/user_info;auth=noSasl";
			} else if (thatClassName === 'dm.jdbc.driver.DmDriver') {
				this.urlPlaceholder = "例：jdbc:dm://127.0.0.1:5236?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8";
			}
			this.sourceUrlDisabled = false;
			this.oracleServerNameShow = false;
			this.initDatabaseNameShow = false;
		},
		handleCurrentChange(to) {
			this.currentPage = to;
			this.getDatasourceList();
		},
		handlePageSizeChange(to) {
			this.pageSize = to;
			this.getDatasourceList();
		},
		getDatasourceList() {
			this.loadDataListLoading = true;
			let param = {...this.searchParam, pageNum: this.currentPage, pageSize: this.pageSize};
			datasourceApi.manageDatasourceList(param).then(json => {
				if (this.currentPage == 1) {
					this.tableTotalCount = json.total || 0;
				}
				this.datasourceList = json.data || [];
				setTimeout(() => {
					this.loadDataListLoading = false;
				}, 800);
			}).catch(() => {
				this.loadDataListLoading = false;
			});
		},
		getDatasourceGroupList() {
			datasourceApi.manageDatasourceGroupList({}).then(json => {
				this.datasourceGroupList = json.data || [];
			});
		},
	}
}
</script>
<style scoped>
.demo-input-suffix {
	display: flex;
	margin-bottom: 10px;
}

.demo-input-suffix > span {
	width: 90px;
}
</style>

