package project.project.service.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceImplTest {

    @InjectMocks
    private MonitorServiceImpl monitorService;

    @MockBean(name = "monitorsRestClient")
    private RestClient monitorRestClient;

    @Test
    void testCreateMonitor() {
        CreateMonitorDTO createMonitorDTO = new CreateMonitorDTO();

        monitorService.createMonitor(createMonitorDTO);

        verify(monitorRestClient).post();
    }

    @Test
    void testGetMonitorById() {
        Long id = 1L;
        MonitorDTO monitorDTO = new MonitorDTO();

        when(monitorRestClient.get().uri("/monitors/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve().body(MonitorDTO.class))
                .thenReturn(monitorDTO);

        MonitorDTO result = monitorService.getMonitorById(id);

        assertEquals(monitorDTO, result);
    }

    @Test
    void testGetAllMonitors() {
        MonitorDTO monitorDTO1 = new MonitorDTO();
        MonitorDTO monitorDTO2 = new MonitorDTO();
        List<MonitorDTO> monitors = List.of(monitorDTO1, monitorDTO2);

        when(monitorRestClient.get().uri("/monitors").accept(MediaType.APPLICATION_JSON).retrieve().body(new ParameterizedTypeReference<List<MonitorDTO>>() {}))
                .thenReturn(monitors);

        List<MonitorDTO> result = monitorService.getAllMonitors();

        assertEquals(monitors, result);
    }

    @Test
    void testDeleteMonitor() {
        Long id = 1L;

        monitorService.deleteMonitor(id);

        verify(monitorRestClient).delete();
    }

    @Test
    void testUpdateMonitor() {
        Long id = 1L;
        CreateMonitorDTO updateMonitorDTO = new CreateMonitorDTO();

        MonitorDTO updatedMonitorDTO = new MonitorDTO();

        when(monitorRestClient.put().uri("/monitors/{id}", id).accept(MediaType.APPLICATION_JSON).body(updateMonitorDTO).retrieve().body(MonitorDTO.class))
                .thenReturn(updatedMonitorDTO);

        MonitorDTO result = monitorService.updateMonitor(id, updateMonitorDTO);

        assertEquals(updatedMonitorDTO, result);
    }
}
