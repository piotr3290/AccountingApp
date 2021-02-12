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

    static getAllPremisesTypes() {
        return httpRequestService.get('/premisesTypes');
    }

    static getPremisesView(id) {
        return httpRequestService.get(`/premises/details/${id}`);
    }

}