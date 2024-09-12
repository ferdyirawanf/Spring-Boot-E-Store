package com.example.estore.Controller;

import com.example.estore.DTO.request.UserDTO;
import com.example.estore.DTO.response.RenderJson;
import com.example.estore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO.RegisterDTO req) {
        return RenderJson.basicFormat(
                userService.register(req),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.LoginDTO req) {
        return RenderJson.basicFormat(
                userService.login(req),
                HttpStatus.OK
        );
    }
}
