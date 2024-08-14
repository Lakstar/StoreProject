package project.project.service;

import project.project.model.dto.RegisterDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    boolean register(RegisterDTO registerDTO);
    boolean addPCToUserOrders(Long userId, Long pcId);
    UserEntity getUserById(Long userId);
    List<PC> getOrdersByUserId(Long userId);

    UserEntity getUserByUsername(String username);

    boolean isUsernameUnique(String username);

    boolean isEmailUnique(String email);
    public void updateUsername(Long userId, String newUsername);
}
