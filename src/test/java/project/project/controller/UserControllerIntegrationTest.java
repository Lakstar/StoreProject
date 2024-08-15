package project.project.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.project.model.dto.RegisterDTO;
import project.project.model.entity.UserEntity;
import project.project.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceImpl;


    /* THIS DOES NOT WORK
    @Test
    public void testRegisterUser_Success() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "tester")
                        .param("email", "test@example.com")
                        .param("password", "1q2w3e4r")
                        .param("confirmPassword", "1q2w3e4r")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(result -> {
                    System.out.println("Result: " + result.getResponse().getRedirectedUrl());
                });
    }*/

    @Test
    void testRegisterUser_Failure() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password")
                        .param("confirmPassword", "passworda")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("registerData"));
    }

    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"USER"})
    public void testGetUserPage_Success() throws Exception {
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setUsername("tester");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");

        when(userServiceImpl.getUserById(anyLong())).thenReturn(mockUser);
        when(userServiceImpl.getOrdersByUserId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .param("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"USER"})
    public void testGetUserPage_UserNotFound() throws Exception {
        when(userServiceImpl.getUserById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .param("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attribute("error", "User not found"));
    }

    @Test
    @WithMockUser(username = "tester", roles = {"ADMIN"})
    public void testUpdateUsername_Success() throws Exception {
        Long userId = 1L;
        String newUsername = "newTester";

        doNothing().when(userServiceImpl).updateUsername(userId, newUsername);
        UserEntity mockUser = new UserEntity();
        mockUser.setId(userId);
        mockUser.setUsername(newUsername);
        mockUser.setEmail("test@example.com");
        when(userServiceImpl.getUserById(userId)).thenReturn(mockUser);
        when(userServiceImpl.getOrdersByUserId(userId)).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/updateUsername")
                        .param("userId", userId.toString())
                        .param("newUsername", newUsername)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user?userId=" + userId))
                .andReturn();
    }
}