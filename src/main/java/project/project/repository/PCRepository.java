package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.project.model.entity.PC;

import java.util.List;

@Repository
public interface PCRepository extends JpaRepository<PC, Long> {
}
