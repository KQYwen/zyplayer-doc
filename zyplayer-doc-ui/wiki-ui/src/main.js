import hljs from 'highlight.js';
import {createApp} from 'vue';
import App from './App.vue';
import ElementUI from 'element-plus';
import Antd from 'ant-design-vue';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import routes from './routes';
import Vant from 'vant';
import {createPinia} from 'pinia';
import 'highlight.js/styles/googlecode.css';
import 'element-plus/dist/index.css';
import './assets/scss/highlight.scss';
import './assets/scss/markdown.scss';
import './assets/scss/pageView.scss';
import './assets/scss/base.scss';

const app = createApp(App);
app.config.productionTip = false;
app.use(Antd);
app.use(ElementUI, {
	locale: zhCn,
});
app.use(Vant);
app.use(routes);
app.use(createPinia());
app.mount('#app');

app.directive('highlight', function (el) {
	let blocks = el.querySelectorAll('pre code');
	blocks.forEach((block) => {
		hljs.highlightBlock(block);
	});
});
