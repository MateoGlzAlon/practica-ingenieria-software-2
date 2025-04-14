package com.backend.repository;

import com.backend.persistence.entity.UserEntity;

import com.backend.persistence.inputDTO.UserInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT new com.backend.persistence.inputDTO.UserInputDTO(u.username,u.email,u.password, u.about, u.avatarUrl) " +
            "FROM UserEntity u " +
            "WHERE u.id = :userId")
    UserInputDTO findInputUserById(@Param("userId") Long id);

}
