package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordDto;
import pl.wroblewski.simplyaccounting.models.responses.LandlordResponse;
import pl.wroblewski.simplyaccounting.services.LandlordService;
import pl.wroblewski.simplyaccounting.validation.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/landlords")
public class LandlordController {

    private final LandlordService landlordService;

    @GetMapping
    public ResponseEntity<List<LandlordResponse>> getAllLandlords() {
        return ResponseEntity.ok(landlordService.getAllLandlords());
    }

    @PostMapping
    public ResponseEntity<LandlordDto> createLandlord(@RequestBody @Validated(CreateInfo.class) LandlordDto landlord) {
        return ResponseEntity.ok(landlordService.createLandlord(landlord));
    }

    @PutMapping
    public ResponseEntity<LandlordDto> updateLandlord(@RequestBody @Validated(EditInfo.class) LandlordDto landlord) {
        return ResponseEntity.ok(landlordService.updateLandlord(landlord));
    }
}
