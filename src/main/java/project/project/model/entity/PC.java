package project.project.model.entity;

import jakarta.persistence.*;
import project.project.model.enums.PCType;

@Entity
@Table(name="pcs")
public class PC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private CpuEntity cpuEntity;

    @ManyToOne(optional = false)
    private GpuEntity gpuEntity;

    @ManyToOne(optional = false)
    private MemoryEntity memoryEntity;

    @ManyToOne(optional = false)
    private RamEntity ramEntity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PCType pcType;

    @Column(nullable = false)
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CpuEntity getCpu() {
        return cpuEntity;
    }

    public void setCpu(CpuEntity cpuEntity) {
        this.cpuEntity = cpuEntity;
    }

    public GpuEntity getGpu() {
        return gpuEntity;
    }

    public void setGpu(GpuEntity gpuEntity) {
        this.gpuEntity = gpuEntity;
    }

    public MemoryEntity getMemory() {
        return memoryEntity;
    }

    public void setMemory(MemoryEntity memoryEntity) {
        this.memoryEntity = memoryEntity;
    }

    public RamEntity getRam() {
        return ramEntity;
    }

    public void setRam(RamEntity ramEntity) {
        this.ramEntity = ramEntity;
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
