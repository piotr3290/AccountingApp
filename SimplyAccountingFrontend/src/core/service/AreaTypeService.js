import {httpRequestService} from "@/App";

const PATH = '/areaTypes';

export default class AreaTypeService {

    static createAreaType(areaType) {
        return httpRequestService.post(PATH, areaType);
    }

    static editAreaType(areaType) {
        return httpRequestService.put(PATH, areaType);
    }

    static deleteAreaType(id) {
        return httpRequestService.delete(`${PATH}/${id}`);
    }

    static getAreaType(id) {
        return httpRequestService.get(`${PATH}/${id}`);
    }

    static getAllAreaTypes() {
        return httpRequestService.get(PATH);
    }
}
