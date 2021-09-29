package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.ChargeTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.ChargeTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeTypeDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChargeTypeService {

    private final ChargeTypeRepository chargeTypeRepository;
    private final ConverterService converterService;

    ChargeTypeEntity getChargeTypeEntity(int id) {
        return chargeTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the charge type."));
    }

    public ChargeTypeDto getChargeType(int id) {
        return converterService.chargeTypeEntityToDto(getChargeTypeEntity(id));
    }

    public List<ChargeTypeDto> createChargeType(ChargeTypeDto chargeType) {
        chargeTypeRepository.saveAndFlush(converterService.chargeTypeDtoToEntity(chargeType));

        return getAllChargeTypes();
    }

    public List<ChargeTypeDto> editChargeType(ChargeTypeDto chargeType) {
        var chargeTypeEntity = getChargeTypeEntity(chargeType.getId())
                .toBuilder()
                .name(chargeType.getName())
                .number(chargeType.getNumber())
                .build();

        chargeTypeRepository.saveAndFlush(chargeTypeEntity);
        return getAllChargeTypes();
    }

    public List<ChargeTypeDto> getAllChargeTypes() {
        return chargeTypeRepository.findAll()
                .stream()
                .map(converterService::chargeTypeEntityToDto)
                .sorted(Comparator.comparing(ChargeTypeDto::getNumber))
                .collect(Collectors.toList());
    }

    public void checkChargeTypeExists(int id) {
        getChargeTypeEntity(id);
    }
}
