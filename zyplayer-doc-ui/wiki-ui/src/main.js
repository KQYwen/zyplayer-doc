import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'

import VueRouter from 'vue-router'
import routes from './routes'
import store from './store/index'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(VueAxios, axios);

// 公用方法
Vue.prototype.$store = store;

const router = new VueRouter({routes});
// 路由跳转时判断处理
router.beforeEach((to, from, next) => {
    if (to.name) {
        document.title = to.name;
    }
    next();
});

let vue = new Vue({
    el: '#app',
    router,
    render(h) {
        return h(App);
    }
});

// 注册一个全局自定义指令
import hljs from 'highlight.js'
import 'highlight.js/styles/googlecode.css'

Vue.directive('highlight', function (el) {
	let blocks = el.querySelectorAll('pre code');
	blocks.forEach((block) => {
		hljs.highlightBlock(block);
	})
});

export default vue;



