package pl.wroblewski.simplyaccounting.utils;

import org.springframework.stereotype.Component;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.models.common.DatePeriod;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class DateUtilsComponent {

    public boolean arePeriodsOverlappingExclusive(@NotNull LocalDate startDate1, @NotNull LocalDate endDate1,
                                                  @NotNull LocalDate startDate2, @NotNull LocalDate endDate2) {
        return !startDate1.isAfter(endDate2) && !startDate2.isAfter(endDate1);
    }

    public boolean arePeriodsOverlappingInclusive(@NotNull LocalDate startDate1, @NotNull LocalDate endDate1,
                                                  @NotNull LocalDate startDate2, @NotNull LocalDate endDate2) {
        return startDate1.isBefore(endDate2) && startDate2.isBefore(endDate1);
    }

    public boolean areNullablePeriodsOverlapping(LocalDate startDate1, LocalDate endDate1,
                                                 LocalDate startDate2, LocalDate endDate2) {
        return (startDate1 == null || endDate2 == null || !startDate1.isAfter(endDate2))
                && (endDate1 == null || startDate2 == null || !startDate2.isAfter(endDate1));
    }

    public boolean areNullablePeriodsOverlapping(@NotNull IDatePeriod datePeriod1, @NotNull IDatePeriod datePeriod2) {
        return areNullablePeriodsOverlapping(datePeriod1.getStartDate(), datePeriod1.getEndDate(),
                datePeriod2.getStartDate(), datePeriod2.getEndDate());
    }

    public LocalDate getFirstDayOfMonth(int month, int year) {
        return LocalDate.of(year, month, 1);
    }

    public LocalDate getLastDayOfMonth(int month, int year) {
        return YearMonth.of(year, month).atEndOfMonth();
    }

    public double getDaysBetweenInclusiveDates(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DECADES.between(startDate, endDate) + 1;
    }

    public  <T extends IDatePeriod> List<T> filterListInPeriod(Collection<T> datePeriodList,
                                                               LocalDate startDate, LocalDate endDate) {
        return datePeriodList.stream()
                .filter(o -> areNullablePeriodsOverlapping(
                        o.getStartDate(), o.getEndDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    public IDatePeriod findLatestPeriod(Collection<? extends IDatePeriod> datePeriodList) {
        return datePeriodList.stream()
                .max((o1, o2) -> {

                    if (o1.getEndDate() == null) {
                        return 1;
                    }

                    if (o2.getEndDate() == null) {
                        return -1;
                    }

                    return o1.getEndDate().compareTo(o2.getEndDate());
                })
                .orElse(null);
    }

    public IDatePeriod findEarliestPeriod(Collection<? extends IDatePeriod> datePeriodList) {
        return datePeriodList.stream()
                .min((o1, o2) -> {

                    if (o1.getStartDate() == null) {
                        return -1;
                    }

                    if (o2.getStartDate() == null) {
                        return 1;
                    }

                    return o1.getStartDate().compareTo(o2.getStartDate());
                })
                .orElse(null);
    }

    public DatePeriod getOverlappingPeriod(IDatePeriod period1, IDatePeriod period2) {

        return DatePeriod.builder()
                .startDate(getLaterDate(period1.getStartDate(), period2.getStartDate()))
                .endDate(getEarlierDate(period1.getEndDate(), period2.getEndDate()))
                .build();
    }

    public LocalDate getLaterDate(LocalDate date1, LocalDate date2) {

        if (date1 == null) {
            return date2;
        } else if (date2 == null) {
            return date1;
        } else {
            return date1.isBefore(date2) ? date2 : date1;
        }
    }

    public LocalDate getEarlierDate(LocalDate date1, LocalDate date2) {

        if (date1 == null) {
            return date2;
        } else if (date2 == null) {
            return date1;
        } else {
            return date1.isAfter(date2) ? date2 : date1;
        }
    }

    public List<IDatePeriod> getNotOverlappingPeriods(Collection<? extends IDatePeriod> minuendPeriods,
                                                      Collection<? extends IDatePeriod> subtrahendPeriods) {

        var result = new ArrayList<IDatePeriod>(minuendPeriods);

        for (var subtrahendPeriod : subtrahendPeriods) {

            var iterPeriods = new ArrayList<IDatePeriod>();

            for (var minuendPeriod : result) {
                iterPeriods.addAll(getNotOverlappingPeriods(minuendPeriod, subtrahendPeriod));
            }

            result = iterPeriods;
        }

        return result;
    }

    public List<DatePeriod> getNotOverlappingPeriods(IDatePeriod period1, IDatePeriod period2) {

        var result = new ArrayList<DatePeriod>();

        if (areNullablePeriodsOverlapping(period1, period2)) {
            var overlappingPeriod = getOverlappingPeriod(period1, period2);

            if (overlappingPeriod.getStartDate() != null
                    && (period1.getStartDate() == null || period1.getStartDate().isBefore(overlappingPeriod.getStartDate()))) {
                result.add(DatePeriod.builder()
                        .startDate(period1.getStartDate())
                        .endDate(overlappingPeriod.getStartDate().minusDays(1))
                        .build());
            }

            if (overlappingPeriod.getEndDate() != null
                    && (period1.getEndDate() == null || period1.getEndDate().isAfter(overlappingPeriod.getEndDate()))) {
                result.add(DatePeriod.builder()
                        .startDate(overlappingPeriod.getEndDate().plusDays(1))
                        .endDate(period1.getEndDate())
                        .build());
            }

        } else {
            result.add(DatePeriod.builder()
                    .startDate(period1.getStartDate())
                    .endDate(period1.getEndDate())
                    .build());
        }

        return result;
    }

}
