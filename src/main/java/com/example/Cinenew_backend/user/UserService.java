package com.example.Cinenew_backend.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Cinenew_backend.enumData.Role;
import com.example.Cinenew_backend.exception.InvalidCredentialsException;
import com.example.Cinenew_backend.exception.UserAlreadyExistsException;
import com.example.Cinenew_backend.user.dto.UserLoginRequestDTO;
import com.example.Cinenew_backend.user.dto.UserRequestDTO;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public String register(UserRequestDTO userRequestDTO){
        User user=userRepository.findByEmail(userRequestDTO.getEmail());
        if(user!=null) throw new UserAlreadyExistsException("Email already exists");
        String encodedPassword=passwordEncoder.encode(userRequestDTO.getPassword());
        User newUser=new User();
        newUser.setUserName(userRequestDTO.getUserName());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
        return "User registration successfull";
    }

    public String login(UserLoginRequestDTO userLoginRequestDTO){
        User user=userRepository.findByEmail(userLoginRequestDTO.getEmail());
        if(user==null||!passwordEncoder.matches(userLoginRequestDTO.getPassword(), user.getPassword())) throw new InvalidCredentialsException("Invalid Credentials");
        return "Login successful";
    }
}
