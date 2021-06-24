package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateBuildingInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditBuildingInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDto {

    @NotNull(groups = {EditBuildingInfo.class})
    @Positive(groups = {EditBuildingInfo.class})
    private Integer id;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String street;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String houseNumber;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String postalCode;

    @NotBlank(groups = {EditBuildingInfo.class, CreateBuildingInfo.class})
    private String city;

    @NotNull(groups = {CreateBuildingInfo.class})
    @Positive(groups = {CreateBuildingInfo.class})
    private Integer cooperativeId;

    private String cooperativeName;
}
