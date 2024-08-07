package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.model.dto.entity.RAM;

public interface RAMRepository extends JpaRepository<RAM, Long> {
}
