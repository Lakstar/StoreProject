package project.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.project.model.entity.CpuEntity;
import project.project.model.entity.GpuEntity;
import project.project.model.entity.MemoryEntity;
import project.project.model.entity.RamEntity;
import project.project.repository.CPURepository;
import project.project.repository.GPURepository;
import project.project.repository.MemoryRepository;
import project.project.repository.RAMRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledTaskServiceImplTest {

    @InjectMocks
    private ScheduledTaskServiceImpl scheduledTaskService;

    @Mock
    private RAMRepository ramRepository;

    @Mock
    private CPURepository cpuRepository;

    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private GPURepository gpuRepository;

    @BeforeEach
    public void setup() {
        // Reset mocks before each test
        reset(ramRepository, cpuRepository, memoryRepository, gpuRepository);
    }

    @Test
    public void testInsertRamsIfLessThanFive_whenLessThanFive() {
        when(ramRepository.count()).thenReturn(4L);

        scheduledTaskService.insertRamsIfLessThanFive();

        verify(ramRepository, times(3)).save(any(RamEntity.class));
    }

    @Test
    public void testInsertRamsIfLessThanFive_whenFiveOrMore() {
        when(ramRepository.count()).thenReturn(5L);

        scheduledTaskService.insertRamsIfLessThanFive();

        verify(ramRepository, never()).save(any(RamEntity.class));
    }

    @Test
    public void testInsertMemoriesIfLessThanFive_whenLessThanFive() {
        when(memoryRepository.count()).thenReturn(4L);

        scheduledTaskService.insertMemoriesIfLessThanFive();

        verify(memoryRepository, times(3)).save(any(MemoryEntity.class));
    }

    @Test
    public void testInsertMemoriesIfLessThanFive_whenFiveOrMore() {
        when(memoryRepository.count()).thenReturn(5L);

        scheduledTaskService.insertMemoriesIfLessThanFive();

        verify(memoryRepository, never()).save(any(MemoryEntity.class));
    }

    @Test
    public void testInsertGpusIfLessThanFive_whenLessThanFive() {
        when(gpuRepository.count()).thenReturn(4L);

        scheduledTaskService.insertGpusIfLessThanFive();

        verify(gpuRepository, times(3)).save(any(GpuEntity.class));
    }

    @Test
    public void testInsertGpusIfLessThanFive_whenFiveOrMore() {
        when(gpuRepository.count()).thenReturn(5L);

        scheduledTaskService.insertGpusIfLessThanFive();

        verify(gpuRepository, never()).save(any(GpuEntity.class));
    }

    @Test
    public void testInsertCpusIfLessThanFive_whenLessThanFive() {
        when(cpuRepository.count()).thenReturn(4L);

        scheduledTaskService.insertCpusIfLessThanFive();

        verify(cpuRepository, times(3)).save(any(CpuEntity.class));
    }

    @Test
    public void testInsertCpusIfLessThanFive_whenFiveOrMore() {
        when(cpuRepository.count()).thenReturn(5L);

        scheduledTaskService.insertCpusIfLessThanFive();

        verify(cpuRepository, never()).save(any(CpuEntity.class));
    }
}
