import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import i18n from "@/i18n";
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue';
import router from "@/core/router/configuration";
import store from '@/core/store';
import "@/core/validation/index";
import _ from "lodash"

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.prototype.$_ = _;

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

new Vue({
    render: h => h(App),
    router: router,
    store,
    i18n
}).$mount('#app');
