package project.project.service;

import project.project.model.dto.OrderInfoDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.dto.UserInfoDTO;

import java.util.List;

public interface UserService {
    public void register(RegisterDTO registerDTO);
    public boolean addPCToUserOrders(Long userId, Long pcId);
    public UserInfoDTO getUserById(Long userId);
    public List<OrderInfoDTO> getOrdersByUserId(Long userId);

    public UserInfoDTO getUserByUsername(String username);
}
