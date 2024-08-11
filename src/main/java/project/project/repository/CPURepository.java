package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project.model.entity.CpuEntity;

@Repository
public interface CPURepository extends JpaRepository<CpuEntity, Long> {
}
