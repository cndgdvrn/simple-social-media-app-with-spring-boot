package com.smapp.sm_app.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueValidator.class})
public @interface Unique {
    String message() default "Field value must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();
}
