<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="leftCollapse" :style="{ width: rightAsideWidth + 'px' }">
				<div style="padding: 10px;height: 100%;box-sizing: border-box;background: #fafafa;">
					<div style="margin-bottom: 10px">
						<el-select :model-value="choiceSpace" filterable placeholder="选择空间" style="width: 100%" @change="spaceChangeEvents">
							<el-option-group label="">
								<el-option key="0" label="创建空间" value="0"></el-option>
								<el-option key="-1" label="空间管理" value="-1"></el-option>
							</el-option-group>
							<el-option-group label="">
								<el-option v-for="item in spaceOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
							</el-option-group>
						</el-select>
					</div>
					<div align="center">
						<el-button :icon="ElIconPlus" style="width: 100%" v-on:click="createWiki">创建文档</el-button>
					</div>
					<el-autocomplete v-model="searchKeywords" :fetch-suggestions="doSearchByKeywords" placeholder="在当前空间搜索" popper-class="search-autocomplete" style="width: 100%; margin: 10px 0" @select="handleSearchKeywordsSelect">
						<template v-slot="{ item }">
							<div class="search-option-item">
								<div class="title">
									<span v-html="item.pageTitle || '-'"></span>
								</div>
								<span class="content" v-html="item.previewContent || '-'"></span>
							</div>
						</template>
					</el-autocomplete>
					<div class="wiki-page-tree-box">
						<el-tree
								ref="wikiPageTree"
								:current-node-key="nowPageId"
								:data="wikiPageList"
								:default-expanded-keys="wikiPageExpandedKeys"
								:expand-on-click-node="false"
								:filter-node-method="filterPageNode"
								:props="defaultProps"
								draggable
								highlight-current
								node-key="id"
								style="background-color: #fafafa"
								@node-click="handleNodeClick"
								@node-expand="handleNodeExpand"
								@node-drop="handlePageDrop"
						>
							<template v-slot="{ node, data }">
				                <span style="font-size: 14px">
				                  <el-icon><el-icon-document/></el-icon>
				                  <span style="margin-left: 6px">{{ node.label }}</span>
				                </span>
							</template>
						</el-tree>
					</div>
				</div>
			</el-aside>
			<RightResize v-show="leftCollapse" v-model:value="rightAsideWidth" @change="rightAsideWidthChange"></RightResize>
			<el-container>
				<el-header>
					<el-icon class="el-icon-s-fold">
						<el-icon-fold/>
					</el-icon>
					<el-icon class="el-icon-s-unfold">
						<el-icon-fold/>
					</el-icon>
					<span class="header-right-user-name">{{userSelfInfo.userName}}</span>
					<el-popover v-model="userMessagePopVisible" placement="bottom" trigger="click" width="600">
						<template v-slot:reference>
							<el-badge :is-dot="haveNotReadUserMessage" style="line-height: 20px; margin: 0 15px">
								<el-icon class="head-icon" style="margin-right: 0">
									<el-icon-bell/>
								</el-icon>
							</el-badge>
						</template>
						<div style="margin-bottom: 10px">
							<span style="font-size: 14px; font-weight: bold">通知</span>
							<el-link v-if="haveNotReadUserMessage" :icon="ElIconCheck" style="float: right" type="primary" v-on:click="readAllUserMessage">本页标记已读</el-link>
						</div>
						<div class="header-user-message">
							<el-table :data="userMessageList" border max-height="500" size="mini" style="width: 100%; margin-bottom: 5px">
								<el-table-column label="操作人" prop="operatorUserName" width="100px"></el-table-column>
								<el-table-column label="操作时间" prop="creationTime" width="140px"></el-table-column>
								<el-table-column label="内容">
									<template v-slot="scope">
										{{ scope.row.msgContent }}
