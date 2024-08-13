package project.project.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.project.service.impl.UserServiceImpl;
import project.project.validation.anotations.UniqueUsername;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

    private final UserServiceImpl userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username==null || username.isEmpty()){
            return true;
        }

        return userService.isUsernameUnique(username);
    }
}
