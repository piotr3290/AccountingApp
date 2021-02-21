package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateCooperativeInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditCooperativeInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CooperativeDto {

    @Positive(groups = {EditCooperativeInfo.class})
    private int id;

    @NotBlank(groups = {CreateCooperativeInfo.class, EditCooperativeInfo.class})
    private String name;

    private String nip;

    private String regon;

    private String city;

    private String street;

    private String houseNumber;

    private String postalCode;

    private AccountDto account;
}
