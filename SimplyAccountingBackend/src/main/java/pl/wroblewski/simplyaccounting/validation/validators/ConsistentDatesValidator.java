package pl.wroblewski.simplyaccounting.validation.validators;

import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.validation.annotations.ConsistentDates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDatesValidator implements ConstraintValidator<ConsistentDates, IDatePeriod> {

    @Override
    public boolean isValid(IDatePeriod object, ConstraintValidatorContext constraintValidatorContext) {

        if (object == null) {
            throw new IllegalArgumentException("@ConsistentDates annotation cannot be applied to null.");
        }

        if (object.getStartDate() == null || object.getEndDate() == null) {
            return true;
        }
        return !object.getStartDate().isAfter(object.getEndDate());
    }
}
