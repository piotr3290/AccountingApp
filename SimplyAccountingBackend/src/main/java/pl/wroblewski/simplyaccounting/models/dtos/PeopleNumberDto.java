package pl.wroblewski.simplyaccounting.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.validation.annotations.ConsistentDates;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConsistentDates(groups = {CreateInfo.class, EditInfo.class})
public class PeopleNumberDto implements IDatePeriod {

    @NotNull(groups = {EditInfo.class})
    @Positive(groups = {EditInfo.class})
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(groups = {CreateInfo.class, EditInfo.class})
    @PositiveOrZero(groups = {CreateInfo.class, EditInfo.class})
    private Integer number;

    @NotNull(groups = {CreateInfo.class})
    @Positive(groups = {CreateInfo.class})
    private Integer premisesId;
}
