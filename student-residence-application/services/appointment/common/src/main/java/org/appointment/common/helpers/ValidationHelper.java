package org.appointment.common.helpers;

import org.appointment.common.exceptions.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationHelper<T> {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public void validate(T obj)throws ValidationException {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(obj);

        if (violations.isEmpty()) {
            return;
        }

        String errorMsg = violations.stream()
                .map(x -> x.getMessage())
                .collect(Collectors.joining(" "));

        throw new ValidationException(errorMsg);
    }
}