<!--										TODO-->
<!--										<template v-slot:reference>-->
<!--											<el-badge :is-dot="scope.row.msgStatus == 0" style="line-height: 10px; padding-right: 5px">-->
<!--												<el-link type="primary" v-on:click="showUserMessage(scope.row)">查看</el-link>-->
<!--											</el-badge>-->
<!--										</template>-->
									</template>
								</el-table-column>
							</el-table>
							<div class="page-info-box">
								<el-pagination
										:current-page="userMsgParam.pageNum"
										:page-size="userMsgParam.pageSize"
										:total="userMsgTotalCount"
										layout="prev, pager, next, total"
										@current-change="handleCurrentChange">
								</el-pagination>
							</div>
						</div>
					</el-popover>
					<el-dropdown trigger="click" @command="userSettingDropdown">
						<el-icon class="head-icon">
							<el-icon-setting/>
						</el-icon>
						<template v-slot:dropdown>
							<el-dropdown-menu>
								<el-dropdown-item command="console">控制台</el-dropdown-item>
								<el-dropdown-item command="aboutDoc">关于</el-dropdown-item>
								<el-dropdown-item command="userSignOut" divided>退出登录</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
				</el-header>
				<el-main style="padding: 0; border-left: 1px solid #dcdfe6">
					<router-view
							v-slot="{ Component }"
							:spaceId="choiceSpace"
							:spaceInfo="getSpaceInfo(choiceSpace)"
							@changeExpandedKeys="changeWikiPageExpandedKeys"
							@loadPageList="loadPageList"
							@loadSpace="loadSpaceList"
							@switchSpace="switchSpacePage">
						<component :is="Component"></component>
					</router-view>
				</el-main>
			</el-container>
		</el-container>
		<create-space ref="createSpace" @success="loadSpaceList"></create-space>
		<about-dialog ref="aboutDialog"></about-dialog>
	</div>
</template>

<script>
import {
	Document as ElIconDocument,
	Fold as ElIconFold,
	Bell as ElIconBell,
	Setting as ElIconSetting,
	Plus as ElIconPlus,
	Check as ElIconCheck,
} from '@element-plus/icons-vue'
import userApi from '../../common/api/user'
import pageApi from '../../common/api/page'
import CreateSpace from '../space/CreateSpace'
import RightResize from './RightResize.vue'
import aboutDialog from '../../views/common/AboutDialog'
import {useStoreDisplay} from '@/store/wikiDisplay.js'

const storeDisplay = useStoreDisplay()

