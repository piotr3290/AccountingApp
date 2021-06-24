package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeElementDto;
import pl.wroblewski.simplyaccounting.models.responses.ChargeElementResponse;
import pl.wroblewski.simplyaccounting.services.ChargeElementService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chargeElements")
public class ChargeElementController {

    private final ChargeElementService chargeElementService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ChargeElementDto> getChargeElement(@PathVariable int id) {
        return ResponseEntity.ok(chargeElementService.getChargeElement(id));
    }

    @PostMapping
    public ResponseEntity<ChargeElementDto> createChargeElement(
            @RequestBody @Validated(CreateInfo.class) ChargeElementDto chargeElement) {
        return ResponseEntity.ok(chargeElementService.createChargeElement(chargeElement));
    }

    @PutMapping
    public ResponseEntity<ChargeElementDto> editChargeElement(
            @RequestBody @Validated(EditInfo.class) ChargeElementDto chargeElement) {
        return ResponseEntity.ok(chargeElementService.editChargeElement(chargeElement));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteChargeElement(@PathVariable Integer id) {
        chargeElementService.deleteChargeElement(id);
        return ResponseEntity.ok("Successfully deleted charge element.");
    }

    @GetMapping(path = "/cooperative/{cooperativeId}")
    public ResponseEntity<List<ChargeElementResponse>> getAllChargeElementsForCooperative(
            @PathVariable Integer cooperativeId) {
        return ResponseEntity.ok(chargeElementService.getAllChargeElementsForCooperative(cooperativeId));
    }
}
