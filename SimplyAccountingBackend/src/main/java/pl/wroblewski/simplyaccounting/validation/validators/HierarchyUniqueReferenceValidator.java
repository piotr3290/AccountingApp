package pl.wroblewski.simplyaccounting.validation.validators;

import lombok.AllArgsConstructor;
import pl.wroblewski.simplyaccounting.interfaces.IHierarchy;
import pl.wroblewski.simplyaccounting.utils.LogicUtilsComponent;
import pl.wroblewski.simplyaccounting.validation.annotations.HierarchyUniqueReference;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class HierarchyUniqueReferenceValidator implements ConstraintValidator<HierarchyUniqueReference, IHierarchy> {

    private final LogicUtilsComponent logicUtilsComponent;

    @Override
    public boolean isValid(IHierarchy object, ConstraintValidatorContext constraintValidatorContext) {

        if (object == null) {
            throw new IllegalArgumentException("@HierarchyUniqueReferenceValidator annotation cannot be applied to null.");
        }

        return logicUtilsComponent.maxOneIsTrue(object.getBuildingId() != null,
                object.getCooperativeId() != null, object.getPremisesId() != null);
    }
}
