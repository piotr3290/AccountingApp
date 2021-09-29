package pl.wroblewski.simplyaccounting.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.models.dtos.ContractorDto;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorPaymentResponse {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate realizationDate;

    private Double value;

    private ContractorDto contractor;

    private Integer cooperativeId;
}
