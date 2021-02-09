<template>
	<div class="create-space-vue">
		<!--新建空间弹窗-->
		<el-dialog title="创建空间" :visible.sync="newSpaceDialogVisible" width="600px" :close-on-click-modal="false">
			<el-form label-width="100px" :model="newSpaceForm" :rules="newSpaceFormRules" ref="newSpaceForm">
				<el-form-item label="空间名：" prop="name">
					<el-input v-model="newSpaceForm.name"></el-input>
				</el-form-item>
				<el-form-item label="空间描述：" prop="spaceExplain">
					<el-input v-model="newSpaceForm.spaceExplain"></el-input>
				</el-form-item>
				<el-form-item label="空间开放：">
					<el-switch v-model="newSpaceForm.openDoc" inactive-text="需要登录" :inactive-value="0" active-text="开放访问" :active-value="1"></el-switch>
				</el-form-item>
				<el-form-item label="目录加载：">
					<el-switch v-model="newSpaceForm.treeLazyLoad" inactive-text="预先加载" :inactive-value="0" active-text="延迟加载" :active-value="1"></el-switch>
				</el-form-item>
				<el-form-item label="空间类型：">
					<el-select v-model="newSpaceForm.type" filterable placeholder="选择类型" style="width: 100%;">
						<el-option :key="1" label="公共空间" :value="1">
							<span style="float: left">公共空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px;">属于公共，登录用户可访问、编辑</span>
						</el-option>
						<el-option :key="2" label="个人空间" :value="2">
							<span style="float: left">个人空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px;">属于个人，所有登录用户可访问</span>
						</el-option>
						<el-option :key="3" label="隐私空间" :value="3">
							<span style="float: left">隐私空间</span>
							<span style="float: right; color: #8492a6; font-size: 13px;">属于个人，仅创建者可访问</span>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-if="newSpaceForm.id > 0" @click="onNewSpaceSubmit('newSpaceForm')">保存修改</el-button>
					<el-button type="primary" v-else @click="onNewSpaceSubmit('newSpaceForm')">立即创建</el-button>
					<el-button @click="onNewSpaceCancel">取消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	import pageApi from '../../common/api/page'

	export default {
		data() {
			return {
				newSpaceDialogVisible: false,
				manageSpaceDialogVisible: false,
				newSpaceForm: {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1},
				newSpaceFormRules: {
					name: [
						{required: true, message: '请输入空间名', trigger: 'blur'},
						{min: 2, max: 25, message: '长度在 2 到 25 个字符', trigger: 'blur'}
					],
				},
				editSpaceId: ''
			};
		},
		mounted() {
		},
		methods: {
			show(spaceId) {
				this.newSpaceForm = {id: '', name: '', spaceExplain: '', treeLazyLoad: 0, openDoc: 0, uuid: '', type: 1};
				this.editSpaceId = spaceId || '';
				if (!!this.editSpaceId) {
					pageApi.spaceList({id: this.editSpaceId}).then(json => {
						let spaceList = json.data || [];
						if (spaceList.length > 0) {
							this.newSpaceForm = spaceList[0];
						}
					});
				}
				this.newSpaceDialogVisible = true;
			},
			onNewSpaceSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						let param = {
							id: this.newSpaceForm.id,
							name: this.newSpaceForm.name,
							type: this.newSpaceForm.type,
							openDoc: this.newSpaceForm.openDoc,
							spaceExplain: this.newSpaceForm.spaceExplain,
							treeLazyLoad: this.newSpaceForm.treeLazyLoad,
						};
						pageApi.updateSpace(param).then(json => {
							this.$message.success("创建成功");
							this.newSpaceDialogVisible = false;
							this.$emit("success", json.data.id);
						});
					}
				});
			},
			onNewSpaceCancel() {
				this.newSpaceDialogVisible = false;
			},
		}
	}
</script>
<style>
	.create-space-vue .empty-news{text-align: center;padding: 100px;}

	.create-space-vue .text-link {
		color: #444;
		/*cursor: pointer;*/
		/*font-weight: bold;*/
	}
	.create-space-vue .line-box{color: #666;border-bottom: 1px solid #eee;padding: 20px 0;}
	.create-space-vue .line-title{font-size: 14px;}
	.create-space-vue .page-preview-box{}
	.create-space-vue .page-preview-title{font-size: 18px;margin: 10px 0 5px 0;color: #3a8ee6;cursor: pointer;}
	.create-space-vue .page-preview-content{font-size: 16px;margin-bottom: 5px;}
	.create-space-vue .zan-img{vertical-align: middle;margin-top: -3px;}
	.create-space-vue .view-img{font-size: 16px;color: #666;}

	.create-space-vue .page-info-box{text-align: right;margin: 20px 0 50px 0;}
</style>

