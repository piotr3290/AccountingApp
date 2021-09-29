import {httpRequestService} from "@/App";

export default class AreaService {

    static createArea(area) {
        return httpRequestService.post('/areas', area);
    }

    static editArea(area) {
        return httpRequestService.put('/areas', area);
    }

    static deleteArea(id) {
        return httpRequestService.delete(`/areas/${id}`);
    }

    static getArea(id) {
        return httpRequestService.get(`/areas/${id}`);
    }
}