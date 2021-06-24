import LoginView from "@/components/login/LoginView";
import Home from "@/components/Home";
import BuildingView from "@/components/building/BuildingView";
import BuildingForm from "@/components/building/BuildingForm";
import ContractorListView from "@/components/contractor/ContractorListView";
import ContractorView from "@/components/contractor/ContractorView";
import ContractorForm from "@/components/contractor/ContractorForm";
import CooperativeListView from "@/components/cooperative/CooperativeListView";
import CooperativeForm from "@/components/cooperative/CooperativeForm";
import CooperativeView from "@/components/cooperative/CooperativeView";
import LandlordListView from "@/components/landlord/LandlordListView";
import LandlordView from "@/components/landlord/LandlordView";
import LandlordForm from "@/components/landlord/LandlordForm";
import PremisesView from "@/components/premises/PremisesView";
import PremisesForm from "@/components/premises/PremisesForm";
import OpeningBalanceListView from "@/components/openingBalance/OpeningBalanceListView";
import NotFound from "@/components/common/NotFound";
import InvoiceForm from "@/components/invoice/InvoiceForm";
import InvoiceListView from "@/components/invoice/InvoiceListView";
import ChargeListView from "@/components/charge/ChargeListView";
import ChargeElementForm from "@/components/charge/ChargeElementForm";
import AreaTypeList from "@/components/area/AreaTypeList";
import PremisesTypeList from "@/components/premises/PremisesTypeList";
import RateList from "@/components/rate/RateList";
import RateForm from "@/components/rate/RateForm";
import AccountsPlan from "@/components/account/AccountsPlan";
import LandlordPaymentForm from "@/components/payment/LandlordPaymentForm";
import ContractorPaymentForm from "@/components/payment/ContractorPaymentForm";
import PaymentList from "@/components/payment/PaymentList";
import AdvanceForm from "@/components/advance/AdvanceForm";
import AdvanceList from "@/components/advance/AdvanceList";
import MoreOptionView from "@/components/type/MoreOptionView";

export const routes = [
    {
        name: 'login',
        path: '/login',
        component: LoginView,
        meta: {notAuthenticated: true}
    },
    {
        name: 'home',
        path: '/',
        component: Home
    },
    {
        name: 'accountsPlan',
        path: '/accounts_plan',
        component: AccountsPlan
    },
    {
        name: 'advanceCreate',
        path: '/premises/:premisesId/advance/create',
        component: AdvanceForm,
        props: true
    },
    {
        name: 'advanceEdit',
        path: '/advance/:id/edit',
        component: AdvanceForm,
        props: true
    },
    {
        name: 'advancePremises',
        path: '/premises/:premisesId/advances',
        component: AdvanceList,
        props: true
    },
    {
        name: 'areaTypeList',
        path: '/area_types',
        component: AreaTypeList
    },
    {
        name: 'building',
        path: '/building/:id',
        component: BuildingView,
        props: true
    },
    {
        name: 'buildingCreate',
        path: '/building/create/:cooperativeId',
        component: BuildingForm,
        props: true
    },
    {
        name: 'buildingEdit',
        path: '/building/edit/:id',
        component: BuildingForm,
        props: true
    },
    {
        name: 'chargesPlan',
        path: '/cooperative/:cooperativeId/charges_plan',
        component: ChargeListView,
        props: true
    },
    {
        name: 'chargeElementEdit',
        path: '/charge_element/edit/:id',
        component: ChargeElementForm,
        props: true
    },
    {
        name: 'chargeElementCreate',
        path: '/charge_element/create/:cooperativeId',
        component: ChargeElementForm,
        props: true
    },
    {
        name: 'contractors',
        path: '/contractor',
        component: ContractorListView
    },
    {
        name: 'contractor',
        path: '/contractor/:id',
        component: ContractorView,
        props: true
    },
    {
        name: 'contractorCreate',
        path: '/contractor/create',
        component: ContractorForm
    },
    {
        name: 'contractorEdit',
        path: '/contractor/edit/:id',
        component: ContractorForm,
        props: true
    },
    {
        name: 'contractorPaymentCreate',
        path: '/contractor_payment/create/:cooperativeId',
        component: ContractorPaymentForm,
        props: true
    },
    {
        name: 'contractorPaymentEdit',
        path: '/contractor_payment/edit/:id',
        component: ContractorPaymentForm,
        props: true
    },
    {
        name: 'cooperatives',
        path: '/cooperatives',
        component: CooperativeListView
    },
    {
        name: 'cooperativePayments',
        path: '/cooperative/:cooperativeId/payments',
        component: PaymentList,
        props: true
    },
    {
        name: 'cooperativeCreate',
        path: '/cooperatives/create',
        component: CooperativeForm
    },
    {
        name: 'cooperativeEdit',
        path: '/cooperatives/edit/:id',
        component: CooperativeForm,
        props: true
    },
    {
        name: 'cooperative',
        path: '/cooperative/:id',
        component: CooperativeView,
        props: true
    },
    {
        name: 'landlords',
        path: '/landlord',
        component: LandlordListView
    },
    {
        name: 'landlord',
        path: '/landlord/:id',
        component: LandlordView,
        props: true
    },
    {
        name: 'landlordCreate',
        path: '/landlord/create',
        component: LandlordForm
    },
    {
        name: 'landlordEdit',
        path: '/landlord/edit/:id',
        component: LandlordForm,
        props: true
    },
    {
        name: 'landlordPaymentEdit',
        path: '/landlord_payment/edit/:id',
        component: LandlordPaymentForm,
        props: true
    },
    {
        name: 'landlordPaymentCreate',
        path: '/landlord_payment/create/:cooperativeId',
        component: LandlordPaymentForm,
        props: true
    },
    {
        name: 'moreOptions',
        path: '/options',
        component: MoreOptionView
    },
    {
        name: 'premises',
        path: '/premises/:id',
        component: PremisesView,
        props: true
    },
    {
        name: 'premisesCreate',
        path: '/premises/create/:buildingId',
        component: PremisesForm,
        props: true
    },
    {
        name: 'premisesEdit',
        path: '/premises/edit/:id',
        component: PremisesForm,
        props: true
    },
    {
        name: 'premisesTypeList',
        path: '/premises_types',
        component: PremisesTypeList
    },
    {
        name: 'rates',
        path: '/rates',
        component: RateList
    },
    {
        name: 'rateCreate',
        path: '/rate/create',
        component: RateForm
    },
    {
        name: 'rateEdit',
        path: '/rate/:id/edit',
        component: RateForm,
        props: true
    },
    {
        name: 'openingBalancesList',
        path: '/opening_balances/:accountId',
        component: OpeningBalanceListView,
        props: true
    },
    {
        name: 'invoiceEdit',
        path: '/invoice/edit/:id',
        component: InvoiceForm,
        props: true
    },
    {
        name: 'invoiceCreate',
        path: '/invoice/create/:cooperativeId',
        component: InvoiceForm,
        props: true
    },
    {
        name: 'invoiceListCooperative',
        path: '/cooperative/:cooperativeId/invoices',
        component: InvoiceListView,
        props: true
    },
    {
        name: 'notFound',
        path: '*',
        component: NotFound
    },
];