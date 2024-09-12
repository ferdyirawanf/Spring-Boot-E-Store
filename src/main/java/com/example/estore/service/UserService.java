package com.example.estore.service;
import com.example.estore.DTO.request.UserDTO;
import com.example.estore.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User register(UserDTO.RegisterDTO registerRequest);
    String login(UserDTO.LoginDTO loginRequest);
}
