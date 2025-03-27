package com.ecommerce.ecommerce.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @GetMapping("/admin")
    public String getAdmin(){
        return "Admin protected";
    }

    @GetMapping
    public String getUser(){
        return "User protected";
    }
}
