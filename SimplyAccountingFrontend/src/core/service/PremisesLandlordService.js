import {httpRequestService} from "@/App";

export default class PremisesLandlordService {

    static createPremisesLandlord(premisesLandlord) {
        return httpRequestService.post('/premisesLandlords', premisesLandlord);
    }

    static editPremisesLandlord(premisesLandlord) {
        return httpRequestService.put('/premisesLandlords', premisesLandlord);
    }

    static deletePremisesLandlord(id) {
        return httpRequestService.delete(`/premisesLandlords/${id}`);
    }

    static getPremisesLandlord(id) {
        return httpRequestService.get(`/premisesLandlords/${id}`);
    }
}