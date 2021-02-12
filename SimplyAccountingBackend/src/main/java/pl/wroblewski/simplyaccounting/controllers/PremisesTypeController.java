package pl.wroblewski.simplyaccounting.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesTypeDto;
import pl.wroblewski.simplyaccounting.services.PremisesTypeService;

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
}
