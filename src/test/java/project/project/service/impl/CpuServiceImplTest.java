package project.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.project.model.dto.CpuDTO;
import project.project.model.entity.CpuEntity;
import project.project.repository.CPURepository;
import project.project.service.impl.CpuServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CpuServiceImplTest {

    @InjectMocks
    private CpuServiceImpl cpuService;

    @Mock
    private CPURepository cpuRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testSave_Success() {
        CpuDTO cpuDTO = new CpuDTO();
        CpuEntity cpuEntity = new CpuEntity();
        when(modelMapper.map(cpuDTO, CpuEntity.class)).thenReturn(cpuEntity);

        boolean result = cpuService.save(cpuDTO);

        assertTrue(result);
        verify(cpuRepository, times(1)).save(cpuEntity);
    }

    @Test
    public void testSave_Exception() {
        CpuDTO cpuDTO = new CpuDTO();
        when(modelMapper.map(cpuDTO, CpuEntity.class)).thenThrow(new RuntimeException("Mapping error"));

        boolean result = cpuService.save(cpuDTO);

        assertFalse(result);
        verify(cpuRepository, never()).save(any(CpuEntity.class));
    }

    @Test
    public void testGetPartById_Success() {
        long id = 1L;
        CpuEntity cpuEntity = new CpuEntity();
        when(cpuRepository.findById(id)).thenReturn(Optional.of(cpuEntity));

        CpuEntity result = cpuService.getPartById(id);

        assertNotNull(result);
        assertEquals(cpuEntity, result);
        verify(cpuRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPartById_NotFound() {
        long id = 1L;
        when(cpuRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> cpuService.getPartById(id));
        assertEquals("CpuEntity not found with id: " + id, thrown.getMessage());
        verify(cpuRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllCpus() {
        List<CpuEntity> cpuEntities = new ArrayList<>();
        cpuEntities.add(new CpuEntity());
        cpuEntities.add(new CpuEntity());
        when(cpuRepository.findAll()).thenReturn(cpuEntities);

        List<CpuEntity> result = cpuService.getAllCpus();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cpuEntities, result);
        verify(cpuRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCpu() {
        long id = 1L;

        cpuService.deleteCpu(id);

        verify(cpuRepository, times(1)).deleteById(id);
    }
}
