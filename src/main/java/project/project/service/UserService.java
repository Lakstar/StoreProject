package project.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.project.config.UserSession;
import project.project.model.dto.LoginDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.dto.entity.User;
import project.project.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean login(LoginDTO data) {
        Optional<User> byUsername=userRepository
                .findByUsername(data.getUsername());
        if(byUsername.isEmpty()){return false;}

        boolean passwordCheck = passwordEncoder.matches(data.getPassword(),byUsername.get().getPassword());
        if(!passwordCheck){return false;}

        userSession.login(byUsername.get().getId(),
                byUsername.get().getUsername());
        return true;
    }

    public boolean register(RegisterDTO data) {
        Optional<User> exists = userRepository
                .findByUsernameOrEmail(data.getUsername(),data.getEmail());

        if(exists.isPresent()){
            return false;
        }
        User user=new User();

        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        this.userRepository.save(user);
        return true;
    }
}
