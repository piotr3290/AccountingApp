package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.AdvanceEntity;
import pl.wroblewski.simplyaccounting.db.repositories.AdvanceRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectAlreadyExistsException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.AdvanceDto;
import pl.wroblewski.simplyaccounting.models.responses.AdvancePremisesResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdvanceService {

    private final AdvanceRepository advanceRepository;
    private final ConverterService converterService;
    private final PremisesService premisesService;
    private final LandlordService landlordService;
    private final ChargeTypeService chargeTypeService;

    public AdvanceDto createAdvance(AdvanceDto advance) {
        premisesService.checkPremisesExists(advance.getPremisesId());
        landlordService.checkLandlordExists(advance.getLandlordId());
        chargeTypeService.checkChargeTypeExists(advance.getChargeTypeId());

        checkAdvanceUniqueness(advance.getLandlordId(), advance.getPremisesId(), advance.getChargeTypeId(),
                advance.getMonth(), advance.getYear());

        var advanceEntity = converterService.advanceDtoToEntity(advance);

        return converterService.advanceEntityToDto(saveAdvance(advanceEntity));
    }

    public AdvanceDto editAdvance(AdvanceDto advance) {
        var advanceEntity = getAdvanceEntity(advance.getId())
                .toBuilder()
                .amount(advance.getAmount())
                .realizationDate(advance.getRealizationDate())
                .build();

        return converterService.advanceEntityToDto(saveAdvance(advanceEntity));
    }

    public void saveAdvances(Collection<AdvanceDto> advances) {

        saveAdvancesEntities(
                advances.stream()
                        .map(advance -> {
                            var advanceEntity = getAdvanceEntity(advance.getLandlordId(), advance.getPremisesId(),
                                    advance.getChargeTypeId(), advance.getMonth(), advance.getYear());

                            if (advanceEntity == null) {
                                advanceEntity = converterService.advanceDtoToEntity(advance);
                            } else {
                                advanceEntity.setAmount(advance.getAmount());
                                advanceEntity.setRealizationDate(advance.getRealizationDate());
                            }
                            return advanceEntity;
                        })
                        .collect(Collectors.toList()));
    }


    public AdvanceDto getAdvance(int id) {
        return converterService.advanceEntityToDto(getAdvanceEntity(id));
    }

    private AdvanceEntity getAdvanceEntity(int id) {
        return advanceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the advance."));
    }

    private AdvanceEntity saveAdvance(AdvanceEntity advance) {
        return advanceRepository.save(advance);
    }

    private List<AdvanceEntity> saveAdvancesEntities(Collection<AdvanceEntity> advances) {
        return advanceRepository.saveAll(advances);
    }

    private void checkAdvanceUniqueness(Integer landlordId, Integer premisesId, Integer chargeTypeId,
                                        Byte month, Short year) {
        if (advanceExists(landlordId, premisesId, chargeTypeId, month, year)) {
            throw new ObjectAlreadyExistsException("This advance already exists.");
        }
    }

    public boolean advanceExists(Integer landlordId, Integer premisesId, Integer chargeTypeId,
                                 Byte month, Short year) {
        return advanceRepository.existsByLandlord_IdAndPremises_IdAndChargeType_IdAndMonthAndYear(
                landlordId, premisesId, chargeTypeId, month, year);
    }

    private AdvanceEntity getAdvanceEntity(Integer landlordId, Integer premisesId, Integer chargeTypeId,
                                           Byte month, Short year) {
        return advanceRepository.findByLandlord_IdAndPremises_IdAndChargeType_IdAndMonthAndYear(
                landlordId, premisesId, chargeTypeId, month, year)
                .orElse(null);
    }

    public List<AdvancePremisesResponse> getAllAdvancesForPremisesAndYear(Integer premisesId, Short year) {
        return getAllAdvanceEntitiesForPremisesAndYear(premisesId, year)
                .stream()
                .map(converterService::advanceEntityToResponse)
                .collect(Collectors.toList());
    }

    List<AdvanceEntity> getAllAdvanceEntitiesForPremisesAndYear(Integer premisesId, Short year) {
        premisesService.checkPremisesExists(premisesId);
        return advanceRepository.findAllByPremises_IdAndYear(premisesId, year);
    }

    public List<AdvanceDto> getAllAdvancesForCooperativeAndYear(Integer cooperativeId, Short year) {
        return getAllAdvanceEntitiesForCooperativeAndYear(cooperativeId, year)
                .stream()
                .map(converterService::advanceEntityToDto)
                .collect(Collectors.toList());
    }

    List<AdvanceEntity> getAllAdvanceEntitiesForCooperativeAndYear(Integer cooperativeId, Short year) {
        return advanceRepository.findAllByCooperativeAndYear(cooperativeId, year);
    }

    public void deleteAdvance(Integer id) {
        advanceRepository.delete(getAdvanceEntity(id));
    }
}
