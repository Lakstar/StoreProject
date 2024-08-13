package project.project.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class GpuDTO {

    @NotEmpty(message = "Name is required")
    @Size(min = 3,max = 40, message = "Name must be between 3 and 60")
    private String name;

    @Min(value = 1, message = "RamEntity must be at least 1 GB")
    private int gpuRam;

    public GpuDTO() {
    }

    public GpuDTO(String name, int gpuRam) {
        this.name = name;
        this.gpuRam = gpuRam;
    }

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
