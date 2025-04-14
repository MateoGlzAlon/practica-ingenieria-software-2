package com.backend.service;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;

public interface UserService {

    UserEntity findUserById(Long id);

    UserInputDTO findUserInputByID(Long id);
}
