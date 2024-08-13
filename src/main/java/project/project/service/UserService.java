package project.project.service;

import project.project.model.dto.RegisterDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    public boolean register(RegisterDTO registerDTO);
    public boolean addPCToUserOrders(Long userId, Long pcId);
    public UserEntity getUserById(Long userId);
    public List<PC> getOrdersByUserId(Long userId);

    public UserEntity getUserByUsername(String username);
}
