package project.project.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.project.model.entity.CPUType;

public class CpuDTO {

    @NotEmpty
    @Size(min=3, max=40)
    private String name;
    @NotNull
    private CPUType cpuType;

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
