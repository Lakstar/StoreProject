package project.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.project.model.dto.MemoryDTO;
import project.project.model.entity.MemoryEntity;
import project.project.repository.MemoryRepository;
import project.project.service.impl.MemoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemoryServiceImplTest {

    @InjectMocks
    private MemoryServiceImpl memoryService;

    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private ModelMapper modelMapper;


    @Test
    public void testSave_Success() {
        MemoryDTO memoryDTO = new MemoryDTO();
        MemoryEntity memoryEntity = new MemoryEntity();
        when(modelMapper.map(memoryDTO, MemoryEntity.class)).thenReturn(memoryEntity);

        boolean result = memoryService.save(memoryDTO);

        assertTrue(result);
        verify(memoryRepository, times(1)).save(memoryEntity);
    }

    @Test
    public void testSave_Exception() {
        MemoryDTO memoryDTO = new MemoryDTO();
        when(modelMapper.map(memoryDTO, MemoryEntity.class)).thenThrow(new RuntimeException("Mapping error"));

        boolean result = memoryService.save(memoryDTO);

        assertFalse(result);
        verify(memoryRepository, never()).save(any(MemoryEntity.class));
    }

    @Test
    public void testGetPartById_Success() {
        long id = 1L;
        MemoryEntity memoryEntity = new MemoryEntity();
        when(memoryRepository.findById(id)).thenReturn(Optional.of(memoryEntity));

        MemoryEntity result = memoryService.getPartById(id);

        assertNotNull(result);
        assertEquals(memoryEntity, result);
        verify(memoryRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPartById_NotFound() {
        long id = 1L;
        when(memoryRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> memoryService.getPartById(id));
        assertEquals("MemoryEntity not found with id: " + id, thrown.getMessage());
        verify(memoryRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllMemories() {
        List<MemoryEntity> memoryEntities = new ArrayList<>();
        memoryEntities.add(new MemoryEntity());
        memoryEntities.add(new MemoryEntity());
        when(memoryRepository.findAll()).thenReturn(memoryEntities);

        List<MemoryEntity> result = memoryService.getAllMemories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(memoryEntities, result);
        verify(memoryRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteMemory() {
        long id = 1L;

        memoryService.deleteMemory(id);

        verify(memoryRepository, times(1)).deleteById(id);
    }
}
