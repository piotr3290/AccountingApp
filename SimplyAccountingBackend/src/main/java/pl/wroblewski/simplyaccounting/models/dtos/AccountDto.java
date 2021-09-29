package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateAccountInfo;
import pl.wroblewski.simplyaccounting.validation.groups.OtherInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    @Positive(groups = {OtherInfo.class})
    private Integer id;

    @Valid
    @NotNull(groups = {CreateAccountInfo.class})
    private AccountTypeDto accountType;

    private Integer cooperativeId;
}
