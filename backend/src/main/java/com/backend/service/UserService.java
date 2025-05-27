package com.backend.service;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.persistence.inputDTO.UserLinksInputDTO;

public interface UserService {

    UserEntity findUserById(Long id);

    UserInputDTO findUserInputByID(Long id);

    ProfileDTO getProfileByUserId(Long id);

    UserEntity changeUserLinks(UserLinksInputDTO userLinks);

    Long getUserIdByEmail(String email);
}
