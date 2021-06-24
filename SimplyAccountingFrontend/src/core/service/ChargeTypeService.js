import {httpRequestService} from "@/App";

export default class ChargeTypeService {

    static getAllChargeTypes() {
        return httpRequestService.get(`/chargeTypes`);
    }

    static getChargeType(id) {
        return httpRequestService.get(`/chargeTypes/${id}`);
    }

    static createChargeType(chargeType) {
        return httpRequestService.post('/chargeTypes', chargeType);
    }

    static editChargeType(chargeType) {
        return httpRequestService.put('/chargeTypes', chargeType);
    }
}