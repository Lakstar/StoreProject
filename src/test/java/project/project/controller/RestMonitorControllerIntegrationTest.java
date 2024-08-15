package project.project.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;
import project.project.service.MonitorService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RestMonitorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MonitorService monitorService;

    private CreateMonitorDTO createMonitorDTO;
    private MonitorDTO monitorDTO;

    @BeforeEach
    void setUp() {
        createMonitorDTO = new CreateMonitorDTO();
        createMonitorDTO.setName("Dell Monitor");
        createMonitorDTO.setDescription("1920x1080");
        createMonitorDTO.setInches(24);

        monitorDTO = new MonitorDTO();
        monitorDTO.setId(1L);
        monitorDTO.setName("Dell Monitor");
        monitorDTO.setDescription("1920x1080");
        monitorDTO.setInches(24);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetMonitor() throws Exception {
        when(monitorService.getMonitorById(1L)).thenReturn(monitorDTO);

        mockMvc.perform(get("/api/monitors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dell Monitor"))
                .andExpect(jsonPath("$.description").value("1920x1080"))
                .andExpect(jsonPath("$.inches").value(24));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllMonitors() throws Exception {
        when(monitorService.getAllMonitors()).thenReturn(Collections.singletonList(monitorDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/monitors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(monitorDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(monitorDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(monitorDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].inches").value(monitorDTO.getInches()));
    }
}
