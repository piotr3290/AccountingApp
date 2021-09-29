import axios from "axios";
import AuthorizationStorageService from "@/core/storage/AuthorizationStorageService";

export default class HttpRequestService {

    constructor(baseUrl) {
        this.instance = axios.create({
            timeout: 30000,
            baseURL: baseUrl,
            headers: {
                'Content-Type': 'application/json-patch+json',
                'Access-Control-Allow-Origin': '*',
                'Accept': '*/*'
            }
        });
        this.AUTHORIZATION_PREFIX = 'Bearer ';
    }

    get(url, pageNumber, itemsPerPage) {
        return this.executeRequest('GET', url, undefined, pageNumber, itemsPerPage);
    }

    post(url, data) {
        return this.executeRequest('POST', url, data);
    }

    put(url, data) {
        return this.executeRequest('PUT', url, data);
    }

    delete(url) {
        return this.executeRequest('DELETE', url);
    }

    executeRequest(method, url, data, pageNumber, itemsPerPage) {

        let headers = {};

        if (pageNumber && itemsPerPage) {
            headers['page-number'] = pageNumber;
            headers['items-per-page'] = itemsPerPage;
        }

        if (AuthorizationStorageService.isAuthorized()) {
            headers['Authorization'] = this.AUTHORIZATION_PREFIX + AuthorizationStorageService.getTokenFromStorage();
        }

        return this.instance.request({
            headers: headers,
            url: url,
            method: method,
            data: data
        });
    }
}