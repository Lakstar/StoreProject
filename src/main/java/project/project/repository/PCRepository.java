package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project.model.entity.PC;

@Repository
public interface PCRepository extends JpaRepository<PC, Long> {
}
