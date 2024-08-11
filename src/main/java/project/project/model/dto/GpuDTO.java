package project.project.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class GpuDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @Min(value = 1, message = "RamEntity must be at least 1 GB")
    private int gpuRam;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGpuRam() {
        return gpuRam;
    }

    public void setGpuRam(int gpuRam) {
        this.gpuRam = gpuRam;
    }
}
