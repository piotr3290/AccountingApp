import {httpRequestService} from "@/App";

export default class BuildingService {
    static createBuilding(building) {
        return httpRequestService.post('/buildings', building);
    }

    static editBuilding(building) {
        return httpRequestService.put('/buildings', building);
    }

    static getBuilding(id) {
        return httpRequestService.get(`/buildings/${id}`);
    }

    static getBuildingWithPremises(id) {
        return httpRequestService.get(`/buildings/premises/${id}`);
    }

    static getAllBuildingsForCooperative(cooperativeId) {
        return httpRequestService.get(`/buildings/cooperative/${cooperativeId}`);
    }
}