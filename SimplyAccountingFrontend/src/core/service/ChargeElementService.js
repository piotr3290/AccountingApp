import {httpRequestService} from "@/App";

export default class ChargeElementService {

    static createChargeElement(chargeElement) {
        return httpRequestService.post('/chargeElements', chargeElement);
    }

    static editChargeElement(chargeElement) {
        return httpRequestService.put('/chargeElements', chargeElement);
    }

    static deleteChargeElement(id) {
        return httpRequestService.delete(`/chargeElements/${id}`);
    }

    static getChargeElement(id) {
        return httpRequestService.get(`/chargeElements/${id}`);
    }

    static getAllChargeElementsForCooperative(cooperativeId) {
        return httpRequestService.get(`/chargeElements/cooperative/${cooperativeId}`);
    }
}