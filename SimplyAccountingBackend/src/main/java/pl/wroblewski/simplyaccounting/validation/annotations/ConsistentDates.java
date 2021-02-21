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
    String message() default "End date must be after begin date and both must be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
