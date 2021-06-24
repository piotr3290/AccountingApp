package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.ChargeElementEntity;
import pl.wroblewski.simplyaccounting.db.repositories.ChargeElementRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeElementDto;
import pl.wroblewski.simplyaccounting.models.responses.ChargeElementResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChargeElementService {

    private final ChargeElementRepository chargeElementRepository;
    private final ConverterService converterService;
    private final DatePeriodService datePeriodService;
    private final CooperativeService cooperativeService;
    private final ChargeTypeService chargeTypeService;
    private final PremisesTypeService premisesTypeService;
    private final AreaTypeService areaTypeService;

    public ChargeElementDto getChargeElement(Integer id) {
        return converterService.chargeElementEntityToDto(getChargeElementEntity(id));
    }

    private ChargeElementEntity getChargeElementEntity(Integer id) {
        return chargeElementRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the charge element."));
    }

    public List<ChargeElementDto> getAllChargeElementsForCooperative(Integer cooperativeId, LocalDate startDate, LocalDate endDate) {
        return chargeElementRepository.findAllByCooperative_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
                cooperativeId, endDate, startDate)
                .stream()
                .map(converterService::chargeElementEntityToDto)
                .collect(Collectors.toList());
    }

    public List<ChargeElementDto> getAllChargeElementsForBuilding(Integer buildingId, LocalDate startDate, LocalDate endDate) {
        return chargeElementRepository.findAllByBuilding_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
                buildingId, endDate, startDate)
                .stream()
                .map(converterService::chargeElementEntityToDto)
                .collect(Collectors.toList());
    }

    public List<ChargeElementDto> getAllChargeElementsForPremises(Integer premisesId, LocalDate startDate, LocalDate endDate) {
        return chargeElementRepository.findAllByPremises_IdAndStartDateLessThanEqualAndEndDateGreaterThan(
                premisesId, endDate, startDate)
                .stream()
                .map(converterService::chargeElementEntityToDto)
                .collect(Collectors.toList());
    }

    public ChargeElementDto createChargeElement(ChargeElementDto chargeElement) {

        checkReferencesCorrectness(chargeElement);

        var chargeElements = getAllChargeElementsForTypeAndCooperativeAndPremisesType(
                chargeElement.getChargeTypeId(), chargeElement.getCooperativeId(), chargeElement.getPremisesTypeId());

        var newChargeElement = converterService.chargeElementDtoToEntity(chargeElement);

        saveChargeElement(chargeElements, newChargeElement);

        return converterService.chargeElementEntityToDto(newChargeElement);
    }

    public ChargeElementDto editChargeElement(ChargeElementDto chargeElement) {
        var chargeElementEntity = getChargeElementEntity(chargeElement.getId())
                .toBuilder()
                .startDate(chargeElement.getStartDate())
                .endDate(chargeElement.getEndDate())
                .multiplyConsumption(chargeElement.getMultiplyConsumption())
                .multiplyPeopleNumber(chargeElement.getMultiplyPeopleNumber())
                .areaType(chargeElement.getAreaTypeId() != null
                        ? areaTypeService.getAreaTypeEntity(chargeElement.getAreaTypeId())
                        : null)
                .build();

        var chargeElements = getAllChargeElementsForTypeAndCooperativeAndPremisesType(
                chargeElement.getChargeTypeId(), chargeElement.getCooperativeId(), chargeElement.getPremisesTypeId());

        chargeElements.remove(chargeElementEntity);

        saveChargeElement(chargeElements, chargeElementEntity);

        return converterService.chargeElementEntityToDto(chargeElementEntity);
    }

    private void saveChargeElement(List<ChargeElementEntity> chargeElements, ChargeElementEntity newChargeElement) {
        datePeriodService.save(chargeElements, newChargeElement, chargeElementRepository);
    }

    private List<ChargeElementEntity> getAllChargeElementsForTypeAndCooperativeAndPremisesType(
            Integer chargeTypeId, Integer cooperativeId, Integer premisesTypeId) {
        return chargeElementRepository.findAllByChargeType_IdAndCooperative_IdAndPremisesType_Id(
                chargeTypeId, cooperativeId, premisesTypeId);
    }

    private void checkReferencesCorrectness(ChargeElementDto chargeElement) {
        cooperativeService.checkCooperativeExists(chargeElement.getCooperativeId());
        chargeTypeService.checkChargeTypeExists(chargeElement.getChargeTypeId());

        if (chargeElement.getPremisesTypeId() != null) {
            premisesTypeService.checkPremisesTypeExists(chargeElement.getPremisesTypeId());
        }

        if (chargeElement.getAreaTypeId() != null) {
            areaTypeService.checkAreaTypeExists(chargeElement.getAreaTypeId());
        }
    }

    public void deleteChargeElement(Integer id) {
        chargeElementRepository.delete(getChargeElementEntity(id));
    }

    public List<ChargeElementResponse> getAllChargeElementsForCooperative(Integer cooperativeId) {
        return chargeElementRepository.findAllByCooperative_Id(cooperativeId)
                .stream()
                .map(converterService::chargeElementEntityToResponse)
                .collect(Collectors.toList());
    }
}
