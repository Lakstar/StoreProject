package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.model.dto.entity.GPU;

public interface GPURepository extends JpaRepository<GPU, Long> {
}
