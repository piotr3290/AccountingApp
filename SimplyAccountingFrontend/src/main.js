import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import i18n from "@/i18n";
import {PromisePlugin} from '@/core/plugins/PromisePlugin';
import router from "@/core/router/configuration";
import store from '@/core/store';
import "@/core/validation/index";
import _ from "lodash";
import Toast, {TYPE} from 'vue-toastification';
import "@/scss/_toastStyle.scss";
import vuetify from './core/plugins/vuetify';
import VueScreenSize from 'vue-screen-size';

Vue.prototype.$_ = _;

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(PromisePlugin);
Vue.use(Toast);
Vue.use(VueScreenSize);

store.commit('loadJwtFromStorage');
store.commit('loadLocaleFromStorage');
store.commit('loadThemeFromStorage');

const vm = new Vue({
    render: h => h(App),
    router: router,
    store,
    vuetify,
    i18n
}).$mount('#app');

Vue.prototype.$errorToast = (error) => {
    vm.$toast(error?.response?.data?.message || vm.$t('error.defaultMessage'), {
        position: "bottom-right",
        timeout: 5000,
        type: TYPE.ERROR
    });
};

Vue.prototype.$successToast = (message) => {
    vm.$toast(message, {
        position: "bottom-right",
        timeout: 5000,
        type: TYPE.SUCCESS
    });
};

