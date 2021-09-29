package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.OpeningBalanceDto;
import pl.wroblewski.simplyaccounting.models.requests.CalculateOpeningBalanceRequest;
import pl.wroblewski.simplyaccounting.models.responses.OpeningBalanceResponse;
import pl.wroblewski.simplyaccounting.services.CalculateOpeningBalanceService;
import pl.wroblewski.simplyaccounting.services.OpeningBalanceService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/openingBalances")
public class OpeningBalanceController {

    private final OpeningBalanceService openingBalanceService;
    private final CalculateOpeningBalanceService calculateOpeningBalanceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<OpeningBalanceDto> getOpeningBalance(@PathVariable int id) {
        return ResponseEntity.ok(openingBalanceService.getOpeningBalance(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteOpeningBalance(@PathVariable int id) {
        openingBalanceService.deleteOpeningBalance(id);
        return ResponseEntity.ok("Successfully deleted the opening balance.");
    }

    @PostMapping
    public ResponseEntity<List<OpeningBalanceResponse>> createOpeningBalance(
            @RequestBody @Validated(CreateInfo.class) OpeningBalanceDto openingBalance) {
        return ResponseEntity.ok(openingBalanceService.createOpeningBalance(openingBalance));
    }

    @PutMapping
    public ResponseEntity<List<OpeningBalanceResponse>> editOpeningBalance(
            @RequestBody @Validated(EditInfo.class) OpeningBalanceDto openingBalance) {
        return ResponseEntity.ok(openingBalanceService.editOpeningBalance(openingBalance));
    }

    @GetMapping(path = "/cooperative/{id}")
    public ResponseEntity<List<OpeningBalanceDto>> getOpeningBalancesForCooperative(
            @PathVariable int id) {
        return ResponseEntity.ok(openingBalanceService.getOpeningBalancesForCooperative(id));
    }

    @GetMapping(path = "/landlord/{id}")
    public ResponseEntity<List<OpeningBalanceDto>> getOpeningBalancesForLandlord(
            @PathVariable int id) {
        return ResponseEntity.ok(openingBalanceService.getOpeningBalancesForLandlord(id));
    }

    @GetMapping(path = "/contractor/{id}")
    public ResponseEntity<List<OpeningBalanceDto>> getOpeningBalancesForContractor(
            @PathVariable int id) {
        return ResponseEntity.ok(openingBalanceService.getOpeningBalancesForContractor(id));
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<List<OpeningBalanceResponse>> getOpeningBalancesForAccount(
            @PathVariable int id) {
        return ResponseEntity.ok(openingBalanceService.getOpeningBalancesForAccount(id));
    }

    @PostMapping(path = "/calculate/cooperative")
    public ResponseEntity<String> calculateOpeningBalance(@RequestBody @Validated CalculateOpeningBalanceRequest request) {
        calculateOpeningBalanceService.calculateAllClosingBalance(request.getCooperativeId(), request.getYear());
        return ResponseEntity.ok("Closing balances have been calculated.");
    }
}
