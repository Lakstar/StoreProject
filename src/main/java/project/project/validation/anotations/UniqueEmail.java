package project.project.validation.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import project.project.validation.validator.UniqueEmailValidator;
import project.project.validation.validator.UniqueUsernameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String value() default "Email must be unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
