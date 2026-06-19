package com.example.Cinenew_backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cinenew_backend.user.dto.UserLoginRequestDTO;
import com.example.Cinenew_backend.user.dto.UserRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        String resp=userService.register(userRequestDTO);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO){
        String resp=userService.login(userLoginRequestDTO);
        return ResponseEntity.ok(resp);
    }
}
