package project.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import project.project.model.entity.*;
import project.project.model.enums.PCType;
import project.project.repository.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PcServiceImplTest {

    @InjectMocks
    private PcServiceImpl pcService;

    @Mock
    private PCRepository pcRepository;

    @Mock
    private CPURepository cpuRepository;

    @Mock
    private GPURepository gpuRepository;

    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private RAMRepository ramRepository;

    @Mock
    private Model model;

    @Test
    public void testSavePC() {
        PC pc = new PC();

        pcService.savePC(pc);

        verify(pcRepository, times(1)).save(pc);
    }

    @Test
    public void testGetAllPCs() {
        List<PC> pcs = new ArrayList<>();
        pcs.add(new PC());
        pcs.add(new PC());
        when(pcRepository.findAll()).thenReturn(pcs);

        List<PC> result = pcService.getAllPCs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pcs, result);
        verify(pcRepository, times(1)).findAll();
    }

    @Test
    public void testGetData() {
        List<CpuEntity> cpus = new ArrayList<>();
        List<GpuEntity> gpus = new ArrayList<>();
        List<MemoryEntity> memories = new ArrayList<>();
        List<RamEntity> rams = new ArrayList<>();

        when(cpuRepository.findAll()).thenReturn(cpus);
        when(gpuRepository.findAll()).thenReturn(gpus);
        when(memoryRepository.findAll()).thenReturn(memories);
        when(ramRepository.findAll()).thenReturn(rams);

        Model result = pcService.getData(model);

        assertNotNull(result);
        verify(model, times(1)).addAttribute("cpus", cpus);
        verify(model, times(1)).addAttribute("gpus", gpus);
        verify(model, times(1)).addAttribute("memories", memories);
        verify(model, times(1)).addAttribute("rams", rams);
        verify(model, times(1)).addAttribute("pcTypes", PCType.values());
    }
}
