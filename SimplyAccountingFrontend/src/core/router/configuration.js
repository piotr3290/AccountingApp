import VueRouter from 'vue-router';
import store from '@/core/store'
import Home from '@/components/Home';
import NotFound from '@/components/NotFound';
import CooperativesView from '@/components/cooperative/CooperativesView';
import CooperativeCreate from "@/components/cooperative/CooperativeCreate";
import CooperativeView from "@/components/cooperative/CooperativeView";
import BuildingCreate from "@/components/building/BuildingCreate";
import BuildingView from "@/components/building/BuildingView";
import PremisesCreate from "@/components/premises/PremisesCreate";
import PremisesView from "@/components/premises/PremisesView";
import LandlordsView from "@/components/landlords/LandlordsView";
import LandlordCreate from "@/components/landlords/LandlordCreate";
import LoginView from "@/components/LoginView";

const routes = [
    {name: 'login', path: '/login', component: LoginView, meta: {notAuthenticated: true}},
    {name: 'home', path: '/', component: Home},
    {name: 'building', path: '/buildings/:id', component: BuildingView, props: true},
    {name: 'buildingCreate', path: '/buildings/create/:cooperativeId', component: BuildingCreate, props: true},
    {name: 'buildingEdit', path: '/buildings/edit/:id', component: BuildingCreate, props: true},
    {name: 'cooperatives', path: '/cooperatives', component: CooperativesView},
    {name: 'cooperativeCreate', path: '/cooperatives/create', component: CooperativeCreate},
    {name: 'cooperativeEdit', path: '/cooperatives/edit/:id', component: CooperativeCreate, props: true},
    {name: 'cooperative', path: '/cooperatives/:id', component: CooperativeView, props: true},
    {name: 'landlords', path: '/landlords', component: LandlordsView},
    {name: 'landlordCreate', path: '/landlords/create', component: LandlordCreate},
    {name: 'premises', path: '/premises/:id', component: PremisesView, props: true},
    {name: 'premisesCreate', path: '/premises/create/:buildingId', component: PremisesCreate, props: true},
    {name: 'premisesEdit', path: '/premises/edit/:id', component: PremisesCreate, props: true},
    {name: 'notFound', path: '*', component: NotFound},
];

const router = new VueRouter({routes: routes});
//const router = new VueRouter({mode: 'history',routes: routes});

router.beforeEach((to, from, next) => {

    if (to.matched.some(record => record.meta.notAuthenticated) ? !store.getters.isAuthenticated : store.getters.isAuthenticated) {
        next();
    } else if (to.matched.some(record => record.meta.notAuthenticated) && store.getters.isAuthenticated) {
        next({name: 'home'});
    } else {
        next({name: 'login'});
    }
})
export default router;