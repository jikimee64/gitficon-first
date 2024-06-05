package order.system.core.domain.common;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {

    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validateSelf(T instance) {
        Set<ConstraintViolation<T>> violations = validator.validate(instance);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
