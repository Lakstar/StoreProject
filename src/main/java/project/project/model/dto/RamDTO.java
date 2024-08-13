package project.project.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;

public class RamDTO {
    @NotEmpty(message = "Name is required")
    @Size(min = 4, max = 60, message = "Name must be between 4 and 60 symbols long")
    private String name;

    @NotNull(message = "Size is required")
    private RamSizes size;

    @NotNull(message = "Type is required")
    private RamType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RamSizes getSize() {
        return size;
    }

    public void setSize(RamSizes size) {
        this.size = size;
    }

    public RamType getType() {
        return type;
    }

    public void setType(RamType type) {
        this.type = type;
    }
}