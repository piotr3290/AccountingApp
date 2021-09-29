package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.RateEntity;
import pl.wroblewski.simplyaccounting.db.repositories.RateRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.RateDto;
import pl.wroblewski.simplyaccounting.models.responses.RateResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final ConverterService converterService;
    private final DatePeriodService datePeriodService;
    private final ChargeTypeService chargeTypeService;
    private final CooperativeService cooperativeService;
    private final BuildingService buildingService;
    private final PremisesService premisesService;

    public RateDto getRate(int id) {
        return converterService.rateEntityToDto(getRateEntity(id));
    }

    private RateEntity getRateEntity(int id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the rate"));
    }

    private List<RateEntity> getAllRateEntities(Integer chargeTypeId, Integer cooperativeId, Integer buildingId, Integer premisesId) {
        return rateRepository.findAllByChargeType_IdAndCooperative_IdAndBuilding_IdAndPremises_Id(
                chargeTypeId, cooperativeId, buildingId, premisesId);
    }

    public RateDto createRate(RateDto rate) {
        chargeTypeService.checkChargeTypeExists(rate.getChargeTypeId());

        if (rate.getCooperativeId() != null) {
            cooperativeService.checkCooperativeExists(rate.getCooperativeId());
        }

        if (rate.getBuildingId() != null) {
            buildingService.checkBuildingExists(rate.getBuildingId());
        }

        if (rate.getPremisesId() != null) {
            premisesService.checkPremisesExists(rate.getPremisesId());
        }

        var newRate = converterService.rateDtoToEntity(rate);

        saveRate(getAllRateEntities(rate.getChargeTypeId(), rate.getCooperativeId(), rate.getBuildingId(), rate.getPremisesId()), newRate);
        return converterService.rateEntityToDto(newRate);
    }

    private void saveRate(List<RateEntity> rateEntities, RateEntity newRate) {
        datePeriodService.save(rateEntities, newRate, rateRepository);
    }

    public RateDto editRate(RateDto rate) {
        var rateEntity = getRateEntity(rate.getId())
                .toBuilder()
                .value(rate.getValue())
                .startDate(rate.getStartDate())
                .endDate(rate.getEndDate())
                .build();

        var rateEntities = getAllRateEntities(rate.getChargeTypeId(), rate.getCooperativeId(), rate.getBuildingId(), rate.getPremisesId());
        rateEntities.remove(rateEntity);

        saveRate(rateEntities, rateEntity);

        return converterService.rateEntityToDto(rateEntity);
    }

    public List<RateDto> getAllRateByCooperativeAndChargeTypesAndPeriod(
            List<Integer> chargeTypeIds, Integer cooperativeId, LocalDate startDate, LocalDate endDate) {

        return rateRepository.findAllByChargeTypesAndCooperativeAndPeriod(
                cooperativeId, startDate, endDate, chargeTypeIds)
                .stream()
                .map(converterService::rateEntityToDto)
                .collect(Collectors.toList());
    }

    public List<RateDto> getAllRateByBuildingAndChargeTypesAndPeriod(
            List<Integer> chargeTypeIds, Integer buildingId, LocalDate startDate, LocalDate endDate) {

        return rateRepository.findAllByChargeTypesAndBuildingAndPeriod(
                buildingId, startDate, endDate, chargeTypeIds)
                .stream()
                .map(converterService::rateEntityToDto)
                .collect(Collectors.toList());
    }

    public List<RateDto> getAllRateByPremisesAndChargeTypesAndPeriod(
            List<Integer> chargeTypeIds, Integer premisesId, LocalDate startDate, LocalDate endDate) {

        return rateRepository.findAllByChargeTypesAndPremisesAndPeriod(
                premisesId, startDate, endDate, chargeTypeIds)
                .stream()
                .map(converterService::rateEntityToDto)
                .collect(Collectors.toList());
    }

    public List<RateResponse> getAllRates() {
        return rateRepository.findAll()
                .stream()
                .map(converterService::rateEntityToResponse)
                .collect(Collectors.toList());
    }

    public void deleteRate(Integer id) {
        var rateEntity = getRateEntity(id);
        rateRepository.delete(rateEntity);
    }
}
