import {httpRequestService} from "@/App";

export default class PeopleNumberService {

    static createPeopleNumber(peopleNumber) {
        return httpRequestService.post('/peopleNumbers', peopleNumber);
    }

    static editPeopleNumber(peopleNumber) {
        return httpRequestService.put('/peopleNumbers', peopleNumber);
    }

    static deletePeopleNumber(id) {
        return httpRequestService.delete(`/peopleNumbers/${id}`);
    }

    static getPeopleNumber(id) {
        return httpRequestService.get(`/peopleNumbers/${id}`);
    }
}