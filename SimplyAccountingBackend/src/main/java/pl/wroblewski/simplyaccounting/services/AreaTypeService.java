package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.AreaTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.AreaTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectReferenceException;
import pl.wroblewski.simplyaccounting.models.dtos.AreaTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaTypeService {

    private final AreaTypeRepository areaTypeRepository;
    private final ConverterService converterService;

    AreaTypeEntity getAreaTypeEntity(int id) {
        return areaTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the area type."));
    }

    public void checkAreaTypeExists(int id) {
        getAreaTypeEntity(id);
    }

    public AreaTypeDto getAreaType(int id) {
        return converterService.areaTypeEntityToDto(getAreaTypeEntity(id));
    }

    public List<AreaTypeDto> getAllAreaTypes() {
        return areaTypeRepository.findAll()
                .stream()
                .map(converterService::areaTypeEntityToDto)
                .collect(Collectors.toList());
    }

    public List<AreaTypeDto> editAreaType(AreaTypeDto areaType) {
        var areaTypeEntity = getAreaTypeEntity(areaType.getId());
        areaTypeEntity.setName(areaType.getName());
        areaTypeRepository.saveAndFlush(areaTypeEntity);
        return getAllAreaTypes();
    }

    public List<AreaTypeDto> createAreaType(AreaTypeDto areaType) {
        areaTypeRepository.saveAndFlush(converterService.areaTypeDtoToEntity(areaType));
        return getAllAreaTypes();
    }

    public void deleteAreaType(Integer id) {
        var areaTypeEntity = getAreaTypeEntity(id);
        if (!areaTypeEntity.getAreas().isEmpty() || !areaTypeEntity.getChargeElements().isEmpty()) {
            throw new ObjectReferenceException("Cannot delete area type.");
        }
        areaTypeRepository.delete(areaTypeEntity);
    }
}
