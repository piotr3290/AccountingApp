package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordPaymentDto;
import pl.wroblewski.simplyaccounting.models.responses.LandlordPaymentResponse;
import pl.wroblewski.simplyaccounting.services.LandlordPaymentService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/landlordPayments")
public class LandlordPaymentController {

    private final LandlordPaymentService landlordPaymentService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<LandlordPaymentDto> getLandlordPayment(@PathVariable int id) {
        return ResponseEntity.ok(landlordPaymentService.getLandlordPayment(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLandlordPayment(@PathVariable int id) {
        landlordPaymentService.deleteLandlordPayment(id);
        return ResponseEntity.ok("Successfully deleted a payment.");
    }

    @PostMapping
    public ResponseEntity<LandlordPaymentDto> createLandlordPayment(
            @RequestBody @Validated(CreateInfo.class) LandlordPaymentDto landlordPayment) {
        return ResponseEntity.ok(landlordPaymentService.createLandlordPayment(landlordPayment));
    }

    @PutMapping
    public ResponseEntity<LandlordPaymentDto> editLandlordPayment(
            @RequestBody @Validated(EditInfo.class) LandlordPaymentDto landlordPayment) {
        return ResponseEntity.ok(landlordPaymentService.editLandlordPayment(landlordPayment));
    }

    @GetMapping(path = "/cooperative/{cooperativeId}/year/{year}")
    public ResponseEntity<List<LandlordPaymentResponse>> getAllLandlordPaymentForYear(
            @PathVariable Integer cooperativeId, @PathVariable Short year) {
        return ResponseEntity.ok(landlordPaymentService.getAllLandlordPaymentForYear(cooperativeId, year));
    }
}
