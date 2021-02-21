package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremisesDto {

    @Positive(groups = {EditInfo.class})
    private int id;

    @NotBlank(groups = {CreateInfo.class, EditInfo.class})
    private String premisesNumber;

    @Positive(groups = {CreateInfo.class, EditInfo.class})
    private int premisesTypeId;

    @Positive(groups = {CreateInfo.class})
    private int buildingId;

    private String buildingAddress;
}
