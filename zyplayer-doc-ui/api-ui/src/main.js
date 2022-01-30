import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHashHistory } from 'vue-router';

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import routes from './routes'
import store from './store/index'

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
const app = createApp(App);
app.config.productionTip = false;
app.use(Antd);
app.use(router);
app.use(store);
app.mount('#app');

// 注册一个全局自定义指令
import hljs from 'highlight.js'
import 'highlight.js/styles/googlecode.css'

app.directive('highlight', {
    updated(el) {
        let blocks = el.querySelectorAll('pre code')
        blocks.forEach((block) => {
            hljs.highlightBlock(block);
        });
    }
});

// 聚焦元素
app.directive('autofocus', {
    updated(el) {
        // 延迟等待弹窗初始完成
        setTimeout(() => el.focus(), 0);
    }
});

