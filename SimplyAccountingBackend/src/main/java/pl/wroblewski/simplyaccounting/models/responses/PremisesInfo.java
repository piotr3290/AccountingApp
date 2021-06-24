package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PremisesInfo {

    private Integer id;

    private String premisesNumber;

    private String premisesTypeName;

    private Integer buildingId;

    private String buildingStreet;

    private String buildingHouseNumber;

    private String cooperativeName;

}
