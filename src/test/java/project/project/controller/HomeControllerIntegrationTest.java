package project.project.controller;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowIndexWithoutPrincipal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("welcomeMessage", "Welcome anonymous!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowIndexWithPrincipal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("welcomeMessage", "Welcome user!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    public void testShowAdd_WithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-main"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testShowLoginFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/failed"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login-failed"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testHandleError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andDo(MockMvcResultHandlers.print());
    }
}
