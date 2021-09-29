package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.ConsumptionEntity;
import pl.wroblewski.simplyaccounting.db.repositories.ConsumptionRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.ConsumptionDto;
import pl.wroblewski.simplyaccounting.models.responses.ConsumptionResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConverterService converterService;
    private final ChargeTypeService chargeTypeService;
    private final PremisesService premisesService;
    private final DatePeriodService datePeriodService;

    public ConsumptionDto getConsumption(int id) {
        return converterService.consumptionEntityToDto(getConsumptionEntity(id));
    }

    private ConsumptionEntity getConsumptionEntity(int id) {
        return consumptionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the consumption."));
    }

    public List<ConsumptionResponse> createConsumption(ConsumptionDto consumption) {
        premisesService.checkPremisesExists(consumption.getPremisesId());

        var consumptionEntity = converterService.consumptionDtoToEntity(consumption);
        consumptionEntity.setChargeType(chargeTypeService.getChargeTypeEntity(consumption.getChargeTypeId()));

        saveConsumption(getAllConsumptionEntities(consumption.getPremisesId(), consumption.getChargeTypeId()), consumptionEntity);

        return getAllConsumptionResponse(consumption.getPremisesId());
    }

    public List<ConsumptionResponse> editConsumption(ConsumptionDto consumption) {
        var consumptionEntity = getConsumptionEntity(consumption.getId())
                .toBuilder()
                .value(consumption.getValue())
                .startDate(consumption.getStartDate())
                .endDate(consumption.getEndDate())
                .build();

        var consumptionEntities = getAllConsumptionEntities(consumption.getPremisesId(), consumption.getChargeTypeId());
        consumptionEntities.remove(consumptionEntity);

        saveConsumption(consumptionEntities, consumptionEntity);

        return getAllConsumptionResponse(consumption.getPremisesId());
    }

    private void saveConsumption(List<ConsumptionEntity> consumptionEntities, ConsumptionEntity newConsumption) {
        datePeriodService.save(consumptionEntities, newConsumption, consumptionRepository);
    }

    private List<ConsumptionEntity> getAllConsumptionEntities(Integer premisesId, Integer chargeTypeId) {
        return consumptionRepository.findAllByPremises_IdAndChargeType_IdOrderByStartDate(premisesId, chargeTypeId);
    }

    private List<ConsumptionEntity> getAllConsumptionEntities(Integer premisesId) {
        return consumptionRepository.findAllByPremises_IdOrderByStartDate(premisesId);
    }

    public List<ConsumptionResponse> getAllConsumptionResponse(Integer premisesId) {
        return getAllConsumptionEntities(premisesId)
                .stream()
                .map(converterService::consumptionEntityToResponse)
                .collect(Collectors.toList());
    }

    public void deleteConsumption(Integer id) {
        consumptionRepository.delete(getConsumptionEntity(id));
    }
}
