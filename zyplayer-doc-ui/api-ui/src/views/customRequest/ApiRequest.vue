<template>
	<div class="api-name-box">
		<a-row type="flex">
			<a-col flex="auto"><a-input v-model:value="docInfoShow.nodeName" :bordered="false" placeholder="请输入接口名称" /></a-col>
			<a-col flex="88px">
				<a-button @click="saveCustomRequest" type="dashed"><save-outlined /> 保存</a-button>
			</a-col>
		</a-row>
	</div>
    <a-input-search v-model:value="docInfoShow.apiUrl" @search="sendRequest" placeholder="请输入目标URL地址">
        <template #addonBefore>
            <a-select v-model:value="docInfoShow.method" style="width: 100px;">
                <a-select-option :value="method" v-for="method in methodList">{{method.toUpperCase()}}</a-select-option>
            </a-select>
        </template>
        <template #enterButton>
            <a-button type="primary" :loading="requestLoading">发送请求</a-button>
        </template>
    </a-input-search>
    <a-tabs v-model:activeKey="activePage" closable @tab-click="activePageChange" style="padding: 5px 10px 0;">
        <a-tab-pane tab="URL参数" key="urlParam" forceRender>
            <div v-show="queryParamVisible">
                <ParamTable ref="urlParamRef" :paramList="urlParamList"></ParamTable>
            </div>
        </a-tab-pane>
        <a-tab-pane tab="Body参数" key="bodyParam" v-if="docInfoShow.method !== 'get'" forceRender>
            <div v-show="queryParamVisible">
                <div style="margin-bottom: 6px;">
                    <a-radio-group v-model:value="bodyParamType">
                        <a-radio value="none">none</a-radio>
                        <a-radio value="form">form-data</a-radio>
                        <a-radio value="formUrlEncode">x-www-form-urlencoded</a-radio>
                        <a-radio value="row">row</a-radio>
                        <a-radio value="binary">binary</a-radio>
                    </a-radio-group>
                    <a-select v-if="bodyParamType === 'row'" v-model:value="consumesParamType" size="small" style="margin-left: 10px;vertical-align: top;width: 100px;">
                        <a-select-option value="json">JSON</a-select-option>
                        <a-select-option value="html">HTML</a-select-option>
                        <a-select-option value="xml">XML</a-select-option>
                        <a-select-option value="javascript">JavaScript</a-select-option>
                        <a-select-option value="text">TEXT</a-select-option>
                    </a-select>
                </div>
                <div v-show="bodyParamType === 'form'">
                    <ParamTable ref="formParamRef" :paramList="formParamList" showType></ParamTable>
                </div>
                <div v-show="bodyParamType === 'formUrlEncode'">
                    <ParamTable ref="formEncodeParamRef" :paramList="formEncodeParamList"></ParamTable>
                </div>
                <div v-show="bodyParamType === 'row'">
                    <ParamBody ref="bodyParamRef" :rowLang="consumesParamType" :paramList="bodyRowParamList"></ParamBody>
                </div>
            </div>
        </a-tab-pane>
        <a-tab-pane tab="Header参数" key="headerParam" forceRender>
            <div v-show="queryParamVisible">
                <ParamTable ref="headerParamRef" :paramList="headerParamList"></ParamTable>
            </div>
        </a-tab-pane>
        <a-tab-pane tab="Cookie参数" key="cookieParam" forceRender>
            <div v-show="queryParamVisible">
                <ParamTable ref="cookieParamRef" :paramList="cookieParamList"></ParamTable>
            </div>
        </a-tab-pane>
        <template #rightExtra>
            <a-button v-if="queryParamVisible" @click="hideQueryParam" type="link">收起参数</a-button>
            <a-button v-else @click="showQueryParam" type="link">展开参数</a-button>
        </template>
    </a-tabs>
    <ApiRequestResult :result="requestResult" :loading="requestLoading"></ApiRequestResult>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import ApiRequestResult from './ApiRequestResult.vue'
    import ParamTable from '../../components/params/ParamTable.vue'
    import ParamBody from '../../components/params/ParamBody.vue'
    import {CloseOutlined, SaveOutlined, VerticalAlignTopOutlined, VerticalAlignBottomOutlined} from '@ant-design/icons-vue';
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'
    import {zyplayerApi} from "../../api";

    export default {
        components: {
            VerticalAlignTopOutlined, VerticalAlignBottomOutlined, CloseOutlined, SaveOutlined, ParamTable, ParamBody, ApiRequestResult,
        },
	    setup(props) {
            let activePage = ref('urlParam');
            const route = useRoute();
            const store = useStore();
		    const queryParam = {docId: route.query.id, nodeId: route.query.nodeId};
            let globalParam = store.state.globalParam || [];
            let nextIndex = 1;
            // URL参数处理
            const urlParamRef = ref();
            let urlParamList = ref([]);
            // Header参数处理
            const headerParamRef = ref();
            let headerParamList = ref([]);
            // cookie参数处理
            const cookieParamRef = ref();
            let cookieParamList = ref([]);
            // form参数处理
            const formParamRef= ref();
            let formParamList = ref([]);
            // form参数处理
            const formEncodeParamRef = ref();
            let formEncodeParamList = ref([]);
            // body 参数
            let bodyParamRef = ref();
            let bodyParamType = ref('form');
            let consumesParamType = ref('json');
            let bodyRowParamList = ref({});
            // 配置信息
            let docInfoShow = ref({method: 'get'});
            // 发送请求
            let requestResult = ref({});
            let requestLoading = ref(false);
            const sendRequest = () => {
                if (!docInfoShow.value.apiUrl) {
                    message.error('请输入请求的目标URL地址');
                    return;
                }
	            setCustomRequestDefaultValue();
                const formData = new FormData();
                let urlParamSelected = urlParamRef.value.getSelectedRowKeys();
                let urlParamStr = urlParamList.value.filter(item => urlParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return item.name + '=' + encodeURIComponent(item.value);
                }).join('&');
                let headerParamSelected = headerParamRef.value.getSelectedRowKeys();
                let headerParamArr = headerParamList.value.filter(item => headerParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return {code: item.name, value: item.value};
                });
                let cookieParamSelected = cookieParamRef.value.getSelectedRowKeys();
                let cookieParamArr = cookieParamList.value.filter(item => cookieParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                    return {code: item.name, value: item.value};
                });
                let formParamArr = [];
                if (formParamRef.value) {
                    let formParamSelected = formParamRef.value.getSelectedRowKeys();
                    formParamArr = formParamList.value.filter(item => formParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                        // todo 判断处理文件格式
                        return {code: item.name, value: item.value};
                    });
                }
                let formEncodeParamArr = [];
                if (formEncodeParamRef.value) {
                    let formEncodeParamSelected = formEncodeParamRef.value.getSelectedRowKeys();
                    formEncodeParamArr = formEncodeParamList.value.filter(item => formEncodeParamSelected.indexOf(item.key) >= 0 && item.name && item.value).map(item => {
                        // todo 判断处理文件格式
                        return {code: item.name, value: item.value};
                    });
                }
                let bodyParamStr = '';
                if (bodyParamRef.value) {
                    bodyParamStr = bodyParamRef.value.getParam();
                }
                let url = urlParamStr ? (docInfoShow.value.apiUrl + '?' + urlParamStr) : docInfoShow.value.apiUrl;
                formData.append('url', url);
                formData.append('host', '');
                formData.append('method', docInfoShow.value.method);
                formData.append('contentType', '');
	            formData.append('docId', queryParam.docId);
	            formData.append('nodeName', docInfoShow.value.nodeName);
	            formData.append('nodeId', queryParam.nodeId);
	            formData.append('headerParam', JSON.stringify(headerParamArr));
                formData.append('cookieParam', JSON.stringify(cookieParamArr));
                formData.append('formParam', JSON.stringify(formParamArr));
                formData.append('formEncodeParam', JSON.stringify(formEncodeParamArr));
                formData.append('bodyParam', bodyParamStr);
                requestLoading.value = true;
                requestResult.value = {};
	            zyplayerApi.requestUrl(formData).then(res => {
		            requestResult.value = res;
		            requestLoading.value = false;
		            changeCustomRequest();
                }).catch(e => {
                    requestLoading.value = false;
                });
            };
            let queryParamVisible = ref(true);
            const hideQueryParam = () => {
                queryParamVisible.value = false;
            }
            const showQueryParam = () => {
                queryParamVisible.value = true;
            }
            const activePageChange = () => {
                queryParamVisible.value = true;
            }
		    // 改变侧边栏标题
		    const changeCustomRequest = () => {
			    store.commit('setCustomRequestChange', {
				    method: docInfoShow.value.method,
				    nodeId: docInfoShow.value.nodeId,
				    nodeName: docInfoShow.value.nodeName,
			    });
			    store.commit('addTableName', {key: route.fullPath, val: docInfoShow.value.nodeName});
		    }
		    onMounted(async () => {
			    let detailRes = await zyplayerApi.apiCustomNodeDetail({id: route.query.nodeId});
			    let requestDetail = detailRes.data;
			    if (!requestDetail) {
				    console.log('文档加载失败', detailRes);
			    }
			    docInfoShow.value = requestDetail;
			    store.commit('addTableName', {key: route.fullPath, val: requestDetail.nodeName});
			    // Header参数处理
			    let headerParamListProp = [];
			    let headerParamListGlobal = globalParam.filter(item => item.paramType === 2);
			    headerParamListGlobal.forEach(item => {
				    headerParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
			    });
			    if (requestDetail.headerData) {
				    let headerData = JSON.parse(requestDetail.headerData);
				    headerData.forEach(item => {
					    headerParamListProp.unshift({name: item.code, value: item.value, type: 'string', key: 'g' + (nextIndex++)});
				    });
			    }
			    headerParamList.value = headerParamListProp;
			    // cookie参数处理
			    let cookieParamListProp = [];
			    let cookieParamListGlobal = globalParam.filter(item => item.paramType === 3);
			    cookieParamListGlobal.forEach(item => {
				    cookieParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
			    });
			    if (requestDetail.cookieData) {
				    let cookieData = JSON.parse(requestDetail.cookieData);
				    cookieData.forEach(item => {
					    cookieParamListProp.unshift({name: item.code, value: item.value, type: 'string', key: 'g' + (nextIndex++)});
				    });
			    }
			    cookieParamList.value = cookieParamListProp;
			    // form参数处理
			    let formParamListProp = [];
			    let formParamListGlobal = globalParam.filter(item => item.paramType === 1);
			    formParamListGlobal.forEach(item => {
				    formParamListProp.push({name: item.paramKey, value: item.paramValue, type: 'string', key: 'g' + (nextIndex++)});
			    });
			    formParamList.value = formParamListProp;
		    });
			// 保存请求内容
		    const saveCustomRequest = () => {
			    setCustomRequestDefaultValue();
			    zyplayerApi.apiCustomNodeAdd(docInfoShow.value).then(res => {
				    message.success('保存成功');
				    changeCustomRequest();
			    });
		    }
		    const setCustomRequestDefaultValue = () => {
			    if (!docInfoShow.value.nodeName) {
				    docInfoShow.value.nodeName = '新建接口';
			    }
		    }
            return {
                activePage,
                activePageChange,
                requestLoading,
                sendRequest,
	            saveCustomRequest,
                requestResult,
                consumesParamType,
                // url参数
                urlParamRef,
                urlParamList,
                // header参数
                headerParamRef,
                headerParamList,
                // cookie参数
                cookieParamRef,
                cookieParamList,
                // form参数
                formParamRef,
                formParamList,
                // form-encode参数
                formEncodeParamRef,
                formEncodeParamList,
                // body参数
                bodyParamRef,
                bodyParamType,
                bodyRowParamList,
                // 界面控制
                queryParamVisible,
                docInfoShow,
                hideQueryParam,
                showQueryParam,
                methodList: ["get", "post", "put", "patch", "head", "delete", "options", "trace"],
            };
        },
    };
</script>

<style>
.api-name-box{padding-bottom: 10px;}
</style>

