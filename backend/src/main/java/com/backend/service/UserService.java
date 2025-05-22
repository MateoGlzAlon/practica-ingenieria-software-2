package com.backend.service;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.specialdto.ProfileDTO;

public interface UserService {

    UserEntity findUserById(Long id);

    UserInputDTO findUserInputByID(Long id);

    ProfileDTO getProfileByUserId(Long id);
}
