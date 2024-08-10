package project.project.service;

import project.project.model.dto.RamDTO;
import project.project.model.entity.RAM;

public interface RamService {
    public boolean save(RamDTO ramDTO);
    public RAM getPartById(long id);
}
