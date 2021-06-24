export default class ThemeStorageService {

    static getThemeFromStorage() {
        return localStorage.getItem('isDarkTheme');
    }

    static saveThemeInStorage(isDarkTheme) {
        localStorage.setItem('isDarkTheme', isDarkTheme.toString());
    }

    static isThemeSaved() {
        return localStorage.getItem('isDarkTheme') != null;
    }
}