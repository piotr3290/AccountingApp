import Vue from 'vue';
import {
    ValidationObserver,
    ValidationProvider,
    extend,
    localize
} from 'vee-validate';
import * as rules from 'vee-validate/dist/rules';
import en from 'vee-validate/dist/locale/en.json';
import pl from 'vee-validate/dist/locale/pl.json';


Vue.component("ValidationProvider", ValidationProvider);
Vue.component("ValidationObserver", ValidationObserver);

localize({
    en,
    pl
});

localize('pl');

Object.keys(rules).forEach(rule => {
    extend(rule, rules[rule]);
});