package project.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.project.model.dto.RegisterDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.PCRepository;
import project.project.repository.UserRepository;
import project.project.repository.UserRoleRepository;
import project.project.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PCRepository pcRepository;

    @Test
    public void testRegister_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(userRoleRepository.findByUserRole(UserRoles.USER)).thenReturn(new UserRoleEntity());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        boolean result = userService.register(registerDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_UsernameExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("existinguser");
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        boolean result = userService.register(registerDTO);

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_EmailExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setEmail("existinguser@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existinguser@example.com")).thenReturn(true);

        boolean result = userService.register(registerDTO);

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_PasswordsDoNotMatch() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("differentPassword");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);

        boolean result = userService.register(registerDTO);

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_UserRoleNotFound() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setEmail("newuser@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(userRoleRepository.findByUserRole(UserRoles.USER)).thenReturn(null);

        boolean result = userService.register(registerDTO);

        assertFalse(result);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testGetUserById_UserFound() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));

        assertEquals("User not found with ID: 1", thrown.getMessage());
    }

    @Test
    public void testGetOrdersByUserId_UserFoundWithOrders() {
        // Arrange
        UserEntity user = new UserEntity();
        List<PC> orders = new ArrayList<>();
        orders.add(new PC());
        orders.add(new PC());
        user.setOrders(orders);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        List<PC> result = userService.getOrdersByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrdersByUserId_UserFoundNoOrders() {
        UserEntity user = new UserEntity();
        user.setOrders(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<PC> result = userService.getOrdersByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrdersByUserId_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.getOrdersByUserId(1L));

        assertEquals("User not found with ID: 1", thrown.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddPCToUserOrders_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        PC pc = new PC();
        pc.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(pcRepository.findById(1L)).thenReturn(Optional.of(pc));

        boolean result = userService.addPCToUserOrders(1L, 1L);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddPCToUserOrders_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.addPCToUserOrders(1L, 1L);

        assertFalse(result);
    }

    @Test
    public void testAddPCToUserOrders_PCNotFound() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(pcRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.addPCToUserOrders(1L, 1L);

        assertFalse(result);
    }

    @Test
    public void testGetUserByUsername_UserFound() {
        UserEntity user = new UserEntity();
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserByUsername("user");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
    }

    @Test
    public void testGetUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername("user"));

        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testIsUsernameUnique_True() {
        when(userRepository.existsByUsername("uniqueuser")).thenReturn(false);

        boolean result = userService.isUsernameUnique("uniqueuser");

        assertTrue(result);
    }

    @Test
    public void testIsUsernameUnique_False() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        boolean result = userService.isUsernameUnique("existinguser");

        assertFalse(result);
    }

    @Test
    public void testIsEmailUnique_EmailUnique() {
        String email = "unique@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = userService.isEmailUnique(email);

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    public void testIsEmailUnique_EmailNotUnique() {
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.isEmailUnique(email);

        assertFalse(result);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    public void testUpdateUsername_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newusername")).thenReturn(false);

        userService.updateUsername(1L, "newusername");

        assertEquals("newusername", user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUsername_UsernameTaken() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("takenusername")).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userService.updateUsername(1L, "takenusername"));

        assertEquals("Username is already taken", thrown.getMessage());
    }
}