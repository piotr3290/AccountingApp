import {httpRequestService} from "@/App";

export default class AdvanceService {

    static createAdvance(advance) {
        return httpRequestService.post('/advances', advance);
    }

    static editAdvance(advance) {
        return httpRequestService.put('/advances', advance);
    }

    static deleteAdvance(id) {
        return httpRequestService.delete(`/advances/${id}`);
    }

    static getAdvance(id) {
        return httpRequestService.get(`/advances/${id}`);
    }

    static getAllAdvancesForPremises(premisesId, year) {
        return httpRequestService.get(`/advances/premises/${premisesId}/year/${year}`);
    }

    static calculateAdvancesForCooperative(advanceCalculateRequest) {
        return httpRequestService.post(`/advances/calculate/cooperative`, advanceCalculateRequest);
    }

    static calculateAdvancesForBuilding(advanceCalculateRequest) {
        return httpRequestService.post(`/advances/calculate/building`, advanceCalculateRequest);
    }

    static calculateAdvancesForPremises(advanceCalculateRequest) {
        return httpRequestService.post(`/advances/calculate/premises`, advanceCalculateRequest);
    }
}