package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.project.model.entity.PC;

import java.util.List;

@Repository
public interface PCRepository extends JpaRepository<PC, Long> {

    @Query("SELECT p FROM PC p " +
            "JOIN FETCH p.cpuEntity " +
            "JOIN FETCH p.gpuEntity " +
            "JOIN FETCH p.memoryEntity " +
            "JOIN FETCH p.ramEntity")
    List<PC> findAllWithDetails();
}
