package project.project.util;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import project.project.model.entity.UserEntity;
import project.project.repository.UserRepository;

import java.util.Optional;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class CustomAuthenticationSuccessHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomAuthenticationSuccessHandler successHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private Authentication authentication;

    @Test
    public void testOnAuthenticationSuccess() throws Exception {
        String username = "testUser";
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);

        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(session).setAttribute("userId", user.getId());
        verify(response).sendRedirect("/");
    }

    @Test
    public void testOnAuthenticationSuccess_UserNotFound() throws Exception {
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            successHandler.onAuthenticationSuccess(request, response, authentication);
        });
    }
}
