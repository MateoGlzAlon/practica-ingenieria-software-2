package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.persistence.entity.TagEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("FROM TagEntity t " +
            "WHERE t.name = :tagName")
    Optional<TagEntity> findTagByName(@Param("tagName") String name);
}

