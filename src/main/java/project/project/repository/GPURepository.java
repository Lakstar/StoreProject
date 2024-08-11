package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project.model.entity.GpuEntity;

@Repository
public interface GPURepository extends JpaRepository<GpuEntity, Long> {
}
