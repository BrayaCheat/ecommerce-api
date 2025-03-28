package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.DTO.Request.LoginRequestDTO;
import com.ecommerce.ecommerce.DTO.Request.RegisterRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.LoginResponseDTO;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.ServiceImpl.AuthenticationServiceImpl;
import com.ecommerce.ecommerce.Services.ServiceImpl.JwtServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtServiceImpl jwtService;

    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(JwtServiceImpl jwtService, AuthenticationServiceImpl authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.status(201).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        return ResponseEntity.status(201).body(loginResponse);
    }
    //LoginRequestDTO loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
}
