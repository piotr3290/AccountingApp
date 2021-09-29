package pl.wroblewski.simplyaccounting.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeTypeDto;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvancePremisesResponse {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate realizationDate = LocalDate.now();

    private Double amount;

    private Byte month;

    private Short year;

    private ChargeTypeDto chargeType;

    private Integer landlordId;

    private String landlordFullName;

    private Integer premisesId;
}
