package project.project.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;

@ExtendWith(MockitoExtension.class)
public class UserRoleRepositoryTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Test
    void testFindByUserRole() {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUserRole(UserRoles.ADMIN);
        when(userRoleRepository.findByUserRole(UserRoles.ADMIN)).thenReturn(userRole);
        UserRoleEntity result = userRoleRepository.findByUserRole(UserRoles.ADMIN);
        assertNotNull(result);
        assertEquals(UserRoles.ADMIN, result.getUserRole());
        verify(userRoleRepository).findByUserRole(UserRoles.ADMIN);
    }
}
