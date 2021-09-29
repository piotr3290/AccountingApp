package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.models.dtos.BuildingDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CooperativeResponse {
    private Integer id;
    private String name;
    private Integer accountId;
    private List<BuildingDto> buildings;
    private String address;
    private String nip;
    private String regon;
}
