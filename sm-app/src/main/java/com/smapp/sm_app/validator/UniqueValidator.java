package com.smapp.sm_app.validator;

import com.smapp.sm_app.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<Unique,String> {

    String fieldName;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if(value == null || value.isEmpty()){
            return true;
        }

        boolean exists = false;

        if(fieldName.equals("email")){
            exists = userRepository.existsByEmail(value);
        } else if (fieldName.equals("username")) {
            exists = userRepository.existsByUsername(value);
        }

        if(exists){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(this.fieldName + " must be unique")
                    .addConstraintViolation();
            return false;
        }

        return true;

    }
}
