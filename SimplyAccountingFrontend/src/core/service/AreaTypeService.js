import {httpRequestService} from "@/App";

export default class AreaTypeService {

    static createAreaType(areaType) {
        return httpRequestService.post('/areaTypes', areaType);
    }

    static editAreaType(areaType) {
        return httpRequestService.put('/areaTypes', areaType);
    }

    static deleteAreaType(id) {
        return httpRequestService.delete(`/areaTypes/${id}`);
    }

    static getAreaType(id) {
        return httpRequestService.get(`/areaTypes/${id}`);
    }

    static getAllAreaTypes() {
        return httpRequestService.get('/areaTypes');
    }
}