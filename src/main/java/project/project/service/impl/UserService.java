package project.project.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.project.controller.HomeController;
import project.project.model.dto.LoginDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.UserRepository;
import project.project.repository.UserRoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            System.out.println("Username taken");
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            System.out.println("Email taken");
            throw new IllegalArgumentException("Email is already in use");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            System.out.println("password mismatch");
            throw new IllegalArgumentException("Passwords do not match");

        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByUserRole(UserRoles.USER);

        if (userRole == null) {
            System.out.println("Role Not found");
            throw new IllegalArgumentException("User role not found");
        }

        userEntity.getRole().add(userRole);

        System.out.println("About to save");

        userRepository.save(userEntity);

        System.out.println("Success.");
    }
}
