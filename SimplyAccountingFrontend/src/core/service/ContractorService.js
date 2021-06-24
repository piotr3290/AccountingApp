import {httpRequestService} from "@/App";

export default class ContractorService {

    static getContractor(id) {
        return httpRequestService.get(`/contractors/${id}`);
    }

    static getAllContractors() {
        return httpRequestService.get('/contractors');
    }

    static createContractor(contractor) {
        return httpRequestService.post('/contractors', contractor);
    }

    static editContractor(contractor) {
        return httpRequestService.put('/contractors', contractor);
    }
}