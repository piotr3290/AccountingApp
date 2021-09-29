export default class LocaleStorageService {

    static getLocaleFromStorage() {
        return localStorage.getItem('locale');
    }

    static saveLocaleInStorage(locale) {
        localStorage.setItem('locale', locale);
    }

    static isLocaleSaved() {
        return localStorage.getItem('locale') != null;
    }
}