package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.ConsumptionDto;
import pl.wroblewski.simplyaccounting.models.responses.ConsumptionResponse;
import pl.wroblewski.simplyaccounting.services.ConsumptionService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/consumptions")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConsumptionDto> getConsumption(@PathVariable int id) {
        return ResponseEntity.ok(consumptionService.getConsumption(id));
    }

    @PostMapping
    public ResponseEntity<List<ConsumptionResponse>> createConsumption(
            @RequestBody @Validated(CreateInfo.class) ConsumptionDto consumption) {
        return ResponseEntity.ok(consumptionService.createConsumption(consumption));
    }

    @PutMapping
    public ResponseEntity<List<ConsumptionResponse>> editConsumption(
            @RequestBody @Validated(EditInfo.class) ConsumptionDto consumption) {
        return ResponseEntity.ok(consumptionService.editConsumption(consumption));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteConsumption(@PathVariable Integer id) {
        consumptionService.deleteConsumption(id);
        return ResponseEntity.ok("Successfully deleted the consumption.");
    }
}
