import {httpRequestService} from "@/App";

export default class ConsumptionService {

    static createConsumption(consumption) {
        return httpRequestService.post('/consumptions', consumption);
    }

    static editConsumption(consumption) {
        return httpRequestService.put('/consumptions', consumption);
    }

    static deleteConsumption(id) {
        return httpRequestService.delete(`/consumptions/${id}`);
    }

    static getConsumption(id) {
        return httpRequestService.get(`/consumptions/${id}`);
    }
}