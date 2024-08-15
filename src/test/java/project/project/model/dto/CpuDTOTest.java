package project.project.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.project.model.enums.CPUType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CpuDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCpuDTO() {
        CpuDTO cpuDTO = new CpuDTO("Intel Core i7", CPUType.I5);
        Set<ConstraintViolation<CpuDTO>> violations = validator.validate(cpuDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCpuDTOName() {
        CpuDTO cpuDTO = new CpuDTO("i7", CPUType.I5);
        Set<ConstraintViolation<CpuDTO>> violations = validator.validate(cpuDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Name must be between 3 and 60", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCpuDTOType() {
        CpuDTO cpuDTO = new CpuDTO("Intel Core i7", null);
        Set<ConstraintViolation<CpuDTO>> violations = validator.validate(cpuDTO);
        assertFalse(violations.isEmpty());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }
}
