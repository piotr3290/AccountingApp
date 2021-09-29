package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.ContractorPaymentDto;
import pl.wroblewski.simplyaccounting.models.responses.ContractorPaymentResponse;
import pl.wroblewski.simplyaccounting.services.ContractorPaymentService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contractorPayments")
public class ContractorPaymentController {

    private final ContractorPaymentService contractorPaymentService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContractorPaymentDto> getContractorPayment(@PathVariable int id) {
        return ResponseEntity.ok(contractorPaymentService.getContractorPayment(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteContractorPayment(@PathVariable int id) {
        contractorPaymentService.deleteContractorPayment(id);
        return ResponseEntity.ok("Successfully deleted a payment.");
    }

    @PostMapping
    public ResponseEntity<ContractorPaymentDto> createContractorPayment(
            @RequestBody @Validated(CreateInfo.class) ContractorPaymentDto contractorPayment) {
        return ResponseEntity.ok(contractorPaymentService.createContractorPayment(contractorPayment));
    }

    @PutMapping
    public ResponseEntity<ContractorPaymentDto> editContractorPayment(
            @RequestBody @Validated(EditInfo.class) ContractorPaymentDto contractorPayment) {
        return ResponseEntity.ok(contractorPaymentService.editContractorPayment(contractorPayment));
    }

    @GetMapping(path = "/cooperative/{cooperativeId}/year/{year}")
    public ResponseEntity<List<ContractorPaymentResponse>> getAllContractorPaymentForYear(
            @PathVariable Integer cooperativeId, @PathVariable Short year) {
        return ResponseEntity.ok(contractorPaymentService.getAllContractorPaymentForYear(cooperativeId, year));
    }
}
