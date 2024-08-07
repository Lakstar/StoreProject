package project.project.model.dto.entity;

import jakarta.persistence.*;

@Entity
@Table(name="pcs")
public class PC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private CPU cpu;

    @ManyToOne(optional = false)
    private GPU gpu;

    @ManyToOne(optional = false)
    private Memory memory;

    @ManyToOne(optional = false)
    private RAM ram;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PCType pcType;

    @Column(nullable = false)
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PCType getPcType() {
        return pcType;
    }

    public void setPcType(PCType pcType) {
        this.pcType = pcType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public GPU getGpu() {
        return gpu;
    }

    public void setGpu(GPU gpu) {
        this.gpu = gpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
