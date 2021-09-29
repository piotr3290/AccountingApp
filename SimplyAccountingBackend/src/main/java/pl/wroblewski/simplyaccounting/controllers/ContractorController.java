package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.ContractorDto;
import pl.wroblewski.simplyaccounting.services.ContractorService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@RequestMapping("/contractors")
@AllArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    @GetMapping
    public ResponseEntity<List<ContractorDto>> getAllContractors() {
        return ResponseEntity.ok(contractorService.getAllContractors());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContractorDto> getContractor(@PathVariable Integer id) {
        return ResponseEntity.ok(contractorService.getContractor(id));
    }

    @PostMapping
    public ResponseEntity<ContractorDto> createContractor(
            @RequestBody @Validated(CreateInfo.class) ContractorDto contractor) {
        return ResponseEntity.ok(contractorService.createContractor(contractor));
    }

    @PutMapping
    public ResponseEntity<ContractorDto> updateContractor(
            @RequestBody @Validated(EditInfo.class) ContractorDto contractor) {
        return ResponseEntity.ok(contractorService.updateContractor(contractor));
    }
}
