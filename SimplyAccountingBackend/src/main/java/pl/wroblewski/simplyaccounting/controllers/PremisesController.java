package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesDto;
import pl.wroblewski.simplyaccounting.models.responses.PremisesResponse;
import pl.wroblewski.simplyaccounting.services.PremisesService;
import pl.wroblewski.simplyaccounting.validation.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.EditInfo;

@RestController
@RequestMapping("/premises")
@AllArgsConstructor
public class PremisesController {

    private final PremisesService premisesService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PremisesDto> getPremises(@PathVariable int id) {
        return ResponseEntity.ok(premisesService.getPremises(id));
    }

    @PostMapping
    public ResponseEntity<PremisesDto> createPremises(@RequestBody @Validated(CreateInfo.class) PremisesDto premises) {
        return ResponseEntity.ok(premisesService.createPremises(premises));
    }

    @PutMapping
    public ResponseEntity<PremisesDto> updatePremises(@RequestBody @Validated(EditInfo.class) PremisesDto premises) {
        return ResponseEntity.ok(premisesService.updatePremises(premises));
    }

    @GetMapping(path = "/details/{id}")
    public ResponseEntity<PremisesResponse> getPremisesResponse(@PathVariable int id) {
        return ResponseEntity.ok(premisesService.getPremisesResponse(id));
    }


}
