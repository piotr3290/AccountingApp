package pl.wroblewski.simplyaccounting.validation.validators;

import pl.wroblewski.simplyaccounting.models.dtos.DatesDto;
import pl.wroblewski.simplyaccounting.validation.annotations.ConsistentDates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDatesValidator implements ConstraintValidator<ConsistentDates, DatesDto> {

    @Override
    public boolean isValid(DatesDto object, ConstraintValidatorContext constraintValidatorContext) {

        if(!(object instanceof DatesDto)){
            throw new IllegalArgumentException("@ConsistentDates annotation can only be applied to DatesDto class.");
        }

        if (object.getStartDate() == null || object.getEndDate() == null) {
            return true;
        }
        return object.getStartDate().isBefore(object.getEndDate());
    }
}
