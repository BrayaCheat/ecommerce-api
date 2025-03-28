package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User getUser();
}
