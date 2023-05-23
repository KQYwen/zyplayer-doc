<template>
    <div>
        <el-tabs v-model="activePage" type="card" closable @tab-click="changePage" @tab-remove="removePageTab" style="padding: 5px 10px 0;">
            <el-tab-pane :label="pageTabNameMap[item.fullPath]||item.name" :name="getRouteRealPath(item)" :fullPath="item.fullPath" :key="item.fullPath" v-for="item in pageList"/>
        </el-tabs>
        <keep-alive>
            <router-view v-on:listenToChildEvent = "getDatafromChild" :key="$route.fullPath" @initLoadDataList="initLoadDataList" @loadDatasourceList="loadDatasourceList"/>
        </keep-alive>
    </div>
</template>

<script>
    import datasourceApi from "@/common/api/datasource";

	export default {
        name: 'PageTableView',
        components: {},
        data() {
            return {
                pageList: [],
                linkList: [],
                activePage: '',
			  datasourceList: [],
				multiPage: true,
				ignoreParamPath: [
					"/data/export",
				],
            }
        },
        computed: {
            pageTabNameMap () {
                return this.$store.state.global.pageTabNameMap;
            }
		},
		created() {
			let {name, path, fullPath} = this.$route;
			//sql执行器tab页名称动态变化
			if(path === '/data/executor'){
				let database = this.$route.query.database;
				let datasourceId = this.$route.query.datasourceId;
				if(datasourceId){
					datasourceApi.datasource({sourceId: datasourceId}).then(json => {
						let dataname = json.data.name;
						let groupName = json.data.name;
						if(dataname){
			  			name = name+"( "+dataname+"["+database+"] )"
						}
						this.pageList.push({name, path, fullPath});
						let activePage = this.getRouteRealPath(this.$route);
						this.linkList.push(activePage);
						this.activePage = activePage;
						this.$router.push(this.$route.fullPath);
					})
		  		return false;
				}else{
		  		datasourceApi.datasourceList({}).then(json => {
						this.datasourceList = json.data || [];
						if (this.datasourceList.length > 0) {
			  			let dataname = this.datasourceList[0].name;
						  if(this.datasourceList[0].id){
								datasourceApi.databaseList({sourceId: this.datasourceList[0].id}).then(json => {
									if (json.data.length > 0) {
										// 排除系统库
										let sysDbName = ["information_schema", "master", "model", "msdb", "tempdb"];
										let notSysDbItem = json.data.find(item => sysDbName.indexOf(item.dbName) < 0);
										let choiceDatabase = (!!notSysDbItem) ? notSysDbItem.dbName : json.data[0].dbName;
										if(dataname&&choiceDatabase){
											name = name+"( "+dataname+"["+choiceDatabase+"] )"
										}
										this.$router.replace({ query: { datasourceId: this.datasourceList[0].id,database:choiceDatabase } })
				  				}
								})
							}
						}
					})
		  		return false;
				}
			}
				this.pageList.push({name, path, fullPath});
				let activePage = this.getRouteRealPath(this.$route);
				this.linkList.push(activePage);
				this.activePage = activePage;
				this.$router.push(this.$route.fullPath);
		},
		watch: {
			'$route': function (newRoute, oldRoute) {
				let activePage = this.getRouteRealPath(newRoute);
				this.activePage = activePage;
				if (this.linkList.indexOf(activePage) < 0) {
					this.linkList.push(activePage);
					let {name, path, fullPath} = newRoute;
		  		this.pageList.push({name, path, fullPath});
					//sql执行器tab页名称动态变化
					if(path === '/data/executor'){
						let database = newRoute.query.database;
						let datasourceId = newRoute.query.datasourceId;
						if(datasourceId) {
							datasourceApi.datasource({sourceId: datasourceId}).then(json => {
							let dataname = json.data.name;
							let groupName = json.data.groupName;
							if (dataname) {
								name = name+"( "+dataname+"["+database+"] )"
							}
								//this.pageList.push({name, path, fullPath});
								let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
								pageRoute.name = name;

							})
						}else{
							datasourceApi.datasourceList({}).then(json => {
								this.datasourceList = json.data || [];
								if (this.datasourceList.length > 0) {
									let dataname = this.datasourceList[0].name;
									if(this.datasourceList[0].id){
									datasourceApi.databaseList({sourceId: this.datasourceList[0].id}).then(json => {
										if (json.data.length > 0) {
											// 排除系统库
											let sysDbName = ["information_schema", "master", "model", "msdb", "tempdb"];
											let notSysDbItem = json.data.find(item => sysDbName.indexOf(item.dbName) < 0);
											let choiceDatabase = (!!notSysDbItem) ? notSysDbItem.dbName : json.data[0].dbName;
											if(dataname&&choiceDatabase){
												name = name+"( "+dataname+"["+choiceDatabase+"] )"
											}
											console.log("this.pageList+++++++++"+JSON.stringify(this.pageList))
					  					this.pageList.splice(this.pageList.findIndex(item => item.fullPath === path),1);
					  					this.linkList.splice(this.linkList.findIndex(item => item === path),1);
					  					console.log("this.pageList+++++++++"+JSON.stringify(this.pageList))
											this.$router.replace({ query: { datasourceId: this.datasourceList[0].id,database:choiceDatabase } })
										}
									})
									}
								}
							})
						}
					}

                }
				//let pageRoute = this.pageList.find(item => this.getRouteRealPath(item) === activePage);
				//pageRoute.fullPath = newRoute.fullPath;
            },
        },
        methods: {
		  getDatafromChild(data){
				this.datasourceList = data;
			},
			initLoadDataList(param) {
                this.$emit('initLoadDataList', param);
            },
			loadDatasourceList() {
                this.$emit('loadDatasourceList');
            },
			isIgnoreParamPath(path) {
				return this.ignoreParamPath.indexOf(path) >= 0;
            },
			getRouteRealPath(route) {
				return this.isIgnoreParamPath(route.path) ? route.path : route.fullPath;
            },
            changePage(tab) {
                this.activePage = tab.name;
				this.$router.push(tab.$attrs.fullPath);

            },
            editPage(key, action) {
                this[action](key);
            },
            removePageTab(key) {
                if (this.pageList.length === 1) {
                    this.$message.warning('这是最后一页，不能再关闭了啦');
                    return;
				}
				this.pageList = this.pageList.filter(item => this.getRouteRealPath(item) !== key);
				this.linkList = this.linkList.filter(item => item !== key);
				let index = this.linkList.indexOf(this.activePage);
				if (index < 0) {
					index = this.linkList.length - 1;
					this.activePage = this.linkList[index];
					this.$router.push(this.activePage);
				}
            },
        }
    }
</script>

<style scoped>
/deep/ .el-tabs--card>.el-tabs__header{
  margin: 0 0 5px;
}
/deep/ .el-tabs--card>.el-tabs__header .el-tabs__item{
  height: 30px;
  line-height: 30px;
}
/deep/ .el-tabs__nav-next{
  line-height: 33px;
  font-size: 20px;
}
/deep/ .el-tabs__nav-prev{
  line-height: 33px;
  font-size: 20px;
}
</style>
