package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CooperativeDto {

    @NotNull(groups = {EditInfo.class})
    @Positive(groups = {EditInfo.class})
    private Integer id;

    @NotBlank(groups = {CreateInfo.class, EditInfo.class})
    private String name;

    private String nip;

    private String regon;

    private String city;

    private String street;

    private String houseNumber;

    private String postalCode;

    private Integer accountId;
}
