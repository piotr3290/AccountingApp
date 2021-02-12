package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.CreateAccountInfo;
import pl.wroblewski.simplyaccounting.validation.OtherInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTypeDto {

    @Positive(groups = {CreateAccountInfo.class})
    private int id;

    @NotBlank(groups = {OtherInfo.class})
    private String name;

    @Positive(groups = {OtherInfo.class})
    private int number;
}
