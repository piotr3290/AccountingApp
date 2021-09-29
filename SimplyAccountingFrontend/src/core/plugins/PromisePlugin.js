import {promisesLoading} from "@/core/service/PromiseService";

export const PromisePlugin = {
    install(Vue,) {
        Vue.prototype.$loadingPromises = promisesLoading.promisesLoading;
    }
};