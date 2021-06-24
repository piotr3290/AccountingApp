import {httpRequestService} from "@/App";

export default class ContractorPaymentService {

    static createContractorPayment(contractorPayment) {
        return httpRequestService.post('/contractorPayments', contractorPayment);
    }

    static editContractorPayment(contractorPayment) {
        return httpRequestService.put('/contractorPayments', contractorPayment);
    }

    static getContractorPayment(id) {
        return httpRequestService.get(`/contractorPayments/${id}`);
    }

    static deleteContractorPayment(id) {
        return httpRequestService.get(`/contractorPayments/${id}`);
    }

    static getAllContractorPaymentsForYear(cooperativeId, year) {
        return httpRequestService.get(`/contractorPayments/cooperative/${cooperativeId}/year/${year}`);
    }
}