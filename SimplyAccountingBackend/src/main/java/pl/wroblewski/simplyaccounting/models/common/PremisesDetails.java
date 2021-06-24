package pl.wroblewski.simplyaccounting.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.models.dtos.AreaDto;
import pl.wroblewski.simplyaccounting.models.dtos.ConsumptionDto;
import pl.wroblewski.simplyaccounting.models.dtos.PeopleNumberDto;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesLandlordDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PremisesDetails {

    private Integer id;

    private Integer premisesTypeId;

    private List<AreaDto> areas;

    private List<PeopleNumberDto> peopleNumbers;

    private List<PremisesLandlordDto> premisesLandlords;

    private List<ConsumptionDto> consumptions;

    private Integer buildingId;

    private Integer cooperativeId;
}
