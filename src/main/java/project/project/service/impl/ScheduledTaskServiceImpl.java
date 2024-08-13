package project.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.model.entity.CpuEntity;
import project.project.model.entity.GpuEntity;
import project.project.model.entity.MemoryEntity;
import project.project.model.entity.RamEntity;
import project.project.model.enums.CPUType;
import project.project.model.enums.MemoryType;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.repository.CPURepository;
import project.project.repository.GPURepository;
import project.project.repository.MemoryRepository;
import project.project.repository.RAMRepository;
import project.project.service.ScheduledTaskService;

@Service
@RequiredArgsConstructor
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    private final RAMRepository ramRepository;

    private final CPURepository cpuRepository;

    private final MemoryRepository memoryRepository;

    private final GPURepository gpuRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void insertRamsIfLessThanFive() {
        long count = ramRepository.count();
        if (count < 5) {
            for (int i = 0; i < 3; i++) {
                RamEntity ram = new RamEntity();
                ram.setName("RAM-" + String.valueOf(i));
                ram.setSize(RamSizes.values()[i % RamSizes.values().length]);
                ram.setType(RamType.values()[i % RamType.values().length]);
                ramRepository.save(ram);
            }
        }
    }
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void insertMemoriesIfLessThanFive() {
        long count = memoryRepository.count();
        if (count < 5) {
            for (int i = 0; i < 3; i++) {
                MemoryEntity memory = new MemoryEntity();
                memory.setName("Memory-" + String.valueOf(i));
                memory.setMemoryType(MemoryType.values()[i % MemoryType.values().length]);
                memory.setSize((i + 1) * 512);
                memoryRepository.save(memory);
            }
        }
    }
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void insertGpusIfLessThanFive() {
        long count = gpuRepository.count();
        if (count < 5) {
            for (int i = 0; i < 3; i++) {
                GpuEntity gpu = new GpuEntity();
                gpu.setName("GPU-" + String.valueOf(i));
                gpu.setGpuRam((i + 1) * 4);
                gpuRepository.save(gpu);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void insertCpusIfLessThanFive() {
        long count = cpuRepository.count();
        if (count < 5) {
            for (int i = 0; i < 3; i++) {
                CpuEntity cpu = new CpuEntity();
                cpu.setName("CPU-" + String.valueOf(i));
                cpu.setCpuType(CPUType.values()[i % CPUType.values().length]);
                cpuRepository.save(cpu);
            }
        }
    }

}
