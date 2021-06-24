package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.InvoiceTypeDto;
import pl.wroblewski.simplyaccounting.services.InvoiceTypeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/invoiceTypes")
public class InvoiceTypeController {

    private final InvoiceTypeService invoiceTypeService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceTypeDto> getInvoiceType(@PathVariable int id) {
        return ResponseEntity.ok(invoiceTypeService.getInvoiceType(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteInvoiceType(@PathVariable int id) {
        invoiceTypeService.deleteInvoiceType(id);
        return ResponseEntity.ok("Successfully deleted the invoice's type.");
    }

    @GetMapping
    public ResponseEntity<List<InvoiceTypeDto>> getAllInvoiceTypes() {
        return ResponseEntity.ok(invoiceTypeService.getAllInvoiceTypes());
    }

    @PostMapping
    public ResponseEntity<InvoiceTypeDto> createInvoiceType(
            @RequestBody @Validated(CreateInfo.class) InvoiceTypeDto invoiceType) {
        return ResponseEntity.ok(invoiceTypeService.createInvoiceType(invoiceType));
    }

    @PutMapping
    ResponseEntity<InvoiceTypeDto> editInvoiceType(
            @RequestBody @Validated(EditInfo.class) InvoiceTypeDto invoiceType) {
        return ResponseEntity.ok(invoiceTypeService.editInvoiceType(invoiceType));
    }
}
