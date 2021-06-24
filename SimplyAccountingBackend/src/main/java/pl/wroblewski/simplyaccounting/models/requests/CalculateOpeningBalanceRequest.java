package pl.wroblewski.simplyaccounting.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculateOpeningBalanceRequest {

    @NotNull
    @Positive
    private Integer cooperativeId;

    @NotNull
    @Positive
    private Short year;

}
