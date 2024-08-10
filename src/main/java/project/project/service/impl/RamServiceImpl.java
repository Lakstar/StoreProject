package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.RamDTO;
import project.project.model.entity.RAM;
import project.project.repository.RAMRepository;
import project.project.service.RamService;

@Service
public class RamServiceImpl implements RamService {
    private final RAMRepository ramRepository;
    private final ModelMapper modelMapper;

    public RamServiceImpl(RAMRepository ramRepository, ModelMapper modelMapper) {
        this.ramRepository = ramRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean save(RamDTO ramDTO) {
        try {
            RAM ram = modelMapper.map(ramDTO, RAM.class);
            ramRepository.save(ram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RAM getPartById(long id) {
        return ramRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RAM not found with id: " + id));
    }

}
