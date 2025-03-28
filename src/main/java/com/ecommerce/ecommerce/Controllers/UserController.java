package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.ServiceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity<List<User>> allUser(){
        return ResponseEntity.status(200).body(userService.allUsers());
    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getUser(){
        return ResponseEntity.status(200).body(userService.getUser());
    }
}
