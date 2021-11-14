<template>
    <a-form :label-col="{span: 4}" :wrapper-col="{span: 20}">
        <a-form-item label="接口地址">{{docInfoShow.url}}</a-form-item>
        <a-form-item label="说明">
            <div class="markdown-body" v-html="docInfoShow.description" v-highlight></div>
        </a-form-item>
        <a-form-item label="请求方式">{{docInfoShow.method}}</a-form-item>
        <a-form-item label="请求数据类型">{{docInfoShow.consumes}}</a-form-item>
        <a-form-item label="响应数据类型">{{docInfoShow.produces}}</a-form-item>
        <a-form-item label="请求参数">
            <a-table :dataSource="requestParamList" :columns="requestParamListColumns" size="small" :pagination="false" defaultExpandAllRows>
                <template #bodyCell="{ column, text, record }">
                    <template v-if="column.dataIndex === 'type'">
                        {{text}}
                        <template v-if="record.subType">[{{record.subType}}]</template>
                        <template v-if="record.format">({{record.format}})</template>
                    </template>
                    <template v-if="column.dataIndex === 'in'">
                        <a-tag color="pink" v-if="text === 'header'">header</a-tag>
                        <a-tag color="red" v-else-if="text === 'body'">body</a-tag>
                        <a-tag color="orange" v-else-if="text === 'query'">query</a-tag>
                        <a-tag color="green" v-else-if="text === 'formData'">formData</a-tag>
                        <template v-else-if="!text">-</template>
                        <a-tag color="purple" v-else>{{text}}</a-tag>
                    </template>
                    <template v-if="column.dataIndex === 'required'">
                        <span v-if="text === '是'" style="color: #f00;">是</span>
                        <template v-else-if="text === '否'">否</template>
                        <template v-else>-</template>
                    </template>
                    <template v-if="column.dataIndex === 'description'">
                        {{text}}
                    </template>
                </template>
            </a-table>
        </a-form-item>
        <a-form-item label="返回结果">
            <a-table :dataSource="responseParamList" :columns="responseCodeListColumns" size="small" :pagination="false">
                <template #bodyCell="{ column, text, record }">
                    <template v-if="column.dataIndex === 'desc'">
                        <div v-html="text"></div>
                    </template>
                </template>
                <template #expandedRowRender="{ record }">
                    <template v-if="record.schemas">
                        <a-table :dataSource="record.schemas" :columns="responseParamListColumns" size="small" :pagination="false">
                            <template #bodyCell="{ column, text, record }">
                                <template v-if="column.dataIndex === 'type'">
                                    {{text}}
                                    <template v-if="record.subType">[{{record.subType}}]</template>
                                    <template v-if="record.format">({{record.format}})</template>
                                </template>
                            </template>
                        </a-table>
                    </template>
                    <div v-else style="text-align: center;padding: 10px 0;">无参数说明</div>
                </template>
            </a-table>
        </a-form-item>
    </a-form>
</template>

<script>
    import {toRefs, ref, reactive, onMounted, watch} from 'vue';
    import { useRouter, useRoute } from "vue-router";
    import {useStore} from 'vuex';
    import { message } from 'ant-design-vue';
    import {markdownIt} from 'mavon-editor'
    import 'mavon-editor/dist/markdown/github-markdown.min.css'
    import 'mavon-editor/dist/css/index.css'

    export default {
        props: {
            docInfoShow: {
                type: Object,
                required: true
            },
            requestParamList: {
                type: Array,
                required: true
            },
            responseParamList: {
                type: Array,
                required: true
            },
        },
        setup() {
            return {
                requestParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 200},
                    {title: '类型', dataIndex: 'type', width: 150},
                    {title: '参数位置', dataIndex: 'in', width: 100},
                    {title: '必填', dataIndex: 'required', width: 60},
                    {title: '说明', dataIndex: 'description'},
                ],
                responseCodeListColumns: [
                    {title: '状态码', dataIndex: 'code', width: 100},
                    {title: '类型', dataIndex: 'type', width: 250},
                    {title: '说明', dataIndex: 'desc'},
                ],
                responseParamListColumns: [
                    {title: '参数名', dataIndex: 'name', width: 250},
                    {title: '类型', dataIndex: 'type', width: 250},
                    {title: '说明', dataIndex: 'description'},
                ],
            };
        },
    };
</script>
