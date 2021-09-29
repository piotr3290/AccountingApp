package pl.wroblewski.simplyaccounting.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    private Integer id;

    private Double value;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate realizationDate;

    private String contractorName;

    private String invoiceTypeName;

    private Integer invoiceTypeNumber;
}
