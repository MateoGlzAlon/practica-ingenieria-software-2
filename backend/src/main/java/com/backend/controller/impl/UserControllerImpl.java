package com.backend.controller.impl;

import com.backend.controller.UserController;
import com.backend.persistence.entity.UserEntity;
import com.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    public UserEntity findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }
}
