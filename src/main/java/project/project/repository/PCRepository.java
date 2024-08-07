package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.model.dto.entity.PC;

public interface PCRepository extends JpaRepository<PC, Long> {
}
