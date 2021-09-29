import {httpRequestService} from "@/App";

export default class OpeningBalanceService {

    static createOpeningBalance(openingBalance) {
        return httpRequestService.post('/openingBalances', openingBalance);
    }

    static editOpeningBalance(openingBalance) {
        return httpRequestService.put('/openingBalances', openingBalance);
    }

    static getOpeningBalance(id) {
        return httpRequestService.get(`/openingBalances/${id}`);
    }

    static deleteOpeningBalance(id) {
        return httpRequestService.delete(`/openingBalances/${id}`);
    }

    static getAllOpeningBalancesForCooperative(cooperativeId) {
        return httpRequestService.get(`/openingBalances/cooperative/${cooperativeId}`);
    }

    static getAllOpeningBalancesForLandlord(landlordId) {
        return httpRequestService.get(`/openingBalances/landlord/${landlordId}`);
    }

    static getAllOpeningBalancesForContractor(contractorId) {
        return httpRequestService.get(`/openingBalances/contractor/${contractorId}`);
    }

    static getAllOpeningBalancesForAccount(accountId) {
        return httpRequestService.get(`/openingBalances/account/${accountId}`);
    }

    static calculateClosingBalancesForCooperative(request) {
        return httpRequestService.post('/openingBalances/calculate/cooperative', request);
    }
}
