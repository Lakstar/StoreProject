package project.project.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.project.model.enums.MemoryType;

public class MemoryDTO {
    @Size(min = 1, max = 40)
    private String name;

    @NotNull(message = "Type is required")
    private MemoryType type;

    @NotNull(message = "Size is required")
    private Integer size;

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
