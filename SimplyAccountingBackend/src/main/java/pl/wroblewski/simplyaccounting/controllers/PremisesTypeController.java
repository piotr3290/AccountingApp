package pl.wroblewski.simplyaccounting.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesTypeDto;
import pl.wroblewski.simplyaccounting.services.PremisesTypeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@RequestMapping("/premisesTypes")
@AllArgsConstructor
public class PremisesTypeController {

    private final PremisesTypeService premisesTypeService;

    @GetMapping
    public ResponseEntity<List<PremisesTypeDto>> getAllPremisesTypes() {
        return ResponseEntity.ok(premisesTypeService.getAllPremisesTypes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PremisesTypeDto> getPremisesType(@PathVariable int id) {
        return ResponseEntity.ok(premisesTypeService.getPremisesType(id));
    }

    @PostMapping
    public ResponseEntity<List<PremisesTypeDto>> createPremisesType(
            @RequestBody @Validated(CreateInfo.class) PremisesTypeDto premisesType) {
        return ResponseEntity.ok(premisesTypeService.createPremisesType(premisesType));
    }

    @PutMapping
    public ResponseEntity<List<PremisesTypeDto>> editPremisesType(
            @RequestBody @Validated(EditInfo.class) PremisesTypeDto premisesType) {
        return ResponseEntity.ok(premisesTypeService.editPremisesType(premisesType));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePremisesType(@PathVariable Integer id) {
        premisesTypeService.deletePremisesType(id);
        return ResponseEntity.ok("Successfully deleted premises type");
    }
}
