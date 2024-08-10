package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.project.model.dto.LoginDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        UserEntity userEntity = modelMapper.map(registerDTO, UserEntity.class);

        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userEntity.setRole(List.of(new UserRoleEntity(UserRoles.USER)));

        userRepository.save(userEntity);
    }
}
