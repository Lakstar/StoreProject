package project.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;
import project.project.service.MonitorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitorService monitorService;

    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void testShowAddMonitorForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-monitor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-monitor"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("monitorData"));
    }
    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void testShowMonitors() throws Exception {
        List<MonitorDTO> mockMonitors = new ArrayList<>();
        mockMonitors.add(new MonitorDTO(1L, "Monitor1", 24, "Description1"));
        mockMonitors.add(new MonitorDTO(2L, "Monitor2", 27, "Description2"));

        when(monitorService.getAllMonitors()).thenReturn(mockMonitors);

        mockMvc.perform(MockMvcRequestBuilders.get("/view/monitors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("monitor-control"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("monitors"))
                .andExpect(MockMvcResultMatchers.model().attribute("monitors", mockMonitors));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void testShowEditMonitorForm() throws Exception {
        Long monitorId = 1L;
        MonitorDTO mockMonitor = new MonitorDTO(monitorId, "Monitor1", 24, "Description1");

        when(monitorService.getMonitorById(monitorId)).thenReturn(mockMonitor);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/monitor/{id}", monitorId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit-monitor"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("monitorData"))
                .andExpect(MockMvcResultMatchers.model().attribute("monitorData", mockMonitor));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void testUpdateMonitor() throws Exception {
        Long monitorId = 1L;
        CreateMonitorDTO updateMonitorDTO = new CreateMonitorDTO("New Monitor", 27, "New Description");

        when(monitorService.updateMonitor(eq(monitorId), any(CreateMonitorDTO.class))).thenReturn(new MonitorDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/edit/monitor/{id}", monitorId)
                        .flashAttr("monitorData", updateMonitorDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/view/monitors"));
    }
}
