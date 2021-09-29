import {httpRequestService} from "@/App";

export default class LandlordPaymentService {

    static createLandlordPayment(landlordPayment) {
        return httpRequestService.post('/landlordPayments', landlordPayment);
    }

    static editLandlordPayment(landlordPayment) {
        return httpRequestService.put('/landlordPayments', landlordPayment);
    }

    static getLandlordPayment(id) {
        return httpRequestService.get(`/landlordPayments/${id}`);
    }

    static deleteLandlordPayment(id) {
        return httpRequestService.get(`/landlordPayments/${id}`);
    }

    static getAllLandlordPaymentsForYear(cooperativeId, year) {
        return httpRequestService.get(`/landlordPayments/cooperative/${cooperativeId}/year/${year}`);
    }
}