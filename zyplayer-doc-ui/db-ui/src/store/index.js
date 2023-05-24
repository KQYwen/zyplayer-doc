import Vue from 'vue'
import Vuex from 'vuex'
import global from './modules/global'
import tagsView from './modules/tagsView'

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        global,
		tagsView
    }
});
