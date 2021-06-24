package pl.wroblewski.simplyaccounting.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.enums.RateHierarchyLevel;
import pl.wroblewski.simplyaccounting.models.dtos.BuildingDto;
import pl.wroblewski.simplyaccounting.models.dtos.ChargeTypeDto;
import pl.wroblewski.simplyaccounting.models.dtos.CooperativeDto;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponse {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Double value;

    private CooperativeDto cooperative;

    private BuildingDto building;

    private PremisesInfo premises;

    private ChargeTypeDto chargeType;

    private RateHierarchyLevel level;
}
