package project.project.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.project.model.enums.*;
import project.project.repository.*;
import project.project.model.entity.*;
import java.util.Map;

@Component
public class Init implements CommandLineRunner {
    private final CPURepository cpuRepository;
    private final GPURepository gpuRepository;
    private final MemoryRepository memoryRepository;
    private final RAMRepository ramRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final Map<String, CPUType> defaultCPUs = Map.of(
            "Intel Core i3 12100", CPUType.I3,
            "Intel Core i5 12600K", CPUType.I5,
            "Intel Core i7 13700H", CPUType.I7,
            "Intel Core i5 11320K", CPUType.I5,
            "Intel Core i7 13300", CPUType.I7
    );

    private final Map<String, Integer> defaultGPUs = Map.of(
            "NVIDIA GeForce GTX 1650", 4,
            "NVIDIA GeForce GTX 1660", 6,
            "NVIDIA GeForce RTX 2060", 6,
            "AMD Radeon RX 570", 4,
            "AMD Radeon RX 580", 8
    );

    private final Map<String, MemoryType> defaultMemories = Map.of(
            "Samsung 970 EVO Plus", MemoryType.SSD,
            "WD Black SN850", MemoryType.SSD,
            "Seagate FireCuda", MemoryType.SSD,
            "Western Digital Blue", MemoryType.HDD,
            "Toshiba P300", MemoryType.HDD
    );

    private final Map<String, Integer> memorySizes = Map.of(
            "Samsung 970 EVO Plus", 1000,
            "WD Black SN850", 1000,
            "Seagate FireCuda", 2000,
            "Western Digital Blue", 1000,
            "Toshiba P300", 2000
    );

    private final Map<String, RamSizes> defaultRAMs = Map.of(
            "Corsair Vengeance LPX 16GB", RamSizes.SIXTEEN_GB,
            "G.Skill Ripjaws V 16GB", RamSizes.SIXTEEN_GB,
            "Crucial Ballistix 16GB", RamSizes.SIXTEEN_GB,
            "G.Skill Ripjaws V 8GB", RamSizes.EIGHT_GB,
            "Corsair Vengeance LPX 8GB", RamSizes.EIGHT_GB
    );

    private final Map<String, RamType> ramTypes = Map.of(
            "Corsair Vengeance LPX 16GB", RamType.DDR4,
            "G.Skill Ripjaws V 16GB", RamType.DDR4,
            "Crucial Ballistix 16GB", RamType.DDR4,
            "G.Skill Ripjaws V 8GB", RamType.DDR4,
            "Corsair Vengeance LPX 8GB", RamType.DDR4
    );

    public Init(CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1, UserRepository userRepository1, UserRoleRepository userRoleRepository) {
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
        this.memoryRepository = memoryRepository;
        this.ramRepository = ramRepository;
        this.passwordEncoder = passwordEncoder1;
        this.userRepository = userRepository1;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (cpuRepository.count() == 0) {
            for (Map.Entry<String, CPUType> entry : defaultCPUs.entrySet()) {
                CpuEntity cpuEntity = new CpuEntity();
                cpuEntity.setName(entry.getKey());
                cpuEntity.setCpuType(entry.getValue());
                cpuRepository.save(cpuEntity);
            }
        }

        if (gpuRepository.count() == 0) {
            for (Map.Entry<String, Integer> entry : defaultGPUs.entrySet()) {
                GpuEntity gpuEntity = new GpuEntity();
                gpuEntity.setName(entry.getKey());
                gpuEntity.setGpuRam(entry.getValue());
                gpuRepository.save(gpuEntity);
            }
        }

        if (memoryRepository.count() == 0) {
            for (String name : defaultMemories.keySet()) {
                MemoryEntity memoryEntity = new MemoryEntity();
                memoryEntity.setName(name);
                memoryEntity.setMemoryType(defaultMemories.get(name));
                memoryEntity.setSize(memorySizes.get(name));
                memoryRepository.save(memoryEntity);
            }
        }

        if (ramRepository.count() == 0) {
            for (String name : defaultRAMs.keySet()) {
                RamEntity ramEntity = new RamEntity();
                ramEntity.setName(name);
                ramEntity.setSize(defaultRAMs.get(name));
                ramEntity.setType(ramTypes.get(name));
                ramRepository.save(ramEntity);
            }
        }

        if (userRoleRepository.count() == 0) {
            for (UserRoles role : UserRoles.values()) {
                userRoleRepository.save(new UserRoleEntity(role));
            }
        }

        if (userRepository.count() == 0) {
            UserRoleEntity adminRole = userRoleRepository.findByUserRole(UserRoles.ADMIN);

            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("1q2w3e4r"));
            adminUser.getRole().add(adminRole);

            userRepository.save(adminUser);
        }

    }
}
