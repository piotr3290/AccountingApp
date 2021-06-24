package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.InvoiceDto;
import pl.wroblewski.simplyaccounting.models.responses.InvoiceResponse;
import pl.wroblewski.simplyaccounting.services.InvoiceService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable int id) {
        return ResponseEntity.ok(invoiceService.getInvoice(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable int id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Successfully deleted the invoice.");
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody @Validated(CreateInfo.class) InvoiceDto invoice) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoice));
    }

    @PutMapping
    public ResponseEntity<InvoiceDto> editInvoice(@RequestBody @Validated(EditInfo.class) InvoiceDto invoice) {
        return ResponseEntity.ok(invoiceService.editInvoice(invoice));
    }

    @GetMapping(path = "/cooperative/{id}")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoicesForCooperative(@PathVariable int id) {
        return ResponseEntity.ok(invoiceService.getAllInvoicesForCooperative(id));
    }

}
