package pl.wroblewski.simplyaccounting.validation.annotations;

import pl.wroblewski.simplyaccounting.validation.validators.HierarchyUniqueReferenceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HierarchyUniqueReferenceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HierarchyUniqueReference {

    String message() default "Object can have only one reference";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
