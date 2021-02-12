package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PremisesTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PremisesTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PremisesTypeService {

    private final PremisesTypeRepository premisesTypeRepository;
    private final ConverterService converterService;

    public List<PremisesTypeDto> getAllPremisesTypes() {
        return premisesTypeRepository
                .findAll()
                .stream()
                .map(converterService::premisesTypeEntityToDto)
                .collect(Collectors.toList());
    }

    PremisesTypeEntity getPremisesTypeEntity(int id) {
        return premisesTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find premises type."));
    }
}
