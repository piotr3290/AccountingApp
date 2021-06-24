import {httpRequestService} from "@/App";

export default class AccountTypeService {

    static getCooperativeAccountType() {
        return httpRequestService.get('/accounts/types/cooperative');
    }

    static getLandlordAccountType() {
        return httpRequestService.get('/accounts/types/landlord');
    }

    static getContractorAccountType() {
        return httpRequestService.get('/accounts/types/contractor');
    }

    static getAccountType(id) {
        return httpRequestService.get(`/accounts/types/${id}`);
    }

    static editAccountType(accountType) {
        return httpRequestService.put('/accounts/types', accountType);
    }
}