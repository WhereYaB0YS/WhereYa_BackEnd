package com.where.WhereYouAt.annotation;

import com.where.WhereYouAt.validator.GenderValidator;
import com.where.WhereYouAt.validator.YearMonthDayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {GenderValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Gender {
    String message() default "Gender 형식에 맞지 않습니다";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends  java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;
}
