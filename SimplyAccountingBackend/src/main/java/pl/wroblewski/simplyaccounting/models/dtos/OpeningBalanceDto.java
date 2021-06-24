package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningBalanceDto {

    @NotNull(groups = {EditInfo.class})
    @Positive(groups = {EditInfo.class})
    private Integer id;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Short year;

    @NotNull(groups = {CreateInfo.class, EditInfo.class})
    private Double balance;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Integer accountId;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Integer cooperativeId;
}
