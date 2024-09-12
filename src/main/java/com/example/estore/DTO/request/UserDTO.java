package com.example.estore.DTO.request;

import com.example.estore.utils.enums.Role;
import lombok.Builder;
import lombok.Data;

public class UserDTO {
    @Data
    @Builder
    public static class RegisterDTO {
        private String username;
        private String password;
        private Role role;
    }

    @Data
    @Builder
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
