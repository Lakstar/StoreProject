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
import project.project.model.entity.*;
import project.project.model.enums.PCType;
import project.project.service.PcService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PcService pcServiceImpl;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testViewShop() throws Exception {
        CpuEntity cpu = new CpuEntity();
        GpuEntity gpu = new GpuEntity();
        MemoryEntity memory = new MemoryEntity();
        RamEntity ram = new RamEntity();

        PC pc1 = new PC();
        pc1.setName("PC1");
        pc1.setCpuEntity(cpu);
        pc1.setGpuEntity(gpu);
        pc1.setMemoryEntity(memory);
        pc1.setRamEntity(ram);
        pc1.setPcType(PCType.PC);
        pc1.setPrice(1000.0);

        PC pc2 = new PC();
        pc2.setName("PC2");
        pc2.setCpuEntity(cpu);
        pc2.setGpuEntity(gpu);
        pc2.setMemoryEntity(memory);
        pc2.setRamEntity(ram);
        pc2.setPcType(PCType.PC);
        pc2.setPrice(1500.0);

        List<PC> pcs = Arrays.asList(pc1, pc2);

        when(pcServiceImpl.getAllPCs()).thenReturn(pcs);

        mockMvc.perform(MockMvcRequestBuilders.get("/shop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("shop"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pcs"))
                .andExpect(MockMvcResultMatchers.model().attribute("pcs", pcs));
    }
}
