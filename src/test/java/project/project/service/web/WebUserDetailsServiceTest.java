package project.project.service.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.UserRepository;
import project.project.service.session.WebUserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    public void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("testPassword");
        userEntity.setRole(List.of(new UserRoleEntity(UserRoles.USER)));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = webUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());

        Set<GrantedAuthority> expectedAuthorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
        Set<GrantedAuthority> actualAuthorities = Set.copyOf(userDetails.getAuthorities());

        assertEquals(expectedAuthorities, actualAuthorities);
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> webUserDetailsService.loadUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }
}