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
import project.project.model.dto.CpuDTO;
import project.project.model.dto.GpuDTO;
import project.project.model.dto.MemoryDTO;
import project.project.model.dto.RamDTO;
import project.project.model.enums.CPUType;
import project.project.model.enums.MemoryType;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.service.CpuService;
import project.project.service.GpuService;
import project.project.service.MemoryService;
import project.project.service.RamService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class PartsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RamService ramServiceImpl;

    @MockBean
    private CpuService cpuServiceImpl;

    @MockBean
    private MemoryService memoryServiceImpl;

    @MockBean
    private GpuService gpuServiceImpl;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddRamForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-ram"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-ram"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ramData", "ramSizes", "ramTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddRam_Success() throws Exception {
        RamDTO ramDTO = new RamDTO();
        ramDTO.setName("Valid RAM Name");
        ramDTO.setSize(RamSizes.SIXTEEN_GB);
        ramDTO.setType(RamType.DDR4);

        when(ramServiceImpl.save(any(RamDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-ram")
                        .flashAttr("ramData", ramDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddRam_Failure() throws Exception {
        RamDTO ramDTO = new RamDTO();
        when(ramServiceImpl.save(any(RamDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-ram")
                        .flashAttr("ramData", ramDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-ram"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddCpuForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-cpu"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-cpu"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cpuData", "cpuTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddCpu_Success() throws Exception {
        CpuDTO cpuDTO = new CpuDTO();
        cpuDTO.setCpuType(CPUType.I5);
        cpuDTO.setName("test");
        when(cpuServiceImpl.save(any(CpuDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-cpu")
                        .flashAttr("cpuData", cpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddCpu_Failure() throws Exception {
        CpuDTO cpuDTO = new CpuDTO();
        when(cpuServiceImpl.save(any(CpuDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-cpu")
                        .flashAttr("cpuData", cpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-cpu"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddGpuForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-gpu"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-gpu"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("gpuData"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddGpu_Success() throws Exception {
        GpuDTO gpuDTO = new GpuDTO();
        gpuDTO.setGpuRam(4);
        gpuDTO.setName("test");
        when(gpuServiceImpl.saveGPU(any(GpuDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-gpu")
                        .flashAttr("gpuData", gpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddGpu_Failure() throws Exception {
        GpuDTO gpuDTO = new GpuDTO(); // Set invalid fields
        when(gpuServiceImpl.saveGPU(any(GpuDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-gpu")
                        .flashAttr("gpuData", gpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-gpu"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddMemoryForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-memory"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-memory"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("memoryData", "memoryTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddMemory_Success() throws Exception {
        MemoryDTO memoryDTO = new MemoryDTO();
        memoryDTO.setName("tester");
        memoryDTO.setSize(256);
        memoryDTO.setType(MemoryType.SSD);
        when(memoryServiceImpl.save(any(MemoryDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-memory")
                        .flashAttr("memoryData", memoryDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddMemory_Failure() throws Exception {
        MemoryDTO memoryDTO = new MemoryDTO(); // Set invalid fields
        when(memoryServiceImpl.save(any(MemoryDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-memory")
                        .flashAttr("memoryData", memoryDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-memory"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testViewParts() throws Exception {
        // Mock service methods
        when(cpuServiceImpl.getAllCpus()).thenReturn(Collections.emptyList());
        when(gpuServiceImpl.getAllGpus()).thenReturn(Collections.emptyList());
        when(memoryServiceImpl.getAllMemories()).thenReturn(Collections.emptyList());
        when(ramServiceImpl.getAllRams()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/view/parts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view-parts"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cpus", "gpus", "memories", "rams"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteCpu() throws Exception {
        doNothing().when(cpuServiceImpl).deleteCpu(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/cpu/{id}", 1L)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/view/parts"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteGpu() throws Exception {
        doNothing().when(gpuServiceImpl).deleteGpu(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/gpu/{id}", 1L)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/view/parts"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteMemory() throws Exception {
        doNothing().when(memoryServiceImpl).deleteMemory(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/memory/{id}", 1L)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/view/parts"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteRam() throws Exception {
        doNothing().when(ramServiceImpl).deleteRam(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/delete/ram/{id}", 1L)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/view/parts"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddRam_Duplicate() throws Exception {
        RamDTO ramDTO = new RamDTO();
        ramDTO.setName("Duplicate RAM Name");
        ramDTO.setSize(RamSizes.SIXTEEN_GB);
        ramDTO.setType(RamType.DDR4);

        when(ramServiceImpl.save(any(RamDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-ram")
                        .flashAttr("ramData", ramDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-ram"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("ramData"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddCpu_Duplicate() throws Exception {
        CpuDTO cpuDTO = new CpuDTO();
        cpuDTO.setCpuType(CPUType.I5);
        cpuDTO.setName("Duplicate CPU Name");

        when(cpuServiceImpl.save(any(CpuDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-cpu")
                        .flashAttr("cpuData", cpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-cpu"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("cpuData"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddGpu_Duplicate() throws Exception {
        GpuDTO gpuDTO = new GpuDTO();
        gpuDTO.setGpuRam(4);
        gpuDTO.setName("Duplicate GPU Name");

        when(gpuServiceImpl.saveGPU(any(GpuDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-gpu")
                        .flashAttr("gpuData", gpuDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-gpu"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("gpuData"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddMemory_Duplicate() throws Exception {
        MemoryDTO memoryDTO = new MemoryDTO();
        memoryDTO.setName("Duplicate Memory Name");
        memoryDTO.setSize(256);
        memoryDTO.setType(MemoryType.SSD);

        when(memoryServiceImpl.save(any(MemoryDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-memory")
                        .flashAttr("memoryData", memoryDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add/add-memory"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("memoryData"));
    }
}
