package project.project.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.project.model.entity.UserEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testExistsByUsername() {
        String username = "testUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);
        boolean exists = userRepository.existsByUsername(username);
        assertTrue(exists);
        verify(userRepository).existsByUsername(username);
    }

    @Test
    void testExistsByEmail() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        boolean exists = userRepository.existsByEmail(email);
        assertFalse(exists);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Optional<UserEntity> result = userRepository.findByUsername(username);
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository).findByUsername(username);
    }
}
