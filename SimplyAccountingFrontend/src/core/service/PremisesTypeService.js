import {httpRequestService} from "@/App";

export default class PremisesTypeService {

    static getAllPremisesTypes() {
        return httpRequestService.get('/premisesTypes');
    }

    static getPremisesType(id) {
        return httpRequestService.get(`/premisesTypes/${id}`);
    }

    static createPremisesType(premisesType) {
        return httpRequestService.post('/premisesTypes', premisesType);
    }

    static editPremisesType(premisesType) {
        return httpRequestService.put('/premisesTypes', premisesType);
    }

    static deletePremisesType(id) {
        return httpRequestService.delete(`/premisesTypes/${id}`);
    }

}