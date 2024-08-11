package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project.model.entity.RamEntity;

@Repository
public interface RAMRepository extends JpaRepository<RamEntity, Long> {
}
