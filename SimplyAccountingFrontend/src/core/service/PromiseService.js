import store from '@/core/store'

export const promisesLoading = {
    promisesLoading: (promises) => {
        store.commit('switchOnLoading');
        Promise.all(promises)
            .finally(() => {
                store.commit('switchOffLoading');
            });
    }
}
