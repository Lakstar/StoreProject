package project.project.init;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.project.model.entity.*;
import project.project.model.enums.UserRoles;
import project.project.repository.*;

@ExtendWith(MockitoExtension.class)
public class InitTest {

    @Mock
    private CPURepository cpuRepository;

    @Mock
    private GPURepository gpuRepository;

    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private RAMRepository ramRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Init init;

    @Test
    void testRunWhenRepositoriesAreEmpty() throws Exception {
        when(cpuRepository.count()).thenReturn(0L);
        when(gpuRepository.count()).thenReturn(0L);
        when(memoryRepository.count()).thenReturn(0L);
        when(ramRepository.count()).thenReturn(0L);
        when(userRoleRepository.count()).thenReturn(0L);
        when(userRoleRepository.findByUserRole(UserRoles.ADMIN)).thenReturn(new UserRoleEntity(UserRoles.ADMIN));

        init.run();

        verify(cpuRepository, times(5)).save(any(CpuEntity.class));
        verify(gpuRepository, times(5)).save(any(GpuEntity.class));
        verify(memoryRepository, times(5)).save(any(MemoryEntity.class));
        verify(ramRepository, times(5)).save(any(RamEntity.class));
        verify(userRoleRepository, times(UserRoles.values().length)).save(any(UserRoleEntity.class));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRunWhenRepositoriesAreNotEmpty() throws Exception {
        when(cpuRepository.count()).thenReturn(10L);
        when(gpuRepository.count()).thenReturn(10L);
        when(memoryRepository.count()).thenReturn(10L);
        when(ramRepository.count()).thenReturn(10L);
        when(userRoleRepository.count()).thenReturn(10L);

        when(userRoleRepository.findByUserRole(UserRoles.ADMIN)).thenReturn(new UserRoleEntity(UserRoles.ADMIN));

        init.run();

        verify(cpuRepository, never()).save(any(CpuEntity.class));
        verify(gpuRepository, never()).save(any(GpuEntity.class));
        verify(memoryRepository, never()).save(any(MemoryEntity.class));
        verify(ramRepository, never()).save(any(RamEntity.class));
        verify(userRoleRepository, never()).save(any(UserRoleEntity.class));
    }
}
