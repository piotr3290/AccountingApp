package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeTypeDto;
import pl.wroblewski.simplyaccounting.services.ChargeTypeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chargeTypes")
public class ChargeTypeController {

    private final ChargeTypeService chargeTypeService;

    @GetMapping
    public ResponseEntity<List<ChargeTypeDto>> getAllChargeTypes() {
        return ResponseEntity.ok(chargeTypeService.getAllChargeTypes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ChargeTypeDto> getChargeType(@PathVariable int id) {
        return ResponseEntity.ok(chargeTypeService.getChargeType(id));
    }

    @PostMapping
    public ResponseEntity<List<ChargeTypeDto>> createChargeType(
            @RequestBody @Validated(CreateInfo.class) ChargeTypeDto chargeType) {
        return ResponseEntity.ok(chargeTypeService.createChargeType(chargeType));
    }

    @PutMapping
    public ResponseEntity<List<ChargeTypeDto>> editChargeType(
            @RequestBody @Validated(EditInfo.class) ChargeTypeDto chargeType) {
        return ResponseEntity.ok(chargeTypeService.editChargeType(chargeType));
    }
}
