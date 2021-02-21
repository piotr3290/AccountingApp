package pl.wroblewski.simplyaccounting.utils;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Component
public class DateUtilsComponent {

    public boolean arePeriodsOverlapping(@NotNull LocalDate startDate1, @NotNull LocalDate endDate1,
                                         @NotNull LocalDate startDate2, @NotNull LocalDate endDate2) {
        return !startDate1.isAfter(endDate2) && !startDate2.isAfter(endDate1);
    }

    public boolean areNullablePeriodsOverlapping(LocalDate startDate1, LocalDate endDate1,
                                                 @NotNull LocalDate startDate2, @NotNull LocalDate endDate2) {
        return (startDate1 == null || !startDate1.isAfter(endDate2))
                && (endDate1 == null || !startDate2.isAfter(endDate1));
    }
}
