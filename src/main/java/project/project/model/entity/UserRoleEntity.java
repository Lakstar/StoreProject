package project.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import project.project.model.enums.UserRoles;

@Entity
@Table(name = "roles")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @NotNull
    private UserRoles userRole;

    public UserRoleEntity(UserRoles userRoles) {
        this.userRole=userRoles;
    }

    public UserRoleEntity() {

    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }
}
