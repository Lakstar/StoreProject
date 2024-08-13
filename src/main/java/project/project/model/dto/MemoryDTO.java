package project.project.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.project.model.enums.MemoryType;

public class MemoryDTO {
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 60, message = "Must be between 3 and 60 symbols")
    private String name;

    @NotNull(message = "Type is required")
    private MemoryType type;

    @NotNull(message = "Size is required")
    private Integer size;

    public MemoryDTO() {
    }

    public MemoryDTO(String name, MemoryType type, Integer size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemoryType getType() {
        return type;
    }

    public void setType(MemoryType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
