import {httpRequestService} from "@/App";

export default class AuthenticationService {

    static signIn(username, password) {
        return httpRequestService.post('/login', {username: username, password: password});
    }

}