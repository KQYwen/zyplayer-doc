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
    if (to.meta.title) {
        document.title = to.meta.name;
    }
    store.commit('global/setFullscreen', !!to.meta.fullscreen);
    next();
});

let vue = new Vue({
    el: '#app',
    router,
    render(h) {
        return h(App);
    }
});
export default vue;



