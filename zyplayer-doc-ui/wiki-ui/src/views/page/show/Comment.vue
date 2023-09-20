<template>
	<div class="comment-box" ref="actionTabCommentRef">
		<div v-if="commentList.length <= 0" class="action-box-empty">
			暂无评论
		</div>
		<div v-else class="comment-list">
			<el-timeline>
				<el-timeline-item :timestamp="comment.createTime" placement="top" v-for="comment in commentList">
					<el-card class="box-card comment-card" :body-style="{ padding: '10px' }">
						<div :style="'background-color: ' + comment.color" class="head">
							{{ comment.createUserName.substr(0, 1) }}
						</div>
						<div class="comment-user-name">
							{{ comment.createUserName }}
							<el-popconfirm v-if="canDeleteComment(comment)"
							               placement="top" width="160" trigger="click"
							               confirm-button-text="删除"
							               cancel-button-text="取消"
							               @confirm="deleteComment(comment.id)"
							               title="确定要删除此评论吗？">
								<template #reference>
									<el-icon class="icon-delete"><ElIconDelete /></el-icon>
								</template>
							</el-popconfirm>
						</div>
						<pre class="comment-content">{{ comment.content }}</pre>
					</el-card>
				</el-timeline-item>
			</el-timeline>
		</div>
	</div>
	<div class="comment-input-box">
		<textarea rows="5" placeholder="发表评论" v-model="commentTextInput" :maxlength="500"></textarea>
		<el-button style="float: right; margin: 2px 5px" type="primary" size="small" @click="submitPageComment">发送</el-button>
	</div>
</template>

<script setup>
import {
	Delete as ElIconDelete,
	Loading as ElIconLoading,
} from '@element-plus/icons-vue'
import {toRefs, ref, reactive, onMounted, watch, defineProps, defineEmits, defineExpose, computed} from 'vue';
import {onBeforeRouteUpdate, useRoute, useRouter} from "vue-router";
import { ElMessageBox, ElMessage, ElNotification } from 'element-plus';
import pageApi from '@/assets/api/page'
import {useStorePageData} from "@/store/pageData";
import {useStoreUserData} from "@/store/userData";

let page = {
	colorArr: ['#67C23A', '#409EFF', '#E6A23C', '#F56C6C', '#909399', '#303133'],
	userHeadColor: {},
}
// 评论相关
let commentTextInput = ref('');
let commentList = ref([]);
let recommentInfo = ref({});

let route = useRoute();
let router = useRouter();
let storePage = useStorePageData();
let storeUser = useStoreUserData();

watch(() => storePage.pageInfo, (newVal) => {
	if (storePage.pageInfo.editorType !== 0){
		loadCommentList();
	}
})
onMounted(() => {
	loadCommentList();
});
let actionTabCommentRef = ref();
const scrollActionTabComment = () => {
	setTimeout(() => {
		let actionTabComment = actionTabCommentRef.value
		actionTabComment.scrollTop = actionTabComment.scrollHeight
	}, 0)
}
const loadCommentList = () => {
	if (!storePage.pageInfo || !storePage.pageInfo.id) {
		return;
	}
	cancelCommentUser()
	pageApi.pageCommentList({pageId: storePage.pageInfo.id}).then((json) => {
		let commentListRes = json.data || []
		for (let i = 0; i < commentListRes.length; i++) {
			commentListRes[i].color = getUserHeadBgColor(commentListRes[i].createUserId)
			let subCommentList = commentListRes[i].commentList || []
			for (let j = 0; j < subCommentList.length; j++) {
				let subItem = subCommentList[j]
				subItem.color = getUserHeadBgColor(subItem.createUserId)
			}
			commentListRes[i].commentList = subCommentList
			commentListRes[i].visible = false
		}
		commentList.value = commentListRes
		scrollActionTabComment()
	})
}
const recommentUser = (id, index) => {
	recommentInfo.value = {
		id: id,
		index: index,
		placeholder: '回复' + (index + 1) + '楼',
	}
}
let canDeleteComment = (row) => {
	return (
		storeUser.userInfo.id === row.createUserId || storeUser.userInfo.id === storePage.pageInfo.createUserId
	)
}
const deleteComment = (id) => {
	pageApi.deletePageComment({id: id}).then(() => {
		// ElMessage.success("删除成功！");
		loadCommentList()
	})
}
const cancelCommentUser = () => {
	recommentInfo.value = {}
}
const submitPageComment = () => {
	if (commentTextInput.value.length <= 0) {
		ElMessage.error('请输入评论内容')
		return
	}
	let param = {
		pageId: storePage.pageInfo.id,
		content: commentTextInput.value,
		parentId: recommentInfo.value.id,
	}
	pageApi.updatePageComment(param).then((json) => {
		let data = json.data
		data.color = getUserHeadBgColor(data.createUserId)
		commentTextInput.value = ''
		loadCommentList()
	})
}
const getUserHeadBgColor = (userId) => {
	let color = page.userHeadColor[userId]
	if (!color) {
		color = page.colorArr[Math.ceil(Math.random() * page.colorArr.length) - 1]
		page.userHeadColor[userId] = color
	}
	return color
}
</script>

<style lang="scss">
.comment-box {
  padding: 8px;
  height: calc(100vh - 115px);
  overflow: auto;

  .comment-list {
	padding-bottom: 130px;
  }

  .comment-card {
	.comment-user-name {
	  margin-bottom: 10px;

	  .icon-delete {
		color: #888;
		font-size: 13px;
		cursor: pointer;
		float: right;
		display: none;
	  }
	}

	.comment-content {
	  padding: 0;
	  color: #666;
	  margin: 0;
	  white-space: pre-wrap;
	  word-wrap: break-word;
	  line-height: 20px;
	}
  }

  .comment-card:hover {
	.icon-delete {
	  display: inline-block;
	}
  }
}

.comment-input-box {
  position: absolute;
  bottom: 0;
  width: 100%;
  background: #fff;
  border-top: 1px solid #f1f1f1;

  textarea {
	resize: none;
	width: 100%;
	box-sizing: border-box;
	border: 0;
	outline: none !important;
	padding: 10px;
  }
}
</style>
