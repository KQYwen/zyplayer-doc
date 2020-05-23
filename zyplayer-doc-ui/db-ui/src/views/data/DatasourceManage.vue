<template>
    <div style="padding: 0 10px;">
        <el-card>
            <div slot="header" class="clearfix">
                <span>数据源管理</span>
                <el-button style="float: right;margin-left: 5px;" :loading="loadDataListLoading" v-on:click="getDatasourceList" plain icon="el-icon-refresh" size="small">刷新</el-button>
                <el-button style="float: right;" v-on:click="addDatasource" type="primary" icon="el-icon-circle-plus-outline" size="small">新增</el-button>
            </div>
            <el-table :data="datasourceList" stripe border style="width: 100%; margin-bottom: 5px;">
                <el-table-column prop="name" label="名字" width="100"></el-table-column>
                <el-table-column prop="groupName" label="分组" width="100"></el-table-column>
                <el-table-column prop="driverClassName" label="驱动类" width="200"></el-table-column>
                <el-table-column prop="sourceUrl" label="数据源URL"></el-table-column>
                <el-table-column prop="sourceName" label="账号"></el-table-column>
                <el-table-column prop="sourcePassword" label="密码"></el-table-column>
                <el-table-column label="操作" width="220">
                    <template slot-scope="scope">
                        <el-button v-on:click="editDatasource(scope.row)" type="primary" size="mini">修改</el-button>
                        <el-button v-on:click="editDbAuth(scope.row)" type="success" size="mini">权限</el-button>
                        <el-button v-on:click="deleteDatasource(scope.row)" type="danger" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <!--增加数据源弹窗-->
        <el-dialog :inline="true" :title="newDatasource.id>0?'编辑数据源':'新增数据源'" :visible.sync="datasourceDialogVisible" width="760px">
            <el-form label-width="120px">
                <el-form-item label="分组：">
					<el-select v-model="newDatasource.groupName" placeholder="分组名字" style="width: 100%" filterable allow-create>
						<el-option value="">未分组</el-option>
						<el-option :value="item" v-for="item in datasourceGroupList"></el-option>
					</el-select>
                </el-form-item>
                <el-form-item label="数据源名称：">
                    <el-input v-model="newDatasource.name" placeholder="给数据源起个中文名称"></el-input>
                </el-form-item>
                <el-form-item label="驱动类：">
                    <el-select v-model="newDatasource.driverClassName" @change="driverClassNameChange" placeholder="驱动类" style="width: 100%">
                        <el-option label="com.mysql.jdbc.Driver" value="com.mysql.jdbc.Driver"></el-option>
                        <el-option label="net.sourceforge.jtds.jdbc.Driver" value="net.sourceforge.jtds.jdbc.Driver"></el-option>
                        <el-option label="oracle.jdbc.driver.OracleDriver" value="oracle.jdbc.driver.OracleDriver"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数据源URL：">
                    <el-input v-model="newDatasource.sourceUrl" :placeholder="urlPlaceholder"></el-input>
                </el-form-item>
                <el-form-item label="账号：">
                    <el-input v-model="newDatasource.sourceName" placeholder="账号"></el-input>
                </el-form-item>
                <el-form-item label="密码：">
                    <el-input v-model="newDatasource.sourcePassword" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="测试连接：">
                    <el-button v-on:click="testDatasource" type="primary" v-loading="testDatasourceErrLoading">测试数据源</el-button>
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
                <span style="margin-left: 10px;color: #999;font-size: 12px;"><i class="el-icon-info"></i> 添加删除或编辑之后记得点击保存哦~</span>
            </span>
            <el-row>
                <el-select v-model="dbSourceAuthNewUser" filterable remote reserve-keyword
                           placeholder="请输入名字、邮箱、账号搜索用户" :remote-method="getSearchUserList"
                           :loading="dbSourceAuthUserLoading" style="width: 750px;">
                    <el-option v-for="item in searchUserList" :key="item.id" :label="item.userName" :value="item.id"></el-option>
                </el-select>
                <el-button v-on:click="addDbSourceAuthUser">添加</el-button>
            </el-row>
            <el-table :data="dbSourceAuthUserList" border style="width: 100%; margin: 10px 0;">
                <el-table-column prop="userName" label="用户" width="150"></el-table-column>
                <el-table-column label="权限">
                    <template slot-scope="scope">
                        <el-radio v-model="scope.row.executeAuth" :label="0">无权限</el-radio>
                        <el-radio v-model="scope.row.executeAuth" :label="1">库表查看权</el-radio>
                        <el-radio v-model="scope.row.executeAuth" :label="2">数据查询权</el-radio>
                        <el-radio v-model="scope.row.executeAuth" :label="3">所有权限</el-radio>
                        <el-checkbox :true-label="1" :false-label="0" v-model="scope.row.descEditAuth">表字段注释修改权</el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <el-button size="small" type="danger" plain v-on:click="deleteUserDbSourceAuth(scope.row)">删除</el-button>
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
                datasourceList: [],
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
            };
        },
        mounted: function () {
            this.getDatasourceList();
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
					this.$message.warn("请先选择用户");
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
                    executeAuth: 0,
                    descEditAuth: 0,
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
                this.newDatasource = {name: "", driverClassName: "", sourceUrl: "", sourceName: "", sourcePassword: "", groupName: ""};
            },
            editDatasource(row) {
                this.newDatasource = JSON.parse(JSON.stringify(row));
                this.datasourceDialogVisible = true;
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
                }).catch(()=>{});
            },
            saveDatasource() {
                this.datasourceDialogVisible = false;
                datasourceApi.manageUpdateDatasource(this.newDatasource).then(() => {
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
                });
            },
            driverClassNameChange() {
                if (this.newDatasource.driverClassName == 'com.mysql.jdbc.Driver') {
                    this.urlPlaceholder = "例：jdbc:mysql://127.0.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8";
                } else if (this.newDatasource.driverClassName == 'net.sourceforge.jtds.jdbc.Driver') {
                    this.urlPlaceholder = "例：jdbc:jtds:sqlserver://127.0.0.1:33434;DatabaseName=user_info;socketTimeout=60;";
                } else if (this.newDatasource.driverClassName == 'oracle.jdbc.driver.OracleDriver') {
                    this.urlPlaceholder = "例：jdbc:oracle:thin:@127.0.0.1:1521/user_info";
                }
            },
            getDatasourceList() {
                this.loadDataListLoading = true;
				datasourceApi.manageDatasourceList({}).then(json => {
					this.datasourceList = json.data || [];
					let datasourceGroupList = [];
					this.datasourceList.filter(item => !!item.groupName).forEach(item => datasourceGroupList.push(item.groupName));
					this.datasourceGroupList = Array.from(new Set(datasourceGroupList));
					setTimeout(() => {
						this.loadDataListLoading = false;
                    }, 800);
                });
            },
        }
    }
</script>
<style>

</style>

