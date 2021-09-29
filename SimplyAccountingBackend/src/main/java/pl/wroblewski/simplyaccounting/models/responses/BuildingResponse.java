package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponse {

    private Integer id;

    private String address;

    private Integer cooperativeId;

    private String cooperativeName;

    private List<PremisesInBuilding> premises;
}
