package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.AreaDto;
import pl.wroblewski.simplyaccounting.models.responses.AreaResponse;
import pl.wroblewski.simplyaccounting.services.AreaService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/areas")
public class AreaController {

    private final AreaService areaService;

    @PostMapping
    public ResponseEntity<List<AreaResponse>> addAreaToPremises(@RequestBody @Validated(CreateInfo.class) AreaDto area) {
        return ResponseEntity.ok(areaService.addNewAreaToPremises(area));
    }

    @PutMapping
    public ResponseEntity<List<AreaResponse>> editArea(@RequestBody @Validated(EditInfo.class) AreaDto area) {
        return ResponseEntity.ok(areaService.editArea(area));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AreaDto> getArea(@PathVariable int id) {
        return ResponseEntity.ok(areaService.getArea(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteArea(@PathVariable int id) {
        areaService.deleteArea(id);
        return ResponseEntity.ok("Successfully deleted an area.");
    }
}