export default {
	data() {
		return {
			leftCollapse: true,
			rightContentLoading: false,
			pathIndex: [],
			defaultProps: {
				children: 'children',
				label: 'name',
			},
			// 空间搜索相关
			spaceOptions: [],
			spaceList: [],
			choiceSpace: '',
			nowSpaceShow: {},
			nowPageId: '',
			// 依据目录树存储的map全局对象
			treePathDataMap: new Map(),
			// 搜索的输入内容
			searchKeywords: '',
			// 页面展示相关
			wikiPageList: [],
			wikiPage: {},
			wikiPageExpandedKeys: [],
			userSelfInfo: {},
			userMessageList: [],
			haveNotReadUserMessage: false,
			userMessagePopVisible: false,
			userMsgTotalCount: 0,
			userMsgParam: {
				sysType: 2,
				pageNum: 1,
				pageSize: 20,
			},
			rightAsideWidth: 300,
			ElIconPlus,
			ElIconCheck,
		}
	},
	components: {
		RightResize,
		'create-space': CreateSpace,
		'about-dialog': aboutDialog,
		ElIconDocument,
		ElIconFold,
		ElIconBell,
		ElIconSetting,
	},
	computed: {},
	mounted() {
		this.loadSpaceList()
		this.loadUserMessageList()
		this.getSelfUserInfo()
	},
	methods: {
		loadPageList(param) {
			param = param || {}
			this.doGetPageList(param.parentId, param.node)
		},
		turnLeftCollapse() {
			this.leftCollapse = !this.leftCollapse
			setTimeout(() => {
				if (this.leftCollapse) {
					this.rightAsideWidthChange(this.rightAsideWidth + 1)
				} else {
					this.rightAsideWidthChange(1)
				}
			}, 100)
		},
		createWiki() {
			if (this.choiceSpace > 0) {
				this.$router.push({
					path: '/page/edit',
					query: {parentId: this.nowPageId},
				})
			} else {
				this.$message.warning('请先选择或创建空间')
			}
		},
		changeWikiPageExpandedKeys(pageId) {
			// 展开没有触发子节点的加载，如果去加载子节点有还找不到当前的node，暂不展开
			// this.wikiPageExpandedKeys = [pageId];
		},
		doSearchByKeywords(queryString, callback) {
			if (!queryString || !queryString.trim()) {
				callback([])
				return
			}
			pageApi
				.pageNews({spaceId: this.choiceSpace, keywords: queryString})
				.then((json) => {
					let spacePageNews = json.data || []
					callback(spacePageNews)
				})
		},
		handleSearchKeywordsSelect(item) {
			this.searchKeywords = ''
			this.$router.push({path: '/page/show', query: {pageId: item.pageId}})
		},
		searchByKeywords() {
			this.$refs.wikiPageTree.filter(this.searchKeywords)
		},
		searchByKeywordsNewPage() {
			var routeUrl = this.$router.resolve({
				path: '/page/search',
				query: {keywords: this.searchKeywords},
			})
			window.open(routeUrl.href, '_blank')
		},
		handleNodeClick(data) {
			console.log('点击节点：', data, this.nowPageId)
			this.nowPageId = data.id
			this.$router.push({path: '/page/show', query: {pageId: data.id}})
			this.handleNodeExpand(data)
		},
		handleNodeExpand(node) {
			if (
				node.children &&
				node.children.length > 0 &&
				node.children[0].needLoad
			) {
				console.log('加载节点：', node)
				this.doGetPageList(node.id, node)
			}
		},
		handlePageDrop(draggingNode, dropNode, dropType, ev) {
			console.log('tree drop: ', draggingNode.data, dropNode.data, dropType)
			// 'prev'、'inner'、'next'
			// before、after、inner
			var param = {id: draggingNode.data.id, parentId: dropNode.data.parentId}
			if (dropType == 'inner') {
				param.parentId = dropNode.data.id
			} else if (dropType == 'before') {
				param.beforeSeq = dropNode.data.seqNo
			} else if (dropType == 'after') {
				param.afterSeq = dropNode.data.seqNo
			}
			pageApi.pageChangeParent(param).then((res) => {
				this.doGetPageList(null)
			})
		},
		loadUserMessageIfPopVisible() {
			if (!this.userMessagePopVisible) {
				this.loadUserMessageList()
			}
		},
		loadUserMessageList() {
			userApi.getUserMessageList(this.userMsgParam).then((res) => {
				this.userMessageList = res.data || []
				this.userMsgTotalCount = res.total || 0
				this.haveNotReadUserMessage =
					this.userMessageList.filter((item) => item.msgStatus == 0).length > 0
			})
		},
		showUserMessage(row) {
			if (row.msgStatus == 0) {
				userApi.readUserMessage({ids: row.id}).then(() => {
					this.loadUserMessageList()
				})
			}
			if (row.msgType >= 2 && row.msgType <= 12) {
				this.$router.push({path: '/page/show', query: {pageId: row.dataId}})
				this.userMessagePopVisible = false
			}
		},
		readAllUserMessage() {
			let msgIds = []
			this.userMessageList
				.filter((item) => item.msgStatus == 0)
				.forEach((item) => {
					msgIds.push(item.id)
				})
			if (msgIds.length <= 0) return
			userApi.readUserMessage({ids: msgIds.join(',')}).then(() => {
				this.$message.success('标记成功')
				this.loadUserMessageList()
			})
		},
		handleCurrentChange(val) {
			this.userMsgParam.pageNum = val
			this.loadUserMessageList()
		},
		filterPageNode(value, data) {
			if (!value || !data.name) return true
			// issues:I2CG72 忽略大小写
			let name = data.name.toLowerCase()
			return name.indexOf(value.toLowerCase()) !== -1
		},
		spaceChangeEvents(data) {
			if (data == 0) {
				// 新建空间
				this.$refs.createSpace.show()
			} else if (data == -1) {
				// 管理空间
				this.$router.push({path: '/space/manage'})
			} else {
				this.choiceSpace = data
				for (let i = 0; i < this.spaceList.length; i++) {
					if (this.spaceList[i].id == data) {
						this.nowSpaceShow = this.spaceList[i]
						break
					}
				}
				this.nowPageId = ''
				this.doGetPageList(null)
				this.$router.push({path: '/home', query: {spaceId: data}})
			}
		},
		loadSpaceList(spaceId) {
			pageApi.spaceList({}).then((json) => {
				this.spaceList = json.data || []
				let spaceOptions = []
				this.spaceList.forEach((item) =>
					spaceOptions.push({label: item.name, value: item.id})
				)
				this.spaceOptions = spaceOptions
				if (this.spaceList.length > 0) {
					let nowSpaceId = spaceId
					let nowSpaceShow = this.spaceList.find((item) => item.id == spaceId)
					if (!nowSpaceShow) {
						nowSpaceShow = this.spaceList[0]
						nowSpaceId = nowSpaceShow.id
					}
					this.nowSpaceShow = nowSpaceShow
					this.choiceSpace = nowSpaceId
					this.nowPageId = ''
					this.doGetPageList(null)
					// TODO 在首页时跳转
					try {
						if (this.$router.app._route.path == '/home') {
							this.$router.push({
								path: '/home',
								query: {spaceId: nowSpaceId},
							})
						}
					} catch (e) {
						console.log(e)
					}
				}
			})
		},
		doGetPageList(parentId, node) {
			let param = {spaceId: this.choiceSpace}
			pageApi.pageList(param).then((json) => {
				this.wikiPageList = json.data || []
				// 设置默认选中效果
				this.$nextTick(() => {
					this.nowPageId = this.$route.query.pageId
					if (this.nowPageId) {
						console.log('moern?')
						this.$refs['wikiPageTree'].setCurrentKey(this.nowPageId)
					}
				})
			})
		},
		userSettingDropdown(command) {
			console.log('command:' + command)
			if (command == 'userSignOut') {
				this.userSignOut()
			} else if (command == 'aboutDoc') {
				this.$refs.aboutDialog.show()
			} else if (command == 'myInfo') {
				this.$router.push({path: '/user/myInfo'})
			} else if (command == 'console') {
				window.open(process.env.VUE_APP_BASE_API, '_blank')
			} else {
				this.$message.warning('暂未开放')
			}
		},
		userSignOut() {
			userApi.userLogout().then(() => {
				location.reload()
			})
		},
		getSelfUserInfo() {
			userApi.getSelfUserInfo().then((json) => {
				this.userSelfInfo = json.data
			})
		},
		getSpaceInfo(spaceId) {
			for (let i = 0; i < this.spaceList.length; i++) {
				if (this.spaceList[i].id == spaceId) {
					return this.spaceList[i]
				}
			}
			return {}
		},
		switchSpacePage(spaceId) {
			spaceId = parseInt(spaceId)
			if (this.choiceSpace == spaceId) {
				return
			}
			this.choiceSpace = spaceId
			this.doGetPageList(null)
		},
		rightAsideWidthChange(width) {
			storeDisplay.viewMenuWidth = width
		},
	},
}
</script>

