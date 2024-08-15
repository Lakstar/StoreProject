package project.project.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.project.model.enums.MemoryType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidMemoryDTO() {
        MemoryDTO memoryDTO = new MemoryDTO("Corsair Vengeance", MemoryType.SSD, 256);
        Set<ConstraintViolation<MemoryDTO>> violations = validator.validate(memoryDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidMemoryDTOName() {
        MemoryDTO memoryDTO = new MemoryDTO("Co", MemoryType.SSD, 256);
        Set<ConstraintViolation<MemoryDTO>> violations = validator.validate(memoryDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Must be between 3 and 60 symbols", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidMemoryDTOType() {
        MemoryDTO memoryDTO = new MemoryDTO("Corsair Vengeance", null, 256);
        Set<ConstraintViolation<MemoryDTO>> violations = validator.validate(memoryDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Type is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidMemoryDTOSize() {
        MemoryDTO memoryDTO = new MemoryDTO("Corsair Vengeance", MemoryType.SSD, null);
        Set<ConstraintViolation<MemoryDTO>> violations = validator.validate(memoryDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Size is required", violations.iterator().next().getMessage());
    }
}
