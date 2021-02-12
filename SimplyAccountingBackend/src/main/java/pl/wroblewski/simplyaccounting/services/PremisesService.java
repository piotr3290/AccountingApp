package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PremisesEntity;
import pl.wroblewski.simplyaccounting.db.entities.PremisesTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PremisesRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesDto;
import pl.wroblewski.simplyaccounting.models.responses.PremisesResponse;


@Service
@AllArgsConstructor
public class PremisesService {

    private final PremisesRepository premisesRepository;
    private final ConverterService converterService;
    private final PremisesTypeService premisesTypeService;
    private final BuildingService buildingService;

    public PremisesDto getPremises(int id) {
        return converterService.premisesEntityToDto(getPremisesEntity(id));
    }

    PremisesEntity getPremisesEntity(int id) {
        return premisesRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find premises."));
    }

    public PremisesDto createPremises(PremisesDto premises) {
        premisesTypeService.getPremisesTypeEntity(premises.getPremisesTypeId());
        buildingService.getBuildingEntity(premises.getBuildingId());
        return converterService.premisesEntityToDto(premisesRepository.save(converterService.premisesDtoToEntity(premises)));
    }

    public PremisesResponse getPremisesResponse(int id) {
        return converterService.premisesEntityToResponse(getPremisesEntity(id));
    }

    public PremisesDto updatePremises(PremisesDto premises) {
        premisesTypeService.getPremisesTypeEntity(premises.getPremisesTypeId());
        return converterService.premisesEntityToDto(premisesRepository.save(
                getPremisesEntity(premises.getId())
                        .toBuilder()
                        .premisesNumber(premises.getPremisesNumber())
                        .premisesType(PremisesTypeEntity.builder().id(premises.getPremisesTypeId()).build())
                        .build()));
    }
}
