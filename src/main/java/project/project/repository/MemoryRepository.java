package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.model.dto.entity.Memory;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
}
