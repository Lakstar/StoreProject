package project.project.model.dto;

import jakarta.validation.constraints.*;
import project.project.model.enums.PCType;

public class PcDTO {
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    private Long cpuId;
    @NotNull
    private Long gpuId;
    @NotNull
    private Long memoryId;
    @NotNull
    private Long ramId;
    @NotNull
    private PCType pcType;
    @NotNull
    @Min(value = 1)
    @Max(value = 10000)
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCpuId() {
        return cpuId;
    }

    public void setCpuId(Long cpuId) {
        this.cpuId = cpuId;
    }

    public Long getGpuId() {
        return gpuId;
    }

    public void setGpuId(Long gpuId) {
        this.gpuId = gpuId;
    }

    public Long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(Long memoryId) {
        this.memoryId = memoryId;
    }

    public Long getRamId() {
        return ramId;
    }

    public void setRamId(Long ramId) {
        this.ramId = ramId;
    }

    public PCType getPcType() {
        return pcType;
    }

    public void setPcType(PCType pcType) {
        this.pcType = pcType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
