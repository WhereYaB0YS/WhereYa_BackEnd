package com.where.WhereYouAt.validator;

import com.where.WhereYouAt.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender,String> {

    private Gender annotation;

    @Override
    public void initialize(Gender constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if(enumValues != null){
            for(Object enumValue : enumValues){
                if(value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
