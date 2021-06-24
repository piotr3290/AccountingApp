package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.PeopleNumberDto;
import pl.wroblewski.simplyaccounting.services.PeopleNumberService;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/peopleNumbers")
public class PeopleNumberController {

    private final PeopleNumberService peopleNumberService;

    @PostMapping
    public ResponseEntity<List<PeopleNumberDto>> addPeopleNumberToPremises(@RequestBody @Validated(CreateInfo.class) PeopleNumberDto peopleNumber) {
        return ResponseEntity.ok(peopleNumberService.addNewPeopleNumber(peopleNumber));
    }

    @PutMapping
    public ResponseEntity<List<PeopleNumberDto>> editPeopleNumber(@RequestBody @Validated(EditInfo.class) PeopleNumberDto peopleNumber) {
        return ResponseEntity.ok(peopleNumberService.editPeopleNumber(peopleNumber));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PeopleNumberDto> getPeopleNumber(@PathVariable int id) {
        return ResponseEntity.ok(peopleNumberService.getPeopleNumber(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePeopleNumber(@PathVariable int id){
        peopleNumberService.deletePeopleNumber(id);
        return ResponseEntity.ok("Successfully deleted a people number.");
    }
}
