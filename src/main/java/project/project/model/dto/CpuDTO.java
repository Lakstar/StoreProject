package project.project.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.project.model.enums.CPUType;

public class CpuDTO {

    @NotEmpty(message = "Cannot be empty")
    @Size(min=3, max=60, message = "Name must be between 3 and 60")
    private String name;
    @NotNull
    private CPUType cpuType;

    public CpuDTO() {
    }

    public CpuDTO(String name, CPUType cpuType) {
        this.name = name;
        this.cpuType = cpuType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CPUType getCpuType() {
        return cpuType;
    }

    public void setCpuType(CPUType cpuType) {
        this.cpuType = cpuType;
    }
}
