package project.project.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreateMonitorDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCreateMonitorDTO() {
        CreateMonitorDTO monitorDTO = new CreateMonitorDTO("Dell", 24, "High resolution monitor");
        Set<ConstraintViolation<CreateMonitorDTO>> violations = validator.validate(monitorDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCreateMonitorDTOName() {
        CreateMonitorDTO monitorDTO = new CreateMonitorDTO("DE", 24, "High resolution monitor");
        Set<ConstraintViolation<CreateMonitorDTO>> violations = validator.validate(monitorDTO);
        assertFalse(violations.isEmpty());
        assertEquals("size must be between 3 and 60", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCreateMonitorDTOInches() {
        CreateMonitorDTO monitorDTO = new CreateMonitorDTO("Dell", 0, "High resolution monitor");
        Set<ConstraintViolation<CreateMonitorDTO>> violations = validator.validate(monitorDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Must be a positive number", violations.iterator().next().getMessage());
    }
}
