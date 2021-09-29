package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordDto;
import pl.wroblewski.simplyaccounting.models.responses.LandlordDetailsResponse;
import pl.wroblewski.simplyaccounting.models.responses.LandlordResponse;
import pl.wroblewski.simplyaccounting.services.LandlordService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

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

    @GetMapping(path = "/details/{id}")
    public ResponseEntity<LandlordDetailsResponse> getLandlordDetails(@PathVariable int id) {
        return ResponseEntity.ok(landlordService.getLandlordDetails(id));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LandlordDto> getLandlord(@PathVariable int id) {
        return ResponseEntity.ok(landlordService.getLandlord(id));
    }

    @GetMapping(path = "/cooperative/{cooperativeId}")
    public ResponseEntity<List<LandlordDto>> getAllLandlordsForCooperative(@PathVariable Integer cooperativeId) {
        return ResponseEntity.ok(landlordService.getAllLandlordsForCooperative(cooperativeId));
    }

    @GetMapping(path = "/premises/{premisesId}")
    public ResponseEntity<List<LandlordDto>> getAllLandlordsForPremises(@PathVariable Integer premisesId) {
        return ResponseEntity.ok(landlordService.getAllLandlordsForPremises(premisesId));
    }
}
