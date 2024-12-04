import hljs from 'highlight.js'
import 'highlight.js/styles/googlecode.css'
// import 'highlight.js/styles/monokai-sublime.css'
import '/public/katex/katex.min.js'

import {createApp} from 'vue'
import App from './App.vue'
import {createRouter, createWebHashHistory} from 'vue-router'

import ElementUI from 'element-plus'
import Antd from 'ant-design-vue';
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import routes from './routes'
import Vant from 'vant'
import {createPinia} from 'pinia'
import 'element-plus/dist/index.css'
import './assets/scss/base.scss'

const router = createRouter({
	history: createWebHashHistory(),
	routes,
});
const app = createApp(App);
app.config.productionTip = false;
app.use(Antd)
app.use(ElementUI, {
	locale: zhCn,
});
app.use(Vant);
app.use(router);
app.use(createPinia());
app.mount('#app');

app.directive('highlight', function (el) {
	let blocks = el.querySelectorAll('pre code');
	blocks.forEach((block) => {
		hljs.highlightBlock(block);
	});
});
