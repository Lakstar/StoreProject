package project.project.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.project.model.enums.CPUType;
import project.project.model.enums.MemoryType;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.repository.*;
import project.project.model.entity.*;
import java.util.Map;

@Component
public class Init implements CommandLineRunner {
    private final CPURepository cpuRepository;
    private final GPURepository gpuRepository;
    private final MemoryRepository memoryRepository;
    private final RAMRepository ramRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, CPUType> defaultCPUs = Map.of(
            "Intel Core i3 13330H", CPUType.I3,
            "Intel Core i5 13455K", CPUType.I5,
            "Intel Core i7 13890H", CPUType.I7
    );

    private final Map<String, Integer> defaultGPUs = Map.of(
            "NVIDIA GeForce GTX 1080", 8, // Example GPU with 8 GB RAM
            "AMD Radeon RX 580", 8 // Example GPU with 8 GB RAM
    );

    private final Map<String, MemoryType> defaultMemories = Map.of(
            "Samsung 860 EVO", MemoryType.SSD,
            "Seagate Barracuda", MemoryType.HDD
    );

    private final Map<String, Integer> memorySizes = Map.of(
            "Samsung 860 EVO", 500, // 500 GB
            "Seagate Barracuda", 2000 // 2 TB
    );

    private final Map<String, RamSizes> defaultRAMs = Map.of(
            "Corsair Vengeance LPX 16GB", RamSizes.SIXTEEN_GB,
            "G.Skill Ripjaws V 8GB", RamSizes.EIGHT_GB
    );

    private final Map<String, RamType> ramTypes = Map.of(
            "Corsair Vengeance LPX 16GB", RamType.DDR4,
            "G.Skill Ripjaws V 8GB", RamType.DDR4
    );

    public Init(CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
        this.memoryRepository = memoryRepository;
        this.ramRepository = ramRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        if (cpuRepository.count() == 0) {
            for (Map.Entry<String, CPUType> entry : defaultCPUs.entrySet()) {
                CPU cpu = new CPU();
                cpu.setName(entry.getKey());
                cpu.setCpuType(entry.getValue());
                cpuRepository.save(cpu);
            }
        }

        if (gpuRepository.count() == 0) {
            for (Map.Entry<String, Integer> entry : defaultGPUs.entrySet()) {
                GPU gpu = new GPU();
                gpu.setName(entry.getKey());
                gpu.setGpuRam(entry.getValue());
                gpuRepository.save(gpu);
            }
        }

        if (memoryRepository.count() == 0) {
            for (String name : defaultMemories.keySet()) {
                Memory memory = new Memory();
                memory.setName(name);
                memory.setMemoryType(defaultMemories.get(name));
                memory.setSize(memorySizes.get(name));
                memoryRepository.save(memory);
            }
        }

        if (ramRepository.count() == 0) {
            for (String name : defaultRAMs.keySet()) {
                RAM ram = new RAM();
                ram.setName(name);
                ram.setSize(defaultRAMs.get(name));
                ram.setType(ramTypes.get(name));
                ramRepository.save(ram);
            }
        }

    }
}
