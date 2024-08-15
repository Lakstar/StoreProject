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
import org.springframework.ui.Model;
import project.project.model.dto.PcDTO;
import project.project.model.entity.*;
import project.project.model.enums.PCType;
import project.project.service.*;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class PcControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GpuService gpuServiceImpl;

    @MockBean
    private CpuService cpuServiceImpl;

    @MockBean
    private RamService ramServiceImpl;

    @MockBean
    private MemoryService memoryServiceImpl;

    @MockBean
    private PcService pcServiceImpl;

    @MockBean
    private UserService userServiceImpl;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddPcForm() throws Exception {
        when(pcServiceImpl.getData(any())).thenAnswer(invocation -> {
            Model model = invocation.getArgument(0);
            model.addAttribute("cpus", new ArrayList<>());
            model.addAttribute("gpus", new ArrayList<>());
            model.addAttribute("memories", new ArrayList<>());
            model.addAttribute("rams", new ArrayList<>());
            model.addAttribute("pcTypes", PCType.values());
            return model;
        });

        mockMvc.perform(MockMvcRequestBuilders.get("/add/add-pc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-pc"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pc"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cpus"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("gpus"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("memories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rams"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pcTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddPC_Success() throws Exception {
        PcDTO pcDTO = new PcDTO();
        pcDTO.setName("Test PC");
        pcDTO.setCpuId(1L);
        pcDTO.setGpuId(1L);
        pcDTO.setMemoryId(1L);
        pcDTO.setRamId(1L);
        pcDTO.setPcType(PCType.PC);
        pcDTO.setPrice(1000.00);

        when(cpuServiceImpl.getPartById(anyLong())).thenReturn(new CpuEntity());
        when(gpuServiceImpl.getPartById(anyLong())).thenReturn(new GpuEntity());
        when(memoryServiceImpl.getPartById(anyLong())).thenReturn(new MemoryEntity());
        when(ramServiceImpl.getPartById(anyLong())).thenReturn(new RamEntity());

        doNothing().when(pcServiceImpl).savePC(any(PC.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/add/add-pc")
                        .flashAttr("pc", pcDTO)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/add"));

        verify(pcServiceImpl, times(1)).savePC(any(PC.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testBuyPC() throws Exception {
        Long pcId = 1L;
        String username = "testuser";

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername(username);
        userEntity.setOrders(new ArrayList<>());

        PC pc = new PC();
        pc.setId(pcId);

        when(userServiceImpl.getUserByUsername(username)).thenReturn(userEntity);
        when(userServiceImpl.addPCToUserOrders(userEntity.getId(), pcId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/shop/buy")
                        .param("pcId", pcId.toString())
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        verify(userServiceImpl).getUserByUsername(username);
        verify(userServiceImpl).addPCToUserOrders(userEntity.getId(), pcId);
    }
}
