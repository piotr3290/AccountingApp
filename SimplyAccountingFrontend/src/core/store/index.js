import Vue from 'vue';
import Vuex from 'vuex';
import i18n from "@/i18n";
import {localize} from 'vee-validate';
import AuthorizationStorageService from "@/core/service/AuthorizationStorageService";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isLoading: false,
        locale: 'pl',
        token: null
    },
    getters: {
        getLoadingState: state => {
            return state.isLoading;
        },
        getLocale: state => {
            return state.locale;
        },
        getJwt: state => {
            return state.token;
        },
        isAuthenticated: state => {
            return state.token != null;
        }
    },
    mutations: {
        switchOnLoading(state) {
            state.isLoading = true;
        },
        switchOffLoading(state) {
            state.isLoading = false;
        },
        changeLocale(state, locale) {
            state.locale = locale;
            i18n.locale = locale;
            localize(locale);
        },
        authenticate(state, locale) {
            AuthorizationStorageService.saveTokenInStorage(locale);
            state.token = locale;
        },
        logOut(state) {
            AuthorizationStorageService.clearStorage();
            state.token = null;
        },
        loadJwtFromStorage(state) {
            state.token = AuthorizationStorageService.getTokenFromStorage();
        }
    }
});