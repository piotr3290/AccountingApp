import {httpRequestService} from "@/App";
import jsonwebtoken from 'jsonwebtoken';

export default class AuthenticationService {

    static signIn(username, password) {
        return httpRequestService.post('/login', {username: username, password: password});
    }

    static isTokenValid(token) {
        try {
            return jsonwebtoken.decode(token).exp * 1000 > Date.now();
        } catch (e) {
            return false;
        }
    }

}