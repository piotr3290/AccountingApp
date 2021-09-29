package pl.wroblewski.simplyaccounting.validation.annotations;

import pl.wroblewski.simplyaccounting.validation.validators.ConsistentDatesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsistentDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentDates {

    String message() default "Begin date must be before end date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
