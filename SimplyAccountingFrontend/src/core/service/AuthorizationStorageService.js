export default class AuthorizationStorageService {

    static clearStorage() {
        sessionStorage.clear();
    }

    static isAuthorized() {
        return sessionStorage.getItem('jwt') != null;
    }

    static saveTokenInStorage(jwt) {
        sessionStorage.setItem('jwt', jwt);
    }

    static getTokenFromStorage() {
        return sessionStorage.getItem('jwt');
    }
}