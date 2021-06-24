import {httpRequestService} from "@/App";

export default class LandlordService {

    static createLandlord(landlord) {
        return httpRequestService.post('/landlords', landlord);
    }

    static editLandlord(landlord) {
        return httpRequestService.put('/landlords', landlord);
    }

    static getAllLandlords() {
        return httpRequestService.get('/landlords');
    }

    static getLandlord(id) {
        return httpRequestService.get(`/landlords/${id}`);
    }

    static getLandlordDetails(id) {
        return httpRequestService.get(`/landlords/details/${id}`);
    }

    static getAllLandlordsForCooperative(cooperativeId) {
        return httpRequestService.get(`/landlords/cooperative/${cooperativeId}`);
    }

    static getAllLandlordsForPremises(premisesId) {
        return httpRequestService.get(`/landlords/premises/${premisesId}`);
    }
}