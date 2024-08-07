package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.model.dto.entity.CPU;

public interface CPURepository extends JpaRepository<CPU, Long> {
}
