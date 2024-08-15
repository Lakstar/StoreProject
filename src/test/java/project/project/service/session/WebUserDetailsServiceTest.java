package project.project.service.session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WebUserDetailsServiceTest {

    @InjectMocks
    private WebUserDetailsService webUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        reset(userRepository);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("password");
        userEntity.setEmail("test@example.com");

        UserRoleEntity roleEntity = new UserRoleEntity();
        roleEntity.setUserRole(UserRoles.USER);
        userEntity.setRole(Collections.singletonList(roleEntity));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = webUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        String username = "unknownUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            webUserDetailsService.loadUserByUsername(username);
        });
    }

    @Test
    void mapToUserDetails_CorrectlyMapsUserEntityToUserDetails() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("password");
        userEntity.setEmail("test@example.com");

        UserRoleEntity roleEntity = new UserRoleEntity();
        roleEntity.setUserRole(UserRoles.ADMIN);
        userEntity.setRole(Collections.singletonList(roleEntity));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = webUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
    }
}