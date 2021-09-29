import {httpRequestService} from "@/App";

export default class InvoiceTypeService {

    static createInvoiceType(invoiceType) {
        return httpRequestService.post('/invoiceTypes', invoiceType);
    }

    static editInvoiceType(invoiceType) {
        return httpRequestService.put('/invoiceTypes', invoiceType);
    }

    static getInvoiceType(id) {
        return httpRequestService.get(`/invoiceTypes/${id}`);
    }

    static deleteInvoiceType(id) {
        return httpRequestService.delete(`/invoiceTypes/${id}`);
    }

    static getAllInvoiceTypes() {
        return httpRequestService.get(`/invoiceTypes`);
    }
}