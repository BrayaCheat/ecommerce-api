package com.ecommerce.ecommerce.Services.ServiceImpl;


import com.ecommerce.ecommerce.DTO.Request.LoginRequestDTO;
import com.ecommerce.ecommerce.DTO.Request.RegisterRequestDTO;
import com.ecommerce.ecommerce.Models.Role;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repositories.RoleRepository;
import com.ecommerce.ecommerce.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signup(RegisterRequestDTO dto) {
        Role role = roleRepository.findByRole("USER").orElseGet(() -> roleRepository.save(Role.builder().role("USER").build()));
        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        return userRepository.findByEmail(dto.getEmail())
                .orElseThrow();
    }
}
