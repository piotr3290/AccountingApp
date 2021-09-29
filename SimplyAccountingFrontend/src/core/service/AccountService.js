import {httpRequestService} from "@/App";

export default class AccountService {

    static getAccount(id) {
        return httpRequestService.get(`/accounts/${id}`);
    }

    static getAccountResponse(id) {
        return httpRequestService.get(`/accounts/details/${id}`);
    }

}