package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.CreateBuildingInfo;
import pl.wroblewski.simplyaccounting.validation.EditBuildingInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDto {

    @Positive(groups = {EditBuildingInfo.class})
    private int id;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String street;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String houseNumber;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String postalCode;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String city;

    @Positive(groups = {CreateBuildingInfo.class})
    private int cooperativeId;

    private String cooperativeName;
}
