import {httpRequestService} from "@/App";

export default class LandlordService {

    static createLandlord(landlord) {
        return httpRequestService.post('/landlord', landlord);
    }

    static editLandlord(landlord) {
        return httpRequestService.put('/landlord', landlord);
    }

    static getAllLandlords() {
        return httpRequestService.get('/landlord');
    }

    static getLandlord(id) {
        return httpRequestService.get(`/landlords/${id}`);
    }

    static getLandlordDetails(id) {
        return httpRequestService.get(`/landlords/details/${id}`);
    }
}