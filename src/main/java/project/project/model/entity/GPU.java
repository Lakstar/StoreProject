package project.project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="gpus")
public class GPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int gpuRam;

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

    public int getGpuRam() {
        return gpuRam;
    }

    public void setGpuRam(int gpuRam) {
        this.gpuRam = gpuRam;
    }
}
