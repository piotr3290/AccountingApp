package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesLandlordDto;
import pl.wroblewski.simplyaccounting.models.responses.PremisesLandlordResponse;
import pl.wroblewski.simplyaccounting.services.PremisesLandlordService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/premisesLandlords")
public class PremisesLandlordController {

    private final PremisesLandlordService premisesLandlordService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PremisesLandlordDto> getPremisesLandlord(@PathVariable int id) {
        return ResponseEntity.ok(premisesLandlordService.getPremisesLandlord(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePremisesLandlord(@PathVariable int id) {
        premisesLandlordService.deletePremisesLandlord(id);
        return ResponseEntity.ok("Successfully deleted the landlord's premises.");
    }

    @PostMapping
    public ResponseEntity<List<PremisesLandlordResponse>> createPremiseLandlord(
            @RequestBody @Validated(CreateInfo.class) PremisesLandlordDto premisesLandlord) {
        return ResponseEntity.ok(premisesLandlordService.createPremisesLandlord(premisesLandlord));
    }

    @PutMapping
    public ResponseEntity<List<PremisesLandlordResponse>> editPremisesLandlord(
            @RequestBody @Validated(EditInfo.class) PremisesLandlordDto premisesLandlord) {
        return ResponseEntity.ok(premisesLandlordService.editPremisesLandlord(premisesLandlord));
    }

}
