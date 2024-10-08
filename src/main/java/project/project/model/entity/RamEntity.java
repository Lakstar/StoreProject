package project.project.model.entity;

import jakarta.persistence.*;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;

@Entity
@Table(name="ram")
public class RamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RamSizes size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RamType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RamSizes getSize() {
        return size;
    }

    public void setSize(RamSizes size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RamType getType() {
        return type;
    }

    public void setType(RamType type) {
        this.type = type;
    }
}
