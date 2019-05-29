import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'

import global from './common/config/global'
import apimix from './common/config/apimix'
import common from './common/lib/common/common'
import toast from './common/lib/common/toast'

import VueRouter from 'vue-router'
import routes from './routes'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(VueAxios, axios);

// 全局参数
Vue.prototype.global = global;
// 接口列表
Vue.prototype.apilist1 = apimix.apilist1;
Vue.prototype.apilist2 = apimix.apilist1;
// 公用方法
Vue.prototype.common = common;
Vue.prototype.toast = toast;

const router = new VueRouter({routes});
// 路由跳转时判断处理
router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title
    }
    global.fullscreen = !!to.meta.fullscreen;
    /* 判断该路由是否需要登录权限 */
    if (to.matched.some(record => record.meta.requireAuth)) {
        if (global.user.isLogin) {
            next();
        } else {
            next({path: '/login'});
        }
    } else {
        next();
    }
});

new Vue({
    el: '#app',
    router,
    render(h) {
        var app = h(App);
        global.vue = app.context;
        return app;
    }
});



