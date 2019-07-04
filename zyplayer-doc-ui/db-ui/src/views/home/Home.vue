<template>
    <div style="padding: 10px;">
        <div style="max-width: 1200px;margin: 20px auto;">
            <div style="text-align: center;">欢迎使用ヾ(๑╹◡╹)ﾉ"</div>
            <el-card style="margin: 10px;">
                <div slot="header" class="clearfix">
                    <span>数据源管理</span>
                    <el-button style="float: right;" v-on:click="addDatasource" type="primary" icon="el-icon-circle-plus-outline" size="small">新增</el-button>
                </div>
                <el-table :data="datasourceList" stripe border style="width: 100%; margin-bottom: 5px;">
                    <el-table-column prop="driverClassName" label="驱动类" width="200"></el-table-column>
                    <el-table-column prop="sourceUrl" label="数据源URL"></el-table-column>
                    <el-table-column prop="sourceName" label="账号"></el-table-column>
                    <el-table-column prop="sourcePassword" label="密码"></el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-button v-on:click="editDatasource(scope.row)" type="primary" size="small">修改</el-button>
                            <el-button v-on:click="deleteDatasource(scope.row)" type="danger" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </div>
        <!--增加数据源弹窗-->
        <el-dialog :inline="true" :title="newDatasource.id>0?'编辑数据源':'新增数据源'" :visible.sync="datasourceDialogVisible" width="600px">
            <el-form label-width="120px">
                <el-form-item label="驱动类：">
                    <el-select v-model="newDatasource.driverClassName" placeholder="驱动类" style="width: 100%">
                        <el-option label="com.mysql.jdbc.Driver" value="com.mysql.jdbc.Driver"></el-option>
                        <el-option label="net.sourceforge.jtds.jdbc.Driver" value="net.sourceforge.jtds.jdbc.Driver"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数据源URL：">
                    <el-input v-model="newDatasource.sourceUrl" placeholder="数据源URL"></el-input>
                </el-form-item>
                <el-form-item label="账号：">
                    <el-input v-model="newDatasource.sourceName" placeholder="账号"></el-input>
                </el-form-item>
                <el-form-item label="密码：">
                    <el-input v-model="newDatasource.sourcePassword" placeholder="密码"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" style="text-align: center;">
                <el-button v-on:click="saveDatasource" type="primary">保存</el-button>
                <el-button v-on:click="datasourceDialogVisible=false" plain>取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import toast from '../../common/lib/common/toast'
    import global from '../../common/config/global'

    var app;

    export default {
        data() {
            return {
                datasourceDialogVisible: false,
                datasourceList: [],
                newDatasource: {},
            };
        },
        mounted: function () {
            app = this;
            this.getDatasourceList();
        },
        methods: {
            addDatasource() {
                this.datasourceDialogVisible = true;
            },
            editDatasource(row) {
                this.newDatasource = row;
                this.datasourceDialogVisible = true;
            },
            deleteDatasource(row) {
                this.$confirm('确定要删除此数据源吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    var datasourceList = [];
                    for (var i = 0; i < this.datasourceList.length; i++) {
                        if (this.datasourceList[i].id != row.id) {
                            datasourceList.push(this.datasourceList[i]);
                        }
                    }
                    this.datasourceList = datasourceList;
                    this.$message.success("删除成功！");
                }).catch(()=>{});
            },
            saveDatasource() {
                this.datasourceDialogVisible = false;
                this.common.post(this.apilist1.manageUpdateDatasource, this.newDatasource, function (json) {
                    app.$message.success("保存成功！");
                    app.getDatasourceList();
                });
            },
            getDatasourceList() {
                this.common.post(this.apilist1.manageDatasourceList, {}, function (json) {
                    app.datasourceList = json.data || [];
                });
            },
        }
    }
</script>
<style>

</style>

