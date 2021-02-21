package pl.wroblewski.simplyaccounting.models.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.wroblewski.simplyaccounting.validation.annotations.ConsistentDates;
import pl.wroblewski.simplyaccounting.validation.groups.CreateInfo;
import pl.wroblewski.simplyaccounting.validation.groups.EditInfo;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ConsistentDates(groups = {CreateInfo.class, EditInfo.class})
public class AreaDto extends DatesDto{

    @Positive(groups = {EditInfo.class})
    private int id;

    @PositiveOrZero(groups = {CreateInfo.class, EditInfo.class})
    private double value;

    @Positive(groups = {CreateInfo.class})
    private int premisesId;
}
