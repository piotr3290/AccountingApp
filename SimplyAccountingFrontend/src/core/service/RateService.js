import {httpRequestService} from "@/App";

export default class RateService {

    static createRate(rate) {
        return httpRequestService.post('/rates', rate);
    }

    static editRate(rate) {
        return httpRequestService.put('/rates', rate);
    }

    static deleteRate(id) {
        return httpRequestService.delete(`/rates/${id}`);
    }

    static getRate(id) {
        return httpRequestService.get(`/rates/${id}`);
    }

    static getAllRates() {
        return httpRequestService.get('/rates');
    }
}