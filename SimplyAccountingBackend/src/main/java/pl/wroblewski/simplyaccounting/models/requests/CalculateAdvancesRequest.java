package pl.wroblewski.simplyaccounting.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculateAdvancesRequest {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private Byte month;

    @NotNull
    @Positive
    private Short year;
}
