package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.enums.RateHierarchyLevel;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.models.common.ChargePeriodDetails;
import pl.wroblewski.simplyaccounting.models.common.PremisesDetails;
import pl.wroblewski.simplyaccounting.models.dtos.*;
import pl.wroblewski.simplyaccounting.utils.DateUtilsComponent;
import pl.wroblewski.simplyaccounting.utils.NumberUtilsComponent;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CalculatingAdvanceService {

    private final ChargeElementService chargeElementService;
    private final CooperativeService cooperativeService;
    private final DateUtilsComponent dateUtilsComponent;
    private final NumberUtilsComponent numberUtilsComponent;
    private final PremisesService premisesService;
    private final RateService rateService;
    private final AdvanceService advanceService;
    private final BuildingService buildingService;

    public void calculateAllAdvancesForCooperative(Integer cooperativeId, Byte month, Short year) {

        cooperativeService.checkCooperativeExists(cooperativeId);

        LocalDate startDate = dateUtilsComponent.getFirstDayOfMonth(month, year);
        LocalDate endDate = dateUtilsComponent.getLastDayOfMonth(month, year);

        var allChargeElements =
                chargeElementService.getAllChargeElementsForCooperative(cooperativeId, startDate, endDate);

        var chargeTypeIds = getUniqueChargeTypeIds(allChargeElements);

        var allPremisesDetails =
                premisesService.getAllPremisesDetailsInCooperative(cooperativeId, startDate, endDate);

        var rates = rateService.getAllRateByCooperativeAndChargeTypesAndPeriod(
                chargeTypeIds, cooperativeId, startDate, endDate);

        calculateAdvances(allPremisesDetails, rates, allChargeElements, chargeTypeIds, startDate, endDate, month, year);
    }

    public void calculateAllAdvancesForBuilding(Integer buildingId, Byte month, Short year) {

        buildingService.checkBuildingExists(buildingId);

        LocalDate startDate = dateUtilsComponent.getFirstDayOfMonth(month, year);
        LocalDate endDate = dateUtilsComponent.getLastDayOfMonth(month, year);

        var allChargeElements =
                chargeElementService.getAllChargeElementsForBuilding(buildingId, startDate, endDate);

        var chargeTypeIds = getUniqueChargeTypeIds(allChargeElements);

        var allPremisesDetails =
                premisesService.getAllPremisesDetailsInBuilding(buildingId, startDate, endDate);

        var rates = rateService.getAllRateByBuildingAndChargeTypesAndPeriod(
                chargeTypeIds, buildingId, startDate, endDate);

        calculateAdvances(allPremisesDetails, rates, allChargeElements, chargeTypeIds, startDate, endDate, month, year);
    }

    public void calculateAllAdvancesForPremises(Integer premisesId, Byte month, Short year) {

        premisesService.checkPremisesExists(premisesId);

        LocalDate startDate = dateUtilsComponent.getFirstDayOfMonth(month, year);
        LocalDate endDate = dateUtilsComponent.getLastDayOfMonth(month, year);

        var allChargeElements =
                chargeElementService.getAllChargeElementsForPremises(premisesId, startDate, endDate);

        var chargeTypeIds = getUniqueChargeTypeIds(allChargeElements);

        var allPremisesDetails =
                Collections.singletonList(premisesService.getPremisesDetails(premisesId, startDate, endDate));

        var rates = rateService.getAllRateByPremisesAndChargeTypesAndPeriod(
                chargeTypeIds, premisesId, startDate, endDate);

        calculateAdvances(allPremisesDetails, rates, allChargeElements, chargeTypeIds, startDate, endDate, month, year);
    }

    private void calculateAdvances(Collection<PremisesDetails> allPremisesDetails,
                                   Collection<RateDto> rates,
                                   Collection<ChargeElementDto> allChargeElements,
                                   Collection<Integer> chargeTypeIds,
                                   LocalDate startDate, LocalDate endDate,
                                   Byte month, Short year) {

        var advancesToSave = new ArrayList<AdvanceDto>();

        allPremisesDetails.forEach(premises -> {

            var premisesRates = RateHierarchyLevel.filterMatchingHierarchyForPremises(
                    rates, premises.getCooperativeId(), premises.getBuildingId(), premises.getId());

            var premisesChargeElements =
                    filterChargeElementsByPremisesType(allChargeElements, premises.getPremisesTypeId());

            premises.getPremisesLandlords()
                    .stream()
                    .map(PremisesLandlordDto::getLandlordId)
                    .distinct()
                    .forEach(landlordId -> {

                        var premisesLandlordForLandlordId = premises.getPremisesLandlords()
                                .stream()
                                .filter(o -> o.getLandlordId().equals(landlordId))
                                .collect(Collectors.toList());


                        chargeTypeIds.forEach(chargeTypeId -> {

                            var premisesChargeTypeRates = premisesRates.stream()
                                    .filter(rate -> rate.getChargeTypeId().equals(chargeTypeId))
                                    .collect(Collectors.toList());

                            var chargeElements = premisesChargeElements.stream()
                                    .filter(o -> o.getChargeTypeId().equals(chargeTypeId))
                                    .collect(Collectors.toList());

                            var premisesConsumptions = premises.getConsumptions()
                                    .stream()
                                    .filter(o -> o.getChargeTypeId().equals(chargeTypeId))
                                    .collect(Collectors.toList());

                            var chargePeriods = Collections.singletonList(ChargePeriodDetails.builder()
                                    .startDate(startDate)
                                    .endDate(endDate)
                                    .build());


                            var advanceChargePeriods = getChargePeriodsToAdvance(
                                    chargePeriods,
                                    chargeElements,
                                    premisesLandlordForLandlordId,
                                    premisesChargeTypeRates,
                                    premises.getPeopleNumbers(),
                                    premisesConsumptions,
                                    premises.getAreas());

                            if (!advanceChargePeriods.isEmpty()) {
                                var advance = AdvanceDto.builder()
                                        .amount(numberUtilsComponent.roundDoubleToCurrency(
                                                calculateAdvanceAmount(advanceChargePeriods, month, year)))
                                        .chargeTypeId(chargeTypeId)
                                        .landlordId(landlordId)
                                        .month(month)
                                        .year(year)
                                        .realizationDate(LocalDate.now())
                                        .premisesId(premises.getId())
                                        .build();

                                advancesToSave.add(advance);
                            }
                        });
                    });
        });

        advanceService.saveAdvances(advancesToSave);
    }

    private Collection<ChargePeriodDetails> getChargePeriodsToAdvance(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                      Collection<ChargeElementDto> chargeElements,
                                                                      Collection<PremisesLandlordDto> premisesLandlords,
                                                                      Collection<RateDto> rates,
                                                                      Collection<PeopleNumberDto> peopleNumbers,
                                                                      Collection<ConsumptionDto> consumptions,
                                                                      Collection<AreaDto> areas) {

        var result = getOverlappingPeriods(chargePeriodDetails,
                chargeElements, ChargePeriodDetails::setChargeElement);

        result = getOverlappingPeriods(result, premisesLandlords,
                ChargePeriodDetails::setPremisesLandlord);

        result = getOverlappingPeriods(result,
                getMostDetailedRatesInPeriod(result, rates),
                ChargePeriodDetails::setRate);

        result = getOverlappingPeriodsPeopleNumber(result, peopleNumbers);

        result = getOverlappingPeriodsConsumption(result, consumptions);

        return getOverlappingPeriodsArea(result, areas);
    }

    private Collection<ChargePeriodDetails> getOverlappingPeriodsArea(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                      Collection<AreaDto> areas) {

        var mapPeriodDetails = chargePeriodDetails.stream()
                .collect(Collectors.groupingBy(o -> Optional.ofNullable(o.getChargeElement().getAreaTypeId())));

        List<ChargePeriodDetails> result = new ArrayList<>();

        Optional<Integer> noAreaKey = Optional.empty();

        if (mapPeriodDetails.containsKey(noAreaKey)) {
            result.addAll(mapPeriodDetails.get(noAreaKey));
        }

        mapPeriodDetails.entrySet().stream()
                .filter(o -> o.getKey().isPresent())
                .forEach(o -> result.addAll(getOverlappingPeriods(
                        o.getValue(),
                        areas.stream()
                                .filter(area -> area.getAreaTypeId().equals(o.getKey().get()))
                                .collect(Collectors.toList()),
                        ChargePeriodDetails::setArea)));

        return result;
    }

    private List<Integer> getUniqueChargeTypeIds(Collection<ChargeElementDto> chargeElements) {
        return chargeElements.stream()
                .map(ChargeElementDto::getChargeTypeId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<ChargeElementDto> filterChargeElementsByPremisesType(Collection<ChargeElementDto> chargeElements, Integer premisesTypeId) {
        return chargeElements.stream()
                .filter(o -> o.getPremisesTypeId() == null || o.getPremisesTypeId().equals(premisesTypeId))
                .collect(Collectors.toList());
    }

    private double calculateAdvanceAmount(Collection<ChargePeriodDetails> chargePeriodDetails, Byte month, Short year) {

        DoubleAdder amount = new DoubleAdder();

        chargePeriodDetails.forEach(p -> amount.add(calculateAdvanceAmount(p, month, year)));

        return amount.sum();
    }

    private double calculateAdvanceAmount(ChargePeriodDetails chargePeriodDetails, Byte month, Short year) {

        double amount = 1;

        amount *= chargePeriodDetails.getRate().getValue();

        if (chargePeriodDetails.getChargeElement().getMultiplyPeopleNumber()) {
            amount *= chargePeriodDetails.getPeopleNumber().getNumber();
        }

        if (chargePeriodDetails.getChargeElement().getMultiplyConsumption()) {
            amount *= chargePeriodDetails.getConsumption().getValue();
        }

        if (chargePeriodDetails.getChargeElement().getAreaTypeId() != null) {
            amount *= chargePeriodDetails.getArea().getValue();
        }

        var periodDays = dateUtilsComponent.getDaysBetweenInclusiveDates(
                chargePeriodDetails.getStartDate(), chargePeriodDetails.getEndDate());

        var monthDays = YearMonth.of(year, month).lengthOfMonth();


        return amount * periodDays / monthDays;
    }

    private Collection<ChargePeriodDetails> getOverlappingPeriodsPeopleNumber(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                              Collection<PeopleNumberDto> peopleNumbers) {
        return getOptionalOverlappingPeriods(chargePeriodDetails, peopleNumbers,
                ChargeElementDto::getMultiplyPeopleNumber, ChargePeriodDetails::setPeopleNumber);
    }

    private Collection<ChargePeriodDetails> getOverlappingPeriodsConsumption(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                             Collection<ConsumptionDto> consumptions) {
        return getOptionalOverlappingPeriods(chargePeriodDetails, consumptions,
                ChargeElementDto::getMultiplyConsumption, ChargePeriodDetails::setConsumption);
    }

    private <T extends IDatePeriod> Collection<ChargePeriodDetails> getOptionalOverlappingPeriods(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                                                  Collection<T> datePeriods, Function<ChargeElementDto, Boolean> f,
                                                                                                  BiConsumer<ChargePeriodDetails, T> consumer) {
        var optionalChargePeriodMap = chargePeriodDetails.stream()
                .collect(Collectors.partitioningBy(o -> f.apply(o.getChargeElement())));

        var overlappingPeriods = getOverlappingPeriods(optionalChargePeriodMap.get(true), datePeriods, consumer);

        return Stream.of(optionalChargePeriodMap.get(false), overlappingPeriods)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private <T extends IDatePeriod> Collection<ChargePeriodDetails> getOverlappingPeriods(Collection<ChargePeriodDetails> chargePeriodDetails,
                                                                                          Collection<T> datePeriods, BiConsumer<ChargePeriodDetails, T> consumer) {
        return chargePeriodDetails.stream()
                .flatMap(o -> datePeriods.stream()
                        .filter(p -> dateUtilsComponent.areNullablePeriodsOverlapping(o, p))
                        .map(p -> {

                            var datePeriod = dateUtilsComponent.getOverlappingPeriod(o, p);

                            var chargePeriodDetailsItem = o.toBuilder()
                                    .startDate(datePeriod.getStartDate())
                                    .endDate(datePeriod.getEndDate())
                                    .build();

                            consumer.accept(chargePeriodDetailsItem, p);

                            return chargePeriodDetailsItem;
                        }))
                .collect(Collectors.toList());
    }

    private Collection<RateDto> getMostDetailedRatesInPeriod(Collection<? extends IDatePeriod> periods,
                                                             Collection<RateDto> rates) {

        List<IDatePeriod> missingPeriods = new ArrayList<>(periods);

        var premisesRates = getPremisesRatesInPeriods(rates, missingPeriods);

        missingPeriods = dateUtilsComponent.getNotOverlappingPeriods(missingPeriods, premisesRates);

        var buildingRates = getBuildingRatesInPeriods(rates, missingPeriods);

        missingPeriods = dateUtilsComponent.getNotOverlappingPeriods(missingPeriods, buildingRates);

        var cooperativeRates = getCooperativeRatesInPeriods(rates, missingPeriods);

        missingPeriods = dateUtilsComponent.getNotOverlappingPeriods(missingPeriods, cooperativeRates);

        var generalRates = getGeneralRatesInPeriods(rates, missingPeriods);

        return Stream.of(premisesRates, buildingRates, cooperativeRates, generalRates)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }

    private List<RateDto> getPremisesRatesInPeriods(Collection<RateDto> rates,
                                                    Collection<? extends IDatePeriod> periods) {
        return getRatesInPeriods(rates, periods, RateHierarchyLevel.PREMISES);
    }

    private List<RateDto> getBuildingRatesInPeriods(Collection<RateDto> rates,
                                                    Collection<? extends IDatePeriod> periods) {
        return getRatesInPeriods(rates, periods, RateHierarchyLevel.BUILDING);
    }

    private List<RateDto> getCooperativeRatesInPeriods(Collection<RateDto> rates,
                                                       Collection<? extends IDatePeriod> periods) {
        return getRatesInPeriods(rates, periods, RateHierarchyLevel.COOPERATIVE);
    }

    private List<RateDto> getGeneralRatesInPeriods(Collection<RateDto> rates,
                                                   Collection<? extends IDatePeriod> periods) {
        return getRatesInPeriods(rates, periods, RateHierarchyLevel.GENERAL);
    }

    private List<RateDto> getRatesInPeriods(Collection<RateDto> rates,
                                            Collection<? extends IDatePeriod> periods,
                                            RateHierarchyLevel hierarchyLevel) {
        return rates.stream()
                .filter(rate -> rate.getHierarchyLevel() == hierarchyLevel)
                .flatMap(rate -> periods.stream()
                        .map(period -> {
                            var datePeriod = dateUtilsComponent.getOverlappingPeriod(rate, period);
                            return rate.toBuilder()
                                    .startDate(datePeriod.getStartDate())
                                    .endDate(datePeriod.getEndDate())
                                    .build();
                        })
                )
                .collect(Collectors.toList());
    }

}
