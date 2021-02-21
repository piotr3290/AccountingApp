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
import LandlordsView from "@/components/landlord/LandlordsView";
import LandlordCreate from "@/components/landlord/LandlordCreate";
import LoginView from "@/components/LoginView";
import LandlordView from "@/components/landlord/LandlordView";
import ContractorsView from "@/components/contractor/ContractorsView";
import ContractorCreate from "@/components/contractor/ContractorCreate";
import ContractorView from "@/components/contractor/ContractorView";

const routes = [
    {name: 'login', path: '/login', component: LoginView, meta: {notAuthenticated: true}},
    {name: 'home', path: '/', component: Home},
    {name: 'building', path: '/buildings/:id', component: BuildingView, props: true},
    {name: 'buildingCreate', path: '/buildings/create/:cooperativeId', component: BuildingCreate, props: true},
    {name: 'buildingEdit', path: '/buildings/edit/:id', component: BuildingCreate, props: true},
    {name: 'contractors', path: '/contractor', component: ContractorsView},
    {name: 'contractor', path: '/contractor/:id', component: ContractorView, props: true},
    {name: 'contractorCreate', path: '/contractor/create', component: ContractorCreate},
    {name: 'contractorEdit', path: '/contractor/edit/:id', component: ContractorCreate, props: true},
    {name: 'cooperatives', path: '/cooperatives', component: CooperativesView},
    {name: 'cooperativeCreate', path: '/cooperatives/create', component: CooperativeCreate},
    {name: 'cooperativeEdit', path: '/cooperatives/edit/:id', component: CooperativeCreate, props: true},
    {name: 'cooperative', path: '/cooperatives/:id', component: CooperativeView, props: true},
    {name: 'landlords', path: '/landlord', component: LandlordsView},
    {name: 'landlord', path: '/landlord/:id', component: LandlordView, props: true},
    {name: 'landlordCreate', path: '/landlord/create', component: LandlordCreate},
    {name: 'landlordEdit', path: '/landlord/edit/:id', component: LandlordCreate, props: true},
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