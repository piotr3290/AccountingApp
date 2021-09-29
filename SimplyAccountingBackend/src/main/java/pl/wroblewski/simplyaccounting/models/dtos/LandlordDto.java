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
public class LandlordDto {

    @NotNull(groups = {EditInfo.class})
    @Positive(groups = {EditInfo.class})
    private Integer id;

    @NotBlank(groups = {EditInfo.class, CreateInfo.class})
    private String firstName;

    private String middleName;

    @NotBlank(groups = {EditInfo.class, CreateInfo.class})
    private String lastName;

    private String city;

    private String street;

    private String houseNumber;

    private String premisesNumber;

    private String postalCode;

    private String phoneNumber;

    private String emailAddress;

    private Integer accountId;
}
