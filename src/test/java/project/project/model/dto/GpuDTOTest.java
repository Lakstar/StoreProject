package project.project.model.dto;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GpuDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidGpuDTO() {
        GpuDTO gpuDTO = new GpuDTO("NVIDIA RTX", 8);
        Set<ConstraintViolation<GpuDTO>> violations = validator.validate(gpuDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidGpuDTOName() {
        GpuDTO gpuDTO = new GpuDTO("GT", 8);
        Set<ConstraintViolation<GpuDTO>> violations = validator.validate(gpuDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Name must be between 3 and 60", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidGpuDTORam() {
        GpuDTO gpuDTO = new GpuDTO("NVIDIA RTX", 0);
        Set<ConstraintViolation<GpuDTO>> violations = validator.validate(gpuDTO);
        assertFalse(violations.isEmpty());
        assertEquals("RamEntity must be at least 1 GB", violations.iterator().next().getMessage());
    }
}
