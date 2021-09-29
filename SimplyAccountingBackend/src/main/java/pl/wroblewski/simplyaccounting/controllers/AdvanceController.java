package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.AdvanceDto;
import pl.wroblewski.simplyaccounting.models.requests.CalculateAdvancesRequest;
import pl.wroblewski.simplyaccounting.models.responses.AdvancePremisesResponse;
import pl.wroblewski.simplyaccounting.services.AdvanceService;
import pl.wroblewski.simplyaccounting.services.CalculatingAdvanceService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/advances")
public class AdvanceController {

    private final AdvanceService advanceService;
    private final CalculatingAdvanceService calculatingAdvanceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AdvanceDto> getAdvance(@PathVariable int id) {
        return ResponseEntity.ok(advanceService.getAdvance(id));
    }

    @PostMapping
    public ResponseEntity<AdvanceDto> createAdvance(@RequestBody @Validated(CreateInfo.class) AdvanceDto advance) {
        return ResponseEntity.ok(advanceService.createAdvance(advance));
    }

    @PutMapping
    public ResponseEntity<AdvanceDto> editAdvance(@RequestBody @Validated(EditInfo.class) AdvanceDto advance) {
        return ResponseEntity.ok(advanceService.editAdvance(advance));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAdvance(@PathVariable Integer id){
        advanceService.deleteAdvance(id);
        return ResponseEntity.ok("Successfully deleted the advance.");
    }

    @GetMapping(path = "/premises/{premisesId}/year/{year}")
    public ResponseEntity<List<AdvancePremisesResponse>> getAllAdvancesForPremisesAndYear(
            @PathVariable Integer premisesId, @PathVariable Short year) {
        return ResponseEntity.ok(advanceService.getAllAdvancesForPremisesAndYear(premisesId, year));
    }

    @PostMapping(path = "/calculate/cooperative")
    public ResponseEntity<String> calculateAdvancesForCooperative(
            @RequestBody @Validated CalculateAdvancesRequest request) {
        calculatingAdvanceService.calculateAllAdvancesForCooperative(request.getId(), request.getMonth(), request.getYear());
        return ResponseEntity.ok("Advances calculated.");
    }

    @PostMapping(path = "/calculate/building")
    public ResponseEntity<String> calculateAdvancesForBuilding(
            @RequestBody @Validated CalculateAdvancesRequest request) {
        calculatingAdvanceService.calculateAllAdvancesForBuilding(request.getId(), request.getMonth(), request.getYear());
        return ResponseEntity.ok("Advances calculated.");
    }

    @PostMapping(path = "/calculate/premises")
    public ResponseEntity<String> calculateAdvancesForPremises(
            @RequestBody @Validated CalculateAdvancesRequest request) {
        calculatingAdvanceService.calculateAllAdvancesForPremises(request.getId(), request.getMonth(), request.getYear());
        return ResponseEntity.ok("Advances calculated.");
    }
}
