import {httpRequestService} from "@/App";

export default class InvoiceService {

    static createInvoice(invoice) {
        return httpRequestService.post('/invoices', invoice);
    }

    static editInvoice(invoice) {
        return httpRequestService.put('/invoices', invoice);
    }

    static getInvoice(id) {
        return httpRequestService.get(`/invoices/${id}`);
    }

    static deleteInvoice(id) {
        return httpRequestService.delete(`/invoices/${id}`);
    }

    static getAllInvoicesForCooperative(cooperativeId) {
        return httpRequestService.get(`/invoices/cooperative/${cooperativeId}`);
    }
}