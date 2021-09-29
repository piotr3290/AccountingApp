import {httpRequestService} from "@/App";

export default class PremisesService {
    static createPremises(premises) {
        return httpRequestService.post('/premises', premises);
    }

    static editPremises(premises) {
        return httpRequestService.put('/premises', premises);
    }

    static getPremises(id) {
        return httpRequestService.get(`/premises/${id}`);
    }

    static getPremisesView(id) {
        return httpRequestService.get(`/premises/details/${id}`);
    }

    static getAllPremisesForBuilding(buildingId) {
        return httpRequestService.get(`/premises/building/${buildingId}`);
    }

    static getPremisesInfo(id) {
        return httpRequestService.get(`/premises/info/${id}`);
    }
}