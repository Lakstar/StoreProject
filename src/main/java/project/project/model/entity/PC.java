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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private CpuEntity cpuEntity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private GpuEntity gpuEntity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private MemoryEntity memoryEntity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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

    public CpuEntity getCpuEntity() {
        return cpuEntity;
    }

    public void setCpuEntity(CpuEntity cpuEntity) {
        this.cpuEntity = cpuEntity;
    }

    public GpuEntity getGpuEntity() {
        return gpuEntity;
    }

    public void setGpuEntity(GpuEntity gpuEntity) {
        this.gpuEntity = gpuEntity;
    }

    public MemoryEntity getMemoryEntity() {
        return memoryEntity;
    }

    public void setMemoryEntity(MemoryEntity memoryEntity) {
        this.memoryEntity = memoryEntity;
    }

    public RamEntity getRamEntity() {
        return ramEntity;
    }

    public void setRamEntity(RamEntity ramEntity) {
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
