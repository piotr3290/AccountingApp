import VueRouter from 'vue-router';
import store from '@/core/store'
import {routes} from "@/core/router/routes";


const router = new VueRouter({routes: routes});

router.beforeEach(async (to, from, next) => {

    await store.dispatch('checkTokenValidity')
        .catch(error => {
            console.error(error.toString());
        });


    if (to.matched.some(record => record.meta.notAuthenticated)
        ? !store.getters.isAuthenticated
        : store.getters.isAuthenticated) {
        next();
    } else if (to.matched.some(record => record.meta.notAuthenticated) && store.getters.isAuthenticated) {
        next({name: 'home'});
    } else {
        next({name: 'login'});
    }
})
export default router;