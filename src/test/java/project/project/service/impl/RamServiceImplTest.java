package project.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.project.model.dto.RamDTO;
import project.project.model.entity.RamEntity;
import project.project.repository.RAMRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RamServiceImplTest {

    @InjectMocks
    private RamServiceImpl ramService;

    @Mock
    private RAMRepository ramRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testSave() {
        RamDTO ramDTO = new RamDTO();
        RamEntity ramEntity = new RamEntity();
        when(modelMapper.map(ramDTO, RamEntity.class)).thenReturn(ramEntity);
        when(ramRepository.save(ramEntity)).thenReturn(ramEntity);

        boolean result = ramService.save(ramDTO);

        assertTrue(result);
        verify(ramRepository, times(1)).save(ramEntity);
    }

    @Test
    public void testSave_throwsException() {
        RamDTO ramDTO = new RamDTO();
        when(modelMapper.map(ramDTO, RamEntity.class)).thenThrow(RuntimeException.class);

        boolean result = ramService.save(ramDTO);

        assertFalse(result);
        verify(ramRepository, never()).save(any(RamEntity.class));
    }

    @Test
    public void testGetPartById() {
        long id = 1L;
        RamEntity ramEntity = new RamEntity();
        when(ramRepository.findById(id)).thenReturn(Optional.of(ramEntity));

        RamEntity result = ramService.getPartById(id);

        assertNotNull(result);
        assertEquals(ramEntity, result);
        verify(ramRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPartById_notFound() {
        long id = 1L;
        when(ramRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ramService.getPartById(id));

        assertEquals("RamEntity not found with id: " + id, exception.getMessage());
        verify(ramRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllRams() {
        List<RamEntity> rams = new ArrayList<>();
        rams.add(new RamEntity());
        rams.add(new RamEntity());
        when(ramRepository.findAll()).thenReturn(rams);

        List<RamEntity> result = ramService.getAllRams();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(rams, result);
        verify(ramRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteRam() {
        long id = 1L;

        ramService.deleteRam(id);

        verify(ramRepository, times(1)).deleteById(id);
    }
}
