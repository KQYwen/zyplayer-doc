<template>
	<div class="global-layout-vue">
		<el-container>
			<el-aside v-show="storeDisplay.showMenu" :style="{ width: storeDisplay.rightAsideWidth + 'px' }">
				<LeftSideBar ref="leftSideBarRef"/>
			</el-aside>
			<RightResize v-show="storeDisplay.showMenu" v-model:value="storeDisplay.rightAsideWidth" @change="rightAsideWidthChange"></RightResize>
			<el-container>
				<el-header v-if="storeDisplay.showHeader">
					<RightHeader ref="rightHeaderRef"/>
				</el-header>
				<el-main style="padding: 0;">
					<router-view/>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script setup>
	import {useStoreDisplay} from '@/store/wikiDisplay.js'
	import LeftSideBar from './LeftSidebar.vue'
	import RightHeader from './RightHeader.vue'
	import RightResize from './RightResize.vue'
	let storeDisplay = useStoreDisplay();
	const rightAsideWidthChange = (width) =>{
		storeDisplay.rightAsideWidth = width
		storeDisplay.commentShow =width
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

	.hidTree {
		display: none;
	}

	#app,
	.el-container,
	.el-menu {
		height: 100%;
	}

	.el-header {
		background-color: #fff !important;
	}

	.header-right-user-name {
		color: #000000;
		vertical-align: middle;
	}

	.el-header {
		color: #333;
		height: 60px !important;
		border-bottom: 0.5px solid #eaeaea;
	}

	.head-icon {
		margin-right: 15px;
		margin-top: 15px;
		font-size: 16px;
		cursor: pointer;
		color: #000000;
		vertical-align: middle;
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
.space-folder-box {
  margin-left: 10px;
  margin-bottom: 10px;
  position: relative;
}

.wiki-page-tree-box {
  overflow-y: auto;
  overflow-x: hidden;
  padding-bottom: 30px;

  .el-tree-node__content {
	height: 35px;
	position: relative;

	.page-tree-node {
	  width: 100%;

	  .label {
		.el-icon {
		  vertical-align: middle;
		}

		.text {
		  margin-left: 5px;
		  vertical-align: middle;

		  max-width: calc(100% - 40px);
		  display: inline-block;
		  overflow: hidden;
		  text-overflow: ellipsis;
		  white-space: nowrap;
		}
	  }

	  .rename-input {
		width: 90%;
	  }

	  .page-action-box {
		position: absolute;
		right: 0;
		top: 0;
		height: 35px;
		line-height: 35px;
		background: #fff;
		border-radius: 4px;
		display: none;

		.page-action-dropdown-btn {
		  padding: 0 8px;
		  height: 35px;
		  margin-top: -1px;
		}

		.el-button + .el-button {
		  margin-left: 0;
		}
	  }

	  .page-action-box.renaming {
		display: none !important;
	  }
	}

	&:hover .page-action-box {
	  display: block;
	}
  }
}
</style>
