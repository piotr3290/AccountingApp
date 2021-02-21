import {httpRequestService} from "@/App";

export default class ContractorService {

    static getContractor(id) {
        return httpRequestService.get(`/contractors/${id}`);
    }

    static getAllContractors() {
        return httpRequestService.get('/contractor');
    }

    static createContractor(contractor) {
        return httpRequestService.post('/contractor', contractor);
    }

    static editContractor(contractor) {
        return httpRequestService.put('/contractor', contractor);
    }
}