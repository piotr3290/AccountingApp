package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.AreaTypeDto;
import pl.wroblewski.simplyaccounting.services.AreaTypeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/areaTypes")
public class AreaTypeController {

    private final AreaTypeService areaTypeService;

    @GetMapping
    public ResponseEntity<List<AreaTypeDto>> getAllAreaTypes() {
        return ResponseEntity.ok(areaTypeService.getAllAreaTypes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AreaTypeDto> getAreaType(@PathVariable int id) {
        return ResponseEntity.ok(areaTypeService.getAreaType(id));
    }

    @PutMapping
    public ResponseEntity<List<AreaTypeDto>> editAreaType(
            @RequestBody @Validated(EditInfo.class) AreaTypeDto areaType) {
        return ResponseEntity.ok(areaTypeService.editAreaType(areaType));
    }

    @PostMapping
    public ResponseEntity<List<AreaTypeDto>> createAreaType(
            @RequestBody @Validated(CreateInfo.class) AreaTypeDto areaType) {
        return ResponseEntity.ok(areaTypeService.createAreaType(areaType));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAreaType(@PathVariable Integer id){
        areaTypeService.deleteAreaType(id);
        return ResponseEntity.ok("Successfully deleted area type.");
    }
}
