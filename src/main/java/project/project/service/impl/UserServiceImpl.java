package project.project.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project.model.dto.OrderInfoDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.dto.UserInfoDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;
import project.project.model.entity.UserRoleEntity;
import project.project.model.enums.UserRoles;
import project.project.repository.PCRepository;
import project.project.repository.UserRepository;
import project.project.repository.UserRoleRepository;
import project.project.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;
    private final PCRepository pcRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PCRepository pcRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.pcRepository = pcRepository;
    }

    @Transactional
    @Override
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

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByUserRole(UserRoles.USER);

        if (userRole == null) {
            throw new IllegalArgumentException("User role not found");
        }

        userEntity.getRole().add(userRole);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public List<PC> getOrdersByUserId(Long userId) {
        UserEntity user = getUserById(userId);
        return user.getOrders();
    }

    @Override
    @Transactional
    public boolean addPCToUserOrders(Long userId, Long pcId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<PC> pcOptional = pcRepository.findById(pcId);

        if (userOptional.isPresent() && pcOptional.isPresent()) {
            UserEntity user = userOptional.get();
            PC pc = pcOptional.get();

            if (!user.getOrders().contains(pc)) {
                user.getOrders().add(pc);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return user.getId();
        }
        throw new IllegalStateException("No user is currently authenticated");
    }
}
