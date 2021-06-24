package pl.wroblewski.simplyaccounting.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatePeriod implements IDatePeriod {

    private LocalDate startDate;
    private LocalDate endDate;

}
