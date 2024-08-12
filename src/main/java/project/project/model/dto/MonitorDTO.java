package project.project.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MonitorDTO {

    @NotEmpty
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 60)
    private String name;

    @NotNull
    @Positive
    private int inches;

    @NotEmpty
    private String description;


    public MonitorDTO(Long id, String name, int inches, String description) {
        this.id = id;
        this.name = name;
        this.inches = inches;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
