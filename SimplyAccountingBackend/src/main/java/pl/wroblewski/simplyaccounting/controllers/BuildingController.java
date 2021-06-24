package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.BuildingDto;
import pl.wroblewski.simplyaccounting.models.responses.BuildingResponse;
import pl.wroblewski.simplyaccounting.services.BuildingService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateBuildingInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditBuildingInfo;

import java.util.List;

@RestController
@RequestMapping("/buildings")
@AllArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @PostMapping
    public ResponseEntity<BuildingDto> createBuilding(
            @Validated(CreateBuildingInfo.class) @RequestBody BuildingDto building) {
        return ResponseEntity.ok(buildingService.createBuilding(building));
    }

    @PutMapping
    public ResponseEntity<BuildingDto> updateBuilding(
            @Validated(EditBuildingInfo.class) @RequestBody BuildingDto building) {
        return ResponseEntity.ok(buildingService.updateBuilding(building));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BuildingDto> getBuilding(@PathVariable int id) {
        return ResponseEntity.ok(buildingService.getBuilding(id));
    }

    @GetMapping(path = "/premises/{id}")
    public ResponseEntity<BuildingResponse> getBuildingWithPremises(@PathVariable int id) {
        return ResponseEntity.ok(buildingService.getBuildingWithPremises(id));
    }

    @GetMapping(path = "/cooperative/{id}")
    public ResponseEntity<List<BuildingDto>> getAllBuildingsForCooperative(@PathVariable Integer id) {
        return ResponseEntity.ok(buildingService.getAllBuildingsForCooperative(id));
    }
}
