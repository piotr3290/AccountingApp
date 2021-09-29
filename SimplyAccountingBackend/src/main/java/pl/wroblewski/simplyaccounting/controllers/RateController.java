package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.RateDto;
import pl.wroblewski.simplyaccounting.models.responses.RateResponse;
import pl.wroblewski.simplyaccounting.services.RateService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<RateDto> getRate(@PathVariable int id) {
        return ResponseEntity.ok(rateService.getRate(id));
    }

    @PostMapping
    public ResponseEntity<RateDto> createRate(@RequestBody @Validated(CreateInfo.class) RateDto rate) {
        return ResponseEntity.ok(rateService.createRate(rate));
    }

    @PutMapping
    public ResponseEntity<RateDto> editRate(@RequestBody @Validated(EditInfo.class) RateDto rate) {
        return ResponseEntity.ok(rateService.editRate(rate));
    }

    @GetMapping
    public ResponseEntity<List<RateResponse>> getAllRates() {
        return ResponseEntity.ok(rateService.getAllRates());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable Integer id){
        rateService.deleteRate(id);
        return ResponseEntity.ok("Successfully deleted the rate.");
    }
}
