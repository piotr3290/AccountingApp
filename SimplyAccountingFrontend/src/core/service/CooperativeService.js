import {httpRequestService} from "@/App";

export default class CooperativeService {

    static getCooperatives(page, itemsPerPage) {
        return httpRequestService.get('/cooperatives', page, itemsPerPage);
    }

    static getCooperative(id) {
        return httpRequestService.get(`/cooperatives/${id}`);
    }

    static createCooperative(cooperative) {
        return httpRequestService.post(`/cooperatives`, cooperative);
    }

    static editCooperative(cooperative) {
        return httpRequestService.put(`/cooperatives`, cooperative);
    }

    static getCooperativesBuildings(id) {
        return httpRequestService.get(`/cooperatives/buildings/${id}`);
    }

    static getAllCooperatives() {
        return httpRequestService.get('/cooperatives/all');
    }

    static getPremisesCooperative(premisesId) {
        return httpRequestService.get(`/cooperatives/premises/${premisesId}`);
    }
}