package project.project.service;

import org.springframework.stereotype.Service;
import project.project.model.dto.entity.RAM;
import project.project.repository.RAMRepository;

import java.util.List;

@Service
public class RamService {
    private final RAMRepository ramRepository;

    public RamService(RAMRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    public void saveRAM(RAM ram) {
        ramRepository.save(ram);
    }

    public List<RAM> findAllRAMs() {
        return ramRepository.findAll();
    }

    public RAM getRAMById(long id) {
        return ramRepository.findById(id).orElseThrow(() -> new RuntimeException("RAM not found"));
    }
}
