import Vue from 'vue';
import Vuex from 'vuex';
import i18n from "@/i18n";
import {localize} from 'vee-validate';
import AuthorizationStorageService from "@/core/storage/AuthorizationStorageService";
import AuthenticationService from "@/core/service/AuthenticationService";
import LocaleStorageService from "@/core/storage/LocaleStorageService";
import ThemeStorageService from "@/core/storage/ThemeStorageService";
import vuetify from "@/core/plugins/vuetify";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isLoading: false,
        locale: 'en',
        token: null,
        isDarkTheme: false
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
        },
        isTokenValid: state => {
            return AuthenticationService.isTokenValid(state.token);
        },
        isDarkTheme: state => {
            return state.isDarkTheme;
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
            LocaleStorageService.saveLocaleInStorage(locale);
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
        },
        loadLocaleFromStorage(state) {
            if (LocaleStorageService.isLocaleSaved()) {
                const locale = LocaleStorageService.getLocaleFromStorage();
                state.locale = locale;
                i18n.locale = locale;
                localize(locale);
            }
        },
        changeTheme(state, isDarkTheme) {
            state.isDarkTheme = isDarkTheme;
            vuetify.framework.theme.dark = isDarkTheme;
            ThemeStorageService.saveThemeInStorage(isDarkTheme);

        },
        loadThemeFromStorage(state) {
            if (ThemeStorageService.isThemeSaved()) {
                const isDarkTheme = ThemeStorageService.getThemeFromStorage() === 'true';
                state.isDarkTheme = isDarkTheme;
                vuetify.framework.theme.dark = isDarkTheme;
            }
        }
    },
    actions: {
        checkTokenValidity(context) {
            if (!context.getters.isTokenValid) {
                context.commit('logOut');
            }
        }
    }
});