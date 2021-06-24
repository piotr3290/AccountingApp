package pl.wroblewski.simplyaccounting.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    @NotNull(groups = {EditInfo.class})
    @Positive(groups = {EditInfo.class})
    private Integer id;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Integer cooperativeId;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Integer contractorId;

    @NotNull(groups = {CreateInfo.class, EditInfo.class})
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate realizationDate;

    @NotNull(groups = {CreateInfo.class, EditInfo.class})
    private Double value;

    @NotNull(groups = {CreateInfo.class, EditInfo.class})
    @Positive(groups = {CreateInfo.class, EditInfo.class})
    private Integer invoiceTypeId;
}
