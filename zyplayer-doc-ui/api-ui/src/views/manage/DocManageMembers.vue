<template>
	<div style="text-align: right;margin: 0 10px 10px 0;">
		<a-button @click="searchDocMemberList" type="primary">
			<template #icon><search-outlined /></template> 查询
		</a-button>
		<a-button @click="openAddDocMember" :style="{ marginLeft: '8px' }">
			<template #icon><plus-outlined /></template> 添加用户
		</a-button>
	</div>
    <a-table :dataSource="docMemberList" :columns="docListColumns" size="middle"
             :loading="docMemberListLoading" :pagination="false"
             @change="handleTableChange"
             :scroll="{ x: 1000, y: 'calc(100vh - 300px)' }">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'operation'">
                <a-popconfirm title="确定要删除吗？" @confirm="deleteDocMember(record)">
                    <a-button size="small" type="link" danger>删除</a-button>
                </a-popconfirm>
            </template>
            <template v-if="column.dataIndex === 'sex'">
	            <a-tag color="pink" v-if="record.sex === 1">男</a-tag>
	            <a-tag color="red" v-else-if="record.sex === 0">女</a-tag>
	            <a-tag color="orange" v-else>-</a-tag>
            </template>
            <template v-if="column.dataIndex === 'authType'">
	            <a-select placeholder="请选择角色" v-model:value="record.authType" @change="userAuthTypeChange(record)" style="width: 150px;">
		            <a-select-option :value="1">管理员</a-select-option>
		            <a-select-option :value="2">开发人员</a-select-option>
	            </a-select>
            </template>
        </template>
    </a-table>
    <a-modal v-model:visible="addUserVisible" title="添加用户" @ok="handleAddUserOk" :width="600">
        <a-form layout="horizontal" ref="addUserFormRef" :model="userAdd" :rules="addUserRules" :label-col="{span: 4}" :wrapper-col="{span: 20}">
            <a-form-item label="选择用户" required name="userId">
	            <a-select
		            v-model:value="userAdd.userId"
		            show-search
		            placeholder="输入用户名、邮箱、手机号搜索"
		            :default-active-first-option="false"
		            :show-arrow="true"
		            :filter-option="false"
		            :not-found-content="undefined"
		            :options="userSearchList"
		            @search="handleUserSearch"
	            >
		            <template v-if="userSearchState.fetching" #notFoundContent>
			            <a-spin size="small" />
		            </template>
	            </a-select>
            </a-form-item>
	        <a-form-item label="用户角色" required name="authType">
		        <a-radio-group v-model:value="userAdd.authType">
			        <a-radio :value="1">管理员</a-radio>
			        <a-radio :value="2">开发人员</a-radio>
		        </a-radio-group>
	        </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { toRefs, ref, reactive, onMounted, watch } from 'vue';
    import {zyplayerApi} from '../../api';
    import {useStore} from 'vuex';
    import {getZyplayerApiBaseUrl} from "../../api/request/utils";
    import {SearchOutlined, PlusOutlined} from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue';

    export default {
		emits: ['showDocList'],
        components: {PlusOutlined, SearchOutlined},
	    props: {
		    doc: {
			    type: Object,
			    required: true
		    },
	    },
        setup(props, {emit}) {
            const store = useStore();
			// watch(() => props.doc, () => {
			// 	searchDocMemberList();
			// });
            let docMemberList = ref([]);
            let docMemberListLoading = ref(false);
            let searchParam = ref({docId: '', pageNum: 1, pageSize: 20});
			// 项目应该加不了很多的人，暂不分页
            let pagination = ref({
                pageSize: 20,
                pageNum: 1,
                total: 0,
                showSizeChanger: true,
                pageSizeOptions: ['20', '50', '100'],
                showTotal: total => `共${total}条`
            });
            const handleTableChange = (paginationNew, filters, sorter) => {
                pagination.value.pageNum = paginationNew.current;
                pagination.value.pageSize = paginationNew.pageSize;
                searchParam.value.pageNum = paginationNew.current;
                searchParam.value.pageSize = paginationNew.pageSize;
                searchDocMemberList();
            };
			const searchDocMemberList = async () => {
				if (!props.doc.id) {
					return;
				}
                docMemberListLoading.value = true;
	            searchParam.value.docId = props.doc.id;
                zyplayerApi.docAuthList(searchParam.value).then(res => {
                    setTimeout(() => docMemberListLoading.value = false, 500);
                    docMemberList.value = res.data || [];
                    pagination.value.total = res.total || 0;
				}).catch(() => {
					docMemberListLoading.value = false;
                });
            };
            let userAdd = ref({});
            let userSearchState = ref({
	            data: [],
	            search: '',
	            fetching: false,
            });
            let userSearchList = ref([]);
            let addUserFormRef = ref();
            let addUserVisible = ref(false);
	        const handleUserSearch = (search) => {
		        userSearchState.value.search = search;
		        if (userSearchState.value.fetching) {
			        return;
		        }
		        userSearchState.value.fetching = true;
		        userSearchList.value = [];
		        setTimeout(() => {
			        zyplayerApi.searchUserList({search: userSearchState.value.search}).then(res => {
				        let resArr = res.data || [];
				        resArr.forEach(item => userSearchList.value.push({label: item.userName, value: item.id}));
				        userSearchState.value.fetching = false;
					}).catch(() => {
						userSearchState.value.fetching = false;
			        });
		        }, 500);
            };
            const handleAddUserOk = async () => {
                addUserFormRef.value.validate().then(() => {
                    zyplayerApi.docAuthAssign(userAdd.value).then(res => {
                        searchDocMemberList();
                        addUserVisible.value = false;
                    });
                }).catch(error => {
                    console.log('error', error);
                });
            };
            const userAuthTypeChange = async (record) => {
				let param = {...record, docId: props.doc.id};
                zyplayerApi.docAuthAssign(param).then(res => {
					message.success('修改成功');
                });
            };
            const openAddDocMember = async () => {
                addUserVisible.value = true;
                userAdd.value = {docId: props.doc.id, userId: undefined, authType: 1};
            };
            const deleteDocMember = async (row) => {
	            zyplayerApi.docAuthDelete({docId: props.doc.id, userId: row.userId}).then(res => {
		            searchDocMemberList();
	            });
            };
            const showDocList = () => {
                emit('showDocList');
            }
            onMounted(() => {
                searchDocMemberList();
            });
            return {
	            showDocList,
                searchParam,
                docMemberList,
                docMemberListLoading,
                addUserVisible,
                userAdd,
	            userSearchList,
                addUserFormRef,
	            userSearchState,
	            handleUserSearch,
                searchDocMemberList,
                openAddDocMember,
                handleAddUserOk,
	            userAuthTypeChange,
                deleteDocMember,
                handleTableChange,
                pagination,
                docListColumns: [
                    {title: 'ID', dataIndex: 'userId', width: 70},
                    {title: '用户名', dataIndex: 'userName'},
                    {title: '帐号', dataIndex: 'userNo'},
                    {title: '邮箱', dataIndex: 'email'},
                    {title: '手机号', dataIndex: 'phone'},
                    {title: '性别', dataIndex: 'sex', width: 90},
                    {title: '角色', dataIndex: 'authType', width: 200},
                    {title: '操作', dataIndex: 'operation', fixed: 'right', width: 100},
                ],
	            addUserRules: {
		            userId: [{type: 'number', required: true, message: '请选择用户', trigger: 'change'}],
		            authType: [{type: 'number', required: true, message: '请选择用户角色', trigger: 'change'}],
	            },
            };
        },
    };
</script>
