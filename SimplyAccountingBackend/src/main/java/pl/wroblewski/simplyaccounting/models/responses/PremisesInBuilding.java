package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PremisesInBuilding {

    private Integer id;

    private String premisesNumber;

    private Integer premisesTypeId;

    private String premisesTypeName;

    private String landlordName;

    private Integer buildingId;
}
