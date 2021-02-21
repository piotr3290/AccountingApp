package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.CooperativeDto;
import pl.wroblewski.simplyaccounting.models.responses.CooperativeResponse;
import pl.wroblewski.simplyaccounting.services.CooperativeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateCooperativeInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditCooperativeInfo;

@RestController
@AllArgsConstructor
@RequestMapping("/cooperatives")
public class CooperativeController {

    private final CooperativeService cooperativeService;

    @GetMapping
    public ResponseEntity<Page<CooperativeDto>> getAllCooperatives(@RequestHeader(value = "page-number", required = false, defaultValue = "1") int pageNumber, @RequestHeader(value = "items-per-page", required = false, defaultValue = "10") int itemsPerPage) {
        return ResponseEntity.ok(cooperativeService.getAll(pageNumber, itemsPerPage));
    }

    @PostMapping
    public ResponseEntity<CooperativeDto> createCooperative(@Validated(CreateCooperativeInfo.class) @RequestBody CooperativeDto cooperative) {
        return ResponseEntity.ok(cooperativeService.createCooperative(cooperative));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CooperativeDto> getCooperative(@PathVariable int id) {
        return ResponseEntity.ok(cooperativeService.getCooperative(id));
    }

    @PutMapping
    public ResponseEntity<CooperativeDto> updateCooperative(@Validated(EditCooperativeInfo.class) @RequestBody CooperativeDto cooperative) {
        return ResponseEntity.ok(cooperativeService.updateCooperative(cooperative));
    }

    @GetMapping(path = "/buildings/{id}")
    public ResponseEntity<CooperativeResponse> getCooperativeWithBuildings(@PathVariable int id) {
        return ResponseEntity.ok(cooperativeService.getCooperativeWithBuildings(id));
    }

}

