package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PremisesTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PremisesTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectReferenceException;
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

    public void checkPremisesTypeExists(int premisesTypeId) {
        getPremisesTypeEntity(premisesTypeId);
    }

    public PremisesTypeDto getPremisesType(int id) {
        return converterService.premisesTypeEntityToDto(getPremisesTypeEntity(id));
    }

    public List<PremisesTypeDto> createPremisesType(PremisesTypeDto premisesType) {
        premisesTypeRepository.saveAndFlush(converterService.premisesTypeDtoToEntity(premisesType));
        return getAllPremisesTypes();
    }

    public List<PremisesTypeDto> editPremisesType(PremisesTypeDto premisesType) {
        var premisesTypeEntity = getPremisesTypeEntity(premisesType.getId())
                .toBuilder()
                .name(premisesType.getName())
                .build();
        premisesTypeRepository.save(premisesTypeEntity);
        return getAllPremisesTypes();
    }

    public void deletePremisesType(Integer id) {
        var premisesTypeEntity = getPremisesTypeEntity(id);
        if (!premisesTypeEntity.getPremises().isEmpty() || !premisesTypeEntity.getChargeElements().isEmpty()) {
            throw new ObjectReferenceException("Cannot delete premises type.");
        }
        premisesTypeRepository.delete(premisesTypeEntity);
    }
}
