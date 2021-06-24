package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PremisesEntity;
import pl.wroblewski.simplyaccounting.db.entities.PremisesTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PremisesRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.common.PremisesDetails;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesDto;
import pl.wroblewski.simplyaccounting.models.responses.PremisesInfo;
import pl.wroblewski.simplyaccounting.models.responses.PremisesResponse;
import pl.wroblewski.simplyaccounting.utils.DateUtilsComponent;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PremisesService {

    private final PremisesRepository premisesRepository;
    private final ConverterService converterService;
    private final PremisesTypeService premisesTypeService;
    private final BuildingService buildingService;
    private final DateUtilsComponent dateUtilsComponent;

    public PremisesDto getPremises(int id) {
        return converterService.premisesEntityToDto(getPremisesEntity(id));
    }

    PremisesEntity getPremisesEntity(int id) {
        return premisesRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find premises."));
    }

    public PremisesDto createPremises(PremisesDto premises) {
        premisesTypeService.checkPremisesTypeExists(premises.getPremisesTypeId());
        buildingService.checkBuildingExists(premises.getBuildingId());
        return converterService.premisesEntityToDto(premisesRepository.save(converterService.premisesDtoToEntity(premises)));
    }

    public PremisesResponse getPremisesResponse(int id) {
        return converterService.premisesEntityToResponse(getPremisesEntity(id));
    }

    public PremisesDto updatePremises(PremisesDto premises) {
        premisesTypeService.checkPremisesTypeExists(premises.getPremisesTypeId());
        return converterService.premisesEntityToDto(premisesRepository.save(
                getPremisesEntity(premises.getId())
                        .toBuilder()
                        .premisesNumber(premises.getPremisesNumber())
                        .premisesType(PremisesTypeEntity.builder().id(premises.getPremisesTypeId()).build())
                        .build()));
    }

    public void checkPremisesExists(int premisesId) {
        getPremisesEntity(premisesId);
    }

    public List<PremisesDetails> getAllPremisesDetailsInCooperative(Integer cooperativeId) {
        return premisesRepository.findAllByBuilding_Cooperative_Id(cooperativeId)
                .stream()
                .map(converterService::premisesEntityToDetails)
                .collect(Collectors.toList());
    }

    public List<PremisesDetails> getAllPremisesDetailsInBuilding(Integer buildingId) {
        return premisesRepository.findAllByBuilding_Id(buildingId)
                .stream()
                .map(converterService::premisesEntityToDetails)
                .collect(Collectors.toList());
    }

    public List<PremisesDetails> getAllPremisesDetailsInCooperative(Integer cooperativeId,
                                                                    LocalDate startDate, LocalDate endDate) {
        return getAllPremisesDetailsInCooperative(cooperativeId)
                .stream()
                .map(o -> filterPremisesCollectionsInDates(o, startDate, endDate))
                .collect(Collectors.toList());
    }

    public List<PremisesDetails> getAllPremisesDetailsInBuilding(Integer buildingId,
                                                                 LocalDate startDate, LocalDate endDate) {
        return getAllPremisesDetailsInBuilding(buildingId)
                .stream()
                .map(o -> filterPremisesCollectionsInDates(o, startDate, endDate))
                .collect(Collectors.toList());
    }

    public PremisesDetails getPremisesDetails(Integer premisesId, LocalDate startDate, LocalDate endDate) {
        return filterPremisesCollectionsInDates(
                converterService.premisesEntityToDetails(getPremisesEntity(premisesId)),
                startDate, endDate);
    }

    private PremisesDetails filterPremisesCollectionsInDates(PremisesDetails premisesDetails, LocalDate startDate,
                                                             LocalDate endDate) {
        return premisesDetails.toBuilder()
                .areas(dateUtilsComponent.filterListInPeriod(
                        premisesDetails.getAreas(), startDate, endDate))
                .consumptions(dateUtilsComponent.filterListInPeriod(
                        premisesDetails.getConsumptions(), startDate, endDate))
                .peopleNumbers(dateUtilsComponent.filterListInPeriod(
                        premisesDetails.getPeopleNumbers(), startDate, endDate))
                .premisesLandlords(dateUtilsComponent.filterListInPeriod(
                        premisesDetails.getPremisesLandlords(), startDate, endDate))
                .build();
    }

    public List<PremisesDto> getAllPremisesForBuilding(Integer id) {
        return premisesRepository.findAllByBuilding_IdOrderByPremisesNumber(id)
                .stream()
                .map(converterService::premisesEntityToDto)
                .collect(Collectors.toList());
    }

    public PremisesInfo getPremisesInfo(Integer id) {
        return converterService.premisesEntityToInfo(getPremisesEntity(id));
    }
}
