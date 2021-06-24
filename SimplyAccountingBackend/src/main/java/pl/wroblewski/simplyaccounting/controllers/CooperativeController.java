package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.CooperativeDto;
import pl.wroblewski.simplyaccounting.models.responses.CooperativeResponse;
import pl.wroblewski.simplyaccounting.services.CooperativeService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cooperatives")
public class CooperativeController {

    private final CooperativeService cooperativeService;

    @GetMapping
    public ResponseEntity<Page<CooperativeDto>> getAllCooperatives(
            @RequestHeader(value = "page-number", required = false, defaultValue = "1") int pageNumber,
            @RequestHeader(value = "items-per-page", required = false, defaultValue = "10") int itemsPerPage) {
        return ResponseEntity.ok(cooperativeService.getPageCooperative(pageNumber, itemsPerPage));
    }

    @PostMapping
    public ResponseEntity<CooperativeDto> createCooperative(
            @Validated(CreateInfo.class) @RequestBody CooperativeDto cooperative) {
        return ResponseEntity.ok(cooperativeService.createCooperative(cooperative));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CooperativeDto> getCooperative(@PathVariable int id) {
        return ResponseEntity.ok(cooperativeService.getCooperative(id));
    }

    @PutMapping
    public ResponseEntity<CooperativeDto> updateCooperative(
            @Validated(EditInfo.class) @RequestBody CooperativeDto cooperative) {
        return ResponseEntity.ok(cooperativeService.updateCooperative(cooperative));
    }

    @GetMapping(path = "/buildings/{id}")
    public ResponseEntity<CooperativeResponse> getCooperativeWithBuildings(@PathVariable int id) {
        return ResponseEntity.ok(cooperativeService.getCooperativeWithBuildings(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CooperativeDto>> getAllCooperatives() {
        return ResponseEntity.ok(cooperativeService.getAllCooperatives());
    }

    @GetMapping(path = "/premises/{id}")
    public ResponseEntity<CooperativeDto> getPremisesCooperative(@PathVariable Integer id) {
        return ResponseEntity.ok(cooperativeService.getPremisesCooperative(id));
    }

}

