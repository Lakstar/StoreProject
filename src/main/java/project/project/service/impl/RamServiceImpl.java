package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.RamDTO;
import project.project.model.entity.RamEntity;
import project.project.repository.RAMRepository;
import project.project.service.RamService;

import java.util.List;
import java.util.stream.Collectors;

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
            RamEntity ramEntity = modelMapper.map(ramDTO, RamEntity.class);
            ramRepository.save(ramEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RamEntity getPartById(long id) {
        return ramRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RamEntity not found with id: " + id));
    }

    @Override
    public List<RamEntity> getAllRams() {
        return ramRepository.findAll();
    }

    @Override
    public void deleteRam(long id) {
        ramRepository.deleteById(id);
    }

}
