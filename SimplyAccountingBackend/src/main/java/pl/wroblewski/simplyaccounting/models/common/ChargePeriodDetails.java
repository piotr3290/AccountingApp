package pl.wroblewski.simplyaccounting.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.models.dtos.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChargePeriodDetails implements IDatePeriod {

    private LocalDate startDate;

    private LocalDate endDate;

    private ChargeElementDto chargeElement;

    private PremisesLandlordDto premisesLandlord;

    private RateDto rate;

    private AreaDto area;

    private PeopleNumberDto peopleNumber;

    private ConsumptionDto consumption;

}
