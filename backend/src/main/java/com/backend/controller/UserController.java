package com.backend.controller;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserController {

    @GetMapping("/{id}")
    UserEntity findUserById(@PathVariable Long id);

    @GetMapping("/input/{id}")
    UserInputDTO findInputUserById(@PathVariable Long id);

    @GetMapping("/profile/{id}")
    ProfileDTO getProfileByUserId(@PathVariable Long id);

}

