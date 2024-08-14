package project.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.project.model.dto.GpuDTO;
import project.project.model.entity.GpuEntity;
import project.project.repository.GPURepository;
import project.project.service.impl.GpuServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GpuServiceImplTest {

    @InjectMocks
    private GpuServiceImpl gpuService;

    @Mock
    private GPURepository gpuRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testSaveGPU_Success() {
        GpuDTO gpuDTO = new GpuDTO();
        GpuEntity gpuEntity = new GpuEntity();
        when(modelMapper.map(gpuDTO, GpuEntity.class)).thenReturn(gpuEntity);

        boolean result = gpuService.saveGPU(gpuDTO);

        assertTrue(result);
        verify(gpuRepository, times(1)).save(gpuEntity);
    }

    @Test
    public void testSaveGPU_Exception() {
        GpuDTO gpuDTO = new GpuDTO();
        when(modelMapper.map(gpuDTO, GpuEntity.class)).thenThrow(new RuntimeException("Mapping error"));

        boolean result = gpuService.saveGPU(gpuDTO);

        assertFalse(result);
        verify(gpuRepository, never()).save(any(GpuEntity.class));
    }

    @Test
    public void testGetPartById_Success() {
        long id = 1L;
        GpuEntity gpuEntity = new GpuEntity();
        when(gpuRepository.findById(id)).thenReturn(Optional.of(gpuEntity));

        GpuEntity result = gpuService.getPartById(id);

        assertNotNull(result);
        assertEquals(gpuEntity, result);
        verify(gpuRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPartById_NotFound() {
        long id = 1L;
        when(gpuRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> gpuService.getPartById(id));
        assertEquals("GpuEntity not found with id: " + id, thrown.getMessage());
        verify(gpuRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllGpus() {
        List<GpuEntity> gpuEntities = new ArrayList<>();
        gpuEntities.add(new GpuEntity());
        gpuEntities.add(new GpuEntity());
        when(gpuRepository.findAll()).thenReturn(gpuEntities);

        List<GpuEntity> result = gpuService.getAllGpus();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(gpuEntities, result);
        verify(gpuRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteGpu() {
        long id = 1L;

        gpuService.deleteGpu(id);

        verify(gpuRepository, times(1)).deleteById(id);
    }
}
