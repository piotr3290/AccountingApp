package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.models.dtos.PeopleNumberDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremisesResponse {

    private Integer id;

    private String premisesNumber;

    private String premisesTypeName;

    private Integer buildingId;

    private String buildingAddress;

    private String cooperativeName;

    private List<AreaResponse> areas;

    private List<PeopleNumberDto> peopleNumbers;

    private List<PremisesLandlordResponse> premisesLandlords;

    private List<ConsumptionResponse> consumptions;
}
