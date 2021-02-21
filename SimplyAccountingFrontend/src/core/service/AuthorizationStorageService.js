export default class AuthorizationStorageService {

    static clearStorage() {
        localStorage.clear();
    }

    static isAuthorized() {
        return localStorage.getItem('jwt') != null;
    }

    static saveTokenInStorage(jwt) {
        localStorage.setItem('jwt', jwt);
    }

    static getTokenFromStorage() {
        return localStorage.getItem('jwt');
    }
}