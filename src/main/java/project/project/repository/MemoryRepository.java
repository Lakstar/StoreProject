package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project.model.entity.MemoryEntity;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Long> {
}
