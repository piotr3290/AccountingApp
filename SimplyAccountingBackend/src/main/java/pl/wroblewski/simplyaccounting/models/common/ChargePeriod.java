package pl.wroblewski.simplyaccounting.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.interfaces.IChargePeriod;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargePeriod implements IChargePeriod {

    private LocalDate startDate;

    private LocalDate endDate;

    private Double amount;
}
