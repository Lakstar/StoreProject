package project.project.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateMonitorDTO {
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 60)
    private String name;

    @NotNull(message = "Cannot be empty")
    @Positive(message = "Must be a positive number")
    private int inches;

    @NotEmpty(message = "Description is required")
    @Size(min = 5,max = 100, message = "Between 5 and 100 symbols")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInches() {
        return inches;
    }

    public void setInches(int inches) {
        this.inches = inches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