<style>
html,
body {
	margin: 0;
	padding: 0;
	height: 100%;
}

.global-layout-vue {
	height: 100%;
}

#app,
.el-container,
.el-menu {
	height: 100%;
}

.el-header {
	background-color: #1d4e89 !important;
}

.header-right-user-name {
	color: #fff;
	padding-right: 5px;
}

.el-header {
	color: #333;
	line-height: 40px;
	text-align: right;
	height: 40px !important;
}

.el-icon-fold {
	float: left;
	font-size: 25px;
	color: #aaa;
	margin-top: 8px;
	cursor: pointer;
}

.el-icon-fold:hover {
	color: #eee;
}

.head-icon {
	margin-right: 15px;
	font-size: 16px;
	cursor: pointer;
	color: #fff;
}

.header-user-message .page-info-box {
	text-align: right;
	margin-top: 10px;
}

.upgrade-info {
	max-height: 150px;
	overflow-y: auto;
	word-break: break-all;
	white-space: pre-wrap;
	line-height: 26px;
}

.search-option-item {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.search-option-item .title {
	font-weight: bold;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.search-option-item .content {
	font-size: 12px;
	color: #888;
}

.search-autocomplete {
	width: 600px !important;
}
</style>

<style lang="scss">
.wiki-page-tree-box {
  overflow: auto;
  padding-bottom: 30px;

  .el-tree-node {
	.el-tree-node__content {
	  height: 35px;

	  .el-tree-node__label {
		width: 100%;
	  }
	}
  }
}
</style>
